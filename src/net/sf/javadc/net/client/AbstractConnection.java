/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.net.client;

import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.util.GenericModel;

/**
 * <code>AbstractConnection</code> represents an abstract base implementation for the <code>IConnection</code> interface
 * mainly used to separate the listener notification code from the main implementation
 * 
 * @author Timo Westk�mper
 */
public abstract class AbstractConnection
    extends GenericModel
    implements
        IConnection
{
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        Object obj )
    {
        // object identity
        if ( obj == this )
        {
            return true;

            // if clients are equals the connections are equal as well
        }
        else if ( obj instanceof IConnection )
        {
            IConnection conn = (IConnection) obj;
            return getClient().equals( conn.getClient() );

            // no IConnection instance
        }
        else
        { // obj is not IConnection
            return false;

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#fireDisconnected()
     */
    public final void fireDisconnected()
    {
        ConnectionListener[] l = listenerList.getListeners( ConnectionListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].disconnected( this );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#fireDownloadComplete()
     */
    public final void fireDownloadComplete()
    {
        ConnectionListener[] l = listenerList.getListeners( ConnectionListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].downloadComplete( this );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#fireDownloadFailed()
     */
    public final void fireDownloadFailed()
    {
        ConnectionListener[] l = listenerList.getListeners( ConnectionListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].downloadFailed( this );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#fireStateChanged()
     */
    public final void fireStateChanged()
    {
        ConnectionListener[] l = listenerList.getListeners( ConnectionListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].stateChanged( this );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#fireUploadComplete()
     */
    public final void fireUploadComplete()
    {
        ConnectionListener[] l = listenerList.getListeners( ConnectionListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].uploadComplete( this );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#fireUploadFailed()
     */
    public final void fireUploadFailed()
    {
        ConnectionListener[] l = listenerList.getListeners( ConnectionListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].uploadFailed( this );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    public final Class getListenerClass()
    {
        return ConnectionListener.class;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {

        // return the Client's hashcode, if available
        if ( getClient() != null )
        {
            return getClient().hashCode();

            // return default hashCode
        }
        else
        {
            return super.hashCode();
        }
    }

}

/*******************************************************************************
 * $Log: AbstractConnection.java,v $ Revision 1.16 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.15
 * 2005/09/12 21:12:02 timowest added log block
 */
