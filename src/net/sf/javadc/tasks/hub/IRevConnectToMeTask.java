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

import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.tasks.BaseHubTask;

import org.apache.log4j.Category;

/**
 * <p>
 * A passive client may send this to cause a peer to send a $ConnectToMe back.
 * </p>
 * <p>
 * $RevConnectToMe &lt;nick&gt; &lt;remoteNick&gt;
 * </p>
 * <p>
 * &lt;nick&gt; is the sender of the message.
 * </p>
 * <p>
 * 6lt;remoteNick&gt; is the user which should send to $ConnectToMe.
 * </p>
 * <p>
 * The server must send this message unmodified to &lt;remoteNick&gt;. If &lt;remoteNick&gt; is an active client, it
 * must send a $ConnectToMe to &lt;nick&gt;. If not, it must ignore the message. XXX DC++ does not ignore the message,
 * but replies with a $RevConnectToMe. now they know they are both passive, and no further connection attempts have to
 * be made.
 * </p>
 * 
 * @author tw70794
 */
public class IRevConnectToMeTask
    extends BaseHubTask
{

    private static final Category logger = Category.getInstance( IRevConnectToMeTask.class );

    private final ISettings       settings;

    private final IClientManager  clientManager;

    /**
     * Create a new IRevConnectToMeTask instance
     * 
     * @param settings
     * @param clientManager
     */
    public IRevConnectToMeTask(
        ISettings settings,
        IClientManager clientManager )
    {

        if ( settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }
        else if ( clientManager == null )
        {
            throw new NullPointerException( "clientManager was null." );
        }

        this.settings = settings;
        this.clientManager = clientManager;

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
        logger.debug( "Got $RevConnectToMe: " + cmdData );

        if ( settings.isActive() )
        {
            // Timo : 22.05.2004
            // externalize ?
            BaseHubTask task = new SRequestConnectionTask( settings, clientManager );

            task.setCmdData( cmdData );
            task.setHub( hub );

        }
        else
        {
            logger.info( "Couldn't connect to user " + cmdData + " because passive mode is used." );

        }

    }

}