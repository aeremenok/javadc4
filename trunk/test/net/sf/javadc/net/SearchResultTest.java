/*
 * Created on 22.8.2004
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
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SearchResultTest extends TestCase {

    // private ISettings settings = new BaseSettings(true);
    private ISettings settings = new Settings();

    private IHub hub = new BaseHub();

    private SearchRequest searchRequest1;

    private SearchRequest searchRequest2;

    private SearchResult searchResult1, searchResult2, searchResult3,
            searchResult4;

    private String tempDir;

    /**
     * Constructor for SearchResultTest.
     * 
     * @param arg0
     */
    public SearchResultTest(String arg0) {
        super(arg0);

        if (File.separatorChar == '/') {
            tempDir = "/tmp";
        } else {
            tempDir = "C:\\Temp";
        }

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        // set the directory for temporary downloads
        settings.setTempDownloadDir(tempDir);

        // free slots
        searchRequest1 = new SearchRequest("eee", 1, 0, false, true);

        // no free slots
        searchRequest2 = new SearchRequest("eee", 1, 0, false, false);

        searchResult1 = new SearchResult(hub, "timowest", "eee", settings, 0);

        searchResult2 = new SearchResult(hub, "timowest", "eee", settings, 1);

        searchResult3 = new SearchResult(hub, "timowest", "1\\fff", settings, 1);

        searchResult4 = new SearchResult(hub, "timowest", "1/ggg", settings, 1);

    }

    public void testCreationFromStringRepresentation() throws Exception {
        String cmdData = new StringBuffer("User1 mypath\\motd.txt").append(
                (char) 5).append("437 3/4").append((char) 5).append(
                "Testhub (10.10.10.10:411").append((char) 5).append("User2")
                .toString();

        SearchResult sr = new SearchResult(hub, cmdData, settings);

        checkSearchResult(sr);
    }

    public void testCreationFromStringRepresentationWithTTH() throws Exception {
        String cmdData = new StringBuffer("User1 mypath\\motd.txt").append(
                (char) 5).append("437 3/4").append((char) 5).append(
                "TTH:XVIVD7G7HGFY2EVOV7ETVMSYUMT5QEEPY4CJUOA (10.10.10.10:411")
                .append((char) 5).append("User2").toString();

        SearchResult sr = new SearchResult(hub, cmdData, settings);
        checkSearchResult(sr);
        assertEquals(sr.getTTH(), "XVIVD7G7HGFY2EVOV7ETVMSYUMT5QEEPY4CJUOA");
        System.out.println(sr.getSearchResponse(hub));
        assertTrue(sr.getSearchResponse(hub).indexOf("TTH:" + sr.getTTH()) > -1);

    }

    public void testCreationFromStringRepresentation2() throws Exception {

        // totalnibeststroj2k4 Downlo
        // ads\## xxx ##\pictures\girls\Playboy Centerfolds 1953-1999 0/10?DANET
        // #2 (Sylvester)
        // (213.204.154.9)

        String cmdData = new StringBuffer("totalnibeststroj2k4 ").append(
                "Downloads\\## xxx ##\\pictures\\girls\\Playboy "
                        + "Centerfolds 1953-1999 0/10").append((char) 5)
                .append("DANET #2 (Sylvester) (213.204.154.9)").toString();

        SearchResult sr = new SearchResult(hub, cmdData, settings);

        assertEquals(sr.getFilename(),
                "Downloads\\## xxx ##\\pictures\\girls\\"
                        + "Playboy Centerfolds 1953-1999");
        assertEquals(sr.getNick(), "totalnibeststroj2k4");
        assertEquals(sr.getFreeSlotCount(), 0);
        assertEquals(sr.getMaxSlotCount(), 10);
        assertEquals(sr.getFileSize(), 0);

    }

    public void testCreationFromStringRepresentation3() throws Exception {
        // (1.5MB)Road_Rash E__\PC Ga
        // mes\Playboy - The Mansion 0/5?DANET #2 (Sylvester) (213.204.154.9)

        String cmdData = new StringBuffer("(1.5MB)Road_Rash ").append(
                "Games\\Playboy - The Mansion 0/5").append((char) 5).append(
                "DANET #2 (Sylvester) (213.204.154.9)").toString();

        SearchResult sr = new SearchResult(hub, cmdData, settings);

        assertEquals(sr.getFilename(), "Games\\Playboy - The Mansion");
        assertEquals(sr.getNick(), "(1.5MB)Road_Rash");
        assertEquals(sr.getFreeSlotCount(), 0);
        assertEquals(sr.getMaxSlotCount(), 5);
        assertEquals(sr.getFileSize(), 0);
    }

    public void testMatchingRegexPattern() {

        String filename = "Games\\Playboy - The Mansion 0/5";
        assertTrue(filename.matches(".+\\s\\d+/\\d+"));
        assertFalse(filename.matches(".+\\s\\d+\\s\\d+/\\d+"));

    }

    private void checkSearchResult(SearchResult sr) {
        assertEquals(sr.getNick(), "User1");
        assertEquals(sr.getFilename(), "mypath\\motd.txt");
        assertEquals(sr.getFileSize(), 437);
        assertEquals(sr.getFreeSlotCount(), 3);
        assertEquals(sr.getMaxSlotCount(), 4);
    }

    public void testMatching() {
        // free slots
        assertFalse(searchRequest1.matches(searchResult1));
        assertTrue(searchRequest1.matches(searchResult2));

        // no free slots
        assertTrue(searchRequest2.matches(searchResult1));
        assertTrue(searchRequest2.matches(searchResult2));

    }

    public void testGetTempFileSize() {
        _testGetTempFileSize(searchResult1, "eee");
        _testGetTempFileSize(searchResult3, "fff");
        _testGetTempFileSize(searchResult4, "ggg");
    }

    private void _testGetTempFileSize(SearchResult searchResult, String pattern) {

        // to ensure that file does not exist
        new File(settings.getTempDownloadDir(), pattern).delete();

        assertEquals(searchResult.getTempFileSize(), 0);

        // assertEquals(searchResult1.getFilename(), "C:\\Temp\\eee");

        File file = new File(settings.getTempDownloadDir(), pattern);

        TestFileHelper.writeIntoFile(file, 5);
        assertEquals(file.length(), 5);

        // ensure that the file size is reflected in the SearchResult
        assertEquals(searchResult.getTempFileSize(), 5);

        file.delete();

        // ensure that the file size is reflected in the SearchResult
        assertEquals(searchResult.getTempFileSize(), 0);
    }

}