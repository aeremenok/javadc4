/*
 * Copyright (C) 2004 Marco Bazzoni This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.net;

import net.sf.javadc.interfaces.ISearchRequestFactory;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Category;

/**
 * <code>SearchRequestFactory</code> is a pooled factory method implementation to create <code>SearchRequest</code>
 * instances based on query strings
 * 
 * @author Timo Westk�mper
 */
public class SearchRequestFactory
    extends GenericObjectPool
    implements
        ISearchRequestFactory
{
    private final static Category logger = Category.getInstance( SearchRequestFactory.class );

    /**
     * Create a SearchRequestFactory which uses the SearchRequestObjectFactory as the default PoolableObjectFactory
     */
    public SearchRequestFactory()
    {
        super( new SearchRequestObjectFactory() );
    }

    /**
     * Obtain an instance from my pool. By contract, clients MUST return the borrowed instance using returnObject or a
     * related method as defined in an implementation or sub-interface.
     * 
     * @see org.apache.commons.pool.BaseObjectPool#borrowObject()
     */
    @Override
    public Object borrowObject()
        throws Exception
    {
        return super.borrowObject();

    }

    /**
     * Create a SearchRequest from the given search query
     * 
     * @param searchString query string to be parsed
     * @return constructed SearchRequest
     */
    public SearchRequest createFromHubRequest(
        String searchString )
    {
        SearchRequest sr = createFromQuery( searchString.substring( searchString.indexOf( " " ) + 1 ) );

        // if we have hub: it's a passive search.
        if ( searchString.indexOf( "Hub:" ) > -1 )
        {
            sr.setActiveMode( false );

            // set the nick as respond address
            sr
                .setRespondAddress( searchString
                    .substring( searchString.indexOf( ":" ) + 1, searchString.indexOf( " " ) ) );

        }
        else
        { // it's an active search.
            sr.setActiveMode( true );

            // set the udp-adress as respond adress
            sr.setRespondAddress( searchString.substring( 0, searchString.indexOf( " " ) ) );

        }

        return sr;

    }

    /**
     * Parse the given search query string and create a SearchRequest from it
     * 
     * @param searchQuery search query to be used
     * @return constructed SearchRequest
     */
    public SearchRequest createFromQuery(
        String searchQuery )
    {
        boolean sizeMatters = false;
        boolean isMinSize = false;
        long size = 0;
        int type = 1;

        String name = null;
        String[] elements = searchQuery.split( "\\?" );

        try
        {
            sizeMatters = elements[0].equals( "T" );
            isMinSize = elements[1].equals( "F" );
            size = Long.parseLong( elements[2] );
            type = Integer.parseInt( elements[3] );

            // TTH: prefix is stripped off
            if ( type == SearchRequest.TTH )
            {
                name = elements[4].substring( 4 );
            }
            else
            {
                name = elements[4].replace( '$', ' ' );
            }

        }
        catch ( ArrayIndexOutOfBoundsException ex )
        {
            logger.warn( "Query string " + searchQuery + " was invalid.", ex );
            // logger.warn(ex);
        }

        SearchRequest sr = null;

        try
        {
            sr = (SearchRequest) borrowObject();

            sr.setNamePattern( name != null ? name.trim() : "" );
            sr.setFileType( type );
            sr.setSize( sizeMatters ? size : 0 );
            sr.setMinSize( isMinSize );
            sr.setFreeSlots( false );

        }
        catch ( Exception e )
        {
            String error = "Problems when trying to set attributes of SearchRequest.";
            logger.error( error, e );
            // logger.error(e);

            // throws the exception to the next level, as not exceptions should
            // occurr in this code
            throw new RuntimeException( error, e );
        }

        return sr;
    }

    /**
     * Return an instance to my pool. By contract, obj MUST have been obtained using borrowObject or a related method as
     * defined in an implementation or sub-interface.
     * 
     * @see org.apache.commons.pool.BaseObjectPool#returnObject(java.lang.Object)
     */
    @Override
    public void returnObject(
        Object arg0 )
        throws Exception
    {
        super.returnObject( arg0 );

    }

}

/*******************************************************************************
 * $Log: SearchRequestFactory.java,v $ Revision 1.13 2005/10/02 11:42:27 timowest updated sources and tests Revision
 * 1.12 2005/09/15 17:37:14 timowest updated sources and tests Revision 1.11 2005/09/12 21:12:02 timowest added log
 * block
 */

