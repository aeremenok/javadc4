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

import java.util.EventListener;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.net.DownloadRequest;

/**
 * <code>RequestsModelListener</code> is the listener interface for objects
 * interested in notifications from <code>RequestsModel</code> instances
 * 
 * @author Timo Westk�mper
 */
public interface RequestsModelListener extends EventListener {

    // Connections

    /**
     * The given Connection with the given index has been removed
     * 
     * @param connection
     *            Connection which has been removed
     * @param index
     *            list location of the removed Connection
     */
    public abstract void connectionRemoved(IConnection connection, int index);

    /**
     * The given Connection with the given index has been added
     * 
     * @param connection
     *            Connection which has been added
     * @param index
     *            list location of the added Connection
     */
    public abstract void connectionAdded(IConnection connection, int index);

    /**
     * The state of the given Connection with the given index has changed
     * 
     * @param connection
     *            Connection which has changed its state
     * @param index
     *            list location of changed Connection
     */
    public abstract void connectionChanged(IConnection connection, int index);

    // Download Requests

    /**
     * The given DownloadRequest of the given Client with the given index has
     * been removed
     * 
     * @param client
     *            Client to which the DownloadRequest is related, if the
     *            Connection is active
     * @param dr
     *            DownloadRequest which has been removed
     * @param index
     *            list location of the removed DownloadRequest
     */
    public abstract void requestRemoved(IClient client, DownloadRequest dr,
            int index);

    /**
     * The given DownloadRequest of the given Client with the given index has
     * been added
     * 
     * @param client
     *            Client to which the DownloadRequest is related, if the
     *            Connection is active
     * @param dr
     *            DownloadRequest which has been added
     * @param index
     *            list location of the added DownloadRequest
     */
    public abstract void requestAdded(IClient client, DownloadRequest dr,
            int index);

    /**
     * The given DownloadRequest of the given Client with the given index has
     * changed
     * 
     * @param client
     *            Client to which the DownloadRequest is related, if the
     *            Connection is active
     * @param downloadRequest
     *            DownloadRequest which has changed its state
     * @param index
     *            list location of the changed DownloadRequest
     */
    public abstract void requestChanged(IClient client,
            DownloadRequest downloadRequest, int index);

}