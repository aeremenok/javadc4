/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
 *
 * Copyright (C) 2004 Timo Westkämper
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

// $Id: SplitPane.java,v 1.14 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.gui.util;

import java.awt.Component;

import javax.swing.JSplitPane;

/**
 * <CODE>SplitPane</CODE> is an extension to <CODE>JSplitPane</CODE>
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.14 $ $Date: 2005/10/02 11:42:27 $
 */
public class SplitPane extends JSplitPane {

    /**
     * 
     */
    private static final long serialVersionUID = -5280308732689641159L;

    /**
     * Create a SplitPane instance with the given orientation
     * 
     * @param orientation
     *            orientation of the SplitPane
     * @param newLeftComponent
     *            left/top component
     * @param newRightComponent
     *            right/bottom component
     */
    public SplitPane(int orientation, Component newLeftComponent,
            Component newRightComponent) {
        super(orientation, newLeftComponent, newRightComponent);

        this.setOneTouchExpandable(true);

    }

}

/*******************************************************************************
 * $Log: SplitPane.java,v $
 * Revision 1.14  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.13 2005/09/25 16:40:58 timowest updated
 * sources and tests
 * 
 * Revision 1.12 2005/09/15 17:32:29 timowest added null checks
 * 
 * Revision 1.11 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
