/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: HostInfo.java,v 1.14 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.hub;

import java.net.InetAddress;
import java.net.UnknownHostException;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.util.SafeParser;

/**
 * <CODE>HostInfo</CODE> represents the host information of a <CODE>Hub
 * </CODE>
 */
public class HostInfo
{
    // private static final Category logger = Logger.getLogger(HostInfo.class);
    /**
     * 
     */
    private int    port;

    /**
     *  
     */
    private int    alternatePort;

    /**
     * 
     */
    private String host        = "";

    /**
     * 
     */
    private String hostAndPort = null;

    /**
     * Create a HostInfo instance
     */
    public HostInfo()
    {
        setPort( ConstantSettings.DEFAULT_PORT );
        setAlternatePort( ConstantSettings.DEFAULT_PORT );

    }

    /**
     * constructs a <CODE>HostInfo</CODE> object from the <CODE>String
     * </CODE> representing the host and port of the hub or remote
     * client
     */
    public HostInfo(
        String hostAndPort )
    {
        this();

        if ( hostAndPort == null )
        {
            throw new NullPointerException( "hostAndPort was null." );
        }

        final String[] elements = hostAndPort.split( ":" );

        setHost( elements[0] );

        // string includes host address
        if ( elements.length > 1 )
        {
            setPort( SafeParser.parseInt( elements[1], ConstantSettings.DEFAULT_PORT ) );

            // string includes alternate host address
            if ( elements.length > 2 )
            {
                this.alternatePort = SafeParser.parseInt( elements[2], ConstantSettings.DEFAULT_PORT );

            }

        }

        // this.hostAndPort = hostAndPort;

    }

    /**
     * Create a new HostInfo instance
     * 
     * @param host
     * @param port
     */
    public HostInfo(
        String host,
        int port )
    {
        this();

        if ( host == null )
        {
            throw new NullPointerException( "host was null." );
        }

        setHost( host );
        setPort( port );
    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(
        Object obj )
    {
        if ( obj instanceof HostInfo )
        {
            // return getHostAndPort().equals(((HostInfo)obj).getHostAndPort());
            return getHost().equals( ((HostInfo) obj).getHost() );
        }
        else
        {
            return false;
        }

    }

    /**
     * Get the alternate port of the HostInfo instance
     * 
     * @return
     */
    public final int getAlternatePort()
    {
        return alternatePort;

    }

    /**
     * Get the Host of the HostInfo instance
     * 
     * @return
     */
    public final String getHost()
    {
        return host;

    }

    /**
     * Get the Host and port of the HostInfo instance
     * 
     * @return
     */
    public final String getHostAndPort()
    {
        if ( hostAndPort == null )
        {
            hostAndPort = host + ":" + port;
        }

        return hostAndPort;
    }

    /**
     * Get the IP string of the HostInto instance
     * 
     * @return
     * @throws UnknownHostException
     */
    public final String getIpString()
        throws UnknownHostException
    {
        return InetAddress.getByName( host ).getHostAddress();

    }

    /**
     * Get the port of the HostInfo instance
     * 
     * @return
     */
    public final int getPort()
    {
        return port;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode()
    {
        // return getHostAndPort().hashCode();
        return getHost().hashCode();
    }

    /**
     * Set the alternate port of the HostInfo instance
     * 
     * @param i
     */
    public void setAlternatePort(
        int i )
    {
        alternatePort = i;

    }

    /**
     * Set the host of the HostInfo instance
     * 
     * @param string
     */
    public void setHost(
        String string )
    {
        host = string;
        hostAndPort = null;
    }

    /**
     * Set the port of the HostInfo instance
     * 
     * @param i
     */
    public void setPort(
        int i )
    {
        port = i;
        hostAndPort = null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString()
    {
        return getHostAndPort();

    }

}

/*******************************************************************************
 * $Log: HostInfo.java,v $ Revision 1.14 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.13 2005/09/30
 * 15:59:53 timowest updated sources and tests Revision 1.12 2005/09/26 17:19:52 timowest updated sources and tests
 * Revision 1.11 2005/09/15 17:37:14 timowest updated sources and tests Revision 1.10 2005/09/12 21:12:02 timowest added
 * log block
 */
