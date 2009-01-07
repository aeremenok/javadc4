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

package net.sf.javadc.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SearchPanelSimulation {

    private IHub hub = new BaseHub();

    private ISettings settings = new BaseSettings(true);

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(
            new HubManager(), segmentManager);

    private SearchComponent searchComponent = new SearchComponent(hub,
            settings, downloadManager);

    private SearchPanel searchPanel = null;

    public SearchPanelSimulation() {
        searchPanel = new SearchPanel(hub, searchComponent);

        JFrame frame = new JFrame();

        frame.getContentPane().add(searchPanel);

        frame.setLocation(100, 100);
        frame.setSize(200, 300);

        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }

        });

        frame.setVisible(true);

        try {
            while (true) {
                Thread.sleep(10000);

            }

        } catch (InterruptedException e) {
            // logger.error(e.toString());
            System.out.println(e.toString());

        }

    }

    /** ********************************************************************** */

    /*
     * @see TestCase#setUp()
     */
    public static void main(String[] args) {
        new SearchPanelSimulation();

    }

}

/*******************************************************************************
 * $Log: SearchPanelSimulation.java,v $
 * Revision 1.12  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.11 2005/09/14 07:11:49
 * timowest updated sources
 * 
 * 
 * 
 */
