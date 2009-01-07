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

import java.util.EventListener;

import javax.swing.event.EventListenerList;

/**
 * <CODE>IGenericModel</CODE> is the abstract interface for <CODE>GenericModel</CODE>, an Observable implementation of
 * the Observer / Observable design pattern
 * 
 * @author Timo Westk�mper
 */
public interface IGenericModel
    extends
        IObject
{

    /**
     * Add the given EventListener to the list of EventListeners
     * 
     * @param listener
     */
    public abstract void addListener(
        EventListener listener );

    /**
     * Get the list of EventListeners
     * 
     * @return
     */
    public abstract EventListenerList getListeners();

    /**
     * Remove the given EventListener from the list of EventListeners
     * 
     * @param listener
     */
    public abstract void removeListener(
        EventListener listener );
}