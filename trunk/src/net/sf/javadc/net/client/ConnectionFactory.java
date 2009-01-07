/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.net.client;

import java.io.IOException;

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.net.InvalidArgumentException;

import org.apache.log4j.Category;

/**
 * <code>ConnectionFactory</code> represents a factory method implementation for the creation of <code>Connection</code>
 * instances.
 * 
 * @author Timo Westk�mper
 */
public class ConnectionFactory
    implements
        IConnectionFactory
{
    private static final Category    logger = Category.getInstance( ConnectionFactory.class );

    // components
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
    private final IClientManager     clientManager;

    /**
     * 
     */
    private final IClientTaskFactory clientTaskFactory;

    /**
     * Create a ConnectionFactory
     * 
     * @param _taskManager ITaskManager to be used
     * @param _clientConnectionManager IConnectionManager to be used
     * @param _clientManager IClientManager to be used
     * @param _clientTaskFactory IClientTaskFactory to be used
     */
    public ConnectionFactory(
        ITaskManager _taskManager,
        IConnectionManager _clientConnectionManager,
        IClientManager _clientManager,
        IClientTaskFactory _clientTaskFactory )
    {

        if ( _taskManager == null )
        {
            throw new NullPointerException( "_taskManager was null." );
        }
        else if ( _clientConnectionManager == null )
        {
            throw new NullPointerException( "_clientConnectionManager was null." );
        }
        else if ( _clientManager == null )
        {
            throw new NullPointerException( "_clientManager was null." );
        }
        else if ( _clientTaskFactory == null )
        {
            throw new NullPointerException( "_clientTaskFactory was null." );
        }

        taskManager = _taskManager;
        clientConnectionManager = _clientConnectionManager;
        clientManager = _clientManager;
        clientTaskFactory = _clientTaskFactory;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnectionFactory#createClientConnection(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.listeners.ConnectionListener, boolean)
     */
    public final IConnection createClientConnection(
        Client client,
        ConnectionListener listener,
        boolean isServer )
    {

        if ( client == null )
        {
            throw new InvalidArgumentException( "Client was null." );

        }

        if ( clientConnectionManager.getConnectionCount() == ConnectionSettings.MAX_CONNECTION_COUNT )
        {
            logger.debug( "No more connections can be created. Maximum of " + ConnectionSettings.MAX_CONNECTION_COUNT +
                " has been reached." );

            return null;
        }

        logger.debug( "Trying to create Connection to Client " + client );

        IConnection conn = null;

        try
        {
            conn =
                new Connection( client, listener, isServer, taskManager, clientConnectionManager, clientManager,
                    clientTaskFactory );

            client.setConnection( conn );

        }
        catch ( IOException e )
        {
            logger.error( "Error when trying to create Client Connection." );
            logger.error( e );

        }

        if ( clientConnectionManager.contains( conn ) )
        {
            // ConnectionManager includes a reference to the same
            // Client Connenction

            String debug = "Connection was already available in " + "ConnectionManager.";
            logger.debug( debug );

            return clientConnectionManager.getConnection( conn );

        }
        else
        {
            // ConnectionManager doesn't include a reference to a Client
            // Connection

            String debug = "Connection has not yet been added to ConnectionManager";
            logger.debug( debug );

            return conn;

        }

    }

}

/*******************************************************************************
 * $Log: ConnectionFactory.java,v $ Revision 1.17 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.16
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.15 2005/09/12 21:12:02 timowest added log block
 */
