/*
 * Created on 27.11.2004
 */

package net.sf.javadc.config.gui;

import junit.framework.TestCase;
import net.sf.javadc.config.SettingsAdapter;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.mockups.BaseSettingsLoader;
import net.sf.javadc.net.ShareManager;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper
 */
public class TransferSettingsPanelTest extends TestCase {

    // private ISettings settings = new Settings();
    private ISettings settings = new SettingsAdapter(new BaseSettingsLoader());

    private ITaskManager taskManager = new TaskManager();

    private IShareManager shareManager = new ShareManager(settings, taskManager);

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for TransferSettingsPanelTest.
     * 
     * @param arg0
     */
    public TransferSettingsPanelTest(String arg0) {
        super(arg0);
    }

    public void testCreation() throws Exception {

        TransferSettingsPanel panel = new TransferSettingsPanel(settings,
                shareManager);

        assertTrue(panel != null);

    }

    // TODO : create more tests

}