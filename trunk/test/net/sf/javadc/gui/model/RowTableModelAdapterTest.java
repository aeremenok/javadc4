/*
 * Created on 18.9.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package net.sf.javadc.gui.model;

import java.awt.Point;
import java.io.File;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IIncompletesLoader;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseClientManager;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.mockups.BaseIncompletesLoader;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.RequestsModel;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westk�mper
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RowTableModelAdapterTest extends TestCase {

    // external components
    private IClientManager clientManager = new BaseClientManager();

    private ISettings settings = new BaseSettings(true);

    private IConnectionManager connectionManager = new ConnectionManager(
            settings);

    private IIncompletesLoader incompletesLoader = new BaseIncompletesLoader();

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(
            new HubManager(), segmentManager);

    private IRequestsModel requestsModel = new RequestsModel(settings,
            clientManager, connectionManager, incompletesLoader,
            downloadManager, segmentManager);

    // testable component
    private RowTableModelAdapter adapter;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        // adapter = new RowTableModelAdapter(requestsModel, new
        // String[]{"File",
        // "Size", "Nick", "Host", "Hub", "State"});

        adapter = new RowTableModelAdapter(requestsModel, new String[] {
                "File", "Hash", "Temp Size", "Size", "Nick", "Host", "Hub",
                "State" });
    }

    public void testInitial() throws Exception {
        assertEquals(adapter.getColumnCount(), 8);

        assertEquals(adapter.getRowCount(), 0);
    }

    public void testGetValueAtNormal() {
        IHub hub = new BaseHub("MyHub123");

        SearchResult searchResult = new SearchResult(hub, "timowest", "eee",
                settings, 0);

        searchResult.setFileSize(1000);

        DownloadRequest request = new MyDownloadRequest(searchResult, settings);

        requestsModel.getAllDownloads().add(request);

        for (int i = 0; i < adapter.getColumnCount(); i++) {
            System.out.println(adapter.getValueAt(0, i));
        }

        // sr.getFilename(),
        assertEquals(adapter.getValueAt(0, 0), "eee");
        // new Long(dr.getTempFileSize()),
        assertEquals(adapter.getValueAt(0, 2), new Long(13));
        // new Long(sr.getFileSize()),
        assertEquals(adapter.getValueAt(0, 3), new Long(1000));
        // sr.getNick(),
        assertEquals(adapter.getValueAt(0, 4), "timowest");
        // sr.getHost(),
        // ?
        // sr.getHub().getName(),
        assertEquals(adapter.getValueAt(0, 6), hub.getName());

        // dr.getState()};

    }

    public void testGetValueAtSegmented() {
        IHub hub = new BaseHub("MyHub123");

        SearchResult searchResult = new SearchResult(hub, "timowest", "eee",
                settings, 0);

        searchResult.setFileSize(1000);
        DownloadRequest request = new MyDownloadRequest(searchResult, settings);

        request.setSegment(new Point(10, 30));

        requestsModel.getAllDownloads().add(request);

        for (int i = 0; i < adapter.getColumnCount(); i++) {
            System.out.println(adapter.getValueAt(0, i));
        }

        // sr.getFilename(),
        assertEquals(adapter.getValueAt(0, 0), "eee");
        // new Long(dr.getTempFileSize()),
        assertEquals(adapter.getValueAt(0, 2), new Long(13));
        // new Long(sr.getFileSize()),
        assertEquals(adapter.getValueAt(0, 3), new Long(20));
        // sr.getNick(),
        assertEquals(adapter.getValueAt(0, 4), "timowest");
        // sr.getHost(),
        // ?
        // sr.getHub().getName(),
        assertEquals(adapter.getValueAt(0, 6), hub.getName());

        // dr.getState()};

    }

    private class MyDownloadRequest extends DownloadRequest {

        /**
         * @param _searchResult
         * @param _localFile
         * @param _settings
         */
        public MyDownloadRequest(SearchResult _searchResult, File _localFile,
                ISettings _settings) {
            super(_searchResult, _localFile, _settings);

        }

        /**
         * @param searchResult
         * @param settings
         */
        public MyDownloadRequest(SearchResult searchResult, ISettings settings) {
            super(searchResult, settings);
        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.net.DownloadRequest#getTempFileSize()
         */
        public long getTempFileSize() {
            return 13;
        }

    }

}