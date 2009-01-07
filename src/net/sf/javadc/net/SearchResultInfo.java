/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.net;

import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.net.hub.HubInfo;

/**
 * <code>SearchResultInfo</code> is used to serialize <code>SearchResult</code> instances
 * 
 * @author Timo Westk�mper
 */
public class SearchResultInfo
{

    /**
     * 
     */
    private String   filename;

    /**
     *  
     */
    private long     fileSize;

    /**
     * 
     */
    private String   nick;

    /**
     * 
     */
    private HostInfo host;

    /**
     * 
     */
    private HubInfo  hub;

    /**
     * localFilename is used as an extra attribute to differentiate the remote filename from the local filename
     */
    private String   localFilename;

    /** hashing information */
    private String   tth;

    /**
     * Create a SearchResultInfo instance
     */
    public SearchResultInfo()
    {

        // ?
    }

    /** ********************************************************************** */

    /**
     * Get the remote filename related to the SearchResult
     * 
     * @return
     */
    public String getFilename()
    {
        return filename;

    }

    /**
     * Get the filesize of the SearchResult
     * 
     * @return
     */
    public long getFileSize()
    {
        return fileSize;

    }

    /**
     * Get the HostInfo of the SearchResult
     * 
     * @return
     */
    public HostInfo getHost()
    {

        return host;

    }

    /**
     * Get the HubInfo related to the Hub of the SearchResult
     * 
     * @return
     */
    public HubInfo getHub()
    {
        return hub;

    }

    /**
     * Get the local filename of the DownloadRequest
     * 
     * @return
     */
    public String getLocalFilename()
    {
        return localFilename;
    }

    /**
     * Get the nick related to the SearchResult
     * 
     * @return
     */
    public String getNick()
    {
        return nick;

    }

    /**
     * @return Returns the tth.
     */
    public String getTTH()
    {
        return tth;
    }

    /**
     * Set the remote filename of the SearchResult
     * 
     * @param string
     */
    public void setFilename(
        String string )
    {
        filename = string;

    }

    /**
     * Set the filesize in bytes of the SearchResult
     * 
     * @param i
     */
    public void setFileSize(
        long i )
    {
        fileSize = i;

    }

    /**
     * Set the HostInfo of the SearchResult
     * 
     * @param info
     */
    public void setHost(
        HostInfo info )
    {
        host = info;

    }

    /**
     * Set the HubInfo of the SearchResult
     * 
     * @param info
     */
    public void setHub(
        HubInfo info )
    {
        hub = info;

    }

    /**
     * Set the local filename of the DownloadRequest
     * 
     * @param localFilename
     */
    public void setLocalFilename(
        String localFilename )
    {
        this.localFilename = localFilename;

    }

    /**
     * Set the nick of the SearchResult
     * 
     * @param string
     */
    public void setNick(
        String string )
    {
        nick = string;

    }

    /**
     * @param tth The tth to set.
     */
    public void setTTH(
        String tth )
    {
        this.tth = tth;
    }
}

/*******************************************************************************
 * $Log: SearchResultInfo.java,v $ Revision 1.12 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.11
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.10 2005/09/12 21:12:02 timowest added log block
 */
