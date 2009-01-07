/*
 * Created on 2.4.2005
 */
package net.sf.javadc.tasks.client;

import java.io.ByteArrayInputStream;

import net.sf.javadc.util.TokenInputStream;

/**
 * @author Timo Westk�mper
 */
public class SResumingTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SResumingTaskTest.class);
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
     * Constructor for SResumingTaskTest.
     * 
     * @param arg0
     */
    public SResumingTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.client.AbstractClientTaskTest#testRunTask()
     */
    public void runTask() {
        prepare();

        task = new SResumingTask();
        task.setClientConnection(clientConnection);
        task.runTask();

    }

    private void prepare() {
        // input
        byte[] bytes = new byte[50 * KBYTES];
        TokenInputStream reader = new TokenInputStream(
                new ByteArrayInputStream(bytes), ' ');
        clientConnection.setReader(reader);

        clientConnection.getConnectionInfo().setVerifyResumeSize(25);
    }

}
