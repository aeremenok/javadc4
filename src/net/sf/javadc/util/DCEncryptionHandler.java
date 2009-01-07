/*
 * Copyright (C) 2001 Stefan Görling, stefan@gorling.se
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

// $Id: DCEncryptionHandler.java,v 1.9 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.util;

import java.util.HashMap;

/**
 * This class handles Encryption, i.e. we load the padding and replacement
 * values and calculates responses on demand. This class calculates the
 * challenge-response process.
 * 
 * Eric Prevoteau <www@ac2i.tzo.com>did break the algorithm in a nicer way, I
 * had a table of padding and replacement values which i used to calculate the
 * key. Eric went one step longer and found the pattern within the numbers, and
 * found a nibble swap. Therefore his algorithm will live on for eternity and
 * mine will be forever forgotten... :) / Stefan
 */

public class DCEncryptionHandler {

    private final static HashMap encoding = new HashMap();

    static {
        encoding.put(new Integer(124), "/%DCN" + 124 + "%/");
        encoding.put(new Integer(36), "/%DCN0" + 36 + "%/");
        encoding.put(new Integer(126), "/%DCN" + 126 + "%/");
        encoding.put(new Integer(96), "/%DCN0" + 96 + "%/");
        encoding.put(new Integer(5), "/%DCN00" + 5 + "%/");
        encoding.put(new Integer(0), "/%DCN00" + 0 + "%/");
    }

    public DCEncryptionHandler() {

    }

    /** ********************************************************************** */

    /***************************************************************************
     * 
     * This is where the magic happends, it takes a string, applies magic and
     * there is a response to the challenge.
     * 
     **************************************************************************/

    public String calculateValidationKey(String validationString) {

        /*
         * This code snippet is stolen and translated into java by Stefan from
         * the DCTC (Direct Connect Text Client) package, see above for further
         * explanation why...
         */

        /** ********************************************************************** */
        /* the key is quite easily :) computed from the lock */
        /* key[x]= ns(lock[x]^lock[x-1]) */
        /* ns is a nibble swap (switch the upper 4 bits with the lower 4 bits) */
        /* exception: */
        /* key[0] is a bit different */
        /* let's name A and B the 2 last bytes of the lock */
        /* key[0]= ns(lock[0]^A^B^0x05) ; 0x05 is a kind of magic nibble */
        /** ********************************************************************** */

        StringBuffer key = new StringBuffer();
        int i;
        int u, l, o;
        int v;
        int lockLength = validationString.length();

        /* first byte is a special case */
        u = (int) validationString.charAt(0);
        l = (int) validationString.charAt(lockLength - 1);
        o = (int) validationString.charAt(lockLength - 2);

        /* do xor */
        u = u ^ l ^ o ^ 0x05; /* don't forget the magic 0x5 */

        /* do nibble swap */
        v = (((u << 8) | u) >> 4) & 255;
        key.append((char) v);

        /* all other bytes use the same code */
        for (i = 1; i < lockLength; i++) {
            u = (int) validationString.charAt(i);
            l = (int) validationString.charAt(i - 1);

            /* do xor */
            u = u ^ l;

            /* do nibble swap */
            v = (((u << 8) | u) >> 4) & 255;

            key.append((char) v);
        }

        return encodeValidationKey(key.toString());

    }

    /***************************************************************************
     * Some characters are reserved, therefore we escape them using a weird
     * escaping method.
     **************************************************************************/

    private String encodeValidationKey(String key) {
        StringBuffer safeKey = new StringBuffer();

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            Object replace = encoding.get(new Integer((int) c));

            // escaping defined
            if (replace != null) {
                safeKey.append((String) replace);

                // no escaping
            } else {
                safeKey.append(c);

            }

        }

        return safeKey.toString();
    }

}

/*******************************************************************************
 * $Log: DCEncryptionHandler.java,v $
 * Revision 1.9  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.8 2005/09/14 07:11:48 timowest
 * updated sources
 * 
 * 
 * 
 */
