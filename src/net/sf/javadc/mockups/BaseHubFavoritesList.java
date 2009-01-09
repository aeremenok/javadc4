/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.mockups;

import java.util.EventListener;
import java.util.List;

import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.IHubInfo;

/**
 * @author Timo Westk�mper To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BaseHubFavoritesList
    implements
        IHubFavoritesList
{

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#addHubInfo(net.sf.javadc.interfaces.IHubInfo)
     */
    public void addHubInfo(
        IHubInfo hubInfo )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     *  
     * @see net.sf.javadc.interfaces.IHubFavoritesList#addListener(java.util.EventListener)
     */
    public void addListener(
        EventListener listener )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#getHubInfos()
     */
    public List<IHubInfo> getHubInfos()
    {
        // TODO Auto-generated method stub
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#removeHub(net.sf.javadc.interfaces.IHubInfo)
     */
    public void removeHub(
        IHubInfo hubInfo )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#setHubInfos(java.util.List)
     */
    public void setHubInfos(
        List list )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public void start()
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public void stop()
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFavoritesList#update()
     */
    public void update()
    {

        // TODO Auto-generated method stub
    }

}