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

import javax.swing.plaf.ColorUIResource;

/**
 * @author tw70794 To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BlackColors implements Colors {

    // BASIC METAL COLORS (PLUS WHITE AND BLACK)
    // Primary1: titles & highlight
    private final ColorUIResource prim1 = new ColorUIResource(153, 153, 153);

    // Primary2: depressed menu
    private final ColorUIResource prim2 = new ColorUIResource(118, 118, 118);

    // Primary3: tooltip & scrollbar highlight
    private final ColorUIResource prim3 = new ColorUIResource(60, 60, 60);

    // Secondary1: base outline
    private final ColorUIResource sec1 = new ColorUIResource(0, 0, 0);

    // Secondary2: inactive tabs, table cols & section outline
    // old "darker" color (135, 145, 170)
    private final ColorUIResource sec2 = new ColorUIResource(36, 36, 36);

    // Secondary3: background color
    private final ColorUIResource sec3 = new ColorUIResource(124, 124, 124);

    // SystemText: playoptions mp3list library sharing prompt everything in
    // options
    private final ColorUIResource win1 = new ColorUIResource(255, 255, 255);

    // CUSTOMIZED METAL COMPONENT COLORS (NEEDED FOR THOSE MAPPED TO
    // WHITE/BLACK)
    // Control: background color
    // (initially mapped to Secondary2)
    private final ColorUIResource win2 = new ColorUIResource(60, 60, 60);

    // ControlHighlight: highlite around radiobuttons inputfields and whole
    // windows
    // (initially mapped to Secondary3)
    private final ColorUIResource win3 = new ColorUIResource(138, 138, 138);

    // ControlShadow: Missing
    // (mapped to Secondary1)
    // ControlDarkShadow: Missing
    // (mapped to Black)
    // ControlTextColor: TABtext - liblist - buttons
    // (initially mapped to Primary1)
    private final ColorUIResource win4 = new ColorUIResource(211, 211, 211);

    // ControlInfo: checks and little arrows on drop downs
    // (initially mapped to Secondary1)
    private final ColorUIResource win5 = new ColorUIResource(255, 255, 255);

    // WindowBackground: unspecified
    // (initially mapped to Primary3)
    private final ColorUIResource win6 = new ColorUIResource(0, 0, 0);

    // WindowTitleInactiveForeground: unspecified
    // (initially mapped to White)
    private final ColorUIResource win7 = new ColorUIResource(0, 0, 0);

    // UserTextColor: results list and blinking cursor
    // (initially mapped to Black)
    private final ColorUIResource win8 = new ColorUIResource(255, 255, 255);

    // MenuForeground:
    // (initially mapped to Primary1)
    private final ColorUIResource win9 = new ColorUIResource(200, 200, 200);

    // MenuSelectedForeground:
    // (initially mapped to Primary1)
    private final ColorUIResource win10 = new ColorUIResource(255, 255, 255);

    // DesktopColor: MDI container or desktop background
    // (initially mapped to Secondary3)
    private final ColorUIResource win11 = new ColorUIResource(204, 0, 0);

    // MenuBackground:
    // (initially mapped to Secondary3)
    private final ColorUIResource win12 = new ColorUIResource(60, 60, 60);

    /**
     * @return
     */
    public ColorUIResource getPrim1() {
        return prim1;

    }

    /**
     * @return
     */
    public ColorUIResource getPrim2() {
        return prim2;

    }

    /**
     * @return
     */
    public ColorUIResource getPrim3() {
        return prim3;

    }

    /**
     * @return
     */
    public ColorUIResource getSec1() {
        return sec1;

    }

    /**
     * @return
     */
    public ColorUIResource getSec2() {
        return sec2;

    }

    /**
     * @return
     */
    public ColorUIResource getSec3() {
        return sec3;

    }

    /**
     * @return
     */
    public ColorUIResource getWin1() {
        return win1;

    }

    /**
     * @return
     */
    public ColorUIResource getWin10() {
        return win10;

    }

    /**
     * @return
     */
    public ColorUIResource getWin11() {
        return win11;

    }

    /**
     * @return
     */
    public ColorUIResource getWin12() {
        return win12;

    }

    /**
     * @return
     */
    public ColorUIResource getWin2() {
        return win2;

    }

    /**
     * @return
     */
    public ColorUIResource getWin3() {
        return win3;

    }

    /**
     * @return
     */
    public ColorUIResource getWin4() {
        return win4;

    }

    /**
     * @return
     */
    public ColorUIResource getWin5() {
        return win5;

    }

    /**
     * @return
     */
    public ColorUIResource getWin6() {
        return win6;

    }

    /**
     * @return
     */
    public ColorUIResource getWin7() {
        return win7;

    }

    /**
     * @return
     */
    public ColorUIResource getWin8() {
        return win8;

    }

    /**
     * @return
     */
    public ColorUIResource getWin9() {
        return win9;

    }

}

/*******************************************************************************
 * $Log: BlackColors.java,v $
 * Revision 1.6  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.5 2005/09/14 07:11:50 timowest updated
 * sources
 * 
 * 
 * 
 */
