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

// $Id: DirectoryListModel.java,v 1.11 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import org.apache.log4j.Logger;

/**
 * <CODE>DirectoryListModel</CODE> is an implementation of the <CODE>
 * ListModel</CODE> interface used to display hierarchical directory structures
 * 
 * @author Ryan Sweny
 * @version $Revision: 1.11 $ $Date: 2005/10/02 11:42:28 $
 */
public class DirectoryListModel extends AbstractListModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1086886749372007114L;

    private static final Logger logger = Logger
            .getLogger(DirectoryListModel.class);

    private final List shares = new ArrayList();

    /**
     * Create an empty DirectoryListModel
     */
    public DirectoryListModel() {

    }

    /** ********************************************************************** */

    /**
     * Add the given directory to the list of shared files
     * 
     * @param s
     */
    public final void addDirectory(String s) {
        if (!shares.contains(s)) {
            shares.add(s);
            update();
        } else {
            logger.error("Directory " + s + " was already contained");
        }

    }

    /**
     * Get the list of shared files
     * 
     * @return
     */
    public final List getDirList() {
        return shares;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public final Object getElementAt(int index) {
        return shares.get(index);

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ListModel#getSize()
     */
    public final int getSize() {
        return shares.size();

    }

    /**
     * Remove the given directory from the list of shared files
     * 
     * @param s
     */
    public final void removeDirectory(String s) {
        if (s == null)
            throw new NullPointerException("s was null.");

        shares.remove(s);
        update();

    }

    /**
     * Removed the directory with the given index from the list of shared files
     * 
     * @param n
     */
    public final void removeDirectoryAt(int n) {
        shares.remove(n);
        update();

    }

    /**
     * Notify registered listeners that the contents have been changed
     */
    private final void update() {
        fireContentsChanged(this, 0, getSize());

    }

}

/*******************************************************************************
 * $Log: DirectoryListModel.java,v $
 * Revision 1.11  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.10 2005/09/26 17:19:52 timowest
 * updated sources and tests
 * 
 * Revision 1.9 2005/09/15 17:32:29 timowest added null checks
 * 
 * Revision 1.8 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
