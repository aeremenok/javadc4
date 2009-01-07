/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.gui.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import net.sf.javadc.gui.HubListComponentListener;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.IHubInfo;
import net.sf.javadc.util.FileUtils;
import net.sf.javadc.util.GenericModel;

public class HubFavoritesTableListener
    extends GenericModel
    implements
        SortableTableListener
{

    private final RowTableModel     model;

    // components
    // private final IHubList hubList;
    private final IHubFavoritesList favoritesList;

    private final IHubFactory       hubFactory;

    /**
     * Create a HubFavoritesTableListener with the given RowTableModel, IHubList instance, IHubFavoritesList instance
     * and IHubFactory
     * 
     * @param _model RowTableModel to be used
     * @param _favoritesList IHubFavoritesList to be used
     * @param _hubFactory IHubFactory to be used
     */
    public HubFavoritesTableListener(
        RowTableModel _model,
        IHubFavoritesList _favoritesList,
        IHubFactory _hubFactory )
    {

        if ( _model == null )
        {
            throw new NullPointerException( "model was null" );
        }

        if ( _favoritesList == null )
        {
            throw new NullPointerException( "favoritesList was null" );
        }

        if ( _hubFactory == null )
        {
            throw new NullPointerException( "hubFactory was null" );
        }

        model = _model;

        // Timo : 31.05.2004
        // already spinned in HubListComponent
        // hubList = _hubList;
        favoritesList = _favoritesList;
        hubFactory = _hubFactory;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.SortableTableListener#cellSelected(int, int)
     */
    public final void cellSelected(
        int row,
        int column,
        int[] selectedRows )
    {

        // multi selection

        for ( int i = 0; i < selectedRows.length; i++ )
        {
            IHub hub = hubFactory.createHub( (IHubInfo) model.getRow( selectedRows[i] ) );
            fireHubSelected( hub );

        }

    }

    /**
     * Notify registered listeners that the given Hub instance has been selected
     * 
     * @param hub
     */
    public final void fireHubSelected(
        IHub hub )
    {
        final HubListComponentListener[] listeners = listenerList.getListeners( HubListComponentListener.class );

        for ( int i = 0; i < listeners.length; i++ )
        {
            listeners[i].hubSelected( hub );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.SortableTableListener#showPopupClicked(int,
     *      int, java.awt.event.MouseEvent)
     */
    public final void showPopupClicked(
        final int row,
        final int column,
        MouseEvent e,
        final int[] selectedRows )
    {
        final JPopupMenu popup = new JPopupMenu();
        final JMenuItem connected = new JMenuItem( "Connect" );
        connected.setIcon( FileUtils.loadIcon( "images/16/connect_established.png" ) );
        connected.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {
                // multi selection

                cellSelected( row, column, selectedRows );

            }

        } );

        final JMenuItem favorites = new JMenuItem( "Remove from Favorites" );
        favorites.setIcon( FileUtils.loadIcon( "images/16/edit_remove.png" ) );
        favorites.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {
                favoritesList.removeHub( (IHubInfo) model.getRow( row ) );

                // favoritesList.update();
            }

        } );

        popup.add( connected );
        popup.add( favorites );

        popup.show( e.getComponent(), e.getX(), e.getY() );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    protected Class getListenerClass()
    {
        return HubListComponentListener.class;

    }

}

/*******************************************************************************
 * $Log: HubFavoritesTableListener.java,v $ Revision 1.12 2005/10/02 11:42:28 timowest updated sources and tests
 * Revision 1.11 2005/09/15 17:32:29 timowest added null checks Revision 1.10 2005/09/14 07:11:49 timowest updated
 * sources
 */
