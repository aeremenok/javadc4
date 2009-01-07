/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
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

// $Id: DownloadRequest.java,v 1.23 2006/05/30 14:20:37 pmoukhataev Exp $
package net.sf.javadc.net;

import net.sf.javadc.interfaces.ICommonRequest;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.DownloadRequestListener;
import net.sf.javadc.util.GenericModel;
import org.apache.log4j.Category;

import java.awt.*;
import java.io.File;

/**
 * <code>DownloadRequest</code> represent a request to another client, to
 * upload a certain file.
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.23 $ $Date: 2006/05/30 14:20:37 $
 */
public class DownloadRequest extends GenericModel implements ICommonRequest {

    private static final Category logger = Category
            .getInstance(DownloadRequest.class);

    // attributes
    /**
     * 
     */
    private final SearchResult searchResult;

    /**
     * 
     */
    private File localFilename;

    /**
     * 
     */
    private boolean isComplete;

    /**
     * 
     */
    private boolean resume = true;

    /**
     * 
     */
    private Point offsets;

    /**
     * 
     */
    private boolean isSegment = false;

    /**
     * 
     */
    private DownloadRequestState state = DownloadRequestState.OFFLINE;

    /**
     * 
     */
    private transient boolean failed = false;

    // components
    // private final ISettings settings;

    /**
     * 
     */
    private String toString = null;

    /**
     * Create a DownloadRequest with the given SearchResult and ISettings
     * instance
     * 
     * @param _searchResult
     *            SearchResult to be used
     * @param _settings
     *            ISettings instance to be used
     */
    public DownloadRequest(SearchResult _searchResult, ISettings _settings) {
        // settings = _settings;

        if (_searchResult == null)
            throw new NullPointerException("_searchResult was null.");
        else if (_settings == null)
            throw new NullPointerException("_settings was null.");

        String _localfilename = _searchResult.getFilename();

        // save File at first into the directory for temporary downloads and
        // move it to the directory of finished downloads when download has
        // finished

        if (_localfilename != null) {

            // truncating (Unix style paths)
            if (_localfilename.indexOf("\\") > -1) {
                logger.debug("Truncating path " + _localfilename);
                _localfilename = _localfilename.substring(_localfilename
                        .lastIndexOf("\\") + 1);

                // truncating (Windows style paths)
            } else if (_localfilename.indexOf("/") > -1) {
                logger.debug("Truncating path " + _localfilename);
                _localfilename = _localfilename.substring(_localfilename
                        .lastIndexOf("/") + 1);

                // no truncating
            } else {
                logger.debug("No truncating needed for " + _localfilename);

            }

            localFilename = new File(_settings.getTempDownloadDir()
                    + File.separator + _localfilename);

            logger.debug("Set localFilename to " + localFilename.toString());

        } else {
            throw new NullPointerException(
                    "localfilename in SearchResult was null.");

        }

        searchResult = _searchResult;

    }

    /**
     * Create a DownloadRequest with the given SearchResult, local file name and
     * ISettings instance
     * 
     * @param _searchResult
     *            SearchResult this DownloadRequest is related to
     * @param _localFilename
     *            local file name to be used
     * @param _settings
     *            ISettings instance to be used
     */
    public DownloadRequest(SearchResult _searchResult, String _localFilename,
            ISettings _settings) {

        if (_searchResult == null)
            throw new NullPointerException("_searchResult was null.");
        else if (_localFilename == null)
            throw new NullPointerException("_localFilename was null.");
        else if (_settings == null)
            throw new NullPointerException("_settings was null.");

        // settings = _settings;
        searchResult = _searchResult;

        localFilename = new File(_localFilename);

    }

    /**
     * Create a DownloadRequest with the given SearchResult, local file name and
     * ISettings instance
     * 
     * @param _searchResult
     *            SearchResult this DownloadRequest is related to
     * @param _localFile
     *            local file name to be used
     * @param _settings
     *            ISettings instance to be used
     */
    public DownloadRequest(SearchResult _searchResult, File _localFile,
            ISettings _settings) {

        if (_searchResult == null)
            throw new NullPointerException("_searchResult was null.");
        else if (_localFile == null)
            throw new NullPointerException("_localFile was null.");
        else if (_settings == null)
            throw new NullPointerException("_settings was null.");

        // settings = _settings;
        searchResult = _searchResult;

        // save File at first into the directory for temporary downloads and
        // move it to the directory of finished downloads when download has
        // finished

        localFilename = new File(new File(_settings.getTempDownloadDir()),
                _localFile.getName());

    }

    /** ********************************************************************** */

    /**
     * Get the SearchResult associated with this DownloadRequest
     * 
     * @return
     */
    public final SearchResult getSearchResult() {
        return searchResult;

    }

    /**
     * Get the local filename associated with this DownloadRequest
     * 
     * @return
     */
    public final String getLocalFilename() {

        if (localFilename != null)
            return localFilename.toString();
        else
            return null;

    }

    /**
     * @param localFilename
     *            The localFilename to set.
     */
    public void setLocalFilename(String localFilename) {

        if (localFilename == null)
            this.localFilename = null;
        else
            this.localFilename = new File(localFilename);
    }

    /**
     * Return whether the download related to this DownloadRequest is complete
     * 
     * @return
     */
    public final boolean isComplete() {
        return isComplete;

    }

    /**
     * Set whether the download related to this DownloadRequest has finished
     * 
     * @param complete
     */
    public final void setComplete(boolean complete) {
        isComplete = complete;

    }

    /**
     * ?
     * 
     * @param offsets
     */
    public final void setSegment(Point offsets) {
        this.offsets = offsets;
        isSegment = true;

    }

    /**
     * ?
     * 
     * @return
     */
    public final Point getSegment() {
        return offsets;

    }

    /**
     * Return whether segmentation is used to download
     * 
     * @return
     */
    public final boolean isSegment() {
        return isSegment;

    }

    /**
     * Return whether the download related to this DownloadRequest is based on a
     * resume to an already started download
     * 
     * @return
     */
    public final boolean isResume() {
        return resume;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    protected final Class getListenerClass() {
        return DownloadRequestListener.class;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;

        } else if (obj == this) {
            return true;

        } else if (obj instanceof DownloadRequest) {
            DownloadRequest dr = (DownloadRequest) obj;

            if (isSegment() && dr.isSegment()) {
                return getSegment().equals(dr.getSegment())
                        && getSearchResult().equals(dr.getSearchResult());

            } else if (!isSegment() && !dr.isSegment()) {
                return getSearchResult().equals(dr.getSearchResult());

            }
        }

        return false;

    }

    /**
     * Notify registered listeners that the state has changed
     */
    public final void fireDownloadRequestStateChanged() {
        // Listeners might remove themselves
        DownloadRequestListener[] l = (DownloadRequestListener[]) listenerList
                .getListeners(DownloadRequestListener.class);

        for (int i = 0; i < l.length; i++) {
            l[i].downloadRequestStateChanged(this);

        }

    }

    /**
     * Get the current state of the DownloadRequest
     * 
     * @return
     */
    public DownloadRequestState getState() {
        return state;

    }

    /**
     * Set the state of the DownloadRequest
     * 
     * @param state
     */
    public void setState(DownloadRequestState state) {
        this.state = state;

        // notifies the EventListeners that the state of the DownloadRequest
        // has changed
        fireDownloadRequestStateChanged();

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {

        if (toString != null)
            return toString;

        if (searchResult != null) {
            toString = "DownloadRequest : " + searchResult.toString();

        } else if (localFilename != null) {
            toString = "DownloadRequest : " + localFilename.getPath();

        } else {
            toString = super.toString();

        }

        return toString;
    }

    /**
     * @return Returns the failed.
     */
    public boolean isFailed() {
        return failed;
    }

    /**
     * @param failed
     *            The failed to set.
     */
    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    /**
     * Return the length in bytes of the temporary download file
     * 
     * @return
     */
    public long getTempFileSize() {
        return localFilename.length();

    }
}

/*******************************************************************************
 * $Log: DownloadRequest.java,v $
 * Revision 1.23  2006/05/30 14:20:37  pmoukhataev
 * Windows installer created
 *
 * Revision 1.22  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.21 2005/09/30 15:59:53 timowest
 * updated sources and tests
 * 
 * Revision 1.20 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
