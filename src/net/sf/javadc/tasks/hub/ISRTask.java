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
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.tasks.BaseHubTask;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * <p>
 * On the client/server connection.
 * </p>
 * <p>
 * From client to server when matching file is found:
 * </p>
 * <p>
 * $SR fromuser path\to\file.ext\0x5filesize freeslots/openslots\0x5hubname (hubaddress[:port])\0x5touser|
 * </P>
 * <p>
 * Command forwarded to final client:
 * </p>
 * <p>
 * $SR fromuser path\to\file.ext\0x5filesize freeslots/openslots\0x5hubname (hubaddress[:port])|
 * </p>
 * <p>
 * fromuser has found a file that matches touser's search.<br/>'\0x5' is the byte valued 5, it is used as a separator,
 * space ' ' is also used as a separator at times<br/>
 * freeslots and openslots are the attributes of the client (in text)<br/>
 * the hupipaddress are how the client connected to the hub
 * </p>
 * <p>
 * example:<br/>
 * $SR User1 mypath\motd.txt5437 3/45Testhub (10.10.10.10:411)5User2| (the bold fives are ascii #5)
 * </p>
 * 
 * @author tw70794
 */
public class ISRTask
    extends BaseHubTask
{

    private static final Category logger = Logger.getLogger( ISRTask.class );

    private final ISettings       settings;

    /**
     * Create a new ISRTask instance
     * 
     * @param _settings
     */
    public ISRTask(
        ISettings _settings )
    {

        if ( _settings == null )
        {
            throw new NullPointerException( "_settings was null." );
        }

        settings = _settings;

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

        logger.debug( "Received search result \n  " + cmdData );

        try
        {
            SearchResult sr = new SearchResult( hub, cmdData, settings );

            sr.setPing( (int) (System.currentTimeMillis() - hub.getStartPing()) );

            hub.addSearchResult( sr );

        }
        catch ( Exception e )
        {
            String error = "Invalid search result received: " + cmdData;
            logger.error( error, e );
            throw new HubTaskException( error, e );

        }

    }

}