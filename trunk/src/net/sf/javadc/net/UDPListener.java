/*
 * Copyright (C) 2001 Ryan Sweny, cabin@geeky.net
 *
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software;      you can redistribute it and/or modify it
 * under the terms of the   GNU General Public License as published by the Free
 * Software Foundation;    either version 2 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;   without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE.   See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.StringTokenizer;

import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ITaskManager;

import org.apache.log4j.Category;

/**
 * <code>UDPListener</code> represents a UDP protocol listener
 * 
 * @author tw70794
 */
class UDPListener implements Runnable {

    private static final Category logger = Category
            .getInstance(UDPListener.class);

    /**
     * 
     */
    DatagramSocket searchReplySocket;

    /**
     * 
     */
    private int port;

    /**
     * 
     */
    private boolean cont = true;

    // components
    /**
     * 
     */
    private final IHubManager hubManager;

    /**
     * 
     */
    private final ITaskManager taskManager;

    /**
     * 
     */
    private final IHubTaskFactory hubTaskFactory;

    /**
     * Create a UDPListener with the given IHubManager, ITaskManager and
     * IHubTaskFactory
     * 
     * @param _hubManager
     *            IHubManager instance to be used
     * @param _taskManager
     *            ITaskManager instance to be used
     * @param _hubTaskFactory
     *            IHubTaskFactory instance to be used
     */
    public UDPListener(IHubManager _hubManager, ITaskManager _taskManager,
            IHubTaskFactory _hubTaskFactory) {

        if (_hubManager == null)
            throw new NullPointerException("hubManager was null.");

        if (_taskManager == null)
            throw new NullPointerException("taskManager was null.");

        if (_hubTaskFactory == null)
            throw new NullPointerException("hubTaskFactory was null.");

        hubManager = _hubManager;
        taskManager = _taskManager;
        hubTaskFactory = _hubTaskFactory;

    }

    /** ********************************************************************** */

    /**
     * Close the underlying socket connection
     */
    public synchronized void kill() {
        logger.debug("Killing thread.");
        cont = false;

        if (searchReplySocket != null) {
            searchReplySocket.close();
        }

        searchReplySocket = null;

    }

    /**
     * @throws Exception
     */
    private final void perform() throws Exception {
        byte[] buf = new byte[1024];

        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        searchReplySocket.receive(packet);

        String result = new String(buf, 0, packet.getLength(), "ISO-8859-1")
                .trim();

        logger.debug("Received packet " + result);

        StringTokenizer st = new StringTokenizer(result, String
                .valueOf((char) 5));

        st.nextToken();

        if (st.hasMoreTokens()) {
            st.nextToken();
        } else {
            logger.error("String " + result + " had only one token");
            return;
        }

        String hubInfo = null;

        if (st.hasMoreTokens()) {
            hubInfo = st.nextToken();
        } else {
            logger.error("String " + result + " had only two tokens");
            return;
        }

        int start = hubInfo.lastIndexOf('(');

        if (start == -1) {
            logger.error("Invalid hubInfo " + hubInfo + ", expected to find (");
            return;

        }

        int end = hubInfo.substring(start + 1).lastIndexOf(')');

        if (end == -1) {
            logger.error("Invalid hubInfo " + hubInfo + ", expected to find )");
            return;
        }

        String hubIP = hubInfo.substring(start + 1, start + 1 + end);

        taskManager.addEvent(new HubSRTask(hubManager, hubTaskFactory, result,
                hubIP));

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public final void run() {
        try {
            // represents a socket for sending and receiving datagram packets.
            searchReplySocket = new DatagramSocket(port);

            // searchReplySocket.setSoTimeout(10000);
            logger.debug("UDP Listener started.");
            cont = true;

            while (cont) {
                try {
                    perform();

                } catch (Exception e) {
                    String error = "Caught " + e.getClass().getName();
                    logger.error(error, e);
                    // cont = false;
                    // throw new Exception(error, e);

                }

            }

        } catch (Exception e) {
            logger.error("Caught " + e.getClass().getName(), e);
            // logger.error(e);

        }

        kill();

    }

    /**
     * Set the port where to listen to
     * 
     * @param i
     */
    public void setPort(int i) {
        port = i;

    }

}

/*******************************************************************************
 * $Log: UDPListener.java,v $
 * Revision 1.13  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.12 2005/09/30 15:59:53 timowest updated
 * sources and tests
 * 
 * Revision 1.11 2005/09/26 17:19:52 timowest updated sources and tests
 * 
 * Revision 1.10 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */

