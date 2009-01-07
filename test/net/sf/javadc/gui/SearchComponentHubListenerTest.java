/*
 * Created on 23.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.gui;

import java.util.List;

import javax.swing.JTabbedPane;

import junit.framework.TestCase;
import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.gui.model.RowTableModel;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISearchRequestFactory;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.HubListener;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchRequestFactory;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.AllHubs;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SearchComponentHubListenerTest extends TestCase {

    protected SearchComponent searchComponent;

    protected SearchRequest searchRequest1, searchRequest2;

    protected List models, searchRequests;

    // external components
    private ISearchRequestFactory factory = new SearchRequestFactory();

    private IHub hub = new BaseHub();

    protected ISettings settings = new BaseSettings(true);

    private IHubManager hubManager = new HubManager();

    private ISegmentManager segmentManager = new SegmentManager(settings);

    protected IDownloadManager downloadManager = new DownloadManager(
            hubManager, segmentManager);

    protected IHub allHubs = new AllHubs(hubManager);

    /**
     * Constructor for SearchComponentHubListenerTest.
     * 
     * @param arg0
     */
    public SearchComponentHubListenerTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        searchComponent = new SearchComponent(hub, settings, downloadManager);

        models = searchComponent.getModels();
        searchRequests = searchComponent.getSearchRequests();

        initialTesting();
    }

    protected void initialTesting() {
        // setting up search requests
        assertEquals(models.size(), 1);
        assertEquals(searchRequests.size(), 0);

        searchRequest1 = factory.createFromQuery("F?F?0?1?eeee");
        // searchComponent.addSearch(searchRequest1);
        // assertEquals(models.size(), 1);
        // assertEquals(searchRequests.size(), 1);

        searchRequest2 = factory.createFromQuery("F?F?0?1?ffff");
        // searchComponent.addSearch(searchRequest2);
        // assertEquals(models.size(), 2);
        // assertEquals(searchRequests.size(), 2);

        searchRequest2.setFreeSlots(true);

        HubListener hubListener = searchComponent.getHubListener();

        // results with free slots

        hubListener.searchResultAdded(hub, new SearchResult(hub, "timowest",
                "eeee.gif", settings, 1), searchRequest1);

        hubListener.searchResultAdded(hub, new SearchResult(hub, "timowest",
                "ffff.gif", settings, 1), searchRequest2);

        hubListener.searchResultAdded(hub, new SearchResult(hub, "timowest",
                "eeee.gif", settings, 1), searchRequest1);

        hubListener.searchResultAdded(hub, new SearchResult(hub, "timowest",
                "ffff.gif", settings, 1), searchRequest2);

        // result without a free slot

        hubListener.searchResultAdded(hub, new SearchResult(hub, "timowest",
                "ffff.gif", settings, 0), searchRequest2);

        // waits a certain amount of time for the update to be triggered when
        // new results are added
        try {
            Thread.sleep(ConstantSettings.SEARCHRESULTS_UPDATEINTERVAL + 500);

        } catch (InterruptedException e) {

            // ?
        }

    }

    public void testFull() {

        _testinitial();
        _testTabTitles();
        _testSearchRequestCount();
        _testModelCount();
        _testSearchComponentTabCount();
        _testModels();
    }

    // hidden

    public void _testinitial() {
        // initialTesting();

    }

    // hidden

    public void _testTabTitles() {
        // initialTesting();

        JTabbedPane tabbedPane = searchComponent.getTabbedPane();

        String title = tabbedPane.getTitleAt(0);

        System.out.println(title);
        assertEquals(title, "ffff (2 hits)");

        title = tabbedPane.getTitleAt(1);
        System.out.println(title);
        assertEquals(title, "eeee (2 hits)");

    }

    // hidden

    public void _testSearchRequestCount() {
        // initialTesting();

        assertEquals(searchComponent.getSearchRequests().size(), 2);
    }

    // hidden

    public void _testModelCount() {
        // initialTesting();

        assertEquals(searchComponent.getModels().size(), 2);
    }

    // hidden

    public void _testSearchComponentTabCount() {
        // initialTesting();

        assertEquals(searchComponent.getTabbedPane().getTabCount(), 2);
    }

    // hidden

    public void _testModels() {
        // initialTesting();

        List models = searchComponent.getModels();
        RowTableModel model1 = (RowTableModel) models.get(0); // ffff
        RowTableModel model2 = (RowTableModel) models.get(1); // eeee

        assertEquals(models.size(), 2);

        assertEquals(model1.getRowCount(), 2);
        assertEquals(model2.getRowCount(), 2);

    }

}