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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.client.ConnectionInfo;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.client.ConnectionStatistics;
import net.sf.javadc.tasks.BaseClientTask;

import org.apache.log4j.Category;

/**
 * @author tw70794 To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SStartDownloadTask
    extends BaseClientTask
{

    private static final Category logger     = Category.getInstance( SStartDownloadTask.class );

    private final ISettings       settings;

    private long                  resumeFrom = 0;

    /**
     * Create a <code>SStartDownloadTask</code> instance
     * 
     * @param _settings ISettings instance to be used
     */
    public SStartDownloadTask(
        ISettings _settings )
    {

        if ( _settings == null )
        {
            throw new NullPointerException( "_settings was null." );
        }
        settings = _settings;

    }

    /**
     * Start a normal or segmented download
     * 
     * @param downloadRequest DownloadRequest instance this download is related to
     * @param file File to download to
     */
    private void download(
        DownloadRequest downloadRequest,
        File file,
        boolean normal )
    {
        ConnectionStatistics stats = clientConnection.getStatistics();

        ConnectionInfo connInfo = clientConnection.getConnectionInfo();

        if ( downloadRequest.isResume() && file.exists() && file.isFile() && file.canRead() )
        {

            connInfo.setVerifyResumeSize( (int) Math.min( file.length(), ConnectionSettings.VERIFY_RESUME_SIZE ) );

            // crop the file to a full amount of blocks
            if ( file.length() > ConnectionSettings.VERIFY_RESUME_SIZE )
            {
                int vrs = ConnectionSettings.VERIFY_RESUME_SIZE;

                // first discard the file length to a full amount of blocks and
                // then
                // chop off the last block
                resumeFrom = file.length() - file.length() % vrs - vrs;

                // start from the beginning, because the veriy resume size is
                // greater than
                // the file size
            }
            else
            {
                resumeFrom = 0;

            }

            try
            {
                clientConnection.getLocalFile().seek( resumeFrom );

            }
            catch ( IOException io )
            {
                logger.error( "Caught " + io.getClass().getName(), io );
                throw new RuntimeException( io );
            }

            if ( !normal )
            {
                resumeFrom += downloadRequest.getSegment().x;
            }

            stats.setBytesReceived( resumeFrom );

        }
        else
        {
            resumeFrom = 0;

            connInfo.setVerifyResumeSize( 0 );
            stats.setBytesReceived( 0 );

        }

    }

    /** ********************************************************************** */
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    @Override
    protected final void runTaskTemplate()
    {
        // Download slots available
        // Timo : 22.05.2004

        // check downloads is called to ensure that the ClientConnection has an
        // active DownloadRequest, if the Client has still DownloadRequests in
        // the queue
        try
        {
            clientConnection.getClient().checkDownloads();

        }
        catch ( IOException e )
        {
            logger.error( e );
            clientConnection.disconnect();
        }

        DownloadRequest downloadRequest = clientConnection.getDownloadRequest();

        if ( downloadRequest == null )
        {
            logger.debug( "No more DownloadRequests in the queue." );
            clientConnection.setState( ConnectionState.ABORTED );

        }
        else if ( settings.getFreeDownloadSlotCount() > 0 )
        {
            resumeFrom = 0;

            File file = new File( downloadRequest.getLocalFilename() );

            try
            {
                clientConnection.setLocalFile( new RandomAccessFile( file, "rw" ) );

            }
            catch ( FileNotFoundException e )
            {
                logger.error( "Caught " + e.getClass().getName(), e );
                clientConnection.setState( ConnectionState.FILE_NOT_FOUND );
            }

            if ( downloadRequest.isSegment() )
            {

                long segmentsize = downloadRequest.getSegment().y - downloadRequest.getSegment().x;

                if ( file.length() == segmentsize )
                {
                    clientConnection.setState( ConnectionState.DOWNLOAD_FINISHED );
                    return;

                }
                else
                {
                    logger.debug( "Segment download." );
                    download( downloadRequest, file, false );
                }

            }
            else
            {

                if ( file.length() == downloadRequest.getSearchResult().getFileSize() )
                {
                    clientConnection.setState( ConnectionState.DOWNLOAD_FINISHED );
                    return;

                }
                else
                {
                    logger.debug( "Normal download." );
                    download( downloadRequest, file, true );
                }

            }

            // request the file.
            sendCommand( "$Get", downloadRequest.getSearchResult().getFilename() + "$" + (resumeFrom + 1) );

            clientConnection.setState( ConnectionState.WAITING );

            clientConnection.getStatistics().setStartTime( System.currentTimeMillis() );

        }
        else
        {
            logger.debug( "Download slot could not be used." );

            logger.debug( "Download Slots (" + settings.getUsedDownloadSlots() + "/" + settings.getDownloadSlots() +
                ")" );

            clientConnection.setState( ConnectionState.NO_DOWNLOAD_SLOTS );

        }

    }

}