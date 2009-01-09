/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: ClientManager.java,v 1.19 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.client;

import java.util.HashMap;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ClientListener;
import net.sf.javadc.listeners.ClientListenerBase;
import net.sf.javadc.listeners.ClientManagerListener;
import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.GenericModel;

import org.apache.log4j.Category;

/**
 * <code>ClientManager</code> represents a main networking components, which keeps track of the active
 * <code>Client</code> instances and creates new ones when requested
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.19 $ $Date: 2005/10/02 11:42:28 $
 */
public class ClientManager
    extends GenericModel
    implements
        IClientManager
{

    /** ********************************************************************** */

    private class MyClientListener
        extends ClientListenerBase
    {

        @Override
        public void connectionRequested(
            Client client,
            boolean isServer,
            ConnectionListener listener )
        {

            client.setConnection( createClientConnection( client, listener, isServer ) );

        }

    }

    private static final Category    logger         = Category.getInstance( ClientManager.class );

    /**
     *  
     */
    private final HashMap            clients        = new HashMap();

    // components
    /**
     * 
     */
    private final ISettings          settings;

    /**
     * 
     */
    private final ITaskManager       taskManager;

    /**
     * 
     */
    private final IConnectionManager clientConnectionManager;

    /**
     * 
     */
    private final IClientTaskFactory clientTaskFactory;

    // listeners
    /**
     * 
     */
    private final ClientListener     clientListener = new MyClientListener();

    /**
     * creates a <CODE>ClientManager</CODE> with the given <CODE>ISettings
     * </CODE>,<CODE>ITaskManager</CODE>,
     * <CODE>IClientConnectionManager
     * </CODE> and <CODE>ClientTaskFactory</CODE>
     */
    public ClientManager(
        ISettings _settings,
        ITaskManager _taskManager,
        IConnectionManager _clientConnectionManager,
        IClientTaskFactory _clientTaskFactory )
    {

        if ( _settings == null )
        {
            throw new NullPointerException( "_settings was null." );
        }
        else if ( _taskManager == null )
        {
            throw new NullPointerException( "_taskManager was null." );
        }
        else if ( _clientConnectionManager == null )
        {
            throw new NullPointerException( "_clientConnectionManager was null." );
        }
        else if ( _clientTaskFactory == null )
        {
            throw new NullPointerException( "_clientTaskFactory was null." );
        }

        settings = _settings;
        taskManager = _taskManager;
        clientConnectionManager = _clientConnectionManager;
        clientTaskFactory = _clientTaskFactory;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#addClient(net.sf.javadc.net.hub.HostInfo,
     *      net.sf.javadc.net.client.Client)
     */
    public void addClient(
        HostInfo info,
        Client c )
    {

        synchronized ( clients )
        {
            clients.put( info, c );
            c.addListener( clientListener );
        }

        fireClientAdded( c );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#createClientConnection(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.listeners.ConnectionListener, boolean)
     */
    public final Connection createClientConnection(
        Client client,
        ConnectionListener listener,
        boolean isServer )
    {
        return new Connection( client, listener, isServer, taskManager, clientConnectionManager, this,
            clientTaskFactory );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#fireClientAdded(net.sf.javadc.net.client.Client)
     */
    public final void fireClientAdded(
        Client c )
    {
        ClientManagerListener[] listeners = listenerList.getListeners( ClientManagerListener.class );

        for ( int i = 0; i < listeners.length; i++ )
        {
            listeners[i].clientAdded( c );

        }

    }

    // synchronized addition of new Clients

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#fireClientRemoved(net.sf.javadc.net.client.Client)
     */
    public final void fireClientRemoved(
        IClient c )
    {
        ClientManagerListener[] listeners = listenerList.getListeners( ClientManagerListener.class );

        for ( int i = 0; i < listeners.length; i++ )
        {
            listeners[i].clientRemoved( c );

        }

    }

    // synchronized deletion of old Clients

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#getClient(net.sf.javadc.net.hub.HostInfo)
     */
    public final Client getClient(
        HostInfo host )
    {
        Client c = (Client) clients.get( host );

        // add the client into the map of clients if it is not yet there
        if ( c == null )
        {
            c = new Client( host, settings );
            addClient( host, c );

        }
        else
        {
            logger.debug( "Client has already been added." );

        }

        return c;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientManager#removeClient(net.sf.javadc.net.hub.HostInfo)
     */
    public void removeClient(
        HostInfo info )
    {

        Client cl = null;

        synchronized ( clients )
        {
            cl = (Client) clients.get( info );

            if ( cl != null )
            {
                clients.remove( info );
                cl.removeListener( clientListener );
            }

        }

        if ( cl != null )
        {
            fireClientRemoved( cl );
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.GenericModel#getListenerClass()
     */
    @Override
    protected final Class getListenerClass()
    {
        return ClientManagerListener.class;

    }

}

/*******************************************************************************
 * $Log: ClientManager.java,v $ Revision 1.19 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.18
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.17 2005/09/25 16:40:59 timowest updated sources and
 * tests Revision 1.16 2005/09/12 21:12:02 timowest added log block
 */
