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

import java.awt.Component;

import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

/**
 * Created by IntelliJ IDEA. User: interactif06 Date: 3-ago-2004 Time: 17.11.45 To change this template use File |
 * Settings | File Templates.
 */
public class MetalThemeWrapper
    implements
        Theme
{

    // --- Constant(s) ---
    // --- Data field(s) ---
    private MetalTheme theme;

    // --- Constructor(s) ---
    public MetalThemeWrapper(
        MetalTheme theme )
    {
        this.theme = theme;

    }

    /**
     * Returns the class name of the theme.
     */
    public String getClassName()
    {
        return getTheme().getClass().getName();

    }

    /**
     * Returns the name theme.
     */
    public String getName()
    {
        return getTheme().getName();

    }

    public MetalTheme getTheme()
    {
        return theme;

    }

    // --- Method(s) ---
    public void install()
    {
        MetalLookAndFeel.setCurrentTheme( getTheme() );

    }

    /**
     * Returns false.
     */
    public boolean isConfigurable()
    {
        return false;

    }

    /**
     * Returns false.
     */
    public boolean isIconTheme()
    {
        return false;

    }

    /**
     * Does nothing.
     */
    public boolean showConfigurationDialog(
        Component parent )
    {
        return false;

    }

    @Override
    public String toString()
    {
        return getName();

    }

}

/*******************************************************************************
 * $Log: MetalThemeWrapper.java,v $ Revision 1.7 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.6
 * 2005/09/14 07:11:50 timowest updated sources
 */
