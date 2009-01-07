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

import java.util.List;

import net.sf.javadc.tasks.BaseHubTask;

/**
 * <CODE>IOpListTask</CODE> is used for lists of all online operators
 * <p>
 * Format is a $$-separated list of nicks: $OpList &lt;op1&gt;$$&lt;op2&gt;$$&lt;op3&gt;$$...
 * </p>
 * <p>
 * Attention: $OpList is a subset of $NickList. If there are no connected operators, the server must send "$OpList ".
 * </p>
 * 
 * @author tw70794
 */
public class IOpListTask
    extends BaseHubTask
{

    // private static final Category logger = Category
    // .getInstance(IOpListTask.class);

    /* 
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    @Override
    protected void runTaskTemplate()
    {
        List opList = hub.getOpList();

        String[] elements = cmdData.split( "\\$\\$" );

        for ( int i = 0; i < elements.length; i++ )
        {
            opList.add( elements[i] );

        }

    }

}