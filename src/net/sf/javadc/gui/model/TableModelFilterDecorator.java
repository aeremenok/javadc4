/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * <CODE>TableModelFilterDecorator</CODE> is a light weight Decoratoe design pattern implementation on top of the
 * <CODE>TableModel</CODE> interface to facilitate the implementation of filtering functionality
 * 
 * @author Timo Westk�mper
 */
public class TableModelFilterDecorator
    implements
        TableModel
{
    // private final List acceptedRows = new ArrayList();
    private int[]      acceptedRows;

    private TableModel model;

    private String     filterPattern;

    private int        filterColumn = -1;

    // private TableModelListener tableModelListener = new
    // MyTableModelListener();

    /**
     * Create a TableModelFilterDecorator instance which wraps the given TableModel
     * 
     * @param _model
     */
    public TableModelFilterDecorator(
        TableModel _model )
    {
        if ( _model == null )
        {
            throw new NullPointerException( "_model was null" );
        }

        model = _model;
        // model.addTableModelListener(tableModelListener);
    }

    /**
     * Create a TableModelFilterDecorator with the given filter on the given column which wraps the given TableModel
     * instance
     * 
     * @param _model
     * @param _filter
     * @param _column
     */
    public TableModelFilterDecorator(
        TableModel _model,
        String _filter,
        int _column )
    {

        this( _model );
        filterPattern = _filter;
        filterColumn = _column;
    }

    /**
     * Add the given filter on the given column
     * 
     * @param column
     * @param pattern
     */
    public void addFilter(
        int column,
        String pattern )
    {
        // TODO : add filter, not only set

        filterColumn = column;
        filterPattern = pattern.toLowerCase().trim();

        updateFilterMapping();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
     */
    public void addTableModelListener(
        TableModelListener arg0 )
    {
        addTableModelListener( arg0 );

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class getColumnClass(
        int arg0 )
    {
        return model.getColumnClass( arg0 );
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount()
    {
        return model.getColumnCount();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(
        int arg0 )
    {
        return model.getColumnName( arg0 );
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount()
    {

        if ( filterColumn == -1 )
        { // filtering disabled
            return model.getRowCount();

        }
        else
        { // filtering enabled
            return acceptedRows != null ? acceptedRows.length : 0;

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(
        int row,
        int column )
    {

        if ( filterColumn == -1 )
        { // filtering disabled
            return model.getValueAt( row, column );

        }
        else
        { // filtering enabled
            // returns the real row index via obtaining the value
            // of acceptedRows at the given row index

            int rowTrans = -1;

            if ( acceptedRows != null )
            {
                rowTrans = acceptedRows[row];
            }

            return model.getValueAt( rowTrans, column );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(
        int arg0,
        int arg1 )
    {
        return false;
    }

    /**
     * Remove the filter of the given column
     * 
     * @param column
     */
    public void removeFilter(
        int column )
    {
        // TODO : removing, not only clearing

        filterColumn = column;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
     */
    public void removeTableModelListener(
        TableModelListener arg0 )
    {
        model.removeTableModelListener( arg0 );

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    public void setValueAt(
        Object arg0,
        int arg1,
        int arg2 )
    {
        // ?

    }

    /**
     * Update the filter mapping
     */
    private void updateFilterMapping()
    {
        List rowList = new ArrayList();

        // if column to filter has been specified
        if ( filterColumn != -1 )
        {
            int rows = model.getRowCount();

            for ( int i = 0; i < rows; i++ )
            {
                String value = model.getValueAt( i, filterColumn ).toString();

                if ( value.trim().toLowerCase().indexOf( filterPattern ) != -1 )
                {
                    rowList.add( new Integer( i ) );
                }
            }

            acceptedRows = new int[rowList.size()];

            for ( int i = 0; i < acceptedRows.length; i++ )
            {
                acceptedRows[i] = ((Integer) rowList.get( i )).intValue();
            }

        }
        else
        {
            acceptedRows = null;

        }

    }

    /** ********************************************************************** */

    // private class MyTableModelListener implements TableModelListener {
    //
    // public void tableChanged(TableModelEvent event) {
    // //updateFilterMapping();
    // }
    //
    // }
}

/*******************************************************************************
 * $Log: TableModelFilterDecorator.java,v $ Revision 1.11 2005/10/02 11:42:28 timowest updated sources and tests
 * Revision 1.10 2005/09/15 17:32:29 timowest added null checks Revision 1.9 2005/09/14 07:11:49 timowest updated
 * sources
 */
