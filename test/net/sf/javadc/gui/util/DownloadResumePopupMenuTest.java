/*
 * Copyright 2005 by Timo Westkämper
 * 
 * $Id$
 *
 * $Revision$
 */

package net.sf.javadc.gui.util;

import java.util.ArrayList;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.mockups.BaseDownloadManager;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.net.SearchResult;

public class DownloadResumePopupMenuTest extends TestCase {

    private Settings settings = new Settings();

    private SearchResult searchResult = new SearchResult();

    private IHub hub = new BaseHub();

    private IDownloadManager downloadManager = new BaseDownloadManager();

    public static void main(String[] args) {
        junit.textui.TestRunner.run(DownloadResumePopupMenuTest.class);
    }

    public void testCreation() {
        // DownloadResumePopupMenu(IHub _hub, SearchResult _searchResult,
        // List _allSelectedSearchResults,
        // ISettings _settings, IDownloadManager _downloadManager) {

        DownloadResumePopupMenu m = new DownloadResumePopupMenu(hub,
                searchResult, new ArrayList(), settings, downloadManager);
    }

}
