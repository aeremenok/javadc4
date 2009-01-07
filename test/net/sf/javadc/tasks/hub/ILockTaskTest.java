/*
 * Created on 26.11.2004
 */
package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.IHubTask;

/**
 * @author Timo Westk�mper
 */
public class ILockTaskTest extends AbstractHubTaskTest {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for ILockTaskTest.
     * 
     * @param arg0
     */
    public ILockTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new ILockTask(settings);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#testRunTask()
     */
    public void runTask() throws Exception {
        settings.getUserInfo().setNick("Tom");
        task
                .setCmdData("EXTENDEDPROTOCOLABCABCABCABCABCABC Pk=DCPLUSPLUS0.242ABCABC");
        task.runTask();

    }

}
