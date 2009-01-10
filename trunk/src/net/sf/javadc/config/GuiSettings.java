/*
 * Copyright (C) 2004 Marco Bazzoni This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

// $Id: GuiSettings.java,v 1.17 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

/**
 * <CODE>GuiSettings</CODE> represents Look-And-Feel and Theme related settings
 */
public class GuiSettings
{
    private Boolean useSystemFonts;

    // private FontSizeHints fontSizeHints;
    // private String font;
    private boolean useNarrowButtons;
    private boolean tabIconsEnabled;
    private Boolean popupDropShadowEnabled;

    // private HashMap font;
    private String  theme;

    private String  lookAndFeel;

    /**
     * Create a GuiSettings instance
     */
    public GuiSettings()
    {
        // createDefault();
    }

    /**
     * Get the look-and-feel used by javadc3
     * 
     * @return
     */
    public String getLookAndFeel()
    {
        return lookAndFeel;
    }

    /**
     * Get whether popup drop shadow is enabled
     * 
     * @return
     */
    public Boolean getPopupDropShadowEnabled()
    {
        return popupDropShadowEnabled;
    }

    /**
     * Get the theme of the look-and-feel
     * 
     * @return
     */
    public String getTheme()
    {
        return theme;
    }

    /**
     * Get whether system fonts are used
     * 
     * @return
     */
    public Boolean getUseSystemFonts()
    {
        return useSystemFonts;
    }

    /**
     * Get whether the popup drop shadow is enabled
     * 
     * @return
     */
    public Boolean isPopupDropShadowEnabled()
    {
        return popupDropShadowEnabled;
    }

    /**
     * Get whether tab icons are enabled
     * 
     * @return
     */
    public boolean isTabIconsEnabled()
    {
        return tabIconsEnabled;
    }

    /**
     * Get whether narrow buttons are used
     * 
     * @return
     */
    public boolean isUseNarrowButtons()
    {
        return useNarrowButtons;
    }

    /**
     * Get whether system fonts are used
     * 
     * @return
     */
    public Boolean isUseSystemFonts()
    {
        return useSystemFonts;
    }

    /**
     * Set the look-and-feel used by javadc3
     * 
     * @param lookAndFeel
     */
    public void setLookAndFeel(
        String lookAndFeel )
    {
        this.lookAndFeel = lookAndFeel;
    }

    /**
     * Set whether the popup drop shadow is enabled
     * 
     * @param popupDropShadowEnabled
     */
    public void setPopupDropShadowEnabled(
        Boolean popupDropShadowEnabled )
    {
        this.popupDropShadowEnabled = popupDropShadowEnabled;
    }

    /**
     * Set whether tab icons are enabled
     * 
     * @param tabIconsEnabled
     */
    public void setTabIconsEnabled(
        boolean tabIconsEnabled )
    {
        this.tabIconsEnabled = tabIconsEnabled;
    }

    /**
     * Set the theme of the look-and-feel
     * 
     * @param theme
     */
    public void setTheme(
        String theme )
    {
        this.theme = theme;
    }

    /**
     * Set whether narrow buttons are used
     * 
     * @param useNarrowButtons
     */
    public void setUseNarrowButtons(
        boolean useNarrowButtons )
    {
        this.useNarrowButtons = useNarrowButtons;
    }

    /**
     * Set whether system fonts are used
     * 
     * @param useSystemFonts
     */
    public void setUseSystemFonts(
        Boolean useSystemFonts )
    {
        this.useSystemFonts = useSystemFonts;
    }
}
