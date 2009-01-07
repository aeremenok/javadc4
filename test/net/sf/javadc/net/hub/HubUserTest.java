/*
 * Created on 2.9.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.hub;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.mockups.BaseHub;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class HubUserTest extends TestCase {

    private IHub hub = new BaseHub();

    private HubUser hubUser;

    /**
     * Constructor for HubUserTest.
     * 
     * @param arg0
     */
    public HubUserTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

    }

    public void testCreation() throws Exception {
        hubUser = new HubUser(hub);

        assertTrue(hubUser != null);

    }

}