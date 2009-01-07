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

package net.sf.javadc.net.hub;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubInfo;

/**
 * <code>HubInfo</code> represents a serializable version of information about
 * a specific <code>Hub</code> instance.
 * 
 * @author Timo Westk�mper
 */
public class HubInfo implements IHubInfo {

    /**
     * 
     */
    private String name = "";

    /**
     * 
     */
    private String description = "";

    /**
     * 
     */
    private String country = "";

    /**
     * 
     */
    private String status = "";

    /**
     * 
     */
    private HostInfo address;

    /**
     * 
     */
    private int userCount;

    /**
     * 
     */
    private long minshare;

    /**
     * 
     */
    private int minslots;

    /**
     * 
     */
    private int maxhubs;

    /**
     * 
     */
    private int maxusers;

    /**
     * Create a HubInfo instance
     */
    public HubInfo() {

    }

    /**
     * Create a HubInfo instance from the given hub
     * 
     * @param hub
     */
    public HubInfo(IHub hub) {
        if (hub == null) {
            throw new NullPointerException("hub was null.");
        }

        name = hub.getName();
        address = hub.getHost();
        description = hub.getDescription();

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubInfo#getAddress()
     */
    public HostInfo getAddress() {
        return address;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubInfo#getHost()
     */
    public HostInfo getHost() {
        return address;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubInfo#getDescription()
     */
    public String getDescription() {
        return description;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubInfo#getName()
     */
    public String getName() {
        return name;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubInfo#setAddress(net.sf.javadc.net.hub.HostInfo)
     */
    public void setAddress(HostInfo info) {
        address = info;

        if (name == null) {
            name = info.getHost();

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubInfo#setDescription(java.lang.String)
     */
    public void setDescription(String string) {
        description = string;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubInfo#setName(java.lang.String)
     */
    public void setName(String string) {
        name = string;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubInfo#getUserCount()
     */
    public int getUserCount() {
        return userCount;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubInfo#setUserCount(int)
     */
    public void setUserCount(int i) {
        userCount = i;

    }

    /**
     * @return Returns the maxhubs.
     */
    public int getMaxhubs() {
        return maxhubs;
    }

    /**
     * @param maxhubs
     *            The maxhubs to set.
     */
    public void setMaxhubs(int maxhubs) {
        this.maxhubs = maxhubs;
    }

    /**
     * @return Returns the maxusers.
     */
    public int getMaxusers() {
        return maxusers;
    }

    /**
     * @param maxusers
     *            The maxusers to set.
     */
    public void setMaxusers(int maxusers) {
        this.maxusers = maxusers;
    }

    /**
     * @return Returns the minshare.
     */
    public long getMinshare() {
        return minshare;
    }

    /**
     * @param minshare
     *            The minshare to set.
     */
    public void setMinshare(long minshare) {
        this.minshare = minshare;
    }

    /**
     * @return Returns the minslots.
     */
    public int getMinslots() {
        return minslots;
    }

    /**
     * @param minslots
     *            The minslots to set.
     */
    public void setMinslots(int minslots) {
        this.minslots = minslots;
    }

    /**
     * @return Returns the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     *            The country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof IHubInfo) {
            if (((IHubInfo) obj).getHost().equals(getHost()))
                return true;
        }

        return false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getHost().hashCode();

    }

}

/*******************************************************************************
 * $Log: HubInfo.java,v $
 * Revision 1.12  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.11 2005/09/30 15:59:53 timowest updated
 * sources and tests
 * 
 * Revision 1.10 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
