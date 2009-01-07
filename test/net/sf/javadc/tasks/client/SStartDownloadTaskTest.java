/*
 * Created on 21.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.tasks.client;

import java.awt.Point;
import java.io.IOException;

import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SStartDownloadTaskTest extends AbstractClientTaskTest {

    public SStartDownloadTaskTest(String arg0) {
        super(arg0);

    }

    public void runTask() {
        addDownloadRequest(false);

        task = new SStartDownloadTask(settings);
        task.setClientConnection(clientConnection);
        task.runTask();

    }

    public void testRunTaskSegmented() {
        addDownloadRequest(true);

        task = new SStartDownloadTask(settings);
        task.setClientConnection(clientConnection);
        task.runTask();

    }

    private void addDownloadRequest(boolean segmented) {
        DownloadRequest dr = new DownloadRequest(new SearchResult(), "test",
                settings);

        dr.getSearchResult().setFileSize(100);

        if (segmented) {
            dr.setSegment(new Point(10, 20));
        }

        try {
            clientConnection.getClient().addDownload(dr);

        } catch (IOException io) {
            throw new RuntimeException(io);

        }

    }
}