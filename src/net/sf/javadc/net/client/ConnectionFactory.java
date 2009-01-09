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

import junit.framework.Assert;
import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListener;

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
    private final ITaskManager       taskManager;
    private final IConnectionManager clientConnectionManager;
    private final IClientManager     clientManager;
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
        Assert.assertNotNull( _clientTaskFactory );
        Assert.assertNotNull( _taskManager );
        Assert.assertNotNull( _clientManager );
        Assert.assertNotNull( _clientConnectionManager );

        taskManager = _taskManager;
        clientConnectionManager = _clientConnectionManager;
        clientManager = _clientManager;
        clientTaskFactory = _clientTaskFactory;
    }

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
        Assert.assertNotNull( client );

        if ( clientConnectionManager.getConnectionCount() == ConnectionSettings.MAX_CONNECTION_COUNT )
        {
            logger.debug( "No more connections can be created. Maximum of " + ConnectionSettings.MAX_CONNECTION_COUNT +
                " has been reached." );

            return null;
        }

        logger.debug( "Trying to create Connection to Client " + client );

        IConnection conn = null;

        conn =
            new Connection( client, listener, isServer, taskManager, clientConnectionManager, clientManager,
                clientTaskFactory );

        client.setConnection( conn );

        if ( clientConnectionManager.contains( conn ) )
        {
            // ConnectionManager includes a reference to the same
            // Client Connenction
            String debug = "Connection was already available in " + "ConnectionManager.";
            logger.debug( debug );

            return clientConnectionManager.getConnection( conn );
        }

        String debug = "Connection has not yet been added to ConnectionManager";
        logger.debug( debug );
        return conn;
    }
}
