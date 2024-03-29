/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: HubFavoritesList.java,v 1.16 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.hub;

import java.util.List;

import junit.framework.Assert;
import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.IHubFavoritesLoader;
import net.sf.javadc.interfaces.IHubInfo;
import net.sf.javadc.listeners.HubListListener;
import net.sf.javadc.util.GenericModel;

import org.picocontainer.Startable;

/**
 * <code>HubFavoritesList</code> reprsents the list of favorite Hubs which can be assembled and extended via the user
 * interface. The serialization and deserialization is accomplised via a <code>HubFavoritesLoader</code> instance.
 * 
 * @author Timo Westk�mper
 */
public class HubFavoritesList
    extends GenericModel
    implements
        Startable,
        IHubFavoritesList
{
    // private static final Category logger = Category
    // .getInstance(HubFavoritesList.class);
    private List<IHubInfo>            hubInfos;
    // components
    private final IHubFavoritesLoader loader;

    /**
     * Creates a <CODE>HubFavoritesList</CODE> with the given <CODE>
     * HubFavoritesLoader</CODE>
     */
    public HubFavoritesList(
        IHubFavoritesLoader _loader )
    {
        Assert.assertNotNull( _loader );
        loader = _loader;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#addHubInfo(net.sf.javadc.interfaces.IHubInfo)
     */
    public final void addHubInfo(
        IHubInfo hubInfo )
    {
        Assert.assertNotNull( hubInfo );

        if ( !hubInfos.contains( hubInfo ) )
        { // if hub has not yet been added
            hubInfos.add( hubInfo );
            fireHubListChanged();
        }
        else
        {
            // ?
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#getHubInfos()
     */
    public List<IHubInfo> getHubInfos()
    {
        return hubInfos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    public Class<HubListListener> getListenerClass()
    {
        return HubListListener.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#removeHub(net.sf.javadc.interfaces.IHubInfo)
     */
    public final void removeHub(
        IHubInfo hubInfo )
    {
        Assert.assertNotNull( hubInfo );
        hubInfos.remove( hubInfos.indexOf( hubInfo ) );
        fireHubListChanged();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#setHubInfos(java.util.ArrayList)
     */
    public void setHubInfos(
        List<IHubInfo> list )
    {
        hubInfos = list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#start()
     */
    public void start()
    {
        // hub infos are loaded when the container is started
        hubInfos = loader.load();
        Assert.assertNotNull( hubInfos );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#stop()
     */
    public void stop()
    {
        // hub infos are saved when the container is closed
        loader.save( hubInfos );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#update()
     */
    public final void update()
    {
        fireHubListChanged();
    }

    /**
     * Notify registered listeners that the HubFavoritesList has changed
     */
    private final void fireHubListChanged()
    {
        final HubListListener[] listeners = listenerList.getListeners( HubListListener.class );
        for ( HubListListener listener : listeners )
        {
            listener.hubListChanged();
        }
    }
}
