/*
 * Copyright (C) 2001 Stefan Görling, stefan@gorling.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

/**
 * @author twe
 */
public class ExtendedBufferedOutputStream
    extends BufferedOutputStream
{

    // private final static Category logger = Category
    // .getInstance(ExtendedBufferedOutputStream.class);

    /**
     * 
     */
    private static final int DEFAULT_BUFFER_SIZE = 512;

    /**
     * 
     */
    private final int        bufferSize;

    /**
     * Create a new ExtendedBufferedOutputStream
     * 
     * @param out
     */
    public ExtendedBufferedOutputStream(
        OutputStream out )
    {
        super( out );
        bufferSize = DEFAULT_BUFFER_SIZE;

    }

    /**
     * Create a new ExtendedBufferedOutputStream
     * 
     * @param out
     * @param size
     */
    public ExtendedBufferedOutputStream(
        OutputStream out,
        int size )
    {
        super( out, size );
        bufferSize = size;

    }

    /** ********************************************************************** */

    /**
     * Returns the number of bytes in the buffer
     * 
     * @return
     */
    public int bytesInBuffer()
    {
        return count;

    }

    /**
     * Returns the number of bytes that can be written without blocking.
     * 
     * @return
     */
    public int nonBlockingCapacity()
    {
        return bufferSize - bytesInBuffer();

    }

}

/*******************************************************************************
 * $Log: ExtendedBufferedOutputStream.java,v $ Revision 1.9 2005/10/02 11:42:28 timowest updated sources and tests
 * Revision 1.8 2005/09/14 07:11:48 timowest updated sources
 */
