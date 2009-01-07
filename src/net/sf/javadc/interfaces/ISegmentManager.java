/*
 * Created on 8.4.2005
 */

package net.sf.javadc.interfaces;

import net.sf.javadc.listeners.SegmentManagerListener;
import net.sf.javadc.net.DownloadRequest;

/**
 * @author Timo Westk�mper
 */
public interface ISegmentManager extends IObject {

    /**
     * Get a segmented DownloadRequest for the given normal DownloadRequest
     * 
     * @param dr
     * @return the segmented DownloadRequest or null, if the all segments are
     *         being used or the given DownloadRequest is already segmented
     */
    public abstract DownloadRequest getNextSegment(DownloadRequest dr);

    /**
     * Add the given SegmentManagerListener to the list of listeners
     * 
     * @param listener
     */
    public abstract void addListener(SegmentManagerListener listener);

    /**
     * Remove the given SegmentManagerListener from the list of listeners
     * 
     * @param listener
     */
    public abstract void removeListener(SegmentManagerListener listener);
}