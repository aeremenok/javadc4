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
 * <p>
 * Feature: GetZBlock
 * </p>
 * 
 * <p>
 * Usage: Instead of $Get and $Send, use "$GetZBlock &lt;start&gt;
 * &lt;numbytes&gt; &lt;filename&gt;|" where &lt;start&gt; is the 0-based (yes,
 * 0-based, not like get that's 1-based) starting index of the file used,
 * &lt;numbytes&gt; is the number of bytes to send and &lt;filename&gt;
 * obviously is the filename. The other client then responds "$Sending
 * &lt;bytes&gt;|&lt;compressed data&gt;", if the sending is ok or "$Failed
 * &lt;errordescription&gt;|" if it isn't. If everything's ok, the data is sent
 * until the whole uncompressed length has been sent. &lt;bytes&gt; specifies
 * how many uncompressed bytes will be sent, not compressed, as the sending
 * client doesn't know how well the file will compress. $Sending is needed to be
 * able to distinguish the failure command from file data. Only one roundtrip is
 * done for each block though, minimizing the need for maintaining states.
 * </p>
 * 
 * <p>
 * Compression: Compression is done using ZLib (v 1.1.4 in DC++ 0.21's case),
 * using dynamic compression level. The compression level can of course be
 * changed by the implementator to reduce CPU usage, or even just store
 * compression in the case of non-compressible files, which then works as
 * adler32 check of the transferred data.
 * </P>
 * 
 * @author Timo Westk�mper
 */
public class IGetZBlockTask extends IUGetBlockTask {

    /**
     * Create a <code>IGetZBlockTask</code> instance
     * 
     * @param _shareManager
     *            IShareManager instance to be used
     * @param _settings
     *            ISettings instance to be used
     */
    public IGetZBlockTask(IShareManager _shareManager, ISettings _settings) {
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