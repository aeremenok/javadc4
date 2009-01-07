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
 * <CODE>ILogedInTask</CODE>
 * <p>
 * If the password is accepted and the user is an Op, the server must send,
 * </p>
 * <p>
 * $LogedIn &lt;username&gt;
 * </p>
 * <p>
 * XXX dc++ uses the OpList to figure out if it has $Kick and $OpForceMove power, nmdc uses $LogedIn / added
 * &lt;username&gt; the $LogedIn may come before or after the $Hello nmdc doesn't seem to care /sed It really is
 * $LogedIn, not $LoggedIn. If the user is not an Op, $LogedIn is skipped.
 * </p>
 * 
 * @author tw70794
 */
public class ILogedInTask
    extends BaseHubTask
{

    private static final Category logger = Category.getInstance( ILogedInTask.class );

    private final ISettings       settings;

    /**
     * Create a ILogedInTask instance
     * 
     * @param settings
     */
    public ILogedInTask(
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

        String nick = settings.getUserInfo().getNick();

        // TODO : define alternative implementation for the loggedIn property of
        // Hub

        if ( getCmdData().equals( nick ) )
        {
            logger.info( "The local client has been accepted to the Hub " + getHub() + " as an Operator." );

            // getHub().setLoggedIn(true);

        }
        else
        {
            throw new HubTaskException( "The client nick " + getCmdData() + " supplied in the $LogedIn " +
                "command didn't equal to the local nick " + nick );

            // getHub().setLoggedIn(false);

        }

    }

}