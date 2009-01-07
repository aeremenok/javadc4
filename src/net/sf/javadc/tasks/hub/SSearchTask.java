/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.tasks.hub;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.tasks.BaseHubTask;

import org.apache.log4j.Category;

/**
 * @author tw70794 To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SSearchTask
    extends BaseHubTask
{

    private static final Category logger = Category.getInstance( SSearchTask.class );

    private final ISettings       settings;

    /**
     * Create a new SSearchTask instance
     * 
     * @param settings
     */
    public SSearchTask(
        ISettings settings )
    {

        if ( settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }

        this.settings = settings;

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
        hub.clearSearchResults();

        logger.debug( "Searching with searchString \n  " + cmdData );

        // active mode
        if ( settings.isActive() )
        {
            int dataPort = settings.getUserInfo().getPort();

            sendCommand( "$Search", settings.getIP() + ":" + dataPort + " " + cmdData );

            hub.setStartPing( System.currentTimeMillis() );

            // passive mode
        }
        else
        {
            String nick = settings.getUserInfo().getNick();

            sendCommand( "$Search", "Hub:" + nick + " " + cmdData );

        }

    }

}