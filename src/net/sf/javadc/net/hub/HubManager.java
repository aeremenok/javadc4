/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: HubManager.java,v 1.23 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.hub;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.listeners.HubListener;
import net.sf.javadc.listeners.HubListenerBase;
import net.sf.javadc.listeners.HubManagerListener;
import net.sf.javadc.util.GenericModel;

import org.apache.log4j.Category;
import org.picocontainer.Startable;

/**
 * <code>HubManager</code> manages connected <code>Hub</code> instances.
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.23 $ $Date: 2005/10/02 11:42:28 $
 */
public class HubManager
    extends GenericModel
    implements
        IHubManager,
        Startable
{
    /** ********************************************************************** */

    private class MyHubListener
        extends HubListenerBase
    {

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.HubListener#hubDisconnected(net.sf.javadc.interfaces.IHub)
         */
        @Override
        public void hubDisconnected(
            IHub hub )
        {
            removeHub( hub );

        }

    }

    private final static Category logger      = Category.getInstance( HubManager.class );

    /**
     * 
     */
    private final List            hubs        = new ArrayList();

    /**
     * 
     */
    private final HubListener     hubListener = new MyHubListener();

    /**
     * Create a new <CODE>HubManager</CODE> without any dependencies
     */
    public HubManager()
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubManager#addHub(net.sf.javadc.interfaces.IHub)
     */
    public void addHub(
        IHub hub )
    {
        // Hub has not yet been added to HubManager
        if ( getHub( hub ) == null )
        {
            logger.debug( "Adding Hub " + hub + " to list of connected Hubs." );

            synchronized ( this )
            {
                hubs.add( hub );
            }

            hub.addListener( hubListener );
            fireHubAdded( hub );

        }
        else
        {
            logger.debug( "Hub " + hub + " has already been added to the HubManager." );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubManager#fireHubAdded(net.sf.javadc.interfaces.IHub)
     */
    public void fireHubAdded(
        IHub hub )
    {
        HubManagerListener[] l = listenerList.getListeners( HubManagerListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].hubAdded( hub );

        }

    }

    // synchronized addition of new Hubs

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubManager#fireHubRemoved(net.sf.javadc.interfaces.IHub)
     */
    public void fireHubRemoved(
        IHub hub )
    {
        final HubManagerListener[] l = listenerList.getListeners( HubManagerListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].hubRemoved( hub );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubManager#getHub(net.sf.javadc.interfaces.IHub)
     */
    public final IHub getHub(
        IHub hub )
    {
        Hub[] h = (Hub[]) hubs.toArray( new Hub[hubs.size()] );

        for ( int i = 0; i < h.length; i++ )
        {
            if ( h[i].equals( hub ) )
            {
                logger.debug( "Duplicate item " + h[i] + " was found for " + hub );

                return h[i];

            }

        }

        logger.debug( "No duplicate item could be found for " + hub );

        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubManager#getHub(int)
     */
    public final IHub getHub(
        int index )
    {
        if ( index < hubs.size() )
        {
            return (IHub) hubs.get( index );
        }
        else
        {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubManager#getHubCount()
     */
    public final int getHubCount()
    {
        return hubs.size();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubManager#getHubWithIP(java.lang.String)
     */
    public final IHub getHubWithIP(
        String ip )
    {
        Hub[] h = (Hub[]) hubs.toArray( new Hub[hubs.size()] );

        for ( int i = 0; i < h.length; i++ )
        {
            try
            {
                if ( h[i].getHost().getIpString().equals( ip ) )
                {
                    return h[i];

                }

            }
            catch ( UnknownHostException e )
            {
                String error = "The host for hub " + h[i] + " could not be found.";
                logger.error( error, e );

            }

        }

        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubManager#removeHub(net.sf.javadc.interfaces.IHub)
     */
    public final void removeHub(
        IHub hub )
    {
        if ( hubs.remove( hub ) )
        {
            fireHubRemoved( hub );
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public void start()
    {

        logger.debug( "starting " + this.getClass().getName() );

        // System.out.println();
        // System.out.println("starting " + this.getClass().getName());
        // System.out.println("====================================================");

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public void stop()
    {

        logger.debug( "stopping " + this.getClass().getName() );

        // System.out.println();
        // System.out.println("stopping " + this.getClass().getName());
        // System.out.println("====================================================");

        logger.info( "Disconnecting from Hubs" );

        for ( Iterator i = hubs.iterator(); i.hasNext(); )
        {
            IHub hub = (IHub) i.next();

            logger.info( "Disconnecting from Hub " + hub );
            hub.disconnect();
        }

        logger.info( "Disconnected from Hubs" );
    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    protected final Class getListenerClass()
    {
        return HubManagerListener.class;

    }

}

/*******************************************************************************
 * $Log: HubManager.java,v $ Revision 1.23 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.22
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.21 2005/09/25 16:40:58 timowest updated sources and
 * tests Revision 1.20 2005/09/12 21:12:02 timowest added log block
 */
