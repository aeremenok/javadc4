/* *
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
 *
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software;      you can redistribute it and/or modify it
 * under the terms of the   GNU General Public License as published by the Free
 * Software Foundation;    either version 2 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;   without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE.   See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.tasks.client;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IUserInfo;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.tasks.BaseClientTask;

/**
 * @author tw70794 To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SMyNickTask extends BaseClientTask {

    // private static final Category logger = Category
    // .getInstance(SMyNickTask.class);

    private final IUserInfo user;

    /**
     * Create a <code>SMyNickTask</code> instance
     * 
     * @param settings
     *            ISettings instance to be used
     */
    public SMyNickTask(ISettings settings) {

        if (settings == null)
            throw new NullPointerException("settings was null.");
        else if (settings.getUserInfo() == null)
            throw new NullPointerException("settings.userInfo was null.");

        user = settings.getUserInfo();

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {

        // if (!clientConnection.isServer()) {
        sendCommand("$MyNick", user.getNick());

        // Lock should be a randomized string, but i don't se the point..
        // It only verifies that we know the algorithm.

        // EXTENDEDPROTOCOLABCABCABCABCABCABC Pk=DCPLUSPLUS0.242ABCABC

        sendCommand("$Lock",
                "EXTENDEDPROTOCOLABCABCABCABCABCABC Pk=DCPLUSPLUS0.242ABCABC");
        // }else{
        // logger.debug("ClientConnection is server");
        // }

        clientConnection.setState(ConnectionState.LOGIN);

    }

}