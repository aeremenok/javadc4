/*
 * Copyright 2005 by Timo Westkämper
 * 
 * $Id$
 *
 * $Revision$
 */

package net.sf.javadc.tasks;

import junit.framework.TestCase;
import net.sf.javadc.ContainerBuilder;

import org.picocontainer.MutablePicoContainer;

public class ClientTaskObjectFactoryTest extends TestCase {

    private ContainerBuilder containerBuilder;

    private MutablePicoContainer main;

    // private MutablePicoContainer gui;

    private MutablePicoContainer hubTasks;

    private MutablePicoContainer clientTasks;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ClientTaskObjectFactoryTest.class);
    }

    public void setUp() {
        containerBuilder = new ContainerBuilder();

        main = containerBuilder.buildMainContainer();

        // gui = containerBuilder.buildGuiContainer(main);

        // containers holding the client and hub tasks
        hubTasks = containerBuilder.buildHubTaskContainer(main);

        clientTasks = containerBuilder.buildClientTaskContainer(main);

    }

    public void testCreation() {

        ClientTaskObjectFactory ctof = new ClientTaskObjectFactory(clientTasks);
        ctof.initialize();

        HubTaskObjectFactory htof = new HubTaskObjectFactory(hubTasks);
    }

}
