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

import net.sf.javadc.net.hub.HostInfo;

/**
 * <CODE>IHubInfo</CODE> is the interface representation of <CODE>HubInfo
 * </CODE>, which provides a serializable version of general
 * <CODE>Hub</CODE> instance information
 * 
 * @author tw70794
 */
public interface IHubInfo
    extends
        IObject
{
    /**
     * Get the HostInfo address of the HubInfo
     * 
     * @return
     */
    public HostInfo getAddress();

    /**
     * @return
     */
    public String getCountry();

    /**
     * Get the Description
     * 
     * @return
     */
    public String getDescription();

    /**
     * Get the Host of this HubInfo
     * 
     * @return
     */
    public HostInfo getHost();

    /**
     * @return
     */
    public int getMaxhubs();

    /**
     * @return
     */
    public int getMaxusers();

    /**
     * @return
     */
    public long getMinshare();

    /**
     * @return
     */
    public int getMinslots();

    /**
     * Get the name
     * 
     * @return
     */
    public String getName();

    /**
     * @return
     */
    public String getStatus();

    /**
     * Get the user count
     * 
     * @return
     */
    public int getUserCount();

    /**
     * Set the HostInfo address
     * 
     * @param info
     */
    public void setAddress(
        HostInfo info );

    /**
     * @param value
     */
    public void setCountry(
        String value );

    /**
     * Set the description
     * 
     * @param string
     */
    public void setDescription(
        String string );

    /**
     * @param i
     */
    public void setMaxhubs(
        int i );

    /**
     * @param i
     */
    public void setMaxusers(
        int i );

    /**
     * @param i
     */
    public void setMinshare(
        long i );

    /**
     * @param i
     */
    public void setMinslots(
        int i );

    /**
     * Set the name
     * 
     * @param string
     */
    public void setName(
        String string );

    /**
     * @param value
     */
    public void setStatus(
        String value );

    /**
     * Set the user count
     * 
     * @param i
     */
    public void setUserCount(
        int i );

}