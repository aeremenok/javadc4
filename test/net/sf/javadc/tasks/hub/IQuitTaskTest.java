/*
 * Created on 26.11.2004
 */
package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.IHubTask;
import net.sf.javadc.net.hub.HubUser;

/**
 * @author Timo Westk�mper
 */
public class IQuitTaskTest extends AbstractHubTaskTest {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for IQuitTaskTest.
     * 
     * @param arg0
     */
    public IQuitTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new IQuitTask();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#testRunTask()
     */
    public void runTask() throws Exception {

        HubUser user = new HubUser(hub);
        user.setNick("timowest");
        hub.addUser(user);

        user = new HubUser(hub);
        user.setNick("jenniwest");
        hub.addUser(user);

        // ensure that both users have been added to the hub
        assertEquals(hub.getUsers().values().size(), 2);

        task.setCmdData("timowest");
        task.runTask();

        // ensure that the user related to the nick timowest has been removed
        assertEquals(hub.getUsers().values().size(), 1);

        assertEquals(hub.getUsers().get("jenniwest"), user);
        assertEquals(hub.getUsers().get("timowest"), null);

    }

}
