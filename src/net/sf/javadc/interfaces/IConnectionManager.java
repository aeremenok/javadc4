/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.interfaces;

import java.util.EventListener;

/**
 * <CODE>IConnectionManager</CODE> is the abstract of the <CODE>ConnectioManager</CODE> which maintains a list of the
 * active Client <CODE>Connections</CODE>
 * 
 * @author Timo Westk�mper
 */
public interface IConnectionManager
    extends
        IObject
{
    /**
     * Add a Client Connection to the list of of active Client Connections
     * 
     * @param connection
     */
    public boolean addConnection(
        IConnection connection );

    /**
     * Add an EventListener to the list of EventListeners
     * 
     * @param listener
     */
    public void addListener(
        EventListener listener );

    /**
     * Test if the given Connection has already been added to the ConnectionManager
     * 
     * @param connection
     * @return
     */
    public boolean contains(
        IConnection connection );

    /**
     * Notify registered listeners, that the given Connection has been added to the list of active Connections
     * 
     * @param connection Connection instance which has been added
     */
    public void fireClientConnectionAdded(
        IConnection connection );

    /**
     * Notify registered listeners, that the given Connection instance has been removed
     * 
     * @param connection Connection instance which has been removed
     */
    public void fireClientConnectionRemoved(
        IConnection connection );

    /**
     * Notify registered listeners, that the given Connection has changed its state
     * 
     * @param connection Connection instance which has changed its state
     */
    public void fireClientConnectionStateChanged(
        IConnection connection );

    /**
     * Return the active Client Connections
     * 
     * @return
     */
    public IConnection[] getClientConnections();

    /**
     * Search the list of Connections for Connection which is equal to the given (means same Client reference)
     * 
     * @param connection IConnection instance for which an equal counterpart is to be searched
     * @return
     */
    public IConnection getConnection(
        IConnection connection );

    /**
     * Return the amount of active connections
     * 
     * @return
     */
    public int getConnectionCount();

}