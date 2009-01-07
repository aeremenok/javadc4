/*
 * Copyright (C) 2004 Marco Bazzoni This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.net.client;

import java.util.ArrayList;
import java.util.List;

import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.ISettings;

import org.apache.log4j.Category;

/**
 * <code>ConnectionSlotReservation</code> is a utility class used by <code>ConnectionManager</code> to manage the
 * download and upload slot reservations of the active Client <code>Connections</code>
 * 
 * @author Timo Westk�mper
 */
public class ConnectionSlotReservation
{
    private static final Category logger              = Category.getInstance( ConnectionSlotReservation.class );

    /**
     * 
     */
    private final List            downloadConnections = new ArrayList();

    /**
     * 
     */
    private final List            uploadConnections   = new ArrayList();

    // external components
    /**
     * 
     */
    private final ISettings       settings;

    /**
     * Create a ConnectionSlotReservation instance with the given ISettings
     * 
     * @param _settings ISettings instance to be used
     */
    public ConnectionSlotReservation(
        ISettings _settings )
    {

        if ( _settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }

        settings = _settings;

    }

    /** ********************************************************************** */

    /**
     * Free a download slot reserved for the given Connection
     * 
     * @param conn Connection for which a download slot was reserved
     * @return true, if freeing was successfull and false, if not
     */
    public boolean freeDownloadSlot(
        IConnection conn )
    {
        // if the Connection has reserved a slot
        if ( downloadConnections.contains( conn ) )
        {
            settings.releaseDownloadSlot();
            downloadConnections.remove( conn );

            logger.debug( "Released download slot for " + conn );
            displayInfo();
            return true;

            // if the Connection has not reserved a slot
        }
        else
        {
            logger.debug( "Not used as DownloadConnection." );
            return false;
        }

    }

    /**
     * Free an upload slot which has been reserved for the given Connection
     * 
     * @param conn Connection for which an upload slot has been reserved
     * @return true, if the freeing was successfull and false, if not
     */
    public boolean freeUploadSlot(
        IConnection conn )
    {
        // if the Connection has reserved an upload slot
        if ( uploadConnections.contains( conn ) )
        {
            settings.releaseUploadSlot();
            uploadConnections.remove( conn );

            logger.debug( "Released upload slot for " + conn );
            displayInfo();
            return true;

            // if the Connection has not reserved an upload slot
        }
        else
        {
            logger.debug( "Not used as UploadConnection." );
            return false;

        }

    }

    /**
     * Return the active download Connections
     * 
     * @return Returns the downloadConnections.
     */
    public List getDownloadConnections()
    {
        return downloadConnections;
    }

    /**
     * Return the active Upload connections
     * 
     * @return Returns the uploadConnections.
     */
    public List getUploadConnections()
    {
        return uploadConnections;
    }

    /**
     * Remove the given Connection from either the active downloads or uploads
     * 
     * @param conn Connection to be removed
     * @return true, if removal succeeded and false, if not
     */
    public boolean removeConnection(
        IConnection conn )
    {

        return freeDownloadSlot( conn ) || freeUploadSlot( conn );

    }

    /**
     * Reserve a download slot for the given Connection
     * 
     * @param conn Connection for which a download slot should be reserved
     * @return true, if reservation was successfull and false, if not
     */
    public boolean useDownloadSlot(
        IConnection conn )
    {
        // if the Connection has not reserved an download slot
        if ( !downloadConnections.contains( conn ) )
        {

            // try to reserve a download slot via the Settings instance
            if ( !settings.reserveDownloadSlot() )
            {
                return false;

            }
            else
            {
                downloadConnections.add( conn );
                logger.debug( "Used download slot for " + conn );
                displayInfo();

                return true;

            }

            // if the Connection has already reserved a download slot
        }
        else
        {
            // logger.debug("Already used as DownloadConnection.");
            return false;

        }

    }

    /**
     * Reserve an upload slot for the given Connection
     * 
     * @param conn Connection for which an upload slot should be reserved
     * @return true, if the reservation was successfull and false, if not
     */
    public boolean useUploadSlot(
        IConnection conn )
    {
        // if the Connection has not reserved an upload slot
        if ( !uploadConnections.contains( conn ) )
        {
            // try to reserve an upload slot via the Settigns instance
            if ( !settings.reserveUploadSlot() )
            {
                return false;

            }
            else
            {
                uploadConnections.add( conn );
                logger.debug( "Used upload slot for " + conn );
                displayInfo();
                return true;
            }

            // if the Connection has reserved an upload slot
        }
        else
        {
            // logger.debug("Already used as UploadConnection.");
            return false;

        }

    }

    /**
     * Log information about the amount of used download and upload slots
     */
    private void displayInfo()
    {

        logger.info( "Used slots : " + downloadConnections.size() + " / " + uploadConnections.size() );
    }
}

/*******************************************************************************
 * $Log: ConnectionSlotReservation.java,v $ Revision 1.17 2005/10/02 11:42:28 timowest updated sources and tests
 * Revision 1.16 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.15 2005/09/12 21:12:02 timowest added
 * log block
 */
