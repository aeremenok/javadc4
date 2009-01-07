/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: ConnectionSettings.java,v 1.15 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * @author Timo Westk�mper
 */
public class ConnectionSettings
{

    private final static Category logger                           = Logger.getLogger( ConnectionSettings.class );

    // Client Connection related

    // public static int UPLOAD_BLOCK_SIZE = 64000;
    /**
     * 
     */
    public static int             UPLOAD_BLOCK_SIZE                = 8000;
    /**
     * 
     */
    public static int             SOCKET_INPUT_BUFFER_SIZE         = 17520;

    /***************************************************************************
     * size in bytes which is reduced from the end position of the file when resuming
     **************************************************************************/
    // public static int VERIFY_RESUME_SIZE = 100 * 1024;
    public static int             VERIFY_RESUME_SIZE               = SOCKET_INPUT_BUFFER_SIZE;

    // public static int BUFFER_SIZE = 1024 * 1024 * 5;

    /**
     * 
     */
    public static int             DOWNLOAD_SEGMENT_SIZE            = 40 * 1024 * 1024;

    /**
     * 
     */
    public static int             DOWNLOAD_CHUNK_SIZE              = 50 * 1024 * 1024;

    /**
     * delay time in microseconds used in Client Tasks when cancelling the Client Connection
     */
    public static int             CANCEL_CONNECTION_DELAY          = 1 * 1000;

    /** delay time for rescheduling remotely queued downloads */
    public static int             CONNECTION_RETRY_INTERVAL        = 20 * 1000;

    /** time the Connection is idle */
    public static int             CONNECTION_WAITING_TIME          = 20 * 1000;

    /** maximal amount of parallel connection * */
    public static int             MAX_CONNECTION_COUNT             = 15;

    // Hub Connection related

    /** Command handling of Hub connections is interrupted for 100 seconds * */
    public static int             HUB_THREAD_INTERRUPTION_TIME     = 100;

    /** Command handling of Hub connections is interrupted after 300 commands * */
    public static int             HUB_THREAD_INTERRUPTION_INTERVAL = 300;

    /** Hub Download Request queuing related */
    public static int             HUB_FLUSHDOWNLOADQUEUE_DELAY     = 20 * 1000;

    /**
     * 
     */
    public static int             HUB_FLUSHDOWNLOADQUEUE_INTERVAL  = 10 * 1000;

    static
    {
        InputStream stream = ConnectionSettings.class.getClassLoader().getResourceAsStream( "connection.properties" );

        if ( stream != null )
        {

            try
            {
                loadProperties( stream );

            }
            catch ( Exception e )
            {
                logger.error( "Caught " + e.getClass().getName(), e );

            }

        }
        else
        {

            logger.error( "InputStream for connection.properties " + "could not be created." );
        }

    }

    /**
     * @param stream
     * @throws IOException
     */
    private static void loadProperties(
        InputStream stream )
        throws IOException
    {
        Properties props = new Properties();

        props.load( stream );

        UPLOAD_BLOCK_SIZE = Integer.parseInt( props.getProperty( "UPLOAD_BLOCK_SIZE" ) );

        SOCKET_INPUT_BUFFER_SIZE = Integer.parseInt( props.getProperty( "SOCKET_INPUT_BUFFER_SIZE" ) );

        VERIFY_RESUME_SIZE = Integer.parseInt( props.getProperty( "SOCKET_INPUT_BUFFER_SIZE" ) );

        DOWNLOAD_SEGMENT_SIZE = Integer.parseInt( props.getProperty( "DOWNLOAD_SEGMENT_SIZE" ) );

        CANCEL_CONNECTION_DELAY = Integer.parseInt( props.getProperty( "CANCEL_CONNECTION_DELAY" ) );

        // Client connection related

        CONNECTION_RETRY_INTERVAL = Integer.parseInt( props.getProperty( "CONNECTION_RETRY_INTERVAL" ) );

        CONNECTION_WAITING_TIME = Integer.parseInt( props.getProperty( "CONNECTION_WAITING_TIME" ) );

        MAX_CONNECTION_COUNT = Integer.parseInt( props.getProperty( "MAX_CONNECTION_COUNT" ) );

        // Hub connection related

        HUB_THREAD_INTERRUPTION_TIME = Integer.parseInt( props.getProperty( "HUB_THREAD_INTERRUPTION_TIME" ) );

        HUB_THREAD_INTERRUPTION_INTERVAL = Integer.parseInt( props.getProperty( "HUB_THREAD_INTERRUPTION_INTERVAL" ) );

        HUB_FLUSHDOWNLOADQUEUE_DELAY = Integer.parseInt( props.getProperty( "HUB_FLUSHDOWNLOADQUEUE_DELAY" ) );

        HUB_FLUSHDOWNLOADQUEUE_INTERVAL = Integer.parseInt( props.getProperty( "HUB_FLUSHDOWNLOADQUEUE_INTERVAL" ) );

    }

}

/*******************************************************************************
 * $Log: ConnectionSettings.java,v $ Revision 1.15 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.14
 * 2005/09/30 15:59:52 timowest updated sources and tests Revision 1.13 2005/09/26 17:19:52 timowest updated sources and
 * tests Revision 1.12 2005/09/14 07:11:48 timowest updated sources
 */
