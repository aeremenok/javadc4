/*
 * Created on 4.9.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.config;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.ShareManager;
import net.sf.javadc.net.hub.HubManager;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class UserTest extends TestCase {

    // external components
    private ISettings settings = new BaseSettings(true);

    private ITaskManager taskManager = new TaskManager();

    private IShareManager shareManager = new ShareManager(settings, taskManager);

    private IHubManager hubManager = new HubManager();

    /**
     * Constructor for UserTest.
     * 
     * @param arg0
     */
    public UserTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        // user = new User(settings, shareManager, hubManager);
    }

    public void testEquality() throws Exception {
        User user1, user2;

        user1 = new User(settings, shareManager, hubManager);
        user2 = new User(settings, shareManager, hubManager);

        user1.setNick("Er$kki $Home");
        user2.setNick("Er kki$ Home");

        // 1
        assertEquals(user1, user2);

        // 2
        assertEquals(user1.getNick(), user2.getNick());

        // 3
        assertEquals(user1.getNick(), "Er_kki__Home");
    }

    public void testToString() {
        User user = new User(settings, shareManager, hubManager);

        user.setNick("EkkiHome");

        // return "$ALL " + getNick() + " " + createTag() + "$ $" + getSpeed()
        // + getSpeedCode() + "$" + getEmail() + "$" + sharedSize + "$";

        assertTrue(user.toString().startsWith("$ALL EkkiHome"));

    }

}