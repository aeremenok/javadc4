/* *
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
 *
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software;      you can redistribute it and/or modify it
 * under the terms of the   GNU General Public License as published by the Free
 * Software Foundation;    either version 2 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;   without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE.   See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.tasks.client;

import java.io.IOException;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.client.ConnectionInfo;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.tasks.BaseClientTask;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * <p>
 * A command used to issue the response to a Lock command sent by the remote
 * host.
 * </p>
 * 
 * <p>
 * Exists and works the same way in both client-client and hub-client
 * communication, however, it is also used when a hub is registering to a
 * hublist server, but then the first character in the key is calculated
 * differently.
 * </p>
 * 
 * <p>
 * For info how to calculate this in hub-client / client-client communication,
 * see LockToKey.
 * </p>
 * 
 * @author tw70794 the response to the Lock command; see: LockToKey
 */
public class IKeyTask extends BaseClientTask {

    private static final Category logger = Logger.getLogger(IKeyTask.class);

    // components
    private final ISettings settings;

    /**
     * Create a <code>IKeyTask</code> instance
     * 
     * @param _settings
     *            ISettings instance to be used
     */
    public IKeyTask(ISettings _settings) {

        if (_settings == null)
            throw new NullPointerException("_settings was null.");

        settings = _settings;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {
        ConnectionInfo connInfo = clientConnection.getConnectionInfo();

        if (ConstantSettings.UPLOAD_DIRECTION.equals(connInfo
                .getCurrentDirection())) {
            // If the other side want's download, we have a upload!
            logger.debug("Requesting upload direction.");

            try {
                clientConnection.requestDirection(
                        ConstantSettings.UPLOAD_DIRECTION, true);

                // we have to ack it!
                clientConnection.setState(ConnectionState.COMMAND_UPLOAD);

            } catch (IOException io) {
                logger.error("Caught " + io.getClass().getName(), io);
                clientConnection.setState(ConnectionState.ABORTED);

            }

        } else if (ConstantSettings.DOWNLOAD_DIRECTION.equals(connInfo
                .getCurrentDirection())) {

            logger.debug("Requesting download direction.");

            if (settings.isActive()) {
                // we may download!
                try {
                    clientConnection.getClient().checkDownloads();
                    clientConnection.setState(ConnectionState.COMMAND_DOWNLOAD);

                } catch (IOException io) {
                    logger.error("Caught " + io.getClass().getName(), io);
                    clientConnection.setState(ConnectionState.ABORTED);

                }

            } else { // passive connection
                clientConnection.setState(ConnectionState.COMMAND_DOWNLOAD);

            }

        } else {
            clientConnection.setState(ConnectionState.INVALID_DIRECTION);

        }

    }

}