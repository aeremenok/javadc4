/* *
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
 *
 * Copyright (C) 2002 Michael Kurz, mkurz@epost.de
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

import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.client.ConnectionStatistics;
import net.sf.javadc.tasks.BaseClientTask;
import net.sf.javadc.util.SafeParser;

/**
 * $FileLength is sent by the uploading client in response to a $Get command.
 * The only content is the size of the requested file in bytes.
 * 
 * <p>
 * $FileLength &lt;filesize&gt;|
 * </p>
 * 
 * @author tw70794
 */
public class IFileLengthTask extends BaseClientTask {

    // private static final Category logger = Category
    // .getInstance(IFileLengthTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {
        long fileLength = 0;
        DownloadRequest dr = clientConnection.getDownloadRequest();

        if (dr.isSegment()) { // segmented download
            // download only until end of segment
            fileLength = dr.getSegment().y;

        } else { // normal download
            fileLength = SafeParser.parseLong(cmdData, 0);

        }

        ConnectionStatistics stats = clientConnection.getStatistics();

        // sets file length of file to be downloaded
        stats.setFileLength(fileLength);

        stats.setStartLocation(stats.getBytesReceived());

        sendCommand("$Send", "");

        if (clientConnection.getConnectionInfo().getVerifyResumeSize() > 0) {
            // resuming file
            clientConnection.setState(ConnectionState.RESUMING);

            // instead of checking the resume match we just resume the download
            // at the given position
            // clientConnection.setState(ConnectionState.DOWNLOADING);

        } else { // starting new download
            clientConnection.setState(ConnectionState.DOWNLOADING);

        }

        sendCommand("$Send", "");

    }

}