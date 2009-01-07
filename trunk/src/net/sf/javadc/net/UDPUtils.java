/*
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
import java.net.InetAddress;

import net.sf.javadc.util.SafeParser;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * <code>UDPUtils</code> represents a utility class which defines two methods
 * used to send string data to remote hosts via UDP
 * 
 * @author Timo Westk�mper
 */
public class UDPUtils {

    private final static Category logger = Logger.getLogger(UDPUtils.class);

    /**
     * Send the given data string to the given host and port
     * 
     * @param data
     *            string to be sent
     * @param host
     *            host the data is to be sent to
     * @param port
     *            port to be used
     */
    public final static void sendUDPString(String data, String host, int port) {
        try {
            byte[] pdata = data.getBytes("ISO-8859-1");

            DatagramPacket packet = new DatagramPacket(pdata, 0, pdata.length,
                    InetAddress.getByName(host), port);

            DatagramSocket UDPSocket = new DatagramSocket();
            UDPSocket.send(packet);

        } catch (Exception e) {
            String error = "Caught " + e.getClass().getName()
                    + " when trying to send " + data;
            logger.error(error, e);

        }

    }

    /**
     * Send the given data string to the given combined host and port
     * 
     * @param data
     *            string to be send
     * @param hostandport
     *            host and port separated by :
     */
    public final static void sendUDPString(String data, String hostandport) {
        if ((hostandport != null) && (data != null)) {
            String host = hostandport.substring(0, hostandport.indexOf(":"));

            int port = SafeParser.parseInt(hostandport.substring(hostandport
                    .indexOf(":") + 1), -1);

            if ((host != null) && (port > 0)) {
                sendUDPString(data, host, port);

            } else {
                logger.warn("Host and/or Port are invalid.");
            }

        } else {
            logger.warn("Hostandport and/or data are invalid.");

        }

    }

}

/*******************************************************************************
 * $Log: UDPUtils.java,v $
 * Revision 1.13  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.12 2005/09/26 17:19:52 timowest updated
 * sources and tests
 * 
 * Revision 1.11 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.10 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
