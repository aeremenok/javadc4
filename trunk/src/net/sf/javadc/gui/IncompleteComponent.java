/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: IncompleteComponent.java,v 1.19 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import net.sf.javadc.gui.model.RowTableModelAdapter;
import net.sf.javadc.gui.model.SortableTable;
import net.sf.javadc.gui.model.SortableTableListener;
import net.sf.javadc.gui.util.ByteCellRenderer;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.hub.AllHubs;
import net.sf.javadc.util.FileUtils;

import org.apache.log4j.Category;

import spin.Spin;

/**
 * <CODE>IncompleteComponent</CODE> provides a <CODE>SortabelTable</CODE> view on the queue of
 * <CODE>DownloadRequests</CODE>
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.19 $ $Date: 2005/10/02 11:42:28 $
 */
public class IncompleteComponent
    extends JPanel
    implements
        SortableTableListener,
        KeyListener
{
    /**
     * 
     */
    private static final long          serialVersionUID = 1181165713477183455L;

    private final static Category      logger           = Category.getInstance( IncompleteComponent.class );

    /**
     * 
     */
    private final RowTableModelAdapter model;

    /**
     * 
     */
    private final SortableTable        incompleteTable;

    // external components
    // private final ISettings settings;
    /**
     * 
     */
    private IDownloadManager           downloadManager;

    /**
     * 
     */
    private IHub                       hub;

    /**
     * Create an IncompleteComponent instance with the given ISettings, IRequestsModel and IDownloadRequestResumer
     * 
     * @param _requestsModel IRequestsModel to be used
     * @param _downloadManager IDownloadManager instance to be used
     */
    public IncompleteComponent(
        IRequestsModel _requestsModel,
        IDownloadManager _downloadManager,
        AllHubs _hub )
    {
        super( new BorderLayout() );

        // if (_settings == null)
        // throw new NullPointerException("settings was null.");

        if ( _requestsModel == null )
        {
            throw new NullPointerException( "requestsModel was null." );
        }

        if ( _downloadManager == null )
        {
            throw new NullPointerException( "downloadManager was null." );
        }

        if ( _hub == null )
        {
            throw new NullPointerException( "hub was null." );
        }

        // spinned components
        // settings = _settings;

        downloadManager = (IDownloadManager) Spin.off( _downloadManager );

        hub = (IHub) Spin.off( _hub );

        // wraps the general RequestsModel into a form used by the
        // SortableTable
        model =
            new RowTableModelAdapter( _requestsModel, new String[] { "File", "Hash", "Temp Size", "Size", "Nick",
                            "Host", "Hub", "State" } );

        incompleteTable =
            new SortableTable( new int[] { 120, 100, 20, 20, 50, 50, 50, 20 }, this, model, "incomplete downloads" );

        incompleteTable.getTable().setDefaultRenderer( Long.class, new ByteCellRenderer() );

        add( incompleteTable, BorderLayout.CENTER );
        incompleteTable.getTable().addKeyListener( this );

    }

    /** ********************************************************************** */

    /**
     * Cancel the DownloadRequest located in the row with the given index
     * 
     * @param row index of the DownloadRequest to be deleted
     */
    public final void cancel(
        int[] selectedRows )
    {

        List rows = new ArrayList();

        for ( int i = 0; i < selectedRows.length; i++ )
        {
            rows.add( model.getRow( selectedRows[i] ) );
        }

        for ( Iterator i = rows.iterator(); i.hasNext(); )
        {
            model.deleteRow( i.next() );
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.SortableTableListener#cellSelected(int, int)
     */
    public final void cellSelected(
        int row,
        int column,
        int[] selectedRows )
    {

        List requests = new ArrayList();

        for ( int i = 0; i < selectedRows.length; i++ )
        {
            requests.add( model.getRow( selectedRows[i] ) );

        }

        for ( Iterator i = requests.iterator(); i.hasNext(); )
        {
            downloadManager.requestDownload( (DownloadRequest) i.next() );

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public final void keyPressed(
        KeyEvent ke )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public final void keyReleased(
        KeyEvent ke )
    {
        if ( ke.getKeyCode() == KeyEvent.VK_DELETE )
        {
            int row = incompleteTable.getTable().getSelectedRow();

            if ( row > -1 )
            {
                int[] rows = { row };
                cancel( rows );

            }

            if ( model.getRowCount() > 1 )
            {
                if ( row < model.getRowCount() )
                {
                    incompleteTable.getTable().setRowSelectionInterval( row, row );

                }
                else
                {
                    incompleteTable.getTable().setRowSelectionInterval( row - 1, row - 1 );

                }

            }

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public final void keyTyped(
        KeyEvent ke )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.SortableTableListener#showPopupClicked(int,
     *      int, java.awt.event.MouseEvent)
     */
    public final void showPopupClicked(
        final int row,
        final int column,
        MouseEvent e,
        final int[] selectedRows )
    {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem close = new JMenuItem( "Remove" );
        close.setIcon( FileUtils.loadIcon( "images/16/edittrash.png" ) );

        JMenuItem reconnect = new JMenuItem( "Resume" );
        reconnect.setIcon( FileUtils.loadIcon( "images/16/reload.png" ) );

        JMenuItem search = new JMenuItem( "Search for alternates" );

        JMenuItem searchFreeSlots = new JMenuItem( "Search for alternates with free slots" );

        close.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {

                // multi selection

                cancel( selectedRows );

            }

        } );

        reconnect.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {

                // multi selection

                cellSelected( row, column, selectedRows );

            }

        } );

        search.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent arg0 )
            {

                // multiselection ( general )

                searchForAlternates( selectedRows, false );

            }

        } );

        searchFreeSlots.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent arg0 )
            {

                // multiselection ( only results with free slots)

                searchForAlternates( selectedRows, true );

            }

        } );

        popup.add( reconnect );
        popup.add( search );
        popup.add( searchFreeSlots );

        popup.addSeparator();

        popup.add( close );

        popup.show( e.getComponent(), e.getX(), e.getY() );

    }

    /**
     * Search for alternates for the search results in the given rows
     * 
     * @param selectedRows indices of search results
     * @param freeSlots true, if only results with free slots are searched and false, if not
     */
    protected void searchForAlternates(
        int[] selectedRows,
        boolean freeSlots )
    {

        List rows = new ArrayList();

        for ( int i = 0; i < selectedRows.length; i++ )
        {
            rows.add( model.getRow( selectedRows[i] ) );
        }

        for ( Iterator i = rows.iterator(); i.hasNext(); )
        {

            SearchResult searchResult = ((DownloadRequest) i.next()).getSearchResult();

            if ( searchResult.getTTH() != null )
            {

                SearchRequest sr = new SearchRequest( searchResult.getTTH(), SearchRequest.TTH, 0, true, freeSlots );

                try
                {
                    hub.search( sr );

                }
                catch ( IOException e )
                {
                    logger.error( "Caught " + e.getClass().getName() + " when trying to search with " + sr, e );
                }

            }
        }

    }

}

/*******************************************************************************
 * $Log: IncompleteComponent.java,v $ Revision 1.19 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.18
 * 2005/09/25 16:40:58 timowest updated sources and tests Revision 1.17 2005/09/14 07:11:49 timowest updated sources
 */
