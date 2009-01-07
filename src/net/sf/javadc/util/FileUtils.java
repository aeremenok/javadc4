/*
 * Copyright (C) 2001 Michael Kurz mkurz@epost.de
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

// $Id: FileUtils.java,v 1.14 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.log4j.Category;

/**
 * @author Jesper Nordenberg
 * @author Michael Kurz
 * @version $Revision: 1.14 $ $Date: 2005/10/02 11:42:28 $ =======
 * 
 */
public class FileUtils {

    private final static Category logger = Category
            .getInstance(FileUtils.class);

    /**
     * Get the last slash index of the given String
     * 
     * @param name
     * @return
     */
    private final static int getLastSlashIndex(String name) {
        return Math.max(name.lastIndexOf('\\'), name.lastIndexOf('/'));

    }

    /**
     * Convert the slahes in the given String to the format of the JVM
     * 
     * @param name
     * @return
     */
    public final static String convertSlashes(String name) {
        return name.replace((File.separatorChar == '/') ? '\\' : '/',
                File.separatorChar);

    }

    /**
     * Return the last path element of the given path
     * 
     * @param name
     * @return
     */
    public final static String getName(String name) {
        int index = getLastSlashIndex(name);

        if (index == -1) {
            return name;

        } else {
            return name.substring(index + 1);

        }

    }

    /**
     * Return the file name of the given path without the extension
     * 
     * @param name
     * @return
     */
    public final static String getNameNoExtension(String name) {
        String shortName = getName(name);
        int index = shortName.lastIndexOf('.');

        if (index == -1) {
            return shortName;

        } else {
            return shortName.substring(0, index);

        }

    }

    /**
     * Get the file extension of the given path
     * 
     * @param name
     * @return
     */
    public final static String getExtension(String name) {
        int index = name.lastIndexOf('.');

        if (index == -1) {
            return "";

        } else {
            return name.substring(index + 1);

        }

    }

    /**
     * Get the path depth of the given path
     * 
     * @param name
     * @return
     */
    public final static int getPathDepth(String name) {
        String[] elements = name.split("\\\\");

        return elements.length - 1;

    }

    /**
     * Get the path without the file name of the given full path
     * 
     * @param name
     * @return
     */
    public final static String getPath(String name) {
        int index = getLastSlashIndex(name);

        if (index == -1) {
            return "";

        } else {
            return name.substring(0, index);

        }

    }

    /**
     * Return whether the given paths are similar
     * 
     * @param name1
     * @param name2
     * @return
     */
    public static final boolean isSimilar(String name1, String name2) {
        String ext1 = getExtension(name1).toLowerCase();
        String ext2 = getExtension(name2).toLowerCase();

        // return ((ext1.compareTo(ext2) == 0) && (getName(name1).charAt(0) ==
        // getName(
        // name2).charAt(0)));

        // strip all extra characters off the file name

        String n1 = getNameNoExtension(name1).toLowerCase().replaceAll(
                "[^a-zA-Z0-9]", "");
        String n2 = getNameNoExtension(name2).toLowerCase().replaceAll(
                "[^a-zA-Z0-9]", "");

        // return ext1.equals(ext2) && n1.equals(n2);

        return ext1.equals(ext2) && isSimilarName(n1, n2);
    }

    /**
     * Return true, if name1 is contained in name2 or vice versa
     * 
     * @param name1
     * @param name2
     * @return
     */
    private static final boolean isSimilarName(String name1, String name2) {

        return name1.indexOf(name2) > -1 || name2.indexOf(name1) > -1;
    }

    /**
     * Load an Icon from the given location
     * 
     * @param location
     * @return
     */
    public static ImageIcon loadIcon(String location) {
        URL img = null;

        try {
            img = ClassLoader.getSystemResource(location);

        } catch (Exception e) {
            // e.printStackTrace();
            logger.error("Caught " + e.getClass().getName(), e);

        }

        if (img == null) {
            System.out.println("Error loading image: " + location);

            return null;

        } else {
            return new ImageIcon(img);

        }

    }

    /**
     * Copy a file from in to out
     * 
     * @param in
     * @param out
     */
    public static void copyFile(File in, File out) {
        if ((in == null) || (out == null)) {
            throw new IllegalArgumentException("in and out must not be null!");

        }

        try {
            FileInputStream fis = new FileInputStream(in);
            FileOutputStream fos = new FileOutputStream(out);

            byte[] buf = new byte[1024];
            int i = 0;

            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);

            }

            fis.close();
            fos.close();

        } catch (Exception e) {
            // logger.error(iex.getLocalizedMessage(), iex);
            logger.error("Caught " + e.getClass().getName(), e);

        }

    }

}

/*******************************************************************************
 * $Log: FileUtils.java,v $
 * Revision 1.14  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.13 2005/09/26 17:19:52 timowest updated
 * sources and tests
 * 
 * Revision 1.12 2005/09/15 17:37:14 timowest updated sources and tests
 * 
 * Revision 1.11 2005/09/14 07:11:48 timowest updated sources
 * 
 * 
 * 
 */
