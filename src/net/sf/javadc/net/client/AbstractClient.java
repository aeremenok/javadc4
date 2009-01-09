/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.net.client;

import java.util.List;

import junit.framework.Assert;
import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.listeners.ClientListener;
import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.GenericModel;

/**
 * <code>AbstractClient</code> represents an abstract base implementation mainly used to separate the listener
 * notification code from the main implementation
 * 
 * @author Timo Westk�mper
 */
public abstract class AbstractClient
    extends GenericModel<ClientListener>
{
    private HostInfo host;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        Object obj )
    {
        Client client = (Client) this;

        if ( obj == this )
        {
            return true;
        }
        else if ( obj instanceof Client )
        {
            Client c = (Client) obj;
            return client.getHost().equals( c.getHost() );
        }
        else
        { // obj is not a Client instance
            return false;
        }
    }

    /**
     * Notify registered listeners, that the browse list for the Client has been downloaded
     * 
     * @param dr
     */
    public void fireBrowseListDownloaded(
        DownloadRequest dr )
    {
        final ClientListener[] listeners = listenerList.getListeners( ClientListener.class );
        for ( ClientListener listener : listeners )
        {
            listener.browseListDownloaded( (IClient) this, dr, host );
        }
    }

    /**
     * @param isServer
     * @param listener
     */
    public void fireConnectionRequested(
        boolean isServer,
        ConnectionListener listener )
    {
        ClientListener[] listeners = listenerList.getListeners( ClientListener.class );
        for ( ClientListener listener2 : listeners )
        {
            listener2.connectionRequested( (Client) this, isServer, listener );
        }
    }

    /**
     * @param downloads TODO
     */
    public void fireDisconnected(
        List<DownloadRequest> downloads )
    {
        final ClientListener[] listeners = listenerList.getListeners( ClientListener.class );
        for ( ClientListener listener : listeners )
        {
            listener.disconnected( downloads );
        }
    }

    /**
     * Notify registered listeners, that a new DownloadRequest has been added
     * 
     * @param dr DownloadRequest which has been added
     */
    public void fireDownloadAdded(
        DownloadRequest dr )
    {
        final ClientListener[] listeners = listenerList.getListeners( ClientListener.class );
        for ( ClientListener listener : listeners )
        {
            listener.downloadAdded( (Client) this, dr );
        }
    }

    /**
     * Notify registered listeners, that a DownloadRequest has been removed
     * 
     * @param dr DownloadRequest which has been removed
     */
    public void fireDownloadRemoved(
        DownloadRequest dr )
    {
        final ClientListener[] listeners = listenerList.getListeners( ClientListener.class );
        for ( ClientListener listener : listeners )
        {
            listener.downloadRemoved( (IClient) this, dr );
        }
    }

    /**
     * Notify reigstered listeners, that the nick for this Client has been received
     */
    public void fireReceivedNick()
    {
        ClientListener[] listeners = listenerList.getListeners( ClientListener.class );
        for ( ClientListener listener : listeners )
        {
            listener.receivedNick( (Client) this );
        }
    }

    /**
     * Get the HostInfo of this Client
     * 
     * @return
     */
    public final HostInfo getHost()
    {
        return host;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return getHost().hashCode();
    }

    /**
     * Set the HostInfo of the Client
     * 
     * @param updatedHost
     */
    public final void setHost(
        HostInfo updatedHost )
    {
        Assert.assertNotNull( updatedHost );
        host = updatedHost;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    protected final Class<ClientListener> getListenerClass()
    {
        return ClientListener.class;
    }
}
