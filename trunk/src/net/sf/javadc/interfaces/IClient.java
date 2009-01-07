/*
 * Copyright 2005 by Timo Westkämper
 * 
 * $Id$
 *
 * $Revision$
 */

package net.sf.javadc.interfaces;

import java.io.IOException;

import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.hub.HostInfo;

/**
 * @author twe
 */
public interface IClient {

    /**
     * Add a DownloadRequest to the queue of downloads
     * 
     * @param dr
     *            DownloadRequest to be added
     * 
     * @throws IOException
     */
    public abstract void addDownload(DownloadRequest dr) throws IOException;

    /**
     * Adds the given array of DownloadRequests to the queue of downloads
     * 
     * @param drs
     *            array of DownloadReqeusts
     * 
     * @throws IOException
     */
    public abstract void addDownloads(DownloadRequest[] drs) throws IOException;

    /**
     * Checks whether downloads are still in the queue, and if yes, the topmost
     * item is set as the DownloadRequest of the ClientConnection
     * 
     * @throws IOException
     */
    public abstract void checkDownloads() throws IOException;

    /**
     * Establishes a new ClientConnection, if not yet available
     * 
     * @deprecated use serverConnect and clientConnect instead
     * 
     * @throws IOException
     */
    public abstract void connect() throws IOException;

    /**
     * @throws IOException
     */
    public abstract void serverConnect() throws IOException;

    /**
     * @throws IOException
     */
    public abstract void clientConnect() throws IOException;

    /**
     * Get the DownloadRequest with the given index
     * 
     * @param index
     *            index of the DownloadRequest to be retrieved
     * 
     * @return
     */
    public abstract DownloadRequest getDownload(int index);

    /**
     * Get the list of downloads as an array of DownloadRequests
     * 
     * @return
     */
    public abstract DownloadRequest[] getDownloads();

    /**
     * Get the last DownloadRequest in the download queue
     * 
     * @return
     */
    public abstract DownloadRequest getLastDownloadInQueue();

    /**
     * Get the nick name of the Client
     * 
     * @return
     */
    public abstract String getNick();

    /**
     * Return whether there are DownloadRequests in the queue of not
     * 
     * @return true, if the queue is not empty and false, if it is empty
     */
    public abstract boolean hasDownloads();

    /**
     * Remove all downloads in the queue
     */
    public abstract void removeAllDownloads();

    /**
     * Remove the given DownloadRequest
     * 
     * @param dr
     *            DownloadRequest to be removed
     */
    public abstract void removeDownload(DownloadRequest dr);

    /**
     * Remove the DownloadRequest with the given index
     * 
     * @param index
     *            index of the DownloadRequest to be removed
     */
    public abstract void removeDownload(int index);

    /**
     * Set the nick name of the Client
     * 
     * @param nick
     */
    public abstract void setNick(String nick);

    /**
     * Get whether the Client supports bzip2 compressed file lists and uploads
     * 
     * @return true, if the Client supports bzip2 and false, if not
     */
    public abstract boolean isBZSupport();

    /**
     * Set whether the Client supports bzip2 compressed file lists and uploads
     * 
     * @param b
     */
    public abstract void setBZSupport(boolean b);

    /**
     * Get the Client Connection associated with this Client
     * 
     * @return
     */
    public abstract IConnection getConnection();

    /**
     * Set the Client Connection associated with this Client
     * 
     * @param connection
     */
    public abstract void setConnection(IConnection connection);

    /**
     * Return the HostInfo instance associated with this Client instance
     * 
     * @return
     */
    public abstract HostInfo getHost();

    /**
     * Set the HostInfo instance associated with the Client instance
     * 
     * @param host
     */
    public abstract void setHost(HostInfo host);

}

/*******************************************************************************
 * $Log$
 * Revision 1.2  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.1 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * 
 */
