/*
 * Created on 26.11.2004
 */
package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.IHubTask;

/**
 * @author Timo Westk�mper
 */
public class IMyINFOTaskTest extends AbstractHubTaskTest {
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for IMyINFOTaskTest.
     * 
     * @param arg0
     */
    public IMyINFOTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new IMyINFOTask();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#testRunTask()
     */
    public void runTask() throws Exception {
        task.setCmdData("$ALL TestUser2 <++ V:0.181,M:A,H:0,S:1>$ $DSL$$0$");
        task.runTask();

    }

    // TODO : provide tests for invalid user descriptions

}
