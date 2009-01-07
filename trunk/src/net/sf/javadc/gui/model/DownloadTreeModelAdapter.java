/*
 * Copyright (C) 2001 Ryan Sweny, cabin@geeky.net Copyright (C) 2004 Timo Westkämper This program is free software; you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: DownloadTreeModelAdapter.java,v 1.19 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui.model;

import java.util.List;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.listeners.RequestsModelListener;
import net.sf.javadc.net.DownloadRequest;

import org.apache.log4j.Category;

/**
 * <CODE>DownloadTreeModelAdapter</CODE> is used as an adapter to the <CODE>
 * RequestsModel</CODE> by providing the
 * <CODE>TreeTableModel</CODE> interface on top of it
 * 
 * @author Timo Westk�mper
 */
public class DownloadTreeModelAdapter
    extends AbstractTreeTableModel
    implements
        TreeTableModel
{
    /** ********************************************************************** */
    private class MyRequestsModelListener
        implements
            RequestsModelListener
    {

        // Connections

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.RequestsModelListener#connectionAdded(net.sf.javadc.interfaces.IConnection,
         *      int)
         */
        public void connectionAdded(
            IConnection connection,
            int index )
        {
            logger.debug( "Received connectionAdded for connection " + connection );

            Object inserted = downloadNodeFactory.createConnectionNode( connection );

            fireTreeNodesInserted( this, new Object[] { root }, new int[] { index }, new Object[] { inserted } );

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.RequestsModelListener#connectionChanged(net.sf.javadc.interfaces.IConnection,
         *      int)
         */
        public void connectionChanged(
            IConnection connection,
            int index )
        {
            logger.debug( "Received connectionChanged for connection " + connection );

            Object changed = downloadNodeFactory.createConnectionNode( connection );

            fireTreeNodesChanged( this, new Object[] { root }, new int[] { index }, new Object[] { changed } );

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.RequestsModelListener#connectionRemoved(net.sf.javadc.interfaces.IConnection,
         *      int)
         */
        public void connectionRemoved(
            IConnection connection,
            int index )
        {
            logger.debug( "Received connectionRemoved for connection " + connection );

            Object forRemoval = downloadNodeFactory.createConnectionNode( connection );

            fireTreeNodesRemoved( this, new Object[] { root }, new int[] { index }, new Object[] { forRemoval } );

        }

        // Download Requests

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.RequestsModelListener#requestAdded(net.sf.javadc.net.client.Client,
         *      net.sf.javadc.net.DownloadRequest, int)
         */
        public void requestAdded(
            IClient client,
            DownloadRequest downloadRequest,
            int index )
        {
            logger.debug( "Received requestAdded for client " + client + " and DownloadRequest " + downloadRequest );

            if ( client != null )
            {
                updateConnection( client.getConnection() );

            }
            else
            {
                logger.debug( "Client was null." );

            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.RequestsModelListener#requestChanged(net.sf.javadc.net.client.Client,
         *      net.sf.javadc.net.DownloadRequest, int)
         */
        public void requestChanged(
            IClient client,
            DownloadRequest downloadRequest,
            int index )
        {
            logger.debug( "Received requestChanged for client " + client + " and DownloadRequest " + downloadRequest );

            // fireTreeNodesChanged
            // Client should always be present
            updateConnection( client.getConnection() );

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.RequestsModelListener#requestRemoved(net.sf.javadc.net.client.Client,
         *      net.sf.javadc.net.DownloadRequest, int)
         */
        public void requestRemoved(
            IClient client,
            DownloadRequest downloadRequest,
            int index )
        {
            logger.debug( "Received requesRemoved for client " + client + " and DownloadRequest " + downloadRequest );

            if ( client != null )
            {
                updateConnection( client.getConnection() );

            }
            else
            {
                logger.debug( "Client was null." );

            }

        }

        /**
         * @param connection
         */
        private void updateConnection(
            IConnection connection )
        {
            // int index = getConnections().indexOf(connection);

            logger.debug( "Received updateConnection for connection " + connection );

            // fireTreeStructureChanged(this, new Object[] { root },
            // new int[] { index }, new Object[] { connection });
            fireTreeStructureChanged( this, new Object[] { root }, new int[] { 0 }, new Object[] { root } );

        }

    }

    // private static final String EMPTY_STRING = "";
    private final static Category       logger              = Category.getInstance( DownloadTreeModelAdapter.class );

    private final static String         ERROR_MESSAGE       = "Not to be used directly from DownloadTreeModelAdapter";

    static final protected String[]     cNames              =
                                                                { "Nick", "Name", "Ext", "Path", "Address", "Status",
                    "Info"                                     };

    static final protected Class[]      cTypes              =
                                                                { TreeTableModel.class, String.class, String.class,
                    String.class, String.class, String.class, String.class

                                                                };

    // NOTE : this instance doesn't need to be static, as the
    // DownloadTreeModelAdapter has only one runtime instance
    private final DownloadNodeFactory   downloadNodeFactory = new DownloadNodeFactory();

    private final IRequestsModel        model;

    private final RequestsModelListener modelListener       = new MyRequestsModelListener();

    /** ********************************************************************** */

    /**
     * Create a DownloadTreeModelAdapter with the given IRequestsModel
     * 
     * @param model IRequestsModel instance to be used
     */
    public DownloadTreeModelAdapter(
        IRequestsModel model )
    {
        super();

        if ( model == null )
        {
            throw new NullPointerException( "model was null" );
        }

        this.model = model;
        this.model.addListener( modelListener );

        setRoot( new DownRootNode( model.getActiveConnections() ) );

    }

    /**
     * Add the given row to the underlying mode
     * 
     * @param c Connection to be added
     */
    public void addRow(
        IConnection c )
    {
        throw new RuntimeException( ERROR_MESSAGE );

    }

    /**
     * Delete the given row from the underlying model
     * 
     * @param c Connection to be deleted
     */
    public void deleteRow(
        IConnection c )
    {
        throw new RuntimeException( ERROR_MESSAGE );

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(
        Object node,
        int i )
    {
        IDownloadNode n = (IDownloadNode) node;

        return n.getChild( i );

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(
        Object node )
    {
        IDownloadNode n = (IDownloadNode) node;

        return n.getNumChildren();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.TreeTableModel#getColumnClass(int)
     */
    @Override
    public Class getColumnClass(
        int column )
    {
        return cTypes[column];

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.TreeTableModel#getColumnCount()
     */
    public int getColumnCount()
    {
        return cNames.length;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.TreeTableModel#getColumnName(int)
     */
    public String getColumnName(
        int column )
    {
        return cNames[column];

    }

    /**
     * Return the active connections as a List
     * 
     * @return
     */
    public List getConnections()
    {
        return model.getActiveConnections();

    }

    /**
     * Return the number of active connection
     * 
     * @return
     */
    public int getNumConnections()
    {
        // return getChildCount(root);
        return model.getActiveConnections().size();

    }

    /**
     * Return the row with the given index
     * 
     * @param i index of the row to be returned
     * @return the Connection with the given idnex
     */
    public IConnection getRow(
        int i )
    {
        return (IConnection) model.getActiveConnections().get( i );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.TreeTableModel#getValueAt(java.lang.Object,
     *      int)
     */
    public Object getValueAt(
        Object obj,
        int column )
    {
        if ( obj instanceof IDownloadNode )
        {
            IDownloadNode node = (IDownloadNode) obj;

            return node.getValueAt( column );

        }
        else
        {
            String error =
                "getValue(Object obj, int column) called " + "on object of class " + obj.getClass().getName();
            logger.error( error );
            throw new RuntimeException( error );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    @Override
    public boolean isLeaf(
        Object node )
    {
        Class cl = node.getClass();

        // ConnectionNodes and RootNodes are not leafes
        if ( cl.equals( DownConnectionNode.class ) || cl.equals( DownRootNode.class ) )
        {
            return false;

            // everything else can be considered a leaf node
        }
        else
        {
            return true;

        }

    }

    /**
     * Refresh the downloads
     */
    public void refreshDownloads()
    {
        throw new RuntimeException( ERROR_MESSAGE );

    }

}

/*******************************************************************************
 * $Log: DownloadTreeModelAdapter.java,v $ Revision 1.19 2005/10/02 11:42:28 timowest updated sources and tests Revision
 * 1.18 2005/09/26 17:19:52 timowest updated sources and tests Revision 1.17 2005/09/25 16:40:58 timowest updated
 * sources and tests Revision 1.16 2005/09/15 17:32:29 timowest added null checks Revision 1.15 2005/09/14 07:11:49
 * timowest updated sources
 */
