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

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.tasks.BaseHubTask;
import net.sf.javadc.util.DCEncryptionHandler;

import org.apache.log4j.Category;

/**
 * <CODE>ILockTask</CODE> represents the first part of the client-hub handshake
 * <p>
 * This command is presumably used to make sure that only the original DC client (NMDC) would be able to connect to
 * Direct Connect Hubs and Clients. Hubs are not required to check the validity of the returned key, but they must send
 * this command. It is used in the Client-Hub Handshake, the Client-Client Handshake and the ?Hublist Registration. The
 * other party has to authenticate itself using the LockToKey function. Luckily for us, ??? has decrypted the algorithm,
 * so now anyone may create a client for the DC network.
 * </p>
 * <p>
 * <i>Syntax:</i>
 * </p>
 * <p>
 * $Lock &lt;lock&gt; Pk=<pk>|
 * </p>
 * &lt;lock&gt; is a sequence of random characters (except (space), $, |, (?more?)), the minimum and maximum length is
 * not known it is not known what the &lt;pk&gt; is for. and some client implementations skip the Pk= part altogether
 * (DC++ can cope with this, presumably NMDC as well) </p>
 * <p>
 * The client never has to send a $Lock to the hub. Clients have to send a $Lock when two clients connect to eachother.
 * </p>
 * <p>
 * Example lock for DC++-0.242:
 * </p>
 * <p>
 * $Lock EXTENDEDPROTOCOLABCABCABCABCABCABC Pk=DCPLUSPLUS0.242ABCABC|
 * </p>
 * <p>
 * The "ABCABC" are just fillers.
 * </p>
 * 
 * @author tw70794
 */
public class ILockTask
    extends BaseHubTask
{

    private static final Category     logger            = Category.getInstance( ILockTask.class );

    private final DCEncryptionHandler encryptionHandler = new DCEncryptionHandler();

    private final ISettings           settings;

    /**
     * Create a new ILockTask instance
     * 
     * @param settings
     */
    public ILockTask(
        ISettings settings )
    {

        if ( settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }

        this.settings = settings;

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

        // send a list of the supported commands
        if ( cmdData.indexOf( "EXTENDEDPROTOCOL" ) > -1 )
        {
            sendCommand( "$Supports", "GetZBlock TTHSearch" );
        }

        final int index = cmdData.indexOf( " Pk=" );

        if ( index > -1 )
        {
            String cmdKey = cmdData.substring( 0, index );
            // String privateKey = cmdData.substring(index + 4,
            // cmdData.length());

            logger.debug( "Received Challenge/Response request recieved, we're Logging in" );

            logger.debug( "Key : " + cmdKey );

            // calculate response key and send it back
            String key = encryptionHandler.calculateValidationKey( cmdKey );
            String nick = settings.getUserInfo().getNick();

            sendCommand( "$Key", key );
            sendCommand( "$ValidateNick", nick );

        }
        else
        {
            throw new HubTaskException( "Received invalid format for $Lock command." );

        }

    }

}