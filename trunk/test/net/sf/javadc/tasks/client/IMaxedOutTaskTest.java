/*
 * Created on 22.3.2005
 */
package net.sf.javadc.tasks.client;

/**
 * @author Timo Westk�mper
 */
public class IMaxedOutTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(IMaxedOutTaskTest.class);
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
     * Constructor for IMaxedOutTaskTest.
     * 
     * @param arg0
     */
    public IMaxedOutTaskTest(String arg0) {
        super(arg0);
    }

    public void runTask() {
        task = new IMaxedOutTask();
        task.setClientConnection(clientConnection);
        task.runTask();
    }

}
