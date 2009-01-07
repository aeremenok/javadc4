/*
 * Created on 13.11.2004
 */
package net.sf.javadc.util;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper
 */
public class TaskManagerTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for TaskManagerTest.
     * 
     * @param arg0
     */
    public TaskManagerTest(String arg0) {
        super(arg0);
    }

    public void testNoTests() {

    }

    // TODO : ensure that events are removed after execution

    // TODO : ensure that the TaskManager throws no exceptions outside,
    // even if the events or tasks throw exceptions

}
