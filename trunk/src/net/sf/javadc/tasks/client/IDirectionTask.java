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

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.net.client.ConnectionInfo;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.tasks.BaseClientTask;

/**
 * This command is sent as a response to the Key command, it decides which party gets to download.
 * <p>
 * The $Direction command is a part of the response packet to the $Lock + $MyNick packet.
 * </p>
 * <p>
 * $Direction &lt;direction&gt; &lt;anumber&gt;
 * </p>
 * <p>
 * &lt;direction&gt; is either Upload or Download depending on if your client is uploading or downloading.
 * </p>
 * <p>
 * &lt;anumber&gt; is a random number, when both clients want to start downloading then one with higher number starts
 * first.
 * </p>
 * <p>
 * After the $Direction command we must send the $Key command (still with in the $Direction packet) in response to the
 * other clients lock.
 * </p>
 * <p>
 * $Key &lt;key&gt;
 * </p>
 * <p>
 * &lt;key&gt; is generated from the other users &lt;lock&gt; See Appendix A: Converting a Lock into a Key for how to
 * compute the key from the lock.
 * </p>
 * <p>
 * The users are now connected.
 * </p>
 * 
 * @author tw70794
 */
public class IDirectionTask
    extends BaseClientTask
{

    // private static final Category logger = Category
    // .getInstance(IDirectionTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    @Override
    protected final void runTaskTemplate()
    {
        String direction = cmdData.substring( 0, cmdData.indexOf( " " ) );
        ConnectionInfo connInfo = clientConnection.getConnectionInfo();

        // get the direction.
        if ( ConstantSettings.DOWNLOAD_DIRECTION.equals( direction ) )
        {
            // if the other side wants download, we have a upload
            connInfo.setCurrentDirection( ConstantSettings.UPLOAD_DIRECTION );

        }
        else if ( ConstantSettings.UPLOAD_DIRECTION.equals( direction ) )
        {
            // if the other side wants upload, we have a download
            connInfo.setCurrentDirection( ConstantSettings.DOWNLOAD_DIRECTION );

        }
        else
        {
            clientConnection.setState( ConnectionState.INVALID_DIRECTION );

            throw new ClientTaskException( "Given direction name " + cmdData + " was invalid." );

        }

    }

}