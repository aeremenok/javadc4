/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import net.sf.javadc.config.AdvancedSettings;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseHubFactory;
import net.sf.javadc.mockups.BaseHubFavoritesList;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.hub.HubList;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper TODO To change the template for this generated type comment go to Window - Preferences - Java
 *         - Code Style - Code Templates
 */
public class HubListComponentSimulation
{

    private class MyAdvancedSettings
        extends AdvancedSettings
    {
        @Override
        public String getHublistAddress()
        {
            return "http://www.neo-modus.com/PublicHubList.config";
            // return "http://fi.hublist.org/PublicHubList.config.bz2";
        }

    }

    private class MySettings
        extends BaseSettings
    {

        private AdvancedSettings advanced = new MyAdvancedSettings();

        public MySettings()
        {
            super( true );
        }

        @Override
        public AdvancedSettings getAdvancedSettings()
        {
            return advanced;
        }
    }

    private IHubFavoritesList hubFavoritesList = new BaseHubFavoritesList();

    private IHubFactory       hubFactory       = new BaseHubFactory();

    private ISettings         settings         = new MySettings();

    // concrete class as interface to use component services
    private TaskManager       taskManager      = new TaskManager();

    // concrete class as interface to use component services
    private HubList           hubList          = new HubList( settings, taskManager );

    private HubListComponent  hublistComponent = new HubListComponent( hubList, hubFavoritesList, hubFactory );

    /** ********************************************************************** */

    public static void main(
        String[] args )
    {
        new HubListComponentSimulation();

    }

    public HubListComponentSimulation()
    {

        taskManager.start();

        JFrame frame = new JFrame();

        frame.getContentPane().add( hublistComponent );

        frame.setLocation( 100, 100 );
        frame.setSize( 400, 300 );

        frame.addWindowListener( new WindowAdapter()
        {

            @Override
            public void windowClosing(
                WindowEvent e )
            {
                System.exit( 0 );

            }

        } );

        frame.setVisible( true );

        try
        {
            while ( true )
            {
                Thread.sleep( 10000 );

            }

        }
        catch ( InterruptedException e )
        {
            // logger.error(e.toString());
            System.out.println( e.toString() );

        }

    }

}

/*******************************************************************************
 * $Log: HubListComponentSimulation.java,v $ Revision 1.9 2005/10/02 11:42:28 timowest updated sources and tests
 * Revision 1.8 2005/09/14 07:11:49 timowest updated sources
 */
