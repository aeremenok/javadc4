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

package net.sf.javadc.tasks.client;

import net.sf.javadc.tasks.BaseClientTask;

/**
 * The first command to be sent when connecting to a user.
 * 
 * <p>
 * The listen User sends his Name when a user connecting.
 * </p>
 * 
 * <p>
 * $MyNick &lt;ownnickname&gt;
 * </p>
 * 
 * <p>
 * &lt;ownnickname&gt; is the nick of the user that sent to command.
 * </p>
 * 
 * <p>
 * The $Lock is sent immediately after the $MyNick command within the same
 * packet.
 * </p>
 * 
 * <p>
 * $Lock &lt;lock&gt; Pk=&lt;pk&gt;
 * </p>
 * 
 * <p>
 * &lt;lock&gt; is used to generate &lt;key&gt;
 * </p>
 * <p>
 * See Appendix A: Converting a Lock into a Key for how to compute the key from
 * the lock.
 * </p>
 * <p>
 * &lt;pk&gt; is unused, but SG believes Pk stands for "private" or "public
 * key".
 * </p>
 * 
 * <p>
 * Immediately following the $MyNick + $Lock packet the connecting user sends
 * their $MyNick + $Lock in the same fashion as above.
 * </p>
 * 
 * @author tw70794
 */
public class IMyNickTask extends BaseClientTask {

    // private static final Category logger = Category
    // .getInstance(IMyNickTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {
        if (cmdData == null)
            throw new NullPointerException("cmdData was null.");

        clientConnection.getClient().setNick(cmdData);

    }

}