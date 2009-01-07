/*
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

package net.sf.javadc.gui.model;

import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;

/**
 * <CODE>FileListTableModel</CODE> represents the contents of the selected
 * directory in a table view
 * 
 * @author Timo Westk�mper
 */
public class FileListTableModel extends AbstractTableModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5425810344578742315L;

    static final protected String[] cNames = { "File Name", "Type", "Hash",
            "Size" };

    static final protected Class[] cTypes = { String.class, String.class,
            String.class, Long.class };

    private List children = null;

    private final TreeSelectionListener treeSelectionListener = new MyTreeSelectionListener();

    /**
     * Create a FileListTableModel which acts also as a TreeSelectionListener to
     * the given JTree
     * 
     * @param tree
     *            JTree instance this FileListTableModel listens to
     */
    public FileListTableModel(JTree tree) {
        if (tree == null)
            throw new NullPointerException("tree was null.");

        tree.addTreeSelectionListener(treeSelectionListener);

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return cNames.length;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return (children == null) ? 0 : children.size();

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int column) {
        return cNames[column];

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class getColumnClass(int columnIndex) {
        return cTypes[columnIndex];

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int arg0, int arg1) {
        if (children == null) {
            return null;

        } else {
            FileTreeNode node = (FileTreeNode) children.get(arg0);

            switch (arg1) {
            case 0:
                return node.getNameNoExtension();

            case 1:
                return node.getExtension();

            case 2:
                return node.getHash();

            case 3:
                return new Long(node.getSize());

            }

            return null;

        }

    }

    /** ********************************************************************** */
    private class MyTreeSelectionListener implements TreeSelectionListener {

        public void valueChanged(TreeSelectionEvent arg0) {
            TreePath path = arg0.getPath();
            Object[] elements = path.getPath();

            children = ((FileTreeNode) elements[elements.length - 1])
                    .getFileChildren();

            fireTableDataChanged();

        }

    }

}

/*******************************************************************************
 * $Log: FileListTableModel.java,v $
 * Revision 1.14  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.13 2005/09/26 17:19:52 timowest
 * updated sources and tests
 * 
 * Revision 1.12 2005/09/15 17:32:29 timowest added null checks
 * 
 * Revision 1.11 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
