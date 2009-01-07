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

import net.sf.javadc.interfaces.IConnection;

/**
 * <code>ConnectionListenerBase</code> is the default implementation of the <code>ConnectionListener</code> interface.
 * It can be used as the super class for implementations which are only interested in few notifications.
 * 
 * @author Timo Westk�mper
 */
public class ConnectionListenerBase
    implements
        ConnectionListener
{
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ConnectionListener#disconnected(net.sf.javadc.interfaces.IConnection)
     */
    public void disconnected(
        IConnection connection )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ConnectionListener#downloadComplete(net.sf.javadc.interfaces.IConnection)
     */
    public void downloadComplete(
        IConnection connection )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ConnectionListener#downloadFailed(net.sf.javadc.interfaces.IConnection)
     */
    public void downloadFailed(
        IConnection connection )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ConnectionListener#stateChanged(net.sf.javadc.interfaces.IConnection)
     */
    public void stateChanged(
        IConnection connection )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ConnectionListener#uploadComplete(net.sf.javadc.interfaces.IConnection)
     */
    public void uploadComplete(
        IConnection connection )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ConnectionListener#uploadFailed(net.sf.javadc.interfaces.IConnection)
     */
    public void uploadFailed(
        IConnection connection )
    {
        // TODO Auto-generated method stub

    }

}