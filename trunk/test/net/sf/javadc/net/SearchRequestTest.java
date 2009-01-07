/*
 * Created on 19.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.ISearchRequestFactory;
import net.sf.javadc.util.PerformanceContext;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SearchRequestTest extends TestCase {

    private ISearchRequestFactory factory;

    /**
     * Constructor for SearchRequestTest.
     * 
     * @param arg0
     */
    public SearchRequestTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        factory = new SearchRequestFactory();
    }

    public void testCreateFromQuery() {

        // takes a string like "a?b?c?d?eeeee" where

        // a is F is size doesn't matter, else T
        // b is F if size is "at least", else T (at most)
        // c is the size in byte
        // d is data type:
        // eeee is the pattern to find

        String searchString = "T?T?5?1?eeee";
        SearchRequest sr = factory.createFromQuery(searchString);

        assertEquals(sr.getNamePattern().equals("eeee"), true);
        assertEquals(sr.getSize(), 5);
        assertEquals(sr.isMinSize(), false);
        assertEquals(sr.getFileType(), 1);

        assertEquals(sr.toString().equals(searchString), true);

        searchString = "F?F?10?2?eeee";
        sr = factory.createFromQuery(searchString);

        // file size is set to 0, because it doesn't matter
        assertEquals(sr.getSize(), 0);
        assertEquals(sr.getFileType(), 2);

        assertEquals(sr.toString().equals("F?F?0?2?eeee"), true);

        // test invalid results

        assertTrue(factory.createFromQuery("") != null);

    }

    public void testSizeMatching() {

        // at least 1000 bytes
        SearchRequest sr1 = factory.createFromQuery("T?F?1000?1?eeee");

        // at most 1000 bytes
        SearchRequest sr2 = factory.createFromQuery("T?T?1000?1?eeee");

        // size doesn't matter
        SearchRequest sr3 = factory.createFromQuery("F?T?1000?1?eeee");

        assertEquals(sr1.sizeMatches(2000), true);
        assertEquals(sr1.sizeMatches(500), false);

        assertEquals(sr2.sizeMatches(2000), false);
        assertEquals(sr2.sizeMatches(500), true);

        assertEquals(sr3.sizeMatches(2000), true);
        assertEquals(sr3.sizeMatches(500), true);

    }

    public void testFileTypeMatches() {
        // all
        // SearchRequest sr1 = factory.createFromQuery("T?F?1000?1?eeee");

        // snd
        SearchRequest sr2 = factory.createFromQuery("T?T?1000?2?eeee");

        assertEquals(sr2.fileTypeMatches("eeee.wav"), true);
        assertEquals(sr2.fileTypeMatches("eeee.MP3"), true);

        // cmp
        SearchRequest sr3 = factory.createFromQuery("F?T?1000?3?eeee");

        assertEquals(sr3.fileTypeMatches("eeee.zip"), true);
        assertEquals(sr3.fileTypeMatches("eeee.RAR"), true);

        // doc
        SearchRequest sr4 = factory.createFromQuery("T?F?1000?4?eeee");

        assertEquals(sr4.fileTypeMatches("eeee.pdf"), true);
        assertEquals(sr4.fileTypeMatches("eeee.DOC"), true);

        // exec
        SearchRequest sr5 = factory.createFromQuery("T?T?1000?5?eeee");

        assertEquals(sr5.fileTypeMatches("eeee.exe"), true);
        assertEquals(sr5.fileTypeMatches("eeee.COM"), true);

        // pic
        SearchRequest sr6 = factory.createFromQuery("F?T?1000?6?eeee");

        assertEquals(sr6.fileTypeMatches("eeee.png"), true);
        assertEquals(sr6.fileTypeMatches("eeee.GIF"), true);

        // vid
        SearchRequest sr7 = factory.createFromQuery("T?F?1000?7?eeee");

        assertEquals(sr7.fileTypeMatches("eeee.mpg"), true);
        assertEquals(sr7.fileTypeMatches("eeee.AVI"), true);

    }

    public void testEquality() {

        // equals
        SearchRequest sr1 = factory.createFromQuery("T?F?1000?7?eeee");
        SearchRequest sr2 = factory.createFromQuery("T?F?1000?7?eeee");
        assertEquals(sr1, sr2);

        // not equal
        sr1.setNamePattern("fifth element");
        sr2.setNamePattern("fifth");

        assertFalse(sr1.equals(sr2));
        assertFalse(sr2.equals(sr1));

        // not equal
        sr2.setNamePattern("element");

        assertFalse(sr1.equals(sr2));
        assertFalse(sr2.equals(sr1));
    }

    public void testEquality_Speed() {

        SearchRequest sr1 = factory.createFromQuery("T?F?1000?7?eeee");
        SearchRequest sr2 = factory.createFromQuery("T?F?1000?7?eeee");

        PerformanceContext cont = new PerformanceContext(
                "SearchRequest#equals(Object)").start();
        assertTrue(sr1.equals(sr2));
        System.out.println(cont.end());

        sr1.setNamePattern("fifth element");
        sr2.setNamePattern("fifth");

        cont.start();
        assertFalse(sr1.equals(sr2));
        System.out.println(cont.end());

    }

    public void testToString_Speed() {

        SearchRequest sr1 = factory.createFromQuery("T?F?1000?7?eeee");
        SearchRequest sr2 = factory.createFromQuery("T?F?1000?7?eeee");

        PerformanceContext cont = new PerformanceContext(
                "SearchRequest#toString().equals(SearchRequest#toString())")
                .start();
        assertTrue(sr1.toString().equals(sr2.toString()));
        System.out.println(cont.end());

        sr1.setNamePattern("fifth element");
        sr2.setNamePattern("fifth");

        cont.start();
        assertFalse(sr1.toString().equals(sr2.toString()));
        System.out.println(cont.end());
    }

    public void testToString() {

        String[] encoded = { "T?F?1000?1?eeee", "T?T?1000?2?eeee",
                "T?F?1000?4?eeee", "T?T?1000?5?eeee", "T?F?1000?7?eeee" };

        for (int i = 0; i < encoded.length; i++) {
            System.out.println("testing " + i);

            SearchRequest sr = factory.createFromQuery(encoded[i]);

            // direct creation
            assertEquals(sr.toString(), encoded[i]);

            // cached version
            assertEquals(sr.toString(), encoded[i]);
        }
    }

    public void testTTHSearchToString() {

        String encoded = "T?T?1000?9?TTH:483907258";

        SearchRequest sr = factory.createFromQuery(encoded);

        System.out.println(sr);
        assertEquals(encoded, sr.toString());
    }

}