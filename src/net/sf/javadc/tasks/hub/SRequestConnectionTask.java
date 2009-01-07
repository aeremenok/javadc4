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

import java.io.IOException;

import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.net.hub.Hub;
import net.sf.javadc.tasks.BaseHubTask;
import net.sf.javadc.util.PerformanceContext;

import org.apache.log4j.Category;

/**
 * @author tw70794 To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SRequestConnectionTask extends BaseHubTask {

    private static final Category logger = Category
            .getInstance(SRequestConnectionTask.class);

    private final ISettings settings;

    private final IClientManager clientManager;

    /**
     * Create a new SRequestConnectionTask instance
     * 
     * @param settings
     * @param clientManager
     */
    public SRequestConnectionTask(ISettings settings,
            IClientManager clientManager) {

        if (settings == null)
            throw new NullPointerException("settings was null.");
        else if (clientManager == null)
            throw new NullPointerException("clientManager was null.");

        this.settings = settings;
        this.clientManager = clientManager;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {
        PerformanceContext cont = new PerformanceContext(
                "SRequestConnection#runTaskTemplate()").start();

        // active mode
        if (settings.isActive()) {
            int dataPort = settings.getUserInfo().getPort();
            String ip = settings.getIP();

            HostInfo host = new HostInfo(ip, dataPort);
            Client client = clientManager.getClient(host);
            client.addListener(((Hub) hub).clientListener);

            try {
                // client.connect();
                client.serverConnect();

            } catch (IOException io) {
                // logger.error(io.toString());
                logger.error("Caught " + io.getClass().getName(), io);

            }

            // $ConnectToMe <RemoteNick> <SenderIp>:<SenderPort>
            sendCommand("$ConnectToMe", cmdData + " " + host.getHostAndPort());

            logger.debug("Requesting active connection to " + cmdData
                    + " on port " + dataPort);

            // passive mode
        } else {
            String nick = settings.getUserInfo().getNick();

            sendCommand("$RevConnectToMe", nick + " " + cmdData);

            logger.debug("Requesting passive connection to " + cmdData);

        }

        cont.end();

        if (cont.getDuration() > 100) {
            logger.info(cont);
        }

    }

}