/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: SortableTable.java,v 1.16 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui.model;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.sf.javadc.gui.util.TableSorter;
import net.sf.javadc.gui.util.TableToolTipHandler;

/**
 * <CODE>SortableTable</CODE> is a user interface component which uses a <CODE>JTable</CODE> component to display the
 * contents of an underlying <CODE>TableModel</CODE>
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.16 $ $Date: 2005/10/02 11:42:28 $
 */
public class SortableTable
    extends JPanel
{

    /** ********************************************************************** */

    private final class MyComponentAdapter
        extends ComponentAdapter
    {

        private final JTable      table;

        private final JScrollPane scrollPane;

        private final int[]       columnWidths;

        private MyComponentAdapter(
            JTable table,
            JScrollPane scrollPane,
            int[] columnWidths )
        {
            super();
            this.table = table;
            this.scrollPane = scrollPane;
            this.columnWidths = columnWidths;

        }

        @Override
        public void componentResized(
            ComponentEvent e )
        {
            if ( lastWidth == -1 )
            {
                int restColumn = -1;
                int rest = table.getWidth();

                int iterations = table.getColumnModel().getColumnCount();

                for ( int i = 0; i < iterations; i++ )
                {
                    TableColumn c = table.getColumnModel().getColumn( i );

                    if ( columnWidths[i] == -1 )
                    {
                        restColumn = i;

                    }
                    else
                    {
                        c.setPreferredWidth( columnWidths[i] );
                        rest -= columnWidths[i];

                    }

                }

                if ( restColumn != -1 )
                {
                    table.getColumnModel().getColumn( restColumn ).setPreferredWidth( rest );

                }

            }
            else
            {
                int delta = scrollPane.getWidth() - lastWidth;
                int sum = 0;

                int iterations = table.getColumnModel().getColumnCount();

                for ( int i = 0; i < iterations; i++ )
                {
                    if ( columnWidths[i] == -1 )
                    {
                        sum++;

                    }

                }

                for ( int i = 0; i < iterations; i++ )
                {
                    if ( columnWidths[i] == -1 )
                    {
                        TableColumn c = table.getColumnModel().getColumn( i );

                        c.setPreferredWidth( c.getPreferredWidth() + delta / sum );

                        c.setWidth( c.getPreferredWidth() + delta / sum );

                    }

                }

            }

            lastWidth = scrollPane.getWidth();

        }

    }

    private final class MyJTable
        extends JTable
    {

        /**
         * 
         */
        private static final long serialVersionUID = -7783562684320596820L;

        private MyJTable(
            TableModel arg0 )
        {
            super( arg0 );

        }

        @Override
        public String getToolTipText(
            MouseEvent e )
        {
            int row = table.rowAtPoint( e.getPoint() );

            if ( toolTipHandler == null || row < 0 )
            {
                return super.getToolTipText( e );

            }

            row = getRealRow( row );

            int column = table.convertColumnIndexToModel( table.getColumnModel().getColumnIndexAtX( e.getX() ) );

            return toolTipHandler.getToolTipText( row, column, table.getValueAt( table.rowAtPoint( e.getPoint() ),
                column ) );

        }

    }

    /**
     * 
     */
    private static final long       serialVersionUID = -600488162525055760L;

    private final int[]             columnWidths;

    private final JLabel            countLabel       = new JLabel();

    private final String            countText;

    private int                     lastWidth        = -1;

    // private ArrayList listeners = new ArrayList();
    private final EventListenerList listenerList     = new EventListenerList();

    private final TableModel        model;

    private final JScrollPane       scrollPane;

    private final TableSorter       sorter;

    private final JTable            table;

    private TableToolTipHandler     toolTipHandler;

    /** ********************************************************************** */

    /**
     * Create a SortableTable instance with the given column lengths, a SortableTableListener and a TableModel
     * 
     * @param cols
     * @param listener
     * @param m
     */
    public SortableTable(
        int[] cols,
        SortableTableListener listener,
        TableModel m )
    {
        this( cols, listener, m, null );

    }

    /**
     * Create a SortableTable instance with the given column lengths, a SortableTableListener, a TableModel and the
     * count text
     * 
     * @param cols
     * @param listener
     * @param m
     * @param _countText
     */
    public SortableTable(
        int[] cols,
        SortableTableListener listener,
        TableModel m,
        String _countText )
    {
        super( new BorderLayout() );

        if ( m == null )
        {
            throw new NullPointerException( "m was null" );
        }

        columnWidths = cols;
        model = m;

        this.countText = _countText;
        sorter = new TableSorter( model, true );

        table = new MyJTable( sorter );

        table.setColumnSelectionAllowed( false );

        table.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        // table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getTableHeader().addMouseListener( new MouseAdapter()
        {

            @Override
            public void mouseClicked(
                MouseEvent e )
            {
                int column = table.convertColumnIndexToModel( table.getColumnModel().getColumnIndexAtX( e.getX() ) );

                if ( e.getClickCount() == 1 && column != -1 )
                {
                    sorter.sortByColumn( column );

                }

            }

        } );

        table.addMouseListener( new MouseAdapter()
        {

            @Override
            public void mouseClicked(
                MouseEvent e )
            {
                if ( e.getClickCount() == 2 )
                {
                    int row = table.rowAtPoint( e.getPoint() );
                    int column = table.convertColumnIndexToModel( table.getColumnModel().getColumnIndexAtX( e.getX() ) );

                    fireCellSelected( getRealRow( row ), column );

                }

            }

            @Override
            public void mousePressed(
                MouseEvent e )
            {
                if ( e.isPopupTrigger() )
                {
                    int row = table.rowAtPoint( e.getPoint() );
                    int column = table.convertColumnIndexToModel( table.getColumnModel().getColumnIndexAtX( e.getX() ) );

                    fireShowPopup( getRealRow( row ), column, e );

                }

            }

            @Override
            public void mouseReleased(
                MouseEvent e )
            {
                if ( e.isPopupTrigger() )
                {
                    int row = table.rowAtPoint( e.getPoint() );
                    int column = table.convertColumnIndexToModel( table.getColumnModel().getColumnIndexAtX( e.getX() ) );

                    fireShowPopup( getRealRow( row ), column, e );

                }

            }

        } );

        scrollPane = new JScrollPane( table );
        add( scrollPane, BorderLayout.CENTER );

        if ( countText != null )
        {
            model.addTableModelListener( new TableModelListener()
            {

                public void tableChanged(
                    TableModelEvent e )
                {
                    countLabel.setText( model.getRowCount() + " " + countText );

                }

            } );

            // countLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
            add( countLabel, BorderLayout.NORTH );

        }

        addListener( listener );

        table.addComponentListener( new MyComponentAdapter( table, scrollPane, columnWidths ) );

        table.setShowGrid( false );
        table.setShowVerticalLines( true );

        table.setAutoResizeMode( JTable.AUTO_RESIZE_LAST_COLUMN );
        table.sizeColumnsToFit( JTable.AUTO_RESIZE_OFF );

        table.getTableHeader().setReorderingAllowed( false );
        table.setAutoCreateColumnsFromModel( false );

        toolTipHandler = new TableToolTipHandler()
        {

            public String getToolTipText(
                int row,
                int column,
                Object o )
            {
                return o != null ? o.toString() : "";

            }

        };

    }

    /**
     * Add the given SortableTableListener to the list of EventListeners
     * 
     * @param listener
     */
    public final void addListener(
        SortableTableListener listener )
    {
        // listeners.add(listener);
        listenerList.add( SortableTableListener.class, listener );

    }

    /**
     * Get the JTable instance used in this SortableTable instance
     * 
     * @return
     */
    public final JTable getTable()
    {
        return table;

    }

    /**
     * Set the TableToolTipHandler of this SortableTable instance
     * 
     * @param tth
     */
    public final void setToolTipHandler(
        TableToolTipHandler tth )
    {
        this.toolTipHandler = tth;

    }

    /**
     * Sort the SortableTable instance by the given column
     * 
     * @param column index of the column the model is to be sorted by
     * @param ascending whether the sorting is to be ascending or descending
     */
    public final void sortByColumn(
        int column,
        boolean ascending )
    {
        sorter.sortByColumn( column, ascending );

    }

    /**
     * Notify registered listeners that the cell with the given row and column index has been selected
     * 
     * @param row
     * @param column
     */
    private final void fireCellSelected(
        int row,
        int column )
    {
        SortableTableListener[] listeners = listenerList.getListeners( SortableTableListener.class );

        int[] rows = table.getSelectedRows();

        // transform th row indices
        for ( int i = 0; i < rows.length; i++ )
        {
            rows[i] = getRealRow( rows[i] );
        }

        for ( int i = 0; i < listeners.length; i++ )
        {
            listeners[i].cellSelected( row, column, rows );

        }

    }

    /**
     * Notify registered listeners that a popup for the cell with the given row and column index is shown
     * 
     * @param row
     * @param column
     * @param e
     */
    private final void fireShowPopup(
        int row,
        int column,
        MouseEvent e )
    {
        SortableTableListener[] listeners = listenerList.getListeners( SortableTableListener.class );

        int[] rows = table.getSelectedRows();

        // transform th row indices
        for ( int i = 0; i < rows.length; i++ )
        {
            rows[i] = getRealRow( rows[i] );
        }

        for ( int i = 0; i < listeners.length; i++ )
        {
            listeners[i].showPopupClicked( row, column, e, rows );

        }

    }

    /**
     * Get the real row index of the given index
     * 
     * @param row
     * @return
     */
    private final int getRealRow(
        int row )
    {
        return sorter.getRealRow( row );

    }

}

/*******************************************************************************
 * $Log: SortableTable.java,v $ Revision 1.16 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.15
 * 2005/09/26 17:19:52 timowest updated sources and tests Revision 1.14 2005/09/15 17:32:29 timowest added null checks
 * Revision 1.13 2005/09/14 07:11:49 timowest updated sources
 */
