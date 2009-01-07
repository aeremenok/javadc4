/*
 * Created on 2.9.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net;

import junit.framework.TestCase;
import net.sf.javadc.util.FileInfo;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FileInfoTest extends TestCase {

    private FileInfo info1, info2;

    /**
     * Constructor for FileInfoTest.
     * 
     * @param arg0
     */
    public FileInfoTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testEquality() throws Exception {
        info1 = new FileInfo("C:\\Timo\\full.proj", "Timo\\full.proj", 250);
        info2 = new FileInfo("/Timo/full.proj", "Timo/full.proj", 250);

        // 1
        assertTrue(info1.equals(info2));

        info2 = new FileInfo("/Timo/full.proj", "Timo/full.proj", 200);

        // 2
        assertFalse(info1.equals(info2));

        info2 = new FileInfo("/Timo/full1.proj", "Timo/full1.proj", 250);

        // 3
        assertFalse(info1.equals(info2));
    }

}