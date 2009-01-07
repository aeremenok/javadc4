/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */
// $Id: SearchComponent.java,v 1.24 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import net.sf.javadc.gui.model.RowTableModel;
import net.sf.javadc.gui.model.SortableTable;
import net.sf.javadc.gui.util.ByteCellRenderer;
import net.sf.javadc.gui.util.SplitPane;
import net.sf.javadc.gui.util.UserCellRenderer;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.util.FileUtils;

import org.apache.log4j.Category;

/**
 * <CODE>SearchComponent</CODE> is a subcomponent which is utilized in the <CODE>HubComponent</CODE> and
 * <CODE>MultiSearchComponent</CODE> to allow the execution of searches on specific or all connected <CODE>Hubs</CODE>.
 * 
 * @author Timo Westk�mper
 */
public class SearchComponent
    extends BasePanel
{

    /**
     * 
     */
    private static final long                serialVersionUID = 8072157846911090407L;

    private static final Category            logger           = Category.getInstance( SearchComponent.class );

    /*
     * private SearchComponentHubListener hubListener = new
     * SearchComponentHubListener( this);
     */
    /**
     * 
     */
    private final SearchComponentHubListener hubListener;

    /**
     * 
     */
    private final List                       models           = new ArrayList();

    /**
     * 
     */
    private final List                       searchRequests   = new ArrayList();

    /**
     * 
     */
    private final JTabbedPane                tabbedPane       = new JTabbedPane();

    /**
     * 
     */
    private boolean                          initialized      = false;

    // external components
    /**
     * 
     */
    private final IHub                       hub;

    /**
     * 
     */
    private final ISettings                  settings;

    /**
     * 
     */
    private final IDownloadManager           downloadManager;

    /**
     * Create a SearchComponent with the given IHub instance and ISettings instance
     * 
     * @param _hub IHub instance to be used
     * @param _settings ISettings instance to be used
     * @param _downloadManager IDownloadManager instance to be used
     */
    public SearchComponent(
        IHub _hub,
        ISettings _settings,
        IDownloadManager _downloadManager )
    {
        super( new BorderLayout() );

        if ( _hub == null )
        {
            throw new NullPointerException( "hub was null." );
        }

        if ( _settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }

        if ( _downloadManager == null )
        {
            throw new NullPointerException( "downloadManager was null." );
        }

        // already spinned
        hub = _hub;

        hubListener = new SearchComponentHubListener( this, hub );
        hub.addListener( hubListener );

        settings = _settings;

        // already spinned
        downloadManager = _downloadManager;

        SearchPanel searchPanel = new SearchPanel( hub, this );

        // initalizes first element in tab pane
        addSearch( null );

        SplitPane splitPane = new SplitPane( SplitPane.HORIZONTAL_SPLIT, searchPanel, tabbedPane );

        tabbedPane.setTabLayoutPolicy( JTabbedPane.SCROLL_TAB_LAYOUT );

        splitPane.setDividerLocation( 200 );
        // splitPane.setResizeWeight(0);

        add( splitPane, BorderLayout.CENTER );

    }

    /** ********************************************************************** */

    /**
     * Add the given SearchRequest to the SearchComponent
     * 
     * @param searchRequest SearchRequest instance to be added
     */
    public void addSearch(
        SearchRequest searchRequest )
    {
        logger.debug( "-- adding SearchRequest " + searchRequest );

        // RowTableModel model;

        SortableTable searchResults = updateModel( searchRequest );

        // for the second call the SearchResults are not pushed down, because
        // they are equal with the previous version
        String name;

        if ( searchRequest != null )
        {
            name = searchRequest.getNamePattern();

        }
        else
        {
            name = "default";

        }

        tabbedPane.insertTab( name + " (0 hits)", null, searchResults, null, 0 );

        // sets the index of the selected tab to the first element
        tabbedPane.setSelectedIndex( 0 );

    }

    /**
     * Get the HubListener instance related to this SearchComponent
     * 
     * @return
     */
    public SearchComponentHubListener getHubListener()
    {
        return hubListener;

    }

    /**
     * Get the models related to the listed SearchRequest instance
     * 
     * @return
     */
    public List getModels()
    {
        return models;

    }

    /**
     * Get the column contents for the given SearchResult instance
     * 
     * @param sr SearchResult instance for which the column contents are to be retrieved
     * @return
     */
    public final Object[] getSearchColumns(
        SearchResult sr )
    {
        String name = sr.getFilename();

        return new Object[] { FileUtils.getNameNoExtension( name ), FileUtils.getExtension( name ),
                        sr.getTTH() != null ? sr.getTTH() : "", // tiger tree hash

                        new Long( sr.getFileSize() ), // full file size

                        sr.getNick(), new Long( sr.getPing() ), sr.getFreeSlotCount() + "/" + sr.getMaxSlotCount() };

    }

    /**
     * Get the list of SearchRequests posted in this SearchComponent
     * 
     * @return
     */
    public List getSearchRequests()
    {
        return searchRequests;

    }

    /**
     * Get the tabbed pane used to display the different model visualizations
     * 
     * @return
     */
    public JTabbedPane getTabbedPane()
    {
        return tabbedPane;

    }

    /**
     * Initialize the given SortableTable instance
     * 
     * @param table SortableTable instance to be initialized
     */
    private void initializeSortableTable(
        SortableTable table )
    {
        table.getTable().setDefaultRenderer( Long.class, new ByteCellRenderer() );

        ImageIcon[] buffer = getUserIcons();

        table.getTable().getColumn( "Nick" ).setCellRenderer( new UserCellRenderer( buffer, null ) );

        table.getTable().getColumn( "Ping" ).setCellRenderer( new javax.swing.table.DefaultTableCellRenderer() );

    }

    /**
     * Update the model with the given SearchRequest instance
     * 
     * @param searchRequest
     * @return
     */
    private SortableTable updateModel(
        SearchRequest searchRequest )
    {
        RowTableModel model;

        SortableTable searchResults;

        // use intial model and search results
        // don't add model again
        if ( models.size() == 1 && !initialized )
        {
            model = (RowTableModel) models.get( 0 );

            searchResults = (SortableTable) tabbedPane.getComponentAt( 0 );

            // tabbedPane.setTitleAt(0, name + " (0)");
            initialized = true;

            searchRequests.add( 0, searchRequest );

            // create new entries
        }
        else
        {
            model = new RowTableModel( new String[] { "Name", "Ext", "Hash", "Size", "Nick", "Ping", "Slots" } );

            searchResults =
                new SortableTable( new int[] { 170, 30, -1, 80, 90, 35, 40 }, new SearchResultsController( hub,
                    settings, downloadManager, model ), model, "hits" );

            initializeSortableTable( searchResults );

            // synchronized (this) {
            // don't add SearchRequest for intial element
            if ( models.size() > 0 )
            {
                searchRequests.add( 0, searchRequest );

            }

            models.add( 0, model );

            // }

        }

        return searchResults;

    }

    /**
     * Get the index of the SearchRequest which is related to the given SearchResult
     * 
     * @param searchResult SearchResult instance for which the related SearchRequest is to be retrieved
     * @return
     */
    protected int findSearchRequestIndex(
        SearchResult searchResult )
    {
        int index = -1;

        SearchRequest[] requests = (SearchRequest[]) searchRequests.toArray( new SearchRequest[searchRequests.size()] );

        // check which of the SearchRequests matches the search result
        for ( int i = 0; i < requests.length && index == -1; i++ )
        {
            // multiple SearchRequests match the search result, the last
            // entered will be returned
            if ( requests[i] != null && requests[i].matches( searchResult ) )
            {
                index = i;

            }

        }

        return index;

    }

}

/*******************************************************************************
 * $Log: SearchComponent.java,v $ Revision 1.24 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.23
 * 2005/09/25 16:40:58 timowest updated sources and tests Revision 1.22 2005/09/14 07:11:49 timowest updated sources
 */
