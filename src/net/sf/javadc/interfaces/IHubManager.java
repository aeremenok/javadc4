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

package net.sf.javadc.interfaces;

import java.util.EventListener;

/**
 * <CODE>IHubManager</CODE> represents the abstract interface of <CODE>HubManager</CODE>,
 * which manages the list of <CODE>Hub</CODE> instances, the local Client is
 * currently connected to
 * 
 * @author Timo Westk�mper
 */
public interface IHubManager extends IObject {

    /**
     * Get the Hub instance with the given index
     * 
     * @param index
     * @return
     */
    public IHub getHub(int index);

    /**
     * Get the amount of active Hubs
     * 
     * @return
     */
    public int getHubCount();

    /**
     * Add an EventListener to the list of EventListeners
     * 
     * @param listener
     */
    public void addListener(EventListener listener);

    /**
     * Remove an EventListener from the list of EventListeners
     * 
     * @param listener
     */
    public void removeListener(EventListener listener);

    /**
     * Add a Hub instance to the list of active Hub (connections)
     * 
     * @param hub
     */
    public void addHub(IHub hub);

    /**
     * Remove the give Hub instance from the list of active Hub (connections)
     * 
     * @param hub
     */
    public void removeHub(IHub hub);

    /**
     * Get the Hub with the given IP address
     * 
     * @param ip
     * @return
     */
    public IHub getHubWithIP(String ip);

    /**
     * Return a Hub from the list of Hub instances that is equal to the given
     * Hub instance
     * 
     * @param hub
     * @return
     */
    public IHub getHub(IHub hub);

    /**
     * Notify registered listeners that the given Hub has been added
     * 
     * @param hub
     *            Hub instance which has been added
     */
    public void fireHubAdded(IHub hub);

    /**
     * Notify registered listeners, that the given Hub has been removed
     * 
     * @param hub
     *            Hub instance which has been removed
     */
    public void fireHubRemoved(IHub hub);

}