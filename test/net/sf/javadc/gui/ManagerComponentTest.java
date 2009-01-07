/*
 * Created on 1.8.2004
 */

package net.sf.javadc.gui;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.IHubList;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.mockups.BaseHubFavoritesList;
import net.sf.javadc.mockups.BaseHubList;
import net.sf.javadc.mockups.BaseHubTaskFactory;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.net.hub.Hub;
import net.sf.javadc.net.hub.HubFactory;
import net.sf.javadc.net.hub.HubManager;
import net.sf.javadc.net.hub.HubUser;
import net.sf.javadc.util.TaskManager;

public class ManagerComponentTest extends TestCase {

    // general components
    private final ISettings settings = new BaseSettings(true);

    private final IHubManager hubManager = new HubManager();

    private final IHubList hubList = new BaseHubList();

    private final IHubFavoritesList hubFavoritesList = new BaseHubFavoritesList();

    private final ITaskManager taskManager = new TaskManager();

    private final IHubTaskFactory hubTaskFactory = new BaseHubTaskFactory();

    private IHubFactory hubFactory;

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private final IDownloadManager downloadManager = new DownloadManager(
            hubManager, segmentManager);

    // gui components
    private HubListComponent hubListComponent;

    private ManagerComponent managerComponent;

    private IHub hub1;

    private IHub hub2;

    // private IHub hub3;
    // private IHub hub4;

    private static int counter = 0;

    /**
     * Constructor for ManagerComponentTest.
     * 
     * @param arg0
     */
    public ManagerComponentTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        System.out.println("\nsetUp" + (++counter) + " \n");

        hub1 = hub2 = null;

        hubFactory = new HubFactory(taskManager, hubManager, hubTaskFactory,
                settings);

        hubListComponent = new HubListComponent(hubList, hubFavoritesList,
                hubFactory);

        managerComponent = new ManagerComponent(hubManager, hubFavoritesList,
                hubListComponent, settings, downloadManager);

        // creating hubs

        HostInfo host1 = new HostInfo("ilotalo.no-ip.org:411");

        hub1 = hubFactory.createHub(host1);
        hubManager.addHub(hub1);

        HostInfo host2 = new HostInfo("makeshubi.no-ip.com");

        hub2 = hubFactory.createHub(host2);
        hubManager.addHub(hub2);

    }

    public void testHubAdding() {
        System.out.println("\ntestHubAdding\n");

        // 1
        assertEquals(managerComponent.getIndexOf(hub1), 0);

        // 2
        assertEquals(managerComponent.getIndexOf(hub2), 1);

    }

    // removal of hubs has to be done now explicitly in the GUI

    public void _testHubRemoving1() {
        System.out.println("\ntestHubRemoving1\n");

        hubManager.removeHub(hub1);

        // 1
        assertEquals(managerComponent.getIndexOf(hub1), -1);

        // 2
        assertEquals(managerComponent.getIndexOf(hub2), 0);

        hubManager.removeHub(hub2);

        // 3
        assertEquals(managerComponent.getIndexOf(hub2), -1);

    }

    // removal of hubs has to be done now explicitly in the GUI

    public void _testHubRemoving2() {
        System.out.println("\ntestHubRemoving2\n");

        // hub1.disconnect();
        ((Hub) hub1).fireHubDisconnected();

        assertEquals(managerComponent.getIndexOf(hub1), -1);

    }

    public void testTabStrings() {
        System.out.println("\ntestTabStrings\n");

        // 1
        assertEquals(managerComponent.getTabString(hub1),
                "ilotalo.no-ip.org (0 users)");

        // 2
        assertEquals(managerComponent.getTabString(hub2),
                "makeshubi.no-ip.com (0 users)");

        HubUser hubUser1 = new HubUser(hub1);

        hubUser1.setNick("Timo");

        hub1.addUser(hubUser1);

        assertEquals(hub1.getUserCount(), 1);

        // 3
        // System.out.println(managerComponent.getTabString(hub1));
        assertEquals(managerComponent.getTabString(hub1),
                "ilotalo.no-ip.org (1 users)");

        hub1.removeUser(hubUser1);

        // 4
        assertEquals(managerComponent.getTabString(hub1),
                "ilotalo.no-ip.org (0 users)");

    }

}