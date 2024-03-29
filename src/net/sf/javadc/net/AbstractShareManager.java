/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.net;

import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.listeners.ShareManagerListener;
import net.sf.javadc.util.GenericModel;

/**
 * <code>AbstractShareManager</code> represents an abstract base implementation for the <code>IShareManager</code>
 * interface mainly used to separate the listener notification code from the main implementation
 * 
 * @author Timo Westk�mper
 */
public abstract class AbstractShareManager
    extends GenericModel<ShareManagerListener>
    implements
        IShareManager<ShareManagerListener>
{

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IShareManager#fireBrowseListCreated()
     */
    public final void fireBrowseListCreated()
    {
        ShareManagerListener[] l = listenerList.getListeners( ShareManagerListener.class );
        for ( ShareManagerListener element : l )
        {
            element.browseListCreated();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IShareManager#fireCreatingBrowseList()
     */
    public final void fireCreatingBrowseList()
    {
        ShareManagerListener[] l = listenerList.getListeners( ShareManagerListener.class );
        for ( ShareManagerListener element : l )
        {
            element.creatingBrowseList();
        }
    }

    /*
     * (non-Javadoc)
     *  
     * @see net.sf.javadc.interfaces.IShareManager#fireDirectoryAdded(java.lang.String)
     */
    public final void fireDirectoryAdded(
        String dir )
    {
        ShareManagerListener[] l = listenerList.getListeners( ShareManagerListener.class );
        for ( ShareManagerListener element : l )
        {
            element.directoryAdded( dir );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IShareManager#fireFileHashed(java.lang.String)
     */
    public final void fireFileHashed(
        String filename )
    {
        ShareManagerListener[] l = listenerList.getListeners( ShareManagerListener.class );
        for ( ShareManagerListener element : l )
        {
            element.fileHashed( filename );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IShareManager#fireHashingFile(java.lang.String,
     *      double)
     */
    public final void fireHashingFile(
        String filename,
        double percent )
    {
        ShareManagerListener[] l = listenerList.getListeners( ShareManagerListener.class );
        for ( ShareManagerListener element : l )
        {
            element.hashingFile( filename, percent );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    protected Class<ShareManagerListener> getListenerClass()
    {
        return ShareManagerListener.class;
    }
}
