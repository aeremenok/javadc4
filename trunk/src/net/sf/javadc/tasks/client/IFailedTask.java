/*
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
 * This command is sent when an error occurs.
 * 
 * @author Timo Westk�mper
 */
public class IFailedTask extends BaseClientTask {

    private static final Category logger = Category
            .getInstance(IFailedTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.BaseClientTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {
        ConnectionState state = clientConnection.getState();

        if (clientConnection.getDownloadRequest() != null) {
            clientConnection.getDownloadRequest().setFailed(true);

        }

        if (state == ConnectionState.DOWNLOADING) {
            clientConnection.fireDownloadFailed();

        } else if (state == ConnectionState.UPLOADING) {

            // clientConnection.fireUploadFailed();
        }

        logger.error("Received $Failed " + getCmdData());

        clientConnection.setState(ConnectionState.ABORTED);

    }

}