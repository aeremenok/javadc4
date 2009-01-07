/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.tasks.hub;

import java.io.IOException;

import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.net.hub.Hub;
import net.sf.javadc.tasks.BaseHubTask;

import org.apache.log4j.Category;

/**
 * <CODE>IConnectToMeTask</CODE> is called when someone requests to be called by you
 * <p>
 * This command starts the process for a client-client connection initiated by an active mode client. On receiving the
 * client responds by connecting to the IP and port number specified, and from there the client-client protocol takes
 * over.
 * </p>
 * <p>
 * $ConnectToMe &lt;remoteNick&gt; &lt;senderIp&gt;:&lt;senderPort&gt;
 * </p>
 * <p>
 * For example: <br/>
 * $ConnectToMe ThatGuy 83.43.232.34:1412
 * </p>
 * <p>
 * Client1 is active and wants to connect to Client2. Client1 sends through the hub, and starts to listen for incomming
 * connection at address Client1ip:Client1port (TCP).
 * </p>
 * <p>
 * Client2 has no way of knowing which client wants to connect, he only knows ip:port of Client1.
 * </p>
 * <p>
 * Extension: <br/>
 * That Client2 has no way of knowing which client wants to connect can be changed if the hub changes <remoteNick>into
 * the nick of the users who wants to initiate the connection. This extension would break old hubs.
 * </p>
 * <p>
 * Why would this break old hubs? The 'feature' would only be available on new hubs, or hubs which have scripting
 * support to implement this feature. It really shouldn't even break old clients unless for some odd reason it needs the
 * <remoteNick>to be their own. The only thing I can see that is a bit tricky is for the new clients to detect if the
 * hub supports this extention ~Moch
 * </p>
 * <p>
 * This would be a good opportunity to introduce the connection-cookies supported by ADC which allow clients to avoid
 * guessing from which hub a userconnection arises. ~Cologic
 * </P>
 * 
 * @author tw70794
 */
public class IConnectToMeTask
    extends BaseHubTask
{

    private static final Category logger = Category.getInstance( IConnectToMeTask.class );

    private final IClientManager  clientManager;

    /**
     * Create a new IConnectToMeTask instance
     * 
     * @param clientManager
     */
    public IConnectToMeTask(
        IClientManager clientManager )
    {

        if ( clientManager == null )
        {
            throw new NullPointerException( "clientManager was null." );
        }

        this.clientManager = clientManager;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    @Override
    protected void runTaskTemplate()
    {
        // creates a Client object based on the host information contained in
        // the
        // cmdData string
        HostInfo host = new HostInfo( cmdData.substring( cmdData.indexOf( " " ) + 1 ) );

        Client client = clientManager.getClient( host );

        if ( client == null )
        {
            logger.error( "Client for host " + cmdData + " could not be found in ClientManager." );

        }
        else
        {
            client.addListener( ((Hub) hub).clientListener );

            // connects to the created Client
            try
            {
                client.connect();

            }
            catch ( IOException io )
            {
                // logger.error(io.toString());
                logger.error( "Caught " + io.getClass().getName(), io );

            }

        }

    }

}