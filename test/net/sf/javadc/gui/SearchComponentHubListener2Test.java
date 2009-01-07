/*
 * Created on 23.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.gui;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SearchComponentHubListener2Test extends
        SearchComponentHubListenerTest {

    /**
     * Constructor for SearchComponentHubListenerTest.
     * 
     * @param arg0
     */
    public SearchComponentHubListener2Test(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        // searchComponent = new SearchComponent(hub, settings,
        // downloadManager);
        searchComponent = new SearchComponent(allHubs, settings,
                downloadManager);

        models = searchComponent.getModels();
        searchRequests = searchComponent.getSearchRequests();

        initialTesting();
    }

}