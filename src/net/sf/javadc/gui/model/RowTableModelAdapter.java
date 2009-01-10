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

import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.listeners.RequestsModelListener;
import net.sf.javadc.listeners.RequestsModelListenerBase;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;

import org.apache.log4j.Category;

/**
 * <CODE>RowTableModelAdapter</CODE> represents an adapter on top of the <CODE>RequestsModel</CODE> which presents the
 * queue of <CODE>
 * DownloadRequests</CODE> in a table view
 * 
 * @author Timo Westk�mper
 */
public class RowTableModelAdapter
    extends AbstractTableModel
{
    private class MyRequestsModelListener
        extends RequestsModelListenerBase
    {
        // NOTE : this listener is not interested in Connection related events

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.RequestsModelListener#requestAdded(net.sf.javadc.net.client.Client,
         *      net.sf.javadc.net.DownloadRequest, int)
         */
        @Override
        public void requestAdded(
            IClient client,
            DownloadRequest dr,
            int index )
        {
            fireTableRowsInserted( index, index );

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.RequestsModelListener#requestChanged(net.sf.javadc.net.client.Client,
         *      net.sf.javadc.net.DownloadRequest, int)
         */
        @Override
        public void requestChanged(
            IClient client,
            DownloadRequest downloadRequest,
            int index )
        {
            fireTableRowsUpdated( index, index );

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.RequestsModelListener#requestRemoved(net.sf.javadc.net.client.Client,
         *      net.sf.javadc.net.DownloadRequest, int)
         */
        @Override
        public void requestRemoved(
            IClient client,
            DownloadRequest dr,
            int index )
        {
            fireTableRowsDeleted( index, index );
        }

    }

    /**
     * 
     */
    private static final long           serialVersionUID = 3512475134629906044L;

    private final static String         ERROR_MESSAGE    = "Not to be used directly from RowTableModelAdapter";

    private final static Category       logger           = Category.getInstance( RowTableModelAdapter.class );

    private final IRequestsModel        model;

    private final RequestsModelListener modelListener    = new MyRequestsModelListener();

    private final String[]              columnNames;

    /**
     * Create a RowTableModelAdapter with the given IRequestsModel and the list of column headers
     * 
     * @param requestsModel IRequestsModel instance to be used
     * @param columnNames array of column headers
     */
    public RowTableModelAdapter(
        IRequestsModel requestsModel,
        String[] columnNames )
    {

        if ( requestsModel == null )
        {
            throw new NullPointerException( "requestsModel was null" );
        }

        if ( columnNames == null )
        {
            throw new NullPointerException( "columnNames was null" );
        }

        // already wrapped
        this.model = requestsModel;
        this.model.addListener( modelListener );

        this.columnNames = columnNames;

    }

    /**
     * @param row
     * @param columns
     */
    public void addRow(
        Object row,
        Object[] columns )
    {
        throw new RuntimeException( ERROR_MESSAGE );
    }

    public void clear()
    {
        throw new RuntimeException( ERROR_MESSAGE );
    }

    /**
     * Delete the given DownloadRequest from the underlying model
     * 
     * @param row
     */
    public void deleteRow(
        DownloadRequest row )
    {
        model.removeDownloadRequest( row );
    }

    /**
     * Delete the DownloadRequest with the given index from the underlying model
     * 
     * @param index
     */
    public void deleteRow(
        int index )
    {
        DownloadRequest dr = model.getAllDownloads().get( index );
        if ( dr != null )
        {
            model.removeDownloadRequest( dr );
        }
        else
        {
            logger.warn( "DownloadRequest with index " + index + " could not be found." );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    @Override
    public final Class<? extends Object> getColumnClass(
        int columnIndex )
    {
        if ( getRowCount() > 0 )
        {
            final Object o = getValueAt( 0, columnIndex );

            return o != null ? o.getClass() : Object.class;
        }
        return super.getColumnClass( columnIndex );
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount()
    {
        return columnNames.length;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    @Override
    public final String getColumnName(
        int column )
    {
        return columnNames[column];

    }

    /**
     * Get the DownloadRequest with the given index
     * 
     * @param row
     * @return
     */
    public DownloadRequest getRow(
        int row )
    {
        List<DownloadRequest> rows = model.getAllDownloads();
        if ( row < rows.size() )
        {
            return rows.get( row );
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount()
    {
        return model.getAllDownloads().size();

    }

    /**
     * Get the index of the given DownloadRequest
     * 
     * @param row
     * @return
     */
    public int getRowIndex(
        Object row )
    {
        return model.getAllDownloads().indexOf( row );

    }

    /**
     * Get the list of rows of this RowTableModel instance
     * 
     * @return
     */
    public List<DownloadRequest> getRows()
    {
        return model.getAllDownloads();

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(
        int rowIndex,
        int columnIndex )
    {
        if ( rowIndex < getRowCount() )
        {
            DownloadRequest dr;

            try
            {
                dr = model.getAllDownloads().get( rowIndex );
            }
            catch ( Exception e )
            {
                logger.error( e );
                return null;
            }

            if ( columnIndex < getColumnCount() )
            {
                try
                {
                    return getColumnsForRow( dr )[columnIndex];
                }
                catch ( Exception e )
                {
                    logger.error( e );
                }
            }
            else
            {
                logger.error( "Column index " + columnIndex + " exceeded " + "amount of available columns." );
            }
        }
        else
        {
            logger.error( "Row index " + rowIndex + " exceeded amount of " + "available rows." );
        }

        return null;

    }

    /**
     * @param row
     * @param columns
     */
    public void rowChanged(
        Object row,
        Object[] columns )
    {
        throw new RuntimeException( ERROR_MESSAGE );
    }

    /**
     * Set the list of rows of this RowTableModel instance
     * 
     * @param rows
     */
    public void setRows(
        @SuppressWarnings( "unused" ) List<DownloadRequest> rows )
    {
        throw new RuntimeException( ERROR_MESSAGE );
    }

    /**
     * Notify registered listeners that the underlying model has changed
     */
    public void update()
    {
        fireTableDataChanged();

    }

    /**
     * Get the columns for the given DownloadRequest instance
     * 
     * @param dr
     * @return
     */
    private final Object[] getColumnsForRow(
        DownloadRequest dr )
    {
        SearchResult sr = dr.getSearchResult();

        // segmented download
        if ( dr.isSegment() )
        {
            return new Object[] { sr.getFilename(), sr.getTTH(), new Long( dr.getTempFileSize() ),

                            // segment size
                            new Long( dr.getSegment().y - dr.getSegment().x ), sr.getNick(), sr.getHost(),
                            sr.getHub().getName(), dr.getState() };

            // normal download
        }

        return new Object[] { sr.getFilename(), sr.getTTH(), new Long( dr.getTempFileSize() ),
        // full file size
                        new Long( sr.getFileSize() ), sr.getNick(), sr.getHost(), sr.getHub().getName(), dr.getState() };

    }

}
