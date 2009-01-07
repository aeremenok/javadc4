/*
 * Copyright (c) 1999-2001 Keiron Liddle, Aftex Software This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version. This library is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */

package net.sf.javadc.util.bzip2;

import java.io.File;

public class ZipThread
    extends Thread
{

    File in;

    File out;

    int  block;

    // BZipProgress guiProg;

    ZipThread(
        File i,
        File o,
        int b )
    {
        // guiProg = new BZipProgress(i.length(), "zipping file " + i);
        in = i;
        out = o;
        block = b;
    }

    @Override
    public void run()
    {
        BZip.compress( in, out, block, BZip.dummyProg );
        // guiProg.dispose();
    }
}