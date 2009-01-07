/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.util;

import java.util.Comparator;

/**
 * <code>FileInfoComparator</code> is used to compare <code>FileInfo</code> instances to put them in a suitable order
 * for the creation of a browse list
 * 
 * @author Timo Westk�mper
 */
public class FileInfoComparator
    implements
        Comparator
{

    /**
     * Compare two FileInfo objects and return whether they are equal or one is smaller / greater than the other
     */
    public int compare(
        Object o1,
        Object o2 )
    {
        FileInfo f1 = (FileInfo) o1;
        FileInfo f2 = (FileInfo) o2;

        String path1 = FileUtils.getPath( f1.getName() );
        String path2 = FileUtils.getPath( f2.getName() );

        int comp = path1.compareTo( path2 );

        if ( comp == 0 )
        { // if the paths are equal, compare the names

            String name1 = FileUtils.getName( f1.getName() );
            String name2 = FileUtils.getName( f2.getName() );

            return name1.compareTo( name2 );

        }

        return comp;

    }

}

/*******************************************************************************
 * $Log: FileInfoComparator.java,v $ Revision 1.5 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.4
 * 2005/09/14 07:11:48 timowest updated sources
 */
