/*
 * Created on 22.3.2005
 */

package net.sf.javadc.tasks.client;

import java.io.ByteArrayInputStream;

import net.sf.javadc.interfaces.IClientTask;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.client.ConnectionStatistics;
import net.sf.javadc.util.TokenInputStream;

/**
 * @author Timo Westk�mper
 */
public class SDownloadingTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SDownloadingTaskTest.class);
    }

    /**
     * Constructor for SDownloadingTaskTest.
     * 
     * @param arg0
     */
    public SDownloadingTaskTest(String arg0) {
        super(arg0);
    }

    public void testCreationSucceeds() {
        // IClientTask clientTask = new SStartDownloadTask(settings);
        // clientTask.setClientConnection(clientConnection);
        // clientTask.runTask();

        IClientTask clientTask = new SDownloadingTask();
        clientTask.setClientConnection(clientConnection);
        // clientTask.runTask();

    }

    private void _prepare() {
        DownloadRequest request = new DownloadRequest(new SearchResult(),
                settings);
        clientConnection.setDownloadRequest(request);

        // input
        byte[] bytes = new byte[50 * KBYTES];
        TokenInputStream reader = new TokenInputStream(
                new ByteArrayInputStream(bytes), ' ');
        clientConnection.setReader(reader);

    }

    private void _perform() {
        clientConnection.setState(ConnectionState.DOWNLOADING);
        task = new SDownloadingTask();
        task.setClientConnection(clientConnection);
        task.runTask();
    }

    public void runTask() {

        testStillDownloading();
    }

    public void testStillDownloading() {
        _prepare();

        ConnectionStatistics stats = clientConnection.getStatistics();
        stats.setBytesReceived(500 * KBYTES);
        stats.setStartLocation(stats.getBytesReceived());
        stats.setFileLength(1024 * KBYTES);

        _perform();

        // ensure the the client is still downloading

        assertEquals(stats.getBytesReceived(), 550 * KBYTES);
        assertEquals(clientConnection.getState(), ConnectionState.DOWNLOADING);

    }

    public void testDownloadFinished() {
        _prepare();

        ConnectionStatistics stats = clientConnection.getStatistics();
        stats.setBytesReceived(MBYTES - 50 * KBYTES);
        stats.setStartLocation(stats.getBytesReceived());
        stats.setFileLength(MBYTES);

        _perform();

        // ensure that the download is finished

        assertEquals(stats.getBytesReceived(), MBYTES);
        assertEquals(clientConnection.getState(),
                ConnectionState.DOWNLOAD_FINISHED);

    }

    public void testAborted() {
        _prepare();

        ConnectionStatistics stats = clientConnection.getStatistics();
        stats.setBytesReceived(70 * MBYTES); // 70 mbytes downloaded
        stats.setStartLocation(10 * MBYTES); // started from 10 mbytes

        stats.setFileLength(100 * MBYTES); // 100 mbytes total

        _perform();

        // ensure that the download is aborted after having downloaded 55 mbytes

        assertEquals(stats.getBytesReceived(), 70 * MBYTES + 50 * KBYTES);
        assertEquals(clientConnection.getState(), ConnectionState.ABORTED);

    }

}