/*
 * Created on 14.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.hub;

import java.net.UnknownHostException;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.HubManagerListener;
import net.sf.javadc.mockups.BaseHubTaskFactory;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class HubManagerTest extends TestCase {

    private IHubManager hubManager;

    // external components
    private final ITaskManager taskManager = new TaskManager();

    private final IHubTaskFactory hubTaskFactory = new BaseHubTaskFactory();

    private final ISettings settings = new BaseSettings(true);

    /**
     * Constructor for HubManagerTest.
     * 
     * @param arg0
     */
    public HubManagerTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        hubManager = new HubManager();

    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

    }

    /**
     * @param host
     * 
     * @return
     */
    private final IHub createHub(HostInfo host) {
        return new Hub(host, taskManager, hubManager, hubTaskFactory, settings);

    }

    /**
     * 
     */
    public void testAdding() throws Exception {
        // 1 intial size is zero
        assertEquals(hubManager.getHubCount(), 0);

        // 2 size after one addition is one
        HostInfo host1 = new HostInfo("ilotalo.no-ip.org");
        IHub hub1 = createHub(host1);

        hubManager.addHub(hub1);
        assertEquals(hubManager.getHubCount(), 1);

        // 3 size after 2 additions is 2
        HostInfo host2 = new HostInfo("makeshubi.no-ip.com");
        IHub hub2 = createHub(host2);

        hubManager.addHub(hub2);
        assertEquals(hubManager.getHubCount(), 2);

        // 4 size is same if hub2 is added again
        hubManager.addHub(hub2);
        assertEquals(hubManager.getHubCount(), 2);
        hubManager.addHub(createHub(new HostInfo("ilotalo.no-ip.org")));
        assertEquals(hubManager.getHubCount(), 2);

        // 5 size is 1 after one hub has been removed
        hubManager.removeHub(hub1);
        assertEquals(hubManager.getHubCount(), 1);

    }

    public void testSearching() throws Exception {
        HostInfo host1 = new HostInfo("ilotalo.no-ip.org");
        IHub hub1 = createHub(host1);
        hubManager.addHub(hub1);

        HostInfo host2 = new HostInfo("makeshubi.no-ip.com");
        IHub hub2 = createHub(host2);
        hubManager.addHub(hub2);

        // 1 finding by index
        assertEquals(hubManager.getHub(0), hub1);
        assertEquals(hubManager.getHub(1), hub2);

        // 2 finding by IP
        try {
            assertEquals(hubManager.getHubWithIP(host1.getIpString()), hub1);
            assertEquals(hubManager.getHubWithIP(host2.getIpString()), hub2);

        } catch (UnknownHostException e) {

            // ?
        }

        // 3 finding by reference
        assertEquals(hubManager.getHub(createHub(host1)), hub1);
        assertEquals(hubManager.getHub(createHub(host2)), hub2);

    }

    public void testStopping() throws Exception {

        ((TaskManager) taskManager).start();

        MyHubManagerListener listener = new MyHubManagerListener();
        hubManager.addListener(listener);

        HostInfo host1 = new HostInfo("ilotalo.no-ip.org");
        IHub hub1 = createHub(host1);
        hub1.connect();

        hubManager.addHub(hub1);

        // check that notification works
        assertEquals(listener.additions, 1);
        assertEquals(listener.deletions, 0);

        HostInfo host2 = new HostInfo("makeshubi.no-ip.com");
        IHub hub2 = createHub(host2);
        hub2.connect();

        hubManager.addHub(hub2);

        // check that notification works
        assertEquals(listener.additions, 2);
        assertEquals(listener.deletions, 0);

        Thread.sleep(300);

        ((HubManager) hubManager).stop();

        // check that notification works
        // assertEquals(listener.additions, 2);
        // assertEquals(listener.deletions, 2);

    }

    private class MyHubManagerListener implements HubManagerListener {

        public int additions = 0;

        public int deletions = 0;

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.HubManagerListener#hubAdded(net.sf.javadc.interfaces.IHub)
         */
        public void hubAdded(IHub hub) {
            additions++;

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.HubManagerListener#hubRemoved(net.sf.javadc.interfaces.IHub)
         */
        public void hubRemoved(IHub hub) {
            deletions++;

        }

    }

}