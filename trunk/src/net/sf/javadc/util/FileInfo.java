/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: FileInfo.java,v 1.8 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.util;

import java.io.Serializable;

import net.sf.javadc.util.hash.HashInfo;

/**
 * <code>FileInfo</code> represents minimal information on a single file shared via the <code>ShareManager</code>
 * instance
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.8 $ $Date: 2005/10/02 11:42:28 $
 */
public class FileInfo
    implements
        Serializable
{

    /**
     * 
     */
    private static final long                 serialVersionUID   = -8180615293145193956L;

    /**
     * 
     */
    protected static final FileInfoComparator fileInfoComparator = new FileInfoComparator();

    // public attributes for performance reasons, an abstraction is not
    // necessary
    /**
     * 
     */
    private String                            absolutePath;

    /**
     * 
     */
    private long                              length;

    /**
     * 
     */
    private String                            name;

    /**
     * 
     */
    private HashInfo                          hash;

    /**
     * Create a FileInfo instance
     */
    public FileInfo()
    {
        hash = new HashInfo();
    }

    /**
     * Constructs a <CODE>FileInfo</CODE> object with the given absolute path, file name and file size
     */
    public FileInfo(
        String _absolutePath,
        String _name,
        long _length )
    {
        this();
        absolutePath = _absolutePath;
        name = _name;
        length = _length;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        Object obj )
    {

        if ( obj == this )
        {
            return true;

        }
        else if ( obj instanceof FileInfo )
        {
            FileInfo info = (FileInfo) obj;

            // check for length first, because this check is cheap
            if ( length == info.getLength() )
            {

                int comp = fileInfoComparator.compare( this, info );

                // check for filename
                if ( comp == 0 )
                {
                    return true;

                    // check for hash
                }
                else
                {
                    return hash.equals( info.getHash() );

                }

                // length doesn't fit
            }
            else
            {
                return false;

            }

        }
        else
        { // obj is no FileInfo

            return false;

        }

    }

    /**
     * @return Returns the absolutePath.
     */
    public String getAbsolutePath()
    {
        return absolutePath;
    }

    /**
     * @return Returns the hash.
     */
    public HashInfo getHash()
    {
        return hash;
    }

    /**
     * @return Returns the length.
     */
    public long getLength()
    {
        return length;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {

        if ( getHash() != null )
        {
            return getHash().hashCode();
        }
        else
        {
            return getName().hashCode();
        }
    }

    /**
     * @param absolutePath The absolutePath to set.
     */
    public void setAbsolutePath(
        String absolutePath )
    {
        if ( absolutePath == null )
        {
            throw new NullPointerException( "absolutePath was null." );
        }
        this.absolutePath = absolutePath;
    }

    /**
     * @param hash The hash to set.
     */
    public void setHash(
        HashInfo hash )
    {
        if ( hash == null )
        {
            throw new NullPointerException( "hash was null." );
        }
        this.hash = hash;
    }

    /**
     * @param length The length to set.
     */
    public void setLength(
        long length )
    {
        this.length = length;
    }

    /**
     * @param name The name to set.
     */
    public void setName(
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
        StringBuffer str = new StringBuffer();

        str.append( "absolutePath : " ).append( absolutePath );
        str.append( " / length : " ).append( length );
        str.append( " / name : " ).append( name );
        str.append( " / hash : " ).append( hash );

        return str.toString();

    }

}

/*******************************************************************************
 * $Log: FileInfo.java,v $ Revision 1.8 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.7 2005/09/26
 * 17:19:52 timowest updated sources and tests Revision 1.6 2005/09/14 07:11:48 timowest updated sources
 */
