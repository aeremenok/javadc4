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

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.themes.LAFManager;
import net.sf.javadc.util.FileUtils;

/**
 * <CODE>MainMenuBar</CODE> represents the main menu bar of the application and contains menu items for viewing the
 * Settings dialog, changing the Look-And-Feel, changing the active tab and exiting the application
 * 
 * @author Timo Westk�mper
 */
public class MainMenuBar
    extends JMenuBar
{
    private static final long    serialVersionUID = -8034134760555725921L;

    // private final MainFrame dcFrame;
    private final ActionListener dcFrame;
    private final LAFManager   themeManager;
    private final ISettings      settings;

    /**
     * Create a MainMenuBar instance with the given ActionListener, ThemeManager and ISettings instance
     * 
     * @param _dcFrame ActionListener to be used
     * @param _themeManager ThemeManager to be used
     * @param _settings ISettings instance to be used
     */
    public MainMenuBar(
        ActionListener _dcFrame,
        LAFManager _themeManager,
        ISettings _settings )
    {
        super();

        dcFrame = _dcFrame;
        themeManager = _themeManager;
        settings = _settings;

        // File
        JMenu fileMenu = buildFileMenu();

        add( fileMenu );

        // View
        JMenu viewMenu = buildViewMenu();

        add( viewMenu );

        // Navigation
        JMenu navigationMenu = buildNavigationMenu();

        add( navigationMenu );

    }

    /**
     * Build the File Menu
     * 
     * @return
     */
    private JMenu buildFileMenu()
    {
        JMenuItem item;

        JMenu menu = createMenu( "File", 'F' );

        item =
            createMenuItem( "Preferences", readImageIcon( "images/16/configure.png" ), 'P', KeyStroke
                .getKeyStroke( "ctrl P" ) );
        item.addActionListener( dcFrame );
        menu.add( item );

        if ( !isQuitInOSMenu() )
        {
            menu.addSeparator();
            item = createMenuItem( "Close", 'C' );
            item.addActionListener( dcFrame );
            menu.add( item );

        }

        return menu;

    }

    /**
     * Build the Navigation Menu
     * 
     * @return
     */
    private JMenu buildNavigationMenu()
    {
        JMenuItem item;

        JMenu menu = createMenu( "Navigation", 'N' );

        item =
            createMenuItem( "Hubs", readImageIcon( "images/16/network_local.png" ), 'H', KeyStroke
                .getKeyStroke( "ctrl H" ) );

        item.addActionListener( dcFrame );
        menu.add( item );

        item =
            createMenuItem( "Search", readImageIcon( "images/16/find.png" ), 'S', KeyStroke.getKeyStroke( "ctrl S" ) );
        item.addActionListener( dcFrame );
        menu.add( item );

        item =
            createMenuItem( "Monitor", readImageIcon( "images/16/list.png" ), 'M', KeyStroke.getKeyStroke( "ctrl M" ) );
        item.addActionListener( dcFrame );
        menu.add( item );

        item =
            createMenuItem( "Library", readImageIcon( "images/16/video.png" ), 'L', KeyStroke.getKeyStroke( "ctrl L" ) );
        item.addActionListener( dcFrame );
        menu.add( item );

        return menu;

    }

    /**
     * Build the View Menu
     * 
     * @return
     */
    private JMenu buildViewMenu()
    {
        JMenu menu = createMenu( "View", 'V' );

        ButtonGroup lafsGroup = new ButtonGroup();
        String[] names = themeManager.getLafsNames();

        for ( int i = 0; i < names.length; i++ )
        {
            String name = names[i];

            JRadioButtonMenuItem item =
                createRadioButtonMenuItem( name, name.equalsIgnoreCase( settings.getGuiSettings().getLookAndFeel() ) );
            item.setName( name );
            item.addActionListener( dcFrame );
            lafsGroup.add( item );
            menu.add( item );
        }

        return menu;

    }

    /**
     * Create a simple Menu with the given text and mnemonic
     * 
     * @param text
     * @param mnemonic
     * @return
     */
    private JMenu createMenu(
        String text,
        char mnemonic )
    {
        JMenu menu = new JMenu( text );
        menu.setMnemonic( mnemonic );
        return menu;
    }

    /**
     * Create a simple MenuItem with the given text and mnemonic
     * 
     * @param text
     * @param mnemonic
     * @return
     */
    private JMenuItem createMenuItem(
        String text,
        char mnemonic )
    {
        return new JMenuItem( text, mnemonic );
    }

    /**
     * Create a simple MenuItem with the given text, icon and mnemonic
     * 
     * @param text
     * @param icon
     * @param mnemonic
     * @return
     */
    private JMenuItem createMenuItem(
        String text,
        Icon icon,
        char mnemonic )
    {
        JMenuItem menuItem = new JMenuItem( text, icon );
        menuItem.setMnemonic( mnemonic );
        return menuItem;
    }

    /**
     * Create a simple MenuItem with the given text, icon, mnemonic and key access
     * 
     * @param text
     * @param icon
     * @param mnemonic
     * @param key
     * @return
     */
    private JMenuItem createMenuItem(
        String text,
        Icon icon,
        char mnemonic,
        KeyStroke key )
    {
        JMenuItem menuItem = createMenuItem( text, icon, mnemonic );
        menuItem.setAccelerator( key );
        return menuItem;
    }

    /**
     * Create a RadioButton MenuItem with the given text and selection state
     * 
     * @param text
     * @param selected
     * @return
     */
    private JRadioButtonMenuItem createRadioButtonMenuItem(
        String text,
        boolean selected )
    {
        return new JRadioButtonMenuItem( text, selected );

    }

    /**
     * Checks and answers whether the quit action has been moved to an operating system specific menu, e.g. the OS X
     * application menu.
     * 
     * @return true if the quit action is in an OS-specific menu
     */
    private boolean isQuitInOSMenu()
    {
        return false;
    }

    /**
     * Looks up and answers an icon for the specified filename suffix.
     */
    private ImageIcon readImageIcon(
        String filename )
    {
        return FileUtils.loadIcon( filename );
    }
}
