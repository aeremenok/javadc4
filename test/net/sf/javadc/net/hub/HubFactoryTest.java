/*
 * Created on 18.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.hub;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubInfo;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.mockups.BaseHubTaskFactory;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class HubFactoryTest extends TestCase {

    private IHubFactory hubFactory;

    private IHub hub1;

    private IHub hub2;

    private IHub hub3;

    private IHub hub4;

    // external components
    private final ITaskManager taskManager = new TaskManager();

    private final IHubManager hubManager = new HubManager();

    private final IHubTaskFactory hubTaskFactory = new BaseHubTaskFactory();

    private final ISettings settings = new BaseSettings(true);

    /**
     * Constructor for HubFactoryTest.
     * 
     * @param arg0
     */
    public HubFactoryTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        hubFactory = new HubFactory(taskManager, hubManager, hubTaskFactory,
                settings);

        hub1 = hub2 = hub3 = hub4 = null;

    }

    protected void createHubs() {
        HostInfo host1 = new HostInfo("ilotalo.no-ip.org:411");

        hub1 = hubFactory.createHub(host1);

        HostInfo host2 = new HostInfo("makeshubi.no-ip.com:411");

        hub2 = hubFactory.createHub(host2);

        IHubInfo hubInfo1 = new HubInfo();

        hubInfo1.setAddress(host1);
        hub3 = hubFactory.createHub(hubInfo1);

        IHubInfo hubInfo2 = new HubInfo();

        hubInfo2.setAddress(host2);
        hub4 = hubFactory.createHub(hubInfo2);

    }

    public void testHostsAreEqual() throws Exception {
        createHubs();

        assertEquals(hub1.getHost().equals(hub3.getHost()), true);

        assertEquals(hub2.getHost().equals(hub4.getHost()), true);

    }

    public void testHubsAreEqual() throws Exception {
        createHubs();

        // assertEquals(hub1, hub3);
        // assertEquals(hub2, hub4);
        assertEquals(hub1.equals(hub3), true);

        assertEquals(hub2.equals(hub4), true);

    }

}