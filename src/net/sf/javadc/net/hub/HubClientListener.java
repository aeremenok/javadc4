/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.net.hub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import net.sf.javadc.interfaces.IClient;
import net.sf.javadc.listeners.ClientListenerBase;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.InvalidArgumentException;
import net.sf.javadc.net.client.Client;
import net.sf.javadc.util.He3;

import org.apache.log4j.Category;

/**
 * <code>HubClientListener</code> represents a <code>ClientListener</code> extension used by <code>Hub</code> instances
 * to notify registered listeners when client specific browse lists have been downloaded.
 * 
 * @author Timo Westk�mper
 */
public class HubClientListener
    extends ClientListenerBase
{
    private final static Category logger = Category.getInstance( HubClientListener.class );

    /**
     *  
     */
    private final Hub             hub;

    /**
     * Creates a <CODE>HubClientListener</CODE> with the given <CODE>Hub
     * </CODE>
     */
    public HubClientListener(
        Hub hub )
    {
        this.hub = hub;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ClientListener#browseListDownloaded(net.sf.javadc.net.client.Client,
     *      net.sf.javadc.net.DownloadRequest, net.sf.javadc.net.hub.HostInfo)
     */
    @Override
    public final void browseListDownloaded(
        IClient client,
        DownloadRequest dr,
        HostInfo host )
    {
        try
        {
            if ( client == null )
            {
                throw new InvalidArgumentException( "Client was null" );

            }
            else if ( dr == null )
            {
                throw new InvalidArgumentException( "DownloadRequest was null" );

            }

            logger.debug( "about to read from " + dr.getLocalFilename() );

            File inFile = new File( dr.getLocalFilename() );
            File outFile = new File( dr.getLocalFilename() + ".dec" );

            FileInputStream fis = new FileInputStream( inFile );
            FileOutputStream fos = new FileOutputStream( outFile );

            // Huffman decoding
            boolean success = He3.decode( fis, fos );

            fis.close();
            fos.close();

            if ( success )
            {
                hub.fireBrowseListDecoded( hub.getUser( dr.getSearchResult().getNick() ) );

            }
            else
            {
                logger.debug( "decode browse list error" );

            }

        }
        catch ( Exception e )
        {
            logger.error( e.toString() );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.listeners.ClientListener#receivedNick(net.sf.javadc.net.client.Client)
     */
    @Override
    public final void receivedNick(
        Client client )
    {
        if ( client == null )
        {
            throw new InvalidArgumentException( "Client was null." );
        }

        HubUser ui = hub.getUser( client.getNick() );

        if ( ui != null )
        {
            ui.setClient( client );

        }
        else
        {
            logger.error( "There is no HubUser mapping available for nick " + client.getNick() + " in Hub " + hub );

            // TODO : create a new HubUser instance ?
        }

    }

}

/*******************************************************************************
 * $Log: HubClientListener.java,v $ Revision 1.14 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.13
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.12 2005/09/25 16:40:58 timowest updated sources and
 * tests Revision 1.11 2005/09/12 21:12:02 timowest added log block
 */
