/*
 * Created on 12.11.2004
 */
package net.sf.javadc.net;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper
 */
public class MessageTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for MessageTest.
     * 
     * @param arg0
     */
    public MessageTest(String arg0) {
        super(arg0);
    }

    public void testCreation() {
        // String _from, String _to, String _text

        Message message = new Message("Hubert", "Thomas", "Hi Thomas");
        // System.out.println(message.toString());

        assertTrue(message.toString() != null);
    }

    public void testDeescaping() {
        // _text = _text.replaceAll("&#124", "|");
        // return _text.replaceAll("&#36", "$");

        Message m1 = new Message("from", "to", "a&#124;b");
        Message m2 = new Message("from", "to", "a&#36;b");

        assertEquals("a|b", m1.getText());
        assertEquals("a$b", m2.getText());
    }

}
