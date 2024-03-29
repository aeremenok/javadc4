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
import java.io.OutputStream;

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.net.client.ConnectionInfo;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.client.ConnectionStatistics;
import net.sf.javadc.tasks.BaseClientTask;
import net.sf.javadc.util.ExtendedBufferedOutputStream;
import net.sf.javadc.util.zlib.ZOutputStream;

import org.apache.log4j.Category;

/**
 * <code>SZUploadingTask</code> represents the task of zlib based compressed uploading. Via the useCompression attribute
 * of <code>Connection</code>, the usage of compression can be toggled.
 * 
 * @author Timo Westk�mper
 */
public class SZUploadingTask
    extends BaseClientTask
{

    private static final Category logger = Category.getInstance( SZUploadingTask.class );

    private OutputStream          writer = null;

    private ConnectionStatistics  stats;

    // components
    // private final ISettings settings;

    /**
     * Create a <code>SZUploadingTask</code> instance
     */
    public SZUploadingTask()
    {

        // if (_settings == null)
        // throw new NullPointerException("_settings was null.");
        //         
        // settings = _settings;

    }

    /**
     * Continue the upload
     * 
     * @param size amount of bytes to be uploaded
     */
    private final void continueUpload(
        int size )
    {
        logger.debug( "Loading buffer with:" + size + "bytes" );

        final byte[] buffer = new byte[size];

        try
        {
            clientConnection.getLocalFile().read( buffer );
            writer.write( buffer );

        }
        catch ( IOException io )
        {
            logger.error( io.toString() );

            // ?
        }

        stats.setBytesReceived( stats.getBytesReceived() + size );
        clientConnection.updateConnectionInfo();

        ConnectionInfo connInfo = clientConnection.getConnectionInfo();

        connInfo.setBytesToUpload( connInfo.getBytesToUpload() - size );

    }

    /**
     * Finish the upload
     */
    private final void finishUpload()
    {
        logger.debug( "Transfer done, cleaning up!" );

        clientConnection.closeFile();

        // if (writer.bytesInBuffer() == 0) {
        try
        {
            writer.flush();
            // This might take some time, but needs to be done!

        }
        catch ( IOException io )
        {
            logger.debug( io );

        }

        // }

        logger.debug( "Going into idle!" );

        // TODO : maybe something that leads to SDisconnectTask
        clientConnection.setState( ConnectionState.UPLOAD_FINISHED );

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
        stats = clientConnection.getStatistics();

        ConnectionInfo connInfo = clientConnection.getConnectionInfo();

        if ( !connInfo.isUseCompression() )
        {
            // Buffered stream.
            writer = clientConnection.getWriter();

            logger.debug( "Free in buffer: " + ((ExtendedBufferedOutputStream) writer).nonBlockingCapacity() );

        }
        else
        {
            // ZOutputStream is used

            // create a ZOutputStream which wraps the
            // ExtendedBufferedOutputStream
            // of the Client Connection
            writer = new ZOutputStream( clientConnection.getWriter() );

        }

        int size = (int) Math.min( ConnectionSettings.UPLOAD_BLOCK_SIZE, connInfo.getBytesToUpload() );

        logger.debug( "File length was:" + stats.getFileLength() );
        logger.debug( "Bytes received:" + stats.getBytesReceived() );

        if ( size > 0 )
        {
            continueUpload( size );

        }
        else
        {
            logger.info( "Finishing upload." );

            finishUpload();

        }

    }

}