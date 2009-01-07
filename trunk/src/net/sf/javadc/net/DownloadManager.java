/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.net;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.net.hub.HubUser;

import org.apache.log4j.Category;

/**
 * <code>DownloadManager</code> is a component which facilitates the starting and resuming of downloads via
 * <code>DownloadRequest</code> instances.
 * 
 * @author Timo Westk�mper
 */
public class DownloadManager
    implements
        IDownloadManager
{

    /** ********************************************************************** */

    private class FlushDownloadsAction
        implements
            ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(
            ActionEvent e )
        {
            requestNextDownload();

        }
    }

    // external
    private static final Category logger        = Category.getInstance( DownloadManager.class );

    /**
     * 
     */
    private final IHubManager     hubManager;

    // internal

    /**
     * 
     */
    private final ISegmentManager segmentManager;

    /**
     * 
     */
    private javax.swing.Timer     flushTimer;

    /**
     * 
     */
    private final List            downloadQueue = new ArrayList();

    /**
     * 
     */
    private boolean               running       = false;

    /**
     * Create a DownloadManager instance
     * 
     * @param hubManager IHubManager instance to be used
     * @param segmentManager TODO
     */
    public DownloadManager(
        IHubManager hubManager,
        ISegmentManager segmentManager )
    {

        if ( hubManager == null )
        {
            throw new NullPointerException( "hubManager was null." );
        }
        else if ( segmentManager == null )
        {
            throw new NullPointerException( "segmentManager was null." );
        }

        this.hubManager = hubManager;
        this.segmentManager = segmentManager;

        // flushDownloadQueue();
    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IDownloadManager#flushDownloadQueue()
     */
    public final void flushDownloadQueue()
    {

        // timer is used here to give the client time to fully connect to the
        // hub and not flood the hub with requests

        if ( flushTimer == null )
        {

            logger.debug( "Creating FlushTimer with intial delay " + ConnectionSettings.HUB_FLUSHDOWNLOADQUEUE_DELAY +
                " ms and interval " + ConnectionSettings.HUB_FLUSHDOWNLOADQUEUE_INTERVAL );

            flushTimer =
                new javax.swing.Timer( ConnectionSettings.HUB_FLUSHDOWNLOADQUEUE_INTERVAL, new FlushDownloadsAction() );

            flushTimer.setRepeats( true );
            flushTimer.setInitialDelay( ConnectionSettings.HUB_FLUSHDOWNLOADQUEUE_DELAY );

        }

        // start the flushTimer only, if it is not yet running

        if ( !running )
        {
            logger.debug( "Starting FlushTimer" );
            running = true;

            flushTimer.start();

        }

    }

    /**
     * Get the download queue as a list of DownloadRequests
     * 
     * @return Returns the downloadQueue.
     */
    public List getDownloadQueue()
    {
        return downloadQueue;
    }

    /**
     * Obtain the Hub referenced in the given SearchResult instance
     * 
     * @param sr SearchResult instance from which the Hub is to be obtained
     * @return
     */
    public IHub obtainHub(
        SearchResult sr )
    {
        logger.debug( "Trying to retrieve Hub " + sr.getHub() + " from HubManager." );

        IHub hub = hubManager.getHub( sr.getHub() );

        // client is not yet connected to Hub
        if ( hub == null )
        {
            logger.debug( "Using Hub instance defined in SearchResult." );

            hub = sr.getHub();
            // method is called in the connect method of Hub
            // hubManager.addHub(hub);

            logger.debug( "Trying to establish a Hub Connection." );
            hub.connect();
        }

        return hub;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IDownloadManager#removeDownload(net.sf.javadc.net.DownloadRequest)
     */
    public void removeDownload(
        DownloadRequest dr )
    {
        logger.debug( "Removing DownloadRequest " + dr + " from queue." );
        downloadQueue.remove( dr );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IDownloadManager#requestDownload(net.sf.javadc.net.DownloadRequest)
     */
    public void requestDownload(
        DownloadRequest dr )
    {

        DownloadRequest seg = segmentManager.getNextSegment( dr );

        // if the DownloadRequest is not segmented and if includes hashing
        // information it is replaced with a segmented download
        if ( seg != null )
        {
            dr = seg;

        }
        else
        {
            logger.debug( "Normal download for " + dr );

        }

        if ( dr.isFailed() )
        {
            logger.info( "DownloadRequest " + dr + " has failed and can't be downloaded " + "anymore in this session." );
            return;
        }

        // add item only if not yet included
        if ( !downloadQueue.contains( dr ) )
        {
            logger.debug( "Adding download " + dr );

            // adds the element to the first position
            downloadQueue.add( 0, dr );

            flushDownloadQueue();

            // item was already available in list
        }
        else
        {
            logger.error( "DownloadRequest " + dr + " was already contained in " + "the download queue." );

        }

    }

    /**
     * <p>
     * Requests the next DownloadRequest via a Client obtained from a related Hub instance.
     * </p>
     * <p>
     * This method should not be called directly. Instead it should be used via the FlushDownloadsAction.
     * </p>
     */
    protected void requestNextDownload()
    {

        // stop the timer if the download queue is empty

        if ( downloadQueue.isEmpty() )
        {
            flushTimer.stop();
            running = false;
            return;
        }

        // if download queue is not empty
        // if (!downloadQueue.isEmpty()) {
        // requests the first DownloadRequest in the queue
        int index = 0;

        DownloadRequest dr = (DownloadRequest) downloadQueue.remove( index );

        if ( dr.isFailed() )
        {
            logger.info( "DownloadRequest " + dr + " has failed in a previous " + " attempt to download it." );

            return;
        }

        // try to fetch a reference to the user this DownloadRequest is
        // bound to from the related Hub
        try
        {
            SearchResult sr = dr.getSearchResult();

            IHub hub = obtainHub( sr );
            HubUser user = (HubUser) hub.getUsers().get( sr.getNick() );

            // if related user could be found in related hub
            if ( user != null )
            {
                user.addDownload( dr );

                // if user could not be found in hub (user is offline)
            }
            else
            {
                logger.info( "User " + sr.getNick() + " could not be found in " + hub );

                downloadQueue.add( dr );
            }

        }
        catch ( IOException io )
        {
            logger.error( "Catched IOException when trying to request " + dr );
            logger.error( io );

        }
        catch ( Exception e )
        {
            logger.error( "Catched " + e.getClass().getName() + " when trying to request " + dr );
            logger.error( e );

        }

        // downloadQueue.remove(index);

        // if download queue is empty

    }

}

/*******************************************************************************
 * $Log: DownloadManager.java,v $ Revision 1.21 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.20
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.19 2005/09/12 21:12:02 timowest added log block
 */
