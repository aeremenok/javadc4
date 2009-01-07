/*
 * Copyright (C) 2001 Ryan Sweny, cabin@geeky.net
 *
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

// $Id: TreeTablePanel.java,v 1.14 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.gui.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

/**
 * <CODE>TreeTablePanel</CODE> is an abstract extension to <CODE>JPanel</CODE>
 * which provides some convenience methods to handle <CODE>JTreeTable</CODE>
 * components
 * 
 * @author Ryan Sweny
 * @version $Revision: 1.14 $ $Date: 2005/10/02 11:42:27 $
 */
public abstract class TreeTablePanel extends JPanel {

    private JTreeTable table;

    /**
     * Create a TreeTablePanel that uses a BorderLayout as the LayoutManager
     */
    public TreeTablePanel() {
        super(new BorderLayout());

    }

    /** ********************************************************************** */

    /**
     * Expand the tree row with the given index
     * 
     * @param row
     */
    private final void expandTreeRow(int row) {
        final JTree tree = table.getTree();

        if (tree.isExpanded(row)) {
            tree.collapseRow(row);

        } else {
            tree.expandRow(row);

        }

    }

    /**
     * Set the JTreeTable instance of the TreeTablePanel instance
     * 
     * @param jtt
     */
    public final void setJTreeTable(JTreeTable jtt) {
        table = jtt;
        add(new JScrollPane(table));
        setPreferredSize(new Dimension(400, 400));

        table.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);

                }

            }

            public void mouseReleased(MouseEvent e) {
                if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                    int row = table.rowAtPoint(e.getPoint());

                    expandTreeRow(row);

                } else if (e.isPopupTrigger()) {
                    showPopup(e);

                }

            }

        });

    }

    /**
     * Show a popup for the given MouseEvent
     * 
     * @param e
     */
    public abstract void showPopup(MouseEvent e);

}

/*******************************************************************************
 * $Log: TreeTablePanel.java,v $
 * Revision 1.14  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.13 2005/09/14 07:11:49 timowest
 * updated sources
 * 
 * 
 * 
 */
