/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.interfaces;

import java.util.List;

/**
 * <CODE>IIncompletesLoader</CODE> represent the abstract interface for <CODE>IncompletesLoader</CODE>, which loads and
 * serializes the list of queued <CODE>DownloadRequests</CODE>
 * 
 * @author Timo Westk�mper
 */
public interface IIncompletesLoader
    extends
        IObject
{
    /**
     * Load the queued of DownloadRequests
     * 
     * @return
     */
    public abstract List load();

    /**
     * Save the queue of DownloadRequests
     * 
     * @param downloadRequests
     */
    public abstract void save(
        List downloadRequests );

}