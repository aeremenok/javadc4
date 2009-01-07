/*
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software;      you can redistribute it and/or modify it 
 * under the terms of the   GNU General Public License as published by the Free
 * Software Foundation;    either version 2 of the License, or (at your option) 
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY;   without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE.   See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.interfaces;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import net.sf.javadc.net.Message;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.net.hub.HubUser;
import net.sf.javadc.util.TokenInputStream;

/**
 * <CODE>IHub</CODE> represents the abstract interface for <CODE>Hub</CODE>,
 * the object representation of a Hub
 * 
 * @author tw70794
 */
public interface IHub extends Runnable, ITask {

    /**
     * Add an EventListener to the list of EventListeners
     * 
     * @param listener
     */
    public void addListener(EventListener listener);

    /**
     * Add a user to the Hub
     * 
     * @param ui
     */
    public void addUser(HubUser ui);

    /**
     * Add a SearchResult to the Hub
     * 
     * @param sr
     */
    public void addSearchResult(SearchResult sr);

    /**
     * Clear the SearchResults
     */
    public void clearSearchResults();

    /**
     * Connect to the Hub
     */
    public void connect();

    /**
     * Disconnect from the Hub
     */
    public void disconnect();

    /**
     * Notify lsteners that the BrowseList for the given Hubuser has been
     * decoded
     * 
     * @param hub
     * @return IHubblic boolean equals(Hub hub) ; /**
     * @param ui
     */
    public void fireBrowseListDecoded(HubUser ui);

    /**
     * Notify listeners that the given Message has been received
     * 
     * @param msg
     */
    public void fireGotMessage(Message msg);

    /**
     * Notify listeners that the given Message from a remote Client has been
     * received
     * 
     * @param msg
     */
    public void fireGotUserMessage(Message msg);

    /**
     * Notify listeners that the local Client has disconnected from the hub
     */
    public void fireHubDisconnected();

    /**
     * Notify listeners that the given SearchResult has been added
     * 
     * @param sr
     * @param ser
     */
    public void fireSearchResultAdded(SearchResult sr, SearchRequest ser);

    /**
     * Notify listeners that the given SearchRequest has been added
     * 
     * @param sr
     */
    // public void fireSearchRequestAdded(SearchRequest sr);
    /**
     * Notify listeners that the SearchResults have been cleared
     */
    public void fireSearchResultsCleared();

    /**
     * Notify listeners that the given HubUser has been added
     * 
     * @param ui
     */
    public void fireUserAdded(HubUser ui);

    /**
     * Notify listeners that the given HubUser has been removed
     * 
     * @param ui
     */
    public void fireUserRemoved(HubUser ui);

    /**
     * Return the Socket of the Connection
     * 
     * @return
     */
    public Socket getConnection();

    /**
     * Return the Description
     * 
     * @return
     */
    public String getDescription();

    /**
     * Return the HostInfo
     * 
     * @return
     */
    public HostInfo getHost();

    /**
     * Return the Name of this Hub
     * 
     * @return
     */
    public String getName();

    /**
     * Return the list of operators of this Hub
     * 
     * @return
     */
    public List getOpList();

    /**
     * Return the SearchResults of this Hub
     * 
     * @return
     */
    public SearchResult[] getSearchResults();

    /**
     * Return the Start Ping
     * 
     * @param asArrayList
     * @return
     */
    public long getStartPing();

    /**
     * Return a HubUser record for the user with given nick
     * 
     * @param nick
     * @return
     */
    public HubUser getUser(String nick);

    /**
     * Return the amount of users in this Hub
     * 
     * @return
     */
    public int getUserCount();

    /**
     * Return whether the connection to the Hub is active
     * 
     * @return
     */
    public boolean isConnected();

    /**
     * Return whether the local client is a registered Operator in the Hub
     * 
     * @return
     */

    // NOTE : this method is not used anymore, because it was previously used
    // with wrong semantics
    // Alternative usage patterns could be to place the information about the
    // hubs where the local Client acts as a Operator into the HubManager
    // public boolean isLoggedIn();
    /**
     * processCommand takes a String with one or multiple commands, parses them
     * and takes appropreate actions.
     * 
     * @param cmdString
     * @throws IOException
     */
    public void processCommand(String cmdString) throws IOException;

    /**
     * @param client
     */
    public void receivedNick(Client client);

    /**
     * Reconnect to this Hub
     * 
     * @throws IOException
     */
    public void reconnect() throws IOException;

    /**
     * Remove the given listeners from the list of listeners
     * 
     * @param listener
     */
    public void removeListener(EventListener listener);

    /**
     * Remove the given HubUser instance from list of users
     * 
     * @param ui
     */
    public void removeUser(HubUser ui);

    /**
     * Request a Client Connection to the given HubUser
     * 
     * @param user
     * @throws IOException
     */
    public void requestConnection(String user) throws IOException;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run();

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ITask#runTask()
     */
    public void runTask();

    /**
     * Search the Hub with the given SearchRequest
     * 
     * @param sr
     * @throws IOException
     */
    public void search(SearchRequest sr) throws IOException;

    /**
     * Send a public Message to the chat of the Hub
     * 
     * @param message
     * @throws IOException
     */
    public void sendChatMessage(String message) throws IOException;

    /**
     * Send the given command with the given data to the Hub
     * 
     * @param command
     * @param data
     */
    public void sendCommand(String command, String data);

    /**
     * Send a private Message to the given User in the Hub
     * 
     * @param message
     * @param nick
     * @throws IOException
     */
    // TODO : factor out
    public void sendPrivateMessage(String message, String nick)
            throws IOException;

    /**
     * Send the given SearchResult to the given User via the Hub
     * 
     * @param result
     * @param to_nick
     * @throws IOException
     */
    // TODO : factor out
    public void sendSearchResult(String result, String to_nick)
            throws IOException;

    /**
     * Set the Socket of this Hub Connection
     * 
     * @param socket
     */
    public void setConnection(Socket socket);

    /**
     * Set the Description of this Hub
     * 
     * @param description
     */
    public void setDescription(String description);

    /**
     * Set the Host of this Hub
     * 
     * @param host
     */
    public void setHost(HostInfo host);

    /**
     * Set whether the User has been accepted to this Hub as an Operator
     * 
     * @param b
     */
    // public void setLoggedIn(boolean b);
    /**
     * Set the name of this Hub
     * 
     * @param name
     */
    public void setName(String name);

    /**
     * Set the SearchResults of this Hub
     * 
     * @param list
     */
    public void setSearchResults(List list);

    /**
     * Set the Start Ping of this Hub
     * 
     * @param l
     */
    public void setStartPing(long l);

    /**
     * Set the user count of this Hub
     * 
     * @param userCount
     */
    public void setUserCount(int userCount);

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString();

    /**
     * Return the active users in a HashMap with Nick to User mappings
     * 
     * @return
     */
    public Map getUsers();

    /**
     * Obtain the OutputStream of the Hub instance
     * 
     * @return
     */
    public OutputStream getWriter();

    /**
     * Set the OutputStream of the Hub instance
     * 
     * @param writer
     */
    public void setWriter(OutputStream writer);

    /**
     * Get the TokenInputStream of the Hub instance
     * 
     * @return
     */
    public TokenInputStream getReader();

    /**
     * Set the TokenInputStream of the Hub instance
     * 
     * @param reader
     */
    public void setReader(TokenInputStream reader);

    /**
     * Get the logged status
     * 
     * @return
     */
    public boolean isLoggedIn();

    /**
     * Set the logged status
     */
    public void setLoggedIn(boolean loggedIn);

    /**
     * Set the list of supported DC++ extensions
     * 
     * @param supports
     */
    public void setSupports(List supports);

    /**
     * Get the list of supported DC++ extensions
     * 
     * @return
     */
    public List getSupports();

}