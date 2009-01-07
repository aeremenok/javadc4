/*
 * Created on 19.11.2004
 */
package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IHubTask;
import net.sf.javadc.mockups.BaseClientManager;

/**
 * @author Timo Westk�mper
 */
public class IConnectToMeTaskTest extends AbstractHubTaskTest {

    private StringBuffer buffer = new StringBuffer();

    private IClientManager clientManager = new BaseClientManager();

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

    }

    /**
     * Constructor for IConnectToMeTaskTest.
     * 
     * @param arg0
     */
    public IConnectToMeTaskTest(String arg0) {
        super(arg0);

    }

    public void runTask() {
        task.runTask();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new IConnectToMeTask(clientManager) {

            protected void sendCommand(String command, String data) {
                buffer.append(command).append(data).append("\n");

            }

        };
    }

}
