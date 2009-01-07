/*
 * Created on 2.4.2005
 */
package net.sf.javadc.tasks.client;

/**
 * @author Timo Westk�mper
 */
public class SRemotelyQueuedTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SRemotelyQueuedTaskTest.class);
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
     * Constructor for SRemotelyQueuedTaskTest.
     * 
     * @param arg0
     */
    public SRemotelyQueuedTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.client.AbstractClientTaskTest#testRunTask()
     */
    public void runTask() {
        task = new SRemotelyQueuedTask();
        task.setClientConnection(clientConnection);
        task.runTask();

    }

}
