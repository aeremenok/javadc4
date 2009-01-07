/*
 * Created on 8.11.2004
 */

package net.sf.javadc.util;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper
 */
public class TokenInputStreamTest extends TestCase {

    private TokenInputStream tokenInputStream;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for TokenInputStreamTest.
     * 
     * @param arg0
     */
    public TokenInputStreamTest(String arg0) {
        super(arg0);
    }

    public void testReading() throws Exception {
        byte[] bytes = new String("$UGetBlock shj � akh|Test1|Test2|")
                .getBytes("utf-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        tokenInputStream = new TokenInputStream(inputStream, '|');

        String token1 = tokenInputStream.readToken();
        System.out.println("Token 1 : " + token1);

        String token2 = tokenInputStream.readToken();
        System.out.println("Token 2 : " + token2);

        String token3 = tokenInputStream.readToken();
        System.out.println("Token 3 : " + token3);

        assertEquals(token1, "$UGetBlock shj � akh");
        assertEquals(token2, "Test1");
        assertEquals(token3, "Test2");

    }

    public void testRoundTrip() throws Exception {
        String command = "$UGetBlock";
        byte[] bytes = command.getBytes("utf-8");
        String commandConv = new String(bytes, "iso-8859-1");

        assertEquals(command, commandConv);

        command = "�";
        bytes = command.getBytes("utf-8");
        commandConv = new String(bytes, "iso-8859-1");

        assertFalse(command.equals(commandConv));
    }

    public String testCharset() {
        throw new RuntimeException(
                "TODO : Test correct handling of ISO-8859-1 and UTF-8.");
    }

}