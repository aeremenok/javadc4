/*
 * Created on 1.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.tasks.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Iterator;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.config.UserInfo;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTask;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListenerBase;
import net.sf.javadc.mockups.BaseClientManager;
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.client.ConnectionFactory;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public abstract class AbstractClientTaskTest extends TestCase {

    // constants

    public static final int KBYTES = 1024;

    public static final int MBYTES = 1024 * KBYTES;

    private static final String testFileName = "C:\\Temp\\test";

    // external components
    // protected final ISettings settings = new BaseSettings(true);
    protected final ISettings settings = new Settings();

    private final ITaskManager taskManager = new TaskManager();

    private final IConnectionManager connectionManager = new ConnectionManager(
            settings);

    // mockup
    private final IClientManager clientManager = new BaseClientManager();

    // mockup
    private final IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    private final IConnectionFactory connectionFactory = new ConnectionFactory(
            taskManager, connectionManager, clientManager, clientTaskFactory);

    // attributes

    private Client client;

    protected IConnection clientConnection;

    protected IClientTask task;

    /**
     * Constructor for SStartDownloadTaskTest.
     * 
     * @param arg0
     */
    public AbstractClientTaskTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        settings.setUserInfo(new UserInfo());

        settings.setDownloadSlots(10);
        settings.setUploadSlots(10);

        client = new Client(new HostInfo("www.gmx.de"), settings);

        clientConnection = connectionFactory.createClientConnection(client,
                new ConnectionListenerBase(), false);

        // output

        try {
            RandomAccessFile file = new RandomAccessFile(testFileName, "rw");
            clientConnection.setLocalFile(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        // System.out.println(clientConnection.getConnectionInfo());

        System.out.println(clientConnection.getStatistics());
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {

        // System.out.println(clientConnection.getConnectionInfo());

        System.out.println(clientConnection.getStatistics());

        if (clientConnection.getLocalFile() != null) {
            clientConnection.getLocalFile().close();
            new File(testFileName).delete();

        } else {
            System.out.println("localFile of clientConnection was closed.");

        }

    }

    /*
     * public void testNoTests() { }
     */

    /**
     * Test the creation of the IClientTask instance
     */
    public abstract void runTask();

    /**
     * Ensure that no Exceptions are thrown when the IClientTask instance is
     * executed
     */
    public void testRunTaskPassiveNoExceptions() {
        noExceptions(false);
    }

    /**
     * 
     */
    public void testRunTaskActiveNoExceptions() {
        noExceptions(true);
    }

    private void noExceptions(boolean active) {

        assertTrue("task was not empty", task == null);

        if (active)
            settings.setActive(true);
        else
            settings.setActive(false);

        runTask();

        assertTrue("task was empty", task != null);

        int counter = 0;

        for (Iterator i = task.getExceptions().iterator(); i.hasNext();) {
            Exception e = (Exception) i.next();

            System.out.println(++counter + " " + e);

        }

        assertTrue("There were " + task.getExceptions().size()
                + " exceptions during the task invocation.", task
                .getExceptions().isEmpty());

    }

}