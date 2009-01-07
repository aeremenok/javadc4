/*
 * Created on 15.2.2005
 */

package net.sf.javadc.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;

import net.sf.javadc.config.Settings;
import net.sf.javadc.gui.model.IncompletesLoader;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseClientManager;
import net.sf.javadc.mockups.BaseHubFactory;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.RequestsModel;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.hub.AllHubs;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westk�mper
 */
public class IncompleteSimulation
{

    private ISettings           settings          = new Settings();

    private IHubManager         hubManager        = new HubManager();

    private AllHubs             allHubs           = new AllHubs( hubManager );

    private ISegmentManager     segmentManager    = new SegmentManager( settings );

    private IDownloadManager    downloadManager   = new DownloadManager( hubManager, segmentManager );

    private IncompletesLoader   incompletesLoader = new IncompletesLoader( settings, new BaseHubFactory() );

    private IClientManager      clientManager     = new BaseClientManager();

    private IConnectionManager  connectionManager = new ConnectionManager( settings );

    private IRequestsModel      requestsModel     =
                                                      new RequestsModel( settings, clientManager, connectionManager,
                                                          incompletesLoader, downloadManager, segmentManager );

    // component under test

    private IncompleteComponent incompleteComponent;

    public static void main(
        String[] args )
    {
        new IncompleteSimulation();
    }

    public IncompleteSimulation()
    {
        settings.setTempDownloadDir( "C:\\Temp" );

        DownloadRequest dr1 = new DownloadRequest( new SearchResult(), new File( "test1" ), settings );

        DownloadRequest dr2 = new DownloadRequest( new SearchResult(), new File( "test2" ), settings );

        requestsModel.getAllDownloads().add( dr1 );

        requestsModel.getAllDownloads().add( dr2 );

        /*
         * public IncompleteComponent(ISettings _settings, IRequestsModel
         * _requestsModel, IDownloadManager _downloadManager)
         */

        incompleteComponent = new IncompleteComponent( requestsModel, downloadManager, allHubs );

        // taskManager.start();
        // hubFavoritesList.start();

        // hubList.fireHubListChanged();

        JFrame frame = new JFrame();

        frame.getContentPane().add( incompleteComponent );

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
 * $Log: IncompleteSimulation.java,v $ Revision 1.8 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.7
 * 2005/09/14 07:11:49 timowest updated sources
 */
