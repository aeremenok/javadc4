/*
 * Created on 19.9.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package net.sf.javadc.gui.model;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IIncompletesLoader;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseClientManager;
import net.sf.javadc.mockups.BaseConnection;
import net.sf.javadc.mockups.BaseIncompletesLoader;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.RequestsModel;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westk�mper
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DownloadTreeModelAdapterTest extends TestCase {

    // external components
    private ISettings settings = new BaseSettings(true);

    private IClientManager clientManager = new BaseClientManager();

    private IConnectionManager connectionManager = new ConnectionManager(
            settings);

    private IIncompletesLoader incompletesLoader = new BaseIncompletesLoader();

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(
            new HubManager(), segmentManager);

    private IRequestsModel requestsModel = new RequestsModel(settings,
            clientManager, connectionManager, incompletesLoader,
            downloadManager, segmentManager);

    // testable component
    private DownloadTreeModelAdapter adapter = new DownloadTreeModelAdapter(
            requestsModel);

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testInitial() throws Exception {

        assertEquals(adapter.getConnections().size(), adapter
                .getNumConnections());
    }

    public void testAfterConnectionsAdded() throws Exception {

        assertEquals(adapter.getNumConnections(), 0);

        connectionManager.addConnection(new BaseConnection());
        assertEquals(adapter.getNumConnections(), 1);

        // BaseConnection is only added once
        connectionManager.addConnection(new BaseConnection());
        assertEquals(adapter.getNumConnections(), 1);
    }

    /**
     * I often get a NullpointerEx in the "DownloadTreeModelAdapter" class's
     * "getValueAt(IConnection conn, int column)" method. I debugged that
     * "conn.getDownloadRequest()" can be null there.
     * 
     * @throws Exception
     */
    public void testGetValueAt_bug20041122_2() throws Exception {
        connectionManager.addConnection(new BaseConnection());

    }

}