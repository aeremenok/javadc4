/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: AllHubs.java,v 1.17 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.hub;

import java.io.IOException;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.listeners.HubListener;
import net.sf.javadc.listeners.HubManagerListener;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.net.Message;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;

/**
 * <code>AllHubs</code> represents an application specific hub abstraction, which includes all the users of all
 * connected hubs. Posting a <code>SearchRequest</code> to this <code>Hub</code> results in a dispatch to all connected
 * <code>Hubs</code>.
 * 
 * @author Timo Westk�mper
 */
public class AllHubs
    extends BaseHub
    implements
        HubListener,
        HubManagerListener
{
    /**
     * 
     */
    private final String      hubDescription = "This is an all-hubs hub";

    // description of the hub
    /**
     * 
     */
    private final String      hubName        = "AllHubs";                // name of the hub

    // components
    /**
     * 
     */
    private final IHubManager hubManager;

    // used to get amount of currently used hubs
    public AllHubs(
        IHubManager _hubManager )
    {
        hubManager = _hubManager;
        hubManager.addListener( this );

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#browseListDecoded(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.hub.HubUser)
     */
    public final void browseListDecoded(
        IHub hub,
        HubUser ui )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#connect()
     */
    @Override
    public final void connect()
    {

        // do nothing;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#disconnect()
     */
    @Override
    public final void disconnect()
    {

        // do nothing.
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        Object obj )
    {
        if ( obj == this )
        {
            return true;

        }
        else if ( obj instanceof IHub )
        {
            IHub hub = (IHub) obj;
            return hub.getHost() == null;

        }
        else
        {
            return false;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getDescription()
     */
    @Override
    final public String getDescription()
    {
        return hubDescription;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getHost()
     */
    @Override
    final public HostInfo getHost()
    {
        return null;

    }

    /**
     * @param nick
     * @throws IOException
     */
    public final void getInfo(
        String nick )
        throws IOException
    {

        /*
         * for (int i= 0; i < getHubCount(); i++) { HubImpl hub= getHub(i);
         * 
         * hub.getInfo(nick); }
         */
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.mockups.BaseHub#getMyNick()
     */
    public final String getMyNick()
    {
        return null; // I don't have a nick.

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getName()
     */
    @Override
    public final String getName()
    {
        return hubName;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getSearchResults()
     */
    @Override
    public final SearchResult[] getSearchResults()
    {
        return null;

        /*
         * ArrayList list = new ArrayList(); int hubCount = getHubCount();
         * 
         * for (int i = 0; i < hubCount; i++)
         * list.addAll(getHub(i).getSearchResults());
         * 
         * SearchResult[] results = new SearchResult[list.size()]; results =
         * (SearchResult[])list.toArray(results);
         * 
         * return results;
         */
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getUser(java.lang.String)
     */
    @Override
    public final HubUser getUser(
        String nick )
    {
        HubUser user = null;
        int hubCount = hubManager.getHubCount();

        for ( int i = 0; i < hubCount && user != null; i++ )
        {
            IHub hub = hubManager.getHub( i );

            user = hub.getUser( nick );

        }

        return user;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getUserCount()
     */
    @Override
    public final int getUserCount()
    {
        final int hubCount = hubManager.getHubCount();
        int count = 0;

        for ( int i = 0; i < hubCount; i++ )
        {
            IHub hub = hubManager.getHub( i );

            count += hub.getUserCount();

        }

        return count;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#gotMessage(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.Message)
     */
    public final void gotMessage(
        IHub hub,
        Message message )
    {
        fireGotMessage( message );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#gotUserMessage(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.Message)
     */
    public final void gotUserMessage(
        IHub hub,
        Message message )
    {
        fireGotUserMessage( message );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubManagerListener#hubAdded(net.sf.javadc.interfaces.IHub)
     */
    public final void hubAdded(
        IHub hub )
    {
        addListenersTo( hub );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#hubDisconnected(net.sf.javadc.interfaces.IHub)
     */
    public final void hubDisconnected(
        IHub hub )
    {
        removeListenersFrom( hub );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubManagerListener#hubRemoved(net.sf.javadc.interfaces.IHub)
     */
    public final void hubRemoved(
        IHub hub )
    {
        removeListenersFrom( hub );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#isConnected()
     */
    @Override
    public final boolean isConnected()
    {
        return true; // this hub is always connected;

    }

    /**
     * @return
     */
    public final boolean logout()
    {
        return true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#reconnect()
     */
    @Override
    public final void reconnect()
        throws IOException
    {

        // do nothing.
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#isLoggedIn()
     */

    // not used anymore
    /*
     * public final boolean isLoggedIn() { return true;
     *  }
     */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#requestConnection(java.lang.String)
     */
    @Override
    public final void requestConnection(
        String user )
        throws IOException
    {

        // should not be used, because the semantics don't make sense

        /*
         * final int count = getHubCount(); // request a connection to all
         * registered hubs for (int i = 0; i < count; i++) { IHub hub =
         * getHub(i); hub.requestConnection(user); }
         */
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#search(net.sf.javadc.net.SearchRequest)
     */
    @Override
    public final void search(
        SearchRequest sr )
        throws IOException
    {
        final int count = hubManager.getHubCount();

        // searches all connected hubs
        for ( int i = 0; i < count; i++ )
        {
            IHub hub = hubManager.getHub( i );

            hub.search( sr );

        }

        // add new tab if new search was executed in main view
        // fireSearchRequestAdded(sr);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.interfaces.HubListener#searchResultAdded(net.sf.javadc.net.interfaces.IHub,
     *      net.sf.javadc.net.SearchResult)
     */
    public final void searchResultAdded(
        IHub hub,
        SearchResult sr,
        SearchRequest ser )
    {

        // dispatched directly via connected Hubs
        // fireSearchResultAdded(sr);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#searchResultsCleared(net.sf.javadc.interfaces.IHub)
     */
    public final void searchResultsCleared(
        IHub hub )
    {

        // dispatched directly via connected Hubs
        // fireSearchResultsCleared();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendChatMessage(java.lang.String)
     */
    @Override
    public final void sendChatMessage(
        String message )
        throws IOException
    {

        // should not be used, because the semantics don't make sense

        /*
         * // this will spam all hubs! final int count = getHubCount(); // sends
         * the chat message to all connected hubs for (int i = 0; i < count;
         * i++) (getHub(i)).sendChatMessage(message);
         */
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#searchRequestAdded(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.SearchRequest)
     */
    /*
     * public final void searchRequestAdded(IHub hub, SearchRequest sr) {
     *  // no new tab for ordinary searches //fireSearchRequestAdded(sr); }
     */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendPrivateMessage(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public final void sendPrivateMessage(
        String message,
        String nick )
        throws IOException
    {

        // should not be used, because the semantics don't make sense

        /*
         * // this will spam duplicate nicks! final int count = getHubCount();
         * for (int i = 0; i < count; i++) { IHub hub = getHub(i);
         * hub.sendPrivateMessage(message, nick); }
         */
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendSearchResult(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public final void sendSearchResult(
        String message,
        String nick )
    {

        // ignore this request.
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setDescription(java.lang.String)
     */
    @Override
    public final void setDescription(
        String description )
    {

        // this.hubDescription = description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setName(java.lang.String)
     */
    @Override
    public final void setName(
        String name )
    {

        // this.hubName = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setUserCount(int)
     */
    @Override
    public final void setUserCount(
        int userCount )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#userAdded(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.hub.HubUser)
     */
    public final void userAdded(
        IHub hub,
        HubUser ui )
    {

        // this is already provided by the underlying hubs
        // fireUserAdded(ui);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#userRemoved(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.hub.HubUser)
     */
    public final void userRemoved(
        IHub hub,
        HubUser ui )
    {

        // this is already provided by the underlying hubs
        // fireUserRemoved(ui);

    }

    /**
     * @param hub
     */
    private void addListenersTo(
        IHub hub )
    {
        hub.addListener( this );

        final HubListener[] listeners = listenerList.getListeners( HubListener.class );

        for ( int i = 0; i < listeners.length; i++ )
        {
            hub.addListener( listeners[i] );

        }
    }

    /**
     * @param hub
     */
    private void removeListenersFrom(
        IHub hub )
    {
        hub.removeListener( this );

        final HubListener[] listeners = listenerList.getListeners( HubListener.class );

        for ( int i = 0; i < listeners.length; i++ )
        {
            hub.removeListener( listeners[i] );

        }

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    protected Class getListenerClass()
    {
        // TODO Auto-generated method stub
        return HubListener.class;

    }

}

/*******************************************************************************
 * $Log: AllHubs.java,v $ Revision 1.17 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.16 2005/09/30
 * 15:59:53 timowest updated sources and tests Revision 1.15 2005/09/12 21:12:02 timowest added log block
 */
