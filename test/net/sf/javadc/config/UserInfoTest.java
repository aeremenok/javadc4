/*
 * Created on 4.9.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.config;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IUserInfo;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UserInfoTest extends TestCase {

    /**
     * Constructor for UserInfoTest.
     * 
     * @param arg0
     */
    public UserInfoTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testEquality() throws Exception {
        IUserInfo user1, user2;

        user1 = new UserInfo();
        user1.setNick("Er$kki $Home");

        user2 = new UserInfo();
        user2.setNick("Er kki$ Home");

        // 1
        assertEquals(user1.getNick(), user2.getNick());

        // 2
        assertEquals(user1.getNick(), "Er_kki__Home");

        // 3
        assertEquals(user1, user2);

    }

}