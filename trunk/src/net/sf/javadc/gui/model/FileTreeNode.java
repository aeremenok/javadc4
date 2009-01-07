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

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.TreeNode;

import net.sf.javadc.util.FileUtils;

/**
 * <CODE>FileTreeNode</CODE> represents a node in a <CODE>FileListTreeModel</CODE>
 * or <CODE>FileListTableModel</CODE>
 * 
 * @author Timo Westk�mper
 */
public class FileTreeNode implements TreeNode {

    // private final Vector children = new Vector();

    private final List folderChildren = new ArrayList();

    private final List fileChildren = new ArrayList();

    private File file;

    private FileTreeNode parent = null;

    private int size;

    private String nameNoExtension;

    private String extension;

    private boolean isDirectoryChildren = false;

    /**
     * Create FileTreeNode instance with the given File and file size
     * 
     * @param file
     *            File this node is related to
     * @param size
     *            file size of the given File instance
     */
    public FileTreeNode(File file, int size) {
        if (file == null)
            throw new NullPointerException("file was null.");

        this.file = file;
        this.size = size;

        nameNoExtension = FileUtils.getNameNoExtension(file.getName());
        extension = FileUtils.getExtension(file.getName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeNode#getChildCount()
     */
    public int getChildCount() {
        // return children.size();
        return folderChildren.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeNode#getAllowsChildren()
     */
    public boolean getAllowsChildren() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeNode#isLeaf()
     */
    public boolean isLeaf() {
        // return children.isEmpty();
        return folderChildren.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeNode#children()
     */
    public Enumeration children() {
        // return new IteratorWrapper(children.iterator());
        return new IteratorWrapper(folderChildren.iterator());
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeNode#getParent()
     */
    public TreeNode getParent() {
        return parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeNode#getChildAt(int)
     */
    public TreeNode getChildAt(int arg0) {
        // return (TreeNode) children.get(arg0);
        return (TreeNode) folderChildren.get(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
     */
    public int getIndex(TreeNode arg0) {
        // FileTreeNode[] nodes = (FileTreeNode[]) folderChildren
        // .toArray(new FileTreeNode[folderChildren.size()]);
        //
        // for (int i = 0; i < nodes.length; i++) {
        // if (nodes[i].equals(this))
        // return i;
        // }
        //
        // return -1;

        return folderChildren.indexOf(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return file.getName();
    }

    /**
     * @param parent
     *            The parent to set.
     */
    public void setParent(FileTreeNode parent) {
        this.parent = parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof FileTreeNode) {
            FileTreeNode n = (FileTreeNode) obj;
            return n.getFile().equals(this.getFile());

        } else {
            return false;

        }

    }

    /**
     * Return the Tiger Tree Hash of this file
     * 
     * @return
     */
    public String getHash() {
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getFile().hashCode();
    }

    /**
     * @param node
     */
    public void addChild(FileTreeNode node) {
        // TODO : check that 0 sized files are not shared
        if (node.getSize() == 0)
            folderChildren.add(node);
        else
            fileChildren.add(node);

        if (parent != null)
            parent.setDirectoryChildren(true);
    }

    /**
     * @return Returns the file.
     */
    public File getFile() {
        return file;
    }

    /**
     * @return Returns the isDirectoryChildren.
     */
    public boolean isDirectoryChildren() {
        return isDirectoryChildren;
    }

    public void setDirectoryChildren(boolean isDirectoryChildren) {
        this.isDirectoryChildren = isDirectoryChildren;
    }

    /**
     * @return Returns the fileChildren.
     */
    public List getFileChildren() {
        return fileChildren;
    }

    /**
     * @return Returns the folderChildren.
     */
    public List getFolderChildren() {
        return folderChildren;
    }

    /**
     * @return Returns the size.
     */
    public int getSize() {
        return size;
    }

    /**
     * @return Returns the extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @return Returns the nameNoExtension.
     */
    public String getNameNoExtension() {
        return nameNoExtension;
    }
}

class IteratorWrapper implements Enumeration {

    private final Iterator iterator;

    public IteratorWrapper(Iterator i) {
        iterator = i;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Enumeration#hasMoreElements()
     */
    public final boolean hasMoreElements() {
        return iterator.hasNext();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Enumeration#nextElement()
     */
    public final Object nextElement() {
        return iterator.next();
    }

}

/*******************************************************************************
 * $Log: FileTreeNode.java,v $
 * Revision 1.12  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.11 2005/09/15 17:32:29 timowest added
 * null checks
 * 
 * Revision 1.10 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
