/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: ClientListener.java,v 1.10 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.listeners;

import java.util.EventListener;
import java.util.List;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.hub.HostInfo;

/**
 * <code>ClientListener</code> is the listener interface for objects interested in notifications from
 * <code>Client</code> instances
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.10 $ $Date: 2005/10/02 11:42:27 $
 */
public interface ClientListener
    extends
        EventListener
{
    /**
     * The file list for the given Client has been downloaded
     * 
     * @param client Client for which the browse list has been downloaded
     * @param dr
     * @param host
     */
    void browseListDownloaded(
        IClient client,
        DownloadRequest dr,
        HostInfo host );

    /**
     * @param client
     * @param isServer
     * @param listener
     */
    void connectionRequested(
        Client client,
        boolean isServer,
        ConnectionListener listener );

    /**
     * Client has disconnected
     * 
     * @param downloads TODO
     */
    void disconnected(
        List downloads );

    /**
     * A DownloadRequest for the given Client has been added
     * 
     * @param client Client for which a download has been added
     * @param dr added DownloadRequest
     */
    void downloadAdded(
        Client client,
        DownloadRequest dr );

    /**
     * The given DownloadRequest has been removed from the given Client
     * 
     * @param client Client in question
     * @param dr removed DownloadRequest
     */
    void downloadRemoved(
        IClient client,
        DownloadRequest dr );

    /**
     * The nick for the given Client has been received
     * 
     * @param client Client whose nick has been received
     */
    void receivedNick(
        Client client );

}