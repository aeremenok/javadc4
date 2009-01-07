/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

// $Id: AbstractSettingsAdapter.java,v 1.14 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

import java.util.EventListener;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ISettingsLoader;
import net.sf.javadc.listeners.SettingsListener;
import net.sf.javadc.util.GenericModel;

import org.picocontainer.Startable;

/**
 * <CODE>AbstractSettingsAdapter</CODE> represents the base class for the <CODE>SettingsAdapter</CODE>, which wraps the
 * serialized <CODE>Settings
 * </CODE> instance
 * 
 * @author Timo Westk�mper $Id: AbstractSettingsAdapter.java,v 1.14 2005/10/02 11:42:27 timowest Exp $ $Author: timowest
 *         $
 */
public abstract class AbstractSettingsAdapter
    extends GenericModel
    implements
        ISettings,
        Startable
{
    /**
     * 
     */
    protected ISettingsLoader settingsLoader;

    /**
     * Add a SettingsListener to the list of EventListeners
     */
    @Override
    public void addListener(
        EventListener listener )
    {

        if ( listener instanceof SettingsListener )
        {
            super.addListener( listener );

            SettingsListener l = (SettingsListener) listener;

            l.downloadSlotsChanged( getUsedDownloadSlots(), getDownloadSlots() );

            l.uploadSlotsChanged( getUsedUploadSlots(), getUploadSlots() );

        }
        else
        {
            throw new RuntimeException( "listener is not an instance of SettingsListener" );
        }

    }

    /**
     * Notify the registered SettingsListeners, that the download slot count has changed
     */
    public final void fireDownloadSlotsChanged()
    {

        SettingsListener[] l = listenerList.getListeners( SettingsListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].downloadSlotsChanged( getUsedDownloadSlots(), getDownloadSlots() );
        }

    }

    /**
     * Notifiy the registered SettingsListeners, that the upload slot count has changed
     */
    public final void fireUploadSlotsChanged()
    {

        SettingsListener[] l = listenerList.getListeners( SettingsListener.class );

        for ( int i = 0; i < l.length; i++ )
        {
            l[i].uploadSlotsChanged( getUsedUploadSlots(), getUploadSlots() );
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public final void start()
    {
        setUsedUploadSlots( 0 );
        setUsedDownloadSlots( 0 );

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public final void stop()
    {
        setUsedUploadSlots( 0 );
        setUsedDownloadSlots( 0 );

        settingsLoader.save();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    protected Class getListenerClass()
    {
        return SettingsListener.class;
    }

}

/*******************************************************************************
 * $Log: AbstractSettingsAdapter.java,v $ Revision 1.14 2005/10/02 11:42:27 timowest updated sources and tests Revision
 * 1.13 2005/09/30 15:59:52 timowest updated sources and tests Revision 1.12 2005/09/14 07:11:48 timowest updated
 * sources
 */
