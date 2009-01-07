/*
 * Created on 12.1.2005
 */

package net.sf.javadc.gui;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westk�mper
 */
public class SearchPanelTest extends TestCase {

    private IHub hub = new BaseHub();

    private ISettings settings = new BaseSettings(true);

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(
            new HubManager(), segmentManager);

    private SearchComponent searchComponent = new SearchComponent(hub,
            settings, downloadManager);

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for SearchPanelTest.
     * 
     * @param arg0
     */
    public SearchPanelTest(String arg0) {
        super(arg0);
    }

    public void testSizeMultiplierWorks() {

        SearchPanel searchPanel = new SearchPanel(new BaseHub(),
                searchComponent);

        // bytes
        searchPanel.getSizeField().setText("5");
        assertEquals(searchPanel.getFileSize(), 5);

        // bytes
        searchPanel.getSizeMultiplier().setSelectedIndex(0);
        assertEquals(searchPanel.getFileSize(), 5);

        // kbytes
        searchPanel.getSizeMultiplier().setSelectedIndex(1);
        assertEquals(searchPanel.getFileSize(), 5 * 1024);

        // Mbytes
        searchPanel.getSizeMultiplier().setSelectedIndex(2);
        assertEquals(searchPanel.getFileSize(), 5 * 1024 * 1024);
    }
}
