/*
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

// $Id: HubManagerListener.java,v 1.7 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.listeners;

import java.util.EventListener;

import net.sf.javadc.interfaces.IHub;

/**
 * <code>HubManagerListener</code> is the listener interface for objects
 * interested in notifications from <code>HubManager</code> instances
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.7 $ $Date: 2005/10/02 11:42:27 $
 */
public interface HubManagerListener extends EventListener {

    /**
     * The given Hub has been added
     * 
     * @param hub
     *            Hub which has been added
     */
    void hubAdded(IHub hub);

    /**
     * The given Hub has been removed
     * 
     * @param hub
     *            Hub which has been removed
     */
    void hubRemoved(IHub hub);

}