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

import org.apache.log4j.Category;

/**
 * <CODE>IHubIsFullTask</CODE> is used to signalize that the hub is full and that the client is not allowed to enter
 * 
 * @author tw70794
 */
public class IHubIsFullTask
    extends BaseHubTask
{

    private static final Category logger = Category.getInstance( IHubIsFullTask.class );

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    @Override
    protected void runTaskTemplate()
    {
        logger.info( "Hub is full." );

        // hub.disconnect();
        // TODO : error notification on GUI

        hub.disconnect();
    }

}