/*
 * Created on 27.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.client;

import java.awt.Point;

import junit.framework.TestCase;
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
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class ConnectionTest extends TestCase {

    private int counter = 0;

    private ConnectionFactory connectionFactory;

    // external components
    private final ISettings settings = new BaseSettings(true);

    private final ITaskManager taskManager = new TaskManager();

    private final IConnectionManager connectionManager = new ConnectionManager(
            settings);

    private final IClientManager clientManager = new BaseClientManager();

    private final IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    // testable components
    private Client client;

    private IConnection conn;

    /**
     * Constructor for ConnectionTest.
     * 
     * @param arg0
     */
    public ConnectionTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        connectionFactory = new ConnectionFactory(taskManager,
                connectionManager, clientManager, clientTaskFactory);

        client = new Client(new HostInfo(), settings);

        conn = connectionFactory.createClientConnection(client,
                new ConnectionListenerBase(), false);

    }

    public void testSegmentOffset() throws Exception {

        DownloadRequest dr1 = createDownloadRequest();
        dr1.setSegment(new Point(10, 20));

        DownloadRequest dr2 = createDownloadRequest();

        // 1
        conn.setDownloadRequest(dr1);
        assertEquals(dr1.getSegment().x, conn.getStatistics()
                .getSegmentOffset());

        // 2
        conn.setDownloadRequest(dr2);
        assertEquals(0, conn.getStatistics().getSegmentOffset());

    }

    /*
     * public void testSlotReservation() throws Exception {
     * assertFalse(conn.isDownloadSlotUsed());
     * assertFalse(conn.isUploadSlotUsed());
     * 
     * conn.setDownloadSlotUsed(true); assertTrue(conn.isDownloadSlotUsed());
     * 
     * conn.setUploadSlotUsed(true); assertTrue(conn.isUploadSlotUsed()); }
     */

    public void testDownloadAddingAndRemoving() throws Exception {

        assertFalse("Client has intially no downloads", client.hasDownloads());

        assertTrue("The initial state of the ClientConnection is CONNECTING",
                conn.getState() == ConnectionState.CONNECTING);

        DownloadRequest[] drs = { createDownloadRequest(),
                createDownloadRequest(), createDownloadRequest(),
                createDownloadRequest() };

        client.addDownloads(drs);

        assertEquals("After having added all the DownloadRequests, "
                + "the client has four downloads in the queue", client
                .getDownloads().length, 4);

        assertTrue("The current download of the ClientConnection equals "
                + " the first element of the Client's download queue", conn
                .getDownloadRequest() == drs[0]);

        client.removeDownload(0);
        client.checkDownloads(); // enforces state update

        assertEquals(conn.getState(), ConnectionState.COMMAND_DOWNLOAD);

        try {
            client.removeAllDownloads();

        } catch (Exception e) {

        }

        client.checkDownloads();

        // System.out.println(conn.getState());
        assertEquals(conn.getState(), ConnectionState.ABORTED);

    }

    private DownloadRequest createDownloadRequest() {
        SearchResult sr = new SearchResult();
        sr.setFilename("test" + ++counter + ".gif");

        return new DownloadRequest(sr, settings);

    }

}