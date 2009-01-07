/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: HubListenerBase.java,v 1.8 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.listeners;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.net.Message;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.hub.HubUser;

/**
 * <code>HubListenerBase</code> is the default implementation of the <code>HubListener</code> interface. It can be used
 * as the super class for implementations which are only interested in few notifications.
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.8 $ $Date: 2005/10/02 11:42:27 $
 */
public class HubListenerBase
    implements
        HubListener
{
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#browseListDecoded(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.hub.HubUser)
     */
    public void browseListDecoded(
        IHub hub,
        HubUser ui )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#gotMessage(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.Message)
     */
    public void gotMessage(
        IHub hub,
        Message message )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#gotUserMessage(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.Message)
     */
    public void gotUserMessage(
        IHub hub,
        Message message )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#hubDisconnected(net.sf.javadc.interfaces.IHub)
     */
    public void hubDisconnected(
        IHub hub )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#searchResultAdded(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.SearchResult, net.sf.javadc.net.SearchRequest)
     */
    public void searchResultAdded(
        IHub hub,
        SearchResult sr,
        SearchRequest sre )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#searchResultsCleared(net.sf.javadc.interfaces.IHub)
     */
    public void searchResultsCleared(
        IHub hub )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#userAdded(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.hub.HubUser)
     */
    public void userAdded(
        IHub hub,
        HubUser ui )
    {

    }

    /*
     * public void searchResultAdded(IHub hub, SearchResult sr) {
     *  }
     */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#searchRequestAdded(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.SearchRequest)
     */
    /*
     * public void searchRequestAdded(IHub hub, SearchRequest sr) {
     *  }
     */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.HubListener#userRemoved(net.sf.javadc.interfaces.IHub,
     *      net.sf.javadc.net.hub.HubUser)
     */
    public void userRemoved(
        IHub hub,
        HubUser ui )
    {

    }

}