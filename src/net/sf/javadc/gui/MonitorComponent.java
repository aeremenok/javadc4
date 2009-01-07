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
// $Id: MonitorComponent.java,v 1.17 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.sf.javadc.gui.util.SplitPane;

/**
 * <CODE>MonitorComponent</CODE> provides a view of active <CODE>Connections
 * </CODE> and <CODE>DownloadRequests</CODE> based on the <CODE>
 * DownloadComponent</CODE> and <CODE>IncompleteComponent</CODE>.
 * 
 * @author Timo Westk�mper
 */
public class MonitorComponent extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -6079649664523670291L;

    /**
     * 
     */
    private final SplitPane splitPane;

    /**
     * 
     */
    private final JPanel downloadPanel = new JPanel(new BorderLayout());

    /**
     * 
     */
    private final JPanel incompletesPanel = new JPanel(new BorderLayout());

    // components
    // private final DownloadComponent downloadComponent;
    // private final IncompleteComponent incompleteComponent;

    /**
     * Create a MonitorComponent instance with the given DownloadComponent and
     * IncompleteComponent
     * 
     * @param downloadComponent
     *            DownloadComponent instance to be used
     * @param incompleteComponent
     *            IncompleteComponent instance to be used
     */
    public MonitorComponent(DownloadComponent downloadComponent,
            IncompleteComponent incompleteComponent) {
        super(new BorderLayout());

        if (downloadComponent == null)
            throw new NullPointerException("downloadComponent was null");

        if (incompleteComponent == null)
            throw new NullPointerException("incompleteComponent was null");

        // this.downloadComponent = downloadComponent;
        // this.incompleteComponent = incompleteComponent;

        // Download Component
        JLabel downloadLabel = new JLabel("Downloads / Uploads");

        downloadLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

        downloadPanel.add(downloadLabel, BorderLayout.NORTH);
        downloadPanel.add(downloadComponent, BorderLayout.CENTER);

        downloadPanel.setMinimumSize(new Dimension(0, 200));

        // Incomplete Component
        JLabel incompletesLabel = new JLabel("Download Queue");

        incompletesLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

        incompletesPanel.add(incompletesLabel, BorderLayout.NORTH);
        incompletesPanel.add(incompleteComponent, BorderLayout.CENTER);

        incompletesPanel.setMinimumSize(new Dimension(0, 200));

        splitPane = new SplitPane(SplitPane.VERTICAL_SPLIT, downloadPanel,
                incompletesPanel);

        splitPane.setDividerLocation(0.5);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);

    }

}

/*******************************************************************************
 * $Log: MonitorComponent.java,v $
 * Revision 1.17  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.16 2005/09/26 17:53:13 timowest
 * added null checks
 * 
 * Revision 1.15 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.14 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
