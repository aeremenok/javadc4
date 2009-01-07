/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
 *
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software;      you can redistribute it and/or modify it
 * under the terms of the   GNU General Public License as published by the Free
 * Software Foundation;    either version 2 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;   without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE.   See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: ClientListenerBase.java,v 1.11 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.listeners;

import java.util.List;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.hub.HostInfo;

/**
 * <code>ClientListenerBase</code> is the default implementation of the
 * <code>ClientListener</code> interface. It can be used as the super class
 * for implementations which are only interested in few notifications.
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.11 $ $Date: 2005/10/02 11:42:27 $
 */
public class ClientListenerBase implements ClientListener {

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ClientListener#receivedNick(net.sf.javadc.net.client.Client)
     */
    public void receivedNick(Client client) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ClientListener#downloadRemoved(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest)
     */
    public void downloadRemoved(IClient client, DownloadRequest dr) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ClientListener#browseListDownloaded(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, net.sf.javadc.net.hub.HostInfo)
     */
    public void browseListDownloaded(IClient client, DownloadRequest sr,
            HostInfo host) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ClientListener#downloadAdded(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest)
     */
    public void downloadAdded(Client client, DownloadRequest dr) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ClientListener#connectionRequested(net.sf.javadc.net.client.Client,
     *      boolean, net.sf.javadc.listeners.ConnectionListener)
     */
    public void connectionRequested(Client client, boolean isServer,
            ConnectionListener listener) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ClientListener#disconnected()
     */
    public void disconnected(List downloads) {
        // TODO Auto-generated method stub

    }

}