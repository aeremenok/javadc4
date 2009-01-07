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
import java.util.Arrays;

import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.client.ConnectionStatistics;
import net.sf.javadc.tasks.BaseClientTask;

import org.apache.log4j.Category;

/**
 * @author tw70794 To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SResumingTask extends BaseClientTask {

    private static final Category logger = Category
            .getInstance(SResumingTask.class);

    // private final ISettings settings;

    /**
     * Create a <code>SResumingTask</code> instance
     */
    public SResumingTask() {

        // if (_settings == null)
        // throw new NullPointerException("_settings was null.");
        //        
        // settings = _settings;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {

        ConnectionStatistics stats = clientConnection.getStatistics();

        // the verifyResumeSize equals the buffer size of the SocketInputStream

        int verifyResumeSize = clientConnection.getConnectionInfo()
                .getVerifyResumeSize();

        try {
            if (clientConnection.getReader().available() >= verifyResumeSize) {

                // size in bytes the file is discarded to
                long resumeFrom = stats.getBytesReceived() - 1024 * 1024;
                byte[] buffer = new byte[verifyResumeSize];

                clientConnection.getReader().read(buffer);

                byte[] fileBlock = new byte[verifyResumeSize];
                clientConnection.getLocalFile().read(fileBlock);

                // update the statistics for the saved file
                stats.setBytesReceived(stats.getBytesReceived()
                        + verifyResumeSize);

                clientConnection.updateConnectionInfo();

                // if the last block doesn't match, abort download.

                if (!(Arrays.equals(buffer, fileBlock))) {
                    blockDoesntMatch(resumeFrom);

                } else {
                    // last block matches blockMatches();
                    blockMatches();
                }

            } else {
                // no abortion, instead give the connection time to fill up the
                // buffer

                // abort the download, if the reader doesn't provide any bytes
                // to download
                // clientConnection.setState(ConnectionState.ABORTED);

            }

        } catch (IOException io) {
            logger.error(io.toString());

        }

    }

    /**
     * the buffer verification failed
     */
    private final void blockDoesntMatch(long resumeFrom) {
        logger.error("Tried resume, but the files weren't equal");

        logger.info("The file is now discarded to a length of " + resumeFrom
                + " bytes.");

        try {
            if (resumeFrom < 0) {
                resumeFrom = 0;
            }

            // DownloadRequest dr = clientConnection.getDownloadRequest();

            clientConnection.getLocalFile().setLength(resumeFrom);

        } catch (IOException io) {
            logger.error("Catched IOException when trying to set length of "
                    + clientConnection.getLocalFile(), io);

        }

        clientConnection.disconnect();

    }

    /**
     * the buffer verification succeeded
     */
    private final void blockMatches() {
        clientConnection.setState(ConnectionState.RESUME_SUCCEEDED);

        ConnectionStatistics stats = clientConnection.getStatistics();

        // file has been fully downloaded
        if (stats.getBytesReceived() == stats.getFileLength()) {
            clientConnection.closeFile();
            clientConnection.fireDownloadComplete();

            clientConnection.setState(ConnectionState.DOWNLOAD_FINISHED);

            // still something to download, continue with State.DOWNLOADING
        } else {
            // Timo : 22.05.2005
            clientConnection.setState(ConnectionState.DOWNLOADING);

        }

    }

}