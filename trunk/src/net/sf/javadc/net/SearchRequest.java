/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
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

// $Id: SearchRequest.java,v 1.22 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.net;

import java.util.Map;
import java.util.TreeMap;

import net.sf.javadc.util.FileInfo;
import net.sf.javadc.util.FileUtils;

/**
 * <code>SearchRequest</code> defines a single request from a local client to
 * a hub or remote client to search for shared files that match the given
 * pattern and report to it to request intiating client
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.22 $ $Date: 2005/10/02 11:42:27 $
 */
public class SearchRequest {

    /**
     * 
     */
    public static final int ALL = 1;

    /**
     * 
     */
    public static final int AUDIO = 2;

    /**
     * 
     */
    public static final int COMP = 3;

    /**
     * 
     */
    public static final int DOCS = 4;

    /**
     * 
     */
    public static final int EXEC = 5;

    /**
     * 
     */
    public static final int IMAG = 6;

    /**
     * 
     */
    public static final int VIDEO = 7;

    /**
     * 
     */
    public static final int DIR = 8;

    /**
     * 
     */
    public static final int TTH = 9;

    private static Map extensionTypes = new TreeMap();

    private final static String[][] fileTypes = {
            { "mp3", "mp2", "wav", "au", "rm", "mid", "sm" }, // 2 = audio
            { "zip", "arj", "rar", "lzh", "gz", "z", "arc", "pak" },
            { "doc", "txt", "wri", "pdf", "ps", "tex" }, // 4 = document
            { "pm", "exe", "bat", "com" }, // 5 = exe
            { "gif", "jpg", "jpeg", "bmp", "pcx", "png", "wmf", "psd" },
            { "mpg", "mpeg", "avi", "asf", "mov" // 7 = video
            } };

    static {
        for (int i = 0; i < fileTypes.length; i++) {
            for (int j = 0; j < fileTypes[i].length; j++) {
                extensionTypes.put(fileTypes[i][j], new Integer(i + 2));

            }

        }

    }

    private boolean activeMode = false;

    private int fileType = 1;

    private boolean isMinSize = true;

    private boolean freeSlots = false;

    private String namePattern;

    // The user to respond to.
    private String respondTo;

    private long size;

    private String toString;

    /*
     * public SearchRequest(String namePattern, int fileType, long minSize) {
     * this(namePattern, fileType, minSize, true); }
     */

    /**
     * Create a SearchRequest with the given attributes
     * 
     * @param _namePattern
     *            name pattern to be used
     * @param _fileType
     *            file type of the SearchRequest
     * @param _size
     *            size of the files to be searched
     * @param _isMinSize
     *            whether the size the minimum size
     * @param _freeSlots
     *            free download slots
     */
    public SearchRequest(String _namePattern, int _fileType, long _size,
            boolean _isMinSize, boolean _freeSlots) {
        if (_namePattern != null) {
            namePattern = _namePattern.trim();

        }

        fileType = _fileType;
        size = _size;
        isMinSize = _isMinSize;
        freeSlots = _freeSlots;

    }

    /**
     * Create a SearchRequest to be populated by the
     */
    public SearchRequest() {

    }

    /** ********************************************************************** */
    /**
     * ?
     * 
     * @return
     */
    public final boolean activeMode() {
        return activeMode;

    }

    /**
     * Make a case-insensitive inclusion comparison
     * 
     * @param str
     *            String instance which acts as the main instance
     * @param part
     *            String instance which is the search instance
     * @return true, if part is included in str and false, if not
     */
    private final static boolean contains(String str, String part) {
        return (part == null)
                || (str.toLowerCase().indexOf(part.toLowerCase()) != -1);

    }

    /**
     * Make a case-insensitive comparison of the two given String instances
     * 
     * @param str1
     * @param str2
     * @return true, if str1 equals str2 and false, if not
     */
    // private final static boolean equals(String str1, String str2) {
    // return (str2 == null) || (str2.length() == 0)
    // || (str1.compareToIgnoreCase(str2) == 0);
    //
    // }
    /**
     * Return whether the given filename matches the file type defined by this
     * SearchRequest instance
     * 
     * @param fileName
     * @return true, if it matches and false, if not
     */
    public final boolean fileTypeMatches(String fileName) {
        if (fileType == 1) {
            return true; // all files are accepted

        } else {
            return fileType == getFileType(fileName);

        }

    }

    /**
     * Get the file type of this SearchRequest instance
     * 
     * @return
     */
    public final int getFileType() {
        return fileType;

    }

    /**
     * Get the file type of the given file name
     * 
     * @param fileName
     * @return
     */
    public static final int getFileType(String fileName) {
        Integer i = (Integer) extensionTypes.get(FileUtils.getExtension(
                fileName).toLowerCase());

        if (i == null) { // unmatched extension
            return 1;

        } else { // matched extension
            return i.intValue();

        }

    }

    /**
     * Get the file name pattern of this SearchRequest
     * 
     * @return
     */
    public final String getNamePattern() {
        return namePattern;

    }

    /**
     * Get the respond address
     * 
     * @return
     */
    public final String getRespondAddress() {
        return respondTo;

    }

    /**
     * Get the file size requested by this SearchRequest
     * 
     * @return
     */
    public final long getSize() {
        return size;

    }

    /**
     * Get whether the file size attribute defines the minimum size
     * 
     * @return
     */
    public final boolean isMinSize() {
        return isMinSize;

    }

    /**
     * Return whether the file denoted by the FileInfo instance matches the
     * criteria of this SearchRequest
     * 
     * @param fileInfo
     *            FileInfo instance to be checked
     * @return true, if the match succeeds and false, if not
     */
    public final boolean matches(FileInfo fileInfo) {

        if (fileType == TTH) {
            return namePattern.equals(fileInfo.getHash().getRoot());

        } else {
            // checking for file name equality costs most
            return fileTypeMatches(fileInfo.getName())
                    && sizeMatches(fileInfo.getLength())
                    && nameMatches(FileUtils.getName(fileInfo.getName()));
        }

    }

    /**
     * Return whether the given SearchResult matches the criteria of the
     * SearchReqeust
     * 
     * @param sr
     *            SearchResult to be checked
     * @return true, if the match succeeds and false, if not
     */
    public final boolean matches(SearchResult sr) {

        boolean ret;

        if (fileType == TTH) {
            ret = namePattern.equals(sr.getTTH());

        } else {
            // checking for file name costs most
            ret = sizeMatches(sr.getFileSize())
                    && fileTypeMatches(sr.getFilename())
                    && nameMatches(sr.getFilename());
        }

        // previous matches succeeded and free slot match is on
        if (ret && freeSlots) {
            ret = sr.getFreeSlotCount() > 0;

        }

        return ret;

    }

    /**
     * Test whether the given file name matches the file name pattern
     * 
     * @param name
     *            file name to be tested
     * @return true, if the file name pattern is included in the given file name
     *         and false, if not
     */
    private final boolean nameMatches(String name) {
        return contains(name, namePattern);

    }

    /**
     * Set the mode of this SearchRequest
     * 
     * @param bol
     */
    public final void setActiveMode(boolean bol) {
        activeMode = bol;
        toString = null;

    }

    /**
     * Set the file type of this SearchRequest
     * 
     * @param i
     */
    public final void setFileType(int i) {
        fileType = i;
        toString = null;

    }

    /**
     * Set whether the size attribute defines the minimum size
     * 
     * @param b
     */
    public final void setMinSize(boolean b) {
        isMinSize = b;
        toString = null;

    }

    /**
     * Set the respond address
     * 
     * @param s
     */
    public final void setRespondAddress(String s) {
        respondTo = s;
        toString = null;
    }

    /**
     * Set the requested file size in bytes
     * 
     * @param l
     */
    public final void setSize(long l) {
        size = l;
        toString = null;
    }

    /**
     * Test whether the given file size in bytes matches the file size criteria
     * of this SearchRequest
     * 
     * @param s
     *            file size in bytes to be tested
     * @return true, if the match succeeds and false, if not
     */
    public final boolean sizeMatches(long s) {
        if (size == 0) {
            return true;

        } else if (isMinSize) {
            return size <= s;

        } else {
            return size >= s;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;

        } else if (obj instanceof SearchRequest) {
            SearchRequest sr = (SearchRequest) obj;

            // checking for name costs most
            return sizeMatches(sr.getSize())
                    && (getFileType() == sr.getFileType())
                    && (isFreeSlots() == sr.isFreeSlots())
                    && getNamePattern().equals(sr.getNamePattern());

        } else {
            return false;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return toString().hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public final String toString() {

        // lazy creation

        if (toString == null)
            toString = ((size != 0) ? "T" : "F") + "?"
                    + (isMinSize ? "F" : "T") + "?" + size + "?" + fileType
                    + "?" + ((fileType == TTH) ? "TTH:" : "")
                    + getNamePattern().replace(' ', '$').replace('.', '$');

        return toString;

    }

    /**
     * Return whether the SearchRequest is in active mode or not
     * 
     * @return
     */
    public boolean isActiveMode() {
        return activeMode;

    }

    /**
     * Return whether the remote client needs to have free download slots
     * 
     * @return
     */
    public boolean isFreeSlots() {
        return freeSlots;

    }

    /**
     * Return the respond to attribute
     * 
     * @return Returns the respondTo.
     */
    public String getRespondTo() {
        return respondTo;
    }

    /**
     * Set the repond to attribute
     * 
     * @param respondTo
     *            The respondTo to set.
     */
    public void setRespondTo(String respondTo) {
        this.respondTo = respondTo;
        toString = null;
    }

    /**
     * @param freeSlots
     *            The freeSlots to set.
     */
    public void setFreeSlots(boolean freeSlots) {
        this.freeSlots = freeSlots;
        toString = null;
    }

    /**
     * @param namePattern
     *            The namePattern to set.
     */
    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
        toString = null;
    }

}

/*******************************************************************************
 * $Log: SearchRequest.java,v $
 * Revision 1.22  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.21 2005/09/30 15:59:53 timowest
 * updated sources and tests
 * 
 * Revision 1.20 2005/09/15 17:37:14 timowest updated sources and tests
 * 
 * Revision 1.19 2005/09/14 07:11:50 timowest updated sources
 * 
 * Revision 1.18 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
