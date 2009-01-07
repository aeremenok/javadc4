/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.tasks.hub;

import net.sf.javadc.tasks.BaseHubTask;

/**
 * <CODE>IHubNameTask</CODE> is used to send the name of the hub
 * <p>
 * This command is part of the Client-Hub Handshake today.
 * </p>
 * <p>
 * Syntax:
 * </p>
 * <p>
 * $HubName &lt;hubname&gt;|
 * </p>
 * <p>
 * &lt;hubname&gt; is the name you use for the hub.
 * </p>
 * <p>
 * Extensions:
 * </p>
 * <p>
 * It can be sent at any time, changing the name of the hub. This variable should only be used as a sort of description
 * of the hub, a topic.
 * </p>
 * <p>
 * Client1 and Client2 in same hub might have recieved different &lt;hubname&gt;, this can occur in nmdchub. So
 * &lt;hubname&gt; should not be used in a way to identify hubs.
 * </p>
 * 
 * @author tw70794
 */
public class IHubNameTask
    extends BaseHubTask
{

    // private static final Category logger = Category
    // .getInstance(IHubNameTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    @Override
    protected void runTaskTemplate()
    {
        hub.setName( cmdData );

    }

}