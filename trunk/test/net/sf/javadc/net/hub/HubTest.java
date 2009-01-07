/*
 * Created on 20.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.hub;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.mockups.BaseHubTaskFactory;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchRequestFactory;
import net.sf.javadc.util.TaskManager;
import spin.Spin;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class HubTest extends TestCase {

    private IHubFactory hubFactory;

    private IHub hub1;

    private IHub hub2;

    // private IHub hub3;
    // private IHub hub4;

    // external components
    private final ITaskManager taskManager = new TaskManager();

    private final IHubManager hubManager = new HubManager();

    private final IHubTaskFactory hubTaskFactory = new BaseHubTaskFactory();

    private final ISettings settings = new BaseSettings(true);

    /**
     * Constructor for HubTest.
     * 
     * @param arg0
     */
    public HubTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        hubFactory = new HubFactory(taskManager, hubManager, hubTaskFactory,
                settings);

        hub1 = hub2 = null;

    }

    public void testConnection() throws Exception {
        HostInfo host1 = new HostInfo("ilotalo.no-ip.org:411");

        hub1 = hubFactory.createHub(host1);

        // new Thread((Hub)hub1).start();
        // hub1.run();
    }

    public void testSearchingWhenLoggedIn() throws Exception {
        HostInfo host1 = new HostInfo("ilotalo.no-ip.org:411");
        Hub hub1 = (Hub) hubFactory.createHub(host1);

        SearchRequestFactory factory = new SearchRequestFactory();
        SearchRequest request = factory.createFromQuery("T?F?1000?1?eeee");

        // 2
        hub1.setLoggedIn(true);
        hub1.search(request);

        // ensure that the search request has been set
        assertEquals(hub1.getSearchRequest(), request);

    }

    public void testNoSearchingWhenNotLoggedIn() throws Exception {
        HostInfo host1 = new HostInfo("ilotalo.no-ip.org:411");
        Hub hub1 = (Hub) hubFactory.createHub(host1);

        SearchRequestFactory factory = new SearchRequestFactory();
        SearchRequest request = factory.createFromQuery("T?F?1000?1?eeee");

        // 1
        hub1.search(request);

        // ensure that not search request has been set
        assertEquals(hub1.getSearchRequest(), null);

    }

    public void testForEqualityWithoutProxies() {
        HostInfo host1 = new HostInfo("ilotalo.no-ip.org:411");
        hub1 = hubFactory.createHub(host1);

        HostInfo host2 = new HostInfo("ilotalo.no-ip.org:411");
        hub2 = hubFactory.createHub(host2);

        assertEquals(hub1, hub2);
    }

    public void testForEqualityWithProxies() {
        HostInfo host1 = new HostInfo("ilotalo.no-ip.org:411");
        hub1 = hubFactory.createHub(host1);

        HostInfo host2 = new HostInfo("ilotalo.no-ip.org:411");
        hub2 = hubFactory.createHub(host2);

        assertEquals(hub1, hub2);

        IHub hubA = (IHub) Spin.off(hub1);
        IHub hubB = (IHub) Spin.off(hub2);

        assertEquals(hub1, hubA);
        assertEquals(hub2, hubB);

        // assertEquals(hubA, hubB);
    }

    /*
     * public void testDownloadRequest() throws Exception { HostInfo host1 = new
     * HostInfo("ilotalo.no-ip.org:411");
     * 
     * hub1 = hubFactory.createHub(host1);
     * 
     * //hub1.run(); hub1.requestDownload(new DownloadRequest(new
     * SearchResult(), settings));
     *  }
     */

}