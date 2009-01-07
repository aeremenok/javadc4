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

// $Id: ByteCellRenderer.java,v 1.18 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.gui.util;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.javadc.util.ByteConverter;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * <CODE>ByteCellRenderer</CODE> renders the long value of bytes into a more
 * readable version
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.18 $ $Date: 2005/10/02 11:42:27 $
 */
public class ByteCellRenderer extends DefaultTableCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = 3599818559293582917L;

    private final static Category logger = Logger
            .getLogger(ByteCellRenderer.class);

    /**
     * Create a ByteCellRenderer instance
     */
    public ByteCellRenderer() {

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

        JLabel result = (JLabel) super.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);

        result.setHorizontalAlignment(JLabel.RIGHT);

        // value is null
        if (value == null) {
            logger.error("Value was null");
            setText("");

            // value is of invalid type
        } else if (!(value instanceof Long)) {
            throw new RuntimeException("Encountered unexpected instance of "
                    + value.getClass().getName());

            // value is valid
        } else {
            try {
                setText(ByteConverter.byteToShortString(((Long) value)
                        .longValue())
                        + " ");

            } catch (NullPointerException e) {
                String error = "Caught NullPointerException in "
                        + "ByteCellRenderer.getTableCellRendereComponent(...)";
                logger.error(error, e);
                // logger.error(e);

            }
        }

        return this;

    }

}

/*******************************************************************************
 * $Log: ByteCellRenderer.java,v $
 * Revision 1.18  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.17 2005/09/30 15:59:54 timowest
 * updated sources and tests
 * 
 * Revision 1.16 2005/09/26 17:19:53 timowest updated sources and tests
 * 
 * Revision 1.15 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.14 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
