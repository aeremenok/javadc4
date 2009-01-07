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
// $Id: MultiSearchComponent.java,v 1.20 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.sf.javadc.gui.util.SplitPane;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.hub.AllHubs;
import spin.Spin;

/**
 * <CODE>MultiSearchComponent</CODE> provides a view which enables searching
 * on all <CODE>Hubs</CODE> the local client is connected to
 * 
 * @author Timo Westk�mper
 */
public class MultiSearchComponent extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 6840965376908157854L;

    /**
     * 
     */
    private final IHub hub;

    /**
     * 
     */
    private final SearchComponent searchComponent;

    /**
     * 
     */
    private final SplitPane splitPane;

    // private final JTabbedPane tabPane = new JTabbedPane();
    // private final JToolBar toolBar;
    /**
     * 
     */
    private final UserListComponent userList;

    // external components
    /**
     * 
     */
    private final ISettings settings;

    /**
     * 
     */
    private final IDownloadManager downloadManager;

    /**
     * Create a MultiSearchComponent instance with the given AllHubs and
     * ISettings instance
     * 
     * @param _hub
     *            AllHubs instance to be used
     * @param _settings
     *            ISettings instance to be used
     */
    public MultiSearchComponent(AllHubs _hub, ISettings _settings,
            IDownloadManager _downloadManager) {
        super(new BorderLayout());

        if (_settings == null)
            throw new NullPointerException("settings was null");

        if (_downloadManager == null)
            throw new NullPointerException("downloadManager was null");

        // spinned components
        hub = (IHub) Spin.off(_hub);
        settings = _settings;

        downloadManager = (IDownloadManager) Spin.off(_downloadManager);

        // add a toolbar to the hub for things such as reconnect, close etc
        // toolBar = new JToolBar();

        // toolBar.setFloatable(false);
        // add(toolBar, BorderLayout.NORTH);

        searchComponent = new SearchComponent(hub, settings, downloadManager);
        // tabPane.addTab("Search All hubs", null, searchComponent);

        userList = new UserListComponent(hub, null, settings, downloadManager);

        splitPane = new SplitPane(SplitPane.HORIZONTAL_SPLIT, searchComponent,
                userList);

        splitPane.setDividerLocation(0.8);
        splitPane.setResizeWeight(1);

        add(splitPane, BorderLayout.CENTER);

    }

}

/*******************************************************************************
 * $Log: MultiSearchComponent.java,v $
 * Revision 1.20  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.19 2005/09/26 17:53:13
 * timowest added null checks
 * 
 * Revision 1.18 2005/09/26 17:19:52 timowest updated sources and tests
 * 
 * Revision 1.17 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.16 2005/09/15 17:32:29 timowest added null checks
 * 
 * Revision 1.15 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
