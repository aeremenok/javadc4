/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.gui.model.RowTableModel;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.listeners.HubListenerBase;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;

import org.apache.log4j.Category;

class SearchComponentHubListener
    extends HubListenerBase
{

    private class ResultAndRequest
    {

        public final SearchResult  searchResult;

        public final SearchRequest searchRequest;

        public ResultAndRequest(
            SearchResult _searchResult,
            SearchRequest _searchRequest )
        {

            // if (_searchResult == null)
            // throw new NullPointerException("searchResult was null.");
            //            
            // else if (_searchRequest == null)
            // throw new NullPointerException("searchRequest was null.");

            searchResult = _searchResult;
            searchRequest = _searchRequest;
        }

    }

    // external

    private static final Category  logger         = Category.getInstance( SearchComponentHubListener.class );

    /**
     * 
     */
    private final SearchComponent  searchComponent;

    // internal

    /**
     * 
     */
    private final IHub             hub;

    /**
     * 
     */
    private List<ResultAndRequest> added          = new ArrayList<ResultAndRequest>();

    // private Map results2requests = new HashMap();

    // private boolean updated = false;

    /**
     * 
     */
    private List<SearchRequest>    searchRequests = new ArrayList<SearchRequest>();

    /**
     * 
     */
    private boolean                isAllHubs      = false;

    /**
     * Create a SearchComponentHubListener with the given SearchComponent
     * 
     * @param _searchComponent SearchComponent to be used
     */
    public SearchComponentHubListener(
        SearchComponent _searchComponent,
        IHub _hub )
    {

        if ( _searchComponent == null )
        {
            throw new NullPointerException( "searchComponent was null" );
        }

        if ( _hub == null )
        {
            throw new NullPointerException( "hub was null" );
        }

        searchComponent = _searchComponent;
        hub = _hub;

        isAllHubs = hub.getHost() == null;

        ActionListener taskPerformer = new ActionListener()
        {

            public void actionPerformed(
                ActionEvent evt )
            {
                update();

            }

        };

        new Timer( ConstantSettings.SEARCHRESULTS_UPDATEINTERVAL, taskPerformer ).start();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#searchRequestAdded(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.SearchRequest)
     */
    public void __searchRequestAdded(
        IHub _hub,
        SearchRequest sr )
    {

        // If Hub equality would not be checked then the MultiSearchComponent
        // would receive
        // searchRequestAdded notifications for the same SearchRequest from all
        // connected
        // Hub instances

        // if (hub.equals(_hub)){
        if ( _hub.equals( hub ) )
        {
            // since the proxy uses instance equality for equals, the
            // overwritten
            // method has to be used by reversing the comparison
            searchComponent.addSearch( sr );

        }
        else
        {
            logger.debug( "Omitting SearchRequest " + sr );

        }// else

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#searchResultAdded(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.SearchResult, net.sf.javadc.net.SearchRequest)
     */
    @Override
    public void searchResultAdded(
        IHub _hub,
        SearchResult sr,
        SearchRequest searchRequest )
    {

        if ( sr == null )
        {
            throw new NullPointerException( "searchResult was null." );
        }
        else if ( searchRequest == null )
        {
            throw new NullPointerException( "searchRequest was null." );
        }

        // RowTableModel model = (RowTableModel)models.get(0);
        // model.addRow(sr, getSearchColumns(sr));

        // maps the SearchRequest instance against the SearchResult instance
        // results2requests.put(sr, searchRequest);
        // logger.debug("Mapped " + searchRequest + " against " + sr);

        if ( searchRequest.matches( sr ) )
        {
            addSearchRequest( _hub, searchRequest );

            // synchronized (added) {
            added.add( new ResultAndRequest( sr, searchRequest ) );
            // }

        }
        else
        {
            logger.debug( "SearchResult " + sr + " didn't match SearchRequest " + searchRequest );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#searchResultsCleared(net.sf.javadc.interfaces.IHub)
     */
    @Override
    public void searchResultsCleared(
        IHub _hub )
    {

        // RowTableModel model = (RowTableModel)models.get(0);
        // model.clear();
    }

    /**
     * Add the given SearchRequest from the given Hub
     * 
     * @param _hub
     * @param searchRequest
     */
    private void addSearchRequest(
        IHub _hub,
        SearchRequest searchRequest )
    {
        // if the given Hub equals the set one and if the given SearchRequest
        // addition has not yet been notified to the SearchComponent, notify it

        if ( _hub.equals( hub ) || isAllHubs )
        {

            // is SearchRequest is not yet contained, add it

            // if (!searchRequests.contains(searchRequest)){
            if ( !contained( searchRequest, searchRequests ) )
            {
                searchRequests.add( 0, searchRequest );
                searchComponent.addSearch( searchRequest );

                logger.debug( "Notified searchComponent that SearchRequest instance " + searchRequest + " was added." );

                // SearchRequest was already contained
            }
            else
            {
                logger.debug( "Omitting SearchRequest " + searchRequest );

            }

            // Hubs are not equal or AllHub requests are omitted
        }
        else
        {
            logger.debug( "Omitting SearchRequest " + searchRequest );

        }

    }

    /**
     * Return true if the given SearchRequest is contained in as the first or second item in the given list and return
     * otherwise false
     * 
     * @param sr
     * @param srs
     * @return
     */
    private boolean contained(
        SearchRequest sr,
        List<SearchRequest> srs )
    {
        int i = srs.indexOf( sr );
        return i > -1 && i < 2;
    }

    /**
     * Update the SearchComponent view
     */
    private void update()
    {
        List models = searchComponent.getModels();
        List searchRequests = searchComponent.getSearchRequests();

        // SearchResult[] searchResults;
        ResultAndRequest[] searchResults;

        // copies the SearchResult instances from a List instance to an array

        synchronized ( added )
        {
            searchResults = added.toArray( new ResultAndRequest[added.size()] );

            added.clear();

        }// synchronized

        // iterates over the items of the SearchResult array

        for ( int i = 0; i < searchResults.length; i++ )
        {
            // searches for the SearchRequest index that matches the given
            // SearchResult

            // int j = -1;
            RowTableModel model = null;
            SearchRequest sr = searchResults[i].searchRequest;

            int j = searchRequests.indexOf( sr );

            if ( j == -1 )
            {
                j = 0;
            }

            if ( j < models.size() )
            {
                model = (RowTableModel) models.get( j );
                // searchRequest = (SearchRequest) searchRequests.get(j);

                // finds the related model and adds the SearchResult
                model.addRow( searchResults[i].searchResult, searchComponent
                    .getSearchColumns( searchResults[i].searchResult ) );

                // updates the title of the related tab in the SearchComponent

                searchComponent.getTabbedPane().setTitleAt( j,
                    sr.getNamePattern() + " (" + model.getRowCount() + " hits)" );
            }
            else
            {
                added.add( 0, searchResults[i] );
            }

        }// for

    }// update
}
