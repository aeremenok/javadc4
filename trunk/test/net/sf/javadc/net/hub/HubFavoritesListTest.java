/*
 * Created on 2.9.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.hub;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.IHubFavoritesLoader;
import net.sf.javadc.mockups.BaseHubFavoritesLoader;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class HubFavoritesListTest extends TestCase {

    private IHubFavoritesLoader hubFavoritesLoader = new BaseHubFavoritesLoader();

    private IHubFavoritesList hubFavoritesList;

    /**
     * Constructor for HubFavoritesListTest.
     * 
     * @param arg0
     */
    public HubFavoritesListTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

    }

    public void testCreation() throws Exception {
        hubFavoritesList = new HubFavoritesList(hubFavoritesLoader);

        assertTrue(hubFavoritesList != null);
    }

}