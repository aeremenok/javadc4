/*
 * Created on 18.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net;

import java.io.File;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseHub;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DownloadRequestTest extends TestCase {

    // external components

    // private final ISettings settings = new BaseSettings(true);
    private final ISettings settings = new Settings();

    private final IHub hub = new BaseHub();

    /**
     * Constructor for DownloadRequestTest.
     * 
     * @param arg0
     */
    public DownloadRequestTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        settings.setTempDownloadDir("/downloads/");
    }

    public void testCreation() {

        new DownloadRequest(new SearchResult(), settings);

        new DownloadRequest(new SearchResult(), new String("test"), settings);

        new DownloadRequest(new SearchResult(), new File("test"), settings);

    }

    public void testMatchingDirectories1() {

        DownloadRequest dr1 = new DownloadRequest(new SearchResult(), new File(
                "test"), settings);

        // ensure that the temp download directory is used

        assertEquals(dr1.getLocalFilename(), trans("\\downloads\\test"));

    }

    public void testMatchingDirectories2() {
        SearchResult searchResult = new SearchResult(hub, "timowest", "eee",
                settings, 0);

        DownloadRequest dr2 = new DownloadRequest(searchResult, settings);

        // ensure that the temp download directory is used

        assertEquals(dr2.getLocalFilename(), trans("\\downloads\\eee"));
    }

    public void testMatchingDirectories3() {
        SearchResult searchResult = new SearchResult(hub, "timowest",
                "test/eee", settings, 0);

        DownloadRequest dr2 = new DownloadRequest(searchResult, settings);

        // ensure that the temp download directory is used

        assertEquals(dr2.getLocalFilename(), trans("\\downloads\\eee"));

    }

    public void testMatchingDirectories4() {
        SearchResult searchResult = new SearchResult(hub, "timowest",
                trans("test\\eee"), settings, 0);

        DownloadRequest dr2 = new DownloadRequest(searchResult, settings);

        // ensure that the temp download directory is used

        assertEquals(dr2.getLocalFilename(), trans("\\downloads\\eee"));

    }

    public void testToString() {

        // if (searchResult != null) {
        // toString = "DownloadRequest : " + searchResult.toString();
        // } else if (localFilename != null) {
        // toString = "DownloadRequest : " + localFilename.getPath();
        // }

        SearchResult searchResult = new SearchResult(hub, "timowest",
                trans("test\\eee"), settings, 0);

        DownloadRequest dr2 = new DownloadRequest(searchResult, settings);

        // direct creation
        assertEquals(dr2.toString(), "DownloadRequest : " + searchResult);

        // cached version
        assertEquals(dr2.toString(), "DownloadRequest : " + searchResult);
    }

    private String trans(String dir) {
        return dir.replace('\\', File.separatorChar);
    }

    // TODO : more tests

}