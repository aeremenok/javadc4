/*
 * Created on 13.11.2004
 */

package net.sf.javadc.net;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.mockups.BaseHubTaskFactory;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.net.hub.Hub;
import net.sf.javadc.net.hub.HubFactory;
import net.sf.javadc.net.hub.HubManager;
import net.sf.javadc.net.hub.HubUser;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper
 */
public class DownloadManagerTest extends TestCase {

    private ISettings settings = new Settings();

    private IHubManager hubManager = new HubManager();

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(hubManager,
            segmentManager);

    private ITaskManager taskManager = new TaskManager();

    private IHubTaskFactory hubTaskFactory = new BaseHubTaskFactory();

    // private IClientManager clientManager = new BaseClientManager();

    private IHubFactory hubFactory;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        hubFactory = new HubFactory(taskManager, hubManager, hubTaskFactory,
                settings);
    }

    /**
     * Constructor for DownloadManagerTest.
     * 
     * @param arg0
     */
    public DownloadManagerTest(String arg0) {
        super(arg0);
    }

    public void testRequestDownload() throws Exception {
        HostInfo host1 = new HostInfo("ilotalo.no-ip.org:411");

        IHub hub1 = hubFactory.createHub(host1);

        // create a HubUser to which the DownloadRequest will be dispatched
        // first
        HubUser user = new HubUser(hub1);
        user.setNick("Mike");
        hub1.addUser(user);

        assertTrue(user.getDownloads().isEmpty());

        SearchResult sr = new SearchResult();
        sr.setHub(hub1);
        sr.setNick("Mike");

        DownloadRequest dr = new DownloadRequest(sr, settings);

        // ensure that no hubs are registered in the HubManager
        assertEquals(hubManager.getHubCount(), 0);

        downloadManager.requestDownload(dr);

        // NOTE : this is hook, that is not used under normal application
        // conditions

        ((DownloadManager) downloadManager).requestNextDownload();

        // ensure that one hub has been added to the HubManager

        // assertEquals(hubManager.getHubCount(), 1);
        // assertEquals(hubManager.getHub(0), hub1);

        assertEquals(user.getDownloads().size(), 1);

        // create a new Client and notify the hub that the nick for the Client
        // has been received

        Client client = new Client(new HostInfo("www.gmx.de"), settings);

        client.setNick("Mike");

        assertEquals(client.getDownloads().length, 0);

        ((Hub) hub1).clientListener.receivedNick(client);

        // ensure that the DownloadRequest has moved from the HubUser to the
        // Client

        assertTrue(user.getDownloads().isEmpty());

        assertEquals(client.getDownloads().length, 1);

    }

}