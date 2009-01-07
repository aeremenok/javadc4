/*
 * Created on 18.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.client;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListenerBase;
import net.sf.javadc.listeners.ConnectionManagerListener;
import net.sf.javadc.mockups.BaseClientManager;
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class ConnectionManagerTest extends TestCase {

    private IConnectionManager connectionManager;

    private IConnectionFactory connectionFactory;

    // external components
    // private final ISettings settings = new BaseSettings(true);
    private final ISettings settings = new Settings();

    private final ITaskManager taskManager = new TaskManager();

    private final IClientManager clientManager = new BaseClientManager();

    private final IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    /**
     * Constructor for ConnectionManagerTest.
     * 
     * @param arg0
     */
    public ConnectionManagerTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        connectionManager = new ConnectionManager(settings);

        connectionFactory = new ConnectionFactory(taskManager,
                connectionManager, clientManager, clientTaskFactory);

    }

    public void testAdding() throws Exception {
        Client client1 = new Client(new HostInfo("www.gmx.de"), settings);

        IConnection connection1 = connectionFactory.createClientConnection(
                client1, new ConnectionListenerBase(), false);

        // ensure that the created connection is not null
        assertEquals(connection1 != null, true);

        Client client2 = new Client(new HostInfo("www.uta.fi"), settings);

        IConnection connection2 = connectionFactory.createClientConnection(
                client2, new ConnectionListenerBase(), false);

        // ensure that the created connection is not null
        assertEquals(connection2 != null, true);

        Thread.sleep(300);

        // ensure that two connections have been added to the ConnectionManager
        assertEquals(connectionManager.getClientConnections().length, 2);

        connectionManager.addConnection(connection2);

        // connection count doesn't increate
        assertEquals(connectionManager.getClientConnections().length, 2);

    }

    public void testStop() throws Exception {
        MyConnectionManagerListener listener = new MyConnectionManagerListener();
        connectionManager.addListener(listener);

        Client client1 = new Client(new HostInfo("www.gmx.de"), settings);
        connectionFactory.createClientConnection(client1,
                new ConnectionListenerBase(), false);

        Client client2 = new Client(new HostInfo("www.uta.fi"), settings);
        connectionFactory.createClientConnection(client2,
                new ConnectionListenerBase(), false);

        Thread.sleep(300);

        assertEquals(listener.additions, 2);

        ((ConnectionManager) connectionManager).stop();

        // assertEquals(listener.removals, 2);
    }

    public void testSlotReservation() throws Exception {
        int dSlots = settings.getFreeDownloadSlotCount();
        int uSlots = settings.getFreeUploadSlotCount();

        Client client1 = new Client(new HostInfo("www.gmx.de"), settings);

        IConnection connection1 = connectionFactory.createClientConnection(
                client1, new ConnectionListenerBase(), false);

        connectionManager.addConnection(connection1);

        // test download slot reservation

        connection1.setState(ConnectionState.DOWNLOADING);
        assertEquals(settings.getFreeDownloadSlotCount(), dSlots - 1);

        // and no change after refreshing state

        connection1.setState(ConnectionState.DOWNLOADING);
        assertEquals(settings.getFreeDownloadSlotCount(), dSlots - 1);

        // and release when switching off from downloading

        connection1.setState(ConnectionState.WAITING);
        assertEquals(settings.getFreeDownloadSlotCount(), dSlots);

        Client client2 = new Client(new HostInfo("www.uta.fi"), settings);

        IConnection connection2 = connectionFactory.createClientConnection(
                client2, new ConnectionListenerBase(), false);

        connectionManager.addConnection(connection2);

        Client client3 = new Client(new HostInfo("www.utu.fi"), settings);

        IConnection connection3 = connectionFactory.createClientConnection(
                client3, new ConnectionListenerBase(), false);

        connectionManager.addConnection(connection3);

        // test upload slot reservation

        connection2.setState(ConnectionState.UPLOADING);
        assertEquals(settings.getFreeUploadSlotCount(), uSlots - 1);

        // and no change after refreshing state

        connection2.setState(ConnectionState.UPLOADING);
        assertEquals(settings.getFreeUploadSlotCount(), uSlots - 1);

        // test upload slot reservation with another UploadConnection

        connection3.setState(ConnectionState.UPLOADING);
        assertEquals(settings.getFreeUploadSlotCount(), uSlots - 2);

        // and release when switching off from uploading

        connection2.setState(ConnectionState.ABORTED);
        assertEquals(settings.getFreeUploadSlotCount(), uSlots - 1);

        connection3.setState(ConnectionState.NOT_CONNECTED);
        assertEquals(settings.getFreeUploadSlotCount(), uSlots);
    }

    private class MyConnectionManagerListener implements
            ConnectionManagerListener {

        public int additions = 0;

        public int removals = 0;

        public int stateChanges = 0;

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionManagerListener#clientConnectionAdded(net.sf.javadc.interfaces.IConnection)
         */
        public void clientConnectionAdded(IConnection connection) {
            additions++;

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionManagerListener#clientConnectionRemoved(net.sf.javadc.interfaces.IConnection)
         */
        public void clientConnectionRemoved(IConnection connection) {
            removals++;

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ConnectionManagerListener#clientConnectionStateChanged(net.sf.javadc.interfaces.IConnection)
         */
        public void clientConnectionStateChanged(IConnection connection) {
            stateChanges++;

        }

    }
}