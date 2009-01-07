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
import java.util.List;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ISettingsLoader;
import net.sf.javadc.interfaces.IUserInfo;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * <CODE>SettingsAdapter</CODE> is an Adapter to the basic <CODE>Settings</CODE> instance which includes EventListener
 * notification
 * 
 * @author Timo Westk�mper
 */
public class SettingsAdapter
    extends AbstractSettingsAdapter
{
    static private final Category logger = Logger.getLogger( SettingsLoader.class );

    // components
    /**
     * 
     */
    private final ISettings       _settings;

    /**
     * Create a SettingsAdapter instance which uses the given ISettingsLoader instance to populate a Settings instance
     * 
     * @param _settingsLoader ISettings instance to be used
     */
    public SettingsAdapter(
        ISettingsLoader _settingsLoader )
    {
        if ( _settingsLoader == null )
        {
            throw new NullPointerException( "_settingsLoader was null." );
        }

        settingsLoader = _settingsLoader;
        _settings = settingsLoader.load();

        clearTemporaryDownloadDirectory();
    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getAdvancedSettings()
     */
    public final AdvancedSettings getAdvancedSettings()
    {
        /*
         * AdvancedSettings a = _settings.getAdvancedSettings();
         * 
         * if (a == null){ a = new AdvancedSettings();
         * _settings.setAdvancedSettings(a);
         *  }
         * 
         * return a;
         */

        // null checks are not necessary, because AdvancedSettings is set via
        // SettingsFactory
        return _settings.getAdvancedSettings();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getDownloadDir()
     */
    public final String getDownloadDir()
    {
        return _settings.getDownloadDir();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getDownloadSlots()
     */
    public final int getDownloadSlots()
    {
        return _settings.getDownloadSlots();

    }

    /*
     * public final File getDownloadDirectory() { return
     * _settings.getDownloadDirectory(); }
     */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getDownloadSpeed()
     */
    public final int getDownloadSpeed()
    {
        return _settings.getDownloadSpeed();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getFreeDownloadSlotCount()
     */
    public final int getFreeDownloadSlotCount()
    {
        return _settings.getFreeDownloadSlotCount();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getFreeUploadSlotCount()
     */
    public final int getFreeUploadSlotCount()
    {
        return _settings.getFreeUploadSlotCount();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getGuiSettings()
     */
    public final GuiSettings getGuiSettings()
    {
        return _settings.getGuiSettings();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getIP()
     */
    public final String getIP()
    {
        return _settings.getIP();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getLogDir()
     */
    public final String getLogDir()
    {
        return _settings.getLogDir();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getTempDownloadDir()
     */
    public final String getTempDownloadDir()
    {
        return _settings.getTempDownloadDir();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUploadDirs()
     */
    public final List getUploadDirs()
    {
        List list = _settings.getUploadDirs();

        if ( list == null )
        {
            list = new ArrayList();
            _settings.setUploadDirs( list );
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
        return _settings.getUploadSlots();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUploadSpeed()
     */
    public final int getUploadSpeed()
    {
        return _settings.getUploadSpeed();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUsedDownloadSlots()
     */
    public final int getUsedDownloadSlots()
    {
        return _settings.getUsedDownloadSlots();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUsedUploadSlots()
     */
    public final int getUsedUploadSlots()
    {
        return _settings.getUsedUploadSlots();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUserInfo()
     */
    public final IUserInfo getUserInfo()
    {

        /*
         * IUserInfo userInfo =_settings.getUserInfo();
         * 
         * if (userInfo == null){ userInfo = new UserInfo();
         * _settings.setUserInfo(userInfo); }
         * 
         * return userInfo;
         */

        // null checks are not necessary, because UserInfo is set via
        // SettingsFactory
        return _settings.getUserInfo();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#isActive()
     */
    public final boolean isActive()
    {
        return _settings.isActive();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#releaseDownloadSlot()
     */
    public final void releaseDownloadSlot()
    {
        _settings.releaseDownloadSlot();
        fireDownloadSlotsChanged();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#releaseUploadSlot()
     */
    public final void releaseUploadSlot()
    {
        _settings.releaseUploadSlot();
        fireUploadSlotsChanged();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#reserveDownloadSlot()
     */
    public final boolean reserveDownloadSlot()
    {
        boolean ret = _settings.reserveDownloadSlot();
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
        boolean ret = _settings.reserveUploadSlot();
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

        _settings.setActive( b );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setAdvancedSettings(net.sf.javadc.config.AdvancedSettings)
     */
    public final void setAdvancedSettings(
        AdvancedSettings settings )
    {
        _settings.setAdvancedSettings( settings );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setDownloadDir(java.lang.String)
     */
    public final void setDownloadDir(
        String dir )
    {
        _settings.setDownloadDir( dir );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setDownloadSlots(int)
     */
    public final void setDownloadSlots(
        int slots )
    {
        _settings.setDownloadSlots( slots );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setDownloadSpeed(int)
     */
    public final void setDownloadSpeed(
        int i )
    {
        _settings.setDownloadSpeed( i );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setGuiSettings(net.sf.javadc.config.GuiSettings)
     */
    public final void setGuiSettings(
        GuiSettings guiSettings )
    {
        _settings.setGuiSettings( guiSettings );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setIP(java.lang.String)
     */
    public final void setIP(
        String string )
    {
        _settings.setIP( string );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setLogDir(java.lang.String)
     */
    public final void setLogDir(
        String string )
    {
        _settings.setLogDir( string );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setTempDownloadDir(java.lang.String)
     */
    public final void setTempDownloadDir(
        String string )
    {
        _settings.setTempDownloadDir( string );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUploadDirs(java.util.List)
     */
    public final void setUploadDirs(
        List dirs )
    {
        _settings.setUploadDirs( dirs );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUploadSlots(int)
     */
    public final void setUploadSlots(
        int i )
    {
        _settings.setUploadSlots( i );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUploadSpeed(int)
     */
    public final void setUploadSpeed(
        int i )
    {
        _settings.setUploadSpeed( i );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUsedDownloadSlots(int)
     */
    public final void setUsedDownloadSlots(
        int i )
    {
        _settings.setUsedDownloadSlots( i );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUsedUploadSlots(int)
     */
    public final void setUsedUploadSlots(
        int i )
    {
        _settings.setUsedUploadSlots( i );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUserInfo(net.sf.javadc.interfaces.IUserInfo)
     */
    public final void setUserInfo(
        IUserInfo userInfo )
    {
        _settings.setUserInfo( userInfo );

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

            for ( int i = 0; i < files.length; i++ )
            {
                if ( files[i].isFile() && files[i].length() == 0 )
                {
                    logger.debug( "File " + files[i].getPath() + " is removed." );
                    files[i].delete();

                }
                else
                {
                    logger.debug( "File " + files[i].getPath() + " is not removed." );

                }

            }

        }
        else
        {
            logger.debug( "Temporary directory has not been specified." );

        }

    }

}

/*******************************************************************************
 * $Log: SettingsAdapter.java,v $ Revision 1.25 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.24
 * 2005/09/30 15:59:52 timowest updated sources and tests Revision 1.23 2005/09/26 17:19:52 timowest updated sources and
 * tests Revision 1.22 2005/09/14 07:11:48 timowest updated sources
 */
