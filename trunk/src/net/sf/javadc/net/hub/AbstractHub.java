/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.net.hub;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.listeners.HubListener;
import net.sf.javadc.net.Message;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.util.GenericModel;

/**
 * <code>AbstractHub</code> represents an abstract base implementation for the <code>IHub</code> interface mainly used
 * to separate the listener notification code from the main implementation
 * 
 * @author Timo Westk�mper
 */
public abstract class AbstractHub
    extends GenericModel
    implements
        IHub
{
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#fireBrowseListDecoded(net.sf.javadc.net.hub.HubUser)
     */
    public final void fireBrowseListDecoded(
        HubUser ui )
    {
        final HubListener[] l = listenerList.getListeners( HubListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].browseListDecoded( this, ui );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#fireGotMessage(net.sf.javadc.net.Message)
     */
    public final void fireGotMessage(
        Message msg )
    {
        final HubListener[] l = listenerList.getListeners( HubListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].gotMessage( this, msg );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#fireGotUserMessage(net.sf.javadc.net.Message)
     */
    public final void fireGotUserMessage(
        Message msg )
    {
        final HubListener[] l = listenerList.getListeners( HubListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].gotUserMessage( this, msg );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#fireHubDisconnected()
     */
    public final void fireHubDisconnected()
    {
        final HubListener[] l = listenerList.getListeners( HubListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].hubDisconnected( this );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#fireSearchRequestAdded(net.sf.javadc.net.SearchRequest)
     */
    /*
     * public final void fireSearchRequestAdded(SearchRequest sr) { final
     * HubListener[] l = (HubListener[]) listenerList
     * .getListeners(HubListener.class);
     * 
     * for (int i = 0; i < l.length; i++) { l[i].searchRequestAdded(this, sr);
     *  }
     *  }
     */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#fireSearchResultAdded(net.sf.javadc.net.SearchResult)
     */
    public final void fireSearchResultAdded(
        SearchResult sr,
        SearchRequest searchRequest )
    {
        final HubListener[] l = listenerList.getListeners( HubListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].searchResultAdded( this, sr, searchRequest );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#fireSearchResultsCleared()
     */
    public final void fireSearchResultsCleared()
    {
        final HubListener[] l = listenerList.getListeners( HubListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].searchResultsCleared( this );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#fireUserAdded(net.sf.javadc.net.hub.HubUser)
     */
    public final void fireUserAdded(
        HubUser ui )
    {
        final HubListener[] l = listenerList.getListeners( HubListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].userAdded( this, ui );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#fireUserRemoved(net.sf.javadc.net.hub.HubUser)
     */
    public final void fireUserRemoved(
        HubUser ui )
    {
        final HubListener[] l = listenerList.getListeners( HubListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].userRemoved( this, ui );

        }

    }

}

/*******************************************************************************
 * $Log: AbstractHub.java,v $ Revision 1.10 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.9
 * 2005/09/12 21:12:02 timowest added log block
 */
