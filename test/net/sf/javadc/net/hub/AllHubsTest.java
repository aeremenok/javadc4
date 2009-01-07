/*
 * Created on 14.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.hub;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.HubListenerBase;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.mockups.BaseHubTaskFactory;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.util.TaskManager;
import spin.Spin;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class AllHubsTest extends TestCase {

    // private AllHubs allHubs;

    // external components
    private final IHubManager hubManager = new HubManager();

    private final ITaskManager taskManager = new TaskManager();

    private final IHubTaskFactory hubTaskFactory = new BaseHubTaskFactory();

    private final ISettings settings = new BaseSettings(true);

    /**
     * Constructor for HubManagerTest.
     * 
     * @param arg0
     */
    public AllHubsTest(String arg0) {
        super(arg0);

    }

    public void testCreation() {
        new AllHubs(hubManager);
    }

    public void testForEqualityWithoutProxies() {
        AllHubs hub1 = new AllHubs(hubManager);
        AllHubs hub2 = new AllHubs(hubManager);

        // ensure that equality is valid for AllHubs instance without proxies
        assertEquals(hub1, hub2);

    }

    public void testForEqualityWithProxies() {
        AllHubs hub1 = new AllHubs(hubManager);
        AllHubs hub2 = new AllHubs(hubManager);

        // ensure that equality is valid for AllHubs instance without proxies
        assertEquals(hub1, hub2);

        IHub hubA = (IHub) Spin.off(hub1);
        IHub hubB = (IHub) Spin.off(hub2);

        assertEquals(hub1, hubA);
        assertEquals(hub2, hubB);

        // Object instance equality is used in the case of proxies, to be
        // investigated further

        // assertEquals(hubA, hub1);
        // assertEquals(hubB, hub2);

        // ensure that equality is valid for AllHubs instance with proxies
        // assertEquals(hubA, hubB);

    }

    public void testUserAddedNotification() {
        AllHubs hub1 = new AllHubs(hubManager);

        MyHubListener listener = new MyHubListener();
        hub1.addListener(listener);

        IHub hub2 = new BaseHub();
        hubManager.addHub(hub2);

        MyHubListener listener2 = new MyHubListener();
        hub2.addListener(listener2);

        hub2.fireUserAdded(null);
        assertEquals(listener.additions, 1);
        assertEquals(listener.removals, 0);

        assertEquals(listener2.additions, 1);
        assertEquals(listener2.removals, 0);

        hub2.fireUserRemoved(null);
        assertEquals(listener.additions, 1);
        assertEquals(listener.removals, 1);

        assertEquals(listener2.additions, 1);
        assertEquals(listener2.removals, 1);
    }

    private class MyHubListener extends HubListenerBase {

        public int additions, removals;

        public void userAdded(IHub hub, HubUser ui) {
            additions++;

        }

        public void userRemoved(IHub hub, HubUser ui) {
            removals++;

        }

    }

    /**
     * java version: 1.4.2_01-b06 os: windows xp javadc version: javadc-20041023
     * 
     * <p>
     * In the Search tab, when I try to download somebody's filelist, I get a
     * StackOverflowError. Here is the end of stack trace: at
     * $Proxy13.requestDownload(Unknown Source) at
     * net.sf.javadc.net.hub.AllHubs.requestDownload (AllHubs.java:293) ...
     * </p>
     * 
     * @throws Exception
     */
    public void testRequestDownload_bug20041122_1() throws Exception {

        // result is invalid, but no exception is thrown

        // assertFalse(allHubs.requestDownload(null));

        new DownloadRequest(new SearchResult(), settings);

        // result is invalid, but no exception is thrown
        // assertFalse(allHubs.requestDownload(dr1));
    }

}