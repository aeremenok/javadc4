/*
 * Created on 19.11.2004
 */
package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.IHubTask;

/**
 * @author Timo Westk�mper
 */
public class IBadPassTaskTest extends AbstractHubTaskTest {

    private StringBuffer buffer = new StringBuffer();

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for IBadPassTaskTest.
     * 
     * @param arg0
     */
    public IBadPassTaskTest(String arg0) {
        super(arg0);
    }

    public void runTask() throws Exception {
        task.runTask();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new IBadPassTask() {

            protected void sendCommand(String command, String data) {
                buffer.append(command).append(data).append("\n");

            }
        };
    }

}
