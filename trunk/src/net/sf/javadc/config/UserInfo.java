/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: UserInfo.java,v 1.12 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

import net.sf.javadc.interfaces.IUserInfo;

/**
 * <CODE>UserInfo</CODE> represents general user information for users and remote clients
 * 
 * @author timowest
 */
public class UserInfo
    implements
        IUserInfo
{
    private String description;
    private String email;
    private String nick;
    private String speed;
    private String tag;

    private int    port;
    private long   sharedSize = 0;
    private byte   speedCode;

    /**
     * Create a UserInfo instance with empty parameters
     */
    public UserInfo()
    {
        description = email = nick = speed = tag = "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        Object obj )
    {
        if ( obj instanceof IUserInfo )
        {
            IUserInfo user = (IUserInfo) obj;
            return getNick().equals( user.getNick() );
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getDescription()
     */
    public String getDescription()
    {
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getEmail()
     */
    public String getEmail()
    {
        return email;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getNick()
     */
    public String getNick()
    {
        return nick;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getPort()
     */
    public int getPort()
    {
        return port;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getSharedSize()
     */
    public long getSharedSize()
    {
        return sharedSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getSpeed()
     */
    public String getSpeed()
    {
        return speed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getSpeedCode()
     */
    public byte getSpeedCode()
    {
        return speedCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#getTag()
     */
    public String getTag()
    {
        return tag;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return getNick().hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setDescription(java.lang.String)
     */
    public void setDescription(
        String string )
    {
        description = string;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setEmail(java.lang.String)
     */
    public void setEmail(
        String string )
    {
        email = string;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setNick(java.lang.String)
     */
    public void setNick(
        String string )
    {
        // Timo : 20.05.2004
        // filters spaces and dollar signs out
        nick = string.replace( ' ', '_' ).replace( '$', '_' );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setPort(int)
     */
    public void setPort(
        int i )
    {
        port = i;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setSharedSize(long)
     */
    public void setSharedSize(
        long l )
    {
        sharedSize = l;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setSpeed(java.lang.String)
     */
    public void setSpeed(
        String string )
    {
        speed = string;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setSpeedCode(byte)
     */
    public void setSpeedCode(
        byte code )
    {
        speedCode = code;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IUserInfo#setTag(java.lang.String)
     */
    public void setTag(
        String string )
    {
        tag = string;
    }
}
