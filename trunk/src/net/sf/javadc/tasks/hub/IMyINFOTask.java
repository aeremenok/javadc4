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
 * <CODE>IMyInfoTask</CODE> contains details about users (share size / connection type / description / email)
 * <p>
 * This command is part of the Client-Hub Handshake. On login is send after client receive $Hello with own nick. Client
 * resend this on any change. It's broadcasted to all clients.
 * </p>
 * <p>
 * Syntax:
 * </p>
 * $MyINFO $ALL &lt;nick&gt; &lt;description&gt;$ $&lt;connection&gt;&lt;flag&gt;$&lt;e-mail&gt;$&lt;sharesize&gt;$|
 * <ul>
 * <li>&lt;nick&gt; Nickname // without spaces</li>
 * <li>&lt;description&gt; User description</li>
 * <li>&lt;connection&gt; User connection to the internet.</li>
 * </ul>
 * <p>
 * // Default NMDC1 connections types 28.8Kbps, 33.6Kbps, 56Kbps, Satellite, ISDN, DSL, Cable, LAN(T1), LAN(T3)
 * </p>
 * <p>
 * // Default NMDC2 connections types Modem, DSL, Cable, Satellite, LAN(T1), LAN(T3)
 * </p>
 * <p>
 * &lt;flag&gt; User status as ascii char
 * </p>
 * <p>
 * // 1 = normal, 2 and 3 = away, 4 and 5 = server, 6 and 7 = server away, 8 and 9 = fireball, 10 and 11 = fireball away
 * </p>
 * <p>
 * // SERVER = client have uptime > 2 hours, > 2 GB shared, upload > 200 MB
 * </p>
 * <p>
 * // fireball = upload > 100 kB/s
 * </p>
 * <ul>
 * <li>&lt;e-mail> User email adress</li>
 * <li>&lt;sharesize> Share size in bytes</li>
 * </ul>
 * <p>
 * // All characters except $ and | are allowed
 * </p>
 * <p>
 * // Extended MyINFO, usefull on hubs striping client tags (passive user complains wihtout tags -> without known other
 * users mode).
 * </p>
 * <p>
 * $MyINFO $ALL &lt;nick&gt;
 * &lt;description&gt;$&lt;modechar&gt;$&lt;connection&gt;&lt;flag&gt;$&lt;e-mail&gt;$&lt;sharesize&gt;$|
 * </p>
 * 
 * @author tw70794
 */
public class IMyINFOTask
    extends BaseHubTask
{

    private static final Category logger = Category.getInstance( IMyINFOTask.class );

    /**
     * @param user
     * @param cmdData
     */
    private final void setHubUserInfo(
        HubUser user,
        String cmdData )
    {
        // Strip $ALL_
        final String userinfo = cmdData.substring( 5 );
        final String[] elements = userinfo.split( "\\$" );

        // 1 (nick / description)
        final int index = elements[0].indexOf( " " );

        if ( index == -1 )
        {
            logger.error( "nick description separation could not be found for " + cmdData );
            return;
        }

        user.setNick( elements[0].substring( 0, index ) );
        user.setDescription( elements[0].substring( index + 1 ) );

        if ( elements.length > 2 )
        {
            setUserInformation( user, elements );

        }
        else
        {
            logger.error( "Not enough arguments  " + elements.length + " in " + cmdData );

        }

    }

    /**
     * @param user
     * @param elements
     */
    private final void setUserInformation(
        HubUser user,
        String[] elements )
    {
        // 3 (raw speed)
        final String rawSpeed = elements[2];

        if ( rawSpeed.length() > 1 )
        {
            // user.setSpeed (rawSpeed);
            user.setSpeed( rawSpeed.substring( 0, rawSpeed.length() - 1 ) );

            char c = rawSpeed.charAt( rawSpeed.length() - 1 );
            byte code = (byte) (c & 0xff);

            user.setSpeedCode( code );

        }
        else
        {
            logger.warn( "Speed could not be determined." );
        }

        // 4 (if count == 5) (email)
        if ( elements.length > 4 )
        {
            user.setEmail( elements[3] );
        }

        // 5 (or 4) (shared size)
        if ( elements.length > 3 )
        {
            String shared = elements[elements.length - 1].trim();

            if ( shared.equals( "" ) )
            {
                user.setSharedSize( 0 );

            }
            else if ( !shared.equals( "Security@Bot" ) )
            {

                try
                {
                    user.setSharedSize( Long.parseLong( shared ) );
                }
                catch ( NumberFormatException e )
                {
                    logger.error( "Given shared size is invalid." );
                    user.setSharedSize( 0 );
                }

            }

        }
        else
        {
            logger.error( "Not enough arguments  " + elements.length + " in " + cmdData );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    @Override
    protected void runTaskTemplate()
    {
        final HubUser user = new HubUser( hub );

        // ensures that also non-valid users are added
        try
        {
            setHubUserInfo( user, cmdData );

        }
        catch ( StringIndexOutOfBoundsException s )
        {
            logger.error( "Caught " + s.getClass().getName() + " for " + cmdData );

            // setHubUserInfo(cmdData);
        }
        catch ( Exception e )
        {
            throw new HubTaskException( "User information " + cmdData + " was invalid.", e );

            // user is nevertheless added
        }
        finally
        {
            hub.addUser( user );

        }
    }

}