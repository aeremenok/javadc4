/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.net.client;

import java.util.HashMap;
import java.util.Map;

import net.sf.javadc.util.Enum;

/**
 * <CODE>ClientConnectionState</CODE> represents the different states of a <CODE>ClientConnection</CODE>
 */
public class ConnectionState
    extends Enum
{
    public final static ConnectionState               COMMAND_DOWNLOAD     = new ConnectionState( "Idle download mode" );
    public final static ConnectionState               COMMAND_UPLOAD       = new ConnectionState( "Idle upload mode" );

    // initial states
    public final static ConnectionState               CONNECTING           = new ConnectionState( "Connecting" );
    public final static ConnectionState               NOT_CONNECTED        = new ConnectionState( "Not connected" );
    public final static ConnectionState               LOGIN                = new ConnectionState( "Login" );
    public final static ConnectionState               SETTING_DIRECTION    = new ConnectionState( "Setting direction" );

    // contiuous states
    public final static ConnectionState               DOWNLOADING          = new ConnectionState( "Downloading" );
    public final static ConnectionState               RESUMING             = new ConnectionState( "Resuming" );
    public final static ConnectionState               RESUME_SUCCEEDED     = new ConnectionState( "Resume succeeded." );
    public final static ConnectionState               UPLOADING            = new ConnectionState( "Uploading" );
    public final static ConnectionState               COMPRESSED_UPLOADING =
                                                                               new ConnectionState(
                                                                                   "Uploading (compressed)" );
    public final static ConnectionState               DOWNLOAD_FINISHED    = new ConnectionState( "Download finished" );
    public final static ConnectionState               UPLOAD_FINISHED      = new ConnectionState( "Upload finished" );

    // waiting states
    public final static ConnectionState               WAITING              = new ConnectionState( "Waiting" );
    public final static ConnectionState               REMOTELY_QUEUED      = new ConnectionState( "Remotely Queued" );
    public final static ConnectionState               NO_UPLOAD_SLOTS      = new ConnectionState( "No Upload slots" );

    public final static ConnectionState               NO_DOWNLOAD_SLOTS    = new ConnectionState( "No Download slots" );

    // public final static ConnectionState RESCHEDULE = new ConnectionState(
    // "Reschedule Download");

    // error states
    public final static ConnectionState               ABORTED              = new ConnectionState( "Aborted" );
    public final static ConnectionState               RESUME_FAILED        = new ConnectionState( "Resume failed." );
    public final static ConnectionState               CORRUPT_FILE         = new ConnectionState( "Corrupt File" );
    public final static ConnectionState               INVALID_LOCK         = new ConnectionState( "Invalid Lock Data" );
    public final static ConnectionState               INVALID_DIRECTION    =
                                                                               new ConnectionState(
                                                                                   "Invalid Direction Data" );
    public final static ConnectionState               FILE_NOT_FOUND       = new ConnectionState( "File Not Found." );
    private static final Map<ConnectionState, String> transitions          = new HashMap<ConnectionState, String>();

    static
    {
        // loads the transitions into the HashMap
        // download related

        transitions.put( ConnectionState.COMMAND_DOWNLOAD, "SStartDownload" );
        transitions.put( ConnectionState.DOWNLOAD_FINISHED, "SDownloadFinished" );
        transitions.put( ConnectionState.DOWNLOADING, "SDownloading" );

        transitions.put( ConnectionState.NO_DOWNLOAD_SLOTS, "SDisconnect" );

        // resume related

        transitions.put( ConnectionState.RESUMING, "SResuming" );

        // upload related

        // COMMAND_UPLOAD relies on further commands

        // transitions.put(ConnectionState.COMMAND_UPLOAD, "SStartUpload");
        transitions.put( ConnectionState.UPLOADING, "SUploading" );
        transitions.put( ConnectionState.UPLOAD_FINISHED, "SDisconnect" );

        transitions.put( ConnectionState.COMPRESSED_UPLOADING, "SZUploading" );

        // queue related
        transitions.put( ConnectionState.REMOTELY_QUEUED, "SRemotelyQueued" );
        // transitions.put(ConnectionState.RESCHEDULE, "SRemotelyQueued");

        // general errors
        transitions.put( ConnectionState.ABORTED, "SDisconnect" );
        transitions.put( ConnectionState.INVALID_DIRECTION, "SDisconnect" );
        transitions.put( ConnectionState.NO_UPLOAD_SLOTS, "SDisconnect" );
        transitions.put( ConnectionState.INVALID_LOCK, "SDisconnect" );

        // file errors
        transitions.put( ConnectionState.CORRUPT_FILE, "SDisconnect" );
        transitions.put( ConnectionState.FILE_NOT_FOUND, "SDisconnect" );

    }

    /**
     * Query for a TaskString by providing a ConnectionState object
     * 
     * @param state ConnectionState for which the task name should be retrieved
     * @return taskName that can be used to query a ClientTask from the ClientTaskFactory
     */
    public static String getTransition(
        ConnectionState state )
    {
        return transitions.get( state );
    }

    /**
     * Get the map of ConnectionState / ClientTask name transitions
     * 
     * @return
     */
    public static Map<ConnectionState, String> getTransitions()
    {
        return transitions;
    }

    /**
     * Create a ConnectionState instance with the given name
     * 
     * @param name
     */
    private ConnectionState(
        String name )
    {
        super( name );
    }

}
