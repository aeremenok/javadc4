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

import java.util.List;

import net.sf.javadc.net.DownloadRequest;

/**
 * <CODE>IDownloadManager</CODE> represents the abstract interface for the
 * <CODE>DownloadManager</CODE>, which provides functionality to resume
 * <CODE>DownloadRequests</CODE> via the related <CODE>Hubs</CODE> and
 * maintain the download queue.
 * 
 * @author Timo Westk�mper
 */
public interface IDownloadManager extends IObject {

    /**
     * Add the given Download to the queue of DownloadRequests
     * 
     * @param dr
     */
    public abstract void requestDownload(DownloadRequest dr);

    /**
     * Remove the given DownloadRequest from the queue of DownloadRequests
     * 
     * @param dr
     */
    public abstract void removeDownload(DownloadRequest dr);

    /**
     * Flush the Download queue
     */
    public abstract void flushDownloadQueue();

    /**
     * Return the downloadQueue
     * 
     * @return Returns the downloadQueue.
     */
    public abstract List getDownloadQueue();

}
