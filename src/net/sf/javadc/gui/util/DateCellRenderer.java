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

// $Id$
package net.sf.javadc.gui.util;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * <CODE>DateCellRenderer</CODE> renders a <CODE>Date</CODE> instance via
 * the given <CODE>DateFormat</CODE> object
 * 
 * @author Jesper Nordenberg
 * @version $Revision$ $Date$
 */
public class DateCellRenderer extends DefaultTableCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = 8410273509725137423L;

    private final DateFormat format;

    /**
     * Create a DateCellRenderer instance which uses the given DateFormat
     * 
     * @param format
     *            DateFormat to be used
     */
    public DateCellRenderer(DateFormat format) {
        this.format = format;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    public final Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column) {

        if (!(value instanceof Date)) {
            throw new RuntimeException("Encountered unexpected instance of "
                    + value.getClass().getName());

        }

        // Component result = super.getTableCellRendererComponent(table,
        // value, isSelected, hasFocus, row, column);

        String dateStr = format.format((Date) value);

        setText(dateStr);
        setIcon(null);

        return this;

    }

}

/*******************************************************************************
 * $Log$
 * Revision 1.2  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.1 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.12 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
