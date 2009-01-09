/*
 * Copyright (C) 2001 Stefan G�rling, stefan@gorling.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: HubUser.java,v 1.18 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.hub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import net.sf.javadc.config.UserInfo;
import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.listeners.ClientListener;
import net.sf.javadc.listeners.ClientListenerBase;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.InvalidArgumentException;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.client.Client;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * <code>HubUser</code> represents user information about a single remote client connected to a <code>Hub</code> the
 * local client is also connected to.
 */
public class HubUser
    extends UserInfo
{

    private class MyClientListener
        extends ClientListenerBase
    {

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ClientListener#disconnected()
         */
        @Override
        public void disconnected(
            List<DownloadRequest> downloads1 )
        {
            if ( client == null )
            {
                logger.debug( "Client was not set" );
                return;
            }

            client.removeListener( clientListener );

            // set client to null
            client = null;

            // no more downloads available
            if ( downloads1.isEmpty() )
            {
                // still downloads available

                logger.info( "No more downloads available" );
                return;
            }

            HubUser.this.downloads.addAll( downloads1 );
            requestConnection( downloads1.get( 0 ).getSearchResult() );
        }
    }

    private final static Category                 logger         = Logger.getLogger( HubUser.class );
    private transient int                         ipTries        = 0;

    private final transient List<DownloadRequest> downloads      = new ArrayList<DownloadRequest>();

    // ClientListener
    private transient ClientListener              clientListener = new MyClientListener();

    // external
    private transient Client                      client;
    private final IHub                            hub;

    /**
     * Creates a <CODE>HubUser</CODE> instance for the given <CODE>IHub
     * </CODE>
     */
    public HubUser(
        IHub hub )
    {
        Assert.assertNotNull( hub );
        this.hub = hub;
    }

    /**
     * Add the given DownloadRequest to the related Client or the internal queue
     * 
     * @param dr DownloadRequest instance to be added
     * @throws IOException
     */
    public final void addDownload(
        DownloadRequest dr )
        throws IOException
    {
        Assert.assertNotNull( dr );
        if ( client != null )
        {
            client.addDownload( dr );
        }
        else
        {
            // add the given DownloadRequest to the queue of downloads which
            // will be given to the Client, when a connection has been
            // established
            downloads.add( dr );

            // request a connection to the remote client
            hub.requestConnection( dr.getSearchResult().getNick() );
        }
    }

    /**
     * Get the associated Client
     * 
     * @return
     */
    public final IClient getClient()
    {
        return client;

    }

    /**
     * Get the list of downloads
     * 
     * @return Returns the downloads.
     */
    public List<DownloadRequest> getDownloads()
    {
        return downloads;
    }

    /**
     * Get IP tries
     * 
     * @return
     */
    public final int getIpTries()
    {
        return ipTries;

    }

    /**
     * Set the associated Client
     * 
     * @param c
     */
    public final void setClient(
        Client _client )
    {
        if ( _client == null )
        {
            throw new InvalidArgumentException( "Client was null." );
        }

        client = _client;
        client.addListener( clientListener );

        // set the nick of the client, if it is empty
        if ( client.getNick().equals( "" ) )
        {
            client.setNick( getNick() );
        }

        if ( !downloads.isEmpty() )
        {
            try
            {
                logger.debug( "Moving download queue to Client instance." );

                // add downloads to client
                client.addDownloads( downloads.toArray( new DownloadRequest[downloads.size()] ) );

                downloads.clear(); // clear downloads

            }
            catch ( IOException e )
            {
                logger.error( "Download queue could not be moved." );
                logger.error( e );
            }

        }
        else
        {
            logger.debug( "There were no download to be moved " + "to the Client instance" );

        }

    }

    /**
     * Set IP tries
     * 
     * @param ipTries
     */
    public final void setIpTries(
        int ipTries )
    {
        this.ipTries = ipTries;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString()
    {
        // return MessageRenderer.getInstance().toString(this);
        return "$ALL " + getNick() + " " + getDescription() + getTag() + "$ $" + getSpeed() + getSpeedCode() + "$" +
            getEmail() + "$" + getSharedSize() + "$";
    }

    /**
     * Request a new Connection to download the given SearchResult
     * <p>
     * For active connections the request has been done via the Hub
     * </p>
     * 
     * @param sr
     */
    private void requestConnection(
        final SearchResult sr )
    {
        // request a connection from the Hub

        if ( client != null )
        {
            logger.info( "Requesting a new connection to " + client.getNick() );
        }
        else
        {
            logger.info( "Requesting a new connection" );
        }

        new Thread( new Runnable()
        {

            public void run()
            {
                try
                {
                    // postpone the request for 1s
                    Thread.sleep( 1000 );
                }
                catch ( InterruptedException e1 )
                {
                    logger.error( "Caught " + e1.getClass().getName(), e1 );
                }

                try
                {
                    hub.requestConnection( sr.getNick() );

                }
                catch ( IOException e )
                {
                    logger.error( "Caught " + e.getClass().getName(), e );
                }

            }

        } ).start();

    }

}

/*******************************************************************************
 * $Log: HubUser.java,v $ Revision 1.18 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.17 2005/09/30
 * 15:59:53 timowest updated sources and tests Revision 1.16 2005/09/26 17:19:52 timowest updated sources and tests
 * Revision 1.15 2005/09/25 16:40:58 timowest updated sources and tests Revision 1.14 2005/09/12 21:12:02 timowest added
 * log block
 */
