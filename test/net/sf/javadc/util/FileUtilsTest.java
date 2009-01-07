/*
 * Created on 19.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.util;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class FileUtilsTest extends TestCase {

    /**
     * Constructor for FileUtilsTest.
     * 
     * @param arg0
     */
    public FileUtilsTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

    }

    public void testLastSlashes() {

    }

    public void testConvertSlashes() {

    }

    public void testName() {

    }

    public void testNameNoExtensions() {

    }

    public void testExtension() {

    }

    public void testPath() {

    }

    public void testPathDepth() {

    }

    public void testSimilar() {

        String f1 = "Batman.Begins.iNTERNAL.TS.XviD-CRDS CD1.avi";

        String f2 = "Batman.Begins CD2.avi";
        String f3 = "Batman Begins - CD2.avi";

        assertFalse(FileUtils.isSimilar(f1, f2));
        assertTrue(FileUtils.isSimilar(f2, f3));

    }

}