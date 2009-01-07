/*
 * Created on 18.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net;

import java.util.ArrayList;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class ShareManagerTest extends TestCase {

    private IShareManager shareManager;

    // external components
    private ISettings settings = new BaseSettings(true);

    private ITaskManager taskManager = new TaskManager();

    /**
     * Constructor for ShareManagerTest.
     * 
     * @param arg0
     */
    public ShareManagerTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {

    }

    public void testInitial() throws Exception {
        shareManager = new ShareManager(settings, taskManager);
        assertTrue(shareManager != null);
        shareManager.update();

    }

    public void testSerialization() throws Exception {
        Settings settings = new Settings();
        settings.setUploadDirs(new ArrayList());
        settings.getUploadDirs().add("C:\\WINNT\\Temp");

        ShareManager shareManager = new ShareManager(settings, taskManager);
        // shareManager.update();

        shareManager.start();

        shareManager.runTask();
        Thread.sleep(300);

        shareManager.stop();

    }

}