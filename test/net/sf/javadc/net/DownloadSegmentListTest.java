/*
 * Created on 6.4.2005
 */

package net.sf.javadc.net;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.SegmentManagerListener;
import net.sf.javadc.mockups.BaseHub;

/**
 * @author Timo Westk�mper
 */
public class DownloadSegmentListTest extends TestCase {

    private ISettings settings = new Settings();

    private DownloadSegmentList list;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(DownloadSegmentListTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        /*
         * public DownloadSegmentList(int size, int segmentSize, int fullsize,
         * String localFilenamePrefix, ISettings settings)
         */

        settings.setTempDownloadDir("/tmp/");

        list = new DownloadSegmentList(6, 10, 55, "/tmp/bogus", settings);
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for DownloadSegmentListTest.
     * 
     * @param arg0
     */
    public DownloadSegmentListTest(String arg0) {
        super(arg0);
    }

    public void testCreation() {

    }

    // public DownloadRequest createSegment(DownloadRequest dr) {

    public void testCreateSegment() throws Exception {

        long filesize = 55;
        String cmdData = new StringBuffer("User1 mypath\\motd.txt").append(
                (char) 5).append(filesize).append(" 3/4").append((char) 5)
                .append(
                        "TTH:XVIVD7G7HGFY2EVOV7ETVMSYUMT5QEEPY4CJUOA "
                                + "(10.10.10.10:411").append((char) 5).append(
                        "User2").toString();

        SearchResult sr = new SearchResult(new BaseHub(), cmdData, settings);

        assertEquals(sr.getTTH(), "XVIVD7G7HGFY2EVOV7ETVMSYUMT5QEEPY4CJUOA");
        DownloadRequest dr = new DownloadRequest(sr, settings);

        DownloadRequest segment = list.createSegment(dr);
        assertEquals(segment.isSegment(), true);
        assertEquals(segment.getSegment().x, 0);
        assertEquals(segment.getSegment().y, 10);
    }

    // public String getSuffix(int index) {

    public void testGetSuffix() {

        assertEquals(DownloadSegmentList.getSuffix(1, 400), ".001of400");
        assertEquals(DownloadSegmentList.getSuffix(20, 400), ".020of400");
        assertEquals(DownloadSegmentList.getSuffix(300, 400), ".300of400");
    }

    // public DownloadRequest getSlot(int i) {

    public void testGetSlot() {

        for (int i = 0; i < 6; i++) {
            assertEquals(list.getSlot(i), null);
        }
    }

    public void testStartNotification() throws Exception {

        settings.setTempDownloadDir("C:\\");

        String prefix = "C:\\test";

        try {

            /*
             * public DownloadSegmentList(int size, int segmentSize, int
             * fullsize, String localFilenamePrefix, ISettings settings)
             */

            System.out.println("segmentList was created.");
            System.out.println();

            DownloadSegmentList segmentList = new DownloadSegmentList(2, 10,
                    20, prefix, settings);

            // create the initial download request

            String cmdData = new StringBuffer("User1 mypath\\motd.txt").append(
                    (char) 5).append(20).append(" 3/4").append((char) 5)
                    .append(
                            "TTH:XVIVD7G7HGFY2EVOV7ETVMSYUMT5QEEPY4CJUOA "
                                    + "(10.10.10.10:411").append((char) 5)
                    .append("User2").toString();

            SearchResult sr = new SearchResult(new BaseHub(), cmdData, settings);
            DownloadRequest dr = new DownloadRequest(sr, settings);

            // create the segmented download request

            System.out.println("About to create segmented download for " + dr);
            System.out.println();

            assertTrue(segmentList.createSegment(dr) != null);

            // create a file segment

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(new File(prefix + ".001of002"))));

            writer.write("0123456789");

            writer.close();

            // create the listener and register it

            MySegmentManagerListener listener = new MySegmentManagerListener();
            segmentList.addListener(listener);

            Thread.sleep(2000);

            // ensure that one notification has been sent

            assertEquals("Expected one download start notification. ",
                    listener.starts, 1);

        } finally {

            // delete the file segment

            new File(prefix + ".001of002").delete();

        }

    }

    private class MySegmentManagerListener implements SegmentManagerListener {

        public int drops = 0;

        public int starts = 0;

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.SegmentManagerListener#dropDownload(net.sf.javadc.net.DownloadRequest)
         */
        public void dropDownload(DownloadRequest dr) {
            drops++;

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.SegmentManagerListener#startDownload(net.sf.javadc.net.DownloadRequest)
         */
        public void startDownload(DownloadRequest dr) {
            starts++;

        }

    }

}
