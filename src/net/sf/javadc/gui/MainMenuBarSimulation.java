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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.sf.javadc.config.GuiSettings;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.themes.ThemeManager;

/**
 * @author Timo Westk�mper To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MainMenuBarSimulation
{
    private class MySettings
        extends Settings
    {

        private GuiSettings guiSettings = new GuiSettings();

        public MySettings()
        {
            guiSettings.setLookAndFeel( "Plastic XP" );
            guiSettings.setPlasticTabStyle( "normal" );
            guiSettings.setTabIconsEnabled( true );
            guiSettings.setTheme( "Dark Star" );
        }

        @Override
        public GuiSettings getGuiSettings()
        {
            return guiSettings;

        }
    }

    private JFrame       frame;

    private JMenuBar     menuBar;

    private ISettings    settings     = new MySettings();

    private ThemeManager themeManager = new ThemeManager( settings );

    public static void main(
        String[] args )
    {
        new MainMenuBarSimulation();

    }

    public MainMenuBarSimulation()
    {
        frame = new ExtendedFrame();
        menuBar = new MainMenuBar( (ActionListener) frame, themeManager, settings );

        frame.setLocation( 100, 100 );
        frame.setSize( 400, 300 );

        frame.setJMenuBar( menuBar );
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

class ExtendedFrame
    extends JFrame
    implements
        ActionListener
{

    /**
     * 
     */
    private static final long   serialVersionUID = 3514345769694408383L;

    private final static String CLOSING_STRING   = "Close";

    public ExtendedFrame()
    {
        super( "MainMenuBar Simulation" );

        addWindowListener( new WindowAdapter()
        {

            @Override
            public void windowClosing(
                WindowEvent e )
            {
                closeApplication();

            }

        } );

    }

    public final void actionPerformed(
        ActionEvent e )
    {
        JMenuItem menuClicked = (JMenuItem) e.getSource();
        final String menuText = menuClicked.getText();

        if ( menuText == CLOSING_STRING )
        {
            closeApplication();

        }
        else
        {
            System.out.println( "MenuItem " + menuText + " was clicked." );

        }

    }

    private final void closeApplication()
    {
        System.out.println( "Closing application." );
        System.exit( 0 );

    }

}

/*******************************************************************************
 * $Log: MainMenuBarSimulation.java,v $ Revision 1.11 2005/10/02 11:42:28 timowest updated sources and tests Revision
 * 1.10 2005/09/26 17:53:13 timowest added null checks Revision 1.9 2005/09/14 07:11:49 timowest updated sources
 */
