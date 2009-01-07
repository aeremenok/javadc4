/*
 * Created on 17.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net;

import junit.framework.TestCase;
import net.sf.javadc.util.FileInfo;
import net.sf.javadc.util.FileInfoComparator;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FileInfoComparatorTest extends TestCase {

    private FileInfo info1, info2, info3, info4;

    private FileInfo info11, info22;

    /**
     * Constructor for FileInfoComparatorTest.
     * 
     * @param arg0
     */
    public FileInfoComparatorTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        info1 = new FileInfo("C:\\Timo\\full.proj", "Timo\\full.proj", 0);

        info11 = new FileInfo("/Timo/full.proj", "Timo/full.proj", 0);

        info2 = new FileInfo("C:\\Timo\\full1.proj", "Timo\\full1.proj", 0);

        info22 = new FileInfo("/Timo/full1.proj", "Timo/full1.proj", 0);

        info3 = new FileInfo("C:\\Timo\\test\\full1.proj",
                "Timo\\test\\full1.proj", 0);

        info4 = new FileInfo("C:\\Timo\\test\\full2.proj",
                "Timo\\test\\full2.proj", 0);
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testComparator() {

        FileInfoComparator comp = new FileInfoComparator();

        assertEquals(comp.compare(info1, info1), 0);
        assertEquals(comp.compare(info1, info11), 0);
        assertEquals(comp.compare(info1, info2) < 0, true);
        assertEquals(comp.compare(info1, info22) < 0, true);
        assertEquals(comp.compare(info1, info3) < 0, true);
        assertEquals(comp.compare(info1, info4) < 0, true);

        assertEquals(comp.compare(info2, info1) > 0, true);
        assertEquals(comp.compare(info2, info11) > 0, true);
        assertEquals(comp.compare(info2, info2), 0);
        assertEquals(comp.compare(info2, info22), 0);
        assertEquals(comp.compare(info2, info3) < 0, true);
        assertEquals(comp.compare(info2, info4) < 0, true);

        assertEquals(comp.compare(info3, info1) > 0, true);
        assertEquals(comp.compare(info3, info11) > 0, true);
        assertEquals(comp.compare(info3, info2) > 0, true);
        assertEquals(comp.compare(info3, info22) > 0, true);
        assertEquals(comp.compare(info3, info3), 0);
        assertEquals(comp.compare(info3, info4) < 0, true);

        assertEquals(comp.compare(info4, info1) > 0, true);
        assertEquals(comp.compare(info4, info11) > 0, true);
        assertEquals(comp.compare(info4, info2) > 0, true);
        assertEquals(comp.compare(info4, info22) > 0, true);
        assertEquals(comp.compare(info4, info3) > 0, true);
        assertEquals(comp.compare(info4, info4), 0);

    }

}