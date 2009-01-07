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
 */

package net.sf.javadc.gui.model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.javadc.gui.IncompleteComponent;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IIncompletesLoader;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.SearchResultInfo;
import net.sf.javadc.net.hub.HubInfo;

import org.apache.log4j.Category;

import spin.Spin;

/**
 * <CODE>IncompletesLoader</CODE> is a component used by the <CODE>
 * IncompletesComponent</CODE> to load the queued <CODE>DownloadRequest
 * </CODE> instances from the XML serialized version into the application's
 * download queue
 * 
 * @author Timo Westk�mper
 */
public class IncompletesLoader implements IIncompletesLoader {

    private final static Category logger = Category
            .getInstance(IncompleteComponent.class); // log4j logger

    private String configFileName = "queue.xml";

    // components
    private final ISettings settings;

    private final IHubFactory hubFactory;

    /**
     * Create an IncompletesLoader with the given ISettings and IHubFactory and
     * default configuration file to use
     * 
     * @param _settings
     *            ISettings instance to be used
     * @param _hubFactory
     *            IHubFactory instance to be used
     */
    public IncompletesLoader(ISettings _settings, IHubFactory _hubFactory) {

        if (_settings == null)
            throw new NullPointerException("_settings was null.");
        else if (_hubFactory == null)
            throw new NullPointerException("_hubFactory was null.");

        settings = _settings;

        // spinned components
        hubFactory = (IHubFactory) Spin.off(_hubFactory);

    }

    /**
     * Create an IncompletesLoader with the given ISettings, IHubFactory and
     * configuration file to use
     * 
     * @param _settings
     *            ISettings instance to be used
     * @param _hubFactory
     *            IHubFactory instance to be used
     * @param configFileName
     *            filename of the configuration file
     */
    public IncompletesLoader(ISettings _settings, IHubFactory _hubFactory,
            String configFileName) {

        this(_settings, _hubFactory);

        if (configFileName == null)
            throw new NullPointerException("_configFileName was null.");

        this.configFileName = configFileName;
    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IIncompletesLoader#load()
     */
    public final List load() {
        // changed 27.04.2004 by Timo Westk�mper
        List searchResults = null;
        List downloadRequests = new ArrayList();

        XMLDecoder d = null;

        try {
            // create an XMLDecoder to deserialize the serialized
            d = new XMLDecoder(new BufferedInputStream(new FileInputStream(
                    configFileName)));

            // load the search results from the deserialized version
            searchResults = (ArrayList) d.readObject();

        } catch (Exception e) {
            logger.error(e);

        } finally {
            if (d != null) {
                d.close();

            }

        }

        if (searchResults == null) {
            searchResults = new ArrayList();

        }

        // HashMap hubList = new HashMap();
        try {
            int size = searchResults.size();

            for (int i = 0; i < size; i++) {
                SearchResultInfo srInfo = (SearchResultInfo) searchResults
                        .get(i);
                SearchResult sr = new SearchResult();

                sr.setFilename(srInfo.getFilename());
                sr.setFileSize((int) srInfo.getFileSize());
                sr.setNick(srInfo.getNick());
                sr.setHost(srInfo.getHost());

                // takes hash into account
                sr.setTTH(srInfo.getTTH());

                // the ISettings is needed to be able to get the temp file size
                // information
                sr.setSettings(settings);

                if (srInfo.getHub() != null) {
                    sr.setHub(hubFactory.createHub(srInfo.getHub()));

                } else {
                    logger.error("HubInfo was null.");

                }

                DownloadRequest dr = new DownloadRequest(sr, settings);
                dr.setLocalFilename(srInfo.getLocalFilename());

                downloadRequests.add(dr);

            }

        } catch (Exception e) {
            logger.error("Problems when trying to create SearchResults", e);

        }

        return downloadRequests;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IIncompletesLoader#save(java.util.List)
     */
    public final void save(List downloadRequests) {
        // changed 27.04.2004 by Timo Westk�mper

        DownloadRequest[] downreqs = new DownloadRequest[downloadRequests
                .size()];

        downreqs = (DownloadRequest[]) downloadRequests.toArray(downreqs);

        final List searchres = new ArrayList(downreqs.length);

        for (int i = 0; i < downreqs.length; i++) {
            // searchres.add(downreqs[i].getSearchResult());
            SearchResult sr = downreqs[i].getSearchResult();

            // create a SearchResultInfo instance and fill it with the
            // attributes
            // from the SearchResult instance
            SearchResultInfo srInfo = new SearchResultInfo();

            srInfo.setFilename(sr.getFilename());

            // use the local filename attribute from the DownloadRequest
            // to allow different filenames in the SearchResult and
            // DownloadRequest

            if (!downreqs[i].isSegment()) {
                srInfo.setLocalFilename(downreqs[i].getLocalFilename());

                // strip the suffix of
            } else {
                String localname = downreqs[i].getLocalFilename();
                // .nnnofnnn
                localname = localname.substring(0, localname.length() - 9);

                srInfo.setLocalFilename(localname);
            }

            srInfo.setFileSize(sr.getFileSize());

            srInfo.setNick(sr.getNick());
            srInfo.setHost(sr.getHost());

            // takes hash into account
            srInfo.setTTH(sr.getTTH());

            if (sr.getHub() != null) {
                srInfo.setHub(new HubInfo(sr.getHub()));

            } else {
                logger.error("Hub was null.");

            }

            searchres.add(srInfo);

        }

        XMLEncoder e = null;

        try {
            logger.info("Saving incomplete downloads.");

            e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(
                    configFileName)));

            e.writeObject(searchres);

            logger.info("Incomplete downloads saved.");

        } catch (Exception ex) {
            logger.error("Problems when trying to save incomplete downloads.",
                    ex);

        } finally {
            if (e != null) {
                e.close();

            }

        }

    }

}

/*******************************************************************************
 * $Log: IncompletesLoader.java,v $
 * Revision 1.16  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.15 2005/09/14 07:11:49 timowest
 * updated sources
 * 
 * 
 * 
 */
