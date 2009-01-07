/*
 * Created on 22.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.gui;

import java.util.List;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISearchRequestFactory;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchRequestFactory;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SearchComponentTest extends TestCase {

    private SearchComponent searchComponent;

    // external components
    private IHub hub = new BaseHub();

    private ISettings settings = new BaseSettings(true);

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(
            new HubManager(), segmentManager);

    /**
     * Constructor for SearchComponentTest.
     * 
     * @param arg0
     */
    public SearchComponentTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        searchComponent = new SearchComponent(hub, settings, downloadManager);

    }

    // a is F is size doesn't matter, else T
    // b is F if size is "at least", else T (at most)
    // c is the size in byte
    // d is data type:
    // eeee is the pattern to find
    public void testSearchRequestsInRightOrder() {
        ISearchRequestFactory factory = new SearchRequestFactory();

        SearchRequest searchRequest1;
        SearchRequest searchRequest2;

        searchRequest1 = factory.createFromQuery("F?F?0?1?eeee");
        searchComponent.addSearch(searchRequest1);

        searchRequest2 = factory.createFromQuery("F?F?0?1?ffff");
        searchComponent.addSearch(searchRequest2);

        List searchRequests = searchComponent.getSearchRequests();

        assertTrue(searchRequests.size() == 2);

        // no null element add the back
        // assertEquals(searchRequests.get(2) == null, true);
        // 1
        assertEquals(searchRequests.get(1).equals(searchRequest1), true);

        // 2
        assertEquals(searchRequests.get(0).equals(searchRequest2), true);

    }

    public void testFindBySearchResults() {
        ISearchRequestFactory factory = new SearchRequestFactory();

        // setting up search requests
        SearchRequest searchRequest1;

        // setting up search requests
        SearchRequest searchRequest2;

        searchRequest1 = factory.createFromQuery("F?F?0?1?eeee");
        searchComponent.addSearch(searchRequest1);

        searchRequest2 = factory.createFromQuery("F?F?0?1?ffff");
        searchComponent.addSearch(searchRequest2);

        // search results
        SearchResult searchResult1;

        // search results
        SearchResult searchResult2;

        // search results
        SearchResult searchResult3;

        searchResult1 = new SearchResult(hub, "timowest", "eeee.gif", settings,
                1);

        searchResult2 = new SearchResult(hub, "timowest", "ffff.gif", settings,
                1);

        searchResult3 = new SearchResult(hub, "timowest", "gggg", settings, 1);

        // List models = searchComponent.getModels();

        assertEquals(searchComponent.findSearchRequestIndex(searchResult2), 0);

        assertEquals(searchComponent.findSearchRequestIndex(searchResult1), 1);

        // no matching search request found
        assertEquals(searchComponent.findSearchRequestIndex(searchResult3), -1);

    }

}