/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: MainFrame.java,v 1.31 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.EventListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import junit.framework.Assert;
import net.sf.javadc.Main;
import net.sf.javadc.config.AdvancedSettings;
import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.config.gui.SettingsDialog;
import net.sf.javadc.gui.util.StatusBar;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.listeners.SettingsListener;
import net.sf.javadc.listeners.ShareManagerListener;
import net.sf.javadc.listeners.ShareManagerListenerBase;
import net.sf.javadc.themes.LAFManager;
import net.sf.javadc.util.FileUtils;

import org.apache.log4j.Category;
import org.picocontainer.Startable;

import spin.Spin;

/**
 * <CODE>MainFrame</CODE> represents the main frame of the application and contains beside the <CODE>MainMenuBar</CODE>
 * the view components <CODE>
 * ManagerComponent</CODE>,<CODE>MultiSearchComponent</CODE>,<CODE>
 * MonitorComponent</CODE>,
 * <CODE>IncompleteComponent</CODE> and <CODE>
 * FileBrowseComponent</CODE>
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.31 $ $Date: 2005/10/02 11:42:28 $
 */
public class MainFrame
    extends JFrame
    implements
        Startable
{
    private class MyActionListener
        implements
            ActionListener
    {

        public final void actionPerformed(
            ActionEvent e )
        {
            JMenuItem menuClicked = (JMenuItem) e.getSource();

            final String menuText = menuClicked.getText();
            final String menuName = menuClicked.getName();

            if ( menuText == "Preferences" )
            {
                try
                {
                    settingsDialog = new SettingsDialog( MainFrame.this, "Preferences", true, settings, shareManager );
                    settingsDialog.showSettings();
                }
                catch ( Exception ex )
                {
                    logger.debug( ex.toString() );
                }
            }
            else if ( menuText == "Close" )
            {
                Main.close();
            }
            else if ( menuText == "Hubs" )
            {
                tabPane.setSelectedIndex( 0 );

            }
            else if ( menuText == "Search" )
            {
                tabPane.setSelectedIndex( 1 );

            }
            else if ( menuText == "Monitor" )
            {
                tabPane.setSelectedIndex( 2 );

            }
            else if ( menuText == "Library" )
            {
                tabPane.setSelectedIndex( 3 );

            }
            else
            {
                String look = null;
                String theme = null;

                if ( menuName.indexOf( "Plastic" ) != -1 )
                {
                    String[] tok = menuName.split( "_" );

                    look = tok[0];
                    theme = tok[1];

                }
                else
                {
                    look = menuText;

                }

                settings.getGuiSettings().setLookAndFeel( look );
                settings.getGuiSettings().setTheme( theme );
                themeManager.configureUI( look );
                SwingUtilities.updateComponentTreeUI( MainFrame.this );

            }

        }

    }

    private class MySettingsListener
        implements
            SettingsListener
    {

        public void downloadSlotsChanged(
            int used,
            int total )
        {
            statusBar.updateDownloadSlots( used, total );
        }

        public void uploadSlotsChanged(
            int used,
            int total )
        {
            statusBar.updateUploadSlots( used, total );
        }

    }

    private class MyTreeHashingListener
        extends ShareManagerListenerBase
    {
        @Override
        public void fileHashed(
            String filename )
        {
            statusBar.updateHashedFile( filename );
        }

        @Override
        public void hashingFile(
            String filename,
            double percent )
        {
            NumberFormat nf = NumberFormat.getPercentInstance();
            statusBar.updateHashingFile( filename, nf.format( percent ) );
        }
    }

    private static final long                         serialVersionUID     = 1730490179377255746L;

    private final static Category                     logger               = Category.getInstance( MainFrame.class );

    private final JTabbedPane                         tabPane              = new JTabbedPane();

    // listeners
    private final SettingsListener                    settingsListener     = new MySettingsListener();

    private final ShareManagerListener                shareManagerListener = new MyTreeHashingListener();

    private final ActionListener                      menuListener         = new MyActionListener();
    // gui components
    private final ManagerComponent                    hubManagerComponent;
    private final MultiSearchComponent                multiSearchComponent;

    private final MonitorComponent                    slotComponent;
    // private final IncompleteComponent incompleteComponent;
    private final FileBrowseComponent                 fileBrowseComponent;
    private final StatusBar                           statusBar            = new StatusBar();

    // other components
    private final ISettings<EventListener>            settings;

    private final IShareManager<ShareManagerListener> shareManager;

    // private final IDownloadManager downloadManager;

    // private final ITreeHashingManager treeHashingManager;
    private final LAFManager                          themeManager;

    private SettingsDialog                            settingsDialog;

    /**
     * Create a MainFrame with the given components
     * 
     * @param _settings ISettings instance to be used
     * @param _themeManager ThemeManager to be used
     * @param _slotComponent MonitorComponent to be used
     * @param _hubManagerComponent HubManagerComponent to be used
     * @param _multiSearchComponent MultiSearchComponent to be used
     * @param _shareManager IShareManager instance to be used
     * @param _downloadManager IDownloadManager instance to be used
     */
    public MainFrame(
        ISettings<EventListener> _settings,
        LAFManager _themeManager,
        MonitorComponent _slotComponent,
        ManagerComponent _hubManagerComponent,
        MultiSearchComponent _multiSearchComponent,
        IShareManager _shareManager,
        IDownloadManager _downloadManager )
    {

        super( ConstantSettings.MAINFRAME_TITLE );

        Assert.assertNotNull( _settings );
        Assert.assertNotNull( _themeManager );
        Assert.assertNotNull( _slotComponent );
        Assert.assertNotNull( _hubManagerComponent );
        Assert.assertNotNull( _multiSearchComponent );
        Assert.assertNotNull( _shareManager );
        Assert.assertNotNull( _downloadManager );

        hubManagerComponent = _hubManagerComponent;
        multiSearchComponent = _multiSearchComponent;
        slotComponent = _slotComponent;
        // incompleteComponent = _incompleteComponent;
        fileBrowseComponent = new FileBrowseComponent( _settings, _downloadManager );

        shareManager = (IShareManager<ShareManagerListener>) Spin.off( _shareManager );
        // treeHashingManager = (ITreeHashingManager)
        // Spin.off(_treeHashingManager);

        settings = _settings;
        themeManager = _themeManager;

        // /downloadManager = (IDownloadManager) Spin.off(_downloadManager);

        settings.addListener( (SettingsListener) Spin.over( settingsListener ) );

        // treeHashingManager.addListener(treeHashingListener);
        shareManager.addListener( (ShareManagerListener) Spin.over( shareManagerListener ) );

        settingsDialog = new SettingsDialog( this, "Preferences", true, settings, shareManager );

        // sets systray properties

        // sets up components
        setupComponents();

        setJMenuBar( new MainMenuBar( menuListener, themeManager, settings ) );

        // sets size and location
        setLocation( settings.getAdvancedSettings().getXLocation(), settings.getAdvancedSettings().getYLocation() );

        int w = settings.getAdvancedSettings().getXSize();

        if ( w == 0 )
        {
            logger.debug( "Using screen width as default width." );
            w = getToolkit().getScreenSize().width;
        }

        int h = settings.getAdvancedSettings().getYSize();

        if ( h == 0 )
        {
            logger.debug( "Using screen height as default height." );
            h = getToolkit().getScreenSize().height;
        }

        setSize( w, h );

        TrayMenu.createTrayMenu();
    }

    public void showhide()
    {
        boolean visible = isVisible();
        setVisible( visible );
        if ( isVisible() )
        {
            if ( JFrame.ICONIFIED == getExtendedState() )
            {
                setExtendedState( JFrame.NORMAL );
            }
        }
    }

    public void showSettings()
    {
        settingsDialog = new SettingsDialog( MainFrame.this, "Preferences", true, settings, shareManager );
        settingsDialog.showSettings();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public void start()
    {
        logger.debug( "starting " + this.getClass().getName() );

        addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing(
                WindowEvent e )
            {
                Main.close();
            }
        } );
        // setVisible(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public void stop()
    {
        logger.debug( "stopping " + this.getClass().getName() );

        final AdvancedSettings advanced = settings.getAdvancedSettings();

        advanced.setXLocation( getX() );
        advanced.setYLocation( getY() );

        advanced.setXSize( getWidth() );
        advanced.setYSize( getHeight() );

        setVisible( false );
    }

    /**
     * Set up the visual components of the MainFrame
     */
    private final void setupComponents()
    {
        setIconImage( FileUtils.loadIcon( "images/16/javadc.png" ).getImage() );

        // Tab Pane
        tabPane.addTab( "Hubs", FileUtils.loadIcon( "images/16/network_local.png" ), hubManagerComponent );
        tabPane.addTab( "Search", FileUtils.loadIcon( "images/16/find.png" ), multiSearchComponent );
        tabPane.addTab( "Monitor", FileUtils.loadIcon( "images/16/list.png" ), slotComponent );

        JLabel fileBrowseLabel = new JLabel( "SharedFiles" );

        fileBrowseLabel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );

        JPanel fileBrowsePanel = new JPanel( new BorderLayout() );

        fileBrowsePanel.add( fileBrowseLabel, BorderLayout.NORTH );
        fileBrowsePanel.add( fileBrowseComponent, BorderLayout.CENTER );

        tabPane.addTab( "Library", FileUtils.loadIcon( "images/16/video.png" ), fileBrowsePanel );

        statusBar.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );

        // Content Pane
        getContentPane().setLayout( new BorderLayout() );
        getContentPane().add( tabPane, BorderLayout.CENTER );
        getContentPane().add( statusBar, BorderLayout.SOUTH );
    }
}
