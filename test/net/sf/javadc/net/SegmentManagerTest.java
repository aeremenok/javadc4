/*
 * Created on 21.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseHub;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SegmentManagerTest extends TestCase {

    private SegmentManager downloadSegmentManager;

    // external components

    private ISettings settings = new Settings();

    /**
     * Constructor for DownloadSegmentManagerTest.
     * 
     * @param arg0
     */
    public SegmentManagerTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        settings.setTempDownloadDir("/tmp/");

        downloadSegmentManager = new SegmentManager(settings);
    }

    public void testCreation() {

    }

    public void testDownloadSegmentHashRequired() throws Exception {
        String cmdData = new StringBuffer("User1 mypath\\motd.txt").append(
                (char) 5).append(500).append(" 3/4").append((char) 5).append(
                "(10.10.10.10:411").append((char) 5).append("User2").toString();

        SearchResult sr = new SearchResult(new BaseHub(), cmdData, settings);

        DownloadRequest dr = new DownloadRequest(sr, settings);

        assertTrue(downloadSegmentManager.getNextSegment(dr) == null);
    }

    public void testDownloadSegment() throws Exception {

        int filesize = 50 * 1024 * 1024;

        String cmdData = new StringBuffer("User1 mypath\\motd.txt")
                .append((char) 5)
                .append(filesize)
                .append(" 3/4")
                .append((char) 5)
                .append(
                        "TTH:XVIVD7G7HGFY2EVOV7ETVMSYUMT5QEEPY4CJUOA (10.10.10.10:411")
                .append((char) 5).append("User2").toString();

        SearchResult sr = new SearchResult(new BaseHub(), cmdData, settings);
        assertTrue(sr.getTTH() != null);

        DownloadRequest dr = new DownloadRequest(sr, settings);
        assertEquals(dr.isSegment(), false);

        DownloadRequest dr1, dr2;

        // get 1st segment
        dr1 = downloadSegmentManager.getNextSegment(dr);
        assertEquals(dr1.isSegment(), true);
        assertEquals(dr1.getSegment().x, 0);
        assertEquals(dr1.getSegment().y, 40 * 1024 * 1024);

        assertTrue(!dr.equals(dr1));

        System.out.println(dr1.getLocalFilename());
        assertTrue(dr1.getLocalFilename().endsWith("motd.txt.001of002"));

        // get 2nd segment
        dr2 = downloadSegmentManager.getNextSegment(dr);
        assertEquals(dr2.isSegment(), true);
        assertEquals(dr2.getSegment().x, 40 * 1024 * 1024);
        assertEquals(dr2.getSegment().y, 50 * 1024 * 1024);

        assertTrue(!dr.equals(dr2));

        System.out.println(dr2.getLocalFilename());
        assertTrue(dr2.getLocalFilename().endsWith("motd.txt.002of002"));

        // get 3rd segment
        assertEquals(downloadSegmentManager.getNextSegment(dr), null);
    }

}