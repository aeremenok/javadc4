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

import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.tasks.BaseClientTask;

import org.apache.log4j.Category;

/**
 * $MaxedOut is the command you would send to a client that wishes to download
 * something from you, but you have no free slots.
 * 
 * @author tw70794
 */
public class IMaxedOutTask extends BaseClientTask {

    // private static int DELAY_TIME = 120 * 1000;
    private static final Category logger = Category
            .getInstance(IMaxedOutTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {
        // The $MaxedOut command is sent when there are no free slots available
        // from the uploading user.

        StringBuffer output = new StringBuffer();

        output.append("Received $MaxedOut");
        try {
            output.append("from ").append(
                    clientConnection.getClient().getNick());

        } catch (NullPointerException e) {
            logger.info(e);

        }

        logger.info(output.toString());

        clientConnection.setState(ConnectionState.REMOTELY_QUEUED);

        // TODO : remove DownloadRequest from Connection and push it to the
        // Client
        // via a timed delay
    }

}