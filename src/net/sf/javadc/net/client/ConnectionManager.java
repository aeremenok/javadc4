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

// $Id: ConnectionManager.java,v 1.28 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.listeners.ConnectionListenerBase;

import org.apache.log4j.Category;
import org.picocontainer.Startable;

/**
 * <code>ConnectionManager</code> represents a networking component that keeps
 * track of the active <code>Connection</code> instances and creates new ones,
 * when requested. ConnectionManager acts also a notification dispatcher (
 * <code>ConnectionListener</code>) for user interface models.
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.28 $ $Date: 2005/10/02 11:42:28 $
 */
public class ConnectionManager extends AbstractConnectionManager implements
        IConnectionManager, Startable {

    private static final Category logger = Category
            .getInstance(ConnectionManager.class);

    /**
     * 
     */
    private final List connections = new ArrayList();

    /**
     * 
     */
    private final ConnectionListener connectionListener;

    /**
     * 
     */
    private final ConnectionSlotReservation slotReservation;

    // external components

    private ISettings settings;

    /**
     * Create a ConnectionManager with the given ISettings instance
     * 
     * @param _settings
     *            ISettings instance to be used
     */
    public ConnectionManager(ISettings _settings) {

        if (_settings == null)
            throw new NullPointerException("_settings was null.");

        settings = _settings;

        connectionListener = new MyConnectionListener();
        slotReservation = new ConnectionSlotReservation(settings);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnectionManager#getClientConnections()
     */
    public final IConnection[] getClientConnections() {
        return (IConnection[]) connections.toArray(new IConnection[connections
                .size()]);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnectionManager#getConnection(net.sf.javadc.interfaces.IConnection)
     */
    public final IConnection getConnection(IConnection connection) {
        if (connections.contains(connection)) {
            // no synchronized block necessary, because an array copy
            // of the ArrayList is used

            IConnection[] connections = getClientConnections();

            // iterates over the connections and returns the one that equals the
            // Connection instance given as a parameter

            for (int i = 0; i < connections.length; i++) {

                if (connections[i].equals(connection)) {
                    return connections[i];

                }

            }

            String error = "ConnectionManager.getConnection(IConnection connection)"
                    + " should never reach this point.";

            logger.error(error);

        }

        logger.debug("Connection " + connection
                + " could not be found in list of active connections.");
        // return null, if no matching Connection could be found

        return null;

    }

    // synchronized addition of new Connections

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnectionManager#addConnection(net.sf.javadc.interfaces.IConnection)
     */
    public boolean addConnection(IConnection connection) {

        boolean added = false;

        // lock the ConnectionManager to ensure that no Connections are added
        // to the ConnectionManager while this connection is added

        // lock on connections
        synchronized (connections) {

            // if connection has not yet been added
            if (!connections.contains(connection)) {
                logger.debug("Adding Connection " + connection);
                connections.add(connection);
                added = true;
            } else {
                logger.warn("Connection " + connection
                        + " has already beed added.");
            }
        }

        // adding was successfull
        if (added) {
            connection.addListener(connectionListener);
            fireClientConnectionAdded(connection);
        }

        return added;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnectionManager#contains(net.sf.javadc.interfaces.IConnection)
     */
    public boolean contains(IConnection connection) {
        return connections.contains(connection);

    }

    /**
     * Update the slot usage for the given Connection
     * 
     * @param connection
     */
    private void updateSlotUsage(IConnection connection) {
        updateSlotUsage(connection, connection.getState());
    }

    /**
     * @param connection
     * @param state
     */
    private void updateSlotUsage(IConnection connection, ConnectionState state) {
        if (state.equals(ConnectionState.DOWNLOADING)) {
            // uses a download slot for the Connection
            slotReservation.useDownloadSlot(connection);

        } else if (state.equals(ConnectionState.UPLOADING)) {
            // uses an upload slot for the Connection (uncompressed upload)
            slotReservation.useUploadSlot(connection);

        } else if (state.equals(ConnectionState.COMPRESSED_UPLOADING)) {
            // uses an upload slot for the Connection (compressed upload)
            slotReservation.useUploadSlot(connection);

        } else {
            // releases any slots the Connection might use
            slotReservation.removeConnection(connection);

        }
    }

    /**
     * Remove the given Connection from the list of active connections and
     * notify the listeners about it
     * 
     * @param connection
     */
    private void removeConnection(IConnection connection) {
        boolean removed = false;

        // lock ConnectionManager to ensure that no external threads remove
        // Connections during the execution of this method

        // lock on connections
        synchronized (connections) {
            removed = connections.remove(connection);

        }

        if (removed) {
            connection.removeListener(connectionListener);

            logger.info("Connection " + connection
                    + " was removed from the list of active connections.");

            fireClientConnectionRemoved(connection);

            // releases any slots the Connection might use
            updateSlotUsage(connection, ConnectionState.NOT_CONNECTED);

        } else {
            logger.warn("Connection " + connection + " could not be found.");

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnectionManager#getConnectionCount()
     */
    public int getConnectionCount() {
        return connections.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public void start() {

        logger.debug("starting " + this.getClass().getName());

        // System.out.println();
        // System.out.println("starting " + this.getClass().getName());
        // System.out.println("====================================================");

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public void stop() {

        logger.debug("stopping " + this.getClass().getName());

        // System.out.println();
        // System.out.println("stopping " + this.getClass().getName());
        // System.out.println("====================================================");

        logger.info("Disconnecting connections");

        for (Iterator i = connections.iterator(); i.hasNext();) {
            IConnection connection = (IConnection) i.next();

            logger.info("Disconnecting from " + connection);
            connection.disconnect();
        }

        logger.info("Connections disconnected.");

    }

    /** ********************************************************************** */

    private class MyConnectionListener extends ConnectionListenerBase {

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionListener#disconnected(net.sf.javadc.interfaces.IConnection)
         */
        public void disconnected(IConnection connection) {

            removeConnection(connection);

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionListener#stateChanged(net.sf.javadc.interfaces.IConnection)
         */
        public void stateChanged(IConnection connection) {
            fireClientConnectionStateChanged(connection);

            updateSlotUsage(connection);
        }

    }
}

/*******************************************************************************
 * $Log: ConnectionManager.java,v $
 * Revision 1.28  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.27 2005/09/30 15:59:53 timowest
 * updated sources and tests
 * 
 * Revision 1.26 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
