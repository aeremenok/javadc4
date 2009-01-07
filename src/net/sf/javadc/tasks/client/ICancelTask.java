/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.tasks.client;

import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.tasks.BaseClientTask;

/**
 * A downloader can cancel the download gracefully with some clients (NMDC Windows version at least).
 * <p>
 * It does this by sending $Cancel (without the trailing | character). The uploader then cancels the stream and responds
 * with $Canceled (again, without the trailing |)
 * </p>
 * 
 * @author Timo Westk�mper
 */
public class ICancelTask
    extends BaseClientTask
{

    // private static final Category logger = Category
    // .getInstance(ICancelTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    @Override
    protected final void runTaskTemplate()
    {

        // A downloader can cancel the download gracefully with some clients
        // (NMDC Windows version at least). It does this by sending $Cancel
        // (without the trailing | character). The uploader then cancels the
        // stream and responds with $Canceled (again, without the trailing |)

        try
        {
            if ( clientConnection.getState() == ConnectionState.UPLOADING )
            {
                sendCommand( "$Canceled", "" );

            }
            else
            {
                throw new ClientTaskException( "$Cancel was send out of context. " +
                    "State should have been UPLOADING, but was " + clientConnection.getState() );

            }

        }
        finally
        {
            clientConnection.setState( ConnectionState.ABORTED );

        }
    }

}