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

//$Id: AdvancedSettings.java,v 1.13 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

/**
 * <CODE>AdvancedSettings</CODE> represents some special application settings
 * 
 * @author Timo Westk�mper
 */
public class AdvancedSettings {

    /**
     * 
     */
    private boolean forceMove = true;

    /**
     * 
     */
    private String hublistAddress;

    /**
     * 
     */
    private String clientVersion;

    /**
     * 
     */
    private int iconSize;

    /**
     * 
     */
    private int xLocation;

    /**
     * 
     */
    private int yLocation;

    /**
     * 
     */
    private int xSize;

    /**
     * 
     */
    private int ySize;

    /**
     * Create an AdvancedSettings instance
     */
    public AdvancedSettings() {
        iconSize = 16;

    }

    /** ********************************************************************** */

    /**
     * Get the client version of javadc3
     * 
     * @return
     */
    public String getClientVersion() {
        return clientVersion;

    }

    /**
     * Get the URL of the hublist from which the hublist view of javadc3 is
     * populated
     * 
     * @return
     */
    public String getHublistAddress() {
        return hublistAddress;

    }

    /**
     * Get the icon size of the icons displayed in the user interface of javadc3
     * (16 pixels is the default size)
     * 
     * @return
     */
    public int getIconSize() {
        return iconSize;

    }

    /**
     * Get the x location of the main frame
     * 
     * @return
     */
    public int getXLocation() {
        return xLocation;

    }

    /**
     * Get the width of the main frame
     * 
     * @return
     */
    public int getXSize() {
        return xSize;

    }

    /**
     * Get the y location of the main frame
     * 
     * @return
     */
    public int getYLocation() {
        return yLocation;

    }

    /**
     * Get the height of the main frame
     * 
     * @return
     */
    public int getYSize() {
        return ySize;

    }

    /**
     * Get whether the Client is automatically forwarded to a secondary hub,
     * when it receives a ForceMove command from a Hub
     * 
     * @return
     */
    public boolean isForceMove() {
        return forceMove;

    }

    /**
     * Set the client version of javadc3
     * 
     * @param string
     */
    public void setClientVersion(String string) {
        clientVersion = string;

    }

    /**
     * Set whether the local Client is automatically forwarded to secondary
     * hubs, when it receives a ForceMove command from a Hub
     * 
     * @param b
     */
    public void setForceMove(boolean b) {
        forceMove = b;

    }

    /**
     * Set the URL from which the hublist view of the user interface is
     * populated
     * 
     * @param string
     */
    public void setHublistAddress(String string) {
        hublistAddress = string;

    }

    /**
     * Set the icon size of the icons displayed in the user interface
     * 
     * @param i
     */
    public void setIconSize(int i) {
        iconSize = i;

    }

    /**
     * Set the x coordination of the main frame
     * 
     * @param i
     */
    public void setXLocation(int i) {
        xLocation = i;

    }

    /**
     * Set the width of the main frame
     * 
     * @param i
     */
    public void setXSize(int i) {
        xSize = i;

    }

    /**
     * Set the y location of the main frame
     * 
     * @param i
     */
    public void setYLocation(int i) {
        yLocation = i;

    }

    /**
     * Set the height of the main frame
     * 
     * @param i
     */
    public void setYSize(int i) {
        ySize = i;

    }

}

/*******************************************************************************
 * $Log: AdvancedSettings.java,v $
 * Revision 1.13  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.12 2005/09/30 15:59:52 timowest
 * updated sources and tests
 * 
 * Revision 1.11 2005/09/14 07:11:48 timowest updated sources
 * 
 * 
 * 
 */
