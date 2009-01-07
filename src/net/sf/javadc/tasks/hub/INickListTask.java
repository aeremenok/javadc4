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

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.tasks.BaseHubTask;
import net.sf.javadc.util.PerformanceContext;

import org.apache.log4j.Category;

/**
 * <CODE>INickListTask</CODE> is used for lists of all online users
 * 
 * <p>
 * Format is a $$-separated list of nicks: $NickList
 * &lt;nick1&gt;$$&lt;nick2&gt;$$&lt;nick3&gt;$$...
 * </p>
 * 
 * <p>
 * Attention: $OpList is a subset of $NickList.
 * </p>
 * 
 * @author tw70794
 */
public class INickListTask extends BaseHubTask {

    private static final Category logger = Category
            .getInstance(INickListTask.class);

    private final ISettings settings;

    /**
     * Create a new INickListTask instance
     * 
     * @param settings
     */
    public INickListTask(ISettings settings) {

        if (settings == null)
            throw new NullPointerException("settings was null.");

        this.settings = settings;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {
        PerformanceContext cont = new PerformanceContext(
                "INickListTask#runTaskTemplate()").start();

        String[] elements = cmdData.split("\\$\\$");

        String nick = settings.getUserInfo().getNick();

        for (int i = 0; i < elements.length; i++) {
            // HubUser newUser = new HubUser(hub);
            sendCommand("$GetINFO", elements[i] + " " + nick);

            // Timo : 06.06.2004
            // removed, because HubUser information can't be updated in GUI
            // addUserToHub(elements[i]);
            if ((i % ConnectionSettings.HUB_THREAD_INTERRUPTION_INTERVAL) == 0) {
                try {
                    Thread
                            .sleep(ConnectionSettings.HUB_THREAD_INTERRUPTION_TIME);

                } catch (InterruptedException e) {

                    // ?
                }

            }

        }

        if (logger.isInfoEnabled()) {
            logger.info(cont.end());
        }

    }

}