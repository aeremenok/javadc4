/*
 * Copyright (C) 2001 Ryan Sweny, cabin@geeky.net
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

// $Id: FileBrowseComponent.java,v 1.26 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import net.sf.javadc.gui.model.FileListModel;
import net.sf.javadc.gui.model.FileListTableModel;
import net.sf.javadc.gui.model.FileListTreeModel;
import net.sf.javadc.gui.model.FileTreeNode;
import net.sf.javadc.gui.model.SortableTable;
import net.sf.javadc.gui.model.SortableTableListener;
import net.sf.javadc.gui.util.ByteCellRenderer;
import net.sf.javadc.gui.util.SplitPane;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;
import spin.Spin;

/**
 * <CODE>FileBrowseComponent</CODE> provides a combined tree and table view on
 * the shared contents of a local or remote file list. A <CODE>JTree</CODE>
 * component is used for the directory view and a <CODE>SortableTable</CODE>
 * for the directory contents
 * 
 * @author Timo Westk�mper
 */
public class FileBrowseComponent extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 7606840779863197560L;

    // private final static Logger logger =
    // Logger.getLogger(FileBrowseComponent.class);

    // attributes
    /**
     * 
     */
    private IHub hub;

    /**
     * 
     */
    private JTree tree;

    /**
     * 
     */
    private JLabel fileTableLabel;

    /**
     * 
     */
    private SortableTable fileTable;

    /**
     * 
     */
    private String nick;

    /**
     * 
     */
    private SortableTableListener fileListTableListener = new MySortableTableListener();

    /**
     * 
     */
    private TreeSelectionListener treeSelectionListener = new MyTreeSelectionListener();

    // components
    /**
     * 
     */
    private final ISettings settings;

    /**
     * 
     */
    private final FileListModel fileListModel;

    /**
     * 
     */
    private final FileListTreeModel fileListTreeModel;

    /**
     * 
     */
    private FileListTableModel fileListTableModel;

    /**
     * 
     */
    private final IDownloadManager downloadManager;

    /**
     * Create a FileBrowseComponent with the given ISettings instance
     * 
     * @param _settings
     *            ISettings instance to be used
     */
    public FileBrowseComponent(ISettings _settings,
            IDownloadManager _downloadManager) {
        super(new BorderLayout());

        if (_settings == null)
            throw new NullPointerException("settings was null");

        if (_downloadManager == null)
            throw new NullPointerException("downloadManager was null");

        this.settings = _settings;

        downloadManager = (IDownloadManager) Spin.off(_downloadManager);

        fileListModel = new FileListModel();
        fileListTreeModel = new FileListTreeModel(fileListModel);

        setupComponents();
    }

    /** ********************************************************************** */

    /**
     * Create a FileBrowseComponent instance with the given IHub, nick and
     * ISettings instance to be used
     * 
     * @param hub
     *            Hub this FileBrowseComponent is related to
     * @param nick
     *            Nick this FileBrowseComponent is related to
     * @param settings
     *            ISettings instance to be used
     */
    public FileBrowseComponent(IHub hub, String nick, ISettings settings,
            IDownloadManager downloadManager) {
        super(new BorderLayout());
        this.nick = nick;

        // is already spinned because FileBrowseComponent is no main level
        // component
        this.hub = hub;
        this.settings = settings;
        this.downloadManager = (IDownloadManager) Spin.off(downloadManager);

        fileListModel = new FileListModel(nick, settings.getDownloadDir());
        fileListTreeModel = new FileListTreeModel(fileListModel);

        setupComponents();
    }

    /**
     * Setup the child components of the FileBrowseComponent
     */
    private void setupComponents() {

        // creates the model used for the JTree element

        fileListModel.createModel();

        // creates the tree component used for the folder view

        tree = new JTree(fileListTreeModel);

        tree.setRootVisible(false);
        tree.setShowsRootHandles(false);
        tree.putClientProperty("JTree.lineStyle", "Angled");

        tree.setCellRenderer(new MyTreeCellRenderer());

        // adds a tree listener to change the caption of the file list table
        // when the tree selection is changed

        tree.addTreeSelectionListener(treeSelectionListener);

        // creates a table component used for the file view

        fileListTableModel = new FileListTableModel(tree);

        fileTable = new SortableTable(new int[] { 300, 10, 30, 50 },
                fileListTableListener, fileListTableModel, "files");

        fileTable.getTable().setDefaultRenderer(Long.class,
                new ByteCellRenderer());

        JPanel fileTablePanel = new JPanel(new BorderLayout());
        fileTablePanel.add(fileTable, BorderLayout.CENTER);

        fileTableLabel = new JLabel("Content empty");
        fileTableLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

        fileTablePanel.add(fileTableLabel, BorderLayout.NORTH);

        // creates a split pane that shows the folder and file views

        SplitPane splitPane = new SplitPane(SplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(tree), fileTablePanel);

        splitPane.setDividerLocation(200);

        add(splitPane, BorderLayout.CENTER);
    }

    /** ********************************************************************** */

    /**
     * Show a download popup menu for the given file name with the given size
     * 
     * @param filename
     *            Filename for which a popup should be shown
     * @param size
     *            filesize of the related file
     * @param e
     *            MouseEvent this popup is related to (to obtain location of
     *            popup)
     */
    private final void showDownloadPopup(final String filename, final int size,
            MouseEvent e) {
        JPopupMenu popup = new JPopupMenu();

        JMenuItem download = new JMenuItem("Download " + filename);

        download.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                SearchResult sr = new SearchResult((IHub) hub, nick, filename,
                        settings, 1);

                sr.setFileSize(size);

                downloadManager.requestDownload(new DownloadRequest(sr,
                        settings));

            }

        });

        popup.add(download);
        popup.show(e.getComponent(), e.getX(), e.getY());

    }

    /**
     * Show a popup menu for the given MouseEvent
     * 
     * @param e
     */
    public final void showPopup(MouseEvent e) {
        // JTreeTable list = (JTreeTable) e.getSource();
        Point pos = e.getPoint();

        FileTreeNode node = (FileTreeNode) tree.getClosestPathForLocation(
                pos.x, pos.y).getLastPathComponent();

        showDownloadPopup(node.getFile().getName(), node.getSize(), e);

    }

    /** ********************************************************************** */

    private class MyTreeCellRenderer extends DefaultTreeCellRenderer {

        /**
         * 
         */
        private static final long serialVersionUID = -1207862436601048067L;

        public Icon getLeafIcon() {
            return getClosedIcon();
        }

    }

    private class MySortableTableListener implements SortableTableListener {

        public void cellSelected(int row, int column, int[] selectedColumn) {
            // TODO Auto-generated method stub

        }

        public void showPopupClicked(int row, int column, MouseEvent e,
                int[] selectedRows) {
            // TODO Auto-generated method stub

        }

    }

    private class MyTreeSelectionListener implements TreeSelectionListener {

        public void valueChanged(TreeSelectionEvent arg0) {
            TreePath path = arg0.getPath();
            Object[] elements = path.getPath();

            FileTreeNode node = (FileTreeNode) elements[elements.length - 1];

            fileTableLabel.setText("Content of "
                    + node.getFile().getPath().substring(1));
        }

    }

}

/*******************************************************************************
 * $Log: FileBrowseComponent.java,v $
 * Revision 1.26  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.25 2005/09/26 17:53:12 timowest
 * added null checks
 * 
 * Revision 1.24 2005/09/26 17:19:52 timowest updated sources and tests
 * 
 * Revision 1.23 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.22 2005/09/15 17:32:29 timowest added null checks
 * 
 * Revision 1.21 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
