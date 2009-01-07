/*
 * Created on 22.3.2005
 */
package net.sf.javadc.tasks.client;

/**
 * @author Timo Westk�mper
 */
public class ILockTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ILockTaskTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for ILockTaskTest.
     * 
     * @param arg0
     */
    public ILockTaskTest(String arg0) {
        super(arg0);
    }

    public void runTask() {
        task = new ILockTask(settings);
        task.setClientConnection(clientConnection);
        task
                .setCmdData("EXTENDEDPROTOCOLABCABCABCABCABCABC Pk=DCPLUSPLUS0.242ABCABC");
        task.runTask();
    }

}
