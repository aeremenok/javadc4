/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.tasks.hub;

import java.util.Collection;
import java.util.Map;

import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.net.hub.HubUser;
import net.sf.javadc.tasks.BaseHubTask;

import org.apache.log4j.Category;

/**
 * @author tw70794 To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SDisconnectTask
    extends BaseHubTask
{

    private static final Category logger = Category.getInstance( SDisconnectTask.class );

    private final ITaskManager    taskManager;

    // private final IHubManager hubManager;

    /**
     * Create an new SDisconnectTask instance
     * 
     * @param _taskManager
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
     * closes the Hub connection and removes the Hub from the TaskManager
     */
    private final void closeConnection()
    {

        // close inputstream

        if ( hub.getReader() != null )
        {
            logger.info( "Closing InputStream of " + hub );

            try
            {
                hub.getReader().close();

            }
            catch ( Exception e )
            {
                logger.error( e );

            }

            hub.setReader( null );

            logger.info( "InputStream of " + hub + " closed." );

        }

        // close outputstream

        if ( hub.getWriter() != null )
        {
            logger.info( "Closing OutputStream of " + hub );

            try
            {
                hub.getWriter().close();

            }
            catch ( Exception e )
            {
                logger.error( e );

            }

            hub.setWriter( null );

            logger.info( "OutputStream of " + hub + " closed." );

        }

        // close socket (connection)

        if ( hub.getConnection() != null )
        {
            logger.info( "Closing socket of " + hub );

            try
            {
                hub.getConnection().close();

            }
            catch ( Exception e )
            {
                logger.error( e );

            }

            hub.setConnection( null );

            logger.info( "Socket of " + hub + " closed." );
        }

    }

    /**
     * remove Hub users from the list and fire events to registered listeners
     */
    private final void removeUsers()
    {
        final Map users = hub.getUsers();

        // HubUser[] us = (HubUser[]) (users.values()).toArray();
        Collection usersCol = users.values();

        try
        {
            HubUser[] us = (HubUser[]) usersCol.toArray( new HubUser[usersCol.size()] );

            for ( int i = 0; i < us.length; i++ )
            {
                hub.fireUserRemoved( us[i] );

            }

        }
        catch ( ClassCastException e )
        {
            logger.error( e.toString() );

        }

        users.clear();

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    @Override
    protected void runTaskTemplate()
    {
        taskManager.removeTask( hub );

        this.closeConnection();
        this.removeUsers();

        // hubManager.removeHub(hub);
        hub.fireHubDisconnected();

    }

}