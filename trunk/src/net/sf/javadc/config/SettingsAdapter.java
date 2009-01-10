/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: SettingsAdapter.java,v 1.25 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

import java.io.File;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import junit.framework.Assert;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ISettingsLoader;
import net.sf.javadc.interfaces.IUserInfo;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * An Adapter to the basic <CODE>Settings</CODE> instance which includes EventListener notification
 * 
 * @author Timo Westk�mper
 */
public class SettingsAdapter
    extends AbstractSettingsAdapter
{
    static private final Category          logger = Logger.getLogger( SettingsLoader.class );

    private final ISettings<EventListener> settings;

    /**
     * Create a SettingsAdapter instance which uses the given ISettingsLoader instance to populate a Settings instance
     * 
     * @param _settingsLoader ISettings instance to be used
     */
    public SettingsAdapter(
        ISettingsLoader _settingsLoader )
    {
        Assert.assertNotNull( _settingsLoader );

        settingsLoader = _settingsLoader;
        settings = settingsLoader.load();

        clearTemporaryDownloadDirectory();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getAdvancedSettings()
     */
    public final AdvancedSettings getAdvancedSettings()
    {
        return settings.getAdvancedSettings();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getDownloadDir()
     */
    public final String getDownloadDir()
    {
        return settings.getDownloadDir();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getDownloadSlots()
     */
    public final int getDownloadSlots()
    {
        return settings.getDownloadSlots();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getDownloadSpeed()
     */
    public final int getDownloadSpeed()
    {
        return settings.getDownloadSpeed();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getFreeDownloadSlotCount()
     */
    public final int getFreeDownloadSlotCount()
    {
        return settings.getFreeDownloadSlotCount();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getFreeUploadSlotCount()
     */
    public final int getFreeUploadSlotCount()
    {
        return settings.getFreeUploadSlotCount();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getGuiSettings()
     */
    public final GuiSettings getGuiSettings()
    {
        return settings.getGuiSettings();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getIP()
     */
    public final String getIP()
    {
        return settings.getIP();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getLogDir()
     */
    public final String getLogDir()
    {
        return settings.getLogDir();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getTempDownloadDir()
     */
    public final String getTempDownloadDir()
    {
        return settings.getTempDownloadDir();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUploadDirs()
     */
    public final List<String> getUploadDirs()
    {
        List<String> list = settings.getUploadDirs();
        if ( list == null )
        {
            list = new ArrayList<String>();
            settings.setUploadDirs( list );
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUploadSlots()
     */
    public final int getUploadSlots()
    {
        return settings.getUploadSlots();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUploadSpeed()
     */
    public final int getUploadSpeed()
    {
        return settings.getUploadSpeed();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUsedDownloadSlots()
     */
    public final int getUsedDownloadSlots()
    {
        return settings.getUsedDownloadSlots();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUsedUploadSlots()
     */
    public final int getUsedUploadSlots()
    {
        return settings.getUsedUploadSlots();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUserInfo()
     */
    public final IUserInfo getUserInfo()
    {
        return settings.getUserInfo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#isActive()
     */
    public final boolean isActive()
    {
        return settings.isActive();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#releaseDownloadSlot()
     */
    public final void releaseDownloadSlot()
    {
        settings.releaseDownloadSlot();
        fireDownloadSlotsChanged();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#releaseUploadSlot()
     */
    public final void releaseUploadSlot()
    {
        settings.releaseUploadSlot();
        fireUploadSlotsChanged();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#reserveDownloadSlot()
     */
    public final boolean reserveDownloadSlot()
    {
        boolean ret = settings.reserveDownloadSlot();
        fireDownloadSlotsChanged();
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#reserveUploadSlot()
     */
    public final boolean reserveUploadSlot()
    {
        boolean ret = settings.reserveUploadSlot();
        fireUploadSlotsChanged();
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setActive(boolean)
     */
    public final void setActive(
        boolean b )
    {
        if ( b )
        {
            logger.info( "Switching to active mode" );
        }
        else
        {
            logger.info( "Switching to passive mode" );
        }
        logger.info( "============================================" );

        settings.setActive( b );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setAdvancedSettings(net.sf.javadc.config.AdvancedSettings)
     */
    public final void setAdvancedSettings(
        AdvancedSettings advancedSettings )
    {
        settings.setAdvancedSettings( advancedSettings );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setDownloadDir(java.lang.String)
     */
    public final void setDownloadDir(
        String dir )
    {
        settings.setDownloadDir( dir );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setDownloadSlots(int)
     */
    public final void setDownloadSlots(
        int slots )
    {
        settings.setDownloadSlots( slots );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setDownloadSpeed(int)
     */
    public final void setDownloadSpeed(
        int i )
    {
        settings.setDownloadSpeed( i );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setGuiSettings(net.sf.javadc.config.GuiSettings)
     */
    public final void setGuiSettings(
        GuiSettings guiSettings )
    {
        settings.setGuiSettings( guiSettings );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setIP(java.lang.String)
     */
    public final void setIP(
        String string )
    {
        settings.setIP( string );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setLogDir(java.lang.String)
     */
    public final void setLogDir(
        String string )
    {
        settings.setLogDir( string );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setTempDownloadDir(java.lang.String)
     */
    public final void setTempDownloadDir(
        String string )
    {
        settings.setTempDownloadDir( string );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUploadDirs(java.util.List)
     */
    public final void setUploadDirs(
        List<String> dirs )
    {
        settings.setUploadDirs( dirs );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUploadSlots(int)
     */
    public final void setUploadSlots(
        int i )
    {
        settings.setUploadSlots( i );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUploadSpeed(int)
     */
    public final void setUploadSpeed(
        int i )
    {
        settings.setUploadSpeed( i );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUsedDownloadSlots(int)
     */
    public final void setUsedDownloadSlots(
        int i )
    {
        settings.setUsedDownloadSlots( i );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUsedUploadSlots(int)
     */
    public final void setUsedUploadSlots(
        int i )
    {
        settings.setUsedUploadSlots( i );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUserInfo(net.sf.javadc.interfaces.IUserInfo)
     */
    public final void setUserInfo(
        IUserInfo userInfo )
    {
        settings.setUserInfo( userInfo );
    }

    /**
     * Remove all zero sized files in the directory for temporary downloads.
     */
    private void clearTemporaryDownloadDirectory()
    {
        String tempDirName = this.getTempDownloadDir();

        if ( tempDirName != null && !tempDirName.equals( "" ) )
        {
            File tempDir = new File( tempDirName );
            File[] files = tempDir.listFiles();
            if ( files == null )
            {
                logger.error( "Couldn't obtain any files for directory " + tempDir );
                return;
            }
            for ( File file : files )
            {
                if ( file.isFile() && file.length() == 0 )
                {
                    logger.debug( "File " + file.getPath() + " is removed." );
                    file.delete();
                }
                else
                {
                    logger.debug( "File " + file.getPath() + " is not removed." );
                }
            }
        }
        else
        {
            logger.debug( "Temporary directory has not been specified." );
        }
    }
}
