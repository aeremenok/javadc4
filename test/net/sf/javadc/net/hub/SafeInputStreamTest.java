/*
 * Created on 12.6.2005
 */
package net.sf.javadc.net.hub;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

/**
 * @author Timo Westkämper
 */
public class SafeInputStreamTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SafeInputStreamTest.class);
    }

    /**
     * Constructor for SafeInputStreamTest.
     * 
     * @param arg0
     */
    public SafeInputStreamTest(String arg0) {
        super(arg0);
    }

    public void testReading() throws Exception {
        byte[] b = new byte[256];

        for (int i = 0; i < 256; i++)
            b[i] = new Integer(i).byteValue();

        SafeInputStream is = new SafeInputStream(new ByteArrayInputStream(b));

        for (int i = 0; i < 256; i++)
            is.read();

        is.close();
    }

    public void testTransformation1() throws Exception {
        byte[] b = {};
        SafeInputStream is = new SafeInputStream(new ByteArrayInputStream(b));

        assertEquals(is.transform(0), 32);

        assertEquals(is.transform(31), 32);
        assertEquals(is.transform(33), 33);
    }

    public void testTransformation2() throws Exception {
        byte[] b = {};
        SafeInputStream is = new SafeInputStream(new ByteArrayInputStream(b));

        assertEquals(is.transform(127), 127);
        assertEquals(is.transform(128), 32);
    }

    public void testTransformation3() throws Exception {
        byte[] b = {};
        SafeInputStream is = new SafeInputStream(new ByteArrayInputStream(b));

        assertEquals(is.transform(159), 32);
        assertEquals(is.transform(160), 160);

    }

    public void testTransformation4() throws Exception {
        byte[] b = {};
        SafeInputStream is = new SafeInputStream(new ByteArrayInputStream(b));

        assertEquals(is.transform(255), 255);
    }

    public void _testIteration() throws Exception {
        byte[] b = {};
        SafeInputStream is = new SafeInputStream(new ByteArrayInputStream(b));

        for (int i = 0; i < 256; i++)
            System.out.println(i + " : " + is.transform(i));
    }

}
