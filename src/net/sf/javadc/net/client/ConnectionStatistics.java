/*
 * Copyright (C) 2004 Marco Bazzoni
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

package net.sf.javadc.net.client;

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.util.ByteConverter;

import org.apache.log4j.Category;

/**
 * <code>ConnectionStatistics</code> represents statistical data on an active
 * Client <code>Connection</code>.
 * 
 * <p>
 * This data was previously contained directly in the <code>Connection</code>
 * class and has been refactored out for maintenance and testability reasons.
 * 
 * @author Timo Westk�mper
 */
public class ConnectionStatistics {

    private static final Category logger = Category
            .getInstance(ConnectionStatistics.class);

    /**
     * 
     */
    private int bytesPerSecond;

    /**
     * 
     */
    private long bytesReceived;

    /**
     * 
     */
    private long lastBytesReceived;

    /**
     * 
     */
    private long lastStateChange;

    /**
     * 
     */
    private long fileLength;

    /**
     * 
     */
    private long startTime;

    /**
     * 
     */
    private String infoBuffered;

    /**
     * 
     */
    private long lastUpdate;

    /**
     * 
     */
    private long startLocation;

    /**
     * 
     */
    private long segmentOffset;

    /**
     * Create a ConnectionStatistics instance
     */
    public ConnectionStatistics() {

        // clientConnection = _clientConnection;
    }

    /** ********************************************************************** */

    /**
     * Obtain the connection speed in bytes per second
     * 
     * @return
     */
    public int getBytesPerSecond() {
        return bytesPerSecond;

    }

    /**
     * Obtain the amount of bytes already received
     * 
     * @return
     */
    public long getBytesReceived() {
        return bytesReceived;

    }

    /**
     * Obtain the amount of bytes already received ?
     * 
     * @return
     */
    public long getLastBytesReceived() {
        return lastBytesReceived;

    }

    /**
     * Get the timestamp of the last state change
     * 
     * @return
     */
    public long getLastStateChange() {
        return lastStateChange;

    }

    /**
     * Set the connection speed in bytes per second
     * 
     * @param i
     */
    public void setBytesPerSecond(int i) {
        if (i >= 0) {
            bytesPerSecond = i;

        }

    }

    /**
     * Set the amount of received bytes
     * 
     * @param l
     */
    public void setBytesReceived(long l) {
        if (l >= 0) {
            bytesReceived = l;

        }

    }

    /**
     * Set the amount of last received bytes
     * 
     * @param l
     */
    public void setLastBytesReceived(long l) {
        if (l >= 0) {
            lastBytesReceived = l;

        }

    }

    /**
     * Set the timestamp of the last state change
     * 
     * @param l
     */
    public void setLastStateChange(long l) {
        lastStateChange = l;

    }

    /**
     * Obtain the connection information
     * 
     * @return
     */
    public String getConnectionInfo() {
        long time = System.currentTimeMillis();
        int interval = ConstantSettings.STATISTICS_UPDATE_INTERVAL;

        if ((time - lastUpdate) > interval) {

            try {
                setBytesPerSecond((int) (((getBytesReceived() - getLastBytesReceived()) * 1000) / (time - lastUpdate)));

            } catch (Exception e) {
                logger.error(e);

            }

            setLastBytesReceived(getBytesReceived());
            infoBuffered = null;

            lastUpdate = time; // updates the lastUpdate field

        }

        // create info string and cache it
        if (infoBuffered == null) {
            StringBuffer str = new StringBuffer();

            // segment start point needs to be given
            // for segmented downloads file length is equal to
            // segment end point

            long temp = getBytesReceived() - getSegmentOffset();
            long full = getFileLength() - getSegmentOffset();

            try {
                str.append((temp * 100) / full);

            } catch (Exception e) {
                logger
                        .error("The FileLength property has not been properly set.");
                str = new StringBuffer("0");

            }

            // in the following parts the segment offset is not taken into
            // account, as otherwise the connection info string wouldn't contain
            // any information on the offset

            str.append("%  ");
            str.append(ByteConverter.byteToShortString(getBytesPerSecond()));
            str.append("/s  ");
            str.append(ByteConverter.byteToShortString(getBytesReceived()));
            str.append(" / ");
            str.append(ByteConverter.byteToShortString(getFileLength()));

            // add the number of the segment offset

            if (getSegmentOffset() > 0) {
                str.append(" (").append(
                        getSegmentOffset()
                                / ConnectionSettings.DOWNLOAD_SEGMENT_SIZE)
                        .append(")");
            }

            infoBuffered = str.toString();

        }

        return infoBuffered;

    }

    /**
     * Obtain the file length in bytes
     * 
     * @return
     */
    public long getFileLength() {
        return fileLength;

    }

    /**
     * Set the file length in bytes
     * 
     * @param l
     */
    public void setFileLength(long l) {
        if (l >= 0) {
            fileLength = l;

        }

    }

    /**
     * Obtain the timestamp of the start time
     * 
     * @return
     */
    public long getStartTime() {
        return startTime;

    }

    /**
     * Set the timestamp of the start time
     * 
     * @param l
     */
    public void setStartTime(long l) {
        startTime = l;

    }

    /**
     * Set the location in bytes from where the download or resume starts
     * 
     * @param fileLength2
     */
    public void setStartLocation(long startLocation) {
        this.startLocation = startLocation;

    }

    /**
     * Get the location in bytes from where the download or resume started
     * 
     * @return
     */
    public long getStartLocation() {
        return startLocation;
    }

    /**
     * @return Returns the segmentOffset.
     */
    public long getSegmentOffset() {
        return segmentOffset;
    }

    /**
     * @param segmentOffset
     *            The segmentOffset to set.
     */
    public void setSegmentOffset(long segmentOffset) {
        this.segmentOffset = segmentOffset;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer("Connection statistics : ");
        buffer.append("\n  bytesPerSecond    : ").append(bytesPerSecond);
        buffer.append("\n  bytesReceived     : ").append(bytesReceived);
        buffer.append("\n  lastBytesReceived : ").append(lastBytesReceived);
        buffer.append("\n  fileLength        : ").append(fileLength);
        buffer.append("\n  startTime         : ").append(startTime);
        buffer.append("\n  startlocation     : ").append(startLocation);
        buffer.append("\n  segmentOffset     : ").append(segmentOffset);

        return buffer.toString();
    }

}

/*******************************************************************************
 * $Log: ConnectionStatistics.java,v $
 * Revision 1.15  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.14 2005/09/30 15:59:53
 * timowest updated sources and tests
 * 
 * Revision 1.13 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
