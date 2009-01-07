/*
 * Created on 3.3.2005
 */
package net.sf.javadc;

import java.io.RandomAccessFile;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper
 */
public class FileTruncationTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(FileTruncationTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for FileTruncationTest.
     * 
     * @param arg0
     */
    public FileTruncationTest(String arg0) {
        super(arg0);
    }

    public void testTruncation() throws Exception {

        String filename = "E:\\_Temporary\\dmd-killbill2-cd1.avi";

        RandomAccessFile file = new RandomAccessFile(filename, "rw");

        file.setLength(200 * 1024 * 1024);
        file.close();
    }

}
