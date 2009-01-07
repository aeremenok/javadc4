/*
 * Copyright (c) 1999-2001 Keiron Liddle, Aftex Software This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version. This library is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */

package net.sf.javadc.util.bzip2;

import java.io.InputStream;
import java.io.OutputStream;

public class CBZip2Compress
{
    ProgressListener progress;

    /**
     * @deprecated
     */
    @Deprecated
    public CBZip2Compress(
        ProgressListener prog )
    {
        progress = prog;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void compressStream(
        InputStream stream,
        OutputStream zStream,
        int inBlockSize )
    {
        BZip.compress( stream, zStream, inBlockSize, progress );
    }
}