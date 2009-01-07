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

package net.sf.javadc.net;

import java.io.File;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.RequestsModelListener;

import org.apache.log4j.Category;

/**
 * <code>RequestsModelCleanupListener</code> is used to remove temporary files
 * that have been created for the downloading act.
 * 
 * @author Timo Westk�mper
 */
public class RequestsModelCleanupListener implements RequestsModelListener {

    private final static Category logger = Category
            .getInstance(RequestsModelCleanupListener.class);

    /**
     * 
     */
    private final ISettings settings;

    /**
     * Create a new RequestsModelCleanupListener instance
     * 
     * @param settings
     */
    public RequestsModelCleanupListener(ISettings settings) {
        if (settings == null) {
            throw new NullPointerException("settings was null.");
        }

        this.settings = settings;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#connectionRemoved(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void connectionRemoved(IConnection connection, int index) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#connectionAdded(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void connectionAdded(IConnection connection, int index) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#connectionChanged(net.sf.javadc.interfaces.IConnection,
     *      int)
     */
    public void connectionChanged(IConnection connection, int index) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#requestRemoved(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void requestRemoved(IClient client, DownloadRequest dr, int index) {
        File oldFile = new File(dr.getLocalFilename());

        if (dr.isSegment()) {
            logger.info("Download was segmented.");
            return;

        } else if (!oldFile.exists()) {
            logger.info("File " + dr.getLocalFilename() + " didn't exist.");
            return;

        } else if (!oldFile.isFile()) {
            logger.info("File " + dr.getLocalFilename() + " is a directory.");
            return;

        }

        long finalsize = dr.getSearchResult().getFileSize();
        long currsize = oldFile.length();

        if (currsize < finalsize) {

            if (currsize < 10 * 1024 * 1024) {
                logger.info("Removing file with path " + dr.getLocalFilename());

                oldFile.delete();

            } else {
                logger
                        .info("File with path "
                                + dr.getLocalFilename()
                                + " is not deleted, because the file size exceeds 10mb.");

            }

        } else if (currsize > finalsize) {
            logger.error("File " + dr.getLocalFilename()
                    + " was larger than expected.");

        } else if (!settings.getTempDownloadDir().equals(
                settings.getDownloadDir())) {
            // move file if temp download directory is different
            // from directory for finished downloads

            File newFile = new File(settings.getDownloadDir(), oldFile
                    .getName());

            String info = "Moving file into directory for finished downloads.";
            logger.info(info);
            oldFile.renameTo(newFile);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#requestAdded(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void requestAdded(IClient client, DownloadRequest dr, int index) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.RequestsModelListener#requestChanged(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, int)
     */
    public void requestChanged(IClient client, DownloadRequest downloadRequest,
            int index) {
        // TODO Auto-generated method stub

    }

}

/*******************************************************************************
 * $Log: RequestsModelCleanupListener.java,v $
 * Revision 1.10  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.9 2005/09/30 15:59:53
 * timowest updated sources and tests
 * 
 * Revision 1.8 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.7 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
