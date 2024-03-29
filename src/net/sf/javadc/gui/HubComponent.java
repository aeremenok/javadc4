/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: HubComponent.java,v 1.23 2006/05/30 14:20:37 pmoukhataev Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import junit.framework.Assert;
import net.sf.javadc.gui.util.SplitPane;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.HubListener;
import net.sf.javadc.listeners.HubListenerBase;
import net.sf.javadc.net.Message;
import net.sf.javadc.net.hub.HubUser;
import net.sf.javadc.util.FileUtils;

import org.apache.log4j.Category;

import spin.Spin;

// import java.util.ArrayList;

/**
 * <CODE>HubComponent</CODE> provides a tabbed view with a <CODE>
 * MessageComponent</CODE> in the first tab and a
 * <CODE>SearchComponent
 * </CODE> in the second tab
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.23 $ $Date: 2006/05/30 14:20:37 $
 */
public class HubComponent
    extends JPanel
{
    private final class MyHubListener
        extends HubListenerBase
    {
        private final MessageComponent messageComponent1;

        private MyHubListener(
            MessageComponent messageComponent )
        {
            super();
            this.messageComponent1 = messageComponent;
        }

        @Override
        public final void gotMessage(
            IHub hub1,
            Message message )
        {
            Assert.assertNotNull( hub1 );
            Assert.assertNotNull( message );

            messageComponent1.addMessage( message );

            logger.debug( "Received Message " + message + " for " + hub1 );
        }

        @Override
        public final void gotUserMessage(
            IHub hub1,
            Message message )
        {
            Assert.assertNotNull( hub1 );
            Assert.assertNotNull( message );

            addUserMessageTab( message.getFrom() ).addMessage( message );
            logger.debug( "Received User Message " + message + " for " + hub1 );
        }

        @Override
        public void hubDisconnected(
            IHub hub1 )
        {
            Assert.assertNotNull( hub1 );

            // deactivate the HubComponent, but don't hide it
            HubComponent.this.setActive( false );

            logger.debug( "Hub " + hub1 + " has been disconnected." );
        }

    }

    private static final long              serialVersionUID = -5374039091287814873L;

    private final static Category          logger           = Category.getInstance( HubComponent.class );
    private final JButton                  disconnectButton;

    // private final JPanel hubPane;
    private final JPopupMenu               popup            = new JPopupMenu();
    private final JTabbedPane              tabPane          = new JTabbedPane();
    private final MessageComponent         messageComponent;
    private final SearchComponent          searchComponent;
    private final SplitPane                splitPane;
    private final JToolBar                 toolBar;
    private final UserListComponent        userList;
    private final HubListener              hubListener;
    private boolean                        active           = true;

    // external components
    private final IHub                     hub;
    private final ISettings<EventListener> settings;
    private final IDownloadManager         downloadManager;

    /**
     * Create a HubComponent which uses the given IHub and ISettings instance
     * 
     * @param _settings ISettigns instance to be used
     * @param _hub IHub instance this component is related to
     */
    public HubComponent(
        IHub _hub,
        ISettings<EventListener> _settings,
        IDownloadManager _downloadManager )
    {
        super( new BorderLayout() );
        Assert.assertNotNull( _hub );
        Assert.assertNotNull( _settings );
        Assert.assertNotNull( _downloadManager );

        hub = _hub;

        settings = _settings;

        // already spinned
        downloadManager = _downloadManager;

        // add a toolbar to the hub for things such as reconnect, close etc
        toolBar = new JToolBar();
        toolBar.setFloatable( false );
        toolBar.putClientProperty( "JToolBar.isRollover", Boolean.TRUE );

        disconnectButton = new JButton( "Disconnect" );
        disconnectButton.setIcon( FileUtils.loadIcon( "images/16/remove.png" ) );

        disconnectButton.setMargin( new Insets( 0, 4, 0, 4 ) );
        toolBar.add( disconnectButton );

        disconnectButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                hub.disconnect();
            }
        } );

        // add(toolBar, BorderLayout.NORTH);

        JMenuItem close = new JMenuItem( "Close" );
        close.setIcon( FileUtils.loadIcon( "images/16/remove.png" ) );
        close.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                int index = tabPane.getSelectedIndex();

                if ( index >= 2 )
                {
                    tabPane.remove( index );
                }
            }
        } );

        popup.add( close );

        // Main Component
        messageComponent = new MessageComponent( this, true, null, settings );
        hubListener = new MyHubListener( messageComponent );

        // hub = (IHub)Spin.off(_hub);
        hub.addListener( (HubListener) Spin.over( hubListener ) );

        tabPane.addTab( "Main", FileUtils.loadIcon( "images/16/mail_generic.png" ), messageComponent );

        // Search Component
        searchComponent = new SearchComponent( hub, settings, downloadManager );
        tabPane.addTab( "Search", FileUtils.loadIcon( "images/16/find.png" ), searchComponent );

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
                    popup.show( e.getComponent(), e.getX(), e.getY() );
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
                    popup.show( e.getComponent(), e.getX(), e.getY() );
                    logger.debug( "Event was a Popup trigger." );
                }
                else
                {
                    logger.debug( "Event was no Popup trigger." );
                }
            }
        } );

        userList = new UserListComponent( hub, this, settings, downloadManager );

        /* userList.setMinimumSize(new Dimension(250, 0)); */
        splitPane = new SplitPane( SplitPane.HORIZONTAL_SPLIT, tabPane, userList );

        splitPane.setDividerLocation( 0.8 );
        splitPane.setResizeWeight( 1 );

        add( splitPane, BorderLayout.CENTER );

        logger.debug( "HubComponent for Hub " + hub + " has been created." );
    }

    /**
     * Add a tab to browse the contents of a users' browse list
     * 
     * @param nick
     */
    public final void addUserBrowseTab(
        final String nick )
    {
        if ( !active )
        {
            logger.debug( "addUserBrowseTab(String nick) can't be invoked, because Hub is not active anymore." );
            return;
        }

        String name = nick + " (File List)";

        FileBrowseComponent fbc = new FileBrowseComponent( hub, nick, settings, downloadManager );

        int i = getIndexOfUserMessageComponent( name );

        if ( i == -1 )
        {
            tabPane.addTab( name, fbc );
            i = tabPane.getTabCount() - 1;
        }

        tabPane.setSelectedIndex( i );
    }

    /**
     * Add a message tab for the messages of the given user
     * 
     * @param name
     * @return
     */
    public final MessageComponent addUserMessageTab(
        String name )
    {
        if ( !active )
        {
            logger.debug( "addUserMessageTabe(String name) can't be invoked, because Hub is not active anymore." );
            return null;
        }

        int i = getIndexOfUserMessageComponent( name );

        if ( i == -1 )
        {
            ImageIcon loadIcon = FileUtils.loadIcon( "images/16/mail_generic.png" );
            tabPane.addTab( name, loadIcon, new MessageComponent( this, false, name, settings ) );
            i = tabPane.getTabCount() - 1;
        }

        tabPane.setSelectedIndex( i );

        return (MessageComponent) tabPane.getComponentAt( i );
    }

    /**
     * Called when the browse list of the given HubUser has been decoded
     * 
     * @param hub1
     * @param ui1
     */
    public final void browseListDecoded(
        @SuppressWarnings( "unused" ) IHub hub1,
        HubUser ui1 )
    {
        if ( !active )
        {
            logger
                .debug( "browseListDecoded(IHub hub, HubUser ui) can't be invoked, because Hub is not active anymore." );
            return;
        }

        addUserBrowseTab( ui1.getNick() );
    }

    /**
     * Get the Hub instance this component is related to
     * 
     * @return
     */
    public final IHub getHub()
    {
        return hub;
    }

    /**
     * @return Returns the active.
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * @param active The active to set.
     */
    public void setActive(
        boolean active )
    {
        this.active = active;
    }

    /**
     * Get the index of the message component for the given user nick
     * 
     * @param name nick name of the user for which the tab index should be retrieved
     * @return
     */
    private final int getIndexOfUserMessageComponent(
        String name )
    {
        return tabPane.indexOfTab( name );
    }
}
