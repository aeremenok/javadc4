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

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.UploadRequest;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.util.FileUtils;

import org.apache.log4j.Category;

/**
 * <CODE>DownConnectionNode</CODE> represents a <CODE>DownloadNode</CODE> in
 * the <CODE>DownloadComponent</CODE> for a single active Client <CODE>
 * Connection</CODE>
 * 
 * @author Timo Westk�mper
 */
public class DownConnectionNode implements IDownloadNode {

    private final static Category logger = Category
            .getInstance(DownConnectionNode.class);

    private final static DownloadNodeFactory nodeFactory = new DownloadNodeFactory();

    private static final int DOWNLOAD_CONNECTION_NODE = 0;

    private static final int UPLOAD_CONNECTION_NODE = 1;

    // attributes

    private final IConnection connection;

    /**
     * Create a DownConnectionNode instance with the given IConnection instance
     * 
     * @param _connection
     */
    public DownConnectionNode(IConnection _connection) {
        if (_connection == null)
            throw new NullPointerException("_connection was null.");

        connection = _connection;

    }

    /** ********************************************************************** */

    /**
     * Return the child node with the given index
     * 
     * @param i
     *            index of the child node to be returned
     * @return child node if found or null, if not
     */
    public IDownloadNode getChild(int i) {
        int type = getType();

        // Connection is an Upload Connection
        if (type == UPLOAD_CONNECTION_NODE) {

            if (i > 0) {
                throw new IndexOutOfBoundsException();
            }

            return nodeFactory.createUploadRequestNode(connection, connection
                    .getUploadRequest());

            // Connection is an Download Connection
        } else if (type == DOWNLOAD_CONNECTION_NODE) {
            try {
                return nodeFactory.createDownloadRequestNode(connection,
                        connection.getClient().getDownloads()[i]);

            } catch (NullPointerException npe) {
                logger.error("Catched NullPointerException in getChild(int i)");
                logger.error(npe);

                return null;

            }

            // Connection type has not been specified properly
        } else {
            logger.error("Connection type has not been properly specified.");

            return null;

        }
    }

    /**
     * Return the number of children of this DownloadNode
     * 
     * @return the number of download requests in the queue or one, if the
     *         connection is a upload connection
     */
    public int getNumChildren() {
        int type = getType();

        if (type == UPLOAD_CONNECTION_NODE) {

            return 1;

        } else if (type == DOWNLOAD_CONNECTION_NODE) {

            try {
                return connection.getClient().getDownloads().length;

            } catch (NullPointerException npe) {
                return 0;

            }
        } else {
            // not really defined
            return 0;
        }
    }

    /**
     * Return the type of the IConnection instance
     * 
     * @return DOWNLOAD_DIRECTION if the connection is a download connection and
     *         UPLOAD_DIRECTION, if the connection is a upload connection
     */
    private int getType() {

        String dir = connection.getConnectionInfo().getCurrentDirection();

        if ((dir == null) || (dir.equals(ConstantSettings.DOWNLOAD_DIRECTION))) {
            return DOWNLOAD_CONNECTION_NODE;

        } else {
            return UPLOAD_CONNECTION_NODE;

        }
    }

    /**
     * Return the Object at the column with the given index
     * 
     * @param column
     * @return
     */
    public Object getValueAt(int column) {
        String name = getFilename();

        switch (column) {

        case 0:
            return connection.getClient().getNick();

        case 1:
            return FileUtils.getNameNoExtension(name);

        case 2:
            return FileUtils.getExtension(name);

        case 3:
            return FileUtils.getPath(name);

        case 4:
            return connection.getClient().getHost().toString();

        case 5:
            return connection.getState().toString();

        case 6:
            return connection.getConnectionInfo().getInfo();

        }

        return EMPTY_STRING;
    }

    /**
     * Get the active local file name from which the client uploads when
     * operating in upload mode or downloads when operating in download mode
     * 
     * @return the file name used by the connection
     */
    private String getFilename() {
        int type = getType();

        String name = EMPTY_STRING;

        // download connection
        if (type == DOWNLOAD_CONNECTION_NODE) {
            DownloadRequest dr = connection.getDownloadRequest();

            if ((dr != null) && (dr.getSearchResult() != null)) {
                name = dr.getSearchResult().getFilename();

            }

            // upload connection
        } else if (type == UPLOAD_CONNECTION_NODE) {
            UploadRequest ur = connection.getUploadRequest();

            if (ur != null) {
                name = ur.getLocalFilename();

            }

        }

        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.IDownloadNode#getMainObject()
     */
    public Object getMainObject() {
        return connection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return connection.getClient().getNick();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.IDownloadNode#isValid()
     */
    public boolean isValid() {
        // only active connections can be reused
        return connection.getState() != ConnectionState.NOT_CONNECTED;
    }

}

/*******************************************************************************
 * $Log: DownConnectionNode.java,v $
 * Revision 1.12  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.11 2005/09/15 17:32:29 timowest
 * added null checks
 * 
 * Revision 1.10 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
