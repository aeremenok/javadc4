/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.gui.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;

/**
 * <CODE>FileListModel</CODE> represents a list model which can be used to visualize the contents of a client's file
 * list
 * 
 * @author Timo Westk�mper
 */
public class FileListModel
    extends AbstractFileListModel
{
    private final static Category         logger = Category.getInstance( FileListModel.class );

    private final ArrayList<FileTreeNode> files  = new ArrayList<FileTreeNode>();

    private final FileTreeNode            root   = new FileTreeNode( new File( File.separator ), 0 );

    private BufferedReader                reader = null;

    /**
     * Create a FileListModel
     */
    public FileListModel()
    {
        String sourceFile = "MyList.DcLst.dec";

        try
        {
            reader = new BufferedReader( new InputStreamReader( ClassLoader.getSystemResourceAsStream( sourceFile ) ) );
        }
        catch ( NullPointerException e )
        {
            logger.error( "Can't use MyList.DcList.dec, using default file list instead." );

            // use example file list instead
            sourceFile = "net/sf/javadc/resources/MyList.DcLst.dec";

            logger.error( "Can't use created file list in main folder, using default file list " + sourceFile +
                " instead" );
            reader = new BufferedReader( new InputStreamReader( ClassLoader.getSystemResourceAsStream( sourceFile ) ) );
            // todo what if file's not found?
        }
        catch ( Exception e )
        {
            String error = "Caught " + e.getClass().getName();
            logger.error( error, e );
            throw new RuntimeException( error, e );
        }
    }

    /**
     * Create a FileListModel instance for the given nick with the given download directory
     * 
     * @param nick Nick for which the FileListModel is created
     * @param downloadDir download directory from which the file list is obtained
     */
    public FileListModel(
        String nick,
        String downloadDir )
    {
        String sourceFile = downloadDir + File.separator + "MyList." + nick + ".DcLst.dec";

        try
        {
            reader = new BufferedReader( new InputStreamReader( new FileInputStream( sourceFile ) ) );

        }
        catch ( FileNotFoundException e )
        {
            logger.error( "File " + sourceFile + " could not be found", e );

        }
        // catch (IOException io) {
        // logger.error("Error when reading file " + sourceFile, io);
        // }
    }

    /**
     * Add the given FileTreeNode to the model
     * 
     * @param node
     */
    public final void addRow(
        FileTreeNode node )
    {
        // logger.debug("Adding row " + node);

        // String parentFile = node.file.getParent();

        // FileNode parent = getNode(parentFile);
        FileTreeNode parent = (FileTreeNode) node.getParent();

        parent.addChild( node );

        files.add( node );

        // fireTreeNodesInserted(this, new Object[] { parent }, new int[] {
        // files
        // .indexOf(parent) - 1 }, new Object[] { node });

        // logger.debug("Row " + node + " added.");
    }

    /**
     * Create the underlying model for the FileListModel by reading from the InputStream specified in the constructor
     */
    public void createModel()
    {
        try
        {
            FileTreeNode[] paths = new FileTreeNode[256];

            logger.debug( "Creating model out of file list." );

            String line;

            while ( (line = reader.readLine()) != null )
            {
                String cleanLine = line.trim();
                int depth = line.lastIndexOf( "\t" );

                // tab characters determine the depth of file in the tree
                depth++;

                String currentPath = "";

                for ( int i = 0; i < depth; i++ )
                {
                    currentPath = currentPath + File.separator + paths[i].getFile().getName();

                }

                FileTreeNode node;
                int div = cleanLine.indexOf( "|" );

                // this is a leaf
                if ( div > -1 )
                {
                    File f = new File( currentPath + File.separator + cleanLine.substring( 0, div ) );

                    node = new FileTreeNode( f, Integer.parseInt( cleanLine.substring( div + 1, cleanLine.length() ) ) );

                    // this is a directory
                }
                else
                {
                    File f = new File( currentPath + File.separator + cleanLine );

                    node = new FileTreeNode( f, 0 );
                    paths[depth] = node;

                }

                if ( depth > 0 )
                {
                    node.setParent( paths[depth - 1] );

                }
                else
                {
                    node.setParent( root );

                }

                addRow( node );

            }

            fireTreeStructureChanged( this, new Object[] { root }, new int[] { 0 }, new Object[] { root } );

            logger.debug( "Model created successfully." );

            // TODO Do we want to cache the list for offline browsing, delete
            // the file or not use files at all and work with the streams
            // directly...
            // File f = new File(sourceFile);
            // f.delete();

        }
        catch ( IOException io )
        {
            logger.error( "Error when reading file." );
            logger.error( io );

        }

    }

    /**
     * @return Returns the files.
     */
    public List getFiles()
    {
        return files;
    }

    /**
     * @return Returns the root.
     */
    public FileTreeNode getRoot()
    {
        return root;
    }
}

/*******************************************************************************
 * $Log: FileListModel.java,v $ Revision 1.15 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.14
 * 2005/09/25 16:40:58 timowest updated sources and tests Revision 1.13 2005/09/14 07:11:49 timowest updated sources
 */
