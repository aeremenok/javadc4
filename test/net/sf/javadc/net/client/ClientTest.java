/*
 * Created on 17.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.client;

import java.io.IOException;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ClientListenerBase;
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
public class ClientTest extends TestCase {

    private Client client;

    private int counter = 0;

    // external components
    private ISettings settings = new BaseSettings(true);

    private ITaskManager taskManager = new TaskManager();

    private IConnectionManager connectionManager = new ConnectionManager(
            settings);

    private IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    private IClientManager clientManager = new ClientManager(settings,
            taskManager, connectionManager, clientTaskFactory);

    /**
     * Constructor for ClientTest.
     * 
     * @param arg0
     */
    public ClientTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        HostInfo host = new HostInfo("www.gmx.de");
        client = new Client(host, settings);

        clientManager.addClient(host, client);

    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {

        // super.tearDown();
    }

    private DownloadRequest createDownloadRequest() {
        return new DownloadRequest(new SearchResult(), "test" + ++counter,
                settings);

    }

    public void testAdding() throws Exception {
        DownloadRequest[] drs = { createDownloadRequest(),
                createDownloadRequest(), createDownloadRequest(),
                createDownloadRequest() };

        assertEquals(client.hasDownloads(), false);

        try {
            client.addDownload(drs[0]);

        } catch (IOException e) {
            fail(e.toString());
        }

        assertEquals(client.hasDownloads(), true);

        assertEquals(drs[0], client.getDownload(0));

        try {
            client.removeDownload(drs[0]);

        } catch (Exception e) {
            assertEquals(e.getMessage(),
                    "Task for SDisconnect could not be created.");

        }

        assertEquals(client.hasDownloads(), false);

        try {
            client.addDownloads(drs);

        } catch (IOException io) {
            fail(io.toString());

        }

        DownloadRequest[] drs2 = client.getDownloads();

        for (int i = 0; i < drs2.length; i++) {
            assertEquals(drs[i], drs2[i]);
        }

    }

    public void testRemoving() {

        try {
            client.addDownload(createDownloadRequest());

        } catch (IOException e) {
            fail(e.toString());

        }

        client.removeDownload(0);

    }

    public void testListening() {

        MyClientListener listener = new MyClientListener();
        client.addListener(listener);

        assertEquals(listener.added, 0);
        assertEquals(listener.removed, 0);

        // createDownloadRequest();

        try {
            client.addDownload(createDownloadRequest());

        } catch (IOException e) {
            fail(e.toString());

        }

        assertEquals(listener.added, 1);

        client.removeDownload(0);

        assertEquals(listener.removed, 1);

    }

    private class MyClientListener extends ClientListenerBase {

        public int added, removed;

        public void downloadAdded(Client client, DownloadRequest dr) {
            added++;

        }

        public void downloadRemoved(IClient client, DownloadRequest dr) {
            removed++;

        }

    }

}