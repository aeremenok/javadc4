/*
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.listeners;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.net.DownloadRequest;

/**
 * <code>RequestsModelListenerBase</code> is the default implementation of the
 * <code>RequestsModelListener</code> interface. It can be used as the super
 * class for implementations which are only interested in few notifications.
 * 
 * @author Timo Westk�mper
 */
public class RequestsModelListenerBase implements RequestsModelListener {

    // Connections

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#connectionRemoved(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void connectionRemoved(IConnection connection, int index) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#connectionAdded(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void connectionAdded(IConnection connection, int index) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#connectionChanged(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void connectionChanged(IConnection connection, int index) {

    }

    // Download Requests

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#requestChanged(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void requestChanged(IClient client, DownloadRequest downloadRequest,
            int index) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#requestRemoved(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void requestRemoved(IClient client, DownloadRequest dr, int index) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#requestAdded(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void requestAdded(IClient client, DownloadRequest dr, int index) {

    }

}