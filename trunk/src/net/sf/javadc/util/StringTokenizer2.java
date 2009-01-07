/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: StringTokenizer2.java,v 1.9 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.util;

/**
 * @author Jesper Nordenberg
 * @version $Revision: 1.9 $ $Date: 2005/10/02 11:42:28 $
 */
public class StringTokenizer2
{

    /**
     * 
     */
    private final String string;

    /**
     * 
     */
    private final String delim;

    /**
     * 
     */
    private int          index = 0;

    /**
     * @param str
     * @param delim
     */
    public StringTokenizer2(
        String str,
        String delim )
    {
        string = str;
        this.delim = delim;

    }

    /**
     * @return
     */
    public int getPosition()
    {
        return index;

    }

    /**
     * @return
     */
    public String getTail()
    {
        return string.substring( getPosition() );

    }

    /**
     * @return
     */
    public boolean hasNextToken()
    {
        return index != string.length();

    }

    /**
     * @return
     */
    public String nextToken()
    {
        return nextToken( delim );

    }

    /**
     * @param delim
     * @return
     */
    public String nextToken(
        String delim )
    {
        int newIndex = string.indexOf( delim, index );
        String result;

        if ( newIndex == -1 )
        {
            result = string.substring( index );
            index = string.length();

        }
        else
        {
            result = string.substring( index, newIndex == -1 ? string.length() : newIndex );
            index = newIndex + delim.length();

        }

        return result;

    }

}

/*******************************************************************************
 * $Log: StringTokenizer2.java,v $ Revision 1.9 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.8
 * 2005/09/14 07:11:48 timowest updated sources
 */
