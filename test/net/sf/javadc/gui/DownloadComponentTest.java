/*
 * Created on 14.11.2004
 */

package net.sf.javadc.gui;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IIncompletesLoader;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.mockups.BaseIncompletesLoader;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.RequestsModel;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.client.ClientManager;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.hub.HubManager;
import net.sf.javadc.util.TaskManager;

/**
 * @author twe
 */
public class DownloadComponentTest extends TestCase {

    // private DownloadComponent downloadComponent;

    // external components
    private ITaskManager taskManager = new TaskManager();

    private ISettings settings = new BaseSettings(true);

    private IConnectionManager connectionManager = new ConnectionManager(
            settings);

    private IIncompletesLoader incompletesLoader = new BaseIncompletesLoader();

    private IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    private IClientManager clientManager = new ClientManager(settings,
            taskManager, connectionManager, clientTaskFactory);

    // private IConnectionFactory connectionFactory = new ConnectionFactory(
    // taskManager, connectionManager, clientManager, clientTaskFactory);

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(
            new HubManager(), segmentManager);

    // testable component
    private IRequestsModel model = new RequestsModel(settings, clientManager,
            connectionManager, incompletesLoader, downloadManager,
            segmentManager);

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

    }

    /**
     * Constructor for DownloadComponentTest.
     * 
     * @param arg0
     */
    public DownloadComponentTest(String arg0) {
        super(arg0);
    }

    public void testCreation() {
        new DownloadComponent(taskManager, model);
    }

    // public void testNoTests() {
    //
    // }

    // TODO : create more tests

}