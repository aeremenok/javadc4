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

import java.util.List;

/**
 * <CODE>IHubFavoritesLoader</CODE> is the interface for <CODE>HubFavoritesLoader</CODE>, which loads the list of
 * favorite hubs from the XML serialized version at application startup and serializes them back at application stop
 * 
 * @author Timo Westk�mper
 */
public interface IHubFavoritesLoader
    extends
        IObject
{
    /**
     * Load the favorite hubs from the serialized XML version
     * 
     * @return
     */
    public abstract List load();

    /**
     * Serialize the given list of favorite hubs into an XML file
     * 
     * @param hubs
     */
    public abstract void save(
        List hubInfos );

}