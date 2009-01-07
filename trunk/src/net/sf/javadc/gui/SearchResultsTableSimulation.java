/*
 * Created on 18.4.2005
 */

package net.sf.javadc.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import net.sf.javadc.config.Settings;
import net.sf.javadc.gui.model.RowTableModel;
import net.sf.javadc.gui.model.SortableTable;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westkämper
 */
public class SearchResultsTableSimulation {

    private IHub hub = new BaseHub();

    private ISettings settings = new Settings();

    private IHubManager hubManager = new HubManager();

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(hubManager,
            segmentManager);

    private SearchComponent searchComponent = new SearchComponent(hub,
            settings, downloadManager);

    /**
     * Create a new SearchResultsTableSimulation instance
     */
    public SearchResultsTableSimulation() {

        settings.setTempDownloadDir("C:\\Temp");

        RowTableModel model = new RowTableModel(new String[] { "Name", "Ext",
                "Hash", "Size", "Nick", "Ping", "Slots" });

        String cmdData1 = new StringBuffer("User1 mypath\\motd1.txt").append(
                (char) 5).append("437 3/4").append((char) 5).append(
                "Testhub (10.10.10.10:411").append((char) 5).append("User1")
                .toString();

        String cmdData2 = new StringBuffer("User1 mypath\\motd2.txt").append(
                (char) 5).append("437 3/4").append((char) 5).append(
                "Testhub (20.10.10.10:411").append((char) 5).append("User2")
                .toString();

        SearchResult[] results = new SearchResult[2];

        try {
            results[0] = new SearchResult(hub, cmdData1, settings);
            results[1] = new SearchResult(hub, cmdData2, settings);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }

        for (int i = 0; i < results.length; i++) {
            model.addRow(results[i], searchComponent
                    .getSearchColumns(results[i]));
        }

        SortableTable searchResults = new SortableTable(new int[] { 170, 30,
                -1, 80, 90, 35, 40 }, new SearchResultsController(hub,
                settings, downloadManager, model), model, "hits");

        JFrame frame = new JFrame();

        frame.getContentPane().add(searchResults);

        frame.setLocation(100, 100);
        frame.setSize(800, 600);

        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }

        });

        frame.setVisible(true);

        try {
            while (true) {
                Thread.sleep(10000);

            }

        } catch (InterruptedException e) {
            // logger.error(e.toString());
            System.out.println(e.toString());

        }
    }

    public static void main(String[] args) {

        new SearchResultsTableSimulation();

    }
}

/*******************************************************************************
 * $Log: SearchResultsTableSimulation.java,v $
 * Revision 1.6  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.5 2005/09/14 07:11:49
 * timowest updated sources
 * 
 * 
 * 
 */
