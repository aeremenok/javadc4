/*
 * Created on 20.7.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net;

import junit.framework.TestCase;
import net.sf.javadc.net.client.ConnectionState;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DownloadRequestStateTest extends TestCase {

    /**
     * Constructor for DownloadRequestStateTest.
     * 
     * @param arg0
     */
    public DownloadRequestStateTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    private DownloadRequestState deriveActiveState(ConnectionState connState) {
        return DownloadRequestState.deriveFromConnectionState(connState, true);

    }

    private DownloadRequestState derivePassiveState(ConnectionState connState) {
        return DownloadRequestState.deriveFromConnectionState(connState, false);

    }

    /**
     * tests the derivation for active requests
     */

    public void testDownloadRequestStateDerivation_Active() {

        assertEquals(deriveActiveState(ConnectionState.DOWNLOADING),
                DownloadRequestState.DOWNLOADING);

        assertEquals(deriveActiveState(ConnectionState.CONNECTING),
                DownloadRequestState.CONNECTING);

        assertEquals(deriveActiveState(ConnectionState.WAITING),
                DownloadRequestState.WAITING);

        assertEquals(deriveActiveState(ConnectionState.NOT_CONNECTED),
                DownloadRequestState.OFFLINE);

        assertEquals(deriveActiveState(ConnectionState.NO_DOWNLOAD_SLOTS),
                DownloadRequestState.QUEUED);

        assertEquals(deriveActiveState(ConnectionState.REMOTELY_QUEUED),
                DownloadRequestState.REMOTELY_QUEUED);

    }

    /**
     * tests the derivation for passive requests
     */

    public void testDownloadRequestStateDerivation_Passive() {

        assertEquals(derivePassiveState(ConnectionState.DOWNLOADING),
                DownloadRequestState.QUEUED);

        assertEquals(derivePassiveState(ConnectionState.CONNECTING),
                DownloadRequestState.CONNECTING);

        assertEquals(derivePassiveState(ConnectionState.NOT_CONNECTED),
                DownloadRequestState.OFFLINE);

        assertEquals(derivePassiveState(ConnectionState.NO_DOWNLOAD_SLOTS),
                DownloadRequestState.QUEUED);

        assertEquals(derivePassiveState(ConnectionState.REMOTELY_QUEUED),
                DownloadRequestState.REMOTELY_QUEUED);

    }

}