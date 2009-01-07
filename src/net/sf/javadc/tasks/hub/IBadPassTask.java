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

import net.sf.javadc.tasks.BaseHubTask;

import org.apache.log4j.Category;

/**
 * <CODE>IBadPassTask</CODE> is called when the supplied password was invalid
 * 
 * <p>
 * Syntax:
 * </p>
 * 
 * <p>
 * $BadPass|
 * </p>
 * 
 * <p>
 * It is sent to a connecting user after the $GetPass command and
 * <p>
 * only if the connecting user provide the Hub with a password that didn't match
 * the password under his nickname in the Hub's registry of users.
 * </p>
 * 
 * @author tw70794
 */
public class IBadPassTask extends BaseHubTask {

    private static final Category logger = Category
            .getInstance(IBadPassTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {
        logger.debug("The password was refused.");

        // TODO : Error handling
    }

}