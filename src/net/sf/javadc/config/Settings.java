/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: Settings.java,v 1.17 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EventListener;
import java.util.List;

import javax.swing.event.EventListenerList;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IUserInfo;

import org.apache.log4j.Category;

/**
 * <CODE>Settings</CODE> represents the main application settings instance
 * 
 * @author Timo Westk�mper
 */
public class Settings
    implements
        ISettings
{
    static private final Category logger           = Category.getInstance( ISettings.class );

    /**
     * 
     */
    private boolean               active           = true;
    /**
     * 
     */
    private AdvancedSettings      advancedSettings = null;

    /**
     * 
     */
    private String                downloadDir;

    /**
     * 
     */
    private String                tempDownloadDir;

    /**
     * 
     */
    private String                logDir;

    /**
     * 
     */
    private String                IP;

    /**
     * 
     */
    private List                  uploadDirs       = null;

    /**
     * 
     */
    private int                   uploadSlots;

    /**
     * 
     */
    private int                   uploadSpeed;

    /**
     * 
     */
    private int                   downloadSlots;

    /**
     * 
     */
    private int                   downloadSpeed;

    /**
     * 
     */
    private transient int         usedDownloadSlots;

    /**
     * 
     */
    private transient int         usedUploadSlots;

    /**
     * 
     */
    private IUserInfo             userInfo         = null;

    /**
     * 
     */
    private GuiSettings           guiSettings;

    /**
     * Create a Settings instance
     */
    public Settings()
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IGenericModel#addListener(java.util.EventListener)
     */
    public void addListener(
        EventListener listener )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#fireDownloadSlotsChanged()
     */
    public void fireDownloadSlotsChanged()
    {
        // TODO Auto-generated method stub

    }

    /*
     * public final File getDownloadDirectory() { return new File(downloadDir); }
     */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#fireUploadSlotsChanged()
     */
    public void fireUploadSlotsChanged()
    {
        // TODO Auto-generated method stub

    }

    /** ********************************************************************** */
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getAdvancedSettings()
     */
    public final AdvancedSettings getAdvancedSettings()
    {
        return advancedSettings;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getDownloadDir()
     */
    public final String getDownloadDir()
    {
        return downloadDir;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getDownloadSlots()
     */
    public final int getDownloadSlots()
    {
        return downloadSlots;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getDownloadSpeed()
     */
    public final int getDownloadSpeed()
    {
        return downloadSpeed;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getFreeDownloadSlotCount()
     */
    public final int getFreeDownloadSlotCount()
    {
        return downloadSlots - usedDownloadSlots;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getFreeUploadSlotCount()
     */
    public final int getFreeUploadSlotCount()
    {
        return uploadSlots - usedUploadSlots;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getGuiSettings()
     */
    public GuiSettings getGuiSettings()
    {
        return guiSettings;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getIP()
     */
    public final String getIP()
    {
        if ( IP != null && !IP.equals( "" ) )
        {
            return IP;

        }
        else
        {
            try
            {
                return InetAddress.getLocalHost().getHostAddress();

            }
            catch ( UnknownHostException e )
            {
                logger.error( e.toString() );

            }

        }

        return "0.0.0.0";

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IGenericModel#getListeners()
     */
    public EventListenerList getListeners()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getLogDir()
     */
    public final String getLogDir()
    {
        return logDir;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getTempDownloadDir()
     */
    public final String getTempDownloadDir()
    {
        return tempDownloadDir;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUploadDirs()
     */
    public final List getUploadDirs()
    {
        return uploadDirs;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUploadSlots()
     */
    public final int getUploadSlots()
    {
        return uploadSlots;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUploadSpeed()
     */
    public final int getUploadSpeed()
    {
        return uploadSpeed;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUsedDownloadSlots()
     */
    public final int getUsedDownloadSlots()
    {
        return usedDownloadSlots;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUsedUploadSlots()
     */
    public final int getUsedUploadSlots()
    {
        return usedUploadSlots;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getUserInfo()
     */
    public final IUserInfo getUserInfo()
    {
        return userInfo;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#isActive()
     */
    public final boolean isActive()
    {
        return active;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#releaseDownloadSlot()
     */
    public final void releaseDownloadSlot()
    {
        if ( usedDownloadSlots > 0 )
        {
            usedDownloadSlots--;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#releaseUploadSlot()
     */
    public final void releaseUploadSlot()
    {
        if ( usedUploadSlots > 0 )
        {
            usedUploadSlots--;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IGenericModel#removeListener(java.util.EventListener)
     */
    public void removeListener(
        EventListener listener )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#reserveDownloadSlot()
     */
    public final synchronized boolean reserveDownloadSlot()
    {
        if ( usedDownloadSlots < downloadSlots )
        { // free download slots
            usedDownloadSlots++;

        }
        else if ( downloadSlots == 0 )
        { // unlimited download slots
            usedDownloadSlots++;

        }
        else
        {
            return false;

        }

        return true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#reserveUploadSlot()
     */
    public final synchronized boolean reserveUploadSlot()
    {
        if ( usedUploadSlots < uploadSlots )
        { // free upload slots
            usedUploadSlots++;

        }
        else if ( uploadSlots == 0 )
        { // unlimited upload slots
            usedUploadSlots++;

        }
        else
        {
            return false;

        }

        return true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setActive(boolean)
     */
    public final void setActive(
        boolean b )
    {
        active = b;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setAdvancedSettings(net.sf.javadc.config.AdvancedSettings)
     */
    public final void setAdvancedSettings(
        AdvancedSettings settings )
    {
        advancedSettings = settings;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setDownloadDir(java.lang.String)
     */
    public final void setDownloadDir(
        String dir )
    {
        downloadDir = dir;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setDownloadSlots(int)
     */
    public final void setDownloadSlots(
        int slots )
    {
        downloadSlots = slots;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setDownloadSpeed(int)
     */
    public final void setDownloadSpeed(
        int i )
    {
        downloadSpeed = i;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setGuiSettings(net.sf.javadc.config.GuiSettings)
     */
    public void setGuiSettings(
        GuiSettings guiSettings )
    {
        this.guiSettings = guiSettings;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setIP(java.lang.String)
     */
    public final void setIP(
        String string )
    {
        IP = string;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setLogDir(java.lang.String)
     */
    public final void setLogDir(
        String string )
    {
        logDir = string;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setTempDownloadDir(java.lang.String)
     */
    public final void setTempDownloadDir(
        String string )
    {
        tempDownloadDir = string;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUploadDirs(java.util.List)
     */
    public final void setUploadDirs(
        List dirs )
    {
        uploadDirs = dirs;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUploadSlots(int)
     */
    public final void setUploadSlots(
        int i )
    {
        uploadSlots = i;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUploadSpeed(int)
     */
    public final void setUploadSpeed(
        int i )
    {
        uploadSpeed = i;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUsedDownloadSlots(int)
     */
    public final void setUsedDownloadSlots(
        int i )
    {
        usedDownloadSlots = i;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUsedUploadSlots(int)
     */
    public final void setUsedUploadSlots(
        int i )
    {
        usedUploadSlots = i;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setUserInfo(net.sf.javadc.interfaces.IUserInfo)
     */
    public final void setUserInfo(
        IUserInfo userInfo )
    {
        this.userInfo = userInfo;

    }

    /**
     * @return
     */
    protected Class getListenerClass()
    {
        return null;
    }

}

/*******************************************************************************
 * $Log: Settings.java,v $ Revision 1.17 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.16 2005/09/30
 * 15:59:52 timowest updated sources and tests Revision 1.15 2005/09/14 07:11:48 timowest updated sources
 */
