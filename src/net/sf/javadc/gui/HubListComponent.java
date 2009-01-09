/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: HubListComponent.java,v 1.24 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import junit.framework.Assert;
import net.sf.javadc.gui.model.HubFavoritesTableListener;
import net.sf.javadc.gui.model.HubListTableListener;
import net.sf.javadc.gui.model.RowTableModel;
import net.sf.javadc.gui.model.SortableTable;
import net.sf.javadc.gui.util.ByteCellRenderer;
import net.sf.javadc.gui.util.SplitPane;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.IHubInfo;
import net.sf.javadc.interfaces.IHubList;
import net.sf.javadc.listeners.HubListListener;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.util.FileUtils;

import org.apache.log4j.Category;

import spin.Spin;

/**
 * <CODE>HubListComponent</CODE> provides a searchable table view on the contents of the <CODE>HubList</CODE> and
 * <CODE>HubFavoritesList</CODE>
 * 
 * @author Timo Westk�mper
 */
public class HubListComponent
    extends JPanel
{
    private static final long               serialVersionUID        = -6223300690555343189L;

    private static final Category           logger                  = Category.getInstance( HubListComponent.class );

    private final RowTableModel             model                   =
                                                                        new RowTableModel( new String[] { "Name",
                    "Address", "Description", "Country", "Users", "Minshare" } );

    private final RowTableModel             modelFavorites          =
                                                                        new RowTableModel( new String[] { "Name",
                    "Address", "Description", "Country", "Users", "Minshare" } );

    // private final TableModelFilterDecorator filteredModel =
    // new TableModelFilterDecorator(model);
    private final HubListTableListener      hubListTableListener;
    private final HubFavoritesTableListener hubFavoritesTableListener;

    // gui components
    private final SortableTable             hubsTable;
    private final SortableTable             hubsTableFavorites;

    private final JPanel                    hubsTablePanel          = new JPanel( new BorderLayout() );
    private final JPanel                    hubsTableFavoritesPanel = new JPanel( new BorderLayout() );
    private final JLabel                    hubListLabel            = new JLabel( "Public hubs" );
    private final JLabel                    hubFavoritesLabel       = new JLabel( "Favorite hubs" );
    private final JToolBar                  toolBar                 = new JToolBar();
    private final JTextField                hostField               = new JTextField();
    private final JButton                   updateButton            = new JButton( "Update" );
    private final JButton                   filterButton            = new JButton( "Filter" );

    // private final JTextField filterText = new JTextField();
    private final JComboBox                 filterText              = new JComboBox();

    // external components
    private final IHubList                  hubList;
    private final IHubFavoritesList         hubFavoritesList;
    private final IHubFactory               hubFactory;

    // private final ISettings settings;

    /**
     * Create a HubListComponent with the given IHubList, IHubFavoritesList, IHubFactory and ISettings
     * 
     * @param _hubList IHubList instance to be used
     * @param _hubFavoritesList IHubFavoritesList instance to be used
     * @param _hubFactory IHubFactory instance to be used
     */
    public HubListComponent(
        final IHubList _hubList,
        final IHubFavoritesList _hubFavoritesList,
        final IHubFactory _hubFactory )
    {
        super( new BorderLayout() );

        Assert.assertNotNull( _hubList );
        Assert.assertNotNull( _hubFactory );
        Assert.assertNotNull( _hubFavoritesList );

        // spinned networking components
        hubList = (IHubList) Spin.off( _hubList );
        hubFavoritesList = (IHubFavoritesList) Spin.off( _hubFavoritesList );
        hubFactory = (IHubFactory) Spin.off( _hubFactory );

        hubListTableListener = new HubListTableListener( model, hubFavoritesList, hubFactory );

        hubFavoritesTableListener = new HubFavoritesTableListener( modelFavorites, hubFavoritesList, hubFactory );

        // hublist

        // uses the filtered table model of the hub list
        hubsTable = new SortableTable( new int[] { 150, 150, -1, 75, 75, 75 }, hubListTableListener, model, "hubs" );

        hubsTable.getTable().setDefaultRenderer( Long.class, new ByteCellRenderer() );

        // favorite hubs

        hubsTableFavorites =
            new SortableTable( new int[] { 150, 150, -1, 75, 75, 75 }, hubFavoritesTableListener, modelFavorites,
                "hubs" );

        hubsTableFavorites.getTable().setDefaultRenderer( Long.class, new ByteCellRenderer() );

        hostField.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                hubListTableListener.fireHubSelected( hubFactory.createHub( new HostInfo( hostField.getText() ) ) );
            }
        } );

        hubList.addListener( (HubListListener) Spin.over( new HubListListener()
        {
            public void hubListChanged()
            {
                // IHubInfo[] hubInfos = hubList.getHubInfos();
                List hubInfos = hubList.getHubInfos();

                if ( hubInfos != null )
                {
                    model.clear();

                    for ( Iterator i = hubInfos.iterator(); i.hasNext(); )
                    {
                        IHubInfo hubInfo = (IHubInfo) i.next();

                        model.addRow( hubInfo, getHubColumns( hubInfo ) );
                    }
                }
            }
        } ) );

        hubFavoritesList.addListener( (HubListListener) Spin.over( new HubListListener()
        {
            public void hubListChanged()
            {
                List<IHubInfo> h = hubFavoritesList.getHubInfos();

                IHubInfo[] hubInfos;

                if ( h != null )
                {
                    hubInfos = h.toArray( new IHubInfo[h.size()] );
                }
                else
                {
                    logger.error( "HubInfos from hubFavoritesList was null." );
                    hubInfos = new IHubInfo[0];
                }

                modelFavorites.clear();

                for ( int i = 0; i < hubInfos.length; i++ )
                {
                    modelFavorites.addRow( hubInfos[i], getHubColumns( hubInfos[i] ) );
                }
            }
        } ) );

        toolBar.setFloatable( false );
        toolBar.putClientProperty( "JToolBar.isRollover", Boolean.TRUE );

        updateButton.setMargin( new Insets( 0, 4, 0, 4 ) );
        updateButton.setIcon( FileUtils.loadIcon( "images/16/refresh.gif" ) );

        updateButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                update();
            }
        } );

        toolBar.add( hubListLabel );
        hubListLabel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );

        toolBar.add( updateButton );

        filterText.setEditable( true );
        toolBar.add( filterText );

        filterButton.setMargin( new Insets( 0, 4, 0, 4 ) );

        filterButton.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {
                // String filter = filterText.getText();
                String filter = null;

                if ( filterText.getSelectedItem() != null )
                {
                    filter = filterText.getSelectedItem().toString();

                }

                filterText.addItem( filter );

                // filteredModel.addFilter(2, filter);
                hubList.setFilter( filter );
                logger.info( "Filter " + filter + " added." );
            }
        } );

        toolBar.add( filterButton );

        hubsTable.sortByColumn( 3, false );

        // Favorite Hubs
        hubFavoritesLabel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        hubsTableFavoritesPanel.add( hubFavoritesLabel, BorderLayout.NORTH );
        hubsTableFavoritesPanel.add( hubsTableFavorites );

        // Public Hus
        hubsTablePanel.add( toolBar, BorderLayout.NORTH );
        hubsTablePanel.add( hubsTable, BorderLayout.CENTER );

        SplitPane splitPane = new SplitPane( SplitPane.VERTICAL_SPLIT, hubsTableFavoritesPanel, hubsTablePanel );

        splitPane.setDividerLocation( 200 );
        add( splitPane, BorderLayout.CENTER );

        // add(hubsTable, BorderLayout.CENTER);
        add( hostField, BorderLayout.SOUTH );

        update();
    }

    /**
     * Add the given HubListComponentListener to the HubListTableListener and HubFavoritesTableListener
     * 
     * @param listener
     */
    public final void addListener(
        HubListComponentListener listener )
    {
        hubListTableListener.addListener( listener );
        hubFavoritesTableListener.addListener( listener );
    }

    /**
     * Update the contents of the HubList and HubFavoritesList
     */
    public final void update()
    {
        hubList.update();
        hubFavoritesList.update();
    }

    /**
     * Get the columns for the given HubInfo instance
     * 
     * @param hubInfo HubInfo instance for which the columns should be retrieved
     * @return
     */
    private final Object[] getHubColumns(
        IHubInfo hubInfo )
    {
        if ( hubInfo != null )
        {
            return new Object[] { hubInfo.getName(), hubInfo.getHost(), hubInfo.getDescription(), hubInfo.getCountry(),
                            new Integer( hubInfo.getUserCount() ),
                            // Long is used to render the given amount of bytes as a
                            // String
                            new Long( hubInfo.getMinshare() ) };
        }

        return null;
    }
}
