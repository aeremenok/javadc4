/*
 * Created on 26.11.2004
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
public class IRevConnectToMeTaskTest extends AbstractHubTaskTest {

    private final IConnectionManager clientConnectionManager = new ConnectionManager(
            settings);

    private final IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    private IClientManager clientManager = new ClientManager(settings,
            taskManager, clientConnectionManager, clientTaskFactory);

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for IRevConnectToMeTaskTest.
     * 
     * @param arg0
     */
    public IRevConnectToMeTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new IRevConnectToMeTask(settings, clientManager);
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
