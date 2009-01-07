/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: HubListener.java,v 1.7 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.listeners;

import java.util.EventListener;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.net.Message;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.hub.HubUser;

/**
 * <code>HubListener</code> is the listener interface for objects interested in notifications from <code>Hub</code>
 * instances
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.7 $ $Date: 2005/10/02 11:42:27 $
 */
public interface HubListener
    extends
        EventListener
{
    /**
     * The browse list for the given HubUser of the given Hub has been decoded
     * 
     * @param hub Hub in which the given HubUser is located
     * @param ui HubUser whose browse list has been decoded
     */
    void browseListDecoded(
        IHub hub,
        HubUser ui );

    /**
     * The given Message has been received via the given Hub
     * 
     * @param hub Hub for which a Message has been received
     * @param message Message which has been received
     */
    void gotMessage(
        IHub hub,
        Message message );

    // void searchResultAdded(IHub hub, SearchResult sr);

    /**
     * The given User Message has been received via the given Hub
     * 
     * @param hub Hub for which a User Message has been received
     * @param message Message which has been received
     */
    void gotUserMessage(
        IHub hub,
        Message message );

    /**
     * The connection to the given Hub has been canceled
     * 
     * @param hub Hub which has been disconnected
     */
    void hubDisconnected(
        IHub hub );

    /**
     * The given SearchResult has been added to the given Hub
     * 
     * @param hub Hub where the given SearchResult has been added
     * @param sr SearchResult which has been added
     */
    void searchResultAdded(
        IHub hub,
        SearchResult sr,
        SearchRequest sre );

    /**
     * The given SearchRequest has been added to the given Hub
     * 
     * @param hub Hub where the given SearchRequest has been added
     * @param sr SearchRequest which has been added
     */
    // void searchRequestAdded(IHub hub, SearchRequest sr);
    /**
     * The list of SearchResults has been cleared
     * 
     * @param hub Hub for which the SearchResults have been cleared
     */
    void searchResultsCleared(
        IHub hub );

    /**
     * The given HubUser has been added to the given Hub
     * 
     * @param hub Hub for which a HubUser has been added
     * @param ui HubUser which has been added
     */
    void userAdded(
        IHub hub,
        HubUser ui );

    /**
     * The given HubUser has been removed from the given Hub
     * 
     * @param hub Hub for which a HubUser has been removed
     * @param ui HubUser which has been removed
     */
    void userRemoved(
        IHub hub,
        HubUser ui );

}