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

package net.sf.javadc.interfaces;

/**
 * <CODE>ISettingsLoader</CODE> is the interface representation of <CODE>
 * SettingsLoader</CODE>, which is responsible of serializing and
 * deserializing the <CODE>Settings</CODE> instance
 * 
 * @author tw70794
 */
public interface ISettingsLoader extends IObject {

    /**
     * Load the core application settings from the serialized version
     * 
     * @return
     */
    public ISettings load();

    /**
     * Save the core application settings into a serialized version
     */
    public void save();

}