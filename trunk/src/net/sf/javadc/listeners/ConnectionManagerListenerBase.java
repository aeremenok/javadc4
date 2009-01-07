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

import net.sf.javadc.interfaces.IConnection;

/**
 * <code>ConnectionManagerListenerBase</code> is the default implementation of
 * the <code>ConnectionManagerListener</code> interface. It can be used as the
 * super class for implementations which are only interested in few
 * notifications.
 * 
 * @author Timo Westk�mper
 */
public class ConnectionManagerListenerBase implements ConnectionManagerListener {

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ConnectionManagerListener#clientConnectionAdded(net.sf.javadc.interfaces.IConnection)
     */
    public void clientConnectionAdded(IConnection connection) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ConnectionManagerListener#clientConnectionRemoved(net.sf.javadc.interfaces.IConnection)
     */
    public void clientConnectionRemoved(IConnection connection) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ConnectionManagerListener#clientConnectionStateChanged(net.sf.javadc.interfaces.IConnection)
     */
    public void clientConnectionStateChanged(IConnection connection) {
        // TODO Auto-generated method stub

    }

}