/*
 * Created on 5.6.2005
 */
package net.sf.javadc.tasks.hub;

import java.util.ArrayList;
import java.util.List;

import net.sf.javadc.interfaces.IHubTask;

/**
 * @author Timo Westkämper
 */
public class ISupportsTaskTest extends AbstractHubTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ISupportsTaskTest.class);
    }

    /**
     * Constructor for ISupportsTaskTest.
     * 
     * @param arg0
     */
    public ISupportsTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new ISupportsTask();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#runTask()
     */
    public void runTask() throws Exception {
        task.setCmdData("A B C");
        task.runTask();

        List supports = new ArrayList();
        supports.add("A");
        supports.add("B");
        supports.add("C");

        assertEquals(hub.getSupports(), supports);
        System.out.println(hub.getSupports());
    }

}
