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

import java.util.Arrays;

import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.util.FileUtils;

/**
 * <CODE>DownDownloadRequestNode</CODE> represents a <CODE>DownloadNode
 * </CODE> in the <CODE>DownloadComponent</CODE> for a single <CODE>
 * DownloadRequest</CODE>
 * under an active Client <CODE>Connection</CODE>
 * 
 * @author Timo Westk�mper
 */
public class DownDownloadRequestNode
    implements
        IDownloadNode
{
    private final IConnection     connection;

    private final DownloadRequest request;

    /**
     * Create a DownDownloadRequestNode instance
     * 
     * @param _connection IConnection instance to be used
     * @param _request DownloadRequest instance to be used
     */
    public DownDownloadRequestNode(
        IConnection _connection,
        DownloadRequest _request )
    {

        if ( _connection == null )
        {
            throw new NullPointerException( "connection was null" );
        }

        if ( _request == null )
        {
            throw new NullPointerException( "request was null" );
        }

        connection = _connection;
        request = _request;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.IDownloadNode#getChild(int)
     */
    public IDownloadNode getChild(
        int i )
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.IDownloadNode#getMainObject()
     */
    public Object getMainObject()
    {
        return request;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.IDownloadNode#getNumChildren()
     */
    public int getNumChildren()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.IDownloadNode#getValueAt(int)
     */
    public Object getValueAt(
        int column )
    {
        String name;

        if ( request.getSearchResult() != null )
        {
            name = request.getSearchResult().getFilename();

        }
        else
        {
            name = EMPTY_STRING;

        }

        switch ( column )
        {

            case 0:
                return request.getSearchResult().getNick();

            case 1:
                return FileUtils.getNameNoExtension( name );

            case 2:
                return FileUtils.getExtension( name );

            case 3:
                return FileUtils.getPath( name );

            case 4:
                return EMPTY_STRING;

            case 5:
                return request.getState().toString();

            case 6:
                return EMPTY_STRING;
        }

        return EMPTY_STRING;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.IDownloadNode#isValid()
     */
    public boolean isValid()
    {
        if ( connection.getState() != ConnectionState.NOT_CONNECTED )
        {
            DownloadRequest[] drs = connection.getClient().getDownloads();

            if ( Arrays.asList( drs ).contains( request ) )
            {
                return true;

            }
            else
            {
                return false;

            }

        }
        else
        {
            return false;

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return EMPTY_STRING;
    }

}

/*******************************************************************************
 * $Log: DownDownloadRequestNode.java,v $ Revision 1.8 2005/10/02 11:42:28 timowest updated sources and tests Revision
 * 1.7 2005/09/15 17:32:29 timowest added null checks Revision 1.6 2005/09/14 07:11:49 timowest updated sources
 */
