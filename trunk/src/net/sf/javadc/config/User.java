/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: User.java,v 1.12 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;

/**
 * <CODE>User</CODE> represents the application user
 * 
 * @author Timo Westk�mper
 */
public class User
    extends UserInfoAdapter
{
    // components
    /**
     * 
     */
    private final IShareManager shareManager;

    /**
     * 
     */
    private final IHubManager   hubManager;

    /**
     * 
     */
    private ISettings           settings;

    /**
     * Create a User instance with the given ISettings, IShareManager and IHubManager
     * 
     * @param settings ISettings instance to be used
     * @param shareManager IShareManager instance to be used
     * @param hubManager IHubManager instance to be used
     */
    public User(
        ISettings settings,
        IShareManager shareManager,
        IHubManager hubManager )
    {
        super( settings.getUserInfo() );

        if ( settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }

        if ( shareManager == null )
        {
            throw new NullPointerException( "shareManager was null." );
        }

        if ( hubManager == null )
        {
            throw new NullPointerException( "hubManager was null." );
        }

        this.settings = settings;
        this.shareManager = shareManager;
        this.hubManager = hubManager;

    }

    /** ********************************************************************** */
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString()
    {
        final long sharedSize = shareManager.getSharedSize();

        return "$ALL " + getNick() + " " + createTag() + "$ $" + getSpeed() + getSpeedCode() + "$" + getEmail() + "$" +
            sharedSize + "$";

    }

    /**
     * Return the DC++ related tag which encodes information about usage mode, client version and download/upload slot
     * usage
     * 
     * @return
     */
    private final String createTag()
    {
        String mode;
        String version;
        String unreghubs;
        String reghubs;
        String ophubs;
        String slots;

        // client version
        version = "0.401";

        // if (settings.isActive()) {
        if ( settings.isActive() )
        {
            // active
            mode = "A";

        }
        else
        {
            // passive
            mode = "P";

        }

        // TODO : differentiate between different kinds of hubs

        // number of hubs connected to where you're not a registered user
        unreghubs = String.valueOf( hubManager.getHubCount() );

        // number of hubs you're registered in
        reghubs = "0";

        // number of hubs you're registered as op
        ophubs = "0";

        final String hubs = unreghubs + "/" + reghubs + "/" + ophubs;

        slots = String.valueOf( settings.getFreeDownloadSlotCount() );

        return "<++ V:" + version + ",M:" + mode + ",H:" + hubs + ",S:" + slots + ">";

    }

}

/*******************************************************************************
 * $Log: User.java,v $ Revision 1.12 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.11 2005/09/30
 * 15:59:52 timowest updated sources and tests Revision 1.10 2005/09/14 07:11:48 timowest updated sources
 */
