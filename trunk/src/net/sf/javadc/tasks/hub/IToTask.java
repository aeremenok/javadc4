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

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.net.Message;
import net.sf.javadc.tasks.BaseHubTask;
import net.sf.javadc.util.StringTokenizer2;

/**
 * <p>
 * $To
 * </p>
 * 
 * <p>
 * The client may send $To to send a private message to a peer.
 * </p>
 * 
 * <p>
 * $To: &lt;othernick&gt; From: &lt;nick&gt; $&lt;nick&gt; &lt;message&gt;
 * </p>
 * 
 * <p>
 * &lt;othernick&gt; is the peer who should receive the message.<br/>
 * &lt;nick&gt; is the sender's nick (both instances).<br/> &lt;message&gt; is
 * the text of the message (spaces permitted).
 * </p>
 * 
 * <p>
 * The server must pass the message unmodified to client &lt;othernick&gt; which
 * must display the message to the user.
 * </p>
 * 
 * <p>
 * The client may send &lt;&gt; to send a public message to all hub users.
 * </p>
 * 
 * <p>
 * &lt;&lt;nick&gt;&gt; &lt;message&gt;
 * </p>
 * 
 * <p>
 * &lt;nick&gt; is this sender's nick.
 * </p>
 * 
 * <p>
 * The server must pass the message unmodified to all other clients (not
 * &lt;nick&gt;). All other clients must display the message to that user.
 * </p>
 * 
 * <p>
 * XXX this is incorrect. the hub does send the message back to the creator /sed
 * </p>
 * 
 * @author tw70794
 */
public class IToTask extends BaseHubTask {

    // private static final Category logger =
    // Logger.getLogger(IToTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {
        // TODO : to be replaced by java.lang.String.split()
        StringTokenizer2 tokenizer = new StringTokenizer2(cmdData,
                ConstantSettings.COMMAND_SEP);

        try {
            String to = tokenizer.nextToken(); // Me

            tokenizer.nextToken(); // From:

            hub.fireGotUserMessage(new Message(tokenizer.nextToken(), to,
                    cmdData.substring(tokenizer.getPosition() + 1)));

        } catch (StringIndexOutOfBoundsException e) {
            throw new HubTaskException("Caught " + e.getClass().getName(), e);

        }

    }

}