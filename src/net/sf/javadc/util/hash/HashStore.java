/*
 * Created on 18-dic-2004
 *
 * Copyright (C) 2004 Marco Bazzoni
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
package net.sf.javadc.util.hash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * @author theBaz
 * 
 * <CODE>HashStore</CODE> singleton class used to access HashData.dat file, in
 * which are stored hash data Uses {@link HashTreeRAF} to read, write and delete
 * <code>HashTree</code>s
 * 
 */
public class HashStore {
    private final static Category logger = Logger.getLogger(HashStore.class);

    private static HashStore instance;

    private static final String DATA_STORE_FILE = "HashData.dat";

    private HashTreeRAF store;

    /**
     * Singleton Style
     */
    private HashStore() {
        initialize();
    }

    /**
     * Get the instance of HashStore (Singleton Style)
     * 
     * @return The instance of HashStore
     */
    public static synchronized HashStore getInstance() {
        if (instance == null) {
            instance = new HashStore();
        }

        return instance;
    }

    /**
     * Reads from data file <code>{@link HashTree}</code> of specified file
     * 
     * @param hashInfo
     *            Info of the Hashed File
     * @return The requested HashTree, null if IOException occurs
     */
    public HashTree readHashTree(HashInfo hashInfo) {
        HashTree hashTree = null;
        try {
            hashTree = store.readHashTree(hashInfo);
        } catch (IOException e) {
            logger.error("Unable to read HashTree", e);
        }

        return hashTree;
    }

    /**
     * Appends <code>{@link HashTree}</code> to data file
     * 
     * @param hashTree
     *            HashTree to be added
     * @return Info of the Hashed File, null if IOException occurs
     */
    public HashInfo addHashTree(HashTree hashTree) {
        HashInfo hashInfo = null;
        try {
            hashInfo = store.writeHashTree(hashTree);
        } catch (IOException e) {
            logger.error("Unable to write HashTree", e);
        }

        return hashInfo;
    }

    /**
     * Deletes <code>{@link HashTree}</code> from data file. Actual deleting
     * task consists of writing a HashTree with all its hash set ZERO
     * 
     * @param hashInfo
     *            Info of the Hashed File to be deleted
     * @return Info of the deleted Hashed File, null if IOException occurs
     */
    public HashInfo deleteHashTree(HashInfo hashInfo) {
        HashInfo retValue = null;
        HashTree hashTree = createVoidHashTree(hashInfo);
        try {
            hashInfo = store.writeHashTree(hashTree, hashInfo.getIndex());
        } catch (IOException e) {
            logger.error("Unable to delete HashTree", e);
        }

        return retValue;
    }

    /**
     * Initializes actual store
     */
    private void initialize() {
        File fd = new File(DATA_STORE_FILE);
        try {
            store = new HashTreeRAF(fd, "rw");
        } catch (FileNotFoundException e) {
            logger.fatal(e);
        }
    }

    /**
     * Creates a void <code>{@link HashTree}</code> (which is a HashTree with
     * leaves and root hash set to ZERO) given a specified HashInfo
     * 
     * @param hashInfo
     *            Info of the HashedTree to be created
     * @return a Void HashTree;
     */
    private HashTree createVoidHashTree(HashInfo hashInfo) {
        HashTree hashTree = new HashTree(hashInfo);
        ByteBuffer buffer = ByteBuffer.allocateDirect(HashTree.HASH_SIZE);
        byte[] zero = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0 };
        buffer.put(zero);

        for (int i = 0; i < hashInfo.getLeafNumber(); i++) {
            hashTree.addLeaf(buffer);
        }
        hashTree.setRoot(buffer);

        return hashTree;
    }
}
