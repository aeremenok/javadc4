/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: Enum.java,v 1.9 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.util;

/**
 * @author Jesper Nordenberg
 * @version $Revision: 1.9 $ $Date: 2005/10/02 11:42:28 $
 */
public class Enum
{

    /**
     * 
     */
    private final String name;

    /**
     * @param name
     */
    protected Enum(
        String name )
    {
        this.name = name;

    }

    /*
     * (non-Javadoc)
     *  
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return name;

    }

}

/*******************************************************************************
 * $Log: Enum.java,v $ Revision 1.9 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.8 2005/09/14
 * 07:11:48 timowest updated sources
 */
