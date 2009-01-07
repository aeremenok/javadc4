/*
 * Copyright (C) 2004 Timo Westkämper
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IIncompletesLoader;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.ClientListener;
import net.sf.javadc.listeners.ClientListenerBase;
import net.sf.javadc.listeners.ClientManagerListener;
import net.sf.javadc.listeners.ClientManagerListenerBase;
import net.sf.javadc.listeners.ConnectionManagerListener;
import net.sf.javadc.listeners.DownloadRequestListener;
import net.sf.javadc.listeners.SegmentManagerListener;
import net.sf.javadc.net.client.Client;

import org.apache.log4j.Category;
import org.picocontainer.Startable;

import spin.Spin;

/**
 * <CODE>RequestsModel</CODE> represents a wrapper model on top of the <CODE>
 * ClientManager</CODE> and <CODE>ConnectionManager</CODE> which provides
 * services for higher level Swing models
 * 
 * @author Timo Westk�mper
 */
public class RequestsModel extends AbstractRequestsModel implements Startable,
        IRequestsModel {

    private final static Category logger = Category
            .getInstance(RequestsModel.class);

    // listeners
    /**
     * 
     */
    private ClientListener clientListener = new MyClientListener();

    /**
     * 
     */
    private ClientManagerListener clientManagerListener = new MyClientManagerListener();

    /**
     * 
     */
    private ConnectionManagerListener connectionManagerListener = new MyConnectionManagerListener();

    /**
     * 
     */
    private DownloadRequestListener downloadRequestListener = new MyDownloadRequestListener();

    /**
     * 
     */
    private SegmentManagerListener segmentManagerListener = new MySegmentManagerListener();

    // members
    /**
     * 
     */
    private List allDownloads = new ArrayList();

    /**
     * 
     */
    private List activeConnections = new ArrayList();

    /**
     * 
     */
    private Map requests2Clients = new HashMap();

    // external components
    /**
     * 
     */
    private final IClientManager clientManager;

    /**
     * 
     */
    private final IConnectionManager connectionManager;

    /**
     * 
     */
    private final IIncompletesLoader incompletesLoader;

    /**
     * 
     */
    private final IDownloadManager downloadManager;

    /**
     * 
     */
    private final ISegmentManager segmentManager;

    /**
     * Create a RequestsModel with the given IClientManager instance,
     * IConnectionManager instance, IIncompletesLoader instance and
     * IDownloadRequestResumer
     * 
     * @param _settings
     *            Settings instance to be used
     * @param _clientManager
     *            IClientManager instance to be used
     * @param _connectionManager
     *            IConnectionManager instance to be used
     * @param _incompletesLoader
     *            IIncompletesLoader instance to be used
     * @param _downloadManager
     *            IDownloadManager instance to be used
     */
    public RequestsModel(ISettings _settings, IClientManager _clientManager,
            IConnectionManager _connectionManager,
            IIncompletesLoader _incompletesLoader,
            IDownloadManager _downloadManager, ISegmentManager _segmentManager) {

        if (_settings == null)
            throw new NullPointerException("_settings was null.");
        else if (_clientManager == null)
            throw new NullPointerException("_clientManager was null.");
        else if (_connectionManager == null)
            throw new NullPointerException("_connectionManager was null.");
        else if (_incompletesLoader == null)
            throw new NullPointerException("_incompletesLoader was null.");
        else if (_downloadManager == null)
            throw new NullPointerException("_downloadManager was null.");
        else if (_segmentManager == null)
            throw new NullPointerException("_segmentManager was null.");

        // external thread
        clientManager = (IClientManager) Spin.off(_clientManager);
        clientManager.addListener((ClientManagerListener) Spin
                .over(clientManagerListener));

        // external thread
        connectionManager = (IConnectionManager) Spin.off(_connectionManager);
        connectionManager.addListener((ConnectionManagerListener) Spin
                .over(connectionManagerListener));

        // used in same thread (Swing thread)
        incompletesLoader = _incompletesLoader;

        // used to notify the DownloadManager, when DownloadRequests are removed
        // addListener(_downloadManager.getRequestsModelListener());

        downloadManager = (IDownloadManager) Spin.off(_downloadManager);

        segmentManager = (ISegmentManager) Spin.off(_segmentManager);

        segmentManager.addListener((SegmentManagerListener) Spin
                .over(segmentManagerListener));

        // a RequestsModelCleanupListener instance removes temporary files that
        // have been created for the download purpose
        addListener(new RequestsModelCleanupListener(_settings));
    }

    /** ********************************************************************** */

    /**
     * Checks whether the given DownloadRequest is already in the model
     * 
     * @param dr
     *            DownloadRequest to be checked
     * 
     * @return returns true, if already available and false, if not
     */
    private final boolean isDuplicate(DownloadRequest dr) {
        return allDownloads.contains(dr);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public void start() {
        logger.debug("starting " + this.getClass().getName());

        // System.out.println();
        // System.out.println("starting " + this.getClass().getName());
        // System.out
        // .println("====================================================");

        logger.debug("Loading incomplete downloads.");

        List requests = incompletesLoader.load();

        // iterate over the all the downloads in the incompletes list

        for (Iterator i = requests.iterator(); i.hasNext();) {
            DownloadRequest dr = (DownloadRequest) i.next();

            DownloadRequest seg = segmentManager.getNextSegment(dr);

            // if the DownloadRequest is not segmented and if includes hashing
            // information it is replaced with a segmented download
            if (seg != null) {
                dr = seg;

            } else {
                logger.debug("Normal download for " + dr);
            }

            allDownloads.add(dr);
            downloadManager.requestDownload(dr);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public void stop() {

        logger.debug("stopping " + this.getClass().getName());

        // System.out.println();
        // System.out.println("stopping " + this.getClass().getName());
        // System.out
        // .println("====================================================");

        logger.debug("Saving incomplete downloads.");

        incompletesLoader.save(allDownloads);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#removeDownloadRequest(net.sf.javadc.net.DownloadRequest)
     */
    public void removeDownloadRequest(DownloadRequest dr) {
        // used directly by
        // remove it from
        int index = allDownloads.indexOf(dr);

        allDownloads.remove(dr);
        downloadManager.removeDownload(dr);

        IClient c = (IClient) requests2Clients.get(dr);

        if (c != null) {
            logger.debug("Removing download request from client.");

            // downloadRequest is active
            c.removeDownload(dr);

        } else {
            logger.warn("Client could not be derived from DownloadRequest.");

            // make feedback available, if Client is not active
            fireRequestRemoved(c, dr, index);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#getActiveConnections()
     */
    public List getActiveConnections() {
        return activeConnections;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IRequestsModel#getAllDownloads()
     */
    public List getAllDownloads() {
        return allDownloads;

    }

    /** ********************************************************************** */
    private class MyClientListener extends ClientListenerBase {

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ClientListener#downloadAdded(net.sf.javadc.net.client.Client,
         *      net.sf.javadc.net.DownloadRequest)
         */
        public final void downloadAdded(Client client, DownloadRequest dr) {
            logger.debug("-- downloadAdded " + client + " " + dr);

            dr.addListener((DownloadRequestListener) Spin
                    .over(downloadRequestListener));

            dr.getSearchResult().setHost(client.getHost());
            requests2Clients.put(dr, (IClient) Spin.off(client));

            if (!isDuplicate(dr)) {
                allDownloads.add(dr);

                // activeDownloads.add(dr);
                int index = allDownloads.size() - 1;

                fireRequestAdded(client, dr, index);

            } else {
                logger.warn("DownloadRequest " + dr
                        + " has already been added.");

            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ClientListener#downloadRemoved(net.sf.javadc.net.client.Client,
         *      net.sf.javadc.net.DownloadRequest)
         */
        public final void downloadRemoved(IClient client, DownloadRequest dr) {
            logger.debug("-- downloadRemoved " + client + " " + dr);

            int index = -1;

            dr.removeListener(downloadRequestListener);

            // activeDownloads.remove(dr);
            logger.debug("Removing request from Client.");
            requests2Clients.remove(dr);

            if (dr.isComplete()) {
                // remove it only from the list used by IncompleteComponent, if
                // the DownloadRequest has finished
                index = allDownloads.indexOf(dr);

                allDownloads.remove(dr);

            }

            fireRequestRemoved(client, dr, index);

        }

    }

    private class MyClientManagerListener extends ClientManagerListenerBase {

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ClientManagerListener#clientAdded(net.sf.javadc.net.client.Client)
         */
        public final void clientAdded(final Client client) {
            logger.debug("-- clientAdded " + client);

            client.addListener((ClientListener) Spin.over(clientListener));

        }

    }

    private class MyConnectionManagerListener implements
            ConnectionManagerListener {

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionManagerListener#clientConnectionAdded(net.sf.javadc.interfaces.IConnection)
         */
        public final void clientConnectionAdded(final IConnection connection) {
            logger.debug("-- clientConnectionAdded " + connection);

            // doesn't allow to add not connected connections

            /*
             * if (connection.getState().equals(ConnectionState.NOT_CONNECTED)){
             * logger.error("Connection was not active.");
             * 
             * }else{
             */
            if (!activeConnections.contains(connection)) {
                activeConnections.add(connection);

                // int index = activeConnections.size() -1;
                int index = activeConnections.indexOf(connection);

                if (index > -1) {
                    fireConnectionAdded(connection, index);

                } else {
                    logger.error("Connection not available.");

                }

            }

            // }
        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionManagerListener#clientConnectionRemoved(net.sf.javadc.interfaces.IConnection)
         */
        public final void clientConnectionRemoved(final IConnection connection) {
            logger.debug("-- clientConnectionRemoved " + connection);

            int index = activeConnections.indexOf(connection);

            if (index > -1) {
                activeConnections.remove(connection);
                fireConnectionRemoved(connection, index);

            } else {
                logger.error("Connection not available.");

            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionManagerListener#clientConnectionStateChanged(net.sf.javadc.interfaces.IConnection)
         */
        public void clientConnectionStateChanged(IConnection connection) {
            logger.debug("-- clientConnectionStateChanged " + connection);

            int index = activeConnections.indexOf(connection);

            if (index > -1) {
                // notifies connection removed event for inactive connections

                fireConnectionChanged(connection, index);

                // }
            } else {
                logger.error("Connection not available.");

            }

        }

    }

    private class MyDownloadRequestListener implements DownloadRequestListener {

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.DownloadRequestListener#downloadRequestStateChanged(net.sf.javadc.net.DownloadRequest)
         */
        public void downloadRequestStateChanged(DownloadRequest downloadRequest) {
            logger.debug("-- downloadRequestStateChanged " + downloadRequest);

            IClient client = (IClient) requests2Clients.get(downloadRequest);

            int index = allDownloads.indexOf(downloadRequest);

            fireRequestChanged(client, downloadRequest, index);

        }

    }

    private class MySegmentManagerListener implements SegmentManagerListener {

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.SegmentManagerListener#dropDownload(net.sf.javadc.net.DownloadRequest)
         */
        public void dropDownload(DownloadRequest dr) {
            logger.debug("-- dropping download " + dr);

            removeDownloadRequest(dr);

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.SegmentManagerListener#startDownload(net.sf.javadc.net.DownloadRequest)
         */
        public void startDownload(DownloadRequest dr) {
            logger.debug("-- starting download " + dr);

            downloadManager.requestDownload(dr);

        }

    }

}

/*******************************************************************************
 * $Log: RequestsModel.java,v $
 * Revision 1.14  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.13 2005/09/30 15:59:53 timowest
 * updated sources and tests
 * 
 * Revision 1.12 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.11 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
