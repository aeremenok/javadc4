/*
 * Created on 2.9.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net;

import java.util.ArrayList;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class BrowseListBuilderTest extends TestCase {

    private BrowseListBuilder browseListBuilder;

    /**
     * Constructor for BrowseListBuilderTest.
     * 
     * @param arg0
     */
    public BrowseListBuilderTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        browseListBuilder = null;

    }

    public void testCreation() throws Exception {
        browseListBuilder = new BrowseListBuilder(new ArrayList());

        assertTrue(browseListBuilder != null);

    }

    // TODO : create more tests

}