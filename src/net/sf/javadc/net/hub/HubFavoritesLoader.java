/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämperer This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.net.hub;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.javadc.interfaces.IHubFavoritesLoader;

import org.apache.log4j.Category;

/**
 * <code>HubFavoritesLoader</code> represents a helper class of the <code>HubFavoritesList</code> used to serialize and
 * deserialize the information about the application users' favorite hubs.
 * 
 * @author Timo Westk�mper
 */
public class HubFavoritesLoader
    implements
        IHubFavoritesLoader
{
    private static final Category logger = Category.getInstance( HubFavoritesLoader.class );

    /**
     *  
     */
    private final String          configFileName;

    // components
    // private final HubFactory hubFactory;

    /**
     * Create a HubFavoritesLoader instance which uses the default location of the configuration file
     */
    public HubFavoritesLoader()
    {
        configFileName = "favorites.xml";

        // hubFactory = _hubFactory;
    }

    /**
     * Create a HubFavoritesLoader instance which uses the given file location to retrieve the configuration file
     * 
     * @param _configFileName configuration file location to be used
     */
    public HubFavoritesLoader(
        String _configFileName )
    {
        configFileName = _configFileName;

        // hubFactory = _hubFactory;
    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesLoader#load()
     */
    public final List load()
    {
        List hubInfos = null;

        try
        {
            logger.info( "Loading favorite Hubs." );

            final XMLDecoder d = new XMLDecoder( new BufferedInputStream( new FileInputStream( configFileName ) ) );

            hubInfos = (ArrayList) d.readObject();
            d.close();

            logger.info( "Favorite Hubs loaded." );

        }
        catch ( Exception e )
        {
            logger.error( "Catched " + e.getClass().getName() + " when trying to load favorite Hubs." );
            logger.error( e );

        }

        if ( hubInfos == null )
        {
            hubInfos = new ArrayList();

        }

        return hubInfos;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesLoader#save(java.util.ArrayList)
     */
    public final void save(
        List hubInfos )
    {
        // serialize hub infos
        try
        {
            logger.info( "Saving favorite Hubs." );

            final XMLEncoder e = new XMLEncoder( new BufferedOutputStream( new FileOutputStream( configFileName ) ) );

            e.writeObject( hubInfos );
            e.close();

            logger.info( "Favorite Hubs saved" );

        }
        catch ( Exception e )
        {
            logger.error( "Catched " + e.getClass().getName() + " when trying to save favorite Hubs." );
            logger.error( e );

        }

    }

}

/*******************************************************************************
 * $Log: HubFavoritesLoader.java,v $ Revision 1.13 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.12
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.11 2005/09/12 21:12:02 timowest added log block
 */
