/*
 * Created on 3.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import junit.framework.TestCase;
import net.sf.javadc.config.UserInfo;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.interfaces.IUserInfo;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.mockups.BaseHubTaskFactory;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.hub.HubManager;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UDPListenerThreadTest extends TestCase {

    private final ISettings settings = new BaseSettings(true) {

        private UserInfo userInfo = new UserInfo() {

            public int getPort() {
                return 8500;
            }
        };

        public IUserInfo getUserInfo() {
            return userInfo;
        }

        public boolean isActive() {
            return true;
        }

    };

    private final HubManager hubManager = new HubManager();

    private final ITaskManager taskManager = new TaskManager();

    private final IHubTaskFactory hubTaskFactory = new BaseHubTaskFactory();

    private UDPListenerThread udpListenerThread;

    /**
     * Constructor for UDPListenerThreadTest.
     * 
     * @param arg0
     */
    public UDPListenerThreadTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        // super.setUp();

        udpListenerThread = new UDPListenerThread(hubManager, taskManager,
                settings, hubTaskFactory);
    }

    public void testStarting() {

        // 1 start UDP server

        udpListenerThread.start();

        // 2 send UDP packet to server

        try {
            sendUDPPackage();
            sendUDPPackage();

        } catch (UnsupportedEncodingException uee) {
            System.out.println("The encoding was not supported.");

        } catch (UnknownHostException uhe) {
            System.out.println("The host  was not found.");

        }

        udpListenerThread.stop();

    }

    private final void sendUDPPackage() throws UnsupportedEncodingException,
            UnknownHostException {
        SearchResult sr = new SearchResult();
        String data = "$SR " + "timowest" + " "
                + sr.getSearchResponse(new BaseHub());

        byte[] pdata = data.getBytes("ISO-8859-1");

        DatagramSocket socket = null;

        DatagramPacket packet = new DatagramPacket(pdata, 0, pdata.length,
                InetAddress.getByName("localhost"), settings.getUserInfo()
                        .getPort());

        try {
            socket = new DatagramSocket();
            socket.send(packet);

        } catch (SocketException e) {
            System.out.println(e.toString());

        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }

        if (socket != null)
            socket.close();

    }
}