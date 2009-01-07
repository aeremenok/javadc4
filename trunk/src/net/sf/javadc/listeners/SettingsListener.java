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

package net.sf.javadc.listeners;

import java.util.EventListener;

/**
 * <code>SettingsListener</code> is the listener interface for objects
 * interested in notifications from <code>Settings</code> instances
 * 
 * @author Timo Westk�mper
 */
public interface SettingsListener extends EventListener {

    /**
     * The amount of used download slots has changed
     * 
     * @param used
     *            number of used download slots
     * @param total
     *            number of total download slots
     */
    public abstract void downloadSlotsChanged(int used, int total);

    /**
     * The amount of used upload slots has changed
     * 
     * @param used
     *            number of used upload slots
     * @param total
     *            number of total upload slots
     */
    public abstract void uploadSlotsChanged(int used, int total);

}