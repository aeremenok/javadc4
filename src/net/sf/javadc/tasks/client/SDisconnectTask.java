/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.tasks.client;

import java.io.IOException;

import javax.swing.event.EventListenerList;

import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.tasks.BaseClientTask;
import net.sf.javadc.util.PerformanceContext;

import org.apache.log4j.Category;

/**
 * @author tw70794 To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SDisconnectTask
    extends BaseClientTask
{

    private static final Category logger = Category.getInstance( SDisconnectTask.class );

    // components
    // private final ISettings settings;
    private final ITaskManager    taskManager;

    // helpers
    // private final ConnectionSlotReservation slotReservation;

    /**
     * <code>SDisconnectTask</code> instance to be used
     * 
     * @param _taskManager ITaskManager instance to be used
     * @param _settings ISettings instance to be used
     */
    public SDisconnectTask(
        ITaskManager _taskManager )
    {
        if ( _taskManager == null )
        {
            throw new NullPointerException( "_taskManager was null." );
        }

        taskManager = _taskManager;

    }

    /**
     * Remove the ConnectionListener instance from the Connection
     */
    private final void removeListeners()
    {
        EventListenerList listenerList = clientConnection.getListeners();

        ConnectionListener[] l = listenerList.getListeners( ConnectionListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].disconnected( clientConnection );

        }

    }

    /** ********************************************************************** */
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    @Override
    protected final void runTaskTemplate()
    {
        PerformanceContext cont = new PerformanceContext( "SDisconnectTask#runTaskTemplate" ).start();

        // ConnectionState state = clientConnection.getState();

        String nick = clientConnection.getClient().getNick();
        logger.info( "Closing connection to " + nick );

        taskManager.removeTask( clientConnection );

        // close output stream

        if ( clientConnection.getWriter() != null )
        {
            logger.info( "Closing OutputStream of " + clientConnection );

            try
            {
                clientConnection.getWriter().close();

            }
            catch ( IOException e )
            {
                // logger.error(e);
                logger.error( "Caught " + e.getClass().getName(), e );

            }

            logger.info( "OutputStream of " + clientConnection + " closed." );

            clientConnection.setWriter( null );

        }

        // close input stream

        if ( clientConnection.getReader() != null )
        {
            logger.info( "Closing InputStream of " + clientConnection );

            try
            {
                clientConnection.getReader().close();

            }
            catch ( IOException e )
            {
                // logger.error(e);
                logger.error( "Caught " + e.getClass().getName(), e );

            }

            logger.info( "InputStream of " + clientConnection + " closed." );

            clientConnection.setReader( null );

        }

        // close socket

        if ( clientConnection.getSocket() != null )
        {
            logger.info( "Closing Socket of " + clientConnection );

            try
            {
                clientConnection.getSocket().close();

            }
            catch ( Exception e )
            {
                // logger.error(e);
                logger.error( "Caught " + e.getClass().getName(), e );

            }

            clientConnection.setSocket( null );

            logger.info( "Socket of " + clientConnection + " closed." );
        }

        // close server socket

        // if (clientConnection.getServerSocket() != null) {
        // logger.info("Closing ServerSocket of " + clientConnection);
        //
        // try {
        // clientConnection.getServerSocket().close();
        //
        // } catch (Exception e) {
        // logger.error(e);
        //
        // }
        //
        // clientConnection.setServerSocket(null);
        //
        // logger.info("ServerSocket of " + clientConnection + " closed.");
        // }

        clientConnection.closeFile();
        clientConnection.setState( ConnectionState.NOT_CONNECTED );

        clientConnection.fireDisconnected();

        removeListeners();

        if ( logger.isInfoEnabled() )
        {
            logger.info( cont.end() );
        }
    }

}