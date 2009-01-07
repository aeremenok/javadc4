/*
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
 *
 */
package net.sf.javadc.net;

import java.util.Iterator;
import java.util.List;

import net.sf.javadc.listeners.SegmentManagerListener;
import net.sf.javadc.util.GenericModel;

/**
 * @author Timo Westkämper
 * 
 * $Id: AbstractDownloadSegmentList.java,v 1.6 2005/10/02 11:42:27 timowest Exp $
 * 
 * $Author: timowest $
 */
public class AbstractDownloadSegmentList extends GenericModel {

    /**
     * Create a new AbstractDownloadSegmentList instance
     */
    public AbstractDownloadSegmentList() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.util.GenericModel#getListenerClass()
     */
    protected Class getListenerClass() {
        return SegmentManagerListener.class;

    }

    /**
     * Notify registered listeners to stop the given DownloadRequest
     * 
     * @param dr
     *            DownloadRequest to be stopped
     */
    protected void notifyDropDownload(DownloadRequest dr) {

        if (dr == null)
            throw new NullPointerException("dr was null.");

        SegmentManagerListener[] lists = (SegmentManagerListener[]) getListeners()
                .getListeners(SegmentManagerListener.class);

        for (int i = 0; i < lists.length; i++) {

            lists[i].dropDownload(dr);

        }

    }

    /**
     * Notify registered listeners to start the given DownloadRequest
     * 
     * @param dr
     *            DownloadRequest to be started
     */
    protected void notifyStartDownload(DownloadRequest dr) {

        if (dr == null)
            throw new NullPointerException("dr was null.");

        SegmentManagerListener[] lists = (SegmentManagerListener[]) getListeners()
                .getListeners(SegmentManagerListener.class);

        for (int i = 0; i < lists.length; i++) {

            lists[i].startDownload(dr);

        }

    }

    /**
     * Add the given list of listeners to the DownloadSegmentList
     * 
     * @param listeners
     *            Listeners to be added
     */
    protected void addListeners(List listeners) {

        if (listeners == null)
            throw new NullPointerException("listeners was null.");

        for (Iterator i = listeners.iterator(); i.hasNext();) {

            addListener((SegmentManagerListener) i.next());

        }

    }

}

/*******************************************************************************
 * $Log: AbstractDownloadSegmentList.java,v $
 * Revision 1.6  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.5 2005/09/12 21:12:02
 * timowest added log block
 * 
 * 
 */
