/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
 *
 * Copyright (C) 2004 Timo Westkämperer
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

package net.sf.javadc.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Accepts connections and forwards them to another server. The traffic between
 * the client and the server is logged.
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.10 $ $Date: 2005/10/02 11:42:28 $
 */
public class SocketListener {

    /**
     * 
     */
    private byte[] buffer = new byte[100000];

    /**
     * 
     */
    private final PrintStream logger;

    /**
     * 
     */
    private final PrintStream logger2;

    /**
     * 
     */
    private final OutputStream os;

    /**
     * 
     */
    private boolean lastClient;

    /**
     * 
     */
    private boolean first = true;

    /**
     * @param port
     *            Port to listen to.
     * @param server
     *            Address of the server to forward to.
     * @param port
     *            Port of the server to forward to.
     */
    public SocketListener(int port, String server, int serverPort)
            throws Exception {
        logger = System.out;

        os = new FileOutputStream("socket.txt");
        logger2 = new PrintStream(os);

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            println("Accepting connections on port " + port + "...");

            Socket cs = serverSocket.accept();

            println("Client connected: " + cs);

            Socket ss = new Socket(server, serverPort);

            println("Connected to server: " + server + ":" + serverPort);

            while (true) {
                copyData(true, cs.getInputStream(), ss.getOutputStream());
                copyData(false, ss.getInputStream(), cs.getOutputStream());

                try {
                    Thread.sleep(10);

                } catch (Exception e) {

                    // ?
                }

            }

        }

    }

    /**
     * @param text
     */
    void print(String text) {
        logger.print(text);
        logger2.print(text);

    }

    /**
     * @param ch
     */
    void print(char ch) {
        logger.print(ch);
        logger2.print(ch);

    }

    /**
     * @param text
     * 
     * @throws Exception
     */
    void println(String text) throws Exception {
        logger.println(text);
        logger2.println(text);
        flush();

    }

    /**
     * @throws Exception
     */
    void println() throws Exception {
        println("");

    }

    /**
     * @throws Exception
     */
    void flush() throws Exception {
        logger.flush();
        logger2.flush();
        os.flush();

    }

    /**
     * @param client
     * @param is
     * @param os
     * 
     * @throws Exception
     */
    void copyData(boolean client, InputStream is, OutputStream os)
            throws Exception {
        if (is.available() > 0) {
            if ((client != lastClient) || first) {
                println();
                print((client ? "C" : "S") + ": ");
                lastClient = client;
                first = false;

            }

            int count = is.read(buffer);

            for (int i = 0; i < count; i++) {
                if ((buffer[i] >= 32) && (buffer[i] < 128)) {
                    print((char) buffer[i]);

                } else {
                    print("[\\" + (((int) buffer[i]) & 0xff) + "]");

                }

            }

            flush();
            os.write(buffer, 0, count);
            os.flush();

        }

    }

    /**
     * @param args
     * 
     * @throws Exception
     */
    static public void main(String[] args) throws Exception {
        // SocketListener sl = new SocketListener(411, "nynasnet.myip.org",
        // 411);
        new SocketListener(411, "localhost", 16524);

    }

}

/*******************************************************************************
 * $Log: SocketListener.java,v $
 * Revision 1.10  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.9 2005/09/14 07:11:48 timowest
 * updated sources
 * 
 * 
 * 
 */
