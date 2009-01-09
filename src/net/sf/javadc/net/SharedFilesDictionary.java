/*
 * Created on 12.6.2005
 */
package net.sf.javadc.net;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import net.sf.javadc.util.FileInfo;

/**
 * SharedFilesDictionary represents a mapping of path names and hash codes against FileInfo instances. One class for
 * both mappings is used, to keep both mappings synchronized.
 * 
 * @author Timo Westkämper
 */
public class SharedFilesDictionary
    implements
        Serializable
{
    private static final long     serialVersionUID = 3387645131279665673L;

    /**
     * Mapping of absolute file names against FileInfo instance
     */
    private Map<String, FileInfo> name2FileInfos;

    /**
     * Mapping of hash codes (TTH) against FileInfo instances
     */
    private Map<String, FileInfo> hash2FileInfos;

    /**
     * Create a new SharedFilesDictionary
     */
    public SharedFilesDictionary()
    {
        name2FileInfos = new HashMap<String, FileInfo>();
        hash2FileInfos = new HashMap<String, FileInfo>();
    }

    /**
     * Add the given FileInfo instance to the mappings
     * 
     * @param fileInfo
     */
    public final synchronized void add(
        FileInfo fileInfo )
    {
        Assert.assertNotNull( fileInfo );
        name2FileInfos.put( fileInfo.getAbsolutePath(), fileInfo );
        hash2FileInfos.put( fileInfo.getHash().getRoot(), fileInfo );
    }

    /**
     * Return whether a FileInfo instance is mapped against the given absolute path
     * 
     * @param absolutePath
     * @return
     */
    public final boolean containsFileNameKey(
        String absolutePath )
    {
        return name2FileInfos.containsKey( absolutePath );
    }

    /**
     * Return whether a FileInfo instance is mapped against the given hash code
     * 
     * @param tth
     * @return
     */
    public final boolean containsHashKey(
        String tth )
    {
        return hash2FileInfos.containsKey( tth );
    }

    /**
     * Return the file name key set
     * 
     * @return
     */
    public final Collection<String> fileNameKeySet()
    {
        return name2FileInfos.keySet();
    }

    /**
     * Return the FileInfo instance mapped against the given absolute path
     * 
     * @param path
     * @return
     */
    public final FileInfo getByFileName(
        String path )
    {
        return name2FileInfos.get( path );
    }

    /**
     * Return the FileInfo instance mapped against the given hash code
     * 
     * @param tth
     * @return
     */
    public final FileInfo getByHash(
        String tth )
    {
        return hash2FileInfos.get( tth );
    }

    /**
     * @return Returns the hash2FileInfos.
     */
    public final Map<String, FileInfo> getHash2FileInfos()
    {
        return hash2FileInfos;
    }

    /**
     * @return Returns the name2FileInfos.
     */
    public final Map<String, FileInfo> getName2FileInfos()
    {
        return name2FileInfos;
    }

    /**
     * Return the hash key set
     * 
     * @return
     */
    public final Collection<String> hashKeySet()
    {
        return hash2FileInfos.keySet();
    }

    /**
     * Remove the given FileInfo instance from the mappings
     * 
     * @param path
     */
    public final synchronized void remove(
        FileInfo fileInfo )
    {
        Assert.assertNotNull( fileInfo );
        name2FileInfos.remove( fileInfo.getAbsolutePath() );
        hash2FileInfos.remove( fileInfo.getHash().getRoot() );
    }

    /**
     * @param hash2FileInfos The hash2FileInfos to set.
     */
    public final void setHash2FileInfos(
        Map<String, FileInfo> hash2FileInfos )
    {
        Assert.assertNotNull( hash2FileInfos );
        this.hash2FileInfos = hash2FileInfos;
    }

    /**
     * @param name2FileInfos The name2FileInfos to set.
     */
    public final void setName2FileInfos(
        Map<String, FileInfo> name2FileInfos )
    {
        Assert.assertNotNull( name2FileInfos );
        this.name2FileInfos = name2FileInfos;
    }
}
