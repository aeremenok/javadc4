/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.tasks.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.net.UploadRequest;
import net.sf.javadc.net.client.ConnectionInfo;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.client.ConnectionStatistics;
import net.sf.javadc.tasks.BaseClientTask;
import net.sf.javadc.util.SafeParser;

import org.apache.log4j.Category;

/**
 * <p>
 * Other than the file list, the client must also support one or two new commands, $UGetBlock and $UGetZBlock. The
 * syntax and semantics of $UGetZBlock are exactly the same as the $GetZBlock, but the filename must be given in utf-8
 * encoding.
 * </p>
 * <p>
 * $UGetBlock follows $UGetZBlock semantics, but without compressing the data. The bytes parameter of $Sending specifies
 * how many bytes will be sent.
 * </p>
 * 
 * @author Timo Westk�mper
 */
public class IUGetBlockTask
    extends BaseClientTask
{

    private static final Category logger = Category.getInstance( IUGetBlockTask.class );

    // components
    private final IShareManager   shareManager;

    private final ISettings       settings;

    /**
     * Create a <code>IUGetBlockTask</code> instance
     * 
     * @param _shareManager IShareManager instance to be used
     * @param _settings ISettings instance to be used
     */
    public IUGetBlockTask(
        IShareManager _shareManager,
        ISettings _settings )
    {

        if ( _shareManager == null )
        {
            throw new NullPointerException( "_shareManager was null." );
        }
        else if ( _settings == null )
        {
            throw new NullPointerException( "_settings was null." );
        }

        shareManager = _shareManager;
        settings = _settings;
    }

    /**
     * Start the upload of the given File at the given offset
     * 
     * @param local File to be uploaded
     * @param fileOffset file offset to be used
     */
    public void startUpload(
        File local,
        long fileOffset )
    {
        boolean success = true;

        try
        {
            clientConnection.setLocalFile( new RandomAccessFile( local, "r" ) );
            clientConnection.setUploadRequest( new UploadRequest( local ) );

        }
        catch ( FileNotFoundException e )
        {
            logger.error( e.toString() );
            sendCommand( "$Failed", "File could not be found." );

            clientConnection.setState( ConnectionState.FILE_NOT_FOUND );
            success = false;

        }

        if ( success )
        {
            // fileOffset is zero based
            logger.debug( "Searching position:" + fileOffset );

            try
            {
                // fileOffset is zero based
                clientConnection.getLocalFile().seek( fileOffset );

            }
            catch ( IOException e )
            {
                logger.error( e.toString() );
                clientConnection.setState( ConnectionState.CORRUPT_FILE );

                sendCommand( "$Failed", "File could not be processed" );
                success = false;
            }

        }

        if ( success )
        {
            ConnectionInfo connInfo = clientConnection.getConnectionInfo();
            // "$Sending <bytes>|<compressed data>"
            sendCommand( "$Sending", String.valueOf( connInfo.getBytesToUpload() ) );

            ConnectionStatistics stats = clientConnection.getStatistics();
            stats.setBytesReceived( fileOffset );

            clientConnection.setState( ConnectionState.COMPRESSED_UPLOADING );
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    @Override
    protected void runTaskTemplate()
    {
        String[] elements = cmdData.split( "\\s" );

        long fileOffset = SafeParser.parseLong( elements[0], -1 );
        long numBytes = SafeParser.parseLong( elements[1], -1 );

        String filename = elements[2];

        ConnectionInfo connInfo = clientConnection.getConnectionInfo();

        if ( settings.reserveUploadSlot() && fileOffset > -1 )
        {
            File local = shareManager.getFile( filename );

            if ( local == null || !local.isFile() )
            {
                sendCommand( "$Failed", "Filename was invalid." );

                clientConnection.setState( ConnectionState.CORRUPT_FILE );

                throw new ClientTaskException( "The required file " + filename + " was " +
                    (local == null ? "null" : "invalid") );

            }
            else
            {
                connInfo.setBytesToUpload( numBytes );
                startUpload( local, fileOffset );
            }

        }
        else
        {
            clientConnection.setState( ConnectionState.NO_UPLOAD_SLOTS );

            logger.info( "Upload slot could not be granted for " + clientConnection );

            // Send No slots.
            sendCommand( "$MaxedOut", "" );

        }

        connInfo.setUseCompression( false );

    }

}