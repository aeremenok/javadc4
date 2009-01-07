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

import java.util.List;

import net.sf.javadc.net.DownloadRequest;

/**
 * <CODE>IRequestsModel</CODE> is the abstract interface of <CODE>
 * RequestsModel</CODE>, which provides a model interface on
 * top of the <CODE>
 * ConnectionManager</CODE> and <CODE>ClientManager</CODE>
 * 
 * @author Timo Westk�mper
 */
public interface IRequestsModel
    extends
        IGenericModel
{
    /**
     * Notify registered listeners that the given Connection instance has been added
     * 
     * @param connection Connection instance which has been added
     * @param index
     */
    public void fireConnectionAdded(
        IConnection connection,
        int index );

    /**
     * Notify registered listeners that the given Connection instance has changed
     * 
     * @param connection Connection instance which has changed
     * @param index
     */
    public void fireConnectionChanged(
        IConnection connection,
        int index );

    /**
     * Notify registered listeners that the given Connection instance has been removed
     * 
     * @param connection Connection instance which has been removed
     * @param index
     */
    public void fireConnectionRemoved(
        IConnection connection,
        int index );

    /**
     * Notify registered listeners that the given DownloadRequest has been added
     * 
     * @param client Client instance where the DownloadRequest has been added
     * @param dr DownloadRequest instance which has been added
     * @param index
     */
    public void fireRequestAdded(
        IClient client,
        DownloadRequest dr,
        int index );

    /**
     * Notify registered listeners that the given DownloadRequest has changed it's state
     * 
     * @param client Client instance this DownloadRequest belongs to
     * @param dr DownloadRequest instance which has been changed
     * @param index
     */
    public void fireRequestChanged(
        IClient client,
        DownloadRequest dr,
        int index );

    /**
     * Notify registered listeners that the given DownloadRequest has been removed
     * 
     * @param client Client instance where the DownloadRequest has been removed
     * @param dr DownloadRequest instance which has been removed
     * @param index
     */
    public void fireRequestRemoved(
        IClient client,
        DownloadRequest dr,
        int index );

    /**
     * Return the list of active Client Connections
     * 
     * @return
     */
    public abstract List getActiveConnections();

    /**
     * Return the list of all queued DownloadRequests
     * 
     * @return
     */
    public abstract List getAllDownloads();

    /**
     * Remove the given DownloadRequest from the queue
     * 
     * @param dr
     */
    public abstract void removeDownloadRequest(
        DownloadRequest dr );

}