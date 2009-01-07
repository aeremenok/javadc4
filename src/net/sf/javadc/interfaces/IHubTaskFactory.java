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
 * <CODE>IHubTaskFactory</CODE> represents the abstract interface used by the <CODE>HubTaskFactory</CODE> to create a
 * pooled factory of <CODE>IHubTask</CODE> instances
 * 
 * @author Timo Westk�mper
 */
public interface IHubTaskFactory
    extends
        IObject
{
    /**
     * Borrow the IHubTask instance with the given name
     * 
     * @param key name of the IHubTask instance to be borrowed
     * @return
     * @throws Exception
     */
    public abstract Object borrowObject(
        Object key );

    /**
     * Return the HubTask instance with the given name
     * 
     * @param key name of the IHubTask instance to be returned
     * @param obj IHubTask instance to be returned
     * @throws Exception
     */
    public abstract void returnObject(
        Object key,
        Object obj );

}