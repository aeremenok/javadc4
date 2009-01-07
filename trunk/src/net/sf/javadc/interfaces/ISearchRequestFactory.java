/*
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.interfaces;

import net.sf.javadc.net.SearchRequest;

/**
 * <CODE>ISearchRequestFactory</CODE> represents the abstract interface for
 * <CODE>SearchRequestFactory</CODE>, which provides a pooled Factory Method
 * implementation for the creation of <CODE>SearchRequest</CODE> instances
 * 
 * @author Timo Westk�mper
 */
public interface ISearchRequestFactory extends IObject {

    /**
     * Parse the given search query string and create a SearchRequest from it
     * 
     * @param searchQuery
     *            search query to be used
     * @return constructed SearchRequest
     */
    public abstract SearchRequest createFromQuery(String searchQuery);

    /**
     * Create a SearchRequest from the given search query
     * 
     * @param searchString
     *            query string to be parsed
     * @return constructed SearchRequest
     */
    public abstract SearchRequest createFromHubRequest(String searchString);

    /**
     * Obtain an instance from my pool. By contract, clients MUST return the
     * borrowed instance using returnObject or a related method as defined in an
     * implementation or sub-interface.
     * 
     * @see org.apache.commons.pool.BaseObjectPool#borrowObject()
     */
    public abstract Object borrowObject() throws Exception;

    /**
     * Return an instance to my pool. By contract, obj MUST have been obtained
     * using borrowObject or a related method as defined in an implementation or
     * sub-interface.
     * 
     * @see org.apache.commons.pool.BaseObjectPool#returnObject(java.lang.Object)
     */
    public abstract void returnObject(Object arg0) throws Exception;
}