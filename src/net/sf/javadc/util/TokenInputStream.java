/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
 *
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software;      you can redistribute it and/or modify it
 * under the terms of the   GNU General Public License as published by the Free
 * Software Foundation;    either version 2 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;   without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE.   See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: TokenInputStream.java,v 1.13 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jesper Nordenberg
 * @version $Revision: 1.13 $ $Date: 2005/10/02 11:42:28 $
 */
public class TokenInputStream {

    private static final String defaultCharset = "ISO-8859-1";

    /**
     * 
     */
    private final InputStream in;

    // private final String separator;
    /**
     * 
     */
    private final char separator;

    // private String buffer = new String();
    /**
     * 
     */
    private final ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

    /**
     * Create a TokenInputStream with the given InputStream to be wrapped and
     * the token separator as a char
     * 
     * @param in
     *            InputStream instance to be wrapped by this TokenInputStream
     * @param separator
     *            character used as a separator to separate tokens
     */
    public TokenInputStream(InputStream in, char separator) {
        // this(in, String.valueOf(separator));
        this.in = in;

        this.separator = separator;

    }

    /**
     * Create a TokenInputStream with the given InputStream to be wrapped and
     * the token separator as a String
     * 
     * @param in
     *            InputStream instance to be wrapped by this TokenInputStream
     * @param separator
     *            String used as a separator to separate tokens
     */
    // public TokenInputStream(InputStream in, String separator) {
    //        
    // this.in = in;
    //        
    // this.separator = separator;
    //
    // }
    /**
     * Read a single token from the InputStream in non-blocking mode
     * 
     * @return
     * @throws IOException
     */
    public String readToken() throws IOException {
        return readToken(false);

    }

    /**
     * Read a single token from the InputStream
     * 
     * @param blocking
     * @return
     * @throws IOException
     */
    public String readToken(boolean blocking) throws IOException {
        // byte lastByte = 0;
        boolean cont = true;

        while (cont) {
            if (!blocking && (available() == 0))
                return null;

            int ch = in.read();

            if (ch == -1)
                throw new IOException("End of stream!");
            else if (((char) ch) == separator)
                cont = false;
            else
                byteBuffer.write(ch);
        }

        // use ISO-8859-1 as default charset

        String result = byteBuffer.toString(defaultCharset);

        // utf is utilized

        if (result.startsWith("$U")) {
            result = byteBuffer.toString("UTF-8");

        }

        byteBuffer.reset();

        return result;
    }

    /**
     * Return whether the TokenInputStream is available
     * 
     * @return
     * @throws IOException
     */
    public int available() throws IOException {
        return in.available();

    }

    /**
     * Read the given amount of bytes from the Stream
     * 
     * @param buffer
     * @return
     * @throws IOException
     */
    public int read(byte[] buffer) throws IOException {
        return in.read(buffer);

    }

    /**
     * Closes the underlying InputStream
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        in.close();
    }

}

/*******************************************************************************
 * $Log: TokenInputStream.java,v $
 * Revision 1.13  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.12 2005/09/14 07:11:48 timowest
 * updated sources
 * 
 * 
 * 
 */
