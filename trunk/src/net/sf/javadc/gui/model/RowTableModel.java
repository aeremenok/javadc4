/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: RowTableModel.java,v 1.14 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Category;

/**
 * <CODE>RowTableModel</CODE> is an extension of the <CODE>TableModel</CODE>, which uses <CODE>RowColumns</CODE> as the
 * internal represention of a row
 * 
 * @author Timo Westk�mper
 */
public class RowTableModel
    extends AbstractTableModel
{
    private static final long     serialVersionUID = -2735540297212202988L;

    private final static Category logger           = Category.getInstance( RowTableModel.class );

    private List<RowColumns>      rows             = new ArrayList<RowColumns>();

    private final String[]        columnNames;

    /**
     * Create a RowTableModel with the given column names
     * 
     * @param columnNames array of column headers to be used
     */
    public RowTableModel(
        String[] columnNames )
    {
        if ( columnNames == null )
        {
            throw new NullPointerException( "columnNames was null" );
        }

        this.columnNames = columnNames;

    }

    /**
     * Add the given row with the given columns to the RowTableModel instance
     * 
     * @param row
     * @param columns
     */
    public final void addRow(
        Object row,
        Object[] columns )
    {
        rows.add( new RowColumns( row, columns ) );

        int index = rows.size() - 1;
        fireTableRowsInserted( index, index );

    }

    /**
     * Clear the underlying model and notify registered listeners about the update
     */
    public final void clear()
    {
        rows.clear();
        update();

    }

    /**
     * Delete the row with the given index from the RowTableModel instance
     * 
     * @param index
     */
    public final void deleteRow(
        int index )
    {
        if ( index >= 0 )
        {
            rows.remove( index );
            fireTableRowsDeleted( index, index );

        }

    }

    /**
     * Delete the given row from the RowTableModel instance
     * 
     * @param row
     */
    public final void deleteRow(
        Object row )
    {
        final int index = getRowIndex( row );

        if ( index >= 0 )
        {
            rows.remove( index );
            fireTableRowsDeleted( index, index );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    @Override
    public final Class getColumnClass(
        int columnIndex )
    {
        if ( columnIndex < 0 )
        {
            logger.error( "columnIndex was " + columnIndex );
            return null;

        }
        else if ( getRowCount() > 0 )
        {
            final Object o = getValueAt( 0, columnIndex );
            if ( o != null )
            {
                return o.getClass();
            }
            return Object.class;
        }
        else
        {
            return super.getColumnClass( columnIndex );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public final int getColumnCount()
    {
        return columnNames.length;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    @Override
    public final String getColumnName(
        int column )
    {
        return columnNames[column];

    }

    /**
     * Get the RowColumns instance with the given index
     * 
     * @param row
     * @return
     */
    public final Object getRow(
        int row )
    {
        return rows.get( row ).row;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public final int getRowCount()
    {
        return rows.size();

    }

    /**
     * Get the index of the given RowColumns instance
     * 
     * @param row
     * @return
     */
    public final int getRowIndex(
        Object row )
    {
        RowColumns[] rowcol = rows.toArray( new RowColumns[rows.size()] );

        for ( int i = 0; i < rowcol.length; i++ )
        {
            if ( rowcol[i].row == row )
            {
                return i;

            }

        }

        return -1;

    }

    /**
     * @return
     */
    public final List<Object> getRows()
    {
        int size = rows.size();
        List<Object> temprows = new ArrayList<Object>( size );

        for ( int i = 0; i < size; i++ )
        {
            temprows.add( getRow( i ) );

        }

        return temprows;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public final Object getValueAt(
        int rowIndex,
        int columnIndex )
    {
        if ( rowIndex >= 0 && columnIndex >= 0 )
        {
            try
            {
                return rows.get( rowIndex ).columns[columnIndex];
            }
            catch ( ArrayIndexOutOfBoundsException e )
            { // todo replace with checking
                logger.error( "Caught " + e.getClass().getName(), e );
            }
        }
        else
        {
            logger.error( "rowIndex was " + rowIndex + " and columnIndex was " + columnIndex );
        }

        return null;
    }

    /**
     * Notify registered listeners that the given row has been updated
     * 
     * @param row
     * @param columns
     */
    public final void rowChanged(
        Object row,
        Object[] columns )
    {
        final int index = getRowIndex( row );

        if ( index >= 0 )
        {
            rows.get( index ).columns = columns;
            fireTableRowsUpdated( index, index );
        }
    }

    /**
     * @param rows
     */
    public final void setRows(
        List<RowColumns> rows )
    {
        this.rows = rows;

        int count = rows.size();

        // Timo asks : necessary ?
        for ( int index = 0; index < count; index++ )
        {
            fireTableRowsInserted( index, index );
        }
    }

    /**
     * Notify registered listeners that the underlying model has been updated
     */
    public final void update()
    {
        fireTableDataChanged();
    }
}
