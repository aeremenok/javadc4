/* *
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
 *
 * Copyright (C) 2002 Michael Kurz, mkurz@epost.de
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

import java.io.File;

import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.tasks.BaseClientTask;

/**
 * $GetListLen is used to know the dimension in bytes of the remote user's list
 * 
 * <p>
 * The downloading client sends the $GetListLen to request the filesize of the
 * remote DCList in bytes.
 * </p>
 * 
 * <p>
 * $GetListLen
 * </p>
 * 
 * <p>
 * The uploading client responds to the $GetListLen command with $ListLen.
 * </p>
 * 
 * <p>
 * $ListLen &lt;sizeinbyte&gt;
 * </p>
 * 
 * <p>
 * &lt;sizeinbyt&gt; is the filesize of the remote DCList in bytes.
 * </p>
 * 
 * @author tw70794
 */
public class IGetListLenTask extends BaseClientTask {

    // private static final Category logger = Category
    // .getInstance(IGetListLenTask.class);
    private final IShareManager shareManager;

    /**
     * Create a <code>IGetListLenTask</code> instance
     * 
     * @param _shareManager
     *            ShareManager instance to be used
     */
    public IGetListLenTask(IShareManager _shareManager) {

        if (_shareManager == null)
            throw new NullPointerException("_shareManager was null.");

        shareManager = _shareManager;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {
        File file = shareManager.getFile("MyList.DcLst");

        sendCommand("$ListLen", Long.toString(file.length()));

    }

}