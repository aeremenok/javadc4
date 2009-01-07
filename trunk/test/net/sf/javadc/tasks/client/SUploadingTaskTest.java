/*
 * Created on 2.4.2005
 */
package net.sf.javadc.tasks.client;

/**
 * @author Timo Westk�mper
 */
public class SUploadingTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SUploadingTaskTest.class);
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
     * Constructor for SUploadingTaskTest.
     * 
     * @param arg0
     */
    public SUploadingTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.client.AbstractClientTaskTest#testRunTask()
     */
    public void runTask() {
        task = new SUploadingTask();
        task.setClientConnection(clientConnection);
        task.runTask();

    }

}
