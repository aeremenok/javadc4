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
 * <CODE>ITaskManager</CODE> is the abstract interface for <CODE>TaskManager</CODE>, which provides a way to execute
 * actions in asynchronous manner
 * 
 * @author tw70794
 */
public interface ITaskManager
    extends
        ITask,
        Runnable
{

    /**
     * Add a Task instance as a single-occurring action to queue of events
     * 
     * @param task
     */
    public abstract void addEvent(
        ITask task );

    /**
     * Add a Task instance as a repeated action to the queue of tasks
     * 
     * @param task
     */
    public abstract void addTask(
        ITask task );

    /**
     * Remove the given Task from the queue of repeated actions
     * 
     * @param task
     */
    public abstract void removeTask(
        ITask task );

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public abstract void run();

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.Task#runTask()
     */
    public abstract void runTask();

}