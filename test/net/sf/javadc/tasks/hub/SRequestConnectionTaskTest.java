/*
 * Created on 2.4.2005
 */

package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IHubTask;
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.net.client.ClientManager;
import net.sf.javadc.net.client.ConnectionManager;

/**
 * @author Timo Westk�mper
 */
public class SRequestConnectionTaskTest extends AbstractHubTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SRequestConnectionTaskTest.class);
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
     * Constructor for SRequestConnectionTaskTest.
     * 
     * @param arg0
     */
    public SRequestConnectionTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        IConnectionManager clientConnectionManager = new ConnectionManager(
                settings);

        // mockup
        IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

        IClientManager clientManager = new ClientManager(settings, taskManager,
                clientConnectionManager, clientTaskFactory);

        /*
         * ClientManager(ISettings _settings, ITaskManager _taskManager,
         * IConnectionManager _clientConnectionManager, IClientTaskFactory
         * _clientTaskFactory)
         */
        return new SRequestConnectionTask(settings, clientManager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#testRunTask()
     */
    public void runTask() throws Exception {
        task.runTask();

    }

}
