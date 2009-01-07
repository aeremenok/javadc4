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

import net.sf.javadc.net.hub.HostInfo;

/**
 * <CODE>IHubFactory</CODE> represents the abstract interface for <CODE>HubFactory</CODE>,
 * a Factory Method implementation to create preconfigured <CODE>Hub</CODE>
 * instances
 * 
 * @author tw70794
 */
public interface IHubFactory extends IObject {

    /**
     * Create a Hub instance based on the given HostInfo
     * 
     * @param hostInfo
     * @return
     */
    public IHub createHub(HostInfo hostInfo);

    /**
     * Create a Hub instance based on the given HubInfo
     * 
     * @param hubInfo
     * @return
     */
    public IHub createHub(IHubInfo hubInfo);

}