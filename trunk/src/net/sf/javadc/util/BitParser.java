/*
 * Copyright (C) 2001 Stefan Görling, stefan@gorling.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: BitParser.java,v 1.9 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author twe
 */
public class BitParser
{
    /**
     * 
     */
    private int               bits;

    /**
     * 
     */
    private int               curBit;

    /** 
     * 
     */
    private int               curByte;

    /**
     * 
     */
    private final InputStream in;

    /**
     * Creates a BitParser object with the given parameters.
     * 
     * @param in The stream from which to read the data.
     */
    public BitParser(
        InputStream in )
        throws IOException
    {
        this.bits = 8;

        // mask the leftmost bit
        this.curBit = 1;
        this.in = in;
        this.curByte = this.in.read();

    }

    /** ********************************************************************** */

    /**
     * Returns the bit at the current location and moves to the next one.
     * 
     * @return The current bit.
     */
    public boolean getNext()
        throws IOException
    {
        boolean retval = false;

        // we're reading from a stream
        retval = (curByte & curBit) != 0;

        if ( curBit < 1 << this.bits - 1 )
        {
            // mask the next bit
            curBit <<= 1;

        }
        else
        {
            // move on to the next byte
            curBit = 1;
            curByte = this.in.read();

        }

        return retval;

    }

    // getNext()

    /**
     * Moves forward to the start of next byte.
     */
    public void seekNextByte()
        throws IOException
    {
        while ( this.curBit != 1 )
        {
            getNext();

        }

    }

}

/*******************************************************************************
 * $Log: BitParser.java,v $ Revision 1.9 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.8 2005/09/14
 * 07:11:48 timowest updated sources
 */
