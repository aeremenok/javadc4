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

import java.util.List;

import net.sf.javadc.interfaces.IConnection;

/**
 * <CODE>DownRootNode</CODE> represents the root node in the <CODE>
 * DownloadComponent</CODE>
 * 
 * @author Timo Westk�mper
 */
public class DownRootNode implements IDownloadNode {

    private final static DownloadNodeFactory nodeFactory = new DownloadNodeFactory();

    private final List connections;

    /**
     * Create a DownRootNode instance with the given list of active connections
     * 
     * @param _connections
     */
    public DownRootNode(List _connections) {
        if (_connections == null)
            throw new NullPointerException("_connections was null");

        connections = _connections;

    }

    /**
     * Return the child node with the given index
     * 
     * @param i
     *            index of the child node to be returned
     * @return child node if found or null, if not
     */
    public final IDownloadNode getChild(int i) {
        // uses type information

        return nodeFactory.createConnectionNode((IConnection) connections
                .get(i));

    }

    /**
     * Return the number of children of this DownloadNode
     * 
     * @return
     */
    public final int getNumChildren() {

        return connections.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.IDownloadNode#getValueAt(int)
     */
    public Object getValueAt(int column) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.IDownloadNode#getMainObject()
     */
    public Object getMainObject() {
        return connections;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.IDownloadNode#isValid()
     */
    public boolean isValid() {
        // always valid, because Singleton
        return true;
    }

}

/*******************************************************************************
 * $Log: DownRootNode.java,v $
 * Revision 1.9  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.8 2005/09/15 17:32:29 timowest added
 * null checks
 * 
 * Revision 1.7 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
