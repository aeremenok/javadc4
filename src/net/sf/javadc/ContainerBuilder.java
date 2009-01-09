/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

// $Id: ContainerBuilder.java,v 1.26 2005/10/02 11:42:29 timowest Exp $
package net.sf.javadc;

import junit.framework.Assert;
import net.sf.javadc.config.SettingsAdapter;
import net.sf.javadc.config.SettingsLoader;
import net.sf.javadc.config.User;
import net.sf.javadc.gui.DownloadComponent;
import net.sf.javadc.gui.HubListComponent;
import net.sf.javadc.gui.IncompleteComponent;
import net.sf.javadc.gui.MainFrame;
import net.sf.javadc.gui.ManagerComponent;
import net.sf.javadc.gui.MonitorComponent;
import net.sf.javadc.gui.MultiSearchComponent;
import net.sf.javadc.gui.model.IncompletesLoader;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnectionFactory;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubFavoritesList;
import net.sf.javadc.interfaces.IHubFavoritesLoader;
import net.sf.javadc.interfaces.IHubList;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.IIncompletesLoader;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ISearchRequestFactory;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ISettingsLoader;
import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.interfaces.IUserInfo;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.RequestsModel;
import net.sf.javadc.net.SearchRequestFactory;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.ShareManager;
import net.sf.javadc.net.UDPListenerThread;
import net.sf.javadc.net.client.ClientManager;
import net.sf.javadc.net.client.ConnectionFactory;
import net.sf.javadc.net.client.ConnectionManager;
import net.sf.javadc.net.hub.AllHubs;
import net.sf.javadc.net.hub.HubFactory;
import net.sf.javadc.net.hub.HubFavoritesList;
import net.sf.javadc.net.hub.HubFavoritesLoader;
import net.sf.javadc.net.hub.HubList;
import net.sf.javadc.net.hub.HubManager;
import net.sf.javadc.tasks.ClientTaskFactory;
import net.sf.javadc.tasks.HubTaskFactory;
import net.sf.javadc.themes.LAFManager;
import net.sf.javadc.util.TaskManager;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.DefaultPicoContainer;

/**
 * <CODE>ContainerBuilder</CODE> is a Utility class, which builds a PicoContainer for the main networking components, a
 * PicoContainer for the GUI components, which inherits the main container, and two containers for the Client Connection
 * tasks and Hub Connection tasks respectively
 * 
 * @author Timo Westkämper $Id: ContainerBuilder.java,v 1.26 2005/10/02 11:42:29 timowest Exp $ $Author: timowest $
 */
public class ContainerBuilder
{
    /**
     * Build the ClientTask PicoContainer with references to the main client2client related tasks
     * 
     * @param mainContainer reference to the main PicoContainer
     * @return
     */
    public final MutablePicoContainer buildClientTaskContainer(
        MutablePicoContainer mainContainer )
    {
        if ( mainContainer == null )
        {
            throw new NullPointerException( "mainContainer was null." );
        }

        final MutablePicoContainer clientTaskContainer = new DefaultPicoContainer( mainContainer );

        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IDirectionTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IErrorTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IFileLengthTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IGetListLenTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IGetTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IKeyTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.ILockTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IMaxedOutTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IMyNickTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.ISendTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.ISupportsTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.ICancelTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.SDisconnectTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.SMyNickTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.SResumingTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.SStartDownloadTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.SDownloadingTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.SUploadingTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.SZUploadingTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.SRemotelyQueuedTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.SDownloadFinishedTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IGetZBlockTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.ISendingTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IFailedTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IUGetZBlockTask.class );
        clientTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.client.IUGetBlockTask.class );

        mainContainer
            .registerComponentInstance( IClientTaskFactory.class, new ClientTaskFactory( clientTaskContainer ) );

        return clientTaskContainer;

    }

    /**
     * Build the gui PicoContainer with references to the main GUI components
     * 
     * @param mainContainer reference to the main PicoContainer
     * @return
     */
    public final MutablePicoContainer buildGuiContainer(
        MutablePicoContainer mainContainer )
    {
        Assert.assertNotNull( mainContainer );

        final MutablePicoContainer guiContainer = new DefaultPicoContainer( mainContainer );

        guiContainer.registerComponentImplementation( HubListComponent.class );
        guiContainer.registerComponentImplementation( MainFrame.class );
        guiContainer.registerComponentImplementation( ManagerComponent.class );
        guiContainer.registerComponentImplementation( MultiSearchComponent.class );
        guiContainer.registerComponentImplementation( MonitorComponent.class );
        guiContainer.registerComponentImplementation( DownloadComponent.class );
        guiContainer.registerComponentImplementation( IncompleteComponent.class );

        // represents a common model for the DownloadComponent and
        // IncompleteComponent
        guiContainer.registerComponentImplementation( IRequestsModel.class, RequestsModel.class );

        // loads the incomplete files from an XML serialized version
        guiContainer.registerComponentImplementation( IIncompletesLoader.class, IncompletesLoader.class );

        // guiContainer.registerComponentImplementation(SettingsDialog.class);

        return guiContainer;
    }

    /**
     * Build the HubTask PicoContainer with references to the main client2hub related tasks
     * 
     * @param mainContainer reference to the main PicoContainer
     * @return
     */
    public final MutablePicoContainer buildHubTaskContainer(
        MutablePicoContainer mainContainer )
    {
        if ( mainContainer == null )
        {
            throw new NullPointerException( "mainContainer was null." );
        }

        final MutablePicoContainer hubTaskContainer = new DefaultPicoContainer( mainContainer );

        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IBadPassTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IConnectToMeTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IForceMoveTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IGetPassTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IHelloTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IHubIsFullTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IHubNameTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.ILockTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.ILogedInTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IMyINFOTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.INickListTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IOpListTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IQuitTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IRevConnectToMeTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.ISearchTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.ISRTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IToTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IValidateDenideTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IUserCommandTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.IHubTopicTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.SDisconnectTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.SLogoutTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.SRequestConnectionTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.SSearchTask.class );
        hubTaskContainer.registerComponentImplementation( net.sf.javadc.tasks.hub.ISupportsTask.class );

        mainContainer.registerComponentInstance( IHubTaskFactory.class, new HubTaskFactory( hubTaskContainer ) );

        return hubTaskContainer;

    }

    /**
     * Build the main PicoContainer with registrations for the main application and networking components
     * 
     * @return
     */
    public final MutablePicoContainer buildMainContainer()
    {
        MutablePicoContainer mainContainer = new DefaultPicoContainer();

        // general components

        // main settings of the application
        mainContainer.registerComponentImplementation( ISettings.class, SettingsAdapter.class );
        // loads the settings from the XML serialized version
        mainContainer.registerComponentImplementation( ISettingsLoader.class, SettingsLoader.class );
        // manager for swing look and feel implementations
        mainContainer.registerComponentImplementation( LAFManager.class );
        // user information
        mainContainer.registerComponentImplementation( IUserInfo.class, User.class );
        // manages the shared files of the user
        mainContainer.registerComponentImplementation( IShareManager.class, ShareManager.class );

        // manages the shared files of the user (does hash of files)
        /*
         * mainContainer.registerComponentImplementation(
         * ITreeHashingManager.class, TreeHashingManager.class);
         */

        // maintains hub and sharemanager related tasks and events
        mainContainer.registerComponentImplementation( ITaskManager.class, TaskManager.class );

        // networking components ----------------------------------------------

        // a hub representation of all connected hubs
        mainContainer.registerComponentImplementation( AllHubs.class );
        // a manager for connected remote clients
        mainContainer.registerComponentImplementation( IClientManager.class, ClientManager.class );
        // a manager for remote client connections
        mainContainer.registerComponentImplementation( IConnectionManager.class, ConnectionManager.class );
        // a manager for hub connections
        mainContainer.registerComponentImplementation( IHubManager.class, HubManager.class );
        // represents a list of public hubs that is downloaded from a given URL
        mainContainer.registerComponentImplementation( IHubList.class, HubList.class );
        // a factory method implementation for hubs
        mainContainer.registerComponentImplementation( IHubFactory.class, HubFactory.class );
        // a factory method implementation for remote client connections
        mainContainer.registerComponentImplementation( IConnectionFactory.class, ConnectionFactory.class );

        // represents the list of favorite hubs which is initialized from
        // an XML serialized version
        mainContainer.registerComponentImplementation( IHubFavoritesList.class, HubFavoritesList.class );
        // the loader implementation which loads the favorites from an XML file
        mainContainer.registerComponentImplementation( IHubFavoritesLoader.class, HubFavoritesLoader.class );
        // a UDP listener thread
        mainContainer.registerComponentImplementation( UDPListenerThread.class );
        // resumes downloads by establishing hub connections and adding
        // downloads
        // into the hubs' download queues
        mainContainer.registerComponentImplementation( IDownloadManager.class, DownloadManager.class );
        mainContainer.registerComponentImplementation( ISegmentManager.class, SegmentManager.class );
        // a factory class for creation of SearchRequests which includes an
        // object
        // pool
        mainContainer.registerComponentImplementation( ISearchRequestFactory.class, SearchRequestFactory.class );

        return mainContainer;

    }

}
