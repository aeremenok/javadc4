/*
 * Created on 22.3.2005
 */
package net.sf.javadc.tasks.client;

/**
 * @author Timo Westk�mper
 */
public class ISupportsTaskTest extends AbstractClientTaskTest {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ISupportsTaskTest.class);
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
     * Constructor for ISupportsTaskTest.
     * 
     * @param arg0
     */
    public ISupportsTaskTest(String arg0) {
        super(arg0);
    }

    public void runTask() {
        task = new ISupportsTask();
        task.setCmdData("");
        task.setClientConnection(clientConnection);
        task.runTask();
    }

    public void testCreationWithBzList() {
        task = new ISupportsTask();
        task.setCmdData("BZList");
        task.setClientConnection(clientConnection);
        task.runTask();
    }

}
