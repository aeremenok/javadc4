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
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListenerBase;
import net.sf.javadc.mockups.BaseClientManager;
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.UploadRequest;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.client.ConnectionFactory;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DownloadNodeTest extends TestCase {

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

        connectionFactory = new ConnectionFactory(taskManager,
                connectionManager, clientManager, clientTaskFactory);
    }

    /**
     * Constructor for DownloadNodeTest.
     * 
     * @param arg0
     */
    public DownloadNodeTest(String arg0) {
        super(arg0);
    }

    public void testUploadConnection() {
        Client client = new Client(new HostInfo(), settings);

        IConnection conn = connectionFactory.createClientConnection(client,
                new ConnectionListenerBase(), false);

        conn.getConnectionInfo().setCurrentDirection(
                ConstantSettings.UPLOAD_DIRECTION);

        // assert that an upload connection node has one child

        IDownloadNode node1 = new DownConnectionNode(conn);

        assertTrue(node1.getNumChildren() == 1);

        UploadRequest ur = new UploadRequest(new File("/tmp/test.txt"));
        conn.setUploadRequest(ur);

        IDownloadNode node2 = new DownConnectionNode(conn);

        // assure that the UploadRequest can be retrieved from the Connection
        // node

        // assertEquals(node2.getChild(0), ur);

        // IDownloadNode node2child = (IDownloadNode) node2.getChild(0);

        // assure that the UploadRequest can be retrieved from UploadRequest
        // node

        // assertEquals(node2child.getUploadRequest(), ur);
        // assertNull(node2child.getClientConnection());

        boolean thrown = false;

        try {
            node2.getChild(1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }

        // ensure that a IndexOutOfBoundsException is thrown

        assertTrue(thrown);
    }

}