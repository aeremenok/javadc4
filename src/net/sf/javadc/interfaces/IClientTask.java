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
 * <CODE>IClientTask</CODE> represents the abstract interface implemented by the different task handler implementations
 * of the Client <CODE>Connection</CODE>
 * 
 * @author tw70794
 */
public interface IClientTask
    extends
        ITask
{
    /**
     * Return the Client Connection currently mapped to this Task instance
     * 
     * @return
     */
    public IConnection getClientConnection();

    /**
     * Return the command data currently assigned to this Task instance
     * 
     * @return
     */
    public String getCmdData();

    /**
     * Return a list of Exceptions which have been thrown in this IClientTask instance
     * 
     * @return
     */
    public List getExceptions();

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.Task#runTask()
     */
    public void runTask();

    /**
     * Set the Client Connection attribute of this Task instance
     * 
     * @param connection
     */
    public void setClientConnection(
        IConnection connection );

    /**
     * Set the command data attribute of this Task instance
     * 
     * @param string
     */
    public void setCmdData(
        String string );

}