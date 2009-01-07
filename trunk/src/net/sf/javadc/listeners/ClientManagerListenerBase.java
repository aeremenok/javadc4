/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.listeners;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.net.client.Client;

/**
 * <code>ClientManagerListenerBase</code> is the default implementation of the <code>ClientManagerListener</code>
 * interface. It can be used as the super class for implementations which are only interested in few notifications.
 * 
 * @author Timo Westk�mper
 */
public class ClientManagerListenerBase
    implements
        ClientManagerListener
{
    /*
     * (non-Javadoc) 
     * 
     * @see net.sf.javadc.listeners.ClientManagerListener#clientAdded(net.sf.javadc.net.client.Client)
     */
    public void clientAdded(
        Client client )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ClientManagerListener#clientRemoved(net.sf.javadc.net.client.Client)
     */
    public void clientRemoved(
        IClient client )
    {

        // TODO Auto-generated method stub
    }

}