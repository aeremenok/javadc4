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

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.client.ConnectionStatistics;
import net.sf.javadc.tasks.BaseClientTask;

import org.apache.log4j.Category;

/**
 * @author tw70794 To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SDownloadingTask
    extends BaseClientTask
{

    private static final Category logger = Category.getInstance( SDownloadingTask.class );

    /**
     * 
     */
    private static final long     MBYTES = 1024 * 1024;

    // components
    // private final ISettings settings;

    /**
     * Create a <code>SDownloadingTask</code> instance
     */
    public SDownloadingTask()
    {
        // if (_settings == null)
        // throw new NullPointerException("_settings was null.");
        //        
        // settings = _settings;

    }

    /**
     * Continue the download
     * 
     * @param size amount of bytes to be downloaded
     */
    private final void continueDownload(
        int size )
    {
        ConnectionStatistics stats = clientConnection.getStatistics();

        byte[] buffer = new byte[size];

        try
        {
            // read from client connection and write to file
            if ( clientConnection.getReader() != null )
            {
                clientConnection.getReader().read( buffer );
            }
            else
            {
                throw new NullPointerException( "clientConnection.reader was null" );
            }

            if ( clientConnection.getLocalFile() != null )
            {
                clientConnection.getLocalFile().write( buffer );
            }
            else
            {
                throw new NullPointerException( "clientConnection.localFile was null" );
            }

        }
        catch ( IOException io )
        {
            throw new ClientTaskException( "Caught " + io.getClass().getName(), io );
        }

        stats.setBytesReceived( stats.getBytesReceived() + size );
        clientConnection.updateConnectionInfo();

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
        ConnectionStatistics stats = clientConnection.getStatistics();

        // long bytesReceived = stats.getBytesReceived();
        int size = 0;

        try
        {
            // selects minimum of reader buffer size and previously specified
            // file length - bytes received
            size =
                (int) Math.min( clientConnection.getReader().available(), stats.getFileLength() -
                    stats.getBytesReceived() );

        }
        catch ( IOException io )
        {
            logger.error( io );

            // ?
        }

        if ( size > 0 )
        { // something to download

            try
            {
                continueDownload( size );

            }
            catch ( Exception e )
            {
                throw new ClientTaskException( "Caught " + e.getClass().getName() +
                    " when trying to continue download.", e );

            }

            // update the last state change time
            clientConnection.setState( clientConnection.getState() );

        }
        else
        {

            logger.debug( "currently nothing to download." );

            long last_time = stats.getLastStateChange();
            long curr_time = System.currentTimeMillis();

            if ( curr_time - last_time > ConnectionSettings.CONNECTION_WAITING_TIME )
            {

                /*
                 * logger.info("Disconnecting because timeout of " +
                 * ConnectionSettings.CONNECTION_WAITING_TIME + " ms was
                 * exceeded in " + this.getClass().getName());
                 */

                logger.info( "Disconnecting because timeout was exceeded." );

                // if the REMOTELEY_QUEUED state is used, the remote client
                // might continue to send the file data, which is not used

                clientConnection.setState( ConnectionState.ABORTED );

            }

        }

        long downloadChunkSize = ConnectionSettings.DOWNLOAD_CHUNK_SIZE;

        if ( stats.getBytesReceived() == stats.getFileLength() )
        { // download
            // finished

            logger.info( "Finishing download." );

            // finishDownload();
            clientConnection.setState( ConnectionState.DOWNLOAD_FINISHED );

        }
        else if ( stats.getBytesReceived() - stats.getStartLocation() > downloadChunkSize )
        {

            logger.info( "Disconnecting after downloading " + downloadChunkSize / MBYTES + " mbytes." );

            clientConnection.setState( ConnectionState.ABORTED );

        }

    }

}