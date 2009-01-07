/*
 * Created on 18.9.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package net.sf.javadc.net;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IIncompletesLoader;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListenerBase;
import net.sf.javadc.listeners.RequestsModelListenerBase;
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.mockups.BaseIncompletesLoader;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.client.ClientManager;
import net.sf.javadc.net.client.ConnectionFactory;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.net.hub.HubManager;
import net.sf.javadc.util.TaskManager;

import org.picocontainer.Startable;

/**
 * @author Timo Westk�mper
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RequestsModelTest extends TestCase {

    /*
     * ClientManager(ISettings _settings, ITaskManager _taskManager,
     * IConnectionManager _clientConnectionManager, IClientTaskFactory
     * _clientTaskFactory)
     */

    // external components
    private ISettings settings = new Settings();

    private IConnectionManager connectionManager = new ConnectionManager(
            settings);

    // mockup
    private IIncompletesLoader incompletesLoader = new BaseIncompletesLoader();

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(
            new HubManager(), segmentManager);

    private ITaskManager taskManager = new TaskManager();

    // mockup
    private IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    private IClientManager clientManager = new ClientManager(settings,
            taskManager, connectionManager, clientTaskFactory);

    private IConnectionFactory connectionFactory = new ConnectionFactory(
            taskManager, connectionManager, clientManager, clientTaskFactory);

    // testable component
    private IRequestsModel requestsModel = new RequestsModel(settings,
            clientManager, connectionManager, incompletesLoader,
            downloadManager, segmentManager);

    private int counter = 0;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

    }

    public void testStarting() throws Exception {
        ((Startable) requestsModel).start();

    }

    public void testConnectionCreation() throws Exception {
        Client client = new Client(new HostInfo("www.gmx.de"), settings);

        IConnection clientConnection = connectionFactory
                .createClientConnection(client, new ConnectionListenerBase(),
                        false);

        // ensure that a valid connection has been created
        assertEquals(clientConnection != null, true);

        // given the Connection time to add itself to the ConnectionManager
        Thread.sleep(1000);

        // ensure that the connection has been added to the ConnectionManager
        assertEquals(connectionManager.getClientConnections().length, 1);

        // ensure that the ConnectionManager has notified the RequestsModel
        // about the addition

        assertEquals(requestsModel.getActiveConnections().size(), 1);
    }

    private DownloadRequest createDownloadRequest() {
        return new DownloadRequest(new SearchResult(), "test" + ++counter,
                settings);

    }

    public void testListeningWorks() throws Exception {

        MyRequestsModelListener listener = new MyRequestsModelListener();
        requestsModel.addListener(listener);

        assertEquals(listener.removed, 0);
        assertEquals(listener.added, 0);
        assertEquals(listener.changed, 0);

        Client client = new Client(new HostInfo(), settings);

        clientManager.fireClientAdded(client);

        // DownloadRequest request = new DownloadRequest(new SearchResult(),
        // settings);

        DownloadRequest request = createDownloadRequest();

        client.addDownload(request);

        assertEquals(listener.added, 1);

        request.fireDownloadRequestStateChanged();
        assertEquals(listener.changed, 1);

        request.fireDownloadRequestStateChanged();
        assertEquals(listener.changed, 2);
    }

    private class MyRequestsModelListener extends RequestsModelListenerBase {

        public int removed, added, changed;

        public MyRequestsModelListener() {
            removed = added = changed = 0;

        }

        public void requestRemoved(IClient client, DownloadRequest dr, int index) {
            // TODO Auto-generated method stub
            removed++;
        }

        public void requestAdded(IClient client, DownloadRequest dr, int index) {
            // TODO Auto-generated method stub
            added++;
        }

        public void requestChanged(IClient client,
                DownloadRequest downloadRequest, int index) {
            // TODO Auto-generated method stub
            changed++;
        }

    }

}