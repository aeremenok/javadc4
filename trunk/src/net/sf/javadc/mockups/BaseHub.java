/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.mockups;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.listeners.HubListener;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.net.hub.AbstractHub;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.net.hub.HubUser;
import net.sf.javadc.util.TokenInputStream;

/**
 * @author tw70794 To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class BaseHub
    extends AbstractHub
    implements
        IHub
{

    private String name = null;

    /**
     * Create a new BaseHub instance
     */
    public BaseHub()
    {
    }

    /**
     * Create a new BaseHub instance with the given name
     * 
     * @param name
     */
    public BaseHub(
        String name )
    {
        if ( name == null )
        {
            throw new NullPointerException( "name was null." );
        }

        this.name = name;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#addSearchResult(net.sf.javadc.net.SearchResult)
     */
    public void addSearchResult(
        SearchResult sr )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#addUser(net.sf.javadc.net.hub.HubUser)
     */
    public void addUser(
        HubUser ui )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#clearSearchResults()
     */
    public void clearSearchResults()
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#connect()
     */
    public void connect()
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#disconnect()
     */
    public void disconnect()
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getConnection()
     */
    public Socket getConnection()
    {
        // TODO Auto-generated method stub
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getDescription()
     */
    public String getDescription()
    {
        // TODO Auto-generated method stub
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getHost()
     */
    public HostInfo getHost()
    {
        return new HostInfo();

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getName()
     */
    public String getName()
    {
        return name;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getOpList()
     */
    public List getOpList()
    {
        // TODO Auto-generated method stub
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getReader()
     */
    public TokenInputStream getReader()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getSearchResults()
     */
    public SearchResult[] getSearchResults()
    {
        // TODO Auto-generated method stub
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getStartPing()
     */
    public long getStartPing()
    {
        // TODO Auto-generated method stub
        return 0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getSupports()
     */
    public List getSupports()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getUser(java.lang.String)
     */
    public HubUser getUser(
        String nick )
    {
        // TODO Auto-generated method stub
        return null;

    }

    /*
     * public boolean isLoggedIn() { // TODO Auto-generated method stub return
     * false;
     *  }
     */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getUserCount()
     */
    public int getUserCount()
    {
        // TODO Auto-generated method stub
        return 0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getUsers()
     */
    public Map getUsers()
    {
        // TODO Auto-generated method stub
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#getWriter()
     */
    public OutputStream getWriter()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#isConnected()
     */
    public boolean isConnected()
    {
        // TODO Auto-generated method stub
        return false;

    }

    public boolean isLoggedIn()
    {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#processCommand(java.lang.String)
     */
    public void processCommand(
        String cmdString )
        throws IOException
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#receivedNick(net.sf.javadc.net.client.Client)
     */
    public void receivedNick(
        Client client )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#reconnect()
     */
    public void reconnect()
        throws IOException
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#removeUser(net.sf.javadc.net.hub.HubUser)
     */
    public void removeUser(
        HubUser ui )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#requestConnection(java.lang.String)
     */
    public void requestConnection(
        String user )
        throws IOException
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run()
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ITask#runTask()
     */
    public void runTask()
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#search(net.sf.javadc.net.SearchRequest)
     */
    public void search(
        SearchRequest sr )
        throws IOException
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendChatMessage(java.lang.String)
     */
    public void sendChatMessage(
        String message )
        throws IOException
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendCommand(java.lang.String,
     *      java.lang.String)
     */
    public void sendCommand(
        String command,
        String data )
    {

        // TODO Auto-generated method stub
    }

    /*
     * public void setLoggedIn(boolean b) {
     *  // TODO Auto-generated method stub }
     */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendPrivateMessage(java.lang.String,
     *      java.lang.String)
     */
    public void sendPrivateMessage(
        String message,
        String nick )
        throws IOException
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#sendSearchResult(java.lang.String,
     *      java.lang.String)
     */
    public void sendSearchResult(
        String result,
        String to_nick )
        throws IOException
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setConnection(java.net.Socket)
     */
    public void setConnection(
        Socket socket )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setDescription(java.lang.String)
     */
    public void setDescription(
        String description )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setHost(net.sf.javadc.net.hub.HostInfo)
     */
    public void setHost(
        HostInfo host )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setLoggedIn(boolean)
     */
    public void setLoggedIn(
        boolean loggedIn )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setName(java.lang.String)
     */
    public void setName(
        String name )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setReader(net.sf.javadc.util.TokenInputStream)
     */
    public void setReader(
        TokenInputStream reader )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setSearchResults(java.util.List)
     */
    public void setSearchResults(
        List list )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setStartPing(long)
     */
    public void setStartPing(
        long l )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setSupports(java.util.List)
     */
    public void setSupports(
        List supports )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setUserCount(int)
     */
    public void setUserCount(
        int userCount )
    {

        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHub#setWriter(java.io.OutputStream)
     */
    public void setWriter(
        OutputStream writer )
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    @Override
    protected Class getListenerClass()
    {
        // TODO Auto-generated method stub
        return HubListener.class;

    }

}