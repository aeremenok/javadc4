/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: ManagerComponent.java,v 1.23 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.HubListener;
import net.sf.javadc.listeners.HubListenerBase;
import net.sf.javadc.listeners.HubManagerListener;
import net.sf.javadc.net.hub.HubInfo;
import net.sf.javadc.net.hub.HubUser;
import net.sf.javadc.util.FileUtils;

import org.apache.log4j.Category;

import spin.Spin;

/**
 * <CODE>ManagerComponent</CODE> provides a tab view with the <CODE>
 * HubListComponent</CODE> as the fixed first tab and
 * connected hubs as subsequent tabs via <CODE>HubComponent</CODE> instances
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.23 $ $Date: 2005/10/02 11:42:28 $
 */
public class ManagerComponent
    extends JPanel
{
    private class MyHubListener
        extends HubListenerBase
    {
        private boolean updated = false;

        public void update(
            final IHub hub )
        {
            updated = true;

            ActionListener taskPerformer = new ActionListener()
            {

                public void actionPerformed(
                    ActionEvent evt )
                {
                    int index = getIndexOf( hub );

                    // update hub text only if hub is still active
                    if ( index > -1 )
                    {
                        tabPane.setTitleAt( index + 1, getTabString( hub ) );

                    }

                    updated = false;

                }

            };

            Timer timer = new Timer( ConstantSettings.MANAGERCOMPONENT_UPDATEINTERVAL, taskPerformer );

            timer.setRepeats( false );
            timer.start();

        }

        @Override
        public void userAdded(
            IHub hub,
            HubUser ui1 )
        {
            if ( !updated )
            {
                update( hub );

            }
        }

        @Override
        public void userRemoved(
            IHub hub,
            HubUser ui1 )
        {
            if ( !updated )
            {
                update( hub );
            }
        }
    }

    private class MyHubManagerListener
        implements
            HubManagerListener
    {

        public void hubAdded(
            IHub hub )
        {
            int index = getIndexOf( hub );

            if ( index > -1 )
            {
                logger.debug( "Hub " + hub + " has already been added." );
                return; // hub already added

            }

            logger.debug( "Adding Hub " + hub );

            hub.addListener( (HubListener) Spin.over( hubListener ) );
            hubs.add( hub );

            tabPane.addTab( getTabString( hub ), FileUtils.loadIcon( "images/16/network_local.png" ), new HubComponent(
                hub, settings, downloadManager ) );

        }

        public void hubRemoved(
            IHub hub )
        {
            int index = getIndexOf( hub );

            if ( index == -1 )
            {
                logger.debug( "Hub " + hub + " has not been found." );
                return; // not found

            }

            logger.debug( "Removing Hub " + hub );
            tabPane.setTitleAt( index + 1, getTabString( hub ) + " (offline)" );
            hub.removeListener( hubListener );
        }
    }

    private static final long              serialVersionUID = 4174061825052027243L;

    private final static Category          logger           = Category.getInstance( ManagerComponent.class );

    private final MyHubListener            hubListener      = new MyHubListener();
    private final HubListComponent         hubListComponent;

    private final IHubManager              hubManager;

    private final List<IHub>               hubs             = new ArrayList<IHub>();

    private final JPopupMenu               popup            = new JPopupMenu();
    private final JTabbedPane              tabPane          = new JTabbedPane();

    // external components
    private final ISettings<EventListener> settings;
    private final IHubFavoritesList        hubFavoritesList;
    private final IDownloadManager         downloadManager;

    /**
     * Create a ManagerComponent with the given IHubManager, IHubFavoritesList, HubListComponent and ISettings instance
     * 
     * @param _hubManager IHubManager instance to be used
     * @param _hubFavoritesList IHubFavoritesList to be used
     * @param _hubListComponent HubListComponent to be used
     * @param _settings ISettings instance to be used
     * @param _downloadManager IDownloadManager instance to be used
     */
    public ManagerComponent(
        IHubManager _hubManager,
        IHubFavoritesList _hubFavoritesList,
        HubListComponent _hubListComponent,
        ISettings<EventListener> _settings,
        IDownloadManager _downloadManager )
    {
        super( new BorderLayout() );

        if ( _hubManager == null )
        {
            throw new NullPointerException( "hubManager was null." );
        }

        if ( _hubFavoritesList == null )
        {
            throw new NullPointerException( "hubFavoritesList was null." );
        }

        if ( _hubListComponent == null )
        {
            throw new NullPointerException( "hubListComponent was null." );
        }

        if ( _settings == null )
        {
            throw new NullPointerException( "_settings was null." );
        }

        if ( _downloadManager == null )
        {
            throw new NullPointerException( "_downloadManager was null." );
        }

        // spinned components
        hubManager = (IHubManager) Spin.off( _hubManager );

        hubListComponent = _hubListComponent;
        settings = _settings;
        downloadManager = _downloadManager;
        hubFavoritesList = _hubFavoritesList;

        hubManager.addListener( (HubManagerListener) Spin.over( new MyHubManagerListener() ) );

        hubListComponent.addListener( new HubListComponentListener()
        {

            public void hubSelected(
                IHub hub )
            {
                int index = getIndexOf( hub );

                if ( index == -1 )
                {
                    hub.connect();

                }
                else
                {
                    tabPane.setSelectedIndex( index + 1 );

                }

            }

        } );

        // Scroll Tab Layout can't be used, because then the popup menus won't
        // work
        // tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        tabPane.addTab( "Hub List", FileUtils.loadIcon( "images/16/world.png" ), hubListComponent );

        add( tabPane, BorderLayout.CENTER );

        tabPane.addMouseListener( new MouseAdapter()
        {

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
             */
            @Override
            public void mousePressed(
                MouseEvent e )
            {
                if ( e.isPopupTrigger() )
                {
                    // int index = tabPane.getSelectedIndex();

                    // if (tabPane.indexOfComponent(hubListComponent) != index)
                    // {
                    popup.show( e.getComponent(), e.getX(), e.getY() );

                    // }

                    logger.debug( "Event was a Popup trigger." );

                }
                else
                {
                    logger.debug( "Event was no Popup trigger." );

                }

            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
             */
            @Override
            public void mouseReleased(
                MouseEvent e )
            {
                if ( e.isPopupTrigger() )
                {
                    // int index = tabPane.getSelectedIndex();

                    // if (tabPane.indexOfComponent(hubListComponent) != index)
                    // {
                    popup.show( e.getComponent(), e.getX(), e.getY() );

                    // }

                    logger.debug( "Event was a Popup trigger." );

                }
                else
                {
                    logger.debug( "Event was no Popup trigger." );

                }

            }

        } );

        JMenuItem close = new JMenuItem( "Close Hub" );

        close.setIcon( FileUtils.loadIcon( "images/16/remove.png" ) );

        close.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {
                int index = tabPane.getSelectedIndex();

                // index 0 is reserved for the HubListComponent
                if ( index > 0 )
                {
                    HubComponent hubComponent = (HubComponent) tabPane.getComponentAt( index );

                    // Hub connection is still active
                    if ( hubComponent.isActive() )
                    {
                        IHub hub = hubs.get( index - 1 );
                        hub.disconnect();
                    }

                    // HubComponent needs to be removed explicitly from the Tab
                    // as it not removed via the hubDisconnected notifications
                    tabPane.remove( index );

                    hubs.remove( index - 1 );

                }

            }

        } );

        popup.add( close );

        JMenuItem addToFavorites = new JMenuItem( "Add to Favorites" );

        addToFavorites.setIcon( FileUtils.loadIcon( "images/16/edit_add.png" ) );

        addToFavorites.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {
                int index = tabPane.getSelectedIndex();

                if ( index > 0 )
                {
                    IHub hub = hubs.get( index - 1 );

                    hubFavoritesList.addHubInfo( new HubInfo( hub ) );

                }

            }

        } );

        popup.add( addToFavorites );

    }

    /**
     * Get the tab index of the given IHub i
     * 
     * @param hub IHub instance for which the tab index is to be retrieved
     * @return
     */
    public final int getIndexOf(
        IHub hub )
    {
        int size = hubs.size();

        for ( int i = 0; i < size; i++ )
        {
            if ( hubs.get( i ).equals( hub ) )
            {
                return i; // hub was found
            }

        }

        return -1; // hub was not found

    }

    /**
     * Get the tab title for the given IHub
     * 
     * @param hub IHub instance for which the tab title is to be retrieved
     * @return
     */
    public final String getTabString(
        IHub hub )
    {
        StringBuffer name = new StringBuffer( hub.getName() );

        // shortens the hub name in the tab if it exceeds 20 chars
        int length = name.length();

        if ( length > 20 )
        {
            name.delete( 20, length - 1 );
            name.append( "..." );

        }

        // appends the user count
        name.append( " (" ).append( hub.getUserCount() ).append( " users)" );

        return name.toString();

    }

}

/*******************************************************************************
 * $Log: ManagerComponent.java,v $ Revision 1.23 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.22
 * 2005/09/25 16:40:58 timowest updated sources and tests Revision 1.21 2005/09/14 07:11:49 timowest updated sources
 */
