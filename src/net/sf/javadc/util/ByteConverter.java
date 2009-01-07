/*
 * Copyright (C) 2001 Stefan Görling, stefan@gorling.se
 * 
 * Copyright (C) 2004 Timo Westkämper
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: ByteConverter.java,v 1.9 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.util;

import java.text.DecimalFormat;

/**
 * @author twe
 */
public class ByteConverter {

    /**
     * 
     */
    private final static DecimalFormat format = new DecimalFormat("0.00");

    private final static String[] suffixes = { "", "k", "M", "G", "T" };

    /**
     * @param bytes
     * 
     * @return
     */
    public static String byteToShortString(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";

        }

        float b = bytes;
        int i = 0;

        while (b >= 1024) {
            b /= 1024;
            i++;

        }

        return format.format(b) + " " + suffixes[i] + "B";

    }

    /**
     * Formats a long into a size with byte suffix.
     */
    public static String byteToString(long bytes) {
        String str;
        int i = 0;

        do {
            str = Long.toString(bytes);

            if (str.length() <= 6) {
                break;

            }

            bytes >>= 10;
            i++;

        } while (str.length() > 6);

        return ((str.length() > 3) ? (str.substring(0, str.length() - 3) + " " + str
                .substring(str.length() - 3))
                : str)
                + " " + suffixes[i] + "B";

    }

}

/*******************************************************************************
 * $Log: ByteConverter.java,v $
 * Revision 1.9  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.8 2005/09/14 07:11:48 timowest
 * updated sources
 * 
 * 
 * 
 */
