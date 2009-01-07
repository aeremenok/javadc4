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

/**
 * <CODE>IGetPassTask</CODE> is called when specified nickname is registered
 * and the hub asks for a password
 * 
 * @author tw70794
 */
public class IGetPassTask extends BaseHubTask {

    // private static final Category logger = Category
    // .getInstance(IGetPassTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {
        // opens a dialog where the user can enter the required password
        String passwd = javax.swing.JOptionPane.showInputDialog(null,
                "Please enter your password: ");

        sendCommand("$MyPass", passwd);

    }

}