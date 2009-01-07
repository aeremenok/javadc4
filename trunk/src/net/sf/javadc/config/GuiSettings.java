/*
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

//$Id: GuiSettings.java,v 1.17 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

/**
 * <CODE>GuiSettings</CODE> represents Look-And-Feel and Theme related
 * settings
 */
public class GuiSettings {

    /**
     * 
     */
    private Boolean useSystemFonts;

    // private FontSizeHints fontSizeHints;
    // private String font;
    /**
     * 
     */
    private boolean useNarrowButtons;

    /**
     * 
     */
    private boolean tabIconsEnabled;

    /**
     * 
     */
    private Boolean popupDropShadowEnabled;

    /**
     * 
     */
    private String plasticTabStyle;

    /**
     * 
     */
    private boolean plasticHighContrastFocusEnabled;

    // private HashMap font;
    /**
     * 
     */
    private String theme;

    /**
     * 
     */
    private String lookAndFeel;

    // Instance Creation ******************************************************

    /**
     * Create a GuiSettings instance
     */
    public GuiSettings() {
        // createDefault();
    }

    // Accessors **************************************************************

    /**
     * Get whether the popup drop shadow is enabled
     * 
     * @return
     */
    public Boolean isPopupDropShadowEnabled() {
        return popupDropShadowEnabled;
    }

    /**
     * Set whether the popup drop shadow is enabled
     * 
     * @param popupDropShadowEnabled
     */
    public void setPopupDropShadowEnabled(Boolean popupDropShadowEnabled) {
        this.popupDropShadowEnabled = popupDropShadowEnabled;
    }

    /**
     * Get whether the plastic high contract focus is enabled
     * 
     * @return
     */
    public boolean isPlasticHighContrastFocusEnabled() {
        return plasticHighContrastFocusEnabled;
    }

    /**
     * Set whether the plastic hight contrast focus is enabled
     * 
     * @param plasticHighContrastFocusEnabled
     */
    public void setPlasticHighContrastFocusEnabled(
            boolean plasticHighContrastFocusEnabled) {
        this.plasticHighContrastFocusEnabled = plasticHighContrastFocusEnabled;
    }

    /**
     * Get the plastic tab style
     * 
     * @return
     */
    public String getPlasticTabStyle() {
        return plasticTabStyle;
    }

    /**
     * Set the plastic tab style
     * 
     * @param plasticTabStyle
     */
    public void setPlasticTabStyle(String plasticTabStyle) {
        this.plasticTabStyle = plasticTabStyle;
    }

    /**
     * Get whether tab icons are enabled
     * 
     * @return
     */
    public boolean isTabIconsEnabled() {
        return tabIconsEnabled;
    }

    /**
     * Set whether tab icons are enabled
     * 
     * @param tabIconsEnabled
     */
    public void setTabIconsEnabled(boolean tabIconsEnabled) {
        this.tabIconsEnabled = tabIconsEnabled;
    }

    /**
     * Get whether narrow buttons are used
     * 
     * @return
     */
    public boolean isUseNarrowButtons() {
        return useNarrowButtons;
    }

    /**
     * Set whether narrow buttons are used
     * 
     * @param useNarrowButtons
     */
    public void setUseNarrowButtons(boolean useNarrowButtons) {
        this.useNarrowButtons = useNarrowButtons;
    }

    /**
     * Get whether system fonts are used
     * 
     * @return
     */
    public Boolean isUseSystemFonts() {
        return useSystemFonts;
    }

    /**
     * Set whether system fonts are used
     * 
     * @param useSystemFonts
     */
    public void setUseSystemFonts(Boolean useSystemFonts) {
        this.useSystemFonts = useSystemFonts;
    }

    /**
     * Set the theme of the look-and-feel
     * 
     * @param theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Set the look-and-feel used by javadc3
     * 
     * @param lookAndFeel
     */
    public void setLookAndFeel(String lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
    }

    /**
     * Get whether system fonts are used
     * 
     * @return
     */
    public Boolean getUseSystemFonts() {
        return useSystemFonts;
    }

    /**
     * Get whether popup drop shadow is enabled
     * 
     * @return
     */
    public Boolean getPopupDropShadowEnabled() {
        return popupDropShadowEnabled;
    }

    /**
     * Get the theme of the look-and-feel
     * 
     * @return
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Get the look-and-feel used by javadc3
     * 
     * @return
     */
    public String getLookAndFeel() {
        return lookAndFeel;
    }

}

/*******************************************************************************
 * $Log: GuiSettings.java,v $
 * Revision 1.17  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.16 2005/09/30 15:59:52 timowest updated
 * sources and tests
 * 
 * Revision 1.15 2005/09/14 07:11:48 timowest updated sources
 * 
 * 
 * 
 */
