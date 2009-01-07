/*
 * Created on 1.1.2005
 */

package net.sf.javadc.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IIncompletesLoader;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseClientManager;
import net.sf.javadc.mockups.BaseIncompletesLoader;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westk�mper
 */
public class RequestsModelCleanupListenerTest extends TestCase {

    // external components
    private IClientManager clientManager = new BaseClientManager();

    private ISettings settings = new Settings();

    private IConnectionManager connectionManager = new ConnectionManager(
            settings);

    private IIncompletesLoader incompletesLoader = new BaseIncompletesLoader();

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(
            new HubManager(), segmentManager);

    // private ITaskManager taskManager = new TaskManager();
    //
    // private IClientTaskFactory clientTaskFactory = new
    // BaseClientTaskFactory();

    // private IConnectionFactory connectionFactory = new ConnectionFactory(
    // taskManager, connectionManager, clientManager, clientTaskFactory);

    // testable component
    private IRequestsModel requestsModel = new RequestsModel(settings,
            clientManager, connectionManager, incompletesLoader,
            downloadManager, segmentManager);

    private RequestsModelCleanupListener listener = new RequestsModelCleanupListener(
            settings);

    private String tempDir;

    private static int counter = 0;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        if (File.separatorChar == '/') {
            tempDir = "/tmp";
        } else {
            tempDir = "C:\\Temp";
        }

        settings.setTempDownloadDir(tempDir);
        settings.setDownloadDir(tempDir);

        new File(settings.getTempDownloadDir(), "test").delete();
        new File(settings.getDownloadDir(), "test").delete();

        System.out.println(++counter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {

        new File(settings.getTempDownloadDir(), "test").delete();
        new File(settings.getDownloadDir(), "test").delete();

    }

    /**
     * Constructor for RequestsModelCleanupListenerTest.
     * 
     * @param arg0
     */
    public RequestsModelCleanupListenerTest(String arg0) {
        super(arg0);
    }

    public void testNotifyDownloadRequestRemoved1() {
        DownloadRequest dr1 = new DownloadRequest(new SearchResult(), new File(
                "test"), settings);

        dr1.getSearchResult().setFileSize(5);

        File file = new File(dr1.getLocalFilename());

        try {
            new RandomAccessFile(file, "rw").close();

        } catch (FileNotFoundException e) {
            fail(e.toString());

        } catch (IOException io) {
            fail(io.toString());
        }

        // ensure that file exists
        assertTrue("File didn't exist", file.exists());

        listener.requestRemoved(null, dr1, -1);

        // ensure that file has been removed
        assertFalse("File was not removed.", file.exists());

        // ensure that file has not been moved to directory for finished
        // downloads
        new File(settings.getDownloadDir(), file.getName());

        assertFalse("File has been moved to directory for finished downloads",
                file.exists());
    }

    public void testNotifyDownloadRequestRemoved2() {
        DownloadRequest dr1 = new DownloadRequest(new SearchResult(), new File(
                "test"), settings);

        dr1.getSearchResult().setFileSize(5);

        File file = new File(dr1.getLocalFilename());

        try {
            new RandomAccessFile(file, "rw").close();

        } catch (FileNotFoundException e) {
            fail(e.toString());

        } catch (IOException io) {
            fail(io.toString());
        }

        // ensure that file exists
        assertTrue("File didn't exist", file.exists());

        // dispatch the notification via the RequestsModel instance to the
        // RequestsModelCleanupListener instance
        requestsModel.fireRequestRemoved(null, dr1, -1);

        // ensure that file has been removed
        assertFalse("File was not removed", file.exists());

        // ensure that file has not been moved to directory for finished
        // downloads
        File newFile = new File(settings.getDownloadDir(), file.getName());
        assertFalse(
                "File has been removed to directory for finished downloads",
                newFile.exists());
    }

    public void testNotifyDownloadRequestRemoved3() {
        settings.setDownloadDir(tempDir);

        notifyDownloadRequestRemoved(false);
    }

    public void testNotifyDownloadRequestRemoved4() {
        notifyDownloadRequestRemoved(true);
    }

    public void notifyDownloadRequestRemoved(boolean differentDirs) {
        DownloadRequest dr1 = new DownloadRequest(new SearchResult(), new File(
                "test"), settings);

        dr1.getSearchResult().setFileSize(5);

        File file = new File(dr1.getLocalFilename());

        TestFileHelper.writeIntoFile(file, 5);

        assertEquals("Exptected file length to be 5, but was " + file.length(),
                file.length(), 5);

        // ensure that file exists
        assertTrue("File didn't exist", file.exists());

        // dispatch the notification via the RequestsModel instance to the
        // RequestsModelCleanupListener instance
        requestsModel.fireRequestRemoved(null, dr1, -1);

        if (differentDirs) {
            // ensure that file been removed
            assertFalse("File was not removed", file.exists());

        } else {
            assertTrue("File was removed", file.exists());
            file.delete();
        }

        // ensure that file has been moved to directory for finished downloads
        File newFile = new File(settings.getDownloadDir(), file.getName());

        if (differentDirs) {
            // if the directories for temp and finished downloads are different,
            // the temp file is moved
            assertTrue(newFile.exists());
            assertFalse(newFile.equals(file));

            newFile.delete();

        } else {
            // if the two directories are equal, the files are equal as well
            // assertFalse(newFile.exists());
            assertEquals(newFile, file);
        }

    }

}