/*
 * Created on 22.3.2005
 */
package net.sf.javadc.tasks.client;

import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.net.ShareManager;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper
 */
public class IGetZBlockTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(IGetZBlockTaskTest.class);
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
     * Constructor for IGetZBlockTaskTest.
     * 
     * @param arg0
     */
    public IGetZBlockTaskTest(String arg0) {
        super(arg0);
    }

    public void runTask() {
        ITaskManager taskManager = new TaskManager();
        IShareManager shareManager = new ShareManager(settings, taskManager);

        task = new IGetZBlockTask(shareManager, settings);
        task.setClientConnection(clientConnection);
        task.runTask();
    }

}
