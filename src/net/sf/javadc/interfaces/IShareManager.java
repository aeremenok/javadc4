/*
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

package net.sf.javadc.interfaces;

import java.io.File;
import java.util.List;

import net.sf.javadc.net.SearchRequest;

/**
 * <CODE>IShareManager</CODE> is the abstract interface for <CODE>ShareManager</CODE>,
 * which manages the creation of file lists based on the shared files and the
 * handling of <CODE>SearchRequests</CODE>
 * 
 * @author tw70794
 */
public interface IShareManager extends IGenericModel {

    /**
     * Get the File with the given filename
     * 
     * @param filename
     * @return
     */
    public File getFile(String filename);

    /**
     * Get the size of the shared files in bytes
     * 
     * @return
     */
    public long getSharedSize();

    /**
     * Return a list of SearchResults based on the given SearchReqeust
     * 
     * @param sr
     * @return
     */
    public List search(SearchRequest sr);

    /**
     * Update the information of shared files
     */
    public void update();

    /**
     * Notify registered listeners that the given directory has been added to
     * the list of shared files
     * 
     * @param dir
     */
    public void fireDirectoryAdded(String dir);

    /**
     * Nofity registered listeners that the browse list is being created
     */
    public void fireCreatingBrowseList();

    /**
     * Notify registered listeners that the browse list has been created
     */
    public void fireBrowseListCreated();

    /**
     * Nofity registered listeners that the given file as been hashed
     * 
     * @param filename
     */
    public void fireFileHashed(String filename);

    /**
     * Notify registered listeners that the given file is being hashed
     * 
     * @param filename
     * @param percent
     */
    public void fireHashingFile(String filename, double percent);
}