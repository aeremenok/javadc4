/*
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

//$Id: UserInfoAdapter.java,v 1.12 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

import net.sf.javadc.interfaces.IUserInfo;

/**
 * <CODE>UserInfoAdapter</CODE> represents an Adapter to the serialized
 * UserInfo instance
 * 
 * @author Timo Westk�mper
 */
public class UserInfoAdapter implements IUserInfo {

    /**
     * 
     */
    private final IUserInfo _userInfo;

    /**
     * Creates a UserInfoAdapter instance which wraps the given UserInfo
     * instance
     * 
     * @param userInfo
     *            IUserInfo instance to be wrapped
     */
    public UserInfoAdapter(IUserInfo userInfo) {
        if (userInfo == null)
            throw new NullPointerException("userInfo was null.");

        _userInfo = userInfo;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        return _userInfo.equals(obj);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setTag(java.lang.String)
     */
    public final void setTag(String string) {
        _userInfo.setTag(string);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getTag()
     */
    public final String getTag() {
        return _userInfo.getTag();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getDescription()
     */
    public final String getDescription() {
        return _userInfo.getDescription();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getEmail()
     */
    public final String getEmail() {
        return _userInfo.getEmail();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getNick()
     */
    public final String getNick() {
        return _userInfo.getNick();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.IUserInfo#getPort()
     */
    public final int getPort() {
        return _userInfo.getPort();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getSharedSize()
     */
    public final long getSharedSize() {
        return _userInfo.getSharedSize();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getSpeed()
     */
    public final String getSpeed() {
        return _userInfo.getSpeed();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setDescription(java.lang.String)
     */
    public final void setDescription(String string) {
        _userInfo.setDescription(string);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setEmail(java.lang.String)
     */
    public final void setEmail(String string) {
        _userInfo.setEmail(string);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setNick(java.lang.String)
     */
    public final void setNick(String string) {
        _userInfo.setNick(string);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setPort(int)
     */
    public final void setPort(int i) {
        _userInfo.setPort(i);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setSharedSize(long)
     */
    public final void setSharedSize(long l) {
        _userInfo.setSharedSize(l);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setSpeed(java.lang.String)
     */
    public final void setSpeed(String string) {
        _userInfo.setSpeed(string);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getSpeedCode()
     */
    public final byte getSpeedCode() {
        return _userInfo.getSpeedCode();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setSpeedCode(byte)
     */
    public final void setSpeedCode(byte code) {
        _userInfo.setSpeedCode(code);

    }

    public int hashCode() {
        return _userInfo.hashCode();
    }

}

/*******************************************************************************
 * $Log: UserInfoAdapter.java,v $
 * Revision 1.12  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.11 2005/09/30 15:59:52 timowest
 * updated sources and tests
 * 
 * Revision 1.10 2005/09/14 07:11:48 timowest updated sources
 * 
 * 
 * 
 */
