/*
 * Copyright (C) 2001 Johan Nilsson, jfn@home.se
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

// $Id: ResumeAsFileChooser.java,v 1.14 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.gui.util;

import java.io.File;

import javax.swing.JFileChooser;

import net.sf.javadc.util.FileUtils;

/**
 * <CODE>ResumeAsFileChooser</CODE> is a <CODE>JFileChooser</CODE> extension
 * used to resume the download of files
 * 
 * @author Johan Nilsson
 * @version $Revision: 1.14 $ $Date: 2005/10/02 11:42:27 $
 */
public class ResumeAsFileChooser extends JFileChooser {

    /**
     * 
     */
    private static final long serialVersionUID = -5638186850456800292L;

    /**
     * Create a ResumeAsFileChooser with the given directory
     * 
     * @param currentDirectory
     */
    public ResumeAsFileChooser(String currentDirectory) {
        super(currentDirectory);
        setDialogTitle("Resume File As");
        setControlButtonsAreShown(false);
        setAcceptAllFileFilterUsed(true);

    }

    /** ********************************************************************** */

    /**
     * Show a resume dialog for the given File
     * 
     * @param file
     * 
     * @return
     */
    public final int showResumeDialog(File file) {
        setupFilters(file);

        return showOpenDialog(null);

    }

    /**
     * Setup extension filters based on the extension of the given File
     * 
     * @param file
     */
    private final void setupFilters(File file) {
        ExtensionFileFilter filter = new ExtensionFileFilter(FileUtils
                .getExtension(file.getName()));

        addChoosableFileFilter(filter);
        setFileFilter(filter);

    }

}

/*******************************************************************************
 * $Log: ResumeAsFileChooser.java,v $
 * Revision 1.14  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.13 2005/09/25 16:40:58 timowest
 * updated sources and tests
 * 
 * Revision 1.12 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
