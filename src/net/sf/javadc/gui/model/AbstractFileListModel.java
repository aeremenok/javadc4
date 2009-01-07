/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.gui.model;

import net.sf.javadc.listeners.FileListModelListener;
import net.sf.javadc.util.GenericModel;

/**
 * <CODE>AbstractFileListModel</CODE> is an abstract super class for the <CODE>FileListModel</CODE>, which provides the
 * main methods use in the Observer / Observable design pattern
 * 
 * @author Timo Westk�mper
 */
public abstract class AbstractFileListModel
    extends GenericModel
{

    /**
     * @param source
     * @param path
     * @param childIndices
     * @param children
     */
    public void fireTreeNodesInserted(
        Object source,
        Object[] path,
        int[] childIndices,
        Object[] children )
    {

        FileListModelListener[] listeners = getFileListModelListeners();

        for ( int i = 0; i < listeners.length; i++ )
        {
            listeners[i].treeNodesInserted( source, path, childIndices, children );
        }
    }

    /**
     * @param source
     * @param path
     * @param childIndices
     * @param children
     */
    public void fireTreeStructureChanged(
        Object source,
        Object[] path,
        int[] childIndices,
        Object[] children )
    {

        FileListModelListener[] listeners = getFileListModelListeners();

        for ( int i = 0; i < listeners.length; i++ )
        {
            listeners[i].treeStructureChanged( source, path, childIndices, children );
        }

    }

    /**
     * @return
     */
    private FileListModelListener[] getFileListModelListeners()
    {

        return listenerList.getListeners( FileListModelListener.class );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    protected Class getListenerClass()
    {
        return FileListModelListener.class;

    }

}

/*******************************************************************************
 * $Log: AbstractFileListModel.java,v $ Revision 1.10 2005/10/02 11:42:28 timowest updated sources and tests Revision
 * 1.9 2005/09/14 07:11:49 timowest updated sources
 */
