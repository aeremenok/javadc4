/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

// $Id: IConnection.java,v 1.16 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.interfaces;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.EventListener;

import javax.swing.event.EventListenerList;

import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.UploadRequest;
import net.sf.javadc.net.client.ConnectionInfo;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.net.client.ConnectionStatistics;
import net.sf.javadc.util.ExtendedBufferedOutputStream;
import net.sf.javadc.util.TokenInputStream;

/**
 * <CODE>IConnection</CODE> represents the abstract interface for the object representation of a Client
 * <CODE>Connection</CODE>
 * 
 * @author tw70794
 */
public interface IConnection
    extends
        Runnable,
        ITask
{
    /**
     * Add a <CODE>ClientConnectionListener</CODE> to the list of listeners
     * 
     * @param listener
     */
    public abstract void addListener(
        EventListener listener );

    /**
     * Close the current local file
     */
    public abstract void closeFile();

    /**
     * Initialize a disconnect of the <CODE>ClientConnection</CODE>
     */
    public abstract void disconnect();

    /**
     * Notify listeners that a Client Connection has been canceled
     */
    public abstract void fireDisconnected();

    /**
     * Notifiy listeners that a DownloadRequest has been successfully processed
     */
    public abstract void fireDownloadComplete();

    /**
     * Notify listeners that processing of a DownloadRequest failed
     */
    public abstract void fireDownloadFailed();

    /**
     * Notify listeners the the state of the Connection has changed
     */
    public abstract void fireStateChanged();

    /**
     * Notify listeners that an UploadRequest has been successfully processed
     */
    public abstract void fireUploadComplete();

    /**
     * Notify listeners that the processing of an UploadRequest failed
     */
    public abstract void fireUploadFailed();

    /**
     * Return the Client of this Client Connection
     * 
     * @return
     */
    public abstract IClient getClient();

    /**
     * Return the current direction of this Client Connection (Download or Upload)
     * 
     * @return
     */
    // public abstract String getCurrentDirection();
    /**
     * Get the ConnectionInfo instance of the IConnection instance
     * 
     * @return
     */
    public ConnectionInfo getConnectionInfo();

    /**
     * Return the info String with the Connection info
     * 
     * @return
     */
    // public abstract String getInfo();
    /**
     * Return the active DownloadRequest (for Download Connection)
     * 
     * @return
     */
    public abstract DownloadRequest getDownloadRequest();

    /**
     * Return the list of registered EventListeners
     * 
     * @return
     */
    public abstract EventListenerList getListeners();

    /**
     * Return the current active file related to this Connection
     * 
     * @return
     */
    public abstract RandomAccessFile getLocalFile();

    /**
     * @return
     */
    // public abstract String getRemoteKey();
    /**
     * Return the ServerSocket
     * 
     * @return
     */
    // public abstract ServerSocket getServerSocket();
    /**
     * Return the TokenInputStream from where the commands are read
     * 
     * @return
     */
    public abstract TokenInputStream getReader();

    /**
     * Return the Socket
     * 
     * @return
     */
    public abstract Socket getSocket();

    /**
     * Return the ConnectionState of this Client Connection
     * 
     * @return
     */
    public abstract ConnectionState getState();

    /**
     * Return the ConnectionStatistics
     * 
     * @return
     */
    public abstract ConnectionStatistics getStatistics();

    /**
     * Return the verifyResumeSize
     * 
     * @return
     */
    // public abstract int getVerifyResumeSize();
    /**
     * Return the active UploadRequest (for Upload Connection)
     * 
     * @return
     */
    public abstract UploadRequest getUploadRequest();

    /**
     * Return the Writer
     * 
     * @return
     */
    public abstract ExtendedBufferedOutputStream getWriter();

    /**
     * Return whether the Client Connection is active or not
     * 
     * @return
     */
    public abstract boolean isServer();

    /**
     * Remove the selected <CODE>ClientConnectionListener</CODE> from the list of listeners
     * 
     * @param listener
     */
    public abstract void removeListener(
        EventListener listener );

    /**
     * Request a direction, returns true if we have the direction, otherwise false.
     * 
     * @param direction
     * @param force
     * @return
     * @throws IOException
     */
    public abstract boolean requestDirection(
        String direction,
        boolean force )
        throws IOException;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public abstract void run();

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ITask#runTask()
     */
    public abstract void runTask();

    /**
     * Set the current Direction (Download or Upload)
     * 
     * @param string
     */
    // public abstract void setCurrentDirection(String string);
    /**
     * Send a command to the remote Client
     * 
     * @param command
     * @param data
     */
    public abstract void sendCommand(
        String command,
        String data );

    /**
     * Set the active DownloadRequest
     * 
     * @param request
     */
    public abstract void setDownloadRequest(
        DownloadRequest request );

    /**
     * Set the local File
     * 
     * @param file
     */
    public abstract void setLocalFile(
        RandomAccessFile file );

    /**
     * Set the remote key
     * 
     * @param string
     */
    // public abstract void setRemoteKey(String string);
    /**
     * Set the active Reader
     * 
     * @param stream
     */
    public abstract void setReader(
        TokenInputStream stream );

    /**
     * Set the ServerSocket
     * 
     * @param socket
     */
    // public abstract void setServerSocket(ServerSocket socket);
    /**
     * Set the Socket
     * 
     * @param socket
     */
    public abstract void setSocket(
        Socket socket );

    /**
     * Set the ConnectionState of this Client Connection
     * 
     * @param state
     */
    public abstract void setState(
        ConnectionState state );

    /**
     * Set the verifyResumeSize
     * 
     * @param i
     */
    // public abstract void setVerifyResumeSize(int i);
    /**
     * Set the UploadRequest (for Upload Connections)
     * 
     * @param u
     */
    public abstract void setUploadRequest(
        UploadRequest u );

    /**
     * Set the Writer
     * 
     * @param stream
     */
    public abstract void setWriter(
        ExtendedBufferedOutputStream stream );

    /**
     * Get the amount of (uncompressed) bytes that are still to be uploaded
     * 
     * @return
     */
    // public abstract long getBytesToUpload();
    /**
     * Set the amount of (uncompressed) bytes that are still to be uploaded
     * 
     * @param bytes
     */
    // public abstract void setBytesToUpload(long bytes);
    /**
     * Get whether the current upload data is compressed or not
     * 
     * @return true, if compressed and false, if not
     */
    // public abstract boolean isUseCompression();
    /**
     * Set whether the current upload data is to be compressed not
     * 
     * @param comp
     */
    // public abstract void setUseCompression(boolean comp);
    /**
     * Update the Connection Information
     */
    public abstract void updateConnectionInfo();

}