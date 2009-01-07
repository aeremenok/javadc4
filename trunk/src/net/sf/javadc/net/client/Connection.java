/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: Connection.java,v 1.44 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import net.sf.javadc.config.ConnectionSettings;
import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IClientTask;
import net.sf.javadc.interfaces.IClientTaskFactory;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.ITask;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.UploadRequest;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.tasks.client.SDisconnectTask;
import net.sf.javadc.util.ExtendedBufferedOutputStream;
import net.sf.javadc.util.TokenInputStream;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * <CODE>Connection</CODE> is the representation of a connection to a remote client via a TCP Socket
 */
public class Connection
    extends AbstractConnection
    implements
        Runnable,
        ITask,
        IConnection
{

    private static final Logger          clientTraffic  = Logger.getLogger( "C2C" );

    private static final Category        logger         = Category.getInstance( Connection.class );

    /**
     * 
     */
    private Client                       client;

    /** 
     * 
     */
    private final IConnectionManager     clientConnectionManager;

    /**
     * 
     */
    private final IClientManager         clientManager;

    /**
     * 
     */
    private final IClientTaskFactory     clientTaskFactory;

    /**
     * 
     */
    private final ConnectionInfo         connectionInfo = new ConnectionInfo();

    /**
     * 
     */
    private IClientTask                  disconnectTask;

    /**
     * 
     */
    private DownloadRequest              downloadRequest;

    // networking stuff
    /**
     * 
     */
    private boolean                      isServer;

    /**
     * 
     */
    private RandomAccessFile             localFile;

    /**
     * 
     */
    private TokenInputStream             reader;

    // private ServerSocket serverSocket;
    /**
     * 
     */
    private Socket                       socket;

    /**
     * 
     */
    private ConnectionState              state          = ConnectionState.CONNECTING;

    // internal components
    /**
     * 
     */
    private final ConnectionStatistics   statistics     = new ConnectionStatistics();

    // external components
    /**
     * 
     */
    private final ITaskManager           taskManager;

    /**
     * 
     */
    private UploadRequest                uploadRequest  = new UploadRequest();

    /**
     * 
     */
    private ExtendedBufferedOutputStream writer         = null;

    /**
     * Create a Connection instance
     * 
     * @param _client Client to be used
     * @param listener ConnectionListener to be used
     * @param _isServer whether the client acts as a server
     * @param _taskManager ITaskManager to be used
     * @param _clientConnectionManager IConnectionManager to be used
     * @param _clientManager IClientManager to be used
     * @param _clientTaskFactory IClientTaskFactory to be used
     * @throws IOException
     */
    public Connection(
        Client _client,
        ConnectionListener listener,
        boolean _isServer,
        ITaskManager _taskManager,
        IConnectionManager _clientConnectionManager,
        IClientManager _clientManager,
        IClientTaskFactory _clientTaskFactory )
        throws IOException
    {

        if ( _taskManager == null )
        {
            throw new NullPointerException( "taskManager was null." );
        }
        else if ( _clientConnectionManager == null )
        {
            throw new NullPointerException( "clientConnectionManager was null." );
        }
        else if ( _clientManager == null )
        {
            throw new NullPointerException( "clientManager was null." );
        }
        else if ( _clientTaskFactory == null )
        {
            throw new NullPointerException( "clientTaskFactory was null." );
        }

        taskManager = _taskManager;
        clientConnectionManager = _clientConnectionManager;
        clientManager = _clientManager;

        clientTaskFactory = _clientTaskFactory;

        disconnectTask = new SDisconnectTask( taskManager );

        client = _client;
        isServer = _isServer;

        logger.debug( "ClientConnection created: " + _client.getHost() );
        addListener( listener );

        // start connection thread
        new Thread( this, toString() ).start();

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#closeFile()
     */
    public final void closeFile()
    {
        try
        {
            if ( localFile != null )
            {
                localFile.close();

            }
            else
            {
                // this log information is not important
                logger.debug( "localFile was null." );

            }

        }
        catch ( IOException e )
        {
            logger.error( "Could not close local file.", e );
            // logger.error(e);

        }

        localFile = null;
        downloadRequest = null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#disconnect()
     */
    public final void disconnect()
    {

        logger.info( "About to disconnect " + this );

        // this code should be executed via the TaskManager as it uses
        // non-thread safe methods

        if ( disconnectTask != null )
        {
            disconnectTask.setClientConnection( Connection.this );
            taskManager.addEvent( disconnectTask );
            disconnectTask = null;

        }
        else
        {
            logger
                .warn( "Already disconnected", new RuntimeException( "Invalid invocation of Connection.disconnect()" ) );
        }

        // run in external thread

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getClient()
     */
    public final IClient getClient()
    {
        return client;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getConnectionInfo()
     */
    public ConnectionInfo getConnectionInfo()
    {
        return connectionInfo;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getDownloadRequest()
     */
    public final DownloadRequest getDownloadRequest()
    {
        return downloadRequest;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getServerSocket()
     */
    // public final ServerSocket getServerSocket() {
    // return serverSocket;
    //
    // }
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getLocalFile()
     */
    public final RandomAccessFile getLocalFile()
    {
        return localFile;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getReader()
     */
    public final TokenInputStream getReader()
    {
        return reader;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getSocket()
     */
    public final Socket getSocket()
    {
        return socket;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getState()
     */
    public final ConnectionState getState()
    {
        return state;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getStatistics()
     */
    public ConnectionStatistics getStatistics()
    {
        return statistics;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getUploadRequest()
     */
    public UploadRequest getUploadRequest()
    {
        return uploadRequest;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#getWriter()
     */
    public final ExtendedBufferedOutputStream getWriter()
    {
        return writer;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {

        return toString().hashCode();
    }

    /**
     * Pause the execution of the calling thread for the given amount of seconds
     * 
     * @param secs
     */
    // private void hibernate(int secs) {
    //
    // try {
    // logger.info("Hibernating for 60 sec.");
    // Thread.sleep(secs * 1000);
    //
    // } catch (InterruptedException e1) {
    // logger.error("Caught InterruptedException ", e1);
    //
    // }
    // }
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#isServer()
     */
    public final boolean isServer()
    {
        return isServer;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#requestDirection(java.lang.String,
     *      boolean)
     */
    public final boolean requestDirection(
        String direction,
        boolean force )
        throws IOException
    {

        String currentDirection = connectionInfo.getCurrentDirection();
        String remoteKey = connectionInfo.getRemoteKey();

        if ( currentDirection != null && currentDirection.equals( direction ) && !force )
        { // if we have it, return true.

            return true;

        }
        else if ( (currentDirection == null || force) && direction != null && remoteKey != null )
        {

            int rnd = (int) (Math.random() * 10000) + 1;

            sendCommand( "$Direction", direction + " " + rnd );

            // I want a download session, I have really no idea what 9413 means,
            // it might be packet size, it might be size of the developers
            // pants.
            // We don't seem to need it anohow.
            sendCommand( "$Key", remoteKey );

            // Give the client the response so that it's happy.
            return true;

        }

        return false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public final void run()
    {
        // ConnectionManager includes already a reference to this Connection

        boolean additionSuccess = clientConnectionManager.addConnection( this );

        if ( !additionSuccess )
        {
            String warn = "ClientConnection " + this + " has already been " + "added to the ConnectionManager.";
            logger.debug( warn );

            // ConnectionManager does not yet include a reference to this
            // Connection

        }
        else
        {
            try
            {
                logger.info( "Connecting to " + client.getHost() );

                // true, if host equals local IP and false, if not
                if ( isServer )
                {
                    serverConnect();

                    // create a client socket (client host and client port)
                }
                else
                {
                    socket = new Socket( client.getHost().getHost(), client.getHost().getPort() );

                }

                socket.setReceiveBufferSize( ConnectionSettings.SOCKET_INPUT_BUFFER_SIZE );

                // timeout in ms for reading from the InputStream
                // socket.setSoTimeout(2000);

                reader =
                    new TokenInputStream( new BufferedInputStream( socket.getInputStream() ),
                        ConstantSettings.COMMAND_END_CHAR );

                writer =
                    new ExtendedBufferedOutputStream( socket.getOutputStream(), ConnectionSettings.UPLOAD_BLOCK_SIZE );

                logger.info( "Connected to " + client.getHost() );

                establishConnection();

            }
            catch ( ConnectException e )
            {
                if ( isServer )
                {
                    logger.error( "Caught " + e.getClass().getName() + " in serverConnect mode", e );

                }
                else
                {
                    logger.error( "Caught " + e.getClass().getName() + " when trying to obtain socket from " +
                        client.getHost(), e );
                }

                // hibernate(60);
                disconnect();

            }
            catch ( NoRouteToHostException e )
            {
                if ( isServer )
                {
                    logger.error( "Caught " + e.getClass().getName() + " in serverConnect mode", e );

                }
                else
                {
                    logger.error( "Caught " + e.getClass().getName() + " when trying to obtain socket from " +
                        client.getHost(), e );
                }

                // hibernate(60);
                disconnect();

            }
            catch ( SocketException e )
            {
                String error = "Caught SocketException in Connection.run()";

                if ( client != null )
                {
                    error += " for " + client.getNick();

                }

                logger.error( error, e );
                disconnect();

            }
            catch ( Exception e )
            {
                logger.error( "Caught " + e.getClass().getName() + " in Connection.run()", e );

                // logger.error(e);
                disconnect();

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
        String taskString = ConnectionState.getTransition( getState() );

        // Connection is in active mode where transitions are directly processed

        if ( taskString != null )
        {
            runActiveTask( taskString );

            // inital states from which state sequences are initialized

        }
        else
        {
            runPassiveTask( true );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#sendCommand(java.lang.String,
     *      java.lang.String)
     */
    public final void sendCommand(
        String command,
        String data )
    {
        try
        {
            String towrite = command + ConstantSettings.COMMAND_SEP_CHAR + data + ConstantSettings.COMMAND_END_CHAR;

            writer.write( towrite.getBytes( "ISO-8859-1" ) );
            writer.flush();

        }
        catch ( SocketException e )
        {
            String error = "Could not send command " + command + " with data " + data;

            if ( client != null )
            {
                error = error + " for " + client.getNick();
            }

            logger.error( error, e );

            disconnect();

        }
        catch ( Exception e )
        {
            logger.error( "Could not send command " + command + " with data " + data, e );

            disconnect();

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setDownloadRequest(net.sf.javadc.net.DownloadRequest)
     */
    public final void setDownloadRequest(
        DownloadRequest request )
    {

        if ( request != null )
        {
            ConnectionStatistics stats = getStatistics();

            if ( request.isSegment() )
            {
                stats.setSegmentOffset( request.getSegment().x );
            }
            else
            {
                stats.setSegmentOffset( 0 );
            }
        }

        downloadRequest = request;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setLocalFile(java.io.RandomAccessFile)
     */
    public final void setLocalFile(
        RandomAccessFile file )
    {
        localFile = file;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setReader(net.sf.javadc.util.TokenInputStream)
     */
    public final void setReader(
        TokenInputStream stream )
    {
        reader = stream;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setServerSocket(java.net.ServerSocket)
     */
    // public final void setServerSocket(ServerSocket socket) {
    // serverSocket = socket;
    //
    // }
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setSocket(java.net.Socket)
     */
    public final void setSocket(
        Socket _socket )
    {
        socket = _socket;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setState(net.sf.javadc.net.client.ConnectionState)
     */
    public final void setState(
        ConnectionState _state )
    {
        long time = System.currentTimeMillis();

        getStatistics().setLastStateChange( time );

        state = _state;
        fireStateChanged();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setUploadRequest(net.sf.javadc.net.UploadRequest)
     */
    public void setUploadRequest(
        UploadRequest uploadRequest )
    {
        this.uploadRequest = uploadRequest;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#setWriter(net.sf.javadc.util.ExtendedBufferedOutputStream)
     */
    public final void setWriter(
        ExtendedBufferedOutputStream stream )
    {
        writer = stream;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {

        if ( client != null )
        {
            return "Connection : " + client.toString();

        }
        else
        {
            return super.toString();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IConnection#updateConnectionInfo()
     */
    public void updateConnectionInfo()
    {
        connectionInfo.setInfo( statistics.getConnectionInfo() );
        fireStateChanged();

    }

    /**
     * Establish a Connection by executing the SMyNick task handler
     */
    private void establishConnection()
    {
        // WARNING : external thread
        IClientTask task = null;

        task = (IClientTask) clientTaskFactory.borrowObject( "SMyNick" );

        task.setClientConnection( this );
        task.setCmdData( null );

        try
        {
            task.runTask();

            // add the Connection task to the TaskManager, after the MyNick
            // task has been run
            taskManager.addTask( this );

        }
        catch ( Exception e )
        {
            logger.error( "Caught Exception when running task " + task.getClass().getName(), e );
            // logger.error(e);

            disconnect();

        }

        // taskManager.addEvent(task);
        if ( task != null )
        {
            clientTaskFactory.returnObject( "SMyNick", task );

        }
    }

    /**
     * Process the given command
     * 
     * @param cmdString Command String to be processed
     * @throws IOException
     */
    private final void processCommand(
        String cmdString )
        throws IOException
    {
        int sepIndex = cmdString.indexOf( ConstantSettings.COMMAND_SEP );

        // if the given string length is greater than one and a separator
        // char could be found

        if ( cmdString.length() > 1 && sepIndex > 0 && sepIndex < cmdString.length() - 1 )
        {

            logger.debug( "Got: " + cmdString );

            String cmdStart = cmdString.substring( 0, sepIndex );
            String cmdData = cmdString.substring( sepIndex + 1 );

            if ( cmdStart.startsWith( "$" ) )
            { // valid command name

                // WARNING : external thread
                IClientTask task = null;

                task = (IClientTask) clientTaskFactory.borrowObject( "I" + cmdStart.substring( 1 ) );

                if ( task != null )
                { // suitable Task handler was found
                    task.setCmdData( cmdData );
                    task.setClientConnection( this );
                    task.runTask();

                    clientTaskFactory.returnObject( "I" + cmdStart.substring( 1 ), task );

                }
                else
                { // no Task handler found
                    String warn = "Command unrecognized:" + cmdStart + " " + cmdData + " for state " + getState();
                    logger.warn( warn );

                }

            }
            else
            { // unrecognized command
                String warn = "Command unrecognized:" + cmdStart + " " + cmdData + " for state " + getState();
                logger.warn( warn );
            }

        }

    }

    /**
     * Get the command handler for the given taskString and execute it. If the given taskString is not SDownloading nor
     * Resuming, execute runPassiveTask() in between to catch MaxedOut and Error commands from the external Client which
     * might not follow the choreography
     * 
     * @param taskString
     */
    private void runActiveTask(
        String taskString )
    {

        ConnectionState oldState = getState();
        ConnectionState newState;

        // if connection state is neither downloading nor resuming

        if ( !taskString.equals( "SDownloading" ) && !taskString.equals( "SResuming" ) )
        {

            // timeout based cancellation is not used for intermediate
            // passive task handling
            runPassiveTask( false );
            newState = getState();

        }
        else
        {
            newState = oldState;

        }

        // run the DisconnectTask in an external thread
        // (normalization function as disconnect adds the DisconnectTask to the
        // TaskManager as an event)

        if ( taskString.equals( "SDisconnect" ) )
        {
            disconnect();
            return;

            // run the the task handler for the active task if no state change
            // occurred

        }
        else if ( oldState.equals( newState ) )
        {
            IClientTask task = (IClientTask) clientTaskFactory.borrowObject( taskString );

            if ( task != null )
            {
                task.setClientConnection( this );

                try
                {
                    task.runTask();

                }
                catch ( Exception e )
                {
                    logger.error( "Caught Exception when running task " + task.getClass().getName(), e );
                    // logger.error(e);

                    disconnect();

                }

                clientTaskFactory.returnObject( taskString, task );

            }
            else
            {
                logger.error( "No handler found for " + taskString );

            }

        }
        else
        {
            logger.warn( "Handler for " + taskString + " is not invoked, because state changed from " + oldState +
                " to " + newState );

        }
    }

    /**
     * Reads a token from the TokenInputStream, derives a TaskHandler from it and executes the task.
     * 
     * @param useTimeout switch for timeout based cancellation
     */
    private void runPassiveTask(
        boolean useTimeout )
    {

        String cmd = null;

        // task is derived from input
        try
        {
            cmd = reader.readToken();

            // derive a command handler and execute it

            if ( cmd != null )
            {
                if ( getClient() != null )
                {
                    clientTraffic.info( "r : " + cmd + "(" + getClient().getNick() + ")" );
                }
                else
                {
                    clientTraffic.info( "r : " + cmd );
                }

                processCommand( cmd );

                // check for timeouts and switch to reschedule mode, if
                // necessary

            }
            else if ( useTimeout )
            {
                long last_time = getStatistics().getLastStateChange();
                long curr_time = System.currentTimeMillis();

                long waiting = ConnectionSettings.CONNECTION_WAITING_TIME;

                if ( curr_time - last_time > waiting )
                {
                    // setState(ConnectionState.RESCHEDULE);
                    setState( ConnectionState.REMOTELY_QUEUED );

                }

            }

        }
        catch ( SocketException e )
        {

            // detailed logging output for SocketExceptions

            String error;

            if ( cmd != null )
            {
                error = "Caught " + e.getClass().getName() + " when processing command " + cmd;

            }
            else
            {
                error = "Caught " + e.getClass().getName();

            }

            if ( client != null )
            {
                error = error + " for " + client.getNick();

            }

            logger.error( error, e );

            // disconnect when SocketExceptions occurr

            disconnect();

        }
        catch ( Exception e )
        {
            if ( cmd != null )
            {
                logger.error( "Caught " + e.getClass().getName() + " when processing command " + cmd, e );

            }
            else
            {
                logger.error( "Caught " + e.getClass().getName() + " in runPassiveTask(boolean useTimeout).", e );

            }

            // logger.error(e);

        }

    }

    /**
     * Create a new Server Socket and recreate a new Client and add it to the ClientManager
     * 
     * @throws IOException
     */
    private final void serverConnect()
        throws IOException
    {

        // create a server socket
        ServerSocket serverSocket = new ServerSocket( client.getHost().getPort() );

        try
        {
            serverSocket.setSoTimeout( 10 * 1000 );

            // receive a client socket
            socket = serverSocket.accept();

        }
        finally
        {
            // close the server socket
            serverSocket.close();
            serverSocket = null;
        }

        // remove the server socket from the ClientManager or else we won't be
        // able to create another one

        clientManager.removeClient( client.getHost() );

        // client socket host + client socket port
        // HostInfo realHost = new HostInfo(socket.getInetAddress()
        // .getHostAddress(), socket.getPort());

        // the server socket port is used, because this identifies the
        // connection a remote client
        // has made better than the host and the connecting port

        // client socket host + server socket port
        HostInfo realHost = new HostInfo( socket.getInetAddress().getHostAddress(), client.getHost().getPort() );

        client.setHost( realHost );
        clientManager.addClient( realHost, client );

    }

}

/*******************************************************************************
 * $Log: Connection.java,v $ Revision 1.44 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.43
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.42 2005/09/25 16:40:59 timowest updated sources and
 * tests Revision 1.41 2005/09/14 07:11:50 timowest updated sources Revision 1.40 2005/09/12 20:56:35 timowest added log
 * block
 */
