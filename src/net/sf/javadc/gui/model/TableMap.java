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

// $Id: TableMap.java,v 1.11 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui.model;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * @author Jesper Nordenberg
 * @version $Revision: 1.11 $ $Date: 2005/10/02 11:42:28 $
 */
public class TableMap extends AbstractTableModel implements TableModelListener {

    /**
     * 
     */
    private static final long serialVersionUID = 8729803106165016186L;

    private final static Category logger = Logger.getLogger(TableMap.class);

    private TableModel model;

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public final Class getColumnClass(int column) {
        return model.getColumnClass(column);

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public final int getColumnCount() {
        if (model != null) {
            return model.getColumnCount();

        } else {
            return 0;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public final String getColumnName(int column) {
        return model.getColumnName(column);

    }

    /**
     * Get the underlying TableModel
     * 
     * @return
     */
    public final TableModel getModel() {
        return model;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public final int getRowCount() {
        if (model != null) {
            return model.getRowCount();

        } else {
            return 0;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int row, int column) {
        return model.getValueAt(row, column);

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int row, int column) {
        return model.isCellEditable(row, column);

    }

    /**
     * Set the underlying TableModel
     * 
     * @param model
     */
    public void setModel(TableModel model) {
        this.model = model;
        model.addTableModelListener(this);

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(Object value, int row, int column) {
        model.setValueAt(value, row, column);

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
     */
    public void tableChanged(TableModelEvent e) {

        try {
            fireTableChanged(e);

        } catch (NullPointerException ex) {
            String error = "Catched NullPointerException in tableChanged(TableModelEvent e)";
            logger.error(error);
            logger.error(ex);

        } catch (IndexOutOfBoundsException ie) {
            String error = "Catched IndexOutOfBoundsException in tableChanged(TableModelEvent e)";
            logger.error(error, ie);
        }

    }

}

/*******************************************************************************
 * $Log: TableMap.java,v $
 * Revision 1.11  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.10 2005/09/26 17:19:52 timowest updated
 * sources and tests
 * 
 * Revision 1.9 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
