/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.net;

import org.apache.commons.pool.BasePoolableObjectFactory;

/**
 * <code>SearchRequestObjectFactory</code> is a simple factory method implementation which is utilized in
 * <code>SearchRequestFactory</code>
 * 
 * @author Timo Westk�mper
 */
public class SearchRequestObjectFactory
    extends BasePoolableObjectFactory
{

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.pool.BasePoolableObjectFactory#makeObject()
     */
    @Override
    public Object makeObject()
        throws Exception
    {
        return new SearchRequest();
    }

    /*
     * (non-Javadoc)
     *  
     * @see org.apache.commons.pool.PoolableObjectFactory#validateObject(java.lang.Object)
     */
    @Override
    public boolean validateObject(
        Object obj )
    {
        return true;
    }

}

/*******************************************************************************
 * $Log: SearchRequestObjectFactory.java,v $ Revision 1.7 2005/10/02 11:42:27 timowest updated sources and tests
 * Revision 1.6 2005/09/12 21:12:02 timowest added log block
 */
