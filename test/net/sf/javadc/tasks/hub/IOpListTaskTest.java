/*
 * Created on 26.11.2004
 */
package net.sf.javadc.tasks.hub;

import java.util.List;

import net.sf.javadc.interfaces.IHubTask;

/**
 * @author Timo Westk�mper
 */
public class IOpListTaskTest extends AbstractHubTaskTest {

    StringBuffer buffer = new StringBuffer();

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for IOpListTaskTest.
     * 
     * @param arg0
     */
    public IOpListTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new IOpListTask();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#testRunTask()
     */
    public void runTask() throws Exception {

        StringBuffer b1 = new StringBuffer();
        b1.append("Hubert").append("$$");
        b1.append("Schubert");

        task.setCmdData(b1.toString());
        task.runTask();

        // ensure that Hubert and Schubert have been added to the list of Ops

        List opList = hub.getOpList();

        assertEquals("Hubert", opList.get(0));
        assertEquals("Schubert", opList.get(1));

    }

}
