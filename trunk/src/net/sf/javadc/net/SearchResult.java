/*
 * Copyright (C) 2001 Stefan Görling, stefan@gorling.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: SearchResult.java,v 1.21 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.net;

import java.io.File;
import java.util.Date;
import java.util.StringTokenizer;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.hub.HostInfo;
import net.sf.javadc.net.hub.Hub;
import net.sf.javadc.net.hub.HubUser;
import net.sf.javadc.util.FileInfo;

import org.apache.log4j.Category;

/**
 * <code>SearchResult</code> holds information about a search result and or the download status of it.
 */
public class SearchResult
{
    private final static Category logger   = Category.getInstance( SearchResult.class );

    // private boolean bDir = false;
    /**
     * 
     */
    private Date                  date;

    /**
     * 
     */
    private String                filename = new String();

    /**
     *  
     */
    private long                  fileSize;

    /**
     * 
     */
    private int                   freeSlotCount;

    /**
     * 
     */
    private HostInfo              host     = new HostInfo();

    // components
    /**
     * 
     */
    private IHub                  hub;

    /**
     * 
     */
    private int                   maxSlotCount;

    /**
     * 
     */
    private String                nick;

    /**
     * 
     */
    private int                   ping;

    /**
     * 
     */
    private ISettings             settings;

    /**
     * 
     */
    private String                tempDownloadDir;

    /**
     * 
     */
    private String                tth;

    /**
     * 
     */
    private File                  tempDownloadFile;

    /**
     * Creates an empty instance of <CODE>SearchResult</CODE> the empty instance is used for loading
     * <CODE>SearchResult</CODE> s from the serialized XML version
     */
    public SearchResult()
    {
        maxSlotCount = 3;

        // ?
    }

    /**
     * Creates a <CODE>SearchResult</CODE> object from the given <CODE>
     * FileInfo</CODE>,<CODE>SearchRequest</CODE> and
     * <CODE>ISettings
     * </CODE> this constructor is used from the <CODE>ShareManager</CODE> component to construct sets of search results
     */
    public SearchResult(
        FileInfo fileInfo,
        SearchRequest sr,
        ISettings _settings )
    {
        this();
        settings = _settings;

        nick = sr.getRespondAddress();
        filename = fileInfo.getName();
        fileSize = (int) fileInfo.getLength();
        host = new HostInfo( "" );

        tth = fileInfo.getHash().getRoot();

        // freeSlotCount = settings.getFreeUploadSlotCount();
        // maxSlotCount = settings.getUploadSlots();

        tempDownloadDir = settings.getTempDownloadDir();
    }

    /**
     * Creates a <CODE>SearchResult</CODE> object from the given <CODE>IHub
     * </CODE>, search result and <CODE>ISettings</CODE>
     */
    public SearchResult(
        IHub _hub,
        String sresult,
        ISettings _settings )
        throws Exception
    {
        this();
        settings = _settings;

        hub = _hub;
        host = hub.getHost();

        StringTokenizer st = new StringTokenizer( sresult, String.valueOf( (char) 5 ) );

        // nick and filename information
        String tmp = st.nextToken();

        nick = tmp.substring( 0, tmp.indexOf( " " ) );

        // if the string contains also information about the slots,
        // don't use the next token
        if ( tmp.matches( ".+\\s\\d+/\\d+" ) )
        {
            filename = tmp.substring( tmp.indexOf( " " ) + 1, tmp.lastIndexOf( " " ) );
            tmp = tmp.substring( tmp.lastIndexOf( " " ) + 1 );

            // use the next token
        }
        else
        {
            filename = tmp.substring( tmp.indexOf( " " ) + 1 );
            tmp = st.nextToken();

        }

        if ( tmp.matches( "\\d+\\s\\d+/\\d+" ) )
        { // digits digits/digits

            try
            {
                fileSize = Long.parseLong( tmp.substring( 0, tmp.indexOf( " " ) ) );

            }
            catch ( Throwable e )
            {
                String error = "Catched " + e.getClass().getName() + " when trying " + "parse " + tmp;

                logger.error( error );
                logger.error( e );

                // this probably means we got a Directory instead of a file
                // bDir = true;

                // weve got no filesize
                fileSize = -1;
                // tmp = filename;
            }

        }
        else
        {
            logger.debug( "No filesize information was contained in " + sresult );

        }

        tmp = tmp.substring( tmp.lastIndexOf( " " ) + 1 );

        freeSlotCount = Integer.parseInt( tmp.substring( 0, tmp.indexOf( "/" ) ) );
        maxSlotCount = Integer.parseInt( tmp.substring( tmp.indexOf( "/" ) + 1 ) );

        tmp = st.nextToken();

        // hashing information

        if ( tmp.startsWith( "TTH:" ) )
        {
            tth = tmp.substring( 4, tmp.indexOf( " " ) );

        }

        // we may want to parse and store the hub where this search originated
        // for reconnection later.

        tempDownloadDir = settings.getTempDownloadDir();
    }

    /**
     * Creates a <CODE>SearchResult</CODE> from the given <CODE>IHub</CODE>, client nick, filename and
     * <CODE>ISettings</CODE> used by <CODE>FileBrowseComponent</CODE>
     */
    public SearchResult(
        IHub _hub,
        String _nick,
        String _filename,
        ISettings _settings,
        int _freeSlotCount )
    {
        this();

        hub = _hub;
        nick = _nick;
        filename = _filename;
        settings = _settings;
        freeSlotCount = _freeSlotCount;

        tempDownloadDir = settings.getTempDownloadDir();
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
        if ( obj instanceof SearchResult )
        {
            SearchResult sr = (SearchResult) obj;

            // we want to avoid NullPointerExceptions in the next statement
            if ( obj == null )
            {
                return false;

            }
            else
            {
                return filename.equals( sr.filename ) && fileSize == sr.fileSize && host.equals( sr.host );

            }

        }
        else
        { // obj is not SearchResult

            return false;

        }

    }

    /**
     * Set the Date of this SearchResult
     * 
     * @return
     */
    public final Date getDate()
    {
        return date;

    }

    /**
     * Set the filename of this SearchResult
     * 
     * @return
     */
    public final String getFilename()
    {
        return filename;

    }

    /**
     * Get the file size in bytes
     * 
     * @return
     */
    public final long getFileSize()
    {
        return fileSize;

    }

    /**
     * Get the amount of free slots of the client which posted this SearchResult
     * 
     * @return
     */
    public final int getFreeSlotCount()
    {
        return freeSlotCount;

    }

    /**
     * Get the host of the client which posted this SearchResult
     * 
     * @return
     */
    public final HostInfo getHost()
    {
        return host;

    }

    /**
     * Get the Hub in which this SearchResult was posted
     * 
     * @return
     */
    public final IHub getHub()
    {
        return hub;

    }

    /**
     * ?
     * 
     * @return
     */
    public final int getMaxSlotCount()
    {
        return maxSlotCount;

    }

    /**
     * Get the nick of the client who posted this SearchResult
     * 
     * @return
     */
    public final String getNick()
    {
        return nick;

    }

    /**
     * ?
     * 
     * @return
     */
    public final long getPing()
    {
        return ping;

    }

    /**
     * Get the SearchResponse to the given Hub
     * 
     * @param hub
     * @return
     */
    public final String getSearchResponse(
        IHub hub )
    {
        StringBuffer str = new StringBuffer();

        // filename
        str.append( filename );
        str.append( ConstantSettings.SEARCHRESULT_SEP_CHAR );

        // filesize, free slot count and max slot count
        str.append( fileSize ).append( " " );
        str.append( freeSlotCount ).append( "/" ).append( maxSlotCount );
        str.append( ConstantSettings.SEARCHRESULT_SEP_CHAR );

        // tth if available
        if ( tth != null )
        {
            str.append( "TTH:" ).append( tth ).append( " " );
        }

        // hub name
        str.append( hub.getName() ).append( " (" ).append( hub.getHost() ).append( ")" );

        return str.toString();

    }

    /**
     * Return the download size of the temporary file related to this SearchResult (instead the getTemplateFileSize() of
     * the DownloadRequest) should be used, as the SearchResult might refer to a different file name)
     * 
     * @return
     */
    public final long getTempFileSize()
    {
        /*
         * if (tempDownloadDir == null) throw new
         * NullPointerException("tempDownloadDir was null.");
         */

        // lazy creation
        if ( tempDownloadFile == null )
        {
            createTempDownloadFile();
        }

        // logger.info(tempDownloadFile.getPath());

        return tempDownloadFile.length();

    }

    /**
     * @return
     */
    public String getTTH()
    {
        return tth;
    }

    /**
     * Retrieve the HubUser instance related to the client which posted this SearchRequest
     * 
     * @return
     */
    public final HubUser getUser()
    {
        return hub.getUser( nick );

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode()
    {
        // Decent hash function
        int r = 17;

        r = 37 * r + filename.hashCode();
        r = 37 * r + (int) fileSize;
        r = 37 * r + host.hashCode();

        return r;

    }

    /*
     * Reqeust a download for this SearchResult to the hub where this
     * SearchResult was posted
     * 
     * @throws IOException
     */

    /*
     * public final void requestDownload() throws IOException {
     * hub.requestDownload(new DownloadRequest(this, settings)); }
     */

    /*
     * @param localName @throws IOException
     */

    /*
     * public final void requestDownload(String localName) throws IOException {
     * hub.requestDownload(new DownloadRequest(this, localName, settings)); }
     */

    /**
     * @param d
     */
    public final void setDate(
        Date d )
    {
        date = d;

    }

    /**
     * @param filename
     */
    public final void setFilename(
        String filename )
    {
        this.filename = filename;

    }

    /**
     * @param fileSize
     */
    public final void setFileSize(
        long fileSize )
    {
        this.fileSize = fileSize;

    }

    /**
     * @param host
     */
    public final void setHost(
        HostInfo host )
    {
        this.host = host;

    }

    /**
     * @param hub
     */
    public final void setHub(
        Hub hub )
    {
        this.hub = hub;

    }

    /**
     * @param hub
     */
    public final void setHub(
        IHub hub )
    {
        this.hub = hub;

    }

    /**
     * @param slots
     */
    public final void setMaxSlotCount(
        int slots )
    {
        this.maxSlotCount = slots;

    }

    /**
     * @param nick
     */
    public final void setNick(
        String nick )
    {
        this.nick = nick;

    }

    /**
     * @param time
     */
    public final void setPing(
        int time )
    {
        ping = time;

    }

    /**
     * @param _settings
     */
    public void setSettings(
        ISettings _settings )
    {
        settings = _settings;

        tempDownloadDir = settings.getTempDownloadDir();
    }

    /**
     * @param tth
     */
    public void setTTH(
        String tth )
    {
        this.tth = tth;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString()
    {
        return filename;

    }

    /**
     * Create the File object for the temporary download file
     */
    private void createTempDownloadFile()
    {
        String _localfilename = filename;

        if ( _localfilename.indexOf( "\\" ) > -1 )
        {
            logger.debug( "Truncating path " + _localfilename );

            _localfilename = _localfilename.substring( _localfilename.lastIndexOf( "\\" ) + 1 );

            // truncating (Windows style paths)
        }
        else if ( _localfilename.indexOf( "/" ) > -1 )
        {
            logger.debug( "Truncating path " + _localfilename );

            _localfilename = _localfilename.substring( _localfilename.lastIndexOf( "/" ) + 1 );

            // no truncating
        }
        else
        {
            logger.debug( "No truncating needed for " + _localfilename );

        }

        tempDownloadFile = new File( tempDownloadDir, _localfilename );
    }

}

/*******************************************************************************
 * $Log: SearchResult.java,v $ Revision 1.21 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.20
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.19 2005/09/12 21:12:02 timowest added log block
 */
