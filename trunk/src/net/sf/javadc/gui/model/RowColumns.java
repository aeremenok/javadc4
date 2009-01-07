/*
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.gui.model;

/**
 * <CODE>RowColumns</CODE> is a simple object representation of a row in the
 * <CODE>RowTableModel</CODE>
 * 
 * @author Timo Westk�mper
 */

class RowColumns {

    Object row;

    Object[] columns;

    /**
     * Create a RowColumns instance with the given row and columns
     * 
     * @param row
     * @param columns
     */
    RowColumns(Object row, Object[] columns) {
        this.row = row;
        this.columns = columns;

    }

}

/*******************************************************************************
 * $Log: RowColumns.java,v $
 * Revision 1.10  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.9 2005/09/14 07:11:49 timowest updated
 * sources
 * 
 * 
 * 
 */

