/*
 * Created on 10.10.2004
 */

package net.sf.javadc.gui;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author twe
 */
public class FileBrowseComponentTest extends TestCase {

    private ISettings settings = new Settings();

    private IHubManager hubManager = new HubManager();

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(hubManager,
            segmentManager);

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testCreation() throws Exception {
        new FileBrowseComponent(settings, downloadManager);

    }

    // TODO : create more tests

}