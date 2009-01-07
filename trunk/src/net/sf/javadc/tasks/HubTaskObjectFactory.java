/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.tasks;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.IHubTask;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.log4j.Category;
import org.picocontainer.ComponentAdapter;
import org.picocontainer.MutablePicoContainer;

/**
 * <code>HubTaskObjectFactory</code> is a simple Jakarta Commons Pool and PicoContainer based Factory method
 * implementation used via the <code>HubTaskFactory</code>.
 * 
 * @author Timo Westk�mper
 */
public class HubTaskObjectFactory
    extends BaseKeyedPoolableObjectFactory
{
    private final static Category      logger = Category.getInstance( HubTaskObjectFactory.class );

    private final MutablePicoContainer hubTasksContainer;

    private Map                        componentAdapters;

    /**
     * Create a HubTaskObjectFactory instance which creates HubTask instances via the given PicoContainer tasksContainer
     * 
     * @param tasksContainer PicoContainer used to create preconfigured instances of hub tasks
     */
    public HubTaskObjectFactory(
        MutablePicoContainer tasksContainer )
    {
        hubTasksContainer = tasksContainer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.pool.BaseKeyedPoolableObjectFactory#makeObject(java.lang.Object)
     */
    @Override
    public Object makeObject(
        Object arg0 )
        throws Exception
    {

        if ( arg0 == null || !(arg0 instanceof String) )
        {
            throw new NullPointerException( "Invalid arguments" );

        }

        String taskName = (String) arg0;

        if ( taskName.equals( "" ) )
        {
            return null;

        }

        IHubTask task = null;

        try
        {
            Class aclass =
                Class.forName( ConstantSettings.HUBTASKFACTORY_PREFIX + taskName +
                    ConstantSettings.HUBTASKFACTORY_POSTFIX );

            // retrieve component adapter related to the specific component type
            ComponentAdapter adapter = (ComponentAdapter) componentAdapters.get( aclass );

            if ( adapter != null )
            {
                task = (IHubTask) adapter.getComponentInstance();

            }
            else
            {
                logger.error( "No Task for " + taskName );

            }

        }
        catch ( ClassNotFoundException e )
        {
            logger.error( "Task not found : " + taskName );

        }
        catch ( NoClassDefFoundError ex )
        {
            logger.error( "Task not found : " + taskName );

        }

        return task;

    }

    /**
     * Initializes the ClientTaskObjectFactory by creating the component adapters from the clientTasksContains
     */
    protected void initialize()
    {
        Collection _adapters = hubTasksContainer.getComponentAdapters();

        Object[] compArray = _adapters.toArray( new Object[_adapters.size()] );

        componentAdapters = new HashMap();

        for ( int i = 0; i < compArray.length; i++ )
        {
            componentAdapters.put( ((ComponentAdapter) compArray[i]).getComponentKey(), compArray[i] );

        }
    }

}