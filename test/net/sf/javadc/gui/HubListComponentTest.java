/*
 * Created on 14.11.2004
 */
package net.sf.javadc.gui;

import junit.framework.TestCase;
import net.sf.javadc.config.AdvancedSettings;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseHubFactory;
import net.sf.javadc.mockups.BaseHubFavoritesList;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.hub.HubList;
import net.sf.javadc.util.TaskManager;

/**
 * @author twe
 */
public class HubListComponentTest extends TestCase {

    private IHubFavoritesList hubFavoritesList = new BaseHubFavoritesList();

    private IHubFactory hubFactory = new BaseHubFactory();

    private ISettings settings = new MySettings();

    // concrete class as interface to use component services
    private TaskManager taskManager = new TaskManager();

    // concrete class as interface to use component services
    private HubList hubList = new HubList(settings, taskManager);

    // private HubListComponent hublistComponent = new HubListComponent(hubList,
    // hubFavoritesList, hubFactory);

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for HubListComponentTest.
     * 
     * @param arg0
     */
    public HubListComponentTest(String arg0) {
        super(arg0);
    }

    public void testCreation() {
        new HubListComponent(hubList, hubFavoritesList, hubFactory);
    }

    // public void testNoTests(){
    //        
    // }

    // TODO : create more tests

}

class MySettings extends BaseSettings {

    private net.sf.javadc.config.AdvancedSettings advanced = new MyAdvancedSettings();

    public MySettings() {
        super(true);
    }

    public net.sf.javadc.config.AdvancedSettings getAdvancedSettings() {
        return advanced;
    }
}

class MyAdvancedSettings extends AdvancedSettings {

    public String getHublistAddress() {
        return "http://www.neo-modus.com/PublicHubList.config";
        // return "http://fi.hublist.org/PublicHubList.config.bz2";
    }

}
