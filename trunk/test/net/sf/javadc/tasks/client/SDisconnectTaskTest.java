/*
 * Created on 2.4.2005
 */
package net.sf.javadc.tasks.client;

import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper
 */
public class SDisconnectTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SDisconnectTaskTest.class);
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
     * Constructor for SDisconnectTaskTest.
     * 
     * @param arg0
     */
    public SDisconnectTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.client.AbstractClientTaskTest#testRunTask()
     */
    public void runTask() {
        ITaskManager taskManager = new TaskManager();
        task = new SDisconnectTask(taskManager);
        task.setClientConnection(clientConnection);
        task.runTask();

    }

}
