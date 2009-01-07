/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.tasks.client;

import java.io.IOException;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.client.ConnectionInfo;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.tasks.BaseClientTask;
import net.sf.javadc.util.DCEncryptionHandler;

import org.apache.log4j.Category;

/**
 * <p>
 * This command is presumably used to make sure that only the original DC client (NMDC) would be able to connect to
 * Direct Connect Hubs and Clients. Hubs are not required to check the validity of the returned key, but they must send
 * this command.
 * </p>
 * <p>
 * It is used in the Client-Hub Handshake, the Client-Client Handshake and the Hublist Registration. The other party has
 * to authenticate itself using the LockToKey function. Luckily for us, x has decrypted the algorithm, so now anyone may
 * create a client for the DC network.
 * </p>
 * 
 * @author tw70794
 */
public class ILockTask
    extends BaseClientTask
{
    private static final Category     logger            = Category.getInstance( ILockTask.class );

    private final DCEncryptionHandler encryptionHandler = new DCEncryptionHandler();

    // components
    private final ISettings           settings;

    /**
     * Create a <code>ILockTask</code> instance
     * 
     * @param _settings ISettings instance to be used
     */
    public ILockTask(
        ISettings _settings )
    {

        if ( _settings == null )
        {
            throw new NullPointerException( "_settings was null." );
        }

        settings = _settings;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    @Override
    protected final void runTaskTemplate()
    {
        // This is the login Challenge/Reponse request, the challenge
        // is the part before " Pk=".
        // I believe that Pk stands for privateKey or publicKey - However
        // I haven't found any use for it
        int index = cmdData.indexOf( " Pk=" );

        if ( index > -1 )
        {
            String cmdKey = cmdData.substring( 0, index );
            // String privateKey = cmdData.substring(index + 4);

            String debug = "Challenge / Response request received," + " we're logging in";
            logger.debug( debug );

            try
            {
                ConnectionInfo connInfo = clientConnection.getConnectionInfo();
                connInfo.setRemoteKey( encryptionHandler.calculateValidationKey( cmdKey ) );

                // no special recovery from invalid keys
            }
            catch ( StringIndexOutOfBoundsException e )
            {
                logger.error( "Invalid key " + cmdKey );

            }

            // sending MyNick and Lock

            // if the connection is active, then MyNick and Lock have already
            // been sent
            if ( !settings.isActive() )
            {
                // TODO : user component from outside ?
                BaseClientTask task = new SMyNickTask( settings );

                task.setClientConnection( clientConnection );
                task.runTask();
            }
            else
            {
                clientConnection.setState( ConnectionState.LOGIN );
            }

            if ( clientConnection.getClient() == null )
            {
                logger.error( "Client was not set" );

            }
            else if ( !clientConnection.getClient().hasDownloads() )
            {
                logger.error( "Client had no downloads" );

            }
            else
            {
                // if we have downloads, request a download direction.
                try
                {

                    // sending Direction and Key
                    // /////////////////////////////////////////////////////////////////
                    clientConnection.requestDirection( ConstantSettings.DOWNLOAD_DIRECTION, false );

                }
                catch ( IOException io )
                {
                    logger.error( "Caught " + io.getClass().getName(), io );
                    clientConnection.setState( ConnectionState.ABORTED );

                }

            }

        }
        else
        {
            clientConnection.setState( ConnectionState.INVALID_LOCK );

            throw new ClientTaskException( "Invalid data " + cmdData + " for $Lock." );

        }

    }

}