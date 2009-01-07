/*
 * Created on 12.6.2005
 */
package net.sf.javadc.net;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.javadc.util.FileInfo;

/**
 * SharedFilesDictionary represents a mapping of path names and hash codes
 * against FileInfo instances. One class for both mappings is used, to keep both
 * mappings synchronized.
 * 
 * @author Timo Westkämper
 */
public class SharedFilesDictionary implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3387645131279665673L;

    /**
     * Mapping of absolute file names against FileInfo instance
     */
    private Map name2FileInfos;

    /**
     * Mapping of hash codes (TTH) against FileInfo instances
     */
    private Map hash2FileInfos;

    /**
     * Create a new SharedFilesDictionary
     */
    public SharedFilesDictionary() {

        name2FileInfos = new HashMap();

        hash2FileInfos = new HashMap();
    }

    // containsKey

    /**
     * Return whether a FileInfo instance is mapped against the given absolute
     * path
     * 
     * @param absolutePath
     * @return
     */
    public final boolean containsFileNameKey(String absolutePath) {

        return name2FileInfos.containsKey(absolutePath);

    }

    /**
     * Return whether a FileInfo instance is mapped against the given hash code
     * 
     * @param tth
     * @return
     */
    public final boolean containsHashKey(String tth) {

        return hash2FileInfos.containsKey(tth);

    }

    // keySet

    /**
     * Return the file name key set
     * 
     * @return
     */
    public final Collection fileNameKeySet() {

        return name2FileInfos.keySet();

    }

    /**
     * Return the hash key set
     * 
     * @return
     */
    public final Collection hashKeySet() {

        return hash2FileInfos.keySet();

    }

    // get

    /**
     * Return the FileInfo instance mapped against the given absolute path
     * 
     * @param path
     * @return
     */
    public final FileInfo getByFileName(String path) {

        return (FileInfo) name2FileInfos.get(path);

    }

    /**
     * Return the FileInfo instance mapped against the given hash code
     * 
     * @param tth
     * @return
     */
    public final FileInfo getByHash(String tth) {

        return (FileInfo) hash2FileInfos.get(tth);

    }

    // add

    /**
     * Add the given FileInfo instance to the mappings
     * 
     * @param fileInfo
     */
    public final synchronized void add(FileInfo fileInfo) {
        if (fileInfo == null)
            throw new NullPointerException("fileInfo was null.");

        name2FileInfos.put(fileInfo.getAbsolutePath(), fileInfo);

        hash2FileInfos.put(fileInfo.getHash().getRoot(), fileInfo);
    }

    // remove

    /**
     * Remove the given FileInfo instance from the mappings
     * 
     * @param path
     */
    public final synchronized void remove(FileInfo fileInfo) {
        if (fileInfo == null)
            throw new NullPointerException("fileInfo was null.");

        name2FileInfos.remove(fileInfo.getAbsolutePath());

        hash2FileInfos.remove(fileInfo.getHash().getRoot());
    }

    /**
     * @return Returns the hash2FileInfos.
     */
    public final Map getHash2FileInfos() {
        return hash2FileInfos;
    }

    /**
     * @param hash2FileInfos
     *            The hash2FileInfos to set.
     */
    public final void setHash2FileInfos(Map hash2FileInfos) {

        if (hash2FileInfos == null)
            throw new NullPointerException("hash2FileInfos was null.");
        this.hash2FileInfos = hash2FileInfos;
    }

    /**
     * @return Returns the name2FileInfos.
     */
    public final Map getName2FileInfos() {
        return name2FileInfos;
    }

    /**
     * @param name2FileInfos
     *            The name2FileInfos to set.
     */
    public final void setName2FileInfos(Map name2FileInfos) {

        if (name2FileInfos == null)
            throw new NullPointerException("name2FileInfos was null.");
        this.name2FileInfos = name2FileInfos;
    }
}

/*******************************************************************************
 * $Log: SharedFilesDictionary.java,v $
 * Revision 1.8  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.7 2005/09/26 17:19:52
 * timowest updated sources and tests
 * 
 * Revision 1.6 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
