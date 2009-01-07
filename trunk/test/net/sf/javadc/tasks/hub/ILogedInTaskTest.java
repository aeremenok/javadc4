/*
 * Created on 19.11.2004
 */

package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.IHubTask;

/**
 * @author Timo Westk�mper
 */
public class ILogedInTaskTest extends AbstractHubTaskTest {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for ILogedInTaskTest.
     * 
     * @param arg0
     */
    public ILogedInTaskTest(String arg0) {
        super(arg0);
    }

    public void runTask() throws Exception {

        settings.getUserInfo().setNick("Hubert");
        task.setCmdData("Hubert");
        task.runTask();
    }

    public void testRunTask_invalid() throws Exception {

        settings.getUserInfo().setNick("Hubert");
        task.setCmdData("Schubert");

        task.runTask();
        assertEquals("Expected one Exception for having a mismatch "
                + "between Hubert and Schubert", task.getExceptions().size(), 1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new ILogedInTask(settings);
    }

}
