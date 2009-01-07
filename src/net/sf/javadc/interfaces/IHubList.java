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

/**
 * <CODE>IHubList</CODE> is the abstract interface of <CODE>HubList</CODE>, which provides a list of <CODE>Hubs</CODE>,
 * the application can connect to
 * 
 * @author tw70794
 */
public interface IHubList
    extends
        IObject
{
    /**
     * Add an EventListener instance to the list of EventListeners
     * 
     * @param listener
     */
    public void addListener(
        EventListener listener );

    /**
     * Notify registered listeners that the contents of the HubList have changed
     */
    public void fireHubListChanged();

    /**
     * Return a list of HubInfo instances
     * 
     * @return
     */
    public List getHubInfos();

    /**
     * Set the filter on the description field of the Hubs
     * 
     * @param filter
     */
    public void setFilter(
        String filter );

    /**
     * Update the information of the HubList
     */
    public void update();

}