/*
 * Created on 28.12.2004
 */
package net.sf.javadc.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.IHubFavoritesLoader;
import net.sf.javadc.interfaces.IHubList;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseHubFavoritesLoader;
import net.sf.javadc.mockups.BaseHubList;
import net.sf.javadc.mockups.BaseHubTaskFactory;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.HubFactory;
import net.sf.javadc.net.hub.HubFavoritesList;
import net.sf.javadc.net.hub.HubManager;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westkämper
 */
public class ManagerComponentSimulation
{

    private TaskManager         taskManager        = new TaskManager();

    private ISettings           settings           = new Settings();

    /*
     * public ManagerComponent(IHubManager _hubManager, IHubFavoritesList
     * _hubFavoritesList, HubListComponent _hubListComponent, ISettings
     * _settings, IDownloadManager _downloadManager)
     */

    private IHubManager         hubManager         = new HubManager();

    private IHubFavoritesLoader hubFavoritesLoader = new BaseHubFavoritesLoader();

    private IHubFavoritesList   hubFavoritesList   = new HubFavoritesList( hubFavoritesLoader );

    /*
     * public HubListComponent(final IHubList _hubList, final IHubFavoritesList
     * _hubFavoritesList, final IHubFactory _hubFactory, ISettings _settings)
     */

    // private IHubList hubList = new HubList(settings, taskManager);
    private IHubList            hubList            = new BaseHubList();

    /*
     * public HubFactory(ITaskManager _taskManager, IHubManager _hubManager,
     * IHubTaskFactory _hubTaskFactory, ISettings _settings)
     */

    // private IHubFactory hubFactory = new BaseHubFactory();
    private IHubTaskFactory     hubTaskFactory     = new BaseHubTaskFactory();

    private IHubFactory         hubFactory         = new HubFactory( taskManager, hubManager, hubTaskFactory, settings );

    private HubListComponent    hubListComponent   = new HubListComponent( hubList, hubFavoritesList, hubFactory );

    private ISegmentManager     segmentManager     = new SegmentManager( settings );

    private IDownloadManager    downloadManager    = new DownloadManager( hubManager, segmentManager );

    private ManagerComponent    managerComponent   =
                                                       new ManagerComponent( hubManager, hubFavoritesList,
                                                           hubListComponent, settings, downloadManager );

    public static void main(
        String[] args )
    {
        new ManagerComponentSimulation();
    }

    public ManagerComponentSimulation()
    {
        taskManager.start();
        hubFavoritesList.start();

        hubList.fireHubListChanged();

        JFrame frame = new JFrame();

        frame.getContentPane().add( managerComponent );

        frame.setLocation( 100, 100 );
        frame.setSize( 800, 600 );

        frame.addWindowListener( new WindowAdapter()
        {

            @Override
            public void windowClosing(
                WindowEvent e )
            {
                System.exit( 0 );

            }

        } );

        frame.setVisible( true );

        try
        {
            while ( true )
            {
                Thread.sleep( 10000 );

            }

        }
        catch ( InterruptedException e )
        {
            // logger.error(e.toString());
            System.out.println( e.toString() );

        }
    }

}

/*******************************************************************************
 * $Log: ManagerComponentSimulation.java,v $ Revision 1.7 2005/10/02 11:42:28 timowest updated sources and tests
 * Revision 1.6 2005/09/14 07:11:49 timowest updated sources
 */
