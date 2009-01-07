/*
 * Created on 5.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Category;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class ContainerBuilderTest extends TestCase {

    private static final Category logger = Category
            .getInstance(ContainerBuilderTest.class);

    private ContainerBuilder containerBuilder;

    private MutablePicoContainer main;

    private MutablePicoContainer gui;

    private MutablePicoContainer hubTasks;

    private MutablePicoContainer clientTasks;

    /**
     * Constructor for ContainerBuilderTest.
     * 
     * @param arg0
     */
    public ContainerBuilderTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        containerBuilder = new ContainerBuilder();

        main = containerBuilder.buildMainContainer();

        gui = containerBuilder.buildGuiContainer(main);

        // containers holding the client and hub tasks
        hubTasks = containerBuilder.buildHubTaskContainer(main);

        clientTasks = containerBuilder.buildClientTaskContainer(main);

        // We must let the parent container know about the child containers.
        main.registerComponentInstance("gui", gui);

        main.registerComponentInstance("hub", hubTasks);

        main.registerComponentInstance("client", clientTasks);

    }

    /**
     * This test ensures, that each container used in javadc can be verified
     * without any exceptions
     * 
     * @throws Exception
     */
    public void testVerifyingContainers() throws Exception {

        try {
            main.verify();
            gui.verify();
            hubTasks.verify();
            clientTasks.verify();

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);

        }

    }

    /**
     * This test ensures, that for each component in the main and gui container,
     * a unit test is available
     * 
     * @throws Exception
     */
    public void testUnitTestsAreAvailable() throws Exception {
        // unit test availability is enforced for main container
        unitTestsAvailable(main);

        // unit test availability is enforced for gui container
        unitTestsAvailable(gui);

    }

    // public void testMainStart(){
    // main.start();
    // }

    /**
     * Ensures that unit tests are available for all component instances defined
     * in the given PicoContainer
     * 
     * @param pico
     *            PicoContainer instance to be tested for verification and unit
     *            test availability
     * 
     * @throws Exception
     */
    protected void unitTestsAvailable(PicoContainer pico) throws Exception {
        pico.verify();

        List components = null;

        components = pico.getComponentInstances();

        List missingTests = new ArrayList();

        for (Iterator i = components.iterator(); i.hasNext();) {
            Class cl = i.next().getClass();

            if (cl.getName().startsWith("net.sf.javadc")) {

                logger.debug("Ensuring that unit test is available for "
                        + cl.getName());

                // String name = cl.getName();

                try {
                    Class.forName(cl.getName() + "Test");

                } catch (Exception e) {
                    missingTests.add(cl);

                }

            } else {
                logger.debug("Skipping " + cl.getName());

            }

        }

        System.out.println();

        // iterate the classes with missing tests and display error message
        for (Iterator i = missingTests.iterator(); i.hasNext();) {
            Class cl = (Class) i.next();

            logger
                    .error("Unittest for class " + cl.getName()
                            + " was missing.");

        }

        assertTrue("There were some classes with missing tests.", missingTests
                .isEmpty());

    }

}