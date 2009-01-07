/*
 * @(#)TreeTableModelAdapter.java 1.2 98/10/27 Copyright 1997, 1998 by Sun Microsystems, Inc., 901 San Antonio Road,
 * Palo Alto, California, 94303, U.S.A. All rights reserved. This software is the confidential and proprietary
 * information of Sun Microsystems, Inc. ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license agreement you entered into with Sun.
 */

package net.sf.javadc.gui.model;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;

/**
 * This is a wrapper class that takes a TreeTableModel and implements the table model interface. The implementation is
 * trivial, with all of the event dispatching support provided by the superclass: the AbstractTableModel.
 * 
 * @author Philip Milne
 * @author Scott Violet
 * @version 1.2 10/27/98
 */
public class TreeTableModelAdapter
    extends AbstractTableModel
{

    /**
     * 
     */
    private static final long serialVersionUID = -7306357545621657786L;

    private JTree             tree;

    private TreeTableModel    treeTableModel;

    /**
     * Create a TreeTableModelAdapter instance which uses the given TreeTableModel and JTree instance
     * 
     * @param treeTableModel
     * @param tree
     */
    public TreeTableModelAdapter(
        TreeTableModel treeTableModel,
        JTree tree )
    {
        if ( treeTableModel == null )
        {
            throw new NullPointerException( "treeTableModel was null" );
        }

        if ( tree == null )
        {
            throw new NullPointerException( "tree was null" );
        }

        this.tree = tree;
        this.treeTableModel = treeTableModel;

        tree.addTreeExpansionListener( new TreeExpansionListener()
        {

            public void treeCollapsed(
                TreeExpansionEvent event )
            {
                fireTableDataChanged();

            }

            // Don't use fireTableRowsInserted() here; the selection model
            // would get updated twice.
            public void treeExpanded(
                TreeExpansionEvent event )
            {
                fireTableDataChanged();

            }

        } );

        // Install a TreeModelListener that can update the table when
        // tree changes. We use delayedFireTableDataChanged as we can
        // not be guaranteed the tree will have finished processing
        // the event before us.
        treeTableModel.addTreeModelListener( new TreeModelListener()
        {

            public void treeNodesChanged(
                TreeModelEvent e )
            {
                delayedFireTableDataChanged();

            }

            public void treeNodesInserted(
                TreeModelEvent e )
            {
                delayedFireTableDataChanged();

            }

            public void treeNodesRemoved(
                TreeModelEvent e )
            {
                delayedFireTableDataChanged();

            }

            public void treeStructureChanged(
                TreeModelEvent e )
            {
                delayedFireTableDataChanged();

            }

        } );

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    @Override
    public Class getColumnClass(
        int column )
    {
        return treeTableModel.getColumnClass( column );

    }

    // Wrappers, implementing TableModel interface.
    public int getColumnCount()
    {
        return treeTableModel.getColumnCount();

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(
        int column )
    {
        return treeTableModel.getColumnName( column );

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount()
    {
        return tree.getRowCount();

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
        return treeTableModel.getValueAt( nodeForRow( row ), column );

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable(
        int row,
        int column )
    {
        return treeTableModel.isCellEditable( nodeForRow( row ), column );

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
    public void setValueAt(
        Object value,
        int row,
        int column )
    {
        treeTableModel.setValueAt( value, nodeForRow( row ), column );

    }

    /**
     * Invokes fireTableDataChanged after all the pending events have been processed. SwingUtilities.invokeLater is used
     * to handle this.
     */
    protected void delayedFireTableDataChanged()
    {
        SwingUtilities.invokeLater( new Runnable()
        {

            public void run()
            {
                fireTableDataChanged();

            }

        } );

    }

    /**
     * @param row
     * @return
     */
    protected Object nodeForRow(
        int row )
    {
        TreePath treePath = tree.getPathForRow( row );

        return treePath.getLastPathComponent();

    }

}

/*******************************************************************************
 * $Log: TreeTableModelAdapter.java,v $ Revision 1.12 2005/10/02 11:42:28 timowest updated sources and tests Revision
 * 1.11 2005/09/26 17:19:52 timowest updated sources and tests Revision 1.10 2005/09/15 17:32:29 timowest added null
 * checks Revision 1.9 2005/09/14 07:11:49 timowest updated sources
 */
