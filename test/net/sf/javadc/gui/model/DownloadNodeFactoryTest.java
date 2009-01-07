/*
 * Created on 25.10.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package net.sf.javadc.gui.model;

import java.io.File;

import junit.framework.TestCase;
import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListenerBase;
import net.sf.javadc.mockups.BaseClientManager;
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.UploadRequest;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.client.ConnectionFactory;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DownloadNodeFactoryTest extends TestCase {

    private DownloadNodeFactory downloadNodeFactory;

    private ConnectionFactory connectionFactory;

    // external components
    private final ISettings settings = new BaseSettings(true);

    private final ITaskManager taskManager = new TaskManager();

    private final IConnectionManager connectionManager = new ConnectionManager(
            settings);

    private final IClientManager clientManager = new BaseClientManager();

    private final IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        downloadNodeFactory = new DownloadNodeFactory();

        connectionFactory = new ConnectionFactory(taskManager,
                connectionManager, clientManager, clientTaskFactory);
    }

    /**
     * Constructor for DownloadNodeFactoryTest.
     * 
     * @param arg0
     */
    public DownloadNodeFactoryTest(String arg0) {
        super(arg0);
    }

    public void testCreateDownloadConnectionNode() throws Exception {

        Client client1 = new Client(new HostInfo("www.gmx.de"), settings);
        client1.setNick("tommy");

        IConnection conn1 = connectionFactory.createClientConnection(client1,
                new ConnectionListenerBase(), false);

        // ensure that initially no connections have been cached
        assertEquals(downloadNodeFactory.getConnectionNodes().values().size(),
                0);

        IDownloadNode node = downloadNodeFactory.createConnectionNode(conn1);
        assertTrue(node instanceof DownConnectionNode);
        assertTrue(node.isValid());

        // ensure that the node is download connection node
        // assertEquals(node.getType(), DownloadNode.DOWNLOAD_CONNECTION_NODE);

        // ensure that one node has been stored in the cache
        assertEquals(downloadNodeFactory.getConnectionNodes().values().size(),
                1);

        // ensure that the node can be queried via the nick of the client
        assertTrue(downloadNodeFactory.getConnectionNodes().get("tommy") == node);

        // ensure that no new node is created when a download node for the same
        // connection is queried

        assertTrue(downloadNodeFactory.createConnectionNode(conn1) == node);

        assertEquals(downloadNodeFactory.getConnectionNodes().values().size(),
                1);

        conn1.setState(ConnectionState.NOT_CONNECTED);
        assertFalse(node.isValid());
    }

    public void testCreateUploadConnectionNode() throws Exception {
        testCreateDownloadConnectionNode();

        Client client2 = new Client(new HostInfo("www.uta.fi"), settings);
        client2.setNick("Hubert");

        IConnection conn2 = connectionFactory.createClientConnection(client2,
                new ConnectionListenerBase(), false);

        conn2.getConnectionInfo().setCurrentDirection(
                ConstantSettings.UPLOAD_DIRECTION);

        IDownloadNode node2 = downloadNodeFactory.createConnectionNode(conn2);
        assertTrue(node2 instanceof DownConnectionNode);

        // assertEquals(node2.getType(), DownloadNode.UPLOAD_CONNECTION_NODE);

        assertEquals(downloadNodeFactory.getConnectionNodes().values().size(),
                2);

        assertTrue(downloadNodeFactory.getConnectionNodes().get("Hubert") == node2);

        assertTrue(downloadNodeFactory.createConnectionNode(conn2) == node2);

        assertEquals(downloadNodeFactory.getConnectionNodes().values().size(),
                2);

    }

    public void testCreateDownloadRequestNode() throws Exception {

        // create a client
        Client client1 = new Client(new HostInfo("www.gmx.de"), settings);
        client1.setNick("tommy");

        // create a client connection
        IConnection conn1 = connectionFactory.createClientConnection(client1,
                new ConnectionListenerBase(), false);

        // create two download requests
        DownloadRequest dr1 = new DownloadRequest(new SearchResult((IHub) null,
                "nick1", "test1", settings, 0), new File("test1"), settings);

        client1.addDownload(dr1);

        DownloadRequest dr2 = new DownloadRequest(new SearchResult((IHub) null,
                "nick2", "test2", settings, 0), new File("test2"), settings);

        client1.addDownload(dr2);

        // create the related download nodes
        IDownloadNode node1 = downloadNodeFactory.createDownloadRequestNode(
                conn1, dr1);

        assertTrue(node1 instanceof DownDownloadRequestNode);
        // assertEquals(node1.getType(), DownloadNode.DOWNLOAD_REQUEST_NODE);

        IDownloadNode node2 = downloadNodeFactory.createDownloadRequestNode(
                conn1, dr2);

        assertTrue(node2 instanceof DownDownloadRequestNode);
        // assertEquals(node2.getType(), DownloadNode.DOWNLOAD_REQUEST_NODE);

        // ensure that two nodes have been cached

        assertEquals(downloadNodeFactory.getDownloadRequestNodes().values()
                .size(), 2);

        assertTrue(downloadNodeFactory.createDownloadRequestNode(conn1, dr1) == node1);

        // ensure that additional querying uses cached version

        assertEquals(downloadNodeFactory.getDownloadRequestNodes().values()
                .size(), 2);

        // ensure that the node can be queried via the search result's file name

        assertEquals(
                downloadNodeFactory.getDownloadRequestNodes().get("test1"),
                node1);
    }

    public void testCreateUploadRequestNode() throws Exception {
        // create a client
        Client client1 = new Client(new HostInfo("www.gmx.de"), settings);
        client1.setNick("tommy");

        // create a client connection
        IConnection conn1 = connectionFactory.createClientConnection(client1,
                new ConnectionListenerBase(), false);

        // create upload request and related download node

        UploadRequest ur1 = new UploadRequest(new File("test1"));

        conn1.setUploadRequest(ur1);

        IDownloadNode node1 = downloadNodeFactory.createUploadRequestNode(
                conn1, ur1);

        assertTrue(node1 instanceof DownUploadRequestNode);
        // assertEquals(node1.getType(), DownloadNode.UPLOAD_REQUEST_NODE);

        // create another upload request and realted download node

        assertTrue(downloadNodeFactory.createUploadRequestNode(conn1, ur1) == node1);

        assertTrue(node1.isValid());

        UploadRequest ur2 = new UploadRequest(new File("test2"));

        conn1.setUploadRequest(ur2);

        assertFalse(node1.isValid());

        IDownloadNode node2 = downloadNodeFactory.createUploadRequestNode(
                conn1, ur2);

        assertTrue(node2.isValid());

        assertTrue(node2 instanceof DownUploadRequestNode);
        // assertEquals(node2.getType(), DownloadNode.UPLOAD_REQUEST_NODE);

        // ensure that two nodes have been cached

        assertEquals(downloadNodeFactory.getUploadRequestNodes().values()
                .size(), 2);

        // ansure that cached versions are used

        assertTrue(downloadNodeFactory.createUploadRequestNode(conn1, ur2) == node2);

        assertEquals(downloadNodeFactory.getUploadRequestNodes().values()
                .size(), 2);

    }

    public void testNoConnectioNodeCachingForNullKeys() throws Exception {
        // create a client
        Client client1 = new Client(new HostInfo("www.gmx.de"), settings);
        // client1.setNick("tommy");

        // create a client connection
        IConnection conn1 = connectionFactory.createClientConnection(client1,
                new ConnectionListenerBase(), false);

        assertEquals(downloadNodeFactory.getConnectionNodes().values().size(),
                0);

        downloadNodeFactory.createConnectionNode(conn1);

        // ensure that node is not cached, because nick of client is null
        assertEquals(downloadNodeFactory.getConnectionNodes().values().size(),
                0);

    }

    public void testNoDownloadRequestNodeCachingForNullKeys() throws Exception {
        // create a client
        Client client1 = new Client(new HostInfo("www.gmx.de"), settings);
        client1.setNick("tommy");

        // create a client connection
        IConnection conn1 = connectionFactory.createClientConnection(client1,
                new ConnectionListenerBase(), false);

        DownloadRequest dr1 = new DownloadRequest(new SearchResult(), new File(
                "test1"), settings);

        assertEquals(downloadNodeFactory.getDownloadRequestNodes().values()
                .size(), 0);

        downloadNodeFactory.createDownloadRequestNode(conn1, dr1);

        // ensure that node is not cached, because filename of searchresult is
        // null
        assertEquals(downloadNodeFactory.getDownloadRequestNodes().values()
                .size(), 0);
    }

}