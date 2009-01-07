/* 
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.net.UploadRequest;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.client.ConnectionStatistics;
import net.sf.javadc.tasks.BaseClientTask;
import net.sf.javadc.util.SafeParser;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * This command is used to request a file from the uploading client.
 * 
 * <p>
 * The $Get command is used to request a file from the uploading client.
 * </p>
 * 
 * <p>
 * $Get &lt;filenameandpath&gt;$&lt;resumecount&gt;
 * </p>
 * 
 * <p>
 * &lt;filenameandpath&gt; is the remote location of the file requested.
 * </p>
 * <p>
 * &lt;resumecount&gt; is the byte offset to start at for resuming files.
 * </p>
 * 
 * @author tw70794
 */
public class IGetTask extends BaseClientTask {

    private static final Category logger = Logger.getLogger(IGetTask.class);

    // components
    private final IShareManager shareManager;

    private final ISettings settings;

    // helpers
    // private final ConnectionSlotReservation slotReservation;

    /**
     * Create a <code>IGetTask</code>
     * 
     * @param _shareManager
     *            IShareManager instance to be used
     * @param _settings
     *            ISettings instance to be used
     */
    public IGetTask(IShareManager _shareManager, ISettings _settings) {

        if (_shareManager == null)
            throw new NullPointerException("_shareManager was null.");

        else if (_settings == null)
            throw new NullPointerException("_settings was null.");

        shareManager = _shareManager;
        settings = _settings;
    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {
        String filename = cmdData.substring(0, cmdData.indexOf("$"));

        // get the filename.
        long fileOffset = SafeParser.parseLong(cmdData.substring(cmdData
                .indexOf("$") + 1), -1);

        if (settings.reserveUploadSlot() && (fileOffset > -1)) {
            // If it's a valid filename and we could reserve a slot, send file
            // length.
            File local = shareManager.getFile(filename);

            if ((local == null) || !local.isFile()) {
                // clientConnection.setState(ConnectionState.NO_UPLOAD_SLOTS);
                // Send No Slots
                sendCommand("$Error", "");

                logger.error("The required file " + filename + " was "
                        + ((local == null) ? "null" : "invalid"));

                clientConnection.setState(ConnectionState.CORRUPT_FILE);

            } else {

                startUpload(local, fileOffset);
            }

        } else {
            clientConnection.setState(ConnectionState.NO_UPLOAD_SLOTS);

            logger.info("Upload slot could not be granted for "
                    + clientConnection);

            // Send No slots.
            sendCommand("$MaxedOut", "");

        }

    }

    /**
     * Start to upload the given local file beginning from the given offset
     * 
     * @param local
     *            file to be uploaded
     * @param fileOffset
     *            byte offset where to begin from
     */
    public void startUpload(File local, long fileOffset) {

        try {
            clientConnection.setLocalFile(new RandomAccessFile(local, "r"));
            clientConnection.setUploadRequest(new UploadRequest(local));

        } catch (FileNotFoundException e) {
            logger.error(e.toString());
            sendCommand("$Error", "");
            clientConnection.setState(ConnectionState.FILE_NOT_FOUND);
            return;

        }

        logger.debug("Searching position:" + (fileOffset - 1));

        try {
            clientConnection.getLocalFile().seek(fileOffset - 1);

        } catch (IOException e) {
            logger.error("Caught " + e.getClass().getName(), e);

            clientConnection.setState(ConnectionState.CORRUPT_FILE);
            return;
        }

        ConnectionStatistics stats = clientConnection.getStatistics();
        stats.setBytesReceived(fileOffset - 1);

        // set status.
        // Try if we get the "start on byte 1 offset" correct.
        stats.setFileLength(local.length() - fileOffset + 1);

        sendCommand("$FileLength", Long.toString(stats.getFileLength()));

        clientConnection.setState(ConnectionState.UPLOADING);

        // We're not certain that a $Send will come, start transfer immediately.

    }

}