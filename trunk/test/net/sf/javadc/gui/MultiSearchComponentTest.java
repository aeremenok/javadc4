/*
 * Created on 14.11.2004
 */

package net.sf.javadc.gui;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.AllHubs;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westk�mper
 */
public class MultiSearchComponentTest extends TestCase {

    // private MultiSearchComponent multiSearchComponent;

    // external components

    private ISettings settings = new BaseSettings(true);

    private IHubManager hubManager = new HubManager();

    private AllHubs allHubs = new AllHubs(hubManager);

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(hubManager,
            segmentManager);

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

    }

    /**
     * Constructor for MultiSearchComponentTest.
     * 
     * @param arg0
     */
    public MultiSearchComponentTest(String arg0) {
        super(arg0);
    }

    public void testCreation() {
        new MultiSearchComponent(allHubs, settings, downloadManager);
    }

    // public void testNoTests() {
    //
    // }

    // TODO : create more tests
}