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
import java.util.List;

import org.picocontainer.Startable;

/**
 * <CODE>IHubFavoritesList</CODE> is the abstract interface for <CODE>HubFavoritesList</CODE>, which represents the list
 * favorite <CODE>Hub</CODE> instances
 * 
 * @author tw70794
 */
public interface IHubFavoritesList
    extends
        Startable,
        IObject
{
    /**
     * Add the given HubInfo to the list of favorite Hubs
     * 
     * @param hubInfo
     */
    public void addHubInfo(
        IHubInfo hubInfo );

    /**
     * Add the given EventListener to the list of EventListeners
     * 
     * @param listener
     */
    public void addListener(
        EventListener listener );

    /**
     * Return the list of favorite Hubs
     * 
     * @return
     */
    public List getHubInfos();

    /**
     * Remove the given HubInfo from the list of favorite Hubs
     * 
     * @param hubInfo
     */
    public void removeHub(
        IHubInfo hubInfo );

    /**
     * Set the list of favorite Hubs
     * 
     * @param list
     */
    public void setHubInfos(
        List list );

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public void start();

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public void stop();

    /**
     * Update the list
     */
    public void update();

}