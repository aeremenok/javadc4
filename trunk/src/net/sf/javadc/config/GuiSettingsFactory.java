/*
 * Copyright (C) 2004 Timo Westkämper
 *
 * Copyright (C) 2004 Marco Bazzoni
 *
 * This program is free software;      you can redistribute it and/or modify it
 * under the terms of the   GNU General Public License as published by the Free
 * Software Foundation;    either version 2 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;   without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE.   See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

//$Id: GuiSettingsFactory.java,v 1.14 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

import com.jgoodies.plaf.plastic.PlasticLookAndFeel;

/**
 * <CODE>GuiSettingsFactory</CODE> represents a Factory Method implementation
 * to create pre populated instances of <CODE>GuiSettings</CODE>
 * 
 * @author Timo Westk�mper
 */
public class GuiSettingsFactory {

    /**
     * 
     */
    public static int DEFAULT = 1;

    /**
     * Create a GuiSettings instance which is populated with the values from the
     * profile with the given index
     * 
     * @param selection
     *            index of the profile to be used for the population
     * @return
     */
    public static GuiSettings createGuiSettings(int selection) {
        GuiSettings settings = null;

        if (selection == DEFAULT) {
            settings = createDefault();

        } else {
            // ?

        }

        return settings;

    }

    /**
     * Return a GuiSettings instance which is populated with default values
     * 
     * @return
     */
    private static GuiSettings createDefault() {
        GuiSettings settings = new GuiSettings();

        settings.setLookAndFeel("Plastic XP");
        settings.setTheme("Sky Bluer - Tahoma");
        settings.setUseSystemFonts(Boolean.TRUE);
        settings.setUseNarrowButtons(false);
        settings.setTabIconsEnabled(false);
        settings.setPlasticTabStyle(PlasticLookAndFeel.TAB_STYLE_METAL_VALUE);
        settings.setPlasticHighContrastFocusEnabled(true);
        settings.setPopupDropShadowEnabled(Boolean.TRUE);

        return settings;
    }

}

/*******************************************************************************
 * $Log: GuiSettingsFactory.java,v $
 * Revision 1.14  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.13 2005/09/30 15:59:52 timowest
 * updated sources and tests
 * 
 * Revision 1.12 2005/09/14 07:11:48 timowest updated sources
 * 
 * 
 * 
 */
