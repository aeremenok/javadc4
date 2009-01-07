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

import java.net.UnknownHostException;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.tasks.BaseHubTask;

import org.apache.log4j.Category;

/**
 * <CODE>IForceMoveTask</CODE> is called when the hub asks you to move to
 * another hub
 * 
 * <p>
 * $ForceMove
 * </p>
 * 
 * <p>
 * The Hub sends $ForceMove to a client to cause the client to move to a
 * different server.
 * </p>
 * 
 * <p>
 * $ForceMove &lt;newAddr&gt;
 * </p>
 * 
 * <p>
 * Accompanied by a PM for Op initiated redirects: $To: &lt;victimNick&gt; From:
 * &lt;senderNick&gt; $&lt;&lt;senderNick&gt;&gt; You are being redirected to
 * &lt;newAddr&gt;: &lt;reasonMsg&gt;
 * </p>
 * 
 * <p>
 * Accompanied by a Chat message for Hub initiated redirects:
 * &lt;&lt;hubbotName&gt;&gt; &lt;reasonMsg&gt;
 * </p>
 * 
 * <p>
 * &lt;senderNick&gt; is the nick that sent the $OpForceMove. <br/>
 * &lt;hubbotName&gt; is a name specifc to the Hub software. <br/>
 * &lt;victimNick&gt; is the nick that will be asked to move. <br/>
 * &lt;newAddr&gt; is the IP address or hostname (and optional colon-separated
 * port) of another hub that the user should move to. <br/>&lt;reasonMsg&gt; is
 * the reason provided to &lt;victimNick>.
 * </p>
 * 
 * @author tw70794
 */
public class IForceMoveTask extends BaseHubTask {

    private static final Category logger = Category
            .getInstance(IForceMoveTask.class);

    // components
    private final IHubManager hubManager;

    private final ISettings settings;

    private final IHubFactory hubFactory;

    /**
     * Create a new IForceMoveTask instance
     * 
     * @param _hubManager
     * @param _settings
     * @param _hubFactory
     */
    public IForceMoveTask(IHubManager _hubManager, ISettings _settings,
            IHubFactory _hubFactory) {

        if (_hubManager == null)
            throw new NullPointerException("_hubManager was null.");
        else if (_settings == null)
            throw new NullPointerException("_settings was null.");
        else if (_hubFactory == null)
            throw new NullPointerException("_hubFactory was null.");

        hubManager = _hubManager;
        settings = _settings;
        hubFactory = _hubFactory;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {
        HostInfo hostInfo = new HostInfo(cmdData);

        try {
            String IP = hostInfo.getIpString();

            // not yet connected
            if (hubManager.getHubWithIP(IP) == null) {
                // forwarding enabled
                if (settings.getAdvancedSettings().isForceMove()) {
                    // (new Hub(hostInfo, settings)).connect();
                    IHub newHub = hubFactory.createHub(hostInfo);

                    newHub.connect();

                    // Timo : 12.05.2004
                    // TODO : could be externalized as setting "disconnect on
                    // move"

                    // forwarding disabled
                } else {
                    String info = "$ForceMove disabled in Application Settings.";
                    logger.info(info);

                }

                // end if
                // already connected
            } else {
                String info = "Already connected to hub mentioned in $ForceMove.";
                logger.info(info);

            }

            // end if
            // host not found
        } catch (UnknownHostException e) {
            logger.debug("Host was not found.");

        }

        // disconnect from the old hub
        hub.disconnect();
    }

}