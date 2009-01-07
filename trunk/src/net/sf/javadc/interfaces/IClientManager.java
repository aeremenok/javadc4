/*
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

package net.sf.javadc.interfaces;

import java.util.EventListener;

import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.client.Connection;
import net.sf.javadc.net.hub.HostInfo;

/**
 * <CODE>IClientManager</CODE> defines the abstract interface for the <CODE>ClientManager</CODE>,
 * which manages the active <CODE>Client</CODE> instances
 * 
 * @author tw70794
 */
public interface IClientManager extends IObject {

    /**
     * Add an EventListener to the list of listeners
     * 
     * @param listener
     *            EventListener to be added
     */
    public void addListener(EventListener listener);

    /**
     * Return the Client with the given HostInfo
     * 
     * @param host
     *            HostInfo of the Client to be searched
     * @return
     */
    public Client getClient(HostInfo host);

    /**
     * Add the given Client mapped to the given HostInfo
     * 
     * @param info
     *            HostInfo used for mapping
     * @param c
     *            Client instance to be added
     */
    public void addClient(HostInfo info, Client c);

    /**
     * Remove the Client mapped to the given HostInfo instance
     * 
     * @param info
     *            HostInfo the Client instance is mapped to
     */
    public void removeClient(HostInfo info);

    /**
     * Create a Client Connection with the given Client and ConnectionListener
     * 
     * @param client
     *            Client this Connection is created for
     * @param listener
     *            ConnectionListener to be used in Connection
     * @param isServer
     *            whether the Connection is active or not
     * @return
     */
    public Connection createClientConnection(Client client,
            ConnectionListener listener, boolean isServer);

    /**
     * Notify registered listeners that the given Client has been added to the
     * list of active Clients
     * 
     * @param c
     *            Client instance which has been added
     */
    public void fireClientAdded(Client c);

    /**
     * Notify registered listeners that the given Client has been removed from
     * the list of active Clients
     * 
     * @param c
     *            Client instance which has been added
     */
    public void fireClientRemoved(IClient c);
}