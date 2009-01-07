/*
 * Created on 2.9.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.hub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sf.javadc.config.AdvancedSettings;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.IHubInfo;
import net.sf.javadc.interfaces.IHubList;
import net.sf.javadc.util.TaskManager;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class HubListTest extends TestCase {

    private IHubList hubList = null;

    /**
     * Constructor for HubListTest.
     * 
     * @param arg0
     */
    public HubListTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        // hubList = new MyHubList(settings, taskManager);
        hubList = new MyHubList();
        // taskManager.start();
    }

    public void testCreation() throws Exception {
        assertTrue(hubList != null);
    }

    public void testHubListConstruction() throws Exception {

        Settings settings = new Settings();

        AdvancedSettings advanced = new AdvancedSettings();
        advanced
                .setHublistAddress("http://suomilista.no-ip.org/SuomiLista.xml.bz2");

        settings.setAdvancedSettings(advanced);

        // TaskManager taskManager = new TaskManager();

        HubList hubList = new HubList(settings, new TaskManager());

        Thread.sleep(10 * 1000);

        assertTrue(hubList.getHubInfos().size() > 0);
    }

    public void testFiltering() throws Exception {

        int normal = hubList.getHubInfos().size();
        assertEquals(normal, 3);

        hubList.setFilter("!#&/(%)?_?");
        assertTrue(hubList.getHubInfos().size() == 0);

        hubList.setFilter(null);
        assertEquals(hubList.getHubInfos().size(), normal);

        hubList.setFilter(" ");
        assertEquals(hubList.getHubInfos().size(), normal);

        hubList.setFilter("mp3");
        assertEquals(hubList.getHubInfos().size(), 2);

        IHubInfo hubInfo = (IHubInfo) hubList.getHubInfos().get(0);
        assertTrue(hubInfo.getDescription().toLowerCase().indexOf("mp3") > -1);
    }

    /** ********************************************************************** */

    private class MyHubList extends AbstractHubList {

        private Category logger = Logger.getLogger(MyHubList.class);

        protected boolean update = false;

        public MyHubList() {

            try {
                hubInfos = createHubList();

            } catch (IOException io) {
                logger.debug(io.toString());

            }

        }

        protected List createHubList() throws IOException {
            logger.debug("Creating Mockup HubList");

            List hInfos = new ArrayList();

            HubInfo hubInfo = new HubInfo();
            hubInfo.setDescription("sad fdsa MP3 421fdsa");
            hInfos.add(hubInfo);

            hubInfo = new HubInfo();
            hubInfo.setDescription("hsjkald h");
            hInfos.add(hubInfo);

            hubInfo = new HubInfo();
            hubInfo.setDescription("fd  ds  mP3");
            hInfos.add(hubInfo);

            return hInfos;

        }

    }

}