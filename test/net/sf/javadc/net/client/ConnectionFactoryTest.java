/*
 * Created on 18.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.client;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListenerBase;
import net.sf.javadc.mockups.BaseClientManager;
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class ConnectionFactoryTest extends TestCase {

    private ConnectionFactory connectionFactory;

    // external components
    private final ISettings settings = new BaseSettings(true);

    private final ITaskManager taskManager = new TaskManager();

    private final IConnectionManager connectionManager = new ConnectionManager(
            settings);

    private final IClientManager clientManager = new BaseClientManager();

    private final IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    /**
     * Constructor for ClientConnectionFactoryTest.
     * 
     * @param arg0
     */
    public ConnectionFactoryTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        connectionFactory = new ConnectionFactory(taskManager,
                connectionManager, clientManager, clientTaskFactory);

    }

    public void testCreation() throws Exception {
        Client client = new Client(new HostInfo("www.gmx.de"), settings);

        IConnection clientConnection = connectionFactory
                .createClientConnection(client, new ConnectionListenerBase(),
                        false);

        // ensure that a valid connection has been created
        assertEquals(clientConnection != null, true);

        // given the Connection time to add itself to the ConnectionManager
        Thread.sleep(300);

        // ensure that the connection has been added to the ConnectionManager

        assertEquals(connectionManager.getClientConnections().length, 1);

        // ensure that no duplicate connections can be created
        // by ensuring that the returned connection matches the first created

        assertTrue(connectionFactory.createClientConnection(client,
                new ConnectionListenerBase(), false) == clientConnection);

        // ensure that for one client no duplicate connections can be created

        /*
         * 
         * boolean exceptionThrown = false;
         * 
         * try { connectionFactory.createClientConnection(client, new
         * ConnectionListenerBase(), false);
         *  } catch (Exception e) { exceptionThrown = true;
         *  }
         * 
         * assertTrue(exceptionThrown);
         */

    }

}