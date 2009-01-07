/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: TaskManager.java,v 1.18 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.javadc.interfaces.ITask;
import net.sf.javadc.interfaces.ITaskManager;

import org.apache.log4j.Category;
import org.picocontainer.Startable;

/**
 * @author Jesper Nordenberg
 * @version $Revision: 1.18 $ $Date: 2005/10/02 11:42:28 $
 */
public class TaskManager
    implements
        ITask,
        Runnable,
        ITaskManager,
        Startable
{

    private static final Category logger     = Category.getInstance( TaskManager.class.getName() );

    // private static final Category logger2 =
    // Logger.getLogger("executionTimes");

    // private boolean running = false;
    /**
     * 
     */
    private final List            tasks      = new ArrayList();

    /**
     * 
     */
    private final List            eventQueue = new ArrayList();

    /**
     * Create a new TaskManager instance
     */
    public TaskManager()
    {
    }

    /**
     * Add the given Task instance to the queue of events
     * 
     * @param task
     */
    public synchronized void addEvent(
        ITask task )
    {
        if ( task == null )
        {
            logger.warn( "task is null." );

        }
        else
        {
            eventQueue.add( task );

        }
    }

    /**
     * Add the given Task instance to the list of tasks
     * 
     * @param task
     */
    public synchronized void addTask(
        ITask task )
    {
        if ( task == null )
        {
            logger.warn( "task is null." );

        }
        else
        {
            tasks.add( task );

        }

    }

    /**
     * Remove the given Task instance from the list of tasks
     * 
     * @param task
     */
    public synchronized void removeTask(
        ITask task )
    {
        if ( task == null )
        {
            logger.warn( "task is null" );

        }
        else
        {
            tasks.remove( task );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        while ( true )
        {
            try
            {
                runTask();
            }
            catch ( Exception e )
            {
                logger.error( "Catched Exception in TaskManager.run()", e );

            }

            try
            {
                Thread.sleep( 50 );
            }
            catch ( InterruptedException e )
            {
                // ?

            }
            catch ( Exception e )
            {
                logger.error( "Catched Exception in Thread.sleep(50)", e );

            }

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.Task#runTask()
     */
    public void runTask()
    {
        // running = true;
        ITask[] r;

        synchronized ( this )
        {
            // Copy tasks list and clear it
            r = (ITask[]) eventQueue.toArray( new ITask[0] );
            eventQueue.clear();

        }

        // System.out.println("\nIterating over tasks.\n");

        for ( int i = 0; i < r.length; i++ )
        {

            // to ensure that the TaskManager thread doesn't die
            try
            {
                if ( r[i] != null )
                {
                    if ( logger.isDebugEnabled() )
                    {
                        logger.debug( "About to invoke runTask() on " + r[i].getClass().getName() );
                    }

                    PerformanceContext cont =
                        new PerformanceContext( "Execution of " + r[i].getClass().getName() + " instance " + r[i] )
                            .start();

                    if ( logger.isDebugEnabled() )
                    {
                        logger.debug( "Invocation of " + r[i].getClass().getName() + " finished." );
                    }

                    r[i].runTask();
                    cont.end();

                    // log the execution time, if it exceeded 500 msec.
                    if ( cont.getDuration() > 1000 )
                    {
                        logger.info( cont );
                    }

                }
                else
                {
                    logger.error( "Instance of class " + r[i].getClass().getName() + " was null." );

                }

            }
            catch ( Exception e )
            {
                logger.error( "Catched Exception when running task " + r[i], e );

            }

        }

        synchronized ( this )
        {
            // Copy tasks list cause a task might remove itself
            r = (ITask[]) tasks.toArray( new ITask[0] );

        }

        // System.out.println("\nIterating over events.\n");

        for ( int i = 0; i < r.length; i++ )
        {

            // to ensure that the TaskManager thread doesn't die
            try
            {
                if ( r[i] != null )
                {
                    if ( logger.isDebugEnabled() )
                    {
                        logger.debug( "About to invoke runTask() on " + r[i].getClass().getName() );
                    }

                    PerformanceContext cont =
                        new PerformanceContext( "Execution of " + r[i].getClass().getName() + " instance " + r[i] )
                            .start();

                    r[i].runTask();

                    cont.end();

                    if ( logger.isDebugEnabled() )
                    {
                        logger.debug( "Invocation of " + r[i].getClass().getName() + " finished." );
                    }

                    // log the execution time, if it exceeded 500 msec.
                    if ( cont.getDuration() > 1000 )
                    {
                        logger.info( cont );
                    }

                }
                else
                {
                    logger.error( "Instance of class " + r[i].getClass().getName() + " was null." );

                }

            }
            catch ( Exception e )
            {
                logger.error( "Catched Exception when running task" + r[i], e );

            }

        }

        // running = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public void start()
    {

        // System.out.println();
        // System.out.println("starting " + this.getClass().getName());
        // System.out.println("====================================================");

        logger.debug( "TaskManager started" );

        new Thread( this, "TaskManager" ).start();

        /*
         * final Timer taskTimer = new javax.swing.Timer(100, new
         * ActionListener() { public void actionPerformed(ActionEvent e) { if
         * (!running) runTask(); } }); taskTimer.setRepeats(true);
         * taskTimer.start();
         */
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public void stop()
    {

        logger.debug( "TaskManager stopped" );

        // System.out.println();
        // System.out.println("stopping " + this.getClass().getName());
        // System.out.println("====================================================");
    }

}

/*******************************************************************************
 * $Log: TaskManager.java,v $ Revision 1.18 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.17
 * 2005/09/26 17:19:52 timowest updated sources and tests Revision 1.16 2005/09/25 16:40:58 timowest updated sources and
 * tests Revision 1.15 2005/09/14 07:11:48 timowest updated sources
 */
