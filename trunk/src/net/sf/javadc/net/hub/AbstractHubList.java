/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.net.hub;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.javadc.interfaces.IHubInfo;
import net.sf.javadc.interfaces.IHubList;
import net.sf.javadc.listeners.HubListListener;
import net.sf.javadc.util.GenericModel;

import org.apache.log4j.Category;

/**
 * <code>AbstractHubList</code> represents an abstract base implementation for the <code>IHubList</code> interface
 * mainly used to separate the listener notification code from the main implementation
 * 
 * @author Timo Westk�mper
 */
public abstract class AbstractHubList
    extends GenericModel
    implements
        IHubList
{
    private final static Category logger           = Category.getInstance( AbstractHubList.class );

    // Timo : changed on 27.05.2004
    /**
     * 
     */
    protected transient List      hubInfos         = new ArrayList();

    /**
     * 
     */
    protected transient List      filteredHubInfos = new ArrayList();

    /**
     * 
     */
    protected boolean             update           = true;

    /**
     * 
     */
    protected String              filter           = null;

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubList#fireHubListChanged()
     */
    public void fireHubListChanged()
    {
        HubListListener[] listeners = listenerList.getListeners( HubListListener.class );

        logger.debug( "Firing hubListChanged event to registered listeners." );

        for ( int i = 0; i < listeners.length; i++ )
        {
            listeners[i].hubListChanged();

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubList#getHubInfos()
     */
    public List getHubInfos()
    {

        if ( filter == null )
        {
            // return all hubs, if no filter has been specified
            return hubInfos;

        }
        else
        {
            // return only filtered hubs, if filter is active
            return filteredHubInfos;

        }

    }

    /**
     * Return whether the HubList is updated or not
     * 
     * @return
     */
    public final boolean isUpdated()
    {
        return !update;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubList#setFilter(java.lang.String)
     */
    public void setFilter(
        String _filter )
    {

        filteredHubInfos.clear();

        if ( _filter != null )
        {
            filter = _filter.toLowerCase().trim();

            for ( Iterator i = hubInfos.iterator(); i.hasNext(); )
            {
                IHubInfo hubInfo = (IHubInfo) i.next();

                if ( hubInfo.getDescription().toLowerCase().trim().indexOf( filter ) > -1 )
                {

                    filteredHubInfos.add( hubInfo );
                }
            }

            /*
             * taskManager.addEvent(new ITask() { public void runTask() {
             * fireHubListChanged(); }
             * 
             * });
             */

            fireHubListChanged();

        }
        else
        {
            filter = _filter;
            logger.info( "Specified filter was null." );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubList#update()
     */
    public final synchronized void update()
    {
        update = true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    protected final Class getListenerClass()
    {
        return HubListListener.class;

    }

}

/*******************************************************************************
 * $Log: AbstractHubList.java,v $ Revision 1.13 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.12
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.11 2005/09/12 21:12:02 timowest added log block
 */
