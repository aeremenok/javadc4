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

import net.sf.javadc.tasks.BaseClientTask;

import org.apache.log4j.Category;

/**
 * Instead of $Get and $Send, use "$GetZBlock &lt;start&gt; &lt;numbytes&gt;
 * &lt;filename&gt;|" where &lt;start&gt; is the 0-based (yes, 0-based, not like
 * get that's 1-based) starting index of the file used, &lt;numbytes&gt; is the
 * number of bytes to send and &lt;filename&gt; obviously is the filename. The
 * other client then responds
 * 
 * <p>
 * "$Sending &lt;bytes&gt;|&lt;compressed data&gt;", if the sending is ok or
 * </p>
 * 
 * <p>
 * "$Failed &lt;errordescription&gt;|" if it isn't.
 * </p>
 * 
 * @author Timo Westk�mper
 * 
 * @see net.sf.javadc.tasks.client.IGetZBlockTask
 */
public class ISendingTask extends BaseClientTask {

    private static final Category logger = Category
            .getInstance(ISendingTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.BaseClientTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {

        logger.debug("Got $Sending " + getCmdData());

    }

}