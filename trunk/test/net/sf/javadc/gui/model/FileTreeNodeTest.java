/*
 * Created on 14.10.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package net.sf.javadc.gui.model;

import java.io.File;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FileTreeNodeTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testCreation() throws Exception {

        // creates a new FileTreeNode
        FileTreeNode node = new FileTreeNode(new File("/tmp"), 0);

        // assures that it is considered to be a leaf node without children

        assertEquals(node.getChildCount(), 0);
        assertTrue(node.isLeaf());

        // add a image file as a child

        node.addChild(new FileTreeNode(new File("/tmp/test.gif"), 5));

        // assures that the FileTreeNode is still considered as a leaf node
        // without children, because the added child was no directory

        assertEquals(node.getChildCount(), 0);
        assertTrue(node.isLeaf());

        FileTreeNode node2 = new FileTreeNode(new File("/tmp/images"), 0);
        node2.setParent(node);

        // adds a directory to the main FileTreeNode

        node.addChild(node2);

        // assures that the parent node is now to be considered as non-leaf
        // node with one child

        assertEquals(node.getChildCount(), 1);
        assertFalse(node.isLeaf());

        // assures that the parent node is considered as a node without any
        // non-leaf children

        assertFalse(node.isDirectoryChildren());

        // adds a file node to the child node

        node2.addChild(new FileTreeNode(new File("/tmp/images/test.gif"), 5));

        // assures that the parent node is now considered as a node with
        // non-leaf children

        assertTrue(node.isDirectoryChildren());
    }

}