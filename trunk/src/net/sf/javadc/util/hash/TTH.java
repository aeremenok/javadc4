/**
 * Copyright (C) 2004 Marco Bazzoni
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

package net.sf.javadc.util.hash;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Stack;

import com.bitzi.util.Base32;

/**
 * 
 * <code>TTH</code> computes the THEX [1] tree hash of a given byte stream,
 * using the Tiger[2] hash as the internal algorithm.
 * 
 * <p>
 * Relies on the implementation of the underlying Tiger algorithm from the
 * Cryptix JCE. See the Cryptix readme for more details.
 * </p>
 * <p>
 * [1] http://www.open-content.net/specs/draft-jchapweske-thex-01.html<br>
 * [2] http://www.cs.technion.ac.il/~biham/Reports/Tiger/
 * </p>
 * 
 * 
 * @author Marco Bazzoni
 * 
 */
public class TTH extends MessageDigest {

    private static final int BLOCKSIZE = 1024;

    private static final int HASHSIZE = 24;

    /**
     * 1024 byte buffer
     */
    private final byte[] buffer;

    /**
     * Buffer offset
     */
    private int bufferOffset;

    /**
     * Number of bytes hashed until now.
     */
    private long byteCount;

    /**
     * Internal Tiger MD instance
     */
    private MessageDigest tiger;

    /**
     * Interim tree node hash values
     */
    private Stack firstStack;

    private Stack secondStack;

    /**
     * Constructor
     */
    public TTH() throws NoSuchAlgorithmException {
        super("TigerTree");
        buffer = new byte[BLOCKSIZE];
        bufferOffset = 0;
        byteCount = 0;
        try {
            java.security.Security
                    .addProvider(new cryptix.jce.provider.CryptixCrypto());
            tiger = MessageDigest.getInstance("Tiger", "CryptixCrypto");
        } catch (NoSuchProviderException e) {
            System.out.println("Provider Cryptix not found");
            System.exit(0);
        }
        firstStack = new Stack();
        secondStack = new Stack();
    }

    protected int engineGetDigestLength() {
        return HASHSIZE;
    }

    protected void engineUpdate(byte in) {
        byteCount += 1;
        buffer[bufferOffset++] = in;
        if (bufferOffset == BLOCKSIZE) {
            blockUpdate();
            bufferOffset = 0;
        }
    }

    protected void engineUpdate(byte[] in, int offset, int length) {
        byteCount += length;

        int remaining;
        while (length >= (remaining = BLOCKSIZE - bufferOffset)) {
            System.arraycopy(in, offset, buffer, bufferOffset, remaining);
            bufferOffset += remaining;
            blockUpdate();
            length -= remaining;
            offset += remaining;
            bufferOffset = 0;
        }

        System.arraycopy(in, offset, buffer, bufferOffset, length);
        bufferOffset += length;
    }

    protected byte[] engineDigest() {
        byte[] hash = new byte[HASHSIZE];
        try {
            engineDigest(hash, 0, HASHSIZE);
        } catch (DigestException e) {
            return null;
        }
        return hash;
    }

    protected int engineDigest(byte[] buf, int offset, int len)
            throws DigestException {
        if (len < HASHSIZE)
            throw new DigestException();

        // hash any remaining fragments
        blockUpdate();
        if (!firstStack.isEmpty())
            secondStack.push(firstStack.pop());

        if (!secondStack.isEmpty()) {
            while (secondStack.size() > 1) {
                Stack tmpStack = new Stack();
                while (!secondStack.isEmpty()) {
                    tmpStack.push(secondStack.pop());
                }
                while (tmpStack.size() > 1) {
                    byte[] left = (byte[]) tmpStack.pop();
                    byte[] right = (byte[]) tmpStack.pop();
                    tiger.reset();
                    tiger.update((byte) 1); // node prefix
                    tiger.update(left);
                    tiger.update(right);
                    secondStack.push((Object) tiger.digest());
                }
                if (!tmpStack.isEmpty())
                    secondStack.push(tmpStack.pop());
            }
        }

        if (!secondStack.isEmpty()) {
            System.arraycopy(secondStack.pop(), 0, buf, offset, HASHSIZE);

        }

        engineReset();
        return HASHSIZE;
    }

    protected void engineReset() {
        bufferOffset = 0;
        byteCount = 0;
        firstStack = new Stack();
        secondStack = new Stack();
        tiger.reset();
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Update the internal state with a single block of size 1024 (or less, in
     * final block) from the internal buffer.
     */
    protected void blockUpdate() {
        tiger.reset();
        tiger.update((byte) 0); // leaf prefix
        tiger.update(buffer, 0, bufferOffset);
        if ((bufferOffset == 0) & (firstStack.size() == 0))
            return; // don't remember a zero-size hash except at very beginning
        firstStack.push((Object) tiger.digest());
        blockConsume();
    }

    protected void blockConsume() {
        while (firstStack.size() > 1) {
            byte[] right = (byte[]) firstStack.pop();
            byte[] left = (byte[]) firstStack.pop();
            tiger.reset();
            tiger.update((byte) 1); // node prefix
            tiger.update(left);
            tiger.update(right);
            secondStack.push((Object) tiger.digest());
        }
    }

    public static void main(String[] args) throws IOException,
            NoSuchAlgorithmException {
        if (args.length < 1) {
            System.out.println("You must supply a filename.");
            return;
        }
        MessageDigest tt = new TTH();
        FileInputStream fis;

        for (int i = 0; i < args.length; i++) {
            fis = new FileInputStream(args[i]);
            int read;
            byte[] in = new byte[1024];
            while ((read = fis.read(in)) > -1) {
                tt.update(in, 0, read);
            }
            fis.close();
            byte[] digest = tt.digest();
            String hash = new BigInteger(1, digest).toString(16);
            while (hash.length() < 48) {
                hash = "0" + hash;
            }
            System.out.println("hex:" + hash);
            System.out.println("b32:" + Base32.encode(digest));
            tt.reset();
        }
    }
}