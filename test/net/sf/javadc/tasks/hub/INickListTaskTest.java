/*
 * Created on 26.11.2004
 */
package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.IHubTask;

/**
 * @author Timo Westk�mper
 */
public class INickListTaskTest extends AbstractHubTaskTest {

    private StringBuffer buffer = new StringBuffer();

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for INickListTaskTest.
     * 
     * @param arg0
     */
    public INickListTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new INickListTask(settings) {

            protected void sendCommand(String command, String data) {
                buffer.append(command).append(data).append("\n");

            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#testRunTask()
     */
    public void runTask() throws Exception {

        // "\\$\\$"

        // prepare the nicklist data

        StringBuffer b2 = new StringBuffer();
        b2.append("Hubert").append("$$");
        b2.append("Schubert").append("$$");
        b2.append("QBert").append("$$");

        // specify the environment settings

        settings.getUserInfo().setNick("timowest");
        task.setCmdData(b2.toString());

        // run the task handler

        task.runTask();

        // ensure that the created output is valid

        StringBuffer b3 = new StringBuffer();

        // sendCommand("$GetINFO", elements[i] + " " + nick);

        b3.append("$GetINFO").append("Hubert timowest\n");
        b3.append("$GetINFO").append("Schubert timowest\n");
        b3.append("$GetINFO").append("QBert timowest\n");

        assertEquals(b3.toString(), buffer.toString());

    }

}
