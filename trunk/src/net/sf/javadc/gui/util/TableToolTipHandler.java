/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: TableToolTipHandler.java,v 1.10 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.gui.util;

/**
 * @author Jesper Nordenberg
 * @version $Revision: 1.10 $ $Date: 2005/10/02 11:42:27 $
 */
public interface TableToolTipHandler
{

    /**
     * Get the tooltip text for the object in the given row and column
     * 
     * @param row
     * @param column
     * @param o
     * @return
     */
    String getToolTipText(
        int row,
        int column,
        Object o );
}

/*******************************************************************************
 * $Log: TableToolTipHandler.java,v $ Revision 1.10 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.9
 * 2005/09/14 07:11:49 timowest updated sources
 */
