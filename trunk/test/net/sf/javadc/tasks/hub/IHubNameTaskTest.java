/*
 * Created on 19.11.2004
 */
package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.IHubTask;

/**
 * @author Timo Westk�mper
 */
public class IHubNameTaskTest extends AbstractHubTaskTest {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for IHubNameTaskTest.
     * 
     * @param arg0
     */
    public IHubNameTaskTest(String arg0) {
        super(arg0);
    }

    public void runTask() throws Exception {

        hub.setName("Schubert");
        assertEquals(hub.getName(), "Schubert");

        task.setCmdData("Hubert");

        task.runTask();

        // ensure that the hub name is updated

        assertEquals(hub.getName(), "Hubert");

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new IHubNameTask();
    }

}
