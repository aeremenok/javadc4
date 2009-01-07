/*
 * Copyright (C) 2001 Ryan Sweny, cabinboy@0wned.org Copyright (C) 2004 Timo Westkämper This program is free software;
 * you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id:
package net.sf.javadc.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.SegmentManagerListener;

import org.apache.log4j.Category;

/**
 * <code>DownloadSegmentManager</code> represents a helper class of a <code>DownloadRequest</code>, which allows a file
 * to be downloaded in segmented fashion.
 * 
 * @author Timo Westk�mper
 * @author Ryan Sweny
 */
public class SegmentManager
    implements
        ISegmentManager
{

    private final static Category logger        = Category.getInstance( SegmentManager.class );

    // private String filename = "";
    // private int numSegments = 0;

    /**
     * 
     */
    private List                  listeners     = new ArrayList();

    // components
    /**
     * 
     */
    private final ISettings       settings;

    // internal
    /**
     * 
     */
    private final Map             hash2segments = new HashMap();

    /**
     * Create a new SegmentManager instance
     */
    public SegmentManager(
        ISettings _settings )
    {

        if ( _settings == null )
        {
            throw new NullPointerException( "_settings was null." );
        }

        settings = _settings;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISegmentManager#addListener(net.sf.javadc.listeners.SegmentManagerListener)
     */
    public void addListener(
        SegmentManagerListener listener )
    {

        if ( listener == null )
        {
            throw new NullPointerException( "listener was null." );
        }

        listeners.add( listener );

        for ( Iterator i = hash2segments.values().iterator(); i.hasNext(); )
        {
            DownloadSegmentList list = (DownloadSegmentList) i.next();
            list.addListener( listener );
        }

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISegmentManager#getNextSegment(net.sf.javadc.net.DownloadRequest)
     */
    public DownloadRequest getNextSegment(
        DownloadRequest dr )
    {

        if ( dr == null )
        {
            throw new NullPointerException( "dr was null." );
        }

        SearchResult sr = dr.getSearchResult();
        String hash = sr.getTTH();

        if ( hash != null )
        {

            if ( !dr.isSegment() )
            {

                // retrieve the DownloadSegmentList which is mapped against the
                // hash of the SearchResult

                DownloadSegmentList segments = (DownloadSegmentList) hash2segments.get( hash );

                if ( segments == null )
                {
                    segments = createSegmentTemplate( dr );

                    if ( segments != null )
                    {
                        hash2segments.put( hash, segments );
                    }
                }

                if ( segments != null )
                {
                    return segments.createSegment( dr );

                }
                else
                {
                    return null;

                }

            }
            else
            {
                logger.debug( "The DownloadRequest was already a segmented download." );
                return null;
            }

        }
        else
        {
            logger.debug( "The DownloadRequest did not contain any hash " + "information." );

            // return null if the SearchResult of the DownloadRequest has no
            // hashing
            // information
            return null;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISegmentManager#removeListener(net.sf.javadc.listeners.SegmentManagerListener)
     */
    public void removeListener(
        SegmentManagerListener listener )
    {

        if ( listener == null )
        {
            throw new NullPointerException( "listener was null." );
        }

        listeners.remove( listener );

        for ( Iterator i = hash2segments.values().iterator(); i.hasNext(); )
        {
            DownloadSegmentList list = (DownloadSegmentList) i.next();
            list.removeListener( listener );
        }

    }

    /**
     * Create an empty DownloadSegmentList for the given DownloadRequest instance
     * 
     * @param dr
     * @return
     */
    private DownloadSegmentList createSegmentTemplate(
        DownloadRequest dr )
    {

        long size = dr.getSearchResult().getFileSize();

        int templates = (int) Math.ceil( (double) size / ConnectionSettings.DOWNLOAD_SEGMENT_SIZE );

        if ( templates < 2 )
        {
            return null;

        }
        else
        {
            DownloadSegmentList list =
                new DownloadSegmentList( templates, ConnectionSettings.DOWNLOAD_SEGMENT_SIZE, (int) size, dr
                    .getLocalFilename(), settings );

            // add the registered listeners to the new DownloadSemgnetList
            list.addListeners( listeners );

            return list;

        }

    }

}

/*******************************************************************************
 * $Log: SegmentManager.java,v $ Revision 1.8 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.7
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.6 2005/09/12 21:12:02 timowest added log block
 */
