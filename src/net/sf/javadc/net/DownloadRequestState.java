/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.net;

import java.util.HashMap;
import java.util.Map;

import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.util.Enum;

/**
 * <code>DownloadRequestState</code> represents a single state of a <code>DownloadRequest</code> instance
 * 
 * @author Timo Westk�mper
 */
public class DownloadRequestState
    extends Enum
{
    /**
     * 
     */
    private static Map                       activeChanges;

    /**
     * 
     */
    private static Map                       passiveChanges;

    /**
     * 
     */
    public final static DownloadRequestState CONNECTING      = new DownloadRequestState( "Connecting" );

    /**
     * 
     */
    public final static DownloadRequestState DOWNLOADING     = new DownloadRequestState( "Downloading" );

    /**
     * 
     */
    public final static DownloadRequestState OFFLINE         = new DownloadRequestState( "Offline" );

    /**
     * 
     */
    public final static DownloadRequestState QUEUED          = new DownloadRequestState( "Queued" );

    /**
     * 
     */
    public final static DownloadRequestState REMOTELY_QUEUED = new DownloadRequestState( "Remotely Queued" );

    /**
     * 
     */
    public final static DownloadRequestState WAITING         = new DownloadRequestState( "Waiting" );

    static
    {
        activeChanges = new HashMap();

        activeChanges.put( ConnectionState.DOWNLOADING, DownloadRequestState.DOWNLOADING );

        activeChanges.put( ConnectionState.CONNECTING, DownloadRequestState.CONNECTING );

        activeChanges.put( ConnectionState.WAITING, DownloadRequestState.WAITING );

        activeChanges.put( ConnectionState.REMOTELY_QUEUED, DownloadRequestState.REMOTELY_QUEUED );

        activeChanges.put( ConnectionState.NO_DOWNLOAD_SLOTS, DownloadRequestState.QUEUED );

        activeChanges.put( ConnectionState.NOT_CONNECTED, DownloadRequestState.OFFLINE );

        passiveChanges = new HashMap();

        passiveChanges.put( ConnectionState.CONNECTING, DownloadRequestState.CONNECTING );

        passiveChanges.put( ConnectionState.DOWNLOADING, DownloadRequestState.QUEUED );

        passiveChanges.put( ConnectionState.REMOTELY_QUEUED, DownloadRequestState.REMOTELY_QUEUED );

        passiveChanges.put( ConnectionState.NO_DOWNLOAD_SLOTS, DownloadRequestState.QUEUED );

        passiveChanges.put( ConnectionState.NOT_CONNECTED, DownloadRequestState.OFFLINE );

    }

    // private String info = "";

    /**
     * Derive the DownloadRequestState from the state of the related IConnection
     * 
     * @param connectionState ConnectionState from which the DownloadRequestState is to be derived
     * @param isDownloadRequestActive whether the download request state is active
     * @return the derived DownloadRequestState
     */
    public static DownloadRequestState deriveFromConnectionState(
        ConnectionState connectionState,
        boolean isDownloadRequestActive )
    {
        if ( isDownloadRequestActive )
        {
            return (DownloadRequestState) activeChanges.get( connectionState );

        }
        else
        {
            return (DownloadRequestState) passiveChanges.get( connectionState );

        }

    }

    /**
     * Create a DownloadRequestState with the given name
     * 
     * @param name Name to be used for this DownloadRequestState
     */
    private DownloadRequestState(
        String name )
    {
        super( name );

    }

}

/*******************************************************************************
 * $Log: DownloadRequestState.java,v $ Revision 1.11 2005/10/02 11:42:27 timowest updated sources and tests Revision
 * 1.10 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.9 2005/09/12 21:12:02 timowest added log block
 */
