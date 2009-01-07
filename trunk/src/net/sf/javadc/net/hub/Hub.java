/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: Hub.java,v 1.37 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.hub;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTask;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITask;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ClientListener;
import net.sf.javadc.listeners.HubListener;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.Message;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.tasks.hub.SDisconnectTask;
import net.sf.javadc.util.PerformanceContext;
import net.sf.javadc.util.TokenInputStream;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * <code>Hub</code> represents a hub connection with the related functionality to post <code>SearchRequests</code> and
 * <code>Messages</code>.
 * <p>
 * <code>Hub</code> instances are managed via a <code>HubManager</code> and can be created via a <code>HubFactory</code>
 * .
 * </p>
 * 
 * @author Timo Westk�mper
 */
public class Hub
    extends AbstractHub
    implements
        Runnable,
        ITask
{

    /** ********************************************************************** */
    private final class CreateConnectionTask
        implements
            ITask
    {

        private final IHubManager  hubManager;

        private final ITaskManager taskManager;

        private final Socket       socket;

        /**
         * Create a CreateConnectionTask
         * 
         * @param hubManager HubManager instance to use
         * @param taskManager ITaskManager instance to use
         * @param c Socket to use
         */
        private CreateConnectionTask(
            IHubManager hubManager,
            ITaskManager taskManager,
            Socket socket )
        {
            super();

            if ( hubManager == null )
            {
                throw new NullPointerException( "hubManager was null" );
            }

            if ( taskManager == null )
            {
                throw new NullPointerException( "taskManager was null" );
            }

            if ( socket == null )
            {
                throw new NullPointerException( "socket was null" );
            }

            this.hubManager = hubManager;
            this.taskManager = taskManager;

            this.socket = socket;

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.interfaces.ITask#runTask()
         */
        public void runTask()
        {
            PerformanceContext cont = new PerformanceContext( "CreateConnectionTask#runTask()" ).start();

            try
            {
                // create InputStream for input commands

                reader =
                    new TokenInputStream( new BufferedInputStream( socket.getInputStream() ),
                        ConstantSettings.COMMAND_END_CHAR );

                // create OutputStream

                writer = new BufferedOutputStream( socket.getOutputStream() );

                connection = socket;

                hubManager.addHub( Hub.this );
                taskManager.addTask( Hub.this );

            }
            catch ( IOException e )
            {
                logger.error( "Caught " + e.getClass().getName(), e );
                // connection = null;

                Hub.this.disconnect();
            }

            cont.end();

            if ( cont.getDuration() > 100 )
            {
                logger.info( cont );
            }

        }

    }

    private final static Category logger         = Logger.getLogger( Hub.class );

    // private SearchRequest activeSearchRequest;
    /**
     * 
     */
    public final ClientListener   clientListener = new HubClientListener( this );

    /**
     * 
     */
    private Socket                connection;

    // private boolean isLoggedIn = false;
    /**
     * 
     */
    private final List            opList         = new ArrayList();

    /**
     * 
     */
    private long                  startPing;

    /**
     * 
     */
    private String                description;

    /**
     * 
     */
    private String                name;

    /**
     * 
     */
    private int                   userCount;

    /**
     * 
     */
    private List                  searchResults  = new ArrayList();

    /**
     * 
     */
    private final Map             users          = new HashMap();

    /**
     * 
     */
    private boolean               loggedIn       = false;

    /**
     * 
     */
    private List                  supports       = new ArrayList();

    /**
     * 
     */
    private SearchRequest         searchRequest;

    /**
     * 
     */
    private final IHubTask        disconnectTask;

    // networking
    /**
     * 
     */
    private TokenInputStream      reader;

    /**
     * 
     */
    private OutputStream          writer;

    /**
     * 
     */
    private HostInfo              host;

    // components
    // private final DCEncryptionHandler encryptionHandler = new
    // DCEncryptionHandler();
    /**
     * 
     */
    private final ISettings       settings;

    /**
     * 
     */
    private final IHubManager     hubManager;

    /**
     * 
     */
    private final IHubTaskFactory hubTaskFactory;

    /**
     * 
     */
    private final ITaskManager    taskManager;

    /**
     * Create a Hub instance
     * 
     * @param _host HostInfo to be used
     * @param _taskManager ITaskManager instance to be used
     * @param _hubManager IHubManager instance to be used
     * @param _hubTaskFactory IHubTaskFactory instance to be used
     * @param _settings ISettings instance to be used
     */
    public Hub(
        HostInfo _host,
        ITaskManager _taskManager,
        IHubManager _hubManager,
        IHubTaskFactory _hubTaskFactory,
        ISettings _settings )
    {

        if ( _host == null )
        {
            throw new NullPointerException( "host was null." );
        }
        else if ( _taskManager == null )
        {
            throw new NullPointerException( "taskManager was null." );
        }
        else if ( _hubManager == null )
        {
            throw new NullPointerException( "hubManager was null." );
        }
        else if ( _hubTaskFactory == null )
        {
            throw new NullPointerException( "hubTaskFactory was null." );
        }
        else if ( _settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }

        taskManager = _taskManager;
        hubManager = _hubManager;
        hubTaskFactory = _hubTaskFactory;
        settings = _settings;

        disconnectTask = new SDisconnectTask( taskManager );

        name = _host.getHost();
        host = _host;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#addSearchResult(net.sf.javadc.net.SearchResult)
     */
    public final void addSearchResult(
        SearchResult sr )
    {
        if ( !searchResults.contains( sr ) )
        {
            searchResults.add( sr );

            if ( searchRequest != null )
            {
                fireSearchResultAdded( sr, searchRequest );
            }
            else
            {
                logger.error( "searchRequest was not specified for searchResult " + sr );
            }

        }
        else
        {
            logger.debug( "SearchResult " + sr + " was already contained." );

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#addUser(net.sf.javadc.net.hub.HubUser)
     */
    public final void addUser(
        HubUser ui )
    {
        if ( users.put( ui.getNick(), ui ) == null )
        {
            fireUserAdded( ui );

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#clearSearchResults()
     */
    public final void clearSearchResults()
    {
        searchResults.clear();
        fireSearchResultsCleared();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#connect()
     */
    public final void connect()
    {
        // start the connecting thread if no connection has been establised
        if ( connection == null )
        {

            PerformanceContext cont = new PerformanceContext( "Hub#connect()" ).start();

            if ( hubManager.getHub( this ) == null )
            {
                logger.debug( "Starting startup Thread for " + this );

                new Thread( this, toString() ).start();

            }
            else
            {
                logger.error( "Hub " + this + " was already contained in HubManager" );

            }

            cont.end();

            if ( cont.getDuration() > 100 )
            {
                logger.info( cont );
            }

        }
        else
        {
            logger.debug( "Connection to Hub has already been established." );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#disconnect()
     */
    public final void disconnect()
    {

        // this code should be executed via the TaskManager as it uses
        // non-thread
        // safe methods

        disconnectTask.setHub( this );

        taskManager.addEvent( disconnectTask );

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(
        Object obj )
    {
        if ( this == obj )
        {
            return true;

        }
        else if ( obj instanceof IHub )
        {
            // compare hosts
            return host.equals( ((IHub) obj).getHost() );

        }
        else
        {
            return false;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getConnection()
     */
    public final Socket getConnection()
    {
        return connection;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getDescription()
     */
    public final String getDescription()
    {
        return description;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getHost()
     */
    public final HostInfo getHost()
    {
        return host;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getName()
     */
    public final String getName()
    {
        return name;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getOpList()
     */
    public final List getOpList()
    {
        return opList;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getReader()
     */
    public TokenInputStream getReader()
    {
        return reader;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getSearchResults()
     */
    public final SearchResult[] getSearchResults()
    {
        return (SearchResult[]) searchResults.toArray( new SearchResult[0] );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getStartPing()
     */
    public final long getStartPing()
    {
        return startPing;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getSupports()
     */
    public List getSupports()
    {
        return supports;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getUser(java.lang.String)
     */
    public final HubUser getUser(
        String nick )
    {
        return (HubUser) users.get( nick );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getUserCount()
     */
    public final int getUserCount()
    {
        if ( users.size() > 0 )
        {
            return users.size();

        }
        else
        {
            return userCount;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getUsers()
     */
    public Map getUsers()
    {
        return users;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getWriter()
     */
    public OutputStream getWriter()
    {
        return writer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#isConnected()
     */
    public final boolean isConnected()
    {
        return connection != null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#isLoggedIn()
     */
    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    /**
     * processCommand takes a String with one or multiple commands, parses them and takes appropreate actions.
     */
    public final void processCommand(
        String cmdString )
        throws IOException
    {
        // logger.debug("Got: " + cmdString);

        int separatorIndex = cmdString.indexOf( ConstantSettings.COMMAND_SEP );
        String cmdStart;
        String cmdData;

        if ( separatorIndex != -1 )
        {
            cmdStart = cmdString.substring( 0, separatorIndex );
            cmdData = cmdString.substring( separatorIndex + 1 );

        }
        else
        {
            cmdStart = cmdString;
            cmdData = null;

        }

        PerformanceContext cont = new PerformanceContext( "Hub#processComand(String) : " + cmdStart ).start();

        // private message command

        if ( "$To:".equals( cmdStart ) )
        {
            // TODO : Event or Direct ?

            IHubTask task = (IHubTask) hubTaskFactory.borrowObject( "ITo" );

            task.setCmdData( cmdData );
            task.setHub( this );

            task.runTask();

            hubTaskFactory.returnObject( "ITo", task );

            // hub message command

        }
        else if ( cmdStart.startsWith( "<" ) && cmdStart.length() > 1 )
        {

            fireGotMessage( new Message( cmdStart.substring( 1, cmdStart.length() - 1 ), "Me", cmdData ) );

            // general command

        }
        else if ( cmdStart.startsWith( "$" ) )
        {
            String name = "I" + cmdStart.substring( 1 );
            IHubTask task = (IHubTask) hubTaskFactory.borrowObject( name );

            if ( task != null )
            {
                task.setCmdData( cmdData );
                task.setHub( this );

                task.runTask();

                hubTaskFactory.returnObject( name, task );

            }
            else
            {
                logger.error( "Command unrecognized: " + cmdStart + " " + cmdData );

            }

            // display content to the message display

        }
        else
        {

            // if command is not empty
            if ( !cmdString.trim().equals( "" ) )
            {
                fireGotMessage( new Message( "Hub", "Me", cmdString ) );
            }

        }

        cont.end();

        // log the duration of the command handling
        if ( cont.getDuration() > 500 )
        {
            logger.info( cont );
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#receivedNick(net.sf.javadc.net.client.Client)
     */
    public final void receivedNick(
        Client client )
    {
        HubUser ui = getUser( client.getNick() );

        if ( ui != null )
        {
            try
            {
                ui.setClient( client );

            }
            catch ( NullPointerException e )
            {
                logger.error( "Caught " + e.getClass().getName(), e );

            }

        }
        else
        {
            String warn = "There is no user with nick " + client.getNick() + " in the hub.";
            logger.warn( warn );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#reconnect()
     */
    public final void reconnect()
        throws IOException
    {
        disconnect();
        connect();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#removeUser(net.sf.javadc.net.hub.HubUser)
     */
    public final void removeUser(
        HubUser ui )
    {
        if ( users.remove( ui.getNick() ) != null )
        {
            fireUserRemoved( ui );

        }
        else
        {
            logger.info( "User " + ui.getNick() + " could not be removed." );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#requestConnection(java.lang.String)
     */
    public final void requestConnection(
        String user )
        throws IOException
    {
        IHubTask task = null;

        task = (IHubTask) hubTaskFactory.borrowObject( "SRequestConnection" );

        // if a task with the given name could be found
        if ( task != null )
        {
            task.setHub( this );
            task.setCmdData( user );
            task.runTask();

            hubTaskFactory.returnObject( "SRequestConnection", task );

            // if no task with the given name could be found
        }
        else
        {
            String error = "Task for SRequestConnection could not be found.";
            logger.error( error );

        }

    }

    /**
     * @param dr
     * @return
     */
    public final boolean requestDownload(
        DownloadRequest dr )
    {
        String nick = dr.getSearchResult().getNick();

        logger.info( "Requesting download from " + nick + "." );

        HubUser user = getUser( nick );

        if ( user != null )
        { // user was found

            try
            {
                user.addDownload( dr );

            }
            catch ( IOException e )
            {
                String error = "Connection to " + nick + " could not be established.";
                logger.error( error, e );
                // logger.error(e);

                return false;

            }

            return true;

        }
        else
        { // user was not found
            logger.info( dr.getSearchResult().getNick() + " is no longer in this hub" );

            return false;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public final void run()
    {
        String host = this.host.getHost();
        int mainPort = this.host.getPort();
        int altPort = this.host.getAlternatePort();

        try
        {
            // tries to create a connection to the main port
            createInputStream( host, mainPort );

        }
        catch ( Exception e )
        {
            logger.error( "Connection to main port " + mainPort + " failed.", e );
            // logger.error(e);

            // no alternative ports available

            if ( mainPort == altPort )
            {
                disconnect();

                // try alternative port

            }
            else
            {
                try
                {
                    // tries to create a connection to the alternate port
                    createInputStream( host, altPort );

                }
                catch ( Exception io )
                {
                    logger.error( "Connection to alternate port " + altPort + " failed.", io );
                    // logger.error(io);

                    disconnect();
                }

            }

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ITask#runTask()
     */
    public final void runTask()
    {
        if ( reader == null )
        {
            logger.info( "reader was null in runTask()" );
            return;
        }

        PerformanceContext cont = new PerformanceContext( "Hub#runTask()" ).start();
        // long startTime = System.currentTimeMillis();

        try
        {
            String cmd;
            int counter = 0;

            while ( (cmd = reader.readToken()) != null )
            {

                processCommand( cmd );

                if ( counter % ConnectionSettings.HUB_THREAD_INTERRUPTION_INTERVAL == 0 )
                {
                    try
                    {
                        Thread.sleep( ConnectionSettings.HUB_THREAD_INTERRUPTION_TIME );

                    }
                    catch ( InterruptedException e )
                    {
                        // ?
                    }
                }

                counter++;

                if ( reader == null )
                {
                    logger.info( "reader was null in runTask()" );
                    return;
                }

                // long executionTime = System.currentTimeMillis() - startTime;
                cont.end();

                if ( cont.getDuration() > 2000 )
                {
                    logger.info( cont );
                    return;
                }
                else if ( counter > 1000 )
                {
                    return;
                }

            }

        }
        catch ( IOException e )
        {
            logger.error( "Disconnecting because of connection fault.", e );
            // logger.error(e);

            disconnect();

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#search(net.sf.javadc.net.SearchRequest)
     */
    public final void search(
        SearchRequest sr )
        throws IOException
    {

        if ( !isLoggedIn() )
        {
            logger.error( "No searching allowed on current hub, because the "
                + "login procedure has not yet been completed." );
            return;
        }

        // activeSearchRequest = sr;
        // doSearch(sr.toString());
        IHubTask task = null;

        searchRequest = sr;

        task = (IHubTask) hubTaskFactory.borrowObject( "SSearch" );

        // if a task could be created
        if ( task != null )
        {
            task.setCmdData( sr.toString() );
            task.setHub( this );
            task.runTask();

            hubTaskFactory.returnObject( "SSearch", task );

            // if no task could be created
        }
        else
        {
            String error = "Task for SSearch could not be created.";
            logger.error( error );

        }

        // notify listeners
        // fireSearchRequestAdded(sr);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendChatMessage(java.lang.String)
     */
    public final void sendChatMessage(
        String message )
        throws IOException
    {
        String nick = settings.getUserInfo().getNick();

        if ( message.length() > 0 )
        {
            sendCommand( "<" + nick + ">", message );

        }
        else
        {
            logger.warn( "Given message was empty." );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendCommand(java.lang.String,
     *      java.lang.String)
     */
    public final void sendCommand(
        String command,
        String data )
    {
        // send command only if connected
        if ( !isConnected() )
        {
            return;
        }

        OutputStream os;

        // intialize the output stream if it has not yet been created
        if ( getWriter() == null )
        {
            try
            {
                setWriter( new BufferedOutputStream( connection.getOutputStream() ) );

            }
            catch ( IOException e )
            {
                logger.error( "Caught " + e.getClass().getName(), e );

            }

        }

        os = getWriter();

        try
        {
            String towrite = command + ConstantSettings.COMMAND_SEP_CHAR + data + ConstantSettings.COMMAND_END_CHAR;

            os.write( towrite.getBytes( "ISO-8859-1" ) );

            os.flush();

        }
        catch ( java.net.SocketException e )
        {
            // "connection reset by peer: write error" for example
            // probably means we are disconnected
            logger.error( "Error when sending command " + command + " with data " + data, e );
            // logger.error(e);

            disconnect();

        }
        catch ( IOException e )
        {
            logger.error( "Error when sending command " + command + " with data " + data, e );
            // logger.error(e);

            disconnect();

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendPrivateMessage(java.lang.String,
     *      java.lang.String)
     */
    public final void sendPrivateMessage(
        String message,
        String nick )
        throws IOException
    {
        String _nick = settings.getUserInfo().getNick();

        sendCommand( "$To:", nick + " From: " + _nick + " $<" + _nick + ">" + message );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendSearchResult(java.lang.String,
     *      java.lang.String)
     */
    public final void sendSearchResult(
        String result,
        String to_nick )
        throws IOException
    {
        String _nick = settings.getUserInfo().getNick();

        sendCommand( "$SR", _nick + " " + result + (char) 5 + to_nick );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setConnection(java.net.Socket)
     */
    public final void setConnection(
        Socket socket )
    {
        connection = socket;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setDescription(java.lang.String)
     */
    public final void setDescription(
        String description )
    {
        this.description = description;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setHost(net.sf.javadc.net.hub.HostInfo)
     */
    public final void setHost(
        HostInfo _host )
    {
        host = _host;

        // set hub name to host name if is has not yet been specified
        if ( name == null )
        {
            name = host.getHost();

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setLoggedIn(boolean)
     */
    public void setLoggedIn(
        boolean loggedIn )
    {
        this.loggedIn = loggedIn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setName(java.lang.String)
     */
    public final void setName(
        String _name )
    {
        name = _name;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setReader(net.sf.javadc.util.TokenInputStream)
     */
    public void setReader(
        TokenInputStream reader )
    {
        this.reader = reader;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setSearchResults(java.util.List)
     */
    public final void setSearchResults(
        List list )
    {
        searchResults = list;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setStartPing(long)
     */
    public final void setStartPing(
        long l )
    {
        startPing = l;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setSupports(java.util.List)
     */
    public void setSupports(
        List supports )
    {
        if ( supports == null )
        {
            throw new NullPointerException( "supports was null." );
        }

        this.supports = supports;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setUserCount(int)
     */
    public final void setUserCount(
        int _userCount )
    {
        userCount = _userCount;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setWriter(java.io.OutputStream)
     */
    public void setWriter(
        OutputStream writer )
    {
        this.writer = writer;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString()
    {
        StringBuffer c = new StringBuffer();

        c.append( "Hub:" ).append( " - " ).append( getName() ).append( " - " ).append( getHost() ).append( " - " )
            .append( getDescription() ).append( " - " ).append( getUserCount() );

        if ( !isConnected() )
        {
            c.append( " - (Disconnected)" );

        }

        return c.toString();

    }

    /**
     * Create an InputStream to the given Host with the given port
     * 
     * @param host
     * @param port
     * @throws Exception
     */
    private final void createInputStream(
        String host,
        int port )
        throws Exception
    {

        final Socket c = new Socket( host, port );

        // create the connection asynchronously via the TaskManager

        taskManager.addEvent( new CreateConnectionTask( hubManager, taskManager, c ) );

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    protected final Class getListenerClass()
    {
        return HubListener.class;

    }

    /**
     * @return
     */
    protected SearchRequest getSearchRequest()
    {

        return searchRequest;
    }

}

/*******************************************************************************
 * $Log: Hub.java,v $ Revision 1.37 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.36 2005/09/30
 * 15:59:53 timowest updated sources and tests Revision 1.35 2005/09/26 17:19:52 timowest updated sources and tests
 * Revision 1.34 2005/09/25 16:40:58 timowest updated sources and tests Revision 1.33 2005/09/14 07:11:49 timowest
 * updated sources Revision 1.32 2005/09/12 21:12:02 timowest added log block
 */
