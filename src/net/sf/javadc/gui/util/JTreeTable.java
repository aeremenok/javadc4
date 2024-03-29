/*
 * @(#)JTreeTable.java 1.2 98/10/27 Copyright 1997, 1998 by Sun Microsystems, Inc., 901 San Antonio Road, Palo Alto,
 * California, 94303, U.S.A. All rights reserved. This software is the confidential and proprietary information of Sun
 * Microsystems, Inc. ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with Sun.
 */

package net.sf.javadc.gui.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import net.sf.javadc.gui.model.TreeTableModel;
import net.sf.javadc.gui.model.TreeTableModelAdapter;

/**
 * This example shows how to create a simple JTreeTable component, by using a JTree as a renderer (and editor) for the
 * cells in a particular column in the JTable.
 * 
 * @author Philip Milne
 * @author Scott Violet
 * @version 1.2 10/27/98
 */
public class JTreeTable
    extends JTable
{
    /**
     * TreeTableCellEditor implementation. Component returned is the JTree.
     */
    public class TreeTableCellEditor
        extends AbstractCellEditor
        implements
            TableCellEditor
    {

        /**
         * 
         */
        private static final long serialVersionUID = 5754529488991321936L;

        public Object getCellEditorValue()
        {
            return null;

        }

        public Component getTableCellEditorComponent(
            JTable table,
            Object value,
            boolean isSelected,
            int r,
            int c )
        {
            return tree;

        }

        /**
         * Overridden to return false, and if the event is a mouse event it is forwarded to the tree.
         * <p>
         * The behavior for this is debatable, and should really be offered as a property. By returning false, all
         * keyboard actions are implemented in terms of the table. By returning true, the tree would get a chance to do
         * something with the keyboard events. For the most part this is ok. But for certain keys, such as left/right,
         * the tree will expand/collapse where as the table focus should really move to a different column. Page up/down
         * should also be implemented in terms of the table. By returning false this also has the added benefit that
         * clicking outside of the bounds of the tree node, but still in the tree column will select the row, whereas if
         * this returned true that wouldn't be the case.
         * </p>
         * <p>
         * By returning false we are also enforcing the policy that the tree will never be editable (at least by a key
         * sequence).
         * </p>
         */
        @Override
        public boolean isCellEditable(
            EventObject e )
        {
            if ( e instanceof MouseEvent )
            {
                for ( int counter = getColumnCount() - 1; counter >= 0; counter-- )
                {
                    if ( getColumnClass( counter ) == TreeTableModel.class )
                    {
                        MouseEvent me = (MouseEvent) e;

                        MouseEvent newME =
                            new MouseEvent( tree, me.getID(), me.getWhen(), me.getModifiers(), me.getX() -
                                getCellRect( 0, counter, true ).x, me.getY(), me.getClickCount(), me.isPopupTrigger() );

                        tree.dispatchEvent( newME );

                        break;

                    }

                }

            }

            return false;

        }

    }

    /**
     * A TreeCellRenderer that displays a JTree.
     */
    public class TreeTableCellRenderer
        extends JTree
        implements
            TableCellRenderer
    {

        /**
         * 
         */
        private static final long serialVersionUID = 2179385033700713524L;

        /** Last table/tree row asked to renderer. */
        protected int             visibleRow;

        public TreeTableCellRenderer(
            TreeModel model )
        {
            super( model );

        }

        /**
         * TreeCellRenderer method. Overridden to update the visible row.
         */
        public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column )
        {
            if ( isSelected )
            {
                setBackground( table.getSelectionBackground() );

            }
            else
            {
                setBackground( table.getBackground() );

            }

            visibleRow = row;

            return this;

        }

        /**
         * Sublcassed to translate the graphics such that the last visible row will be drawn at 0,0.
         */
        @Override
        public void paint(
            Graphics g )
        {
            g.translate( 0, -visibleRow * getRowHeight() );
            super.paint( g );

        }

        /**
         * This is overridden to set the height to match that of the JTable.
         */
        @Override
        public void setBounds(
            int x,
            int y,
            int w,
            int h )
        {
            super.setBounds( x, 0, w, JTreeTable.this.getHeight() );

        }

        /**
         * Sets the row height of the tree, and forwards the row height to the table.
         */
        @Override
        public void setRowHeight(
            int rowHeight )
        {
            if ( rowHeight > 0 )
            {
                super.setRowHeight( rowHeight );

                try
                {
                    if ( JTreeTable.this.getRowHeight() != rowHeight )
                    {
                        JTreeTable.this.setRowHeight( getRowHeight() );

                    }

                }
                catch ( NullPointerException e )
                {

                    // ?
                }

            }

        }

        /**
         * updateUI is overridden to set the colors of the Tree's renderer to match that of the table.
         */
        @Override
        public void updateUI()
        {
            super.updateUI();

            // Make the tree's cell renderer use the table's cell selection
            // colors.

            // TreeCellRenderer tcr = getCellRenderer();

            // if (tcr instanceof DefaultTreeCellRenderer) {
            // DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer) tcr);
            //
            // // For 1.1 uncomment this, 1.2 has a bug that will cause an
            // // exception to be thrown if the border selection color is
            // // null.
            // // dtcr.setBorderSelectionColor(null);
            // /*
            // * dtcr.setTextSelectionColor(
            // * UIManager.getColor("Table.selectionForeground"));
            // * dtcr.setBackgroundSelectionColor(
            // * UIManager.getColor("Table.selectionBackground"));
            // */
            //
            // //dtcr.setLeafIcon(FileUtils.loadIcon("images/tree_leaf.png"));
            // //dtcr.setOpenIcon(FileUtils.loadIcon("images/tree_open.png"));
            // //dtcr.setClosedIcon(FileUtils.loadIcon("images/tree_closed.png"));
            // }

        }

    }

    /**
     * ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel to listen for changes in the ListSelectionModel
     * it maintains. Once a change in the ListSelectionModel happens, the paths are updated in the
     * DefaultTreeSelectionModel.
     */
    class ListToTreeSelectionModelWrapper
        extends DefaultTreeSelectionModel
    {

        /**
         * Class responsible for calling updateSelectedPathsFromSelectedRows when the selection of the list changse.
         */
        class ListSelectionHandler
            implements
                ListSelectionListener
        {

            public void valueChanged(
                ListSelectionEvent e )
            {
                updateSelectedPathsFromSelectedRows();

            }

        }

        /**
         * 
         */
        private static final long serialVersionUID = -5662716406687200005L;

        /** Set to true when we are updating the ListSelectionModel. */
        protected boolean         updatingListSelectionModel;

        public ListToTreeSelectionModelWrapper()
        {
            super();
            getListSelectionModel().addListSelectionListener( createListSelectionListener() );

        }

        /**
         * This is overridden to set <code>updatingListSelectionModel</code> and message super. This is the only place
         * DefaultTreeSelectionModel alters the ListSelectionModel.
         */
        @Override
        public void resetRowSelection()
        {
            if ( !updatingListSelectionModel )
            {
                updatingListSelectionModel = true;

                try
                {
                    super.resetRowSelection();

                }
                finally
                {
                    updatingListSelectionModel = false;

                }

            }

            // Notice how we don't message super if
            // updatingListSelectionModel is true. If
            // updatingListSelectionModel is true, it implies the
            // ListSelectionModel has already been updated and the
            // paths are the only thing that needs to be updated.
        }

        /**
         * Creates and returns an instance of ListSelectionHandler.
         */
        protected ListSelectionListener createListSelectionListener()
        {
            return new ListSelectionHandler();

        }

        /**
         * If <code>updatingListSelectionModel</code> is false, this will reset the selected paths from the selected
         * rows in the list selection model.
         */
        protected void updateSelectedPathsFromSelectedRows()
        {
            if ( !updatingListSelectionModel )
            {
                updatingListSelectionModel = true;

                try
                {
                    // This is way expensive, ListSelectionModel needs an
                    // enumerator for iterating.
                    int min = listSelectionModel.getMinSelectionIndex();
                    int max = listSelectionModel.getMaxSelectionIndex();

                    clearSelection();

                    if ( min != -1 && max != -1 )
                    {
                        for ( int counter = min; counter <= max; counter++ )
                        {
                            if ( listSelectionModel.isSelectedIndex( counter ) )
                            {
                                TreePath selPath = tree.getPathForRow( counter );

                                if ( selPath != null )
                                {
                                    addSelectionPath( selPath );

                                }

                            }

                        }

                    }

                }
                finally
                {
                    updatingListSelectionModel = false;

                }

            }

        }

        /**
         * Returns the list selection model. ListToTreeSelectionModelWrapper listens for changes to this model and
         * updates the selected paths accordingly.
         */
        ListSelectionModel getListSelectionModel()
        {
            return listSelectionModel;

        }

    }

    /**
     *  
     */
    private static final long       serialVersionUID = -7791862287904491186L;

    /** A subclass of JTree. */
    protected TreeTableCellRenderer tree;

    public JTreeTable(
        TreeTableModel treeTableModel )
    {
        super();

        // Create the tree. It will be used as a renderer and editor.
        tree = new TreeTableCellRenderer( treeTableModel );

        // Install a tableModel representing the visible rows in the tree.
        super.setModel( new TreeTableModelAdapter( treeTableModel, tree ) );

        // Force the JTable and JTree to share their row selection models.
        ListToTreeSelectionModelWrapper selectionWrapper = new ListToTreeSelectionModelWrapper();

        tree.setSelectionModel( selectionWrapper );
        setSelectionModel( selectionWrapper.getListSelectionModel() );

        // Install the tree editor renderer and editor.
        setDefaultRenderer( TreeTableModel.class, tree );
        setDefaultEditor( TreeTableModel.class, new TreeTableCellEditor() );

        // No grid.
        setShowGrid( false );

        // No intercell spacing
        setIntercellSpacing( new Dimension( 0, 0 ) );

        // And update the height of the trees row to match that of
        // the table.
        if ( tree.getRowHeight() < 1 )
        {
            // Metal looks better like this.
            setRowHeight( 18 );

        }

    }

    /*
     * Workaround for BasicTableUI anomaly. Make sure the UI never tries to
     * paint the editor. The UI currently uses different techniques to paint the
     * renderers and editors and overriding setBounds() below is not the right
     * thing to do for an editor. Returning -1 for the editing row in this case,
     * ensures the editor is never painted.
     */
    @Override
    public int getEditingRow()
    {
        return getColumnClass( editingColumn ) == TreeTableModel.class ? -1 : editingRow;

    }

    /**
     * Returns the tree that is being shared between the model.
     */
    public JTree getTree()
    {
        return tree;

    }

    /**
     * Overridden to pass the new rowHeight to the tree.
     */
    @Override
    public void setRowHeight(
        int rowHeight )
    {
        super.setRowHeight( rowHeight );

        if ( tree != null && tree.getRowHeight() != rowHeight )
        {
            tree.setRowHeight( getRowHeight() );

        }

    }

    /**
     * Overridden to message super and forward the method to the tree. Since the tree is not actually in the component
     * hieachy it will never receive this unless we forward it in this manner.
     */
    @Override
    public void updateUI()
    {
        super.updateUI();

        if ( tree != null )
        {
            tree.updateUI();

        }

        // Use the tree's default foreground and background colors in the
        // table.

        /*
         * LookAndFeel.installColorsAndFont( this, "Tree.background",
         * "Tree.foreground", "Tree.font");
         */
    }

}

/*******************************************************************************
 * $Log: JTreeTable.java,v $ Revision 1.10 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.9
 * 2005/09/26 17:19:53 timowest updated sources and tests Revision 1.8 2005/09/25 16:40:58 timowest updated sources and
 * tests Revision 1.7 2005/09/14 07:11:49 timowest updated sources
 */
