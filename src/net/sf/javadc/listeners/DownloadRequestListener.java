/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
/*
 * Created on 14.7.2004 To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code
 * Generation&gt;Code and Comments
 */

package net.sf.javadc.listeners;

import java.util.EventListener;

import net.sf.javadc.net.DownloadRequest;

/**
 * <code>DownloadRequestListener</code> is the listener interface for objects interested in notifications from
 * <code>DownloadRequest</code> instances
 * 
 * @author Timo Westk�mper
 */
public interface DownloadRequestListener
    extends
        EventListener
{
    /**
     * The state of the given DownloadRequest has changed
     * 
     * @param downloadRequest DownloadRequest whose state has changed
     */
    void downloadRequestStateChanged(
        DownloadRequest downloadRequest );

}