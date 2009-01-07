/*
 * Created on 22.3.2005
 */

package net.sf.javadc.tasks.client;

import java.awt.Point;

import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.client.ConnectionStatistics;

/**
 * @author Timo Westk�mper
 */
public class IFileLengthTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(IFileLengthTaskTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for IFileLengthTest.
     * 
     * @param arg0
     */
    public IFileLengthTaskTest(String arg0) {
        super(arg0);
    }

    // public void testConnectionStatistics() {
    public void runTask() {
        task = new IFileLengthTask();
        task.setClientConnection(clientConnection);

        DownloadRequest request = new DownloadRequest(new SearchResult(),
                settings);
        clientConnection.setDownloadRequest(request);
        ConnectionStatistics stats = clientConnection.getStatistics();

        // start the download from the byte position 512
        stats.setBytesReceived(512);

        assertEquals(stats.getStartLocation(), 0);

        // length of the file to be downloaded is 1024 bytes
        task.setCmdData("1024");
        task.runTask();

        assertEquals(stats.getFileLength(), 1024);

        // startLocation equals the initial value of bytes received
        assertEquals(stats.getStartLocation(), 512);
    }

    public void testRunTaskWithSegmentedDownloadRequest() {
        task = new IFileLengthTask();
        task.setClientConnection(clientConnection);

        DownloadRequest request = new DownloadRequest(new SearchResult(),
                settings);
        request.setSegment(new Point(100, 200));

        clientConnection.setDownloadRequest(request);
        ConnectionStatistics stats = clientConnection.getStatistics();

        // run task
        task.setCmdData("1024");
        task.runTask();

        assertEquals(stats.getFileLength(), 200);
    }

}