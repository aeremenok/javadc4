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

package net.sf.javadc.gui.model;

import java.util.HashMap;
import java.util.Map;

import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.UploadRequest;

/**
 * <CODE>DownloadNodeFactory</CODE> is a combined Factory Method and Object
 * Pool implementation to decrease the amount of created <CODE>DownloadNode
 * </CODE> instances
 * 
 * @author Timo Westk�mper
 */
public class DownloadNodeFactory {

    private Map connectionNodes = new HashMap();

    private Map downloadRequestNodes = new HashMap();

    private Map uploadRequestNodes = new HashMap();

    /**
     * Create a ClientConnection node
     * 
     * @param cc
     *            ClientConnection to be used
     * 
     * @return a DownloadNode with a reference to a Download or Upload
     *         Connection
     */
    public IDownloadNode createConnectionNode(IConnection cc) {
        if (cc == null) {
            throw new NullPointerException("ClientConnection was null.");
        }

        String name = cc.getClient().getNick();

        name = (name.trim().equals("")) ? null : name.trim();

        IDownloadNode node = null;

        // use cache only if a real nick is provided
        if (name != null) {
            node = (IDownloadNode) connectionNodes.get(name);

            if (node != null) {
                node = node.isValid() ? node : null;

            }

        }

        // create a new node and map it
        if (node == null) {
            node = new DownConnectionNode(cc);

            // use cache only if a real nick is provided
            if (name != null) {
                connectionNodes.put(name, node);

            }

        }

        return node;

    }

    /**
     * Create a UploadRequest Node
     * 
     * @param clientConnection
     *            ClientConnection of the UploadRequest
     * @param uploadRequest
     *            the UploadRequest itself
     * 
     * @return a DownloadNode containing the UploadRequest information
     */
    public IDownloadNode createUploadRequestNode(IConnection clientConnection,
            UploadRequest uploadRequest) {
        if (clientConnection == null) {
            throw new NullPointerException("ClientConnection was null.");
        } else if (uploadRequest == null) {
            throw new NullPointerException("UploadRequest was null.");
        }

        String name = uploadRequest.getLocalFilename();

        name = (name.trim().equals("")) ? null : name.trim();

        IDownloadNode node = null;

        // use cache only if a real name is provided
        if (name != null) {
            node = (IDownloadNode) uploadRequestNodes.get(name);

            if (node != null) {
                node = node.isValid() ? node : null;

            }

        }

        // create a new node and map it
        if (node == null) {
            node = new DownUploadRequestNode(clientConnection, uploadRequest);

            // use cache only if a real name is provided
            if (name != null) {
                uploadRequestNodes.put(name, node);

            }

        }

        return node;

    }

    /**
     * Create a DownloadRequest Node
     * 
     * @param clientConnection
     *            ClientConnection of the DownloadRequest
     * @param request
     *            the DownloadRequest node itself
     * 
     * @return a DownloadNode containing the DownloadRequest information
     */
    public IDownloadNode createDownloadRequestNode(
            IConnection clientConnection, DownloadRequest request) {
        if (clientConnection == null) {
            throw new NullPointerException("ClientConnection was null.");
        } else if (request == null) {
            throw new NullPointerException("DownloadRequest was null.");
        }

        String name = request.getSearchResult().getFilename();

        name = (name.trim().equals("")) ? null : name.trim();

        IDownloadNode node = null;

        // use cache only if a real name if provided
        if (name != null) {
            node = (IDownloadNode) downloadRequestNodes.get(name);

            if (node != null) {
                node = node.isValid() ? node : null;

            }

        }

        // create a new node and map it
        if (node == null) {
            node = new DownDownloadRequestNode(clientConnection, request);

            // use cache only if a real nick is provided
            if (name != null) {
                downloadRequestNodes.put(name, node);

            }

        }

        return node;

    }

    /**
     * @return Returns the connectionNodes.
     */
    public Map getConnectionNodes() {
        return connectionNodes;

    }

    /**
     * @param connectionNodes
     *            The connectionNodes to set.
     */
    public void setConnectionNodes(Map connectionNodes) {
        this.connectionNodes = connectionNodes;

    }

    /**
     * @return Returns the downloadRequestNodes.
     */
    public Map getDownloadRequestNodes() {
        return downloadRequestNodes;

    }

    /**
     * @param downloadRequestNodes
     *            The downloadRequestNodes to set.
     */
    public void setDownloadRequestNodes(Map downloadRequestNodes) {
        this.downloadRequestNodes = downloadRequestNodes;

    }

    /**
     * @return Returns the uploadRequestNodes.
     */
    public Map getUploadRequestNodes() {
        return uploadRequestNodes;

    }

    /**
     * @param uploadRequestNodes
     *            The uploadRequestNodes to set.
     */
    public void setUploadRequestNodes(Map uploadRequestNodes) {
        this.uploadRequestNodes = uploadRequestNodes;

    }

}

/*******************************************************************************
 * $Log: DownloadNodeFactory.java,v $
 * Revision 1.10  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.9 2005/09/14 07:11:49 timowest
 * updated sources
 * 
 * 
 * 
 */
