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

import javax.swing.JWindow;

import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.ShareManager;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class ShareManagerWindowSimulation {

    public static void main(String[] args) {
        // JDialog dialog = new MyShareManagerFrame();
        JWindow window = new MyShareManagerDialog();

        window.setVisible(true);

        try {
            while (true) {
                Thread.sleep(10000);

            }

        } catch (InterruptedException e) {
            // logger.error(e.toString());
            System.out.println(e.toString());

        }

    }

}

class MyShareManagerDialog extends ShareManagerWindow {

    /**
     * 
     */
    private static final long serialVersionUID = -7553688259029047947L;

    public MyShareManagerDialog() {
        super(new ShareManager(new BaseSettings(true), new TaskManager()));

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                closeApplication();

            }

        });

    }

    private final void closeApplication() {
        System.out.println("Closing application.");
        System.exit(0);

    }

}

/*******************************************************************************
 * $Log: ShareManagerWindowSimulation.java,v $
 * Revision 1.9  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.8 2005/09/26 17:19:52
 * timowest updated sources and tests
 * 
 * Revision 1.7 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
