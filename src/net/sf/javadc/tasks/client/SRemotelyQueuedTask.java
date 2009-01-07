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

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.tasks.BaseClientTask;

/**
 * @author tw70794 To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SRemotelyQueuedTask extends BaseClientTask {

    // private static final Category logger = Category
    // .getInstance(SRemotelyQueuedTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {

        long curr_time = System.currentTimeMillis();
        long last_time = clientConnection.getStatistics().getLastStateChange();
        // logger.debug("Waiting for remote Client to free slot");

        // make a reattempt to download the file after the retry interval has
        // passed
        if (curr_time - last_time > ConnectionSettings.CONNECTION_RETRY_INTERVAL) {

            clientConnection.setState(ConnectionState.COMMAND_DOWNLOAD);

        }

    }

}