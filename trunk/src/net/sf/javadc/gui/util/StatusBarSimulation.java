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

//$Id: StatusBarSimulation.java,v 1.12 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.gui.util;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * @author Timo Westk�mper
 */
public class StatusBarSimulation {

    private StatusBar statusBar = new StatusBar();

    public StatusBarSimulation() {

        JFrame frame = new JFrame();

        frame.getContentPane().add(statusBar);
        frame.setLocation(100, 100);
        frame.setSize(200, 300);

        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }

        });

        frame.setVisible(true);

        int counter = 0;

        try {
            while (true) {
                doMethod(counter);
                counter = (counter + 1) % 5;

                Thread.sleep(5000);
            }

        } catch (InterruptedException e) {
            // logger.error(e.toString());
            System.out.println(e.toString());

        }

    }

    /** ********************************************************************** */

    private final void doMethod(int sel) {

        if (sel == 0) {
            statusBar.updateDownloadSlots(5, 10);

        } else if (sel == 1) {
            statusBar.updateUploadSlots(4, 10);

        } else if (sel == 2) {
            statusBar.updateDownloadSlots(6, 10);

        } else if (sel == 3) {
            statusBar.updateUploadSlots(3, 10);

        } else if (sel == 4) {
            statusBar.updateDownloadSlots(10, 10);
        }

    }

    /*
     * @see TestCase#setUp()
     */
    public static void main(String[] args) {
        new StatusBarSimulation();

    }

}

/*******************************************************************************
 * $Log: StatusBarSimulation.java,v $
 * Revision 1.12  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.11 2005/09/14 07:11:49 timowest
 * updated sources
 * 
 * 
 * 
 */
