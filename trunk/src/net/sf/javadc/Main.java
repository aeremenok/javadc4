/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: Main.java,v 1.21 2006/05/30 14:20:36 pmoukhataev Exp $
package net.sf.javadc;

import javax.swing.JFrame;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.gui.MainFrame;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.picocontainer.MutablePicoContainer;

/**
 * <CODE>Main</CODE> represents the main application class, which instantiates the PicoContainers and starts the main
 * application loop
 * 
 * @author Timo Westk�mper $Id: Main.java,v 1.21 2006/05/30 14:20:36 pmoukhataev Exp $ $Author: pmoukhataev $
 */
public class Main
{
    private final static Category         logger = Logger.getLogger( Main.class );

    // containers for main components
    protected static MutablePicoContainer mainContainer;
    protected static MutablePicoContainer guiContainer;

    // task containers
    protected static MutablePicoContainer hubTaskContainer;
    protected static MutablePicoContainer clientTaskContainer;

    /**
     * closes the the container and exits the system
     */
    public static final void close()
    {
        logger.debug( "stopping Main container" );
        mainContainer.stop();
        logger.debug( "main Container stopped." );

        System.exit( 0 );
    }

    public static MutablePicoContainer getGuiContainer()
    {
        return guiContainer;
    }

    public static void main(
        String[] args )
    {
        try
        {
            instantiateContainers();
        }
        catch ( Exception e )
        {
            String error = "Problems occurred when trying to instantiate the container.";
            // print out the full stack trace
            logger.error( error, e );
            return;
        }

        try
        {
            runApplication();
        }
        catch ( Exception e )
        {
            String error = "Problems occurred when running the application";
            // print out the full stack trace
            logger.error( error, e );
        }
    }

    /**
     * instantiates the main container, gui container and task containers
     */
    protected static void instantiateContainers()
    {
        ContainerBuilder containerBuilder = new ContainerBuilder();

        mainContainer = containerBuilder.buildMainContainer();
        guiContainer = containerBuilder.buildGuiContainer( mainContainer );

        // containers holding the client and hub tasks
        hubTaskContainer = containerBuilder.buildHubTaskContainer( mainContainer );
        clientTaskContainer = containerBuilder.buildClientTaskContainer( mainContainer );

        // We must let the parent container know about the child containers.
        mainContainer.registerComponentInstance( "gui", guiContainer );
        mainContainer.registerComponentInstance( "hub", hubTaskContainer );
        mainContainer.registerComponentInstance( "client", clientTaskContainer );

        // garbage collection
        System.gc();
    }

    /**
     * verifies the dependencies, starts the container and enters the main loop of the excuting thread
     */
    protected static final void runApplication()
    {
        logger.debug( "verifying that the dependencies are ok." );
        mainContainer.verify();

        logger.debug( "starting the main Container, which will start the children." );
        mainContainer.start();

        // shows the main frame
        JFrame mainFrame = (JFrame) guiContainer.getComponentInstance( MainFrame.class );
        mainFrame.setVisible( true );

        logger.debug( "main Container started." );

        try
        {
            while ( true )
            {
                Thread.sleep( ConstantSettings.MAIN_THREADSLEEP_TIME );
            }
        }
        catch ( InterruptedException e )
        {
            logger.error( "Caught " + e.getClass().getName(), e );
        }
    }
}
