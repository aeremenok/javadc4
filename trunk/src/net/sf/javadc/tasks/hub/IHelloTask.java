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

package net.sf.javadc.tasks.hub;

import net.sf.javadc.config.AdvancedSettings;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IUserInfo;
import net.sf.javadc.tasks.BaseHubTask;

import org.apache.log4j.Category;

/**
 * <CODE>IHelloTask</CODE> is used by client to announce that they are logged
 * on
 * 
 * <p>
 * Hello is part of the Client-Hub Handshake and is sent to the client to
 * indicate which nickname the hub will identify the client with.
 * </p>
 * 
 * <p>
 * $Hello &lt;nick&gt;|
 * </p>
 * 
 * <p>
 * &lt;nick&gt; is the clients nickname
 * </p>
 * 
 * <p>
 * The Hello command is also used to announce newly connected clients.
 * </p>
 * 
 * <p>
 * Comment:<br/> The &lt;nick&gt; NMDCHub sends in $Hello can be different than
 * the nick submitted in the ValidateNick command. DC++ and NMDC do not support
 * this though.
 * </p>
 * 
 * <p>
 * If they did, it would be a nice way to force e.g. [ISP]nickname for a user.
 * Like: Client sends to Hub, $ValidateNick fusbar| Hub replies, $Hello
 * [BBB]fusbar|
 * </p>
 * 
 * <p>
 * thereby forcing ISP into my nickname
 * </P>
 * 
 * @author tw70794
 */
public class IHelloTask extends BaseHubTask {

    private static final Category logger = Category
            .getInstance(IHelloTask.class);

    // components
    // doesn't use pure user information from settings, because the modified
    // toString() method is needed
    private final IUserInfo userInfo;

    private final AdvancedSettings advancedSettings;

    /**
     * Create a new IHelloTask instance
     * 
     * @param settings
     * @param _userInfo
     */
    public IHelloTask(ISettings settings, IUserInfo _userInfo) {
        // user = settings.getUserInfo();

        if (settings == null)
            throw new NullPointerException("settings was null.");
        else if (settings.getAdvancedSettings() == null)
            throw new NullPointerException(
                    "settings.advancedSettings was null.");
        else if (_userInfo == null)
            throw new NullPointerException("_userInfo was null.");

        advancedSettings = settings.getAdvancedSettings();
        userInfo = _userInfo;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {
        // user info equals own nick
        if (cmdData.equals(userInfo.getNick())) {
            // send client version information and require nick list of hub
            sendCommand("$Version", advancedSettings.getClientVersion());
            sendCommand("$GetNickList", "");

            // send own information to hub
            sendCommand("$MyINFO", userInfo.toString());

            // external user enters hub

            hub.setLoggedIn(true);

        } else {
            logger.debug("HubUser arrived: '" + cmdData + "'");

        }

    }

}