/*
 * Copyright (C) 2004 Marco Bazzoni This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.themes;

import java.awt.Dimension;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

import net.sf.javadc.config.GuiSettings;
import net.sf.javadc.config.GuiSettingsFactory;
import net.sf.javadc.interfaces.ISettings;

import org.picocontainer.Startable;

import com.jgoodies.plaf.FontSizeHints;
import com.jgoodies.plaf.LookUtils;
import com.jgoodies.plaf.Options;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticTheme;

/**
 * Created by IntelliJ IDEA. User: interactif06 Date: 3-ago-2004 Time: 16.10.52 To change this template use File |
 * Settings | File Templates.
 */
public class ThemeManager
    implements
        Startable
{

    /**
     * Compares {@link Theme}objects by name.
     */
    public class ThemeComparator
        implements
            Comparator
    {

        public int compare(
            Object o1,
            Object o2 )
        {
            return ((Theme) o1).getName().compareToIgnoreCase( ((Theme) o2).getName() );

        }

    }

    private static class MyTheme
        extends MetalThemeWrapper
    {

        public MyTheme(
            PlasticTheme theme )
        {
            super( theme );

        }

        @Override
        public void install()
        {
            PlasticLookAndFeel.setMyCurrentTheme( (PlasticTheme) getTheme() );
        }
    }

    private List      themes = new ArrayList();

    private ISettings settings;

    /**
     * Returns true, if a plugin with className is installed.
     */
    public static boolean isInstalled(
        String className )
    {
        UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();

        if ( info != null )
        {
            for ( int i = 0; i < info.length; i++ )
            {
                if ( info[i].getClassName().equals( className ) )
                {
                    return true;

                }

            }

        }

        return false;

    }

    /**
     * Create a new ThemeManager instance
     * 
     * @param settings
     */
    public ThemeManager(
        ISettings settings )
    {
        if ( settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }

        this.settings = settings;

    }

    /**
     * @param theme
     */
    public void addTheme(
        Theme theme )
    {
        themes.add( theme );

    }

    /**
     * @param lookAndFeelName
     */
    public void configureUI(
        String lookAndFeelName )
    {
        UIManager.LookAndFeelInfo[] lafs = getInstalledLookAndFeels();
        for ( int i = 0; i < lafs.length; i++ )
        {
            UIManager.LookAndFeelInfo laf = lafs[i];
            if ( laf.getName().equalsIgnoreCase( lookAndFeelName ) )
            {
                // Swing Settings
                if ( laf.getClassName().indexOf( "Plastic" ) != -1 )
                {
                    PlasticLookAndFeel.setMyCurrentTheme( (PlasticTheme) getThemeByName( settings.getGuiSettings()
                        .getTheme() ) );
                    PlasticLookAndFeel.setTabStyle( settings.getGuiSettings().getPlasticTabStyle() );
                    PlasticLookAndFeel.setHighContrastFocusColorsEnabled( settings.getGuiSettings()
                        .isPlasticHighContrastFocusEnabled() );
                }
                else if ( laf.getClassName().indexOf( "MetalLookAndFeel" ) != -1 )
                {
                    MetalLookAndFeel.setCurrentTheme( new DefaultMetalTheme() );
                }
                try
                {
                    UIManager.setLookAndFeel( laf.getClassName() );
                    settings.getGuiSettings().setLookAndFeel( lookAndFeelName );
                }
                catch ( Exception e )
                {
                    System.out.println( "Can't change L&F: " + e );
                }
                break;
            }
        }
    }

    /**
     * Returns an array of all installed and supported look and feels.
     */
    public UIManager.LookAndFeelInfo[] getInstalledLookAndFeels()
    {
        HashSet classNames = new HashSet();

        LinkedList lafs = new LinkedList();
        UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();

        if ( info != null )
        {
            for ( int i = 0; i < info.length; i++ )
            {
                if ( classNames.contains( info[i].getClassName() ) )
                {
                    // work around for lafs that were installed twice
                    continue;

                }

                try
                {
                    Class c = ClassLoader.getSystemClassLoader().loadClass( info[i].getClassName() );
                    Object laf = c.newInstance();
                    Method m = c.getMethod( "isSupportedLookAndFeel", (Class[]) null );
                    Object supported = m.invoke( laf, (Object[]) null );

                    if ( ((Boolean) supported).booleanValue() )
                    {
                        lafs.add( info[i] );
                        classNames.add( info[i].getClassName() );

                    }

                }
                catch ( Exception e )
                {

                }

            }

        }

        return (UIManager.LookAndFeelInfo[]) lafs.toArray( new UIManager.LookAndFeelInfo[0] );

    }

    /**
     * @return
     */
    public String[] getLafsNames()
    {
        UIManager.LookAndFeelInfo[] lookAndFeelInfos = getInstalledLookAndFeels();
        String[] names = new String[lookAndFeelInfos.length];

        for ( int i = 0; i < lookAndFeelInfos.length; i++ )
        {
            UIManager.LookAndFeelInfo lookAndFeelInfo = lookAndFeelInfos[i];

            names[i] = lookAndFeelInfo.getName();

        }

        return names;

    }

    /**
     * @param themeName
     * @return
     */
    public MetalTheme getThemeByName(
        String themeName )
    {
        for ( Iterator it = themes.iterator(); it.hasNext(); )
        {
            Theme theme = (Theme) it.next();

            if ( theme.getName().equals( themeName ) )
            {
                return ((MyTheme) theme).getTheme();

            }

        }

        return null;

    }

    /**
     * @return
     */
    public String[] getThemeNames()
    {
        Theme[] t = getThemes();
        String[] names = new String[t.length];
        for ( int i = 0; i < t.length; i++ )
        {
            names[i] = t[i].getName();
        }

        return names;
    }

    /**
     * Returns a sorted array of all available themes.
     */
    public Theme[] getThemes()
    {
        Theme[] array = (Theme[]) themes.toArray( new Theme[0] );

        Arrays.sort( array, new ThemeComparator() );

        return array;

    }

    /**
     * Makes the default themes available.
     */
    public void initialize()
    {
        GuiSettings guiSettings;

        // modifies the settings only, if none have been specified before
        if ( settings.getGuiSettings() == null )
        {
            guiSettings = GuiSettingsFactory.createGuiSettings( GuiSettingsFactory.DEFAULT );
            settings.setGuiSettings( guiSettings );
            // settings.getGuiSettings().setSelectedTheme((PlasticTheme)
            // getThemeByName("com.jgoodies.plaf.plastic.theme.SkyBluerTahoma"));
        }
        else
        {
            guiSettings = settings.getGuiSettings();
        }

        UIManager.put( "ClassLoader", LookUtils.class.getClassLoader() );
        installLookAndFeels();
        setUIDefaults();
        configureUI( settings.getGuiSettings().getLookAndFeel() );
    }

    /**
     * Install the look and feel instances of the system
     */
    public void installLookAndFeels()
    {
        // install look & feels
        UIManager.installLookAndFeel( "Kunststoff", "com.incors.plaf.kunststoff.KunststoffLookAndFeel" );

        UIManager.installLookAndFeel( "Extended Windows", "com.jgoodies.plaf.windows.ExtWindowsLookAndFeel" );
        UIManager.installLookAndFeel( "Plastic", "com.jgoodies.plaf.plastic.PlasticLookAndFeel" );
        UIManager.installLookAndFeel( "Plastic 3D", "com.jgoodies.plaf.plastic.Plastic3DLookAndFeel" );
        UIManager.installLookAndFeel( "Plastic XP", "com.jgoodies.plaf.plastic.PlasticXPLookAndFeel" );

        List plasticThemes = new ArrayList( PlasticLookAndFeel.getInstalledThemes() );

        // add some custom themes
        plasticThemes.add( new DarkTheme() );

        // install themes
        for ( Iterator it = plasticThemes.iterator(); it.hasNext(); )
        {
            Object theme = it.next();

            if ( theme instanceof PlasticTheme )
            {
                themes.add( new MyTheme( (PlasticTheme) theme ) );

            }

        }

    }

    /**
     * @return
     */
    public boolean isLafSelected()
    {
        // todo
        return false;

    }

    /**
     * Set tbe UI defaults
     */
    public void setUIDefaults()
    {
        Options.setDefaultIconSize( new Dimension( 18, 18 ) );

        // Set font options
        UIManager.put( Options.USE_SYSTEM_FONTS_APP_KEY, settings.getGuiSettings().isUseSystemFonts() );

        // TODO : fix it in GuiSettings (Problem : Serialization)

        Options.setGlobalFontSizeHints( FontSizeHints.MIXED );

        Options.setUseNarrowButtons( settings.getGuiSettings().isUseNarrowButtons() );

        // Global options
        Options.setTabIconsEnabled( settings.getGuiSettings().isTabIconsEnabled() );

        UIManager.put( Options.POPUP_DROP_SHADOW_ENABLED_KEY, settings.getGuiSettings().isPopupDropShadowEnabled() );

        // Work around caching in MetalRadioButtonUI
        JRadioButton radio = new JRadioButton();
        radio.getUI().uninstallUI( radio );
        JCheckBox checkBox = new JCheckBox();
        checkBox.getUI().uninstallUI( checkBox );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public void start()
    {
        initialize();

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public void stop()
    {

        // do nothing
    }

}

/*******************************************************************************
 * $Log: ThemeManager.java,v $ Revision 1.12 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.11
 * 2005/09/14 07:11:50 timowest updated sources
 */
