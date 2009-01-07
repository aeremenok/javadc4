/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.mockups;

import java.util.EventListener;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.client.Connection;
import net.sf.javadc.net.hub.HostInfo;

/**
 * @author Timo Westk�mper To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BaseClientManager
    implements
        IClientManager
{
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#addClient(net.sf.javadc.net.hub.HostInfo,
     *      net.sf.javadc.net.client.Client)
     */
    public void addClient(
        HostInfo info,
        Client c )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#addListener(java.util.EventListener)
     */
    public void addListener(
        EventListener listener )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#createClientConnection(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.listeners.ConnectionListener, boolean)
     */
    public Connection createClientConnection(
        Client client,
        ConnectionListener listener,
        boolean isServer )
    {
        // TODO Auto-generated method stub
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#fireClientAdded(net.sf.javadc.net.client.Client)
     */
    public void fireClientAdded(
        Client c )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#fireClientRemoved(net.sf.javadc.net.client.Client)
     */
    public void fireClientRemoved(
        IClient c )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#getClient(net.sf.javadc.net.hub.HostInfo)
     */
    public Client getClient(
        HostInfo host )
    {
        // TODO Auto-generated method stub
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#removeClient(net.sf.javadc.net.hub.HostInfo)
     */
    public void removeClient(
        HostInfo info )
    {

        // TODO Auto-generated method stub
    }

}