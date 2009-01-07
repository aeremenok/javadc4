/*
 * Created on 27.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.client;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sf.javadc.config.SettingsAdapter;
import net.sf.javadc.config.SettingsLoader;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListenerBase;
import net.sf.javadc.mockups.BaseClientManager;
import net.sf.javadc.mockups.BaseClientTaskFactory;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.TaskManager;

/**
 * ConnectionSlotReservationExtTest uses the SettingsAdapter instead of the
 * normal Settings instance
 * 
 * @author Timo Westk�mper
 */
public class ConnectionSlotReservationExtTest extends TestCase {

    private ConnectionFactory connectionFactory;

    // external components

    // uses the Settin
    private final ISettings settings = new SettingsAdapter(new SettingsLoader());

    private final ITaskManager taskManager = new TaskManager();

    private final IConnectionManager connectionManager = new ConnectionManager(
            settings);

    private final IClientManager clientManager = new BaseClientManager();

    private final IClientTaskFactory clientTaskFactory = new BaseClientTaskFactory();

    // internal components
    private IConnection clientConnection, clientConnection2;

    private ConnectionSlotReservation connectionSlotReservation = new ConnectionSlotReservation(
            settings);

    /**
     * Constructor for ConnectionSlotReservationTest.
     * 
     * @param arg0
     */
    public ConnectionSlotReservationExtTest(String arg0) {
        super(arg0);

        settings.setDownloadSlots(10);
        settings.setUploadSlots(10);

        connectionFactory = new ConnectionFactory(taskManager,
                connectionManager, clientManager, clientTaskFactory);

        Client client = new Client(new HostInfo("uta.fi"), settings);

        clientConnection = connectionFactory.createClientConnection(client,
                new ConnectionListenerBase(), false);

        client = new Client(new HostInfo("utu.fi"), settings);

        clientConnection2 = connectionFactory.createClientConnection(client,
                new ConnectionListenerBase(), false);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        // connectionSlotReservation.setSettings(settings);
        // connectionSlotReservation.setConnection(clientConnection);

    }

    public void testDownloadSlots() throws Exception {
        int slots = settings.getFreeDownloadSlotCount();

        assertTrue(connectionSlotReservation.useDownloadSlot(clientConnection));
        assertEquals(slots - 1, settings.getFreeDownloadSlotCount());

        // doesn't allow more than one slot for a connection
        assertFalse(connectionSlotReservation.useDownloadSlot(clientConnection));
        assertEquals(slots - 1, settings.getFreeDownloadSlotCount());

        assertTrue(connectionSlotReservation.freeDownloadSlot(clientConnection));
        assertEquals(slots, settings.getFreeDownloadSlotCount());

        // do the same again, but release the download slot differently

        assertTrue(connectionSlotReservation.useDownloadSlot(clientConnection));
        assertEquals(slots - 1, settings.getFreeDownloadSlotCount());

        assertTrue(connectionSlotReservation.removeConnection(clientConnection));
        assertEquals(slots, settings.getFreeDownloadSlotCount());

    }

    public void testUploadSlots() throws Exception {
        int slots = settings.getFreeUploadSlotCount();

        assertTrue(connectionSlotReservation.useUploadSlot(clientConnection));
        assertEquals(slots - 1, settings.getFreeUploadSlotCount());

        // doesn't allow more than one slot for a connection
        assertFalse(connectionSlotReservation.useUploadSlot(clientConnection));
        assertEquals(slots - 1, settings.getFreeUploadSlotCount());

        assertTrue(connectionSlotReservation.freeUploadSlot(clientConnection));
        assertEquals(slots, settings.getFreeUploadSlotCount());

        // do the same again, but release the upload differently

        assertTrue(connectionSlotReservation.useUploadSlot(clientConnection));
        assertEquals(slots - 1, settings.getFreeUploadSlotCount());

        assertTrue(connectionSlotReservation.removeConnection(clientConnection));
        assertEquals(slots, settings.getFreeUploadSlotCount());

    }

    public void testMultipleDownloads() throws Exception {
        int dSlots = settings.getFreeDownloadSlotCount();
        int uSlots = settings.getFreeUploadSlotCount();

        assertTrue(connectionSlotReservation.useUploadSlot(clientConnection));
        assertTrue(connectionSlotReservation.useDownloadSlot(clientConnection2));

        assertEquals(settings.getFreeDownloadSlotCount(), dSlots - 1);
        assertEquals(settings.getFreeUploadSlotCount(), uSlots - 1);

        // no duplicate slot reservations

        assertFalse(connectionSlotReservation.useUploadSlot(clientConnection));
        assertFalse(connectionSlotReservation
                .useDownloadSlot(clientConnection2));

        List downloadConnections = new ArrayList();
        downloadConnections.add(clientConnection2);

        List uploadConnections = new ArrayList();
        uploadConnections.add(clientConnection);

        // checks that the sets representing the download and upload conenctions
        // are valid

        assertEquals(connectionSlotReservation.getDownloadConnections(),
                downloadConnections);
        assertEquals(connectionSlotReservation.getUploadConnections(),
                uploadConnections);

        // checks releasing slots (invalid form)

        assertFalse(connectionSlotReservation.freeUploadSlot(clientConnection2));
        assertFalse(connectionSlotReservation
                .freeDownloadSlot(clientConnection));

        // checks releasing slots (valid form)

        assertTrue(connectionSlotReservation.removeConnection(clientConnection));
        assertTrue(connectionSlotReservation
                .removeConnection(clientConnection2));

        // checks that the download and upload slot count are back to initial
        // values

        assertEquals(settings.getFreeDownloadSlotCount(), dSlots);
        assertEquals(settings.getFreeUploadSlotCount(), uSlots);
    }

}