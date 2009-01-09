/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.util.FileInfo;
import net.sf.javadc.util.FileInfoComparator;
import net.sf.javadc.util.FileUtils;
import net.sf.javadc.util.He3;
import net.sf.javadc.util.bzip2.BZip;

import org.apache.log4j.Category;

/**
 * <code>BrowseListBuilder</code> builds a browse list of the local client's shared files which can be requested and
 * browsed by remote clients
 * 
 * @author Timo Westk�mper
 */
public class BrowseListBuilder
{
    private final static Category logger    = Category.getInstance( ShareManager.class );

    /**
     * 
     */
    private final File            fileBzOut = new File( ConstantSettings.BROWSELIST_ZIP );

    /**
     * 
     */
    private final File            fileHeOut = new File( ConstantSettings.BROWSELIST );

    /**
     * 
     */
    private final File            fileIn    = new File( ConstantSettings.BROWSELIST + ".dec" );

    /**
     * list of shared files
     */
    private final List<FileInfo>  sharedFiles;

    /**
     * Create a BrowseListBuilder with the given list of shared files
     * 
     * @param sharedFiles List of shared files to be used
     */
    public BrowseListBuilder(
        List<FileInfo> sharedFiles )
    {
        Assert.assertNotNull( sharedFiles );
        this.sharedFiles = sharedFiles;
    }

    /**
     * Create the BrowseList
     * 
     * @throws IOException
     */
    public final void createBrowseList()
        throws IOException
    {
        String pathname = ConstantSettings.BROWSELIST + ".dec";
        OutputStream out = new BufferedOutputStream( new FileOutputStream( new File( pathname ) ) );

        List<FileInfo> list = new LinkedList<FileInfo>( sharedFiles );
        Collections.sort( list, new FileInfoComparator() );

        String lastPath = null;
        // iterates over the given files
        for ( FileInfo element : list )
        {
            String path = FileUtils.getPath( element.getName() );

            writePath( out, path, lastPath );
            writeFile( out, element, path );

            lastPath = path;
        }

        out.flush();
        out.close();

        // Timo : 30.05.2004
        // use buffered writing
        InputStream fis = new BufferedInputStream( new FileInputStream( fileIn ) );
        OutputStream fos = new BufferedOutputStream( new FileOutputStream( fileHeOut ) );
        InputStream fbzin = new BufferedInputStream( new FileInputStream( fileIn ) );
        OutputStream fbzout = new BufferedOutputStream( new FileOutputStream( fileBzOut ) );

        if ( fileIn.length() > 0 )
        {
            if ( !He3.encode( fis, fos ) )
            {
                logger.error( "An error occured during processing when encoding BrowseList." );
            }
            BZip.compress( fbzin, fbzout, 1, BZip.dummyProg );
        }
        else
        {
            logger.error( "An error occured during processing when encoding BrowseList, File length was 0" );
        }

        try
        {
            logger.debug( "Closing InputStream ..." );
            fis.close();
            logger.debug( "InputStream closed." );
        }
        catch ( Exception e )
        {
            logger.error( e );
        }

        try
        {
            logger.debug( "Closing compressed InputStream ..." );
            fbzin.close();
            logger.debug( "Compressed InputStream closed" );
        }
        catch ( Exception e )
        {
            logger.error( e );
        }

        try
        {
            logger.debug( "Closing OutputStream ..." );
            fos.close();
            logger.debug( "OutputStream closed." );
        }
        catch ( Exception e )
        {
            logger.error( e );
        }

        try
        {
            logger.debug( "Closing compressed OutputStream ..." );
            fbzout.close();
            logger.debug( "Compressed OutputStream closed." );
        }
        catch ( Exception e )
        {
            logger.error( e );
        }
    }

    /**
     * Write the given FileInfo element with the given path to the OutputStream
     * 
     * @param out OutputStream the FileInfo content should be written to
     * @param file FileInfo object whose content is to be written
     * @param path used for indentation
     * @throws IOException
     */
    private final void writeFile(
        OutputStream out,
        FileInfo file,
        String path )
        throws IOException
    {
        // the FileUtils version seems to be more efficient
        int depth = FileUtils.getPathDepth( path );

        StringBuffer c = new StringBuffer();

        for ( int i = 0; i <= depth; i++ )
        {
            c.append( '\t' ); // out.write('\t');
        }

        c.append( FileUtils.getName( file.getName() ) ).append( "|" ).append( file.getLength() ).append( "\r\n" );

        out.write( c.toString().getBytes( "ISO-8859-1" ) );
    }

    /**
     * Write the given path to the OutputStream
     * 
     * @param out OutputStream the path content is to be written to
     * @param path content to be written
     * @param lastPath previous path element, which was written to the OutputStream
     * @throws IOException
     */
    private final void writePath(
        OutputStream out,
        String path,
        String lastPath )
        throws IOException
    {
        String splitPattern = "\\\\";

        // first element
        if ( lastPath == null && path != null )
        {
            // int level = 0;

            String[] elements = path.split( splitPattern );

            StringBuffer c = new StringBuffer();

            // iterates over the path elements
            for ( int j = 0; j < elements.length; j++ )
            {
                for ( int i = 0; i < j; i++ )
                {
                    c.append( '\t' ); // out.write('\t');
                }

                c.append( elements[j] ).append( "\r\n" );
            }

            out.write( c.toString().getBytes( "ISO-8859-1" ) );

            // no path change
        }
        else if ( path != null && path.equals( lastPath ) )
        {
            return;
            // path != null && lastPath != null
        }
        else
        {
            String[] elements = path.split( splitPattern );
            String[] oldElements = lastPath.split( splitPattern );

            int level = 0;
            int j = 0;

            String newToken;
            String oldToken;

            StringBuffer c = new StringBuffer();

            for ( int k = 0; k < elements.length; k++ )
            {
                newToken = elements[k];
                oldToken = null;

                if ( j < oldElements.length )
                {
                    oldToken = oldElements[j++];
                }

                if ( newToken != null && newToken.equals( oldToken ) )
                {
                    level++;
                    // if the root directories match, we just indent once.
                }
                else if ( newToken != null && (oldToken == null || !newToken.equals( oldToken )) )
                {
                    // subdirectory or another path, print directory.
                    for ( int i = 0; i < level; i++ )
                    {
                        c.append( '\t' ); // out.write('\t');
                    }

                    // indent and write directory info
                    c.append( newToken ).append( "\r\n" );

                    level++;
                }
            }

            out.write( c.toString().getBytes( "ISO-8859-1" ) );
        }
    }
}
