/*
 * Created on 4.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net.client;

import java.util.Collection;

import junit.framework.TestCase;
import net.sf.javadc.config.ConstantSettings;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class ConnectionStateTest extends TestCase {

    /**
     * Constructor for ConnectionStateMachineTest.
     * 
     * @param arg0
     */
    public ConnectionStateTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {

        // super.setUp();
    }

    public void testDefinedTransitions() throws Exception {
        assertEquals(ConnectionState
                .getTransition(ConnectionState.COMMAND_DOWNLOAD),
                "SStartDownload");

        assertEquals(ConnectionState.getTransition(ConnectionState.RESUMING),
                "SResuming");

        assertEquals(
                ConnectionState.getTransition(ConnectionState.DOWNLOADING),
                "SDownloading");

        assertEquals(ConnectionState.getTransition(ConnectionState.UPLOADING),
                "SUploading");

        assertEquals(ConnectionState
                .getTransition(ConnectionState.REMOTELY_QUEUED),
                "SRemotelyQueued");

    }

    public void testZeroTransitions() throws Exception {
        assertEquals(ConnectionState.getTransition(ConnectionState.WAITING),
                null);

        // No Download Slots is not anymore an unmatched state

        /*
         * assertEquals(ConnectionState
         * .getTransition(ConnectionState.NO_DOWNLOAD_SLOTS), null);
         */

    }

    public void testHandlersExist() throws Exception {
        Collection transitions = ConnectionState.getTransitions().values();
        String[] handlerNames = new String[transitions.size()];

        handlerNames = (String[]) transitions.toArray(handlerNames);

        assertTrue(handlerNames.length > 0);

        for (int i = 0; i < handlerNames.length; i++) {
            Class cl = null;

            try {
                System.out.println("Checking for " + handlerNames[i] + ".");

                cl = Class.forName(ConstantSettings.CLIENTTASKFACTORY_PREFIX
                        + handlerNames[i]
                        + ConstantSettings.CLIENTTASKFACTORY_POSTFIX);

            } catch (ClassNotFoundException e) {
                System.out.println(e.toString());

            }

            assertTrue(cl != null);

        }

    }

}