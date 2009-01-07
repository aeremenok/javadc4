/*
 * Created on 22.3.2005
 */
package net.sf.javadc.tasks.client;

/**
 * @author Timo Westk�mper
 */
public class IMyNickTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(IMyNickTaskTest.class);
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
     * Constructor for IMyNickTaskTest.
     * 
     * @param arg0
     */
    public IMyNickTaskTest(String arg0) {
        super(arg0);
    }

    public void runTask() {
        task = new IMyNickTask();
        task.setClientConnection(clientConnection);
        task.setCmdData("test");
        task.runTask();
    }

}
