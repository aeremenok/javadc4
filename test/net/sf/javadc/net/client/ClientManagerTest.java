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
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class ClientManagerTest extends TestCase {

    private IClientManager clientManager;

    // external components
    private final ISettings settings = new BaseSettings(true);

    private final ITaskManager taskManager = new TaskManager();

    private final IConnectionManager clientConnectionManager = new ConnectionManager(
            settings);

    private final IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    /**
     * Constructor for ClientManagerTest.
     * 
     * @param arg0
     */
    public ClientManagerTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        clientManager = new ClientManager(settings, taskManager,
                clientConnectionManager, clientTaskFactory);

    }

    public void testAdding() throws Exception {
        HostInfo host1 = new HostInfo("www.gmx.de");
        Client client1 = new Client(host1, settings);

        HostInfo host2 = new HostInfo("www.uta.fi");
        Client client2 = new Client(host2, settings);

        clientManager.addClient(host1, client1);

        // 1
        assertEquals(client1 == clientManager.getClient(host1), true);

        clientManager.addClient(host2, client2);

        // 2
        assertEquals(client2 == clientManager.getClient(host2), true);

        IConnection connection = clientManager.createClientConnection(client1,
                new ConnectionListenerBase(), false);

        // 3
        assertEquals(connection != null, true);

        clientManager.removeClient(host1);

        clientManager.removeClient(host2);

    }

}