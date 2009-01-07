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

// $Id: ISettings.java,v 1.10 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.interfaces;

import java.util.List;

import net.sf.javadc.config.AdvancedSettings;
import net.sf.javadc.config.GuiSettings;

/**
 * <CODE>ISettings</CODE> represents the core application settings, which are
 * serialized into an XML configuration file
 * 
 * @author Jesper Nordenberg
 * @author Timo Westk�mper
 * @version $Revision: 1.10 $ $Date: 2005/10/02 11:42:27 $
 */
public interface ISettings extends IGenericModel {

    /**
     * Get the AdvancedSettings
     * 
     * @return
     */
    public AdvancedSettings getAdvancedSettings();

    /**
     * Get the download directory
     * 
     * @return
     */
    public String getDownloadDir();

    /**
     * Get the total amount of download slots
     * 
     * @return
     */
    public int getDownloadSlots();

    /**
     * Get the download speed
     * 
     * @return
     */
    public int getDownloadSpeed();

    /**
     * Get the amount of free download slots
     * 
     * @return
     */
    public int getFreeDownloadSlotCount();

    /**
     * Get the amount of free upload slots
     * 
     * @return
     */
    public int getFreeUploadSlotCount();

    /**
     * Get the IP address of the local Client
     * 
     * @return
     */
    public String getIP();

    /**
     * Get the logging directory
     * 
     * @return
     */
    public String getLogDir();

    /**
     * Get the directory for temporary downloads
     * 
     * @return
     */
    public String getTempDownloadDir();

    /**
     * Get the list of shared directories
     * 
     * @return
     */
    public List getUploadDirs();

    /**
     * Get the amount of total upload slots
     * 
     * @return
     */
    public int getUploadSlots();

    /**
     * Get the upload speed
     * 
     * @return
     */
    public int getUploadSpeed();

    /**
     * Get the amount of used download slots
     * 
     * @return
     */
    public int getUsedDownloadSlots();

    /**
     * Get the amount of used upload slots
     * 
     * @return
     */
    public int getUsedUploadSlots();

    /**
     * Get the UserInfo instance related to this Settings instance
     * 
     * @return
     */
    public IUserInfo getUserInfo();

    /**
     * Return whether the Client is active or passive
     * 
     * @return
     */
    public boolean isActive();

    /**
     * Release a download slot
     */
    public void releaseDownloadSlot();

    /**
     * Release an upload slot
     */
    public void releaseUploadSlot();

    /**
     * Reserve a download slot
     * 
     * @return
     */
    public boolean reserveDownloadSlot();

    /**
     * Reserve an upload slot
     * 
     * @return
     */
    public boolean reserveUploadSlot();

    /**
     * Set the active mode of the local Client
     * 
     * @param b
     */
    public void setActive(boolean b);

    /**
     * Set the AdvancedSettings instance
     * 
     * @param settings
     */
    public void setAdvancedSettings(AdvancedSettings settings);

    /**
     * Set the download directory
     * 
     * @param dir
     */
    public void setDownloadDir(String dir);

    /**
     * Set the amount of total download slots
     * 
     * @param slots
     */
    public void setDownloadSlots(int slots);

    /**
     * Set the download speed
     * 
     * @param i
     */
    public void setDownloadSpeed(int i);

    /**
     * Set the IP address of the local Client
     * 
     * @param string
     */
    public void setIP(String string);

    /**
     * Set the logging directory
     * 
     * @param string
     */
    public void setLogDir(String string);

    /**
     * Set the directory for temporary downloads
     * 
     * @param string
     */
    public void setTempDownloadDir(String string);

    /**
     * Set the list of shared directories
     * 
     * @param dirs
     */
    public void setUploadDirs(List dirs);

    /**
     * Set the amount of total upload slots
     * 
     * @param i
     */
    public void setUploadSlots(int i);

    /**
     * Set the upload speed code
     * 
     * @param i
     */
    public void setUploadSpeed(int i);

    /**
     * Set the amount of used download slots
     * 
     * @param i
     */
    public void setUsedDownloadSlots(int i);

    /**
     * Set the amount of used upload slots
     * 
     * @param i
     */
    public void setUsedUploadSlots(int i);

    /**
     * Set the UserInfo instance
     * 
     * @param userInfo
     */
    public void setUserInfo(IUserInfo userInfo);

    /**
     * Get the GuiSettings instance
     * 
     * @return
     */
    public GuiSettings getGuiSettings();

    /**
     * Set the GuiSettings instance
     * 
     * @param guiSettings
     */
    public void setGuiSettings(GuiSettings guiSettings);

    /**
     * Notify the registered SettingsListeners, that the download slot count has
     * changed
     */
    public void fireDownloadSlotsChanged();

    /**
     * Notifiy the registered SettingsListeners, that the upload slot count has
     * changed
     */
    public void fireUploadSlotsChanged();

}