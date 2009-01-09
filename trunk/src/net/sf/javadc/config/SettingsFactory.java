/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

// $Id: SettingsFactory.java,v 1.8 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

import net.sf.javadc.interfaces.ISettings;

/**
 * <code>SettingsFactory</code> is used to create preconfigured Settings instances, if the Settings can't be
 * deserialized properly from the XML representation
 * 
 * @author Timo Westk�mper
 */
public class SettingsFactory
{
    /**
     * Return a default representation of a Settings instance
     * 
     * @return
     */
    public ISettings createDefaultSettings()
    {
        ISettings settings = new Settings();

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setDescription( "Place your description here." );
        userInfo.setEmail( "a@b.com" );
        userInfo.setNick( "JohnDoe" );
        userInfo.setPort( 5100 );
        settings.setUserInfo( userInfo );

        // slots
        settings.setDownloadSlots( 10 );
        settings.setUploadSlots( 10 );

        // advancedSettings
        AdvancedSettings advancedSettings = new AdvancedSettings();
        // advancedSettings
        // .setHublistAddress("http://www.neo-modus.com/PublicHubList.config");

        advancedSettings.setHublistAddress( "http://www.hublist.org/FullHubList.config.bz2" );

        settings.setAdvancedSettings( advancedSettings );

        // guiSettings
        GuiSettings guiSettings = GuiSettingsFactory.createGuiSettings( GuiSettingsFactory.DEFAULT );
        settings.setGuiSettings( guiSettings );

        return settings;
    }
}
