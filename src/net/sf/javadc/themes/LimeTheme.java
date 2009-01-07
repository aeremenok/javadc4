/*
 *  LimeWire - An advanced Gnutella client.
 *
 *  Copyright (C) 2001 Lime Wire LLC ( info@limewire.com)
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

package net.sf.javadc.themes;

import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

/**
 * This class defines the colors used in the application.
 */

// 2345678|012345678|012345678|012345678|012345678|012345678|012345678|012345678|
public final class LimeTheme extends DefaultMetalTheme {

    // private Colors colors = SettingsImpl.getInstance().getColors();
    private Colors colors = new BlackColors();

    // private FontUIResource controlFont; //dialog, BOLD_STYLE, 11/12
    // private FontUIResource captionFont; //dialog, BOLD, 11/12
    // private FontUIResource systemFont; //dialog, PLAIN/BOLD_STYLE, 11/12
    // private FontUIResource userFont; //dialog(input), PLAIN, 11/12
    // private FontUIResource smallFont; //dialog, PLAIN, 10
    private final FontUIResource controlFont = new FontUIResource("SansSerif",
            Font.PLAIN, 11);

    private final FontUIResource systemFont = new FontUIResource("Dialog",
            Font.PLAIN, 11);

    private final FontUIResource windowTitleFont = new FontUIResource(
            "SansSerif", Font.PLAIN, 11);

    private final FontUIResource userFont = new FontUIResource("SansSerif",
            Font.PLAIN, 11);

    private final FontUIResource smallFont = new FontUIResource("Dialog",
            Font.PLAIN, 10);

    private final FontUIResource menuFont = new FontUIResource("SansSerif",
            Font.PLAIN, 11);

    private final FontUIResource emphasisFont = new FontUIResource("SansSerif",
            Font.PLAIN, 11);

    public String getName() {
        return "LimeTheme";

    }

    protected ColorUIResource getPrimary1() {
        return colors.getPrim1();

    }

    protected ColorUIResource getPrimary2() {
        return colors.getPrim2();

    }

    protected ColorUIResource getPrimary3() {
        return colors.getPrim3();

    }

    protected ColorUIResource getSecondary1() {
        return colors.getSec1();

    }

    protected ColorUIResource getSecondary2() {
        return colors.getSec2();

    }

    protected ColorUIResource getSecondary3() {
        return colors.getSec3();

    }

    public ColorUIResource getSystemTextColor() {
        return colors.getWin1();

    }

    public ColorUIResource getControl() {
        return colors.getWin2();

    }

    public ColorUIResource getControlHighlight() {
        return colors.getWin3();

    }

    public ColorUIResource getControlTextColor() {
        return colors.getWin4();

    }

    public ColorUIResource getControlInfo() {
        return colors.getWin5();

    }

    public ColorUIResource getWindowBackground() {
        return colors.getWin6();

    }

    public ColorUIResource getWindowTitleInactiveForeground() {
        return colors.getWin7();

    }

    public ColorUIResource getUserTextColor() {
        return colors.getWin8();

    }

    public ColorUIResource getMenuForeground() {
        return colors.getWin9();

    }

    public ColorUIResource getMenuSelectedForeground() {
        return colors.getWin10();

    }

    public ColorUIResource getDesktopColor() {
        return colors.getWin11();

    }

    public ColorUIResource getMenuBackground() {
        return colors.getWin12();

    }

    public FontUIResource getControlTextFont() {
        return controlFont;

    }

    public FontUIResource getSystemTextFont() {
        return systemFont;

    }

    public FontUIResource getUserTextFont() {
        return userFont;

    }

    public FontUIResource getMenuTextFont() {
        // return controlFont;
        return menuFont;

    }

    public FontUIResource getEmphasisTextFont() {
        // return windowTitleFont;
        return emphasisFont;

    }

    public FontUIResource getSubTextFont() {
        return smallFont;

    }

    public FontUIResource getWindowTitleFont() {
        return windowTitleFont;

    }

    public Colors getColors() {
        return colors;

    }

    public void setColors(Colors colors) {
        this.colors = colors;

    }

}

/*******************************************************************************
 * $Log: LimeTheme.java,v $
 * Revision 1.6  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.5 2005/09/14 07:11:50 timowest updated
 * sources
 * 
 * 
 * 
 */
