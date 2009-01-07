/*
 * Created on 10.10.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package net.sf.javadc.gui.model;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FileListTreeModelTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testCreation() throws Exception {
        FileListModel fileListModel = new FileListModel();

        FileListTreeModel fileListTreeModel = new FileListTreeModel(
                fileListModel);
        fileListModel.createModel();

        FileTreeNode root = (FileTreeNode) fileListTreeModel.getRoot();

        // assures that the root node is considered as a node with non-leaf
        // children and the child count is greater than zero

        assertTrue(root.isDirectoryChildren());
        assertTrue(fileListTreeModel.getChildCount(root) > 0);

    }

}