/*
 * Created on 26.11.2004
 */
package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.IHubTask;

/**
 * @author Timo Westk�mper
 */
public class ISRTaskTest extends AbstractHubTaskTest {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for ISRTaskTest.
     * 
     * @param arg0
     */
    public ISRTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new ISRTask(settings);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#testRunTask()
     */
    public void runTask() throws Exception {
        String cmdData = new StringBuffer("User1 mypath\\motd.txt").append(
                (char) 5).append("437 3/4").append((char) 5).append(
                "Testhub (10.10.10.10:411").append((char) 5).append("User2")
                .toString();

        task.setCmdData(cmdData);
        task.runTask();

    }

    public void testRunTaskWithTTHResult() throws Exception {
        String cmdData = new StringBuffer("User1 mypath\\motd.txt").append(
                (char) 5).append("437 3/4").append((char) 5).append(
                "TTH:XVIVD7G7HGFY2EVOV7ETVMSYUMT5QEEPY4CJUOA (10.10.10.10:411")
                .append((char) 5).append("User2").toString();

        task.setCmdData(cmdData);
        task.runTask();
    }

}
