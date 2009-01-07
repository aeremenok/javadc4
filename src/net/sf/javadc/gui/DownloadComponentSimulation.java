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

package net.sf.javadc.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IIncompletesLoader;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListenerBase;
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.mockups.BaseIncompletesLoader;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.RequestsModel;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.UploadRequest;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.client.ClientManager;
import net.sf.javadc.net.client.ConnectionFactory;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.net.hub.HubManager;
import net.sf.javadc.util.TaskManager;

/**
 * 
 * @author Timo Westk�mper
 */
public class DownloadComponentSimulation {

    private DownloadComponent downloadComponent;

    // external components
    private ITaskManager taskManager = new TaskManager();

    private ISettings settings = new BaseSettings(true);

    private IConnectionManager connectionManager = new ConnectionManager(
            settings);

    private IIncompletesLoader incompletesLoader = new BaseIncompletesLoader();

    private IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    private IClientManager clientManager = new ClientManager(settings,
            taskManager, connectionManager, clientTaskFactory);

    private IConnectionFactory connectionFactory = new ConnectionFactory(
            taskManager, connectionManager, clientManager, clientTaskFactory);

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(
            new HubManager(), segmentManager);

    // testable component
    private IRequestsModel model = new RequestsModel(settings, clientManager,
            connectionManager, incompletesLoader, downloadManager,
            segmentManager);

    /**
     * 
     */
    public DownloadComponentSimulation() {

        ((TaskManager) taskManager).start();

        downloadComponent = new DownloadComponent(taskManager, model);

        // adding a download connection with two download requests
        setupDownloadConnection();

        // adding a upload connection
        setupUploadConnection();

        JFrame downloadFrame = new JFrame();

        downloadFrame.getContentPane().add(downloadComponent);

        downloadFrame.setLocation(100, 100);
        downloadFrame.setSize(400, 300);

        downloadFrame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }

        });

        downloadFrame.setVisible(true);

        try {
            while (true) {
                Thread.sleep(10000);

            }

        } catch (InterruptedException e) {
            // logger.error(e.toString());
            e.printStackTrace();
        }

    }

    /**
     * 
     */
    protected void setupDownloadConnection() {
        // create a client
        // Client client1 = new Client(new HostInfo("www.gmx.de"), settings,
        // clientManager);

        Client client1 = clientManager.getClient(new HostInfo("www.gmx.de"));
        client1.setNick("tommy");

        // create a client connection
        IConnection conn1 = connectionFactory.createClientConnection(client1,
                new ConnectionListenerBase(), false);

        connectionManager.addConnection(conn1);

        // creates download requests

        DownloadRequest dr1 = new DownloadRequest(new SearchResult((IHub) null,
                "nick1", "test1", settings, 0), new File("test1"), settings);

        DownloadRequest dr2 = new DownloadRequest(new SearchResult((IHub) null,
                "nick2", "test2", settings, 0), new File("test2"), settings);

        DownloadRequest dr3 = new DownloadRequest(new SearchResult((IHub) null,
                "nick3", "test3", settings, 0), new File("test3"), settings);

        DownloadRequest dr4 = new DownloadRequest(new SearchResult((IHub) null,
                "nick4", "test4", settings, 0), new File("test4"), settings);

        // add download requests to client

        try {
            client1.addDownload(dr1);
            client1.addDownload(dr2);

            DownloadRequest drs[] = { dr3, dr4 };

            client1.addDownloads(drs);

        } catch (IOException e) {
            // System.out.println(e);
            // logger.error("Caught " + e.getClass().getName(), e);
            e.printStackTrace();

        }

    }

    /**
     * 
     */
    protected void setupUploadConnection() {
        // Client client2 = new Client(new HostInfo("www.uta.fi"), settings,
        // clientManager);

        Client client2 = clientManager.getClient(new HostInfo("www.uta.fi"));

        client2.setNick("Hubert");

        // create a client connection

        // create a client connection
        IConnection conn2 = connectionFactory.createClientConnection(client2,
                new ConnectionListenerBase(), false);

        conn2.getConnectionInfo().setCurrentDirection(
                ConstantSettings.UPLOAD_DIRECTION);

        conn2.setUploadRequest(new UploadRequest(new File("/tmp/test.txt")));

        connectionManager.addConnection(conn2);
    }

    /** ********************************************************************** */

    /*
     * @see TestCase#setUp()
     */
    public static void main(String[] args) {
        new DownloadComponentSimulation();

    }

}

/*******************************************************************************
 * $Log: DownloadComponentSimulation.java,v $
 * Revision 1.18  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.17 2005/09/14 07:11:49
 * timowest updated sources
 * 
 * 
 * 
 */
