/*
 * Created on 17.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.hub;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class HostInfoTest extends TestCase {

    private HostInfo hostInfo;

    /**
     * Constructor for HostInfoTest.
     * 
     * @param arg0
     */
    public HostInfoTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

    }

    public void testInitial() throws Exception {
        hostInfo = new HostInfo("www.uta.fi:411");

        assertEquals(hostInfo.getHost(), "www.uta.fi");
        assertEquals(hostInfo.getHostAndPort(), "www.uta.fi:411");

        assertEquals(hostInfo.getPort(), 411);
        assertEquals(hostInfo.getAlternatePort(), 411);

    }

    public void testEquals() throws Exception {
        HostInfo host1 = new HostInfo("www.uta.fi");
        HostInfo host2 = new HostInfo("www.uta.fi:411");
        HostInfo host3 = new HostInfo("www.uta.fi:411:822");

        assertEquals(host1, host2);
        assertEquals(host2, host3);
        assertEquals(host1, host3);

    }

    public void testEqualsExtended() throws Exception {
        HostInfo host1 = new HostInfo("www.uta.fi:400");
        HostInfo host2 = new HostInfo("www.uta.fi:400:800");

        HostInfo host3 = new HostInfo("www.uta.fi:500:800");
        HostInfo host4 = new HostInfo("www.uta.fi:400:900");

        assertEquals(host1, host2);
        assertEquals(host1, host4);

        assertEquals(host2, host3);
        assertEquals(host2, host4);

        // assertEquals(host3, host4);
    }

    public void testExtended() throws Exception {
        hostInfo = new HostInfo("www.uta.fi:444:888");

        assertEquals(hostInfo.getHost(), "www.uta.fi");
        assertEquals(hostInfo.getHostAndPort(), "www.uta.fi:444");

        assertEquals(hostInfo.getPort(), 444);
        assertEquals(hostInfo.getAlternatePort(), 888);

    }

}