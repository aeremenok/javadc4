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

package net.sf.javadc.net.client;

import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.listeners.ConnectionManagerListener;
import net.sf.javadc.util.GenericModel;

/**
 * <code>AbstractConnectionManager</code> represents an abstract base
 * implementation for the <code>IConnectionManager</code> interface mainly
 * used to separate the listener notification code from the main implementation
 * 
 * @author Timo Westk�mper
 */
public abstract class AbstractConnectionManager extends GenericModel implements
        IConnectionManager {

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnectionManager#fireClientConnectionAdded(net.sf.javadc.interfaces.IConnection)
     */
    public void fireClientConnectionAdded(IConnection connection) {
        ConnectionManagerListener[] listeners = (ConnectionManagerListener[]) listenerList
                .getListeners(ConnectionManagerListener.class);

        for (int i = 0; i < listeners.length; i++) {
            listeners[i].clientConnectionAdded(connection);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnectionManager#fireClientConnectionStateChanged(net.sf.javadc.interfaces.IConnection)
     */
    public void fireClientConnectionStateChanged(IConnection connection) {
        ConnectionManagerListener[] listeners = (ConnectionManagerListener[]) listenerList
                .getListeners(ConnectionManagerListener.class);

        for (int i = 0; i < listeners.length; i++) {
            listeners[i].clientConnectionStateChanged(connection);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnectionManager#fireClientConnectionRemoved(net.sf.javadc.interfaces.IConnection)
     */
    public void fireClientConnectionRemoved(IConnection connection) {
        ConnectionManagerListener[] listeners = (ConnectionManagerListener[]) listenerList
                .getListeners(ConnectionManagerListener.class);

        for (int i = 0; i < listeners.length; i++) {
            listeners[i].clientConnectionRemoved(connection);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    protected Class getListenerClass() {
        return ConnectionManagerListener.class;

    }

}

/*******************************************************************************
 * $Log: AbstractConnectionManager.java,v $
 * Revision 1.11  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.10 2005/09/12 21:12:02
 * timowest added log block
 * 
 * 
 */
