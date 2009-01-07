/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.gui;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import net.sf.javadc.gui.model.RowTableModel;
import net.sf.javadc.gui.model.SortableTableListener;
import net.sf.javadc.gui.util.DownloadResumePopupMenu;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;

public class SearchResultsController
    implements
        SortableTableListener
{

    // private static final Category logger = Category
    // .getInstance(SearchResultsController.class);
    /**
     * 
     */
    private final RowTableModel    model;

    // components
    /**
     * 
     */
    private final IHub             hub;

    /**
     * 
     */
    private final ISettings        settings;

    /** 
     * 
     */
    private final IDownloadManager downloadManager;

    /**
     * Create a SearchResultsController with the given IHub, ISettings and RowTableModel
     * 
     * @param _hub IHub instance to be used
     * @param _settings ISettings instance to be used
     * @param _downloadManager IDownloadManager instance to be used
     * @param _model RowTableModel to be used
     */
    public SearchResultsController(
        IHub _hub,
        ISettings _settings,
        IDownloadManager _downloadManager,
        RowTableModel _model )
    {

        if ( _hub == null )
        {
            throw new NullPointerException( "hub was null" );
        }

        if ( _settings == null )
        {
            throw new NullPointerException( "settings was null" );
        }

        if ( _downloadManager == null )
        {
            throw new NullPointerException( "downloadManager was null" );
        }

        if ( _model == null )
        {
            throw new NullPointerException( "model was null" );
        }

        hub = _hub;
        settings = _settings;

        // already spinned
        downloadManager = _downloadManager;
        model = _model;

    }

    /** ********************************************************************** */
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

        // iterate over the selected rows

        // logger.debug("Iterating over " + selectedRows.length + " selected
        // rows.");

        for ( int i = 0; i < selectedRows.length; i++ )
        {

            downloadManager.requestDownload( new DownloadRequest( (SearchResult) model.getRow( selectedRows[i] ),
                settings ) );

        }

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
        int[] selectedRows )
    {

        List searchResults = new ArrayList();

        for ( int i = 0; i < selectedRows.length; i++ )
        {
            searchResults.add( model.getRow( selectedRows[i] ) );
        }

        // single selection action

        DownloadResumePopupMenu popup =
            new DownloadResumePopupMenu( hub, (SearchResult) model.getRow( row ), searchResults, settings,
                downloadManager );

        popup.show( e.getComponent(), e.getX(), e.getY() );

    }

}

/*******************************************************************************
 * $Log: SearchResultsController.java,v $ Revision 1.13 2005/10/02 11:42:28 timowest updated sources and tests Revision
 * 1.12 2005/09/14 07:11:49 timowest updated sources
 */
