/*
 * Created on 6.4.2005
 */

package net.sf.javadc.net;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITask;

import org.apache.log4j.Category;

/**
 * @author Timo Westk�mper
 */
public class DownloadSegmentList
    extends AbstractDownloadSegmentList
{
    private class DownloadRequestIteration
        implements
            ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(
            ActionEvent arg0 )
        {

            int tempSize = 0;

            // iterates over the slots and checks the download requests

            logger.debug( "Iterating over " + requests.length + " slots." );

            for ( int i = 0; i < requests.length; i++ )
            {

                if ( requests[i] != null )
                {
                    logger.debug( "Checking the state of " + requests[i] );

                    // slot can be emptied
                    if ( checkState( requests[i] ) )
                    {
                        requests[i] = null;
                    }

                }

                File temp = new File( localFilenamePrefix + getSuffix( i + 1, requests.length ) );

                if ( temp.exists() )
                {
                    tempSize += temp.length();
                }
            }

            // all segments have been downloaded

            if ( tempSize == fullsize )
            {
                logger.info( "Merging " + requests.length + " segments of " + localFilenamePrefix );

                // merge the segments and stop the timer

                ITask mergeTask = new SegmentManagerMergeTask( localFilenamePrefix, requests.length );

                mergeTask.runTask();

                timer.stop();

            }

        }

    }

    private static final Category logger = Category.getInstance( DownloadSegmentList.class );

    /**
     * 
     */
    private DownloadRequest[]     requests;

    /**
     * 
     */
    private int                   segmentSize, fullsize;

    /**
     * 
     */
    private String                localFilenamePrefix;

    // external

    /**
     * 
     */
    private Timer                 timer;

    /**
     * 
     */
    private ISettings             settings;

    /**
     * Get the filename suffix for the given index and the given full amount of files
     * 
     * @param index
     * @param full
     * @return
     */
    public static String getSuffix(
        int index,
        int full )
    {

        StringBuffer buffer = new StringBuffer();
        buffer.append( "." );
        getSuffix( buffer, index );
        buffer.append( "of" );
        getSuffix( buffer, full );
        return buffer.toString();

    }

    /**
     * Put the filename for the given index into the given StringBuffer instance
     * 
     * @param index
     * @return
     */
    private static final void getSuffix(
        StringBuffer buffer,
        int index )
    {
        if ( index < 10 )
        {
            buffer.append( "00" );
        }
        else if ( index < 100 )
        {
            buffer.append( "0" );
        }

        buffer.append( index );
    }

    /**
     * Create a new DownloadSegmentList instance
     * 
     * @param size
     * @param segmentSize
     * @param localFilenamePrefix
     * @param settings
     */
    public DownloadSegmentList(
        int size,
        int segmentSize,
        int fullsize,
        String localFilenamePrefix,
        ISettings settings )
    {

        if ( localFilenamePrefix == null )
        {
            throw new NullPointerException( "localFilenamePrefix was null." );
        }

        if ( settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }

        // segment lists for segmented downloads with less then two segments
        // are not allowed
        if ( size < 2 )
        {
            throw new RuntimeException( "No segmentation possible for sizes of " + size );
        }

        this.segmentSize = segmentSize;
        this.fullsize = fullsize;

        this.localFilenamePrefix = localFilenamePrefix;

        this.settings = settings;
        requests = new DownloadRequest[size];

        logger.debug( "Starting timer for DownloadRequest iteration." );

        timer = new Timer( 2 * 60 * 1000, new DownloadRequestIteration() );
        timer.setInitialDelay( 100 );

        timer.start();
    }

    /**
     * Create a new segmented DownloadRequest for the given DownloadRequest instance
     * 
     * @param dr
     * @return
     */
    public DownloadRequest createSegment(
        DownloadRequest dr )
    {

        if ( dr == null )
        {
            throw new NullPointerException( "dr was null." );
        }

        logger.debug( "About to create segmented download for " + dr );

        for ( int i = 0; i < requests.length; i++ )
        {

            if ( requests[i] == null )
            {

                DownloadRequest newrequest =
                    new DownloadRequest( dr.getSearchResult(), new File( localFilenamePrefix +
                        getSuffix( i + 1, requests.length ) ), settings );

                Point segment = new Point( i * segmentSize, Math.min( (i + 1) * segmentSize, fullsize ) );

                newrequest.setSegment( segment );

                // return the element only if there is still something to
                // download

                if ( newrequest.getTempFileSize() < segment.y - segment.x )
                {
                    logger.debug( "Creation of segmented download succeeded." );

                    // fill the slot
                    requests[i] = newrequest;

                    return newrequest;

                }
                else
                {
                    logger.debug( newrequest.getTempFileSize() + " is not smaller than " + (segment.y - segment.x) );

                }

            }
            else
            {

                logger.debug( "Slot for segment " + (i + 1) + " was already reserved." );
            }

        }

        // return null if all slots are full
        return null;
    }

    /**
     * Get the DownloadRequest instance of the given slot
     * 
     * @param i
     * @return
     */
    public DownloadRequest getSlot(
        int i )
    {
        return requests[i];
    }

    /**
     * Put the given DownloadRequest instance into the given slot
     * 
     * @param i
     * @param dr
     */
    public void setSlot(
        int i,
        DownloadRequest dr )
    {
        requests[i] = dr;
    }

    /**
     * @param downloadRequest
     */
    private boolean checkState(
        DownloadRequest downloadRequest )
    {

        // if the segment is complete create a new segmented download
        // with the same client information

        int segmentSize = downloadRequest.getSegment().y - downloadRequest.getSegment().x;

        logger.debug( "Checking file size of " + downloadRequest.getLocalFilename() );

        if ( downloadRequest.getTempFileSize() == segmentSize )
        {
            logger.debug( "Creating new segment with same client data." );

            DownloadRequest newSegment = createSegment( downloadRequest );

            // notify registered listeners that the new segment
            // should be downloaded
            if ( newSegment != null )
            {
                notifyStartDownload( newSegment );
                return true;

            }
            else
            {
                logger.debug( "No segments left for downloading " + localFilenamePrefix );

            }

        }
        else
        {

            logger.debug( downloadRequest.getTempFileSize() + " of " + segmentSize + " have been downloaded." );
        }

        return false;

    }

}

/*******************************************************************************
 * $Log: DownloadSegmentList.java,v $ Revision 1.7 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.6
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.5 2005/09/12 21:12:02 timowest added log block
 */
