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

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import junit.framework.Assert;
import net.sf.javadc.config.GuiSettings;
import net.sf.javadc.config.GuiSettingsFactory;
import net.sf.javadc.interfaces.ISettings;

import org.picocontainer.Startable;

/**
 * Created by IntelliJ IDEA. User: interactif06 Date: 3-ago-2004 Time: 16.10.52 To change this template use File |
 * Settings | File Templates.
 */
public class LAFManager
    implements
        Startable
{
    private ISettings settings;

    /**
     * Create a new ThemeManager instance
     * 
     * @param settings
     */
    public LAFManager(
        ISettings settings )
    {
        Assert.assertNotNull( settings );
        this.settings = settings;
    }

    public void configureUI(
        String lookAndFeelName )
    {
        UIManager.LookAndFeelInfo[] lafs = getInstalledLookAndFeels();

        for ( LookAndFeelInfo laf : lafs )
        {
            if ( laf.getName().equalsIgnoreCase( lookAndFeelName ) )
            {
                try
                {
                    UIManager.setLookAndFeel( laf.getClassName() );
                    settings.getGuiSettings().setLookAndFeel( lookAndFeelName );
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
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
        return UIManager.getInstalledLookAndFeels();
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
        }
        else
        {
            guiSettings = settings.getGuiSettings();
        }

        setUIDefaults();
        configureUI( settings.getGuiSettings().getLookAndFeel() );
    }

    /**
     * Set tbe UI defaults
     */
    public void setUIDefaults()
    {
        // Work around caching in MetalRadioButtonUI
        // xxx ?
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
