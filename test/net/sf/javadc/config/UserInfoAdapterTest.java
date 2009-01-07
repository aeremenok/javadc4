/*
 * Created on 12.11.2004
 */
package net.sf.javadc.config;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IUserInfo;

/**
 * @author Timo Westk�mper
 */
public class UserInfoAdapterTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for UserInfoAdapterTest.
     * 
     * @param arg0
     */
    public UserInfoAdapterTest(String arg0) {
        super(arg0);
    }

    public void testEquality() throws Exception {
        IUserInfo user1, user2;

        user1 = new UserInfoAdapter(new UserInfo());
        user1.setNick("Er$kki $Home");

        user2 = new UserInfoAdapter(new UserInfo());
        user2.setNick("Er kki$ Home");

        // 1
        assertEquals(user1.getNick(), user2.getNick());

        // 2
        assertEquals(user1.getNick(), "Er_kki__Home");

        // 3
        assertEquals(user1, user2);

    }

}
