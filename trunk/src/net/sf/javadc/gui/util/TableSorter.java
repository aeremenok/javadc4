/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: TableSorter.java,v 1.13 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.gui.util;

import java.util.Date;

import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import net.sf.javadc.gui.model.TableMap;

/**
 * A sorter for TableModels. The sorter has a model (conforming to TableModel) and itself implements TableModel.
 * TableSorter does not store or copy the data in the TableModel, instead it maintains an array of integers which it
 * keeps the same size as the number of rows in its model. When the model changes it notifies the sorter that something
 * has changed eg. "rowsAdded" so that its internal array of integers can be reallocated. As requests are made of the
 * sorter (like getValueAt(row, col) it redirects them to its model via the mapping array. That way the TableSorter
 * appears to hold another copy of the table with the rows in a different order. The sorting algorthm used is stable
 * which means that it does not move around rows when its comparison function returns 0 to denote that they are
 * equivalent.
 * 
 * @author Philip Milne
 * @version 1.5 12/17/97
 */
public class TableSorter
    extends TableMap
{

    /**
     * 
     */
    private static final long serialVersionUID = 5840460863390024317L;

    private int[]             indexes          = new int[0];

    private int               sortedColumn     = -1;

    private boolean           ascending        = false;

    private final boolean     readOnly;

    /**
     * Create a TableSorter
     * 
     * @param readOnly whether the Table is a readonly table
     */
    public TableSorter(
        boolean readOnly )
    {
        this.readOnly = readOnly;

    }

    /**
     * Create a TableSorter with the given TableModel
     * 
     * @param model TableModel to be used
     * @param readOnly whether the Table is a readonly table
     */
    public TableSorter(
        TableModel model,
        boolean readOnly )
    {
        setModel( model );
        this.readOnly = readOnly;

    }

    /**
     * @param row1
     * @param row2
     * @return
     */
    public final int compareRows(
        int row1,
        int row2 )
    {
        return compareRows( row1, row2, sortedColumn );

    }

    /**
     * @param row1
     * @param row2
     * @param column
     * @return
     */
    public final int compareRows(
        int row1,
        int row2,
        int column )
    {
        TableModel data = getModel();
        Class type = data.getColumnClass( column );

        // Check for nulls.
        Object o1 = data.getValueAt( row1, column );
        Object o2 = data.getValueAt( row2, column );

        // If both values are null, return 0.
        if ( o1 == null && o2 == null )
        {
            return 0;

        }
        else if ( o1 == null )
        {
            // Define null less than everything.
            return -1;

        }
        else if ( o2 == null )
        {
            return 1;

        }

        /*
         * We copy all returned values from the getValue call in case an
         * optimised model is reusing one object to return many values. The
         * Number subclasses in the JDK are immutable and so will not be used in
         * this way but other subclasses of Number might want to do this to save
         * space and avoid unnecessary heap allocation.
         */
        if ( type.getSuperclass() == java.lang.Number.class )
        {
            Number n1 = (Number) data.getValueAt( row1, column );
            double d1 = n1.doubleValue();

            Number n2 = (Number) data.getValueAt( row2, column );
            double d2 = n2.doubleValue();

            if ( d1 < d2 )
            {
                return -1;

            }
            else if ( d1 > d2 )
            {
                return 1;

            }
            else
            {
                return 0;

            }

        }
        else if ( type == java.util.Date.class )
        {
            Date d1 = (Date) data.getValueAt( row1, column );
            long n1 = d1.getTime();

            Date d2 = (Date) data.getValueAt( row2, column );
            long n2 = d2.getTime();

            if ( n1 < n2 )
            {
                return -1;

            }
            else if ( n1 > n2 )
            {
                return 1;

            }
            else
            {
                return 0;

            }

        }
        else if ( type == String.class )
        {
            String s1 = (String) data.getValueAt( row1, column );
            String s2 = (String) data.getValueAt( row2, column );
            int result = s1.compareToIgnoreCase( s2 );

            if ( result < 0 )
            {
                return -1;

            }
            else if ( result > 0 )
            {
                return 1;

            }
            else
            {
                return 0;

            }

        }
        else if ( type == Boolean.class )
        {
            Boolean bool1 = (Boolean) data.getValueAt( row1, column );
            boolean b1 = bool1.booleanValue();

            Boolean bool2 = (Boolean) data.getValueAt( row2, column );
            boolean b2 = bool2.booleanValue();

            if ( b1 == b2 )
            {
                return 0;

            }
            else if ( b1 )
            {
                // Define false < true
                return 1;

            }
            else
            {
                return -1;

            }

        }
        else
        {
            Object v1 = data.getValueAt( row1, column );
            String s1 = v1.toString();

            Object v2 = data.getValueAt( row2, column );
            String s2 = v2.toString();
            int result = s1.compareToIgnoreCase( s2 );

            if ( result < 0 )
            {
                return -1;

            }
            else if ( result > 0 )
            {
                return 1;

            }
            else
            {
                return 0;

            }

        }

    }

    /**
     * @param row
     * @return
     */
    public final int getRealRow(
        int row )
    {
        if ( sortedColumn == -1 )
        {
            return row;

        }
        else
        {
            return indexes[row];

        }

    }

    @Override
    public final Object getValueAt(
        int row,
        int column )
    {
        return getModel().getValueAt( getRealRow( row ), column );

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    @Override
    public final boolean isCellEditable(
        int row,
        int column )
    {
        return !readOnly && super.isCellEditable( row, column );

    }

    /**
     * @param row1
     * @param row2
     * @return
     */
    public final boolean rowBefore(
        int row1,
        int row2 )
    {
        if ( ascending )
        {
            return compareRows( row1, row2 ) < 0;

        }
        else
        {
            return compareRows( row1, row2 ) >= 0;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.TableMap#setModel(javax.swing.table.TableModel)
     */
    @Override
    public final void setModel(
        TableModel model )
    {
        super.setModel( model );
        updateRows();

    }

    @Override
    public final void setValueAt(
        Object value,
        int row,
        int column )
    {
        getModel().setValueAt( value, getRealRow( row ), column );

    }

    /**
     * @param column
     */
    public final void sortByColumn(
        int column )
    {
        sortByColumn( column, column != sortedColumn || !ascending );

    }

    /**
     * @param column
     * @param ascending
     */
    public final void sortByColumn(
        int column,
        boolean ascending )
    {
        if ( column < 0 || column >= getModel().getColumnCount() )
        {
            return;

        }

        sortedColumn = column;
        this.ascending = ascending;
        updateRows();

    }

    @Override
    public final void tableChanged(
        TableModelEvent e )
    {
        if ( sortedColumn == -1 )
        {
            super.tableChanged( e );

        }
        // new TableModelEvent(this, e.getFirstRow(), e.getLastRow()));
        else
        {
            if ( e.getType() == TableModelEvent.INSERT )
            {
                insertRows( e.getFirstRow(), e.getLastRow() );

            }
            else if ( e.getType() == TableModelEvent.DELETE )
            {
                deleteRows( e.getFirstRow(), e.getLastRow() );

            }
            else if ( e.getType() == TableModelEvent.UPDATE )
            {
                sort();
                super.tableChanged( new TableModelEvent( this ) );

            }

        }

    }

    /**
     * @param firstRow
     * @param lastRow
     */
    private final void deleteRows(
        int firstRow,
        int lastRow )
    {
        int minRow = Integer.MAX_VALUE;
        int maxRow = 0;
        int size = indexes.length;

        for ( int row = firstRow; row <= lastRow; row++ )
        {
            for ( int i = 0; i < size; i++ )
            {
                if ( row == indexes[i] )
                {
                    System.arraycopy( indexes, i + 1, indexes, i, indexes.length - i - 1 );

                    if ( i < minRow )
                    {
                        minRow = i;

                    }

                    if ( i > maxRow )
                    {
                        maxRow = i;

                    }

                    size--;

                    break;

                }

            }

        }

        for ( int i = 0; i < size; i++ )
        {
            if ( indexes[i] >= firstRow )
            {
                indexes[i] -= lastRow - firstRow + 1;

            }

        }

        int[] newIndexes = new int[size];

        System.arraycopy( indexes, 0, newIndexes, 0, size );
        indexes = newIndexes;
        super.tableChanged( new TableModelEvent( this, minRow, maxRow, TableModelEvent.ALL_COLUMNS,
            TableModelEvent.DELETE ) );

    }

    /**
     * @param firstRow
     * @param lastRow
     */
    private final void insertRows(
        int firstRow,
        int lastRow )
    {
        int minRow = Integer.MAX_VALUE;
        int maxRow = 0;
        int size = indexes.length;
        int[] newIndexes = new int[lastRow - firstRow + 1 + size];

        System.arraycopy( indexes, 0, newIndexes, 0, size );
        indexes = newIndexes;

        for ( int i = 0; i < size; i++ )
        {
            if ( indexes[i] >= firstRow )
            {
                indexes[i] += lastRow - firstRow + 1;

            }

        }

        for ( int row = firstRow; row <= lastRow; row++ )
        {
            int i = 0;

            while ( i < size )
            {
                if ( rowBefore( row, indexes[i] ) )
                {
                    System.arraycopy( indexes, i, indexes, i + 1, size - i );

                    if ( i < minRow )
                    {
                        minRow = i;

                    }

                    if ( i > maxRow )
                    {
                        maxRow = i;

                    }

                    indexes[i] = row;

                    break;

                }

                i++;

            }

            if ( i == size )
            {
                indexes[i] = row;

                if ( i < minRow )
                {
                    minRow = i;

                }

                if ( i > maxRow )
                {
                    maxRow = i;

                }

            }

            size++;

        }

        super.tableChanged( new TableModelEvent( this, minRow, maxRow, TableModelEvent.ALL_COLUMNS,
            TableModelEvent.INSERT ) );

    }

    /**
     * 
     */
    private final void sort()
    {
        int length = getModel().getRowCount();
        int[] a = new int[length];

        if ( length == 0 )
        {
            indexes = a;

            return;

        }

        if ( length == 1 )
        {
            a[0] = 0;
            indexes = a;

            return;

        }

        int[] b = new int[getModel().getRowCount()];

        // Optimized first step
        for ( int i = 0; i < length - 1; i += 2 )
        {
            if ( rowBefore( i, i + 1 ) )
            {
                b[i] = i;
                b[i + 1] = i + 1;

            }
            else
            {
                b[i] = i + 1;
                b[i + 1] = i;

            }

        }

        if ( (length & 1) == 1 )
        {
            b[length - 1] = length - 1;

        }

        // Merge sort the rest
        for ( int step = 2; step < length; step *= 2 )
        {
            int x = 0;
            int z = 0;
            int[] temp = a;

            a = b;
            b = temp;

            do
            {
                int maxX = x + step;
                int y = maxX;
                int maxY = Math.min( length, y + step );

                while ( true )
                {
                    if ( rowBefore( a[x], a[y] ) )
                    {
                        b[z++] = a[x++];

                        if ( x == maxX )
                        {
                            System.arraycopy( a, y, b, z, maxY - y );

                            break;

                        }

                    }
                    else
                    {
                        b[z++] = a[y++];

                        if ( y == maxY )
                        {
                            System.arraycopy( a, x, b, z, maxX - x );

                            break;

                        }

                    }

                }

                z = x = maxY;

            }
            while ( x + step < length - 1 );

            System.arraycopy( a, x, b, z, length - x );

        }

        indexes = b;

    }

    /**
     * 
     */
    private final void updateRows()
    {
        sort();
        super.tableChanged( new TableModelEvent( this ) );

    }

}

/*******************************************************************************
 * $Log: TableSorter.java,v $ Revision 1.13 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.12
 * 2005/09/25 16:40:58 timowest updated sources and tests Revision 1.11 2005/09/14 07:11:49 timowest updated sources
 */
