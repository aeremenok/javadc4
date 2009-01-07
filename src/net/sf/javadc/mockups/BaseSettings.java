/*
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.mockups;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.swing.event.EventListenerList;

import net.sf.javadc.config.AdvancedSettings;
import net.sf.javadc.config.GuiSettings;
import net.sf.javadc.config.UserInfo;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IUserInfo;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class BaseSettings implements ISettings {

    private final boolean windowsStyle;

    public BaseSettings(boolean _windowsStyle) {
        windowsStyle = _windowsStyle;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getAdvancedSettings()
     */
    public AdvancedSettings getAdvancedSettings() {
        return new AdvancedSettings();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getDownloadDir()
     */
    public String getDownloadDir() {
        if (windowsStyle) {
            return "C:\\";

        } else {
            return "/temp/";

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getDownloadDirectory()
     */
    /*
     * public File getDownloadDirectory() {
     * 
     * return new File(this.getDownloadDir()); }
     */
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getDownloadSlots()
     */
    public int getDownloadSlots() {
        // TODO Auto-generated method stub
        return 0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getDownloadSpeed()
     */
    public int getDownloadSpeed() {
        // TODO Auto-generated method stub
        return 0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getFreeDownloadSlotCount()
     */
    public int getFreeDownloadSlotCount() {
        // TODO Auto-generated method stub
        return 10;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getFreeUploadSlotCount()
     */
    public int getFreeUploadSlotCount() {
        // TODO Auto-generated method stub
        return 10;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getIP()
     */
    public String getIP() {
        // TODO Auto-generated method stub
        try {
            return InetAddress.getLocalHost().getHostAddress();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getLogDir()
     */
    public String getLogDir() {
        // TODO Auto-generated method stub
        return getDownloadDir();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getTempDownloadDir()
     */
    public String getTempDownloadDir() {
        // TODO Auto-generated method stub
        return getDownloadDir();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getUploadDirs()
     */
    public List getUploadDirs() {
        // TODO Auto-generated method stub
        List list = new ArrayList();

        list.add(new String("C:\\Timo"));

        return list;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getUploadSlots()
     */
    public int getUploadSlots() {
        // TODO Auto-generated method stub
        return 0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getUploadSpeed()
     */
    public int getUploadSpeed() {
        // TODO Auto-generated method stub
        return 0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getUsedDownloadSlots()
     */
    public int getUsedDownloadSlots() {
        // TODO Auto-generated method stub
        return 0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getUsedUploadSlots()
     */
    public int getUsedUploadSlots() {
        // TODO Auto-generated method stub
        return 0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#getUserInfo()
     */
    public IUserInfo getUserInfo() {
        // TODO Auto-generated method stub
        return new UserInfo();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#isActive()
     */
    public boolean isActive() {
        // TODO Auto-generated method stub
        return false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#releaseDownloadSlot()
     */
    public void releaseDownloadSlot() {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#releaseUploadSlot()
     */
    public void releaseUploadSlot() {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#reserveDownloadSlot()
     */
    public boolean reserveDownloadSlot() {
        // TODO Auto-generated method stub
        // return false;
        return true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#reserveUploadSlot()
     */
    public boolean reserveUploadSlot() {
        // TODO Auto-generated method stub
        // return false;
        return true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setActive(boolean)
     */
    public void setActive(boolean b) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setAdvancedSettings(net.sf.javadc.config.AdvancedSettings)
     */
    public void setAdvancedSettings(AdvancedSettings settings) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setDownloadDir(java.lang.String)
     */
    public void setDownloadDir(String dir) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setDownloadSlots(int)
     */
    public void setDownloadSlots(int slots) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setDownloadSpeed(int)
     */
    public void setDownloadSpeed(int i) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setIP(java.lang.String)
     */
    public void setIP(String string) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setLogDir(java.lang.String)
     */
    public void setLogDir(String string) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setTempDownloadDir(java.lang.String)
     */
    public void setTempDownloadDir(String string) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setUploadDirs(java.util.List)
     */
    public void setUploadDirs(List dirs) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setUploadSlots(int)
     */
    public void setUploadSlots(int i) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setUploadSpeed(int)
     */
    public void setUploadSpeed(int i) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setUsedDownloadSlots(int)
     */
    public void setUsedDownloadSlots(int i) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setUsedUploadSlots(int)
     */
    public void setUsedUploadSlots(int i) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.ISettings#setUserInfo(net.sf.javadc.config.IUserInfo)
     */
    public void setUserInfo(IUserInfo userInfo) {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#getGuiSettings()
     */
    public GuiSettings getGuiSettings() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#setGuiSettings(net.sf.javadc.config.GuiSettings)
     */
    public void setGuiSettings(GuiSettings guiSettings) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IGenericModel#removeListener(java.util.EventListener)
     */
    public void removeListener(EventListener listener) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IGenericModel#addListener(java.util.EventListener)
     */
    public void addListener(EventListener listener) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IGenericModel#getListeners()
     */
    public EventListenerList getListeners() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#fireDownloadSlotsChanged()
     */
    public void fireDownloadSlotsChanged() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ISettings#fireUploadSlotsChanged()
     */
    public void fireUploadSlotsChanged() {
        // TODO Auto-generated method stub

    }

}