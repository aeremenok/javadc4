/*
 * Copyright (c) 1997-1999 by Sun Microsystems, Inc. All Rights Reserved. Sun grants you ("Licensee") a non-exclusive,
 * royalty free, license to use, modify and redistribute this software in source and binary code form, provided that i)
 * this copyright notice and license appear on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun. This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE
 * FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES.
 * IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGES. This software is not designed or intended for use in on-line control of aircraft, air traffic, aircraft
 * navigation or aircraft communications; or in the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or redistribute the Software for such purposes.
 */

package net.sf.javadc.gui.model;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

/**
 * An abstract implementation of the <CODE>TreeTableModel</CODE> interface, handling the list of listeners.
 * 
 * @author Philip Milne
 * @version
 */
public abstract class AbstractTreeTableModel
    implements
        TreeTableModel
{
    protected EventListenerList listenerList = new EventListenerList();

    protected Object            root;

    public AbstractTreeTableModel()
    {

    }

    public AbstractTreeTableModel(
        Object root )
    {
        this.root = root;

    }

    public void addTreeModelListener(
        TreeModelListener l )
    {
        listenerList.add( TreeModelListener.class, l );

    }

    //
    // Default implementations for methods in the TreeTableModel interface.
    //
    public Class getColumnClass(
        int column )
    {
        return Object.class;

    }

    // This is not called in the JTree's default mode: use a naive
    // implementation.
    public int getIndexOfChild(
        Object parent,
        Object child )
    {
        for ( int i = 0; i < getChildCount( parent ); i++ )
        {
            if ( getChild( parent, i ).equals( child ) )
            {
                return i;

            }

        }

        return -1;

    }

    //
    // Default implementations for methods in the TreeModel interface.
    //
    public Object getRoot()
    {
        return root;

    }

    /**
     * By default, make the column with the Tree in it the only editable one. Making this column editable causes the
     * JTable to forward mouse and keyboard events in the Tree column to the underlying JTree.
     */
    public boolean isCellEditable(
        Object node,
        int column )
    {
        // return getColumnClass(column) == TreeTableModel.class;
        return false;

    }

    public boolean isLeaf(
        Object node )
    {
        return getChildCount( node ) == 0;

    }

    public void removeTreeModelListener(
        TreeModelListener l )
    {
        listenerList.remove( TreeModelListener.class, l );

    }

    public void setRoot(
        Object root )
    {
        this.root = root;

    }

    public void setValueAt(
        Object aValue,
        Object node,
        int column )
    {

    }

    public void valueForPathChanged(
        TreePath path,
        Object newValue )
    {

    }

    /*
     * Notify all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     * 
     * @see EventListenerList
     */
    protected void fireTreeNodesChanged(
        Object source,
        Object[] path,
        int[] childIndices,
        Object[] children )
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();

        TreeModelEvent e = new TreeModelEvent( source, path, childIndices, children );

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for ( int i = listeners.length - 2; i >= 0; i -= 2 )
        {
            if ( listeners[i] == TreeModelListener.class )
            {
                ((TreeModelListener) listeners[i + 1]).treeNodesChanged( e );

            }

        }

    }

    /*
     * Notify all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     * 
     * @see EventListenerList
     */
    protected void fireTreeNodesInserted(
        Object source,
        Object[] path,
        int[] childIndices,
        Object[] children )
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = new TreeModelEvent( source, path, childIndices, children );

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for ( int i = listeners.length - 2; i >= 0; i -= 2 )
        {
            if ( listeners[i] == TreeModelListener.class )
            {
                ((TreeModelListener) listeners[i + 1]).treeNodesInserted( e );

            }

        }

    }

    /*
     * Notify all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     * 
     * @see EventListenerList
     */
    protected void fireTreeNodesRemoved(
        Object source,
        Object[] path,
        int[] childIndices,
        Object[] children )
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();

        TreeModelEvent e = new TreeModelEvent( source, path, childIndices, children );

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for ( int i = listeners.length - 2; i >= 0; i -= 2 )
        {
            if ( listeners[i] == TreeModelListener.class )
            {
                ((TreeModelListener) listeners[i + 1]).treeNodesRemoved( e );

            }

        }

    }

    /*
     * Notify all listeners that have registered interest for notification on
     * this event type. The event instance is lazily created using the
     * parameters passed into the fire method.
     * 
     * @see EventListenerList
     */
    protected void fireTreeStructureChanged(
        Object source,
        Object[] path,
        int[] childIndices,
        Object[] children )
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = new TreeModelEvent( source, path, childIndices, children );

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for ( int i = listeners.length - 2; i >= 0; i -= 2 )
        {
            if ( listeners[i] == TreeModelListener.class )
            {
                ((TreeModelListener) listeners[i + 1]).treeStructureChanged( e );

            }

        }

    }

}

/*******************************************************************************
 * $Log: AbstractTreeTableModel.java,v $ Revision 1.7 2005/10/02 11:42:28 timowest updated sources and tests Revision
 * 1.6 2005/09/14 07:11:49 timowest updated sources
 */
