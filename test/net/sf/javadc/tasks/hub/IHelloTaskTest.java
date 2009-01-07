/*
 * Created on 19.11.2004
 */

package net.sf.javadc.tasks.hub;

import net.sf.javadc.config.AdvancedSettings;
import net.sf.javadc.config.UserInfo;
import net.sf.javadc.interfaces.IHubTask;

/**
 * @author Timo Westk�mper
 */
public class IHelloTaskTest extends AbstractHubTaskTest {

    private StringBuffer buffer = new StringBuffer();

    private UserInfo userInfo = new UserInfo();

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for IHelloTaskTest.
     * 
     * @param arg0
     */
    public IHelloTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.hub.AbstractHubTaskTest#createTask()
     */
    public IHubTask createTask() {
        return new IHelloTask(settings, userInfo) {

            protected void sendCommand(String command, String data) {
                buffer.append(command).append(data).append("\n");

            }

        };
    }

    public void testIntial() throws Exception {

        assertNotNull(settings.getUserInfo());
        assertNotNull(settings.getAdvancedSettings());
    }

    public void runTask() throws Exception {
        AdvancedSettings advancedSettings = settings.getAdvancedSettings();
        advancedSettings.setClientVersion("0.1");
        userInfo.setNick("Schubert");

        task.setCmdData("Schubert");
        task.runTask();

        // sendCommand("$Version", advancedSettings.getClientVersion());
        // sendCommand("$GetNickList", "");
        // sendCommand("$MyINFO", userInfo.toString());

        StringBuffer b2 = new StringBuffer();
        b2.append("$Version").append(advancedSettings.getClientVersion())
                .append("\n");
        b2.append("$GetNickList").append("\n");
        b2.append("$MyINFO").append(userInfo.toString()).append("\n");

        // ensure that the output of the task equals the created string
        assertEquals(buffer.toString(), b2.toString());
    }

    public void testRunTask_OtherNick() throws Exception {
        settings.getAdvancedSettings().setClientVersion("0.1");
        userInfo.setNick("Schubert");

        task.setCmdData("Hubert");
        task.runTask();

        // no output when the given nick doesn't equal the local one
        assertEquals(buffer.toString(), new String());
    }

}