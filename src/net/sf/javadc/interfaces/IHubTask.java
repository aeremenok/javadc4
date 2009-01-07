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
 * <CODE>IHubTask</CODE> represents the abstract interface for a task that is managed via <CODE>HubTaskFactory</CODE>
 * and is related to an incoming or outgoing command in the Client-Hub communication
 * 
 * @author tw70794
 */
public interface IHubTask
    extends
        ITask
{
    /**
     * Get the command data associated with this Task
     * 
     * @return
     */
    public String getCmdData();

    /**
     * Return a list of Exceptions which have been thrown in this IHubTask instance
     * 
     * @return
     */
    public List getExceptions();

    /**
     * Get the Hub currently mapped to this Task
     * 
     * @return
     */
    public IHub getHub();

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.Task#runTask()
     */
    public void runTask();

    /**
     * Set the command data of this Task
     * 
     * @param string
     */
    public void setCmdData(
        String string );

    /**
     * Set the Hub of this task
     * 
     * @param hub
     */
    public void setHub(
        IHub hub );

}