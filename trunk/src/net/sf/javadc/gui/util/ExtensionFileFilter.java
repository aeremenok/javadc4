/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.gui.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import net.sf.javadc.util.FileUtils;

/**
 * <CODE>ExtensionFileFilter</CODE> is a <CODE>FileFilter</CODE> extension which filters filenames based on the given
 * filename extension
 * 
 * @author tw70794
 */
public class ExtensionFileFilter
    extends FileFilter
{

    private final String extension;

    /**
     * Create an ExtensionFilterFilter which uses the given file extension as a filter criterium
     * 
     * @param _extension file extension to be used as filter criterion
     */
    public ExtensionFileFilter(
        String _extension )
    {
        extension = _extension.toLowerCase();

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    @Override
    public final boolean accept(
        File file )
    {
        return FileUtils.getExtension( file.getName().toLowerCase() ).equals( extension ) || file.isDirectory();

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    @Override
    public final String getDescription()
    {
        if ( extension.equals( "" ) )
        {
            return "Selected File Type (no extension)";

        }
        else
        {
            return "Selected File Type (*." + extension + ")";
        }

    }

}

/*******************************************************************************
 * $Log: ExtensionFileFilter.java,v $ Revision 1.9 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.8
 * 2005/09/14 07:11:49 timowest updated sources
 */
