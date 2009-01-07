/*
 * Created on 1.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.client;

import junit.framework.TestCase;
import net.sf.javadc.config.ConnectionSettings;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class ConnectionStatisticsTest extends TestCase {

    private ConnectionStatistics stats;

    /**
     * Constructor for ConnectionStatisticsTest.
     * 
     * @param arg0
     */
    public ConnectionStatisticsTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        stats = new ConnectionStatistics();

    }

    public void testCreation() throws Exception {
        assertTrue(stats != null);

    }

    public void testSegmentOffsetConnectionInfo() throws Exception {
        stats.setBytesReceived(10);
        stats.setSegmentOffset(5);
        stats.setFileLength(15);

        System.out.println(stats.getConnectionInfo());
        assertTrue(stats.getConnectionInfo().startsWith("50%"));
        assertTrue(stats.getConnectionInfo().endsWith("(0)"));
    }

    public void testSegmentOffsetConnectionInfo2() throws Exception {
        long seg = ConnectionSettings.DOWNLOAD_SEGMENT_SIZE;
        stats.setBytesReceived((int) (3.5 * seg));
        stats.setSegmentOffset(3 * seg);
        stats.setFileLength(4 * seg);

        System.out.println(stats.getConnectionInfo());
        assertTrue(stats.getConnectionInfo().startsWith("50%"));
        assertTrue(stats.getConnectionInfo().endsWith("(3)"));
    }

    public void testNormalConnectionInfo() throws Exception {
        stats.setBytesReceived(10);
        stats.setSegmentOffset(0);
        stats.setFileLength(20);

        System.out.println(stats.getConnectionInfo());
        assertTrue(stats.getConnectionInfo().startsWith("50%"));

    }

}