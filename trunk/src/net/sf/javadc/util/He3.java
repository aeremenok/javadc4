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

// $Id: He3.java,v 1.10 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Vector;

/**
 * This class is highly influenced by a Huffman encoder/decoder written by Ville
 * Luolajan-Mikkola <eagle42@earthling.net>
 */

public class He3 {

    static class HuffCouple {

        public char chr = 0;

        public int length = 0;

        public HuffCouple(char chr, int length) {
            this.length = length;
            this.chr = chr;
        }
    }

    /**
     * @author tw70794
     * 
     * To change the template for this generated type comment go to
     * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
     */

    static class HuffNode {

        public int chr = -1;

        public HuffNode left = null;

        public HuffNode right = null;

        public HuffNode() {

        }
    }

    /**
     * 
     */

    private He3() {

    }

    /**
     * Decodes the given data.
     * 
     * @param in
     *            The InputStream from which to read the data.
     * @param out
     *            The OutputStream to which to write the data.
     * @return <code>true</code> if compressing successful.
     */

    public static boolean decode(InputStream in, OutputStream out)
            throws IOException {
        BufferedInputStream buffin = new BufferedInputStream(in);
        buffin.mark(-1);
        // BufferedOutputStream buffout = new BufferedOutputStream(out);
        //
        // int iter = 0;
        int size = 0;
        // int checksum;

        try {
            // read the header
            if (buffin.read() != (int) 'H' || buffin.read() != (int) 'E'
                    || buffin.read() != (int) '3') {

                throw new IOException("Couldn't read Header");
            }

            buffin.read();
            // checksum = buffin.read();
            buffin.read();
            size = (buffin.read());

            size += (buffin.read()) << 8;
            size += (buffin.read()) << 16;
            size += (buffin.read()) << 24;

            // System.out.println("Size should be:" + size);
        } catch (ArrayIndexOutOfBoundsException e) {
            // invalid header
            System.err.println("Invalid header");
            return false;

        }

        int treeInfoSize;
        // byte[] treeInfo;
        // byte[] characters;
        // int charpos = 0;

        BitParser bp = null;
        Vector chars = new Vector();
        HuffNode root = new HuffNode();

        try {
            // read the tree
            treeInfoSize = (buffin.read());
            treeInfoSize += (buffin.read()) << 8;

            // System.out.println(treeInfoSize+" couples");
            for (int i = 0; i < treeInfoSize; i++) {

                char curChar = (char) buffin.read();
                int bits = buffin.read();
                // System.out.println("Char: "+(int)curChar+" bits:"+(int)bits);
                chars.addElement(new HuffCouple(curChar, bits));
            }

            bp = new BitParser(buffin);

            for (int i = 0; i < chars.size(); i++) {
                HuffCouple hc = (HuffCouple) chars.elementAt(i);
                HuffNode node = root;

                for (int j = 0; j < hc.length; j++) {
                    if (bp.getNext()) {
                        if (node.right == null)
                            node.right = new HuffNode();

                        node = node.right;
                    } else {
                        if (node.left == null)
                            node.left = new HuffNode();

                        node = node.left;
                    }
                }
                node.chr = hc.chr;
            }

        } catch (Exception e) {
            System.out.println("Error:" + e);
        }

        // int count = 0;
        bp.seekNextByte(); // go to the next whole byte.

        // Now we have some kind of tree - try to decompress it.

        for (int i = 0; i < size; i++) {
            HuffNode curNode = root;
            while (curNode.chr == -1) {
                if (bp.getNext()) {
                    curNode = curNode.right;

                } else {
                    curNode = curNode.left;

                }

                if (curNode == null) {
                    System.err.println("There was an error!");
                    throw new IOException();

                }

            }
            // System.out.println("Found:"+(char)curNode.chr);
            out.write(curNode.chr);
        }

        return true;
    } // decode()

    /**
     * @param in
     * @param out
     * @return
     * @throws IOException
     */
    public static boolean encode(InputStream in, OutputStream out)
            throws IOException {
        BufferedInputStream buffin = new BufferedInputStream(in);
        buffin.mark(-1);
        // BufferedOutputStream buffout = new BufferedOutputStream(out);
        // int iter = 0;
        // long size = 0;
        int checksum = 0;

        // Write Header
        out.write('H');
        out.write('E');
        out.write('3');
        out.write((char) 13); // vbCr
        StringBuffer buffer = new StringBuffer();

        checksum = in.read();
        buffer.append((char) checksum + "");
        int chr = 0;

        while ((chr = in.read()) != -1) {
            buffer.append((char) chr + "");
            checksum = checksum ^ (byte) chr;

        }

        // Write checksum
        out.write(checksum);
        // System.out.println("Buffer length is:"+buffer.length());
        // Write Size
        byte sizeBytes[] = (BigInteger.valueOf(buffer.length())).toByteArray();

        // System.out.println("Byte length is:"+sizeBytes.length);
        // Write sizeBytes in inverted order!
        // out.write(sizeBytes);

        for (int i = (sizeBytes.length - 1); i >= 0; i--) {
            out.write(sizeBytes[i]);

        }

        for (int i = 0; i < (4 - sizeBytes.length); i++) {
            out.write((byte) 0);
            // System.out.println("Wrote additional byte");
        }

        // Number of couples;
        out.write((byte) 255);
        out.write((byte) 0);

        // Each couple has 8 bits.
        for (int i = 0; i < 255; i++) {
            out.write((byte) i);
            out.write(8);

        }

        // Start of data, each byte decodes as iteself
        for (int i = 0; i < 255; i++) {
            out.write((byte) i);

        }

        // rest of data.

        out.write(buffer.toString().getBytes());

        return true;
    } // encode()

} // He3

/*******************************************************************************
 * $Log: He3.java,v $
 * Revision 1.10  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.9 2005/09/14 07:11:48 timowest updated sources
 * 
 * 
 * 
 */
