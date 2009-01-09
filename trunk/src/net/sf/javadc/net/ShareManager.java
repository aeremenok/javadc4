/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id:
package net.sf.javadc.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import junit.framework.Assert;
import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.gui.ShareManagerWindow;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITask;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.util.FileInfo;
import net.sf.javadc.util.FileUtils;
import net.sf.javadc.util.PerformanceContext;
import net.sf.javadc.util.hash.HashInfo;
import net.sf.javadc.util.hash.HashStore;
import net.sf.javadc.util.hash.HashTree;
import net.sf.javadc.util.hash.TTH;

import org.apache.log4j.Category;
import org.picocontainer.Startable;

/**
 * <CODE>ShareManager</CODE> manages the shared files of the connected user, calculates the size of the shared files
 */
public class ShareManager
    extends AbstractShareManager
    implements
        ITask,
        Runnable,
        Startable
{

    /**
     * <code>FileHasher</code> low priority Thread used for hashing new files
     * 
     * @author Marco Bazzoni
     * @see net.sf.javadc.util.hash.TTH
     */
    private class FileHasher
        extends Thread
    {

        private static final long MIN_BLOCK_SIZE = 64 * 1024;

        /**
         * When an object implementing interface <code>Runnable</code> is used to create a thread, starting the thread
         * causes the object's <code>run</code> method to be called in that separately executing thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may take any action whatsoever.
         * 
         * @see Thread#run()
         */
        @Override
        public void run()
        {

            try
            {
                hash();

            }
            catch ( Exception e )
            {
                logger.error( "Caught " + e.getClass().getName() + " in FileHasher.", e );
                // logger.error(e);
            }
        }

        private void hash()
        {
            hashing = true;
            while ( !fileToBeHashed.isEmpty() )
            {
                // Get element in stack
                FileInfo fileInfo;
                synchronized ( fileToBeHashed )
                {
                    fileInfo = fileToBeHashed.pop();
                }

                try
                {
                    MessageDigest tt = new TTH();
                    logger.debug( tt.getAlgorithm() );
                    FileInputStream fis;

                    fis = new FileInputStream( fileInfo.getAbsolutePath() );
                    int read;
                    byte[] in = new byte[1024];
                    long total = 0;
                    HashInfo hashInfo = new HashInfo();
                    HashTree hashTree = new HashTree( hashInfo );
                    // int depth =
                    // hashTree.calculateDepth(fileInfo.getLength());
                    long leafSize = Math.max( hashTree.calculateBlockSize( fileInfo.getLength(), 10 ), MIN_BLOCK_SIZE );
                    hashInfo.setLeafSize( leafSize );
                    // hashInfo.setLeafNumber(fileInfo.getLength()/leafSize);

                    while ( (read = fis.read( in )) > -1 )
                    {
                        tt.update( in, 0, read );
                        total = total + read;

                        if ( total % hashInfo.getLeafSize() == 0 )
                        {
                            byte[] digest = tt.digest();
                            ByteBuffer buffer = ByteBuffer.wrap( digest );// .allocateDirect(HashTree.HASH_SIZE);
                            // buffer.put(digest);
                            hashTree.addLeaf( buffer );
                            tt.reset();
                        }
                        // update status bar info every 100k
                        if ( total % 102400 == 0 )
                        {
                            fireHashingFile( fileInfo.getName(), ((double) total / (double) fileInfo.getLength()) );
                        }

                    }
                    fis.close();
                    // Hash remaining leaf
                    if ( total % hashInfo.getLeafSize() > 0 )
                    {
                        byte[] digest = tt.digest();
                        ByteBuffer buffer = ByteBuffer.wrap( digest );// .allocateDirect(HashTree.HASH_SIZE);
                        // buffer.put(digest);
                        hashTree.addLeaf( buffer );
                        tt.reset();
                    }

                    // Adjust leaf number
                    hashInfo.setLeafNumber( hashTree.getLeaves().size() );

                    // Calculate root
                    List tmpLeaves = Collections.unmodifiableList( hashTree.getLeaves() );
                    hashTree.calculateRoot( tmpLeaves );
                    String TTH = hashTree.getBase32Root();
                    logger.debug( "TTH:" + TTH );

                    hashInfo.setRoot( TTH );
                    // Store HashTree data
                    HashStore.getInstance().addHashTree( hashTree );
                    // Store HashInfo Metadata
                    fileInfo.setHash( hashInfo );

                    hashedFiles.add( fileInfo );

                    fireFileHashed( fileInfo.getName() );
                    tt.reset();
                }
                catch ( NoSuchAlgorithmException e )
                {
                    logger.error( "Unable to Hash: No algorithm", e );
                    // logger.error(e);
                }
                catch ( FileNotFoundException e )
                {
                    logger.error( "Unable to Hash: File not found", e );
                    // logger.error(e);
                }
                catch ( IOException e )
                {
                    logger.error( "Unable to Hash: IO Exception", e );
                    // logger.error(e);
                }

            }
            hashing = false;
            // reset status bar
            fireFileHashed( "" );
        }
    }

    /**
     * <code>HashedFilesManager</code> low priority Thread used for managing updates and deletions of Hashed Files
     * 
     * @author Marco Bazzoni
     */
    private class HashedFilesManager
        extends Thread
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Thread#run()
         */
        @Override
        public void run()
        {
            try
            {
                manage();
            }
            catch ( Exception e )
            {
                logger.error( "Caught " + e.getClass().getName() + " in HashedFilesManager.", e );
                // logger.error(e);
            }
        }

        private void manage()
        {
            managing = true;

            Set<String> keys = new HashSet<String>();

            synchronized ( hashedFiles )
            {
                keys.addAll( hashedFiles.fileNameKeySet() );
            }

            Iterator<String> iterator = keys.iterator();

            while ( iterator.hasNext() )
            {
                String path = iterator.next();
                FileInfo fileInfo;

                synchronized ( hashedFiles )
                {
                    fileInfo = hashedFiles.getByFileName( path );

                    if ( fileInfo == null )
                    {
                        logger.error( "No FileInfo instance " + "was mapped against " + path );

                        throw new RuntimeException( "No FileInfo instance " + "was mapped against " + path );

                    }
                    else if ( !sharedFiles.contains( fileInfo ) )
                    {
                        hashedFiles.remove( fileInfo );
                    }
                    else
                    {
                        File fd = new File( fileInfo.getAbsolutePath() );
                        if ( !fd.exists() )
                        {
                            hashedFiles.remove( fileInfo );
                        }
                        if ( fd.length() != fileInfo.getLength() )
                        {
                            synchronized ( fileToBeHashed )
                            {
                                fileToBeHashed.push( fileInfo );
                            }
                        }
                    }
                }

            }
            managing = false;
        }
    }

    // private Map hashedFiles;

    private final static Category   logger            = Category.getInstance( ShareManager.class );

    private final List<FileInfo>    sharedFiles       = new ArrayList<FileInfo>();
    private SharedFilesDictionary   hashedFiles;
    private final Stack<FileInfo>   fileToBeHashed    = new Stack<FileInfo>();
    private final BrowseListBuilder browseListBuilder = new BrowseListBuilder( sharedFiles );
    private boolean                 update            = true;
    private boolean                 running           = false;
    private boolean                 hashing           = false;
    private boolean                 managing          = false;

    private long                    shareSize;

    private ISettings               settings;
    private ITaskManager            taskManager;

    /**
     * Creates a <CODE>ShareManager</CODE> with the given <CODE>Settings
     * </CODE>
     */
    public ShareManager(
        ISettings _settings,
        ITaskManager _taskManager )
    {
        Assert.assertNotNull( _settings );
        Assert.assertNotNull( _taskManager );

        settings = _settings;
        taskManager = _taskManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IShareManager#getFile(java.lang.String)
     */
    public final File getFile(
        String filename )
    {
        // browselist
        if ( filename.equals( ConstantSettings.BROWSELIST ) )
        {
            // compressed browse list
            return new File( ConstantSettings.BROWSELIST );
        }
        else if ( filename.equals( ConstantSettings.BROWSELIST_ZIP ) )
        {
            // normal file
            return new File( ConstantSettings.BROWSELIST_ZIP );
        }
        else
        {
            final FileInfo[] shared = sharedFiles.toArray( new FileInfo[sharedFiles.size()] );

            for ( int i = 0; i < shared.length; i++ )
            {
                // if (shared[i].getName().equals(filename)) {
                if ( shared[i].getName().equals( filename ) )
                {
                    // final File f = new File(shared[i].getAbsolutePath());
                    final File f = new File( shared[i].getAbsolutePath() );

                    if ( f.isFile() )
                    {
                        return f;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Gets the shareSize attribute of the ShareManager object
     */
    public final long getSharedSize()
    {
        return shareSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        running = true;

        try
        {
            updateSharedFiles();
        }
        catch ( Exception e )
        {
            logger.error( "Caught " + e.getClass().getName() + " when trying to update shared files.", e );
            // logger.error(e);
        }

        running = false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ITask#runTask()
     */
    public final void runTask()
    {
        synchronized ( this )
        {
            // if update flag has been set and thread is not running
            if ( update && !running )
            {
                update = false;

                new Thread( this, "ShareManager" ).start();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IShareManager#search(net.sf.javadc.net.SearchRequest)
     */
    public final List<SearchResult> search(
        SearchRequest sr )
    {
        PerformanceContext cont = null;

        List<SearchResult> results = null;

        // search via hash information

        if ( sr.getFileType() == SearchRequest.TTH )
        {
            cont = new PerformanceContext( "ShareManager#search(SearchRequest) using TTH" ).start();

            FileInfo fileInfo = hashedFiles.getByHash( sr.getNamePattern() );

            if ( fileInfo != null )
            {
                results = new ArrayList<SearchResult>();
                results.add( new SearchResult( fileInfo, sr, settings ) );
            }

            // search via some other information
        }
        else
        {
            cont = new PerformanceContext( "ShareManager#search(SearchRequest) using standard" ).start();

            FileInfo[] shared = sharedFiles.toArray( new FileInfo[0] );

            int size = 0;

            for ( int i = 0; i < shared.length && size < ConstantSettings.MAX_SEARCH_RESULTS; i++ )
            {
                // match

                if ( sr.matches( shared[i] ) )
                {
                    if ( results == null )
                    {
                        results = new ArrayList<SearchResult>();
                    }
                    results.add( new SearchResult( shared[i], sr, settings ) );
                    size++;
                }
            }

        }

        cont.end();

        if ( cont.getDuration() > 100 )
        {
            logger.info( cont );
        }

        if ( results == null )
        {
            return Collections.emptyList();
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public void start()
    {
        logger.debug( "starting " + this.getClass().getName() );

        // the first update of the shared files is executed in the main thread
        // when the ShareManager is created, because the Hubs, which are
        // connected to in the IncompletesComponent are depending on it

        ShareManagerWindow dialog = new ShareManagerWindow( this );

        // add Dialog as ShareManagerListener
        // addListener((ShareManagerListener) Spin.over(dialog));
        dialog.setVisible( true );

        // XMLDecoder d = null;

        ObjectInputStream ois = null;

        logger.debug( "loading Hashed file list ... " );

        String hashedFileList = ConstantSettings.HASH_INDEX_FILENAME;
        try
        {
            // d = new XMLDecoder(new BufferedInputStream(new FileInputStream(hashedFileList)));
            // hashedFiles = (SharedFilesDictionary) d.readObject();

            ois = new ObjectInputStream( new BufferedInputStream( new FileInputStream( new File( hashedFileList ) ) ) );

            PerformanceContext cont = new PerformanceContext( "Loaded hashed file list" ).start();

            // long start = System.currentTimeMillis();
            hashedFiles = (SharedFilesDictionary) ois.readObject();
            // long end = System.currentTimeMillis();

            System.out.println( cont.end() );

            // System.out.println("Loaded hashed file list in " + (end - start) + " ms.");
            logger.debug( "Hashed file list loaded." );
        }
        catch ( Exception e )
        {
            logger.error( "cannot load hashed file list", e );
            hashedFiles = new SharedFilesDictionary();
            logger.debug( "new hashed file list created " );
        }
        finally
        {
            // if(d != null) d.close();

            if ( ois != null )
            {
                try
                {
                    ois.close();
                }
                catch ( IOException e1 )
                {
                    logger.error( "cannot close ObjectInputStream", e1 );
                }
            }
        }

        updateSharedFiles();

        dialog.setVisible( false );

        update = false;

        // TODO : Splash Screen hide
        // starts the Update Thread which updates the shared files when ever
        // the update flag is set to true
        // new Thread(this).start();
        taskManager.addTask( this );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public synchronized void stop()
    {

        logger.debug( "stopping " + this.getClass().getName() );

        // Save Hashed file list
        logger.debug( "saving Hashed file list ... " );

        // XMLEncoder e = null;

        ObjectOutputStream oos = null;

        try
        {
            String hashedFileList = ConstantSettings.HASH_INDEX_FILENAME;
            // e = new XMLEncoder(new BufferedOutputStream(
            // new FileOutputStream(hashedFileList)));

            // e.writeObject(hashedFiles);

            oos =
                new ObjectOutputStream( new BufferedOutputStream( new FileOutputStream( new File( hashedFileList ) ) ) );

            PerformanceContext cont = new PerformanceContext( "Saved hashed file list" ).start();
            // long start = System.currentTimeMillis();
            oos.writeObject( hashedFiles );
            // long end = System.currentTimeMillis();

            System.out.println( cont.end() );

            // System.out.println("Saved hashed file list in " + (end - start) +
            // " ms.");

            logger.debug( "Hashed file list saved." );

        }
        catch ( Exception e )
        {
            String error = "Error when trying to save hashing information to file.";
            logger.error( error, e );
        }
        finally
        {
            // if (e != null) e.close();

            if ( oos != null )
            {
                try
                {
                    oos.close();
                }
                catch ( IOException e1 )
                {
                    logger.error( "Caught " + e1.getClass().getName(), e1 );
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IShareManager#update()
     */
    public final synchronized void update()
    {
        update = true;

    }

    /**
     * Add the given File with the given name to the list of shared files
     * 
     * @param file File to be added
     * @param name filename of File to be added
     */
    private final void addFile(
        File file,
        String name )
    {
        if ( !file.isDirectory() )
        { // file is File
            // doesn't add general filesand executables
            int fileType = SearchRequest.getFileType( name );

            if ( fileType != 1 && fileType != 5 )
            {
                final String fileName = file.getAbsolutePath();
                final long length = file.length();

                final FileInfo fileInfo = new FileInfo( fileName, name, length );

                // logger.debug("Adding FileInfo " + fileInfo);
                sharedFiles.add( fileInfo );
                shareSize += length;
                // adds file only, if it is not yet contained

                if ( !hashedFiles.containsFileNameKey( fileInfo.getAbsolutePath() ) )
                {
                    fileToBeHashed.push( fileInfo );
                }
            }
        }
        else
        { // file is Directory

            final File[] children = file.listFiles();

            // logger.debug("Adding directory " + name + " ...");
            // has children
            if ( children != null )
            {
                for ( int i = 0; i < children.length; i++ )
                {
                    addFile( children[i], name + ConstantSettings.DC_PATH_SEPARATOR + children[i].getName() );

                }
                // logger.debug("Directory " + name + " added.");
                // has no children
            }
            else
            {
                // logger.error("IO-error " + file);
            }
        }
    }

    /**
     * Start the hashing thread
     */
    private void startHashingThread()
    {
        // Set up Hasher Thread
        FileHasher fileHasher = new FileHasher();
        // Low priority Thread
        fileHasher.setPriority( Thread.MIN_PRIORITY );
        fileHasher.setName( "File Hasher" );
        fileHasher.start();
    }

    /**
     * Start the managing thread
     */
    private void startManagingThread()
    {
        // Set up Hasher Thread
        HashedFilesManager manager = new HashedFilesManager();
        // Low priority Thread
        manager.setPriority( Thread.MIN_PRIORITY );
        manager.setName( "Hashed Files Manager" );
        manager.start();
    }

    /**
     * Update the shared files
     */
    private final void updateSharedFiles()
    {
        shareSize = 0;
        sharedFiles.clear();
        fileToBeHashed.clear();

        List<String> dirList = settings.getUploadDirs();
        // the list of directories can be null if Settings is a fresh instance
        if ( dirList == null )
        {
            dirList = new ArrayList<String>();
        }

        for ( String dir : dirList )
        {
            addFile( new File( dir ), FileUtils.getName( dir ) );
            logger.info( "Directory " + dir + " added." );
            fireDirectoryAdded( dir );
        }

        // creating browselist
        logger.debug( "Creating BrowseList ..." );

        fireCreatingBrowseList();

        try
        {
            browseListBuilder.createBrowseList();
            logger.debug( "BrowseList created." );
            fireBrowseListCreated();
        }
        catch ( IOException e )
        {
            logger.error( "Could not create browselist.", e );
        }

        if ( !hashing )
        {
            startHashingThread();
        }

        if ( !managing )
        {
            startManagingThread();
            // System.gc();
        }
    }
}
