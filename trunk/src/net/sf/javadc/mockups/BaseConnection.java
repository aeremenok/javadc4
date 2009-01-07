/*
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.mockups;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EventListener;

import javax.swing.event.EventListenerList;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.UploadRequest;
import net.sf.javadc.net.client.AbstractConnection;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.client.ConnectionInfo;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.client.ConnectionStatistics;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.ExtendedBufferedOutputStream;
import net.sf.javadc.util.TokenInputStream;

/**
 * @author Timo Westk�mper
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BaseConnection extends AbstractConnection implements IConnection {

    private final ISettings settings = new BaseSettings(true);

    // private final IClientManager clientManager = new BaseClientManager();

    private final IClient client;

    // private final ConnectionInfo connectionInfo;

    public BaseConnection() {
        client = new Client(new HostInfo("www.gmx.de"), settings);
        client.setNick("Hubert");

        // connectionInfo = new ConnectionInfo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#addListener(java.util.EventListener)
     */
    public void addListener(EventListener listener) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#closeFile()
     */
    public void closeFile() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#disconnect()
     */
    public void disconnect() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getClient()
     */
    public IClient getClient() {
        return client;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getDownloadRequest()
     */
    public DownloadRequest getDownloadRequest() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getListeners()
     */
    public EventListenerList getListeners() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getLocalFile()
     */
    public RandomAccessFile getLocalFile() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getReader()
     */
    public TokenInputStream getReader() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getServerSocket()
     */
    public ServerSocket getServerSocket() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getSocket()
     */
    public Socket getSocket() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getState()
     */
    public ConnectionState getState() {
        return ConnectionState.DOWNLOADING;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getStatistics()
     */
    public ConnectionStatistics getStatistics() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getWriter()
     */
    public ExtendedBufferedOutputStream getWriter() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#isServer()
     */
    public boolean isServer() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#removeListener(java.util.EventListener)
     */
    public void removeListener(EventListener listener) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#requestDirection(java.lang.String,
     *      boolean)
     */
    public boolean requestDirection(String direction, boolean force)
            throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ITask#runTask()
     */
    public void runTask() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#sendCommand(java.lang.String,
     *      java.lang.String)
     */
    public void sendCommand(String command, String data) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setDownloadRequest(net.sf.javadc.net.DownloadRequest)
     */
    public void setDownloadRequest(DownloadRequest request) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setReader(net.sf.javadc.util.TokenInputStream)
     */
    public void setReader(TokenInputStream stream) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setServerSocket(java.net.ServerSocket)
     */
    public void setServerSocket(ServerSocket socket) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setSocket(java.net.Socket)
     */
    public void setSocket(Socket socket) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setState(net.sf.javadc.net.client.ConnectionState)
     */
    public void setState(ConnectionState state) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setWriter(net.sf.javadc.util.ExtendedBufferedOutputStream)
     */
    public void setWriter(ExtendedBufferedOutputStream stream) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#updateConnectionInfo()
     */
    public void updateConnectionInfo() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getUploadRequest()
     */
    public UploadRequest getUploadRequest() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setUploadRequest(net.sf.javadc.net.UploadRequest)
     */
    public void setUploadRequest(UploadRequest u) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getConnectionInfo()
     */
    public ConnectionInfo getConnectionInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setLocalFile(java.io.RandomAccessFile)
     */
    public void setLocalFile(RandomAccessFile file) {
        // TODO Auto-generated method stub

    }

}