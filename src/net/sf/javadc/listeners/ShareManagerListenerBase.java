/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */
package net.sf.javadc.listeners;

/**
 * <code>ShareManagerListenerBase</code> is the default implementation of the <code>ShareManagerListener</code>
 * interface. It can be used as the super class for implementations which are only interested in few notifications.
 * 
 * @author Timo Westk�mper
 */
public class ShareManagerListenerBase
    implements
        ShareManagerListener
{
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ShareManagerListener#browseListCreated()
     */
    public void browseListCreated()
    {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ShareManagerListener#creatingBrowseList()
     */
    public void creatingBrowseList()
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ShareManagerListener#directoryAdded(java.lang.String)
     */
    public void directoryAdded(
        String directory )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ShareManagerListener#fileHashed(java.lang.String)
     */
    public void fileHashed(
        String filename )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ShareManagerListener#hashingFile(java.lang.String,
     *      double)
     */
    public void hashingFile(
        String filename,
        double percent )
    {
        // TODO Auto-generated method stub

    }

}
