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

package net.sf.javadc.net.client;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.listeners.ClientListener;
import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.InvalidArgumentException;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.GenericModel;

import java.util.List;

/**
 * <code>AbstractClient</code> represents an abstract base implementation
 * mainly used to separate the listener notification code from the main
 * implementation
 * 
 * @author Timo Westk�mper
 */
public abstract class AbstractClient extends GenericModel {

    /**
     * 
     */
    private HostInfo host;

    /**
     * @param downloads
     *            TODO
     * 
     */
    public void fireDisconnected(List downloads) {

        final ClientListener[] listeners = (ClientListener[]) listenerList
                .getListeners(ClientListener.class);

        for (int i = 0; i < listeners.length; i++) {
            listeners[i].disconnected(downloads);

        }

    }

    /**
     * Notify registered listeners, that the browse list for the Client has been
     * downloaded
     * 
     * @param dr
     */
    public void fireBrowseListDownloaded(DownloadRequest dr) {
        final ClientListener[] listeners = (ClientListener[]) listenerList
                .getListeners(ClientListener.class);

        for (int i = 0; i < listeners.length; i++) {
            listeners[i].browseListDownloaded((IClient) this, dr, host);

        }

    }

    /**
     * Notify registered listeners, that a new DownloadRequest has been added
     * 
     * @param dr
     *            DownloadRequest which has been added
     */
    public void fireDownloadAdded(DownloadRequest dr) {
        final ClientListener[] listeners = (ClientListener[]) listenerList
                .getListeners(ClientListener.class);

        for (int i = 0; i < listeners.length; i++) {
            listeners[i].downloadAdded((Client) this, dr);

        }

    }

    /**
     * Notify registered listeners, that a DownloadRequest has been removed
     * 
     * @param dr
     *            DownloadRequest which has been removed
     */
    public void fireDownloadRemoved(DownloadRequest dr) {
        final ClientListener[] listeners = (ClientListener[]) listenerList
                .getListeners(ClientListener.class);

        for (int i = 0; i < listeners.length; i++) {
            listeners[i].downloadRemoved((IClient) this, dr);

        }

    }

    /**
     * Notify reigstered listeners, that the nick for this Client has been
     * received
     */
    public void fireReceivedNick() {
        ClientListener[] listeners = (ClientListener[]) listenerList
                .getListeners(ClientListener.class);

        for (int i = 0; i < listeners.length; i++) {
            listeners[i].receivedNick((Client) this);

        }

    }

    /**
     * @param isServer
     * @param listener
     */
    public void fireConnectionRequested(boolean isServer,
            ConnectionListener listener) {
        ClientListener[] listeners = (ClientListener[]) listenerList
                .getListeners(ClientListener.class);

        for (int i = 0; i < listeners.length; i++) {
            listeners[i].connectionRequested((Client) this, isServer, listener);

        }

    }

    /**
     * Get the HostInfo of this Client
     * 
     * @return
     */
    public final HostInfo getHost() {
        return host;

    }

    /**
     * Set the HostInfo of the Client
     * 
     * @param updatedHost
     */
    public final void setHost(HostInfo updatedHost) {
        if (updatedHost == null)
            throw new InvalidArgumentException("updatedHost was null.");

        host = updatedHost;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        Client client = (Client) this;

        if (obj == this) {
            return true;

        } else if (obj instanceof Client) {
            Client c = (Client) obj;
            return client.getHost().equals(c.getHost());

        } else { // obj is not a Client instance
            return false;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getHost().hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    protected final Class getListenerClass() {
        return ClientListener.class;

    }

}

/*******************************************************************************
 * $Log: AbstractClient.java,v $
 * Revision 1.19  2006/05/30 14:20:37  pmoukhataev
 * Windows installer created
 *
 * Revision 1.18  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.17 2005/09/30 15:59:53 timowest
 * updated sources and tests
 * 
 * Revision 1.16 2005/09/25 16:40:59 timowest updated sources and tests
 * 
 * Revision 1.15 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
