/*
 * Created on 6.4.2005
 */
package net.sf.javadc.util;

import junit.framework.TestCase;
import net.sf.javadc.util.hash.HashInfo;

/**
 * @author Timo Westk�mper
 */
public class FileInfoTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(FileInfoTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for FileInfoTest.
     * 
     * @param arg0
     */
    public FileInfoTest(String arg0) {
        super(arg0);
    }

    public void testConstruction() {

        FileInfo info = new FileInfo();
        info.setHash(new HashInfo());
    }

}
