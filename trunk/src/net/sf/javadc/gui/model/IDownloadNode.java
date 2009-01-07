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

package net.sf.javadc.gui.model;

/**
 * <CODE>IDownloadNode</CODE> represents an abstract interface used to define
 * the public interface of nodes utilized in the <NODE>DownloadTreeModelAdapter</CODE>
 * 
 * @author Timo Westk�mper
 */
public interface IDownloadNode {

    public static String EMPTY_STRING = "";

    /**
     * Get the main object related to this DownloadNode instance
     * 
     * @return
     */
    public abstract Object getMainObject();

    /**
     * Return the child node with the given index
     * 
     * @param i
     *            index of the child node to be retrieved
     * @return
     */
    public abstract IDownloadNode getChild(int i);

    /**
     * Return the number of children
     * 
     * @return
     */
    public abstract int getNumChildren();

    /**
     * Return the value of the column with the given index
     * 
     * @param column
     * @return
     */
    public abstract Object getValueAt(int column);

    /**
     * Return whether this DownloadNode can be reused or a new one has to be
     * created
     * 
     * @return true, if it can be reused, and false, if not
     */
    public abstract boolean isValid();

}

/*******************************************************************************
 * $Log: IDownloadNode.java,v $
 * Revision 1.9  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.8 2005/09/14 07:11:49 timowest
 * updated sources
 * 
 * 
 * 
 */
