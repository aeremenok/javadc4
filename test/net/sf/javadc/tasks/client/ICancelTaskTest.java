/*
 * Created on 22.3.2005
 */

package net.sf.javadc.tasks.client;

import net.sf.javadc.net.client.ConnectionState;

/**
 * @author Timo Westk�mper
 */
public class ICancelTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ICancelTaskTest.class);
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
     * Constructor for ICancelTaskTest.
     * 
     * @param arg0
     */
    public ICancelTaskTest(String arg0) {
        super(arg0);
    }

    public void runTask() {
        clientConnection.setState(ConnectionState.UPLOADING);
        creation();
    }

    public void testInWrongContext() {
        creation();
        assertEquals("No Exception was found for execution in wrong context",
                task.getExceptions().size(), 1);
    }

    private void creation() {
        task = new ICancelTask();
        task.setClientConnection(clientConnection);
        task.runTask();

        assertEquals("The connectionState has to become ABORTED, but was "
                + clientConnection.getState(), clientConnection.getState(),
                ConnectionState.ABORTED);
    }

}
