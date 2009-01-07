/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.interfaces;

/**
 * <CODE>IUserInfo</CODE> is the interface representation of <CODE>UserInfo
 * </CODE>
 * 
 * @author tw70794
 */
public interface IUserInfo
    extends
        IObject
{
    /**
     * Get the Description
     * 
     * @return
     */
    public String getDescription();

    /**
     * Get the Email address
     * 
     * @return
     */
    public String getEmail();

    /**
     * Set the nick
     * 
     * @return
     */
    public String getNick();

    /**
     * Get the port
     * 
     * @return
     */
    public int getPort();

    /**
     * Get the shared size in bytes
     * 
     * @return
     */
    public long getSharedSize();

    /**
     * Get the connection speed
     * 
     * @return
     */
    public String getSpeed();

    /**
     * Get the connection speecd code
     * 
     * @return
     */
    public byte getSpeedCode();

    /**
     * Get the tag of the UserInfo
     * 
     * @return
     */
    public String getTag();

    /**
     * Set the description
     * 
     * @param string
     */
    public void setDescription(
        String string );

    /**
     * Set the Email address
     * 
     * @param string
     */
    public void setEmail(
        String string );

    /**
     * Set the nick
     * 
     * @param string
     */
    public void setNick(
        String string );

    /**
     * Set the port
     * 
     * @param i
     */
    public void setPort(
        int i );

    /**
     * Set the shared size in bytes
     * 
     * @param l
     */
    public void setSharedSize(
        long l );

    /**
     * Set the connection speed
     * 
     * @param string
     */
    public void setSpeed(
        String string );

    /**
     * Set the connection speed code
     * 
     * @param code
     */
    public void setSpeedCode(
        byte code );

    /**
     * Set the tag of the UserInfo
     * 
     * @param string
     */
    public void setTag(
        String string );

}