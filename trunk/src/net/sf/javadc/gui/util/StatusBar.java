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
// $Id: StatusBar.java,v 1.19 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.gui.util;

import java.awt.BorderLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <CODE>StatusBar</CODE> is the status bar class used in javadc3
 * 
 * @author Ryan Sweny
 * @version $Revision: 1.19 $ $Date: 2005/10/02 11:42:27 $
 */
public class StatusBar extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 3874981949782500006L;

    // private int counter = 0;
    // for general info
    private final JLabel lblStatus1 = new JLabel(" ");

    // for warnings and errors
    private JLabel lblStatus2 = new JLabel(" ");

    // for startup information
    private JLabel lblStatus3 = new JLabel(" ");

    private String downloadSlotsText = null;

    private String uploadSlotsText = null;

    private Date startTime = new Date();

    /**
     * Create a StatusBar instance
     */
    public StatusBar() {
        // super(new GridLayout(1, 2));
        super(new BorderLayout());

        // lblStatus1.setBorder(BorderFactory.createLoweredBevelBorder());
        // lblStatus2.setBorder(BorderFactory.createLoweredBevelBorder());
        add(lblStatus1, BorderLayout.EAST);

        add(lblStatus2, BorderLayout.CENTER);

        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        lblStatus3
                .setText("javadc3 was started at " + format.format(startTime));

        add(lblStatus3, BorderLayout.WEST);

    }

    /** ********************************************************************** */

    /**
     * Update the hashing file information label
     * 
     * @param fileName
     *            filename of the File which is currently hashed
     * @param percent
     *            percentage of the File hashing process
     */
    public void updateHashingFile(String fileName, String percent) {
        StringBuffer sb = new StringBuffer();

        sb.append("Hashing File: ").append(fileName).append(" - ").append(
                percent);
        lblStatus2.setText(sb.toString());

    }

    /**
     * Update the hashing file information label
     * 
     * @param fileName
     *            filename of the File which has been hashed
     */
    public void updateHashedFile(String fileName) {
        StringBuffer sb = new StringBuffer();

        if (!"".equalsIgnoreCase(fileName)) {
            sb.append("File: ").append(fileName).append(" hashed");
        }

        lblStatus2.setText(sb.toString());

    }

    /**
     * Update the download slots information label
     * 
     * @param used
     *            number of used download slots
     * @param total
     *            number of total download slots
     */
    public void updateDownloadSlots(int used, int total) {
        StringBuffer str = new StringBuffer();

        str.append("Used download Slots : ").append(used).append(" / ").append(
                total);
        downloadSlotsText = str.toString();

        if (uploadSlotsText != null) {
            lblStatus1.setText(downloadSlotsText + " " + uploadSlotsText);

        }

    }

    /**
     * Update the upload slots information label
     * 
     * @param used
     *            number of used upload slots
     * @param total
     *            number of total upload slots
     */
    public void updateUploadSlots(int used, int total) {
        StringBuffer str = new StringBuffer();

        str.append("Used upload Slots : ").append(used).append(" / ").append(
                total);
        uploadSlotsText = str.toString();

        if (downloadSlotsText != null) {
            lblStatus1.setText(downloadSlotsText + " " + uploadSlotsText);

        }

    }

}

/*******************************************************************************
 * $Log: StatusBar.java,v $
 * Revision 1.19  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.18 2005/09/25 16:40:58 timowest updated
 * sources and tests
 * 
 * Revision 1.17 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
