/*
 * Created on 17-dic-2004 Copyright (C) 2004 Marco Bazzoni This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version. This program is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place
 * - Suite 330, Boston, MA 02111-1307, USA.
 */
package net.sf.javadc.util.hash;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.bitzi.util.Base32;

/**
 * @author theBaz <CODE>HashTree</CODE> holds data of Hashed File. Internal rapresentation of a MerkleTree
 */
public class HashTree
{
    public static final int             HASH_SIZE            = 24;

    private HashInfo                    hashInfo;

    private List                        leaves;

    private ByteBuffer                  root;

    private byte[]                      digest;

    private static transient final int  KB                   = 1024;

    private static transient final int  MB                   = 1024 * KB;

    static transient final int          BLOCK_SIZE           = 1024;

    private static transient final byte INTERNAL_HASH_PREFIX = 0x01;

    /**
     * @param hashInfo HashTree descriptor
     */
    public HashTree(
        HashInfo hashInfo )
    {
        this.hashInfo = hashInfo;
        leaves = new ArrayList();
    }

    /**
     * @param buffer leaf's hash
     */
    public void addLeaf(
        ByteBuffer buffer )
    {
        leaves.add( buffer );
    }

    public long calculateBlockSize(
        long fileSize,
        int treeDepth )
    {
        long tmp = BLOCK_SIZE;
        int maxHashes = 1 << treeDepth;
        while ( maxHashes * tmp < fileSize )
        {
            tmp *= 2;
        }
        return tmp;
    }

    public int calculateDepth(
        long size )
    {
        if ( size < 256 * KB )
        {
            return 0;
        }
        else if ( size < 512 * KB )
        {
            return 1;
        }
        else if ( size < MB )
        {
            return 2;
        }
        else if ( size < 2 * MB )
        {
            return 3;
        }
        else if ( size < 5 * MB )
        {
            return 4;
        }
        else if ( size < 10 * MB )
        {
            return 5;
        }
        else if ( size < 20 * MB )
        {
            return 6;
        }
        else if ( size < 50 * MB )
        {
            return 7;
        }
        else if ( size < 100 * MB )
        {
            return 8;
        }
        else
        {
            return 9;
        }
    }

    /**
     * @param leaves
     * @return
     */
    public void calculateRoot(
        List leaves )
    {
        MessageDigest tt = new Tiger();
        Stack firstStack = new Stack();
        Stack secondStack = new Stack();
        // Iterator iterator = leaves.iterator();
        ByteBuffer buffer;
        for ( int leaf = leaves.size() - 1; leaf >= 0; leaf-- )
        {
            buffer = (ByteBuffer) leaves.get( leaf );
            buffer.position( 0 );

            byte[] data = new byte[HashTree.HASH_SIZE];
            for ( int i = 0; i < data.length; i++ )
            {
                data[i] = buffer.get();
            }
            firstStack.push( data );
        }
        while ( true )
        {
            while ( firstStack.size() > 1 )
            {
                byte[] left = (byte[]) firstStack.pop();
                byte[] right = (byte[]) firstStack.pop();
                tt.reset();
                tt.update( INTERNAL_HASH_PREFIX ); // node prefix
                tt.update( left, 0, left.length );
                tt.update( right, 0, right.length );
                secondStack.push( tt.digest() );
            }
            if ( !firstStack.empty() )
            {
                secondStack.push( firstStack.pop() );
            }
            if ( secondStack.size() == 1 )
            {
                break;
            }
            while ( !secondStack.empty() )
            {
                firstStack.push( secondStack.pop() );
            }
        }

        digest = (byte[]) secondStack.pop();
        setRoot( ByteBuffer.wrap( digest ) );
    }

    /**
     * @return Returns the root.
     */
    public String getBase32Root()
    {
        return Base32.encode( digest );
    }

    /**
     * @return Returns the hashInfo.
     */
    public HashInfo getHashInfo()
    {
        return hashInfo;
    }

    /**
     * @return Returns the leaves.
     */
    public List getLeaves()
    {
        return leaves;
    }

    /**
     * @return Returns the root.
     */
    public ByteBuffer getRoot()
    {
        return root;
    }

    /**
     * @return Returns the root.
     */
    public byte[] getRootDigest()
    {
        return digest;
    }

    /**
     * @param leaves The leaves to set.
     */
    public void setLeaves(
        List leaves )
    {
        this.leaves = leaves;
    }

    /**
     * @param root The root to set.
     */
    public void setRoot(
        ByteBuffer root )
    {
        this.root = root;
    }

}
