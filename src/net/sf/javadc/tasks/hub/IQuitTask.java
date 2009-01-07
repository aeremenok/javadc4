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

import net.sf.javadc.net.hub.HubUser;
import net.sf.javadc.tasks.BaseHubTask;

import org.apache.log4j.Category;

/**
 * <p>
 * Sent from the hub, this command signals to connected users, that a client is disconnecting from the hub.
 * </p>
 * <p>
 * $Quit &lt;nick&gt;
 * </p>
 * <p>
 * For example: $Quit BlackClaw
 * </p>
 * <p>
 * Comments: Contrary to common belief, this command should not be sent to the hub from the client when client
 * disconnects. glosearch does this, which is a bug that has been fixed, although no new version has been released since
 * the bugfix.
 * </p>
 * 
 * @author tw70794
 */
public class IQuitTask
    extends BaseHubTask
{

    private static final Category logger = Category.getInstance( IQuitTask.class );

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    @Override
    protected void runTaskTemplate()
    {
        // cmdData only contains the nick for a Quit command
        HubUser ui = hub.getUser( cmdData );

        if ( ui != null )
        {
            hub.removeUser( ui );

        }
        else
        {
            logger.info( "Couldn't remove user " + "'" + cmdData + "'" );

        }

    }

}