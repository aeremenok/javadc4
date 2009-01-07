/*
 * Created on 14.10.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package net.sf.javadc.gui.model;

import javax.swing.JTree;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FileListTableModelTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testCreation() throws Exception {
        FileListTableModel model = new FileListTableModel(new JTree());

        assertEquals(model.getColumnCount(), 3);
        assertEquals(model.getRowCount(), 0);
    }

}