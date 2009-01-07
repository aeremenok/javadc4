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

package net.sf.javadc.mockups;

import java.util.List;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.listeners.RequestsModelListener;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.util.GenericModel;

/**
 * @author Timo Westk�mper
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BaseRequestsModel extends GenericModel implements IRequestsModel {

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#getActiveConnections()
     */
    public List getActiveConnections() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * public List getActiveDownloads() { // TODO Auto-generated method stub
     * return null; }
     */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#getAllDownloads()
     */
    public List getAllDownloads() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#removeDownloadRequest(net.sf.javadc.net.DownloadRequest)
     */
    public void removeDownloadRequest(DownloadRequest dr) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    protected Class getListenerClass() {
        return RequestsModelListener.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireRequestAdded(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void fireRequestAdded(IClient client, DownloadRequest dr, int index) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireRequestRemoved(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void fireRequestRemoved(IClient client, DownloadRequest dr, int index) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireRequestChanged(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void fireRequestChanged(IClient client, DownloadRequest dr, int index) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireConnectionAdded(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void fireConnectionAdded(IConnection connection, int index) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireConnectionChanged(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void fireConnectionChanged(IConnection connection, int index) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireConnectionRemoved(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void fireConnectionRemoved(IConnection connection, int index) {
        // TODO Auto-generated method stub

    }

}