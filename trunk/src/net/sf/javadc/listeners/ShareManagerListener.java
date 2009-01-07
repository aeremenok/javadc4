/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.listeners;

import java.util.EventListener;

/**
 * <code>ShareManagerListener</code> is the listener interface for objects interested in notifications from
 * <code>ShareManager</code> instances
 * 
 * @author Timo Westk�mper
 */
public interface ShareManagerListener
    extends
        EventListener
{
    /**
     * The browse list for the local client's shared files has been created
     */
    public void browseListCreated();

    /**
     * The browse list for the local client's shared files is being created
     */
    public void creatingBrowseList();

    /**
     * The directory with the given path has been added to the set of shared files
     * 
     * @param directory filename of the directory which has been added
     */
    public void directoryAdded(
        String directory );

    /**
     * The file with the given filename has been hashed
     * 
     * @param filename
     */
    public void fileHashed(
        String filename );

    /**
     * The file with the given filename is being hashed
     * 
     * @param filename filename of the file which is being hashed
     * @param percent state of the hashing process
     */
    public void hashingFile(
        String filename,
        double percent );
}