/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

// $Id: SettingsLoader.java,v 1.16 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ISettingsLoader;

import org.apache.log4j.Category;

/**
 * <CODE>SettingsLoader</CODE> loads the core application settings from XML when the application containers are started
 * and saves the settings back to XML when the application is closed
 */
public class SettingsLoader
    implements
        ISettingsLoader
{

    /**
     * Factory method implementation to create instance of Settings, if the XML serialized representation is not
     * available
     */
    private final SettingsFactory settingsFactory = new SettingsFactory();

    static private final Category logger          = Category.getInstance( SettingsLoader.class );
    /**
     * 
     */
    private final String          configFileName;

    /**
     * 
     */
    private ISettings             settings;

    /**
     * Create a SettingsLoader instance which reads the Settings from the default location
     */
    public SettingsLoader()
    {
        configFileName = "config.xml";

    }

    /**
     * Create a SettingsLoader instance which reads the Settings information from the given file location
     * 
     * @param settingsFileName
     */
    public SettingsLoader(
        String _configFileName )
    {
        configFileName = _configFileName;

    }

    /** ********************************************************************** */

    /**
     * loads the core application settings from an XML configuration file
     * 
     * @return
     */
    public ISettings load()
    {
        // DEBUG
        logger.debug( "loading Settings ... " );

        // System.out.println();
        // System.out.println("loading Settings ... ");
        // System.out.println("====================================================");

        try
        {
            XMLDecoder d = new XMLDecoder( new BufferedInputStream( new FileInputStream( configFileName ) ) );

            settings = (ISettings) d.readObject();
            d.close();

        }
        catch ( Exception e )
        {
            // logger.error("Catched " + e.getClass().getName()
            // + " when trying to load Settings.");
            // logger.error(e);

            logger.error( "Caught " + e.getClass().getName(), e );

        }

        if ( settings == null )
        {
            settings = settingsFactory.createDefaultSettings();

        }

        logger.info( "Settings loaded." );

        return settings;

    }

    /**
     * saves the core application settings into an XML configuration file
     */
    public void save()
    {
        logger.debug( "saving Settings ... " );

        // System.out.println();
        // System.out.println("saving Settings ...");
        // System.out.println("====================================================");

        try
        {
            XMLEncoder e = new XMLEncoder( new BufferedOutputStream( new FileOutputStream( configFileName ) ) );

            e.writeObject( settings );
            e.close();

            logger.info( "Settings saved." );

        }
        catch ( Exception e )
        {
            // logger.error("Catched " + e.getClass().getName()
            // + " when trying to save Settings.");
            // logger.error(e);

            logger.error( "Caught " + e.getClass().getName(), e );

        }

    }

}

/*******************************************************************************
 * $Log: SettingsLoader.java,v $ Revision 1.16 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.15
 * 2005/09/30 15:59:52 timowest updated sources and tests Revision 1.14 2005/09/14 07:11:48 timowest updated sources
 */
