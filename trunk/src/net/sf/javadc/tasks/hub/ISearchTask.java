/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.tasks.hub;

import java.util.List;

import net.sf.javadc.interfaces.ISearchRequestFactory;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.UDPUtils;
import net.sf.javadc.tasks.BaseHubTask;
import net.sf.javadc.util.PerformanceContext;

import org.apache.log4j.Category;

/**
 * <p>
 * Active User Request: $Search &lt;ip&gt;:&lt;port&gt; &lt;searchstring&gt;
 * </p>
 * <p>
 * &lt;ip&gt; is this client's IP address.<br/>
 * &lt;port&gt; is a UDP port on which the client is listening for responses.
 * </p>
 * <p>
 * eg. $Search 64.78.55.32:412 T?T?500000?1?madonna$ray
 * </p>
 * <p>
 * Passive User Request: $Search Hub:&lt;requestornick&gt; &lt;searchstring&gt;
 * </p>
 * <p>
 * &lt;requestornick&gt; is the Nick of the Passive User doing the Search.
 * </p>
 * <p>
 * eg. $Search Hub:SomeNick T?T?500000?1?madonna$ray
 * </p>
 * <p>
 * &lt;searchstring&gt; is a string describing the file the client is searching for it is made up of:
 * </p>
 * <p>
 * &lt;sizerestricted&gt;?&lt;isminimumsize&gt;?&lt;size&gt;?&lt;datatype&gt;?&lt;searchpattern&gt;<br/>
 * &lt;sizerestricted&gt; is 'T' if the search should be restricted to files of a minimum or maximum size, otherwise
 * 'F'.<br/>
 * &lt;isminimumsize&gt; is 'F' if &lt;sizerestricted&gt; is 'F' or if the size restriction<br/>
 * places an upper limit on file size, otherwise 'T'.<br/>
 * &lt;size&gt; is the minimum or maximum size of the file to report (according to &lt;isminimumsize&gt;) if
 * &lt;sizerestricted&gt; is 'T', otherwise 0.<br/>
 * &lt;datatype&gt; restricts the search to files of a particular type. It is an integer selected from:
 * </p>
 * <ul>
 * <li>1 for any file type</li>
 * <li>2 for audio files ("mp3", "mp2", "wav", "au", "rm", "mid", "sm")</li>
 * <li>3 for compressed files ("zip", "arj", "rar", "lzh", "gz", "z", "arc", "pak")</li>
 * <li>4 for documents ("doc", "txt", "wri", "pdf", "ps", "tex")</li>
 * <li>5 for executables ("pm", "exe", "bat", "com")</li>
 * <li>6 for pictures ("gif", "jpg", "jpeg", "bmp", "pcx", "png", "wmf", "psd")</li>
 * <li>7 for video ("mpg", "mpeg", "avi", "asf", "mov")</li>
 * <li>8 for folders</li>
 * </ul>
 * <p>
 * &lt;searchpattern&gt; is used by other users to determine if any files match. Non-alphanumeric characters (including
 * spaces and periods) are replaced by '$'. The server must forward this message unmodified to all the other users.
 * Every other user with one or more matching files must send a UDP packet to &lt;ip&gt;:&lt;port&gt; for Active
 * requests or a reply to the Hub for Passive requests.
 * </p>
 * <p>
 * See $SR command for details of response format.
 * </p>
 * 
 * @author tw70794
 */
public class ISearchTask
    extends BaseHubTask
{

    private static final Category       logger = Category.getInstance( ISearchTask.class );

    // components

    private final ISettings             settings;

    private final IShareManager         shareManager;

    private final ISearchRequestFactory factory;

    /**
     * Create a new ISearchTask instance
     * 
     * @param settings
     * @param shareManager
     */
    public ISearchTask(
        ISettings settings,
        IShareManager shareManager,
        ISearchRequestFactory factory )
    {

        if ( settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }
        else if ( shareManager == null )
        {
            throw new NullPointerException( "shareManager was null." );
        }
        else if ( factory == null )
        {
            throw new NullPointerException( "factory was null." );
        }

        this.settings = settings;
        this.shareManager = shareManager;
        this.factory = factory;

    }

    /**
     * Post the SearchResults back to the Hub
     * 
     * @param sr SearchRequest which was the basis for the search
     * @param resulta SearchResults obtained via the ShareManager
     */
    private final void postResults(
        SearchRequest sr,
        SearchResult[] resulta )
    {

        PerformanceContext cont =
            new PerformanceContext( "ISearchTask#postResults(SearchRequest, SearchResult[])" ).start();

        String nick = settings.getUserInfo().getNick();

        // UDP It!
        if ( sr.activeMode() )
        {
            for ( int i = 0; i < resulta.length; i++ )
            {
                // logger.debug("Found Match:" + resulta[i].toString());

                // logger.debug("Returning UDP address");

                UDPUtils.sendUDPString( "$SR " + nick + " " + resulta[i].getSearchResponse( hub ), sr
                    .getRespondAddress() );

            }

            // send it through the hub!
        }
        else
        {
            for ( int i = 0; i < resulta.length; i++ )
            {
                // logger.debug("Found Match:" + resulta[i].toString());

                sendCommand( "$SR", nick + " " + resulta[i].getSearchResponse( hub ) + (char) 5 +
                    sr.getRespondAddress() );

            }

        }

        cont.end();

        if ( cont.getDuration() > 500 )
        {
            logger.info( cont );
        }

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.HubBaseTask#runTaskTemplate()
     */
    @Override
    protected void runTaskTemplate()
    {
        String nick = settings.getUserInfo().getNick();
        boolean cont = true;

        logger.debug( "Received Search String : \n  " + cmdData );

        // active
        if ( settings.isActive() )
        {
            int dataPort = settings.getUserInfo().getPort();
            String ip = settings.getIP();

            // don't continue if Search is from own client
            if ( cmdData.indexOf( ip + ":" + dataPort ) > -1 )
            {
                cont = false;

            }

            // passive
        }
        else
        {
            // don't continue if Search is from own client
            if ( cmdData.indexOf( "Hub:" + nick ) > -1 )
            {
                cont = false;

            }

        }

        if ( cont )
        {
            // search
            SearchRequest sr = factory.createFromHubRequest( cmdData );

            List results = shareManager.search( sr );

            // no results
            if ( !results.isEmpty() )
            {
                SearchResult[] resulta;

                // NOTE : to avoid java.lang.ArrayStoreException
                synchronized ( results )
                {
                    resulta = (SearchResult[]) results.toArray( new SearchResult[0] );
                }

                postResults( sr, resulta );

            }
            else
            {
                logger.debug( "No results found." );

            }

            try
            {
                // return the SearchRequest to the pool
                factory.returnObject( sr );

            }
            catch ( Exception e )
            {
                logger.error( e );
                throw new HubTaskException( e );

            }

        }

    }

}