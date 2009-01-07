/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: Client.java,v 1.34 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.DownloadRequestState;
import net.sf.javadc.net.InvalidArgumentException;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.FileUtils;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * <code>Client</code> represents a remote client with a related queue of downloads (list of
 * <code>DownloadRequests</code>) and a related Client <code>Connection</code>. <code>Client</code> instances are
 * managed via the <code>ClientManager</code>.
 * 
 * @author Timo Westk�mper
 */
public class Client
    extends AbstractClient
    implements
        IClient
{

    /** ********************************************************************** */
    private class MyConnectionListener
        implements
            ConnectionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionListener#disconnected(
         *      net.sf.javadc.interfaces.IConnection)
         */
        public void disconnected(
            IConnection connection )
        {
            Client.this.connection = null;

            // if the Connection was based on a local socket, we can't
            // reestablish the connection here
            if ( connection.isServer() )
            {
                fireDisconnected( new ArrayList( downloads ) );

            }
            else
            {
                try
                {
                    checkDownloads();

                }
                catch ( IOException e )
                {
                    // logger.error(e);
                    logger.error( "Caught " + e.getClass().getName(), e );
                }
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionListener#downloadComplete(net.sf.javadc.interfaces.IConnection)
         */
        public void downloadComplete(
            IConnection connection )
        {
            // removes the finished element from the list of downloads
            DownloadRequest completed = (DownloadRequest) downloads.remove( 0 );

            logger.debug( "DL complete " + completed.getSearchResult().getFilename() );

            // finished file is a file list of a user
            if ( FileUtils.getExtension( completed.getSearchResult().getFilename() ).indexOf( "DcLst" ) > -1 ) // bad
            {
                logger.debug( "Firing list complete" );
                fireBrowseListDownloaded( completed );

            }

            completed.setComplete( true );
            fireDownloadRemoved( completed );

            // disconnect of no more downloads are available
            if ( downloads.isEmpty() )
            {
                disconnect();
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionListener#downloadFailed(net.sf.javadc.interfaces.IConnection)
         */
        public void downloadFailed(
            IConnection connection )
        {
            removeDownload( 0 );

            // by not calling removeSuccessfulDownload this item will remain in
            // the queue
            if ( downloads.isEmpty() )
            {
                disconnect();
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionListener#stateChanged(
         *      net.sf.javadc.interfaces.IConnection)
         */
        public void stateChanged(
            IConnection connection )
        {
            try
            {
                checkDownloads();

            }
            catch ( IOException e )
            {
                // logger.error(e);
                logger.error( "Caught " + e.getClass().getName(), e );

            }

            updateDownloadRequestStates( connection );

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionListener#uploadComplete(net.sf.javadc.interfaces.IConnection)
         */
        public void uploadComplete(
            IConnection connection )
        {

            // TODO Auto-generated method stub
        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionListener#uploadFailed(net.sf.javadc.interfaces.IConnection)
         */
        public void uploadFailed(
            IConnection connection )
        {
            // TODO Auto-generated method stub
        }

    }

    private final static Category logger             = Logger.getLogger( Client.class );

    // members
    /**
     * 
     */
    private final List            downloads          = new ArrayList();

    /**
     * 
     */
    private IConnection           connection;

    /**
     * 
     */
    private String                nick               = "";

    /**
     * 
     */
    private boolean               bZSupport          = false;

    /**
     * 
     */
    private ConnectionListener    connectionListener = new MyConnectionListener();

    // private final IClientManager clientManager;

    // components
    /**
     * 
     */
    private final ISettings       settings;

    /**
     * Create a Client instance with the given HostInfo, ISettings instance and IClientManager
     * 
     * @param _host HostInfo to be used for the client
     * @param _settings ISettings instance to be used
     * @param _clientManager IClientManager instance to be used
     */
    public Client(
        HostInfo _host,
        ISettings _settings )
    {

        if ( _host == null )
        {
            throw new NullPointerException( "host was null." );
        }
        else if ( _settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }

        settings = _settings;
        // clientManager = _clientManager;
        setHost( _host );

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#addDownload(net.sf.javadc.net.DownloadRequest)
     */
    public final void addDownload(
        DownloadRequest dr )
        throws IOException
    {
        if ( !downloads.contains( dr ) )
        {
            logger.debug( "Adding download " + dr );

            // add the element to the list of download requests,
            // if it is not already in the list
            downloads.add( dr );
            fireDownloadAdded( dr );
            checkDownloads();

        }
        else
        {
            logger.debug( "Download " + dr + " was already in queue." );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#addDownloads(net.sf.javadc.net.DownloadRequest[])
     */
    public final void addDownloads(
        DownloadRequest[] drs )
        throws IOException
    {

        for ( int i = 0; i < drs.length; i++ )
        {
            if ( !downloads.contains( drs[i] ) )
            {
                downloads.add( drs[i] );
                fireDownloadAdded( drs[i] );
            }
        }

        checkDownloads();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#checkDownloads()
     */
    public final void checkDownloads()
        throws IOException
    {
        if ( !downloads.isEmpty() )
        { // downloads available

            // avoids debug messages (Already connected etc.)
            if ( connection == null )
            {
                connect();

            }

            // get first element and set it to be downloaded
            DownloadRequest downloadRequest = (DownloadRequest) downloads.get( 0 );

            // add the downloadRequest if a connection is available and the
            // downloadRequest has not failed
            if ( connection != null && !downloadRequest.isFailed() )
            {
                connection.setDownloadRequest( downloadRequest );

            }

        }
        else
        {
            logger.debug( "DownloadQueue was empty." );

        }

        // TODO : here we should handle DirectionChanging...If it's possible.
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#clientConnect()
     */
    public final void clientConnect()
        throws IOException
    {
        if ( connection == null )
        {
            fireConnectionRequested( false, connectionListener );
        }
        else
        {
            logger.debug( "Already connected." );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#connect()
     */
    public final void connect()
        throws IOException
    {
        if ( connection == null )
        { // connect if not connected
            // true, if host equals local IP and false, if not
            boolean isServer = getHost().getHost().compareTo( settings.getIP() ) == 0;

            fireConnectionRequested( isServer, connectionListener );

        }
        else
        {
            logger.debug( "Already connected." );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#getConnection()
     */
    public IConnection getConnection()
    {
        return connection;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#getDownload(int)
     */
    public final DownloadRequest getDownload(
        int index )
    {

        if ( index < downloads.size() )
        {
            return (DownloadRequest) downloads.get( index );

        }
        else
        {
            logger
                .error( "Error in DownloadRequest.getDownload(int index)\n" + "Index " + index + " was out of range." );
            return null;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#getDownloads()
     */
    public final DownloadRequest[] getDownloads()
    {
        return (DownloadRequest[]) downloads.toArray( new DownloadRequest[downloads.size()] );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#getLastDownloadInQueue()
     */
    public final DownloadRequest getLastDownloadInQueue()
    {
        if ( downloads.size() > 0 )
        {
            return (DownloadRequest) downloads.get( downloads.size() - 1 );

        }
        else
        {
            logger.debug( "No downloads in queue." );
            return null;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#getNick()
     */
    public final String getNick()
    {
        return nick;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#hasDownloads()
     */
    public final boolean hasDownloads()
    {
        return !downloads.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#isBZSupport()
     */
    public final boolean isBZSupport()
    {
        return bZSupport;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#removeAllDownloads()
     */
    public final void removeAllDownloads()
    {
        final int lastIndex = downloads.size() - 1;

        // removes downloads from last to first
        for ( int i = lastIndex; i >= 0; i-- )
        {
            removeDownload( i );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#removeDownload(net.sf.javadc.net.DownloadRequest)
     */
    public final void removeDownload(
        DownloadRequest dr )
    {
        int index = downloads.indexOf( dr );

        if ( index > -1 )
        {
            // removeDownload(downloads.indexOf(dr));
            removeDownload( index );

        }
        else
        {
            logger.error( "Download " + dr + " was not found in queue." );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#removeDownload(int)
     */
    public final void removeDownload(
        int index )
    {

        // index is out of range
        if ( index < 0 || index >= downloads.size() )
        {
            logger.error( "Error in DownloadRequest.removeDownload(int index)\n" + "Index " + index +
                " was out of range." );

            // index is in valid ranges
        }
        else
        {
            logger.info( "Removing DownloadRequest with index " + index + "." );

            DownloadRequest lastRemoved = (DownloadRequest) downloads.remove( index );

            if ( lastRemoved == null )
            {
                logger.error( "lastRemoved was null." );

            }
            else
            {
                logger.info( "DownloadRequest was successfully removed." );

            }

            // if the given index of the DownloadRequest is 0
            if ( index == 0 )
            {
                // if the removed DownloadRequest equals the DownloadRequest
                // of the Connection, the Connection continus an old request

                DownloadRequest dr = null;

                if ( connection != null )
                {
                    dr = connection.getDownloadRequest();

                }
                else
                {
                    logger.error( "connection was null in Client.removedDownload(int index)" );

                }

                // initiate a requestChange also when no DownloadRequest has
                // been
                // set for the ClientConnection
                boolean requestChange = true;

                if ( dr != null )
                {
                    requestChange = lastRemoved.equals( connection.getDownloadRequest() );

                }
                else
                {
                    logger.info( "DownloadRequest was not set for " + connection );

                }

                if ( requestChange )
                {
                    if ( hasDownloads() )
                    {
                        // ConnectionListener.stateChanged(Connection) will
                        // update the current ClientConnection

                        if ( connection != null )
                        {
                            connection.setState( ConnectionState.COMMAND_DOWNLOAD );
                        }

                    }
                    else
                    {
                        logger.info( "Closing connection to " + nick + " because no more downloads are left." );

                        // Because no downloads are left, the ClientConnection
                        // can be closed
                        if ( connection != null )
                        {
                            connection.setState( ConnectionState.ABORTED );
                        }

                        disconnect();

                    }

                }

            }

            lastRemoved.setComplete( false );
            fireDownloadRemoved( lastRemoved );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#serverConnect()
     */
    public final void serverConnect()
        throws IOException
    {
        if ( connection == null )
        {
            fireConnectionRequested( true, connectionListener );
        }
        else
        {
            logger.debug( "Already connected." );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#setBZSupport(boolean)
     */
    public final void setBZSupport(
        boolean b )
    {
        bZSupport = b;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#setConnection(net.sf.javadc.interfaces.IConnection)
     */
    public void setConnection(
        IConnection connection )
    {
        this.connection = connection;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#setNick(java.lang.String)
     */
    public final void setNick(
        String nick )
    {
        if ( nick == null )
        {
            throw new InvalidArgumentException( "nick was null." );
        }

        this.nick = nick;

        // inform registered listeners
        fireReceivedNick();

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.net.client.IClient#toString()
     */
    @Override
    public String toString()
    {

        return getNick() + " " + getHost();

    }

    /**
     * Closes the current ClientConnection and removes the ConnectionListener
     */
    private final void disconnect()
    {

        // disconnect the connection if this has not yet happened
        if ( connection != null )
        {
            connection.removeListener( connectionListener );
            connection.disconnect();
            connection = null;
        }

        fireDisconnected( new ArrayList( downloads ) );

    }

    /**
     * Update the states of the DownloadRequests based on the state of the given IConnection instance
     * 
     * @param connection IConnection whose state is used to determine the states of the DownloadRequests of this Client
     */
    private final void updateDownloadRequestStates(
        IConnection connection )
    {
        DownloadRequest connDownloadRequest = connection.getDownloadRequest();

        // goes through the DownloadRequests in the list and notifies them
        // about the state changes of the ClientConnection
        // seems easier than binding each DownloadRequest as a listener to a
        // connection

        if ( connDownloadRequest != null )
        {
            ConnectionState connState = connection.getState();

            for ( int i = 0; i < downloads.size(); i++ )
            {
                DownloadRequest dr = (DownloadRequest) downloads.get( i );

                DownloadRequestState newState =
                    DownloadRequestState.deriveFromConnectionState( connState, connDownloadRequest.equals( dr ) );

                // set new state if derived state was defined
                if ( newState != null )
                {
                    dr.setState( DownloadRequestState.deriveFromConnectionState( connState, connDownloadRequest
                        .equals( dr ) ) );

                }

            }

        }

    }

}

/*******************************************************************************
 * $Log: Client.java,v $ Revision 1.34 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.33 2005/09/30
 * 15:59:53 timowest updated sources and tests Revision 1.32 2005/09/26 17:19:53 timowest updated sources and tests
 * Revision 1.31 2005/09/25 16:40:59 timowest updated sources and tests Revision 1.30 2005/09/14 07:11:50 timowest
 * updated sources Revision 1.29 2005/09/12 21:12:02 timowest added log block
 */
