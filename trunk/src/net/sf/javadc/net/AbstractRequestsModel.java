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

package net.sf.javadc.net;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.listeners.RequestsModelListener;
import net.sf.javadc.util.GenericModel;

/**
 * <CODE>AbstractRequestsModel</CODE> is an abstract super class for the
 * <CODE>RequestsModel</CODE>, which provides the main methods use in the
 * Observer / Observable design pattern
 * 
 * @author Timo Westk�mper
 */
public abstract class AbstractRequestsModel extends GenericModel implements
        IRequestsModel {

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireRequestAdded(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void fireRequestAdded(IClient client, DownloadRequest dr, int index) {

        RequestsModelListener[] l = getRequestsModelListeners();

        for (int i = 0; i < l.length; i++) {
            l[i].requestAdded(client, dr, index);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireRequestRemoved(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void fireRequestRemoved(IClient client, DownloadRequest dr, int index) {

        RequestsModelListener[] l = getRequestsModelListeners();

        for (int i = 0; i < l.length; i++) {
            l[i].requestRemoved(client, dr, index);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireRequestChanged(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void fireRequestChanged(IClient client, DownloadRequest dr, int index) {

        RequestsModelListener[] l = getRequestsModelListeners();

        // NOTE : index is not really used

        for (int i = 0; i < l.length; i++) {
            l[i].requestChanged(client, dr, index);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireConnectionAdded(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void fireConnectionAdded(IConnection connection, int index) {

        RequestsModelListener[] l = getRequestsModelListeners();

        for (int i = 0; i < l.length; i++) {
            l[i].connectionAdded(connection, index);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireConnectionChanged(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void fireConnectionChanged(IConnection connection, int index) {

        RequestsModelListener[] l = getRequestsModelListeners();

        for (int i = 0; i < l.length; i++) {
            l[i].connectionChanged(connection, index);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#fireConnectionRemoved(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void fireConnectionRemoved(IConnection connection, int index) {

        RequestsModelListener[] l = getRequestsModelListeners();

        for (int i = 0; i < l.length; i++) {
            l[i].connectionRemoved(connection, index);
        }

    }

    /**
     * @return
     */
    private RequestsModelListener[] getRequestsModelListeners() {

        return (RequestsModelListener[]) listenerList
                .getListeners(RequestsModelListener.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    protected Class getListenerClass() {
        return RequestsModelListener.class;
    }

}

/*******************************************************************************
 * $Log: AbstractRequestsModel.java,v $
 * Revision 1.6  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.5 2005/09/25 16:40:58
 * timowest updated sources and tests
 * 
 * Revision 1.4 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
