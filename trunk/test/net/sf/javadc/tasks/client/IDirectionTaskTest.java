/*
 * Created on 22.3.2005
 */

package net.sf.javadc.tasks.client;

import net.sf.javadc.net.client.ConnectionInfo;

/**
 * @author Timo Westk�mper
 */
public class IDirectionTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(IDirectionTaskTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for IDirectionTaskTest.
     * 
     * @param arg0
     */
    public IDirectionTaskTest(String arg0) {
        super(arg0);
    }

    public void runTask() {
        testDownloadDirection();
    }

    public void testDownloadDirection() {
        creation("Download ");

        ConnectionInfo connectionInfo = clientConnection.getConnectionInfo();
        assertTrue("connectionInfo should not have been null",
                connectionInfo != null);

        assertEquals(connectionInfo.getCurrentDirection(), "Upload");
    }

    public void testUploadDirection() {
        creation("Upload ");

        ConnectionInfo connectionInfo = clientConnection.getConnectionInfo();
        assertTrue("connectionInfo should not have been null",
                connectionInfo != null);

        assertEquals(connectionInfo.getCurrentDirection(), "Download");
    }

    public void testInvalidDirection() {
        creation("Invalid ");

        assertEquals(
                "Expected one Exception for having supplied a wrong direction "
                        + "parameter", task.getExceptions().size(), 1);
    }

    public void creation(String direction) {
        task = new IDirectionTask();
        task.setCmdData(direction);
        task.setClientConnection(clientConnection);
        task.runTask();
    }

}
