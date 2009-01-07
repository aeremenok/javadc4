/* *
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

package net.sf.javadc.themes;

import java.awt.Component;

/**
 * Created by IntelliJ IDEA. User: interactif06 Date: 3-ago-2004 Time: 16.58.00
 * To change this template use File | Settings | File Templates.
 */
public interface Theme {

    // --- Constant(s) ---

    // --- Method(s) ---

    /**
     * Installs the theme. Invoked by {@link ThemeManager}.
     */
    void install();

    /**
     * Returns a unique identifier.
     */
    String getClassName();

    /**
     * Returns the name of this theme.
     */
    String getName();

    /**
     * Returns true, if this theme provides additional configuration options.
     * 
     * @see #showConfigurationDialog(Component)
     */
    boolean isConfigurable();

    /**
     * Returns true, if this theme provides a custom set of icons.
     */
    boolean isIconTheme();

    /**
     * Show a configuration dialog.
     * 
     * @return true, if theme should be reloaded; false, otherwise
     * @see #isConfigurable()
     */
    boolean showConfigurationDialog(Component parent);

}

/*******************************************************************************
 * $Log: Theme.java,v $
 * Revision 1.7  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.6 2005/09/14 07:11:50 timowest updated
 * sources
 * 
 * 
 * 
 */
