/*
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
import net.sf.javadc.interfaces.IShareManager;

/**
 * Other than the file list, the client must also support one or two new
 * commands, $UGetBlock and $UGetZBlock. The syntax and semantics of $UGetZBlock
 * are exactly the same as the $GetZBlock, but the filename must be given in
 * utf-8 encoding.
 * 
 * @author Timo Westk�mper
 */
public class IUGetZBlockTask extends IUGetBlockTask {

    /**
     * Create a <code>IUGetZBlockTask</code> instance
     * 
     * @param _shareManager
     *            IShareManager instance to be used
     * @param _settings
     *            ISettings instance to be used
     */
    public IUGetZBlockTask(IShareManager _shareManager, ISettings _settings) {
        super(_shareManager, _settings);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.BaseClientTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {
        super.runTaskTemplate();

        clientConnection.getConnectionInfo().setUseCompression(true);
    }
}