/*
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.gui.model;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.sf.javadc.listeners.FileListModelListener;

/**
 * <CODE>FileListTreeModel</CODE> presents the contents of a <CODE>FileListModel</CODE>
 * in a <CODE>TreeModel</CODE> suitable to be used in a <CODE>JTree</CODE>
 * instance
 * 
 * @author Timo Westk�mper
 */
public class FileListTreeModel extends DefaultTreeModel {

    // private final FileListModel model;

    /**
     * 
     */
    private static final long serialVersionUID = -8375434548182358964L;

    private final FileListModelListener fileListModelListener = new MyFileListModelListener();

    /**
     * Create a FileListTreeModel which provides a TreeModel interface to the
     * underlying FileListModel
     * 
     * @param model
     *            FileListModel instance to be adapted into a TreeModel
     */
    public FileListTreeModel(FileListModel model) {
        super(model.getRoot());

        // this.model = model;
        model.addListener(fileListModelListener);
    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object arg0) {
        if (arg0 instanceof FileTreeNode) {
            FileTreeNode node = (FileTreeNode) arg0;

            if (node.isDirectoryChildren())
                return node.getChildCount();
            else
                return 0;

        } else {
            return 0;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object arg0) {
        if (arg0 instanceof FileTreeNode) {
            FileTreeNode node = (FileTreeNode) arg0;

            if (node.isDirectoryChildren()) {
                return false;
            } else {
                return true;
            }

        } else {
            return true;

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object arg0, int arg1) {
        if (arg0 instanceof FileTreeNode) {
            FileTreeNode node = (FileTreeNode) arg0;
            return node.getChildAt(arg1);

        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object,
     *      java.lang.Object)
     */
    public int getIndexOfChild(Object arg0, Object arg1) {
        if ((arg0 instanceof FileTreeNode) && (arg1 instanceof FileTreeNode)) {
            FileTreeNode parent = (FileTreeNode) arg0;
            FileTreeNode child = (FileTreeNode) arg1;

            return parent.getIndex(child);

        } else {
            return -1;

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath,
     *      java.lang.Object)
     */
    public void valueForPathChanged(TreePath arg0, Object arg1) {
        // TODO Auto-generated method stub

    }

    /** ********************************************************************** */

    private class MyFileListModelListener implements FileListModelListener {

        public void treeNodesInserted(Object source, Object[] path,
                int[] childIndices, Object[] children) {

            fireTreeNodesInserted(source, path, childIndices, children);

        }

        public void treeStructureChanged(Object source, Object[] path,
                int[] childIndices, Object[] children) {

            fireTreeStructureChanged(source, path, childIndices, children);

        }

    }

}

/*******************************************************************************
 * $Log: FileListTreeModel.java,v $
 * Revision 1.13  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.12 2005/09/26 17:19:52 timowest
 * updated sources and tests
 * 
 * Revision 1.11 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
