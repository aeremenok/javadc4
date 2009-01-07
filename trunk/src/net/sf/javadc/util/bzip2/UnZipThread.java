/*
 * Copyright (c) 1999-2001 Keiron Liddle, Aftex Software
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *  
 */

package net.sf.javadc.util.bzip2;

import java.io.File;

public class UnZipThread extends Thread {

    File in;

    File out;

    // BZipProgress guiProg;

    UnZipThread(File i, File o) {
        // guiProg = new BZipProgress(i.length(), "unzipping file " + i);
        in = i;
        out = o;
    }

    public void run() {
        BZip.decompress(in, out, BZip.dummyProg);
        // guiProg.dispose();
    }
}