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

// $Id: UploadRequest.java,v 1.10 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.net;

import java.io.File;

import net.sf.javadc.interfaces.ICommonRequest;

//public class UploadRequest extends GenericModel {
/**
 * <code>UploadRequest</code> represents a request from a remote client to
 * receive a certain file from the local client
 * 
 * @author Timo Westk�mper
 */
public class UploadRequest implements ICommonRequest {

    // private static final Category logger = Category
    // .getInstance(UploadRequest.class);

    // attributes

    /**
     * 
     */
    private File localFile;

    /**
     * Create an UploadRequest with the given File instance
     * 
     * @param _file
     *            to be used
     */
    public UploadRequest(File _file) {
        localFile = _file;
    }

    /**
     * Create am Upload without a related File instance
     */
    public UploadRequest() {

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    /*
     * protected final Class getListenerClass() { return
     * UploadRequestListener.class; }
     */

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;

        } else if (obj instanceof UploadRequest) {
            UploadRequest ur = (UploadRequest) obj;
            return getLocalFilename().equals(ur.getLocalFilename());

        } else {
            return false;

        }

    }

    /**
     * 
     */
    /*
     * public final void fireStateChanged() { // Listeners might remove
     * themselves DownloadRequestListener[] l = (DownloadRequestListener[])
     * listenerList.getListeners(DownloadRequestListener.class);
     * 
     * for (int i = 0; i < l.length; i++) {
     * l[i].downloadRequestStateChanged(this); } }
     */

    /**
     * @return
     */
    /*
     * public DownloadRequestState getState() { return state; }
     */

    /**
     * @param state
     */
    /*
     * public void setState(DownloadRequestState state) { this.state = state; //
     * notifies the EventListeners that the state of the DownloadRequest // has
     * changed fireStateChanged(); }
     */

    /**
     * Return the local file name
     * 
     * @return Returns the localFile.
     */
    public String getLocalFilename() {
        // return localFile;
        return (localFile == null) ? "" : localFile.getPath();
    }
}

/*******************************************************************************
 * $Log: UploadRequest.java,v $
 * Revision 1.10  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.9 2005/09/30 15:59:53 timowest
 * updated sources and tests
 * 
 * Revision 1.8 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
