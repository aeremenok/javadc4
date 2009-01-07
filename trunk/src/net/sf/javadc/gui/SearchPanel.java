/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
 *
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
// $Id: SearchPanel.java,v 1.17 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.net.SearchRequest;

import org.apache.log4j.Category;

/**
 * <CODE>SearchPanel</CODE> provides a simple helper component which provides
 * a simple form to enter search criteria.
 * 
 * @author Timo Westk�mper
 */
public class SearchPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -1651679926931390860L;

    private final static Category logger = Category
            .getInstance(SearchPanel.class);

    /**
     * 
     */
    private final int KBYTES = 1024;

    /**
     * 
     */
    private final int MBYTES = 1024 * 1024;

    // private final JTextField nameField = new JTextField();
    /**
     * 
     */
    private final JComboBox nameField = new JComboBox();

    // private final JTextField pathField = new JTextField();
    // private final JTextField extensionField = new JTextField(5);
    /**
     * 
     */
    private final JTextField sizeField = new JTextField(5);

    /**
     * 
     */
    private final JComboBox sizeType = new JComboBox(new String[] { "greater",
            "smaller" });

    // private final JComboBox minSlots = new JComboBox(new String[]{"0", "1",
    // "2", "3", "4", "5"});
    /**
     * 
     */
    private final JComboBox type = new JComboBox(new String[] { "any", "Audio",
            "Compressed", "Document", "Exe", "Picture", "Video", "Folder",
            "TTH" });

    /**
     * 
     */
    private final JCheckBox freeSlots = new JCheckBox("free slots");

    /**
     * 
     */
    private final JButton searchButton = new JButton("Search");

    /**
     * 
     */
    private final JComboBox sizeMultiplier = new JComboBox(new String[] { "B",
            "kB", "MB" });

    // external components
    /**
     * 
     */
    private final IHub hub;

    // private final SearchComponent searchComponent;

    /**
     * Create a SearchPanel with the given IHub istance and SearchComponent
     * 
     * @param _hub
     *            IHub instance to be used
     * @param _searchComponent
     *            SearchComponent to be used
     */
    public SearchPanel(IHub _hub, SearchComponent _searchComponent) {
        super(new BorderLayout());

        if (_hub == null)
            throw new NullPointerException("_hub was null");

        if (_searchComponent == null)
            throw new NullPointerException("_searchComponent was null.");

        hub = _hub;
        // searchComponent = _searchComponent;

        JPanel subPanel = new JPanel(new GridLayout(7, 2, 2, 2));

        subPanel.setBorder(new EmptyBorder(2, 2, 2, 2));

        // Name
        subPanel.add(new JLabel("Text"));

        nameField.setEditable(true);
        subPanel.add(nameField);

        // Type
        subPanel.add(new JLabel("Type"));
        subPanel.add(type);

        // Only users with free Slots
        subPanel.add(new JLabel("Slots"));
        subPanel.add(freeSlots);

        // Size Type
        subPanel.add(new JLabel("Size"));
        subPanel.add(sizeType);

        // Size Multiplication
        subPanel.add(new JLabel());
        subPanel.add(sizeMultiplier);

        sizeMultiplier.setSelectedIndex(0);

        // Size Field
        subPanel.add(new JLabel());
        subPanel.add(sizeField);

        // Search
        subPanel.add(searchButton);

        ActionListener action = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                search();

            }

        };

        // nameField.addActionListener(action);
        searchButton.addActionListener(action);

        JLabel label = new JLabel("Search");

        label.setBorder(new EmptyBorder(2, 2, 2, 2));

        add(label, BorderLayout.NORTH);
        add(subPanel, BorderLayout.SOUTH);

    }

    /** ********************************************************************** */

    /**
     * Executes the search on the related DirectConnect Hub
     */
    private void search() {
        String name = null;

        try {
            if (nameField.getSelectedItem() != null) {
                name = nameField.getSelectedItem().toString();
            } else {
                name = "";
            }

            // nameField.addItem(makeObj(name));
            nameField.addItem(name);

            long size = getFileSize();

            SearchRequest sr = new SearchRequest(name,
                    type.getSelectedIndex() + 1, size, sizeType
                            .getSelectedIndex() == 0, freeSlots.isSelected());

            // SearchRequest is added indirectly via the Hub listener
            // searchComponent.addSearch(sr);
            hub.search(sr);

        } catch (IOException e) {
            logger.error("Catched IOException when trying to search for "
                    + name, e);
            // logger.error(e);

            /* hub.disconnect(); */
        } catch (NumberFormatException e) {
            logger.error(
                    "Catched NumberFormatException when trying to search for "
                            + name, e);
            // logger.error(e);

            JOptionPane.showMessageDialog(getTopLevelAncestor(),
                    "Illegal size!");

        } catch (Exception e) {
            logger.error("Catched " + e.getClass().getName()
                    + " when trying to search for " + name, e);

            // logger.error(e);
        }

    }

    /**
     * @return Returns the sizeField.
     */
    public JTextField getSizeField() {
        return sizeField;
    }

    /**
     * @return Returns the sizeMultiplier.
     */
    public JComboBox getSizeMultiplier() {
        return sizeMultiplier;
    }

    /**
     * Return the content of the sizeField as file size ion bytes
     * 
     * @return
     */
    protected long getFileSize() {
        long size = (sizeField.getText().length() > 0) ? Long
                .parseLong(sizeField.getText()) : 0;

        if (size > 0) {
            if (sizeMultiplier.getSelectedIndex() == 1) {
                size *= KBYTES; // kbytes

            } else if (sizeMultiplier.getSelectedIndex() == 2) {
                size *= MBYTES; // mbytes

            }
        }

        return size;
    }

}

/*******************************************************************************
 * $Log: SearchPanel.java,v $
 * Revision 1.17  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.16 2005/09/25 16:40:58 timowest updated
 * sources and tests
 * 
 * Revision 1.15 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
