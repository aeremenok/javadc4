/*
 * Created on 9-dic-2004
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

import java.io.Serializable;

/**
 * @author theBaz
 * 
 * <CODE>HashInfo</CODE> holds HashTree metadata
 * 
 */
public class HashInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -359121060562296149L;

    private String type;

    private long index;

    private long leafSize;

    private String root;

    private long leafNumber;

    /**
     * Create a HashInfo instance
     */
    public HashInfo() {
        type = "TTH";
    }

    /**
     * @return Returns the index.
     */
    public long getIndex() {
        return index;
    }

    /**
     * @param index
     *            The index to set.
     */
    public void setIndex(long index) {
        this.index = index;
    }

    /**
     * @return Returns the leafSize.
     */
    public long getLeafSize() {
        return leafSize;
    }

    /**
     * @param leafSize
     *            The leafSize to set.
     */
    public void setLeafSize(long leafSize) {
        this.leafSize = leafSize;
    }

    /**
     * @return Returns the root.
     */
    public String getRoot() {
        return root;
    }

    /**
     * @param root
     *            The root to set.
     */
    public void setRoot(String root) {
        this.root = root;
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return Returns the leafNumber.
     */
    public long getLeafNumber() {
        return leafNumber;
    }

    /**
     * @param leafNumber
     *            The leafNumber to set.
     */
    public void setLeafNumber(long leafNumber) {
        this.leafNumber = leafNumber;
    }

    /**
     * @return
     */
    // public String toXML(){
    // // TODO
    // StringBuffer sb = new StringBuffer();
    // return sb.toString();
    // }
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return root;
    }
}
