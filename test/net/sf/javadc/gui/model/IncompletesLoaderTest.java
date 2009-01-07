/*
 * Created on 13.11.2004
 */

package net.sf.javadc.gui.model;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sf.javadc.config.SettingsAdapter;
import net.sf.javadc.config.SettingsLoader;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.IIncompletesLoader;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.mockups.BaseHubTaskFactory;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.hub.HubFactory;
import net.sf.javadc.net.hub.HubManager;
import net.sf.javadc.util.TaskManager;

/**
 * @author Timo Westk�mper
 */
public class IncompletesLoaderTest extends TestCase {

    private ISettings settings = new SettingsAdapter(new SettingsLoader());

    private final ITaskManager taskManager = new TaskManager();

    private final IHubManager hubManager = new HubManager();

    private final IHubTaskFactory hubTaskFactory = new BaseHubTaskFactory();

    private IHubFactory hubFactory = new HubFactory(taskManager, hubManager,
            hubTaskFactory, settings);

    private IIncompletesLoader loader;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        settings.setTempDownloadDir("/tmp/");
    }

    /**
     * Constructor for IncompletesLoaderTest.
     * 
     * @param arg0
     */
    public IncompletesLoaderTest(String arg0) {
        super(arg0);
    }

    public void testLoading() throws Exception {
        loader = new IncompletesLoader(settings, hubFactory);

        assertTrue(loader.load() != null);
    }

    public void testLoading2() {
        loader = new IncompletesLoader(settings, hubFactory, "queue_test.xml");

        // create a set of DownloadRequest instances

        // 1
        SearchResult sr1 = new SearchResult();
        sr1.setFilename("test1");
        sr1.setTTH("dksjaLHFLJKE");

        DownloadRequest dr1 = new DownloadRequest(sr1, new File("test4"),
                settings);

        // 2
        SearchResult sr2 = new SearchResult();
        sr2.setFilename("test2");

        DownloadRequest dr2 = new DownloadRequest(sr2, new File("test5"),
                settings);

        // 3
        SearchResult sr3 = new SearchResult();
        sr3.setFilename("test3");

        DownloadRequest dr3 = new DownloadRequest(sr3, new File("test6"),
                settings);

        List saved = new ArrayList();

        saved.add(dr1);
        saved.add(dr2);
        saved.add(dr3);

        loader.save(saved);
        List loaded = loader.load();

        assertEquals("Amount of elements in loaded list is 3", loaded.size(), 3);

        // 1
        dr1 = (DownloadRequest) loaded.get(0);
        assertTrue(dr1.getLocalFilename().endsWith("test4"));
        assertTrue(dr1.getSearchResult().getFilename().equals("test1"));
        assertTrue(dr1.getSearchResult().getTTH().equals("dksjaLHFLJKE"));

        // 2
        dr2 = (DownloadRequest) loaded.get(1);
        assertTrue(dr2.getLocalFilename().endsWith("test5"));
        assertTrue(dr2.getSearchResult().getFilename().equals("test2"));

        // 3
        dr3 = (DownloadRequest) loaded.get(2);
        assertTrue(dr3.getLocalFilename().endsWith("test6"));
        assertTrue(dr3.getSearchResult().getFilename().equals("test3"));

    }

    public void testLoadAndSaveSegmentedDownload() {

        loader = new IncompletesLoader(settings, hubFactory, "queue_test.xml");

        SearchResult sr1 = new SearchResult();
        sr1.setFilename("test1");
        sr1.setTTH("dksjaLHFLJKE");

        DownloadRequest dr1 = new DownloadRequest(sr1, new File(
                "test4.001of004"), settings);

        assertTrue(dr1.getLocalFilename() != null);

        dr1.setSegment(new Point(0, 10));

        // save and load

        List saved = new ArrayList();
        saved.add(dr1);

        loader.save(saved);
        List loaded = loader.load();

        DownloadRequest dr2 = (DownloadRequest) loaded.get(0);

        // assertEquals(dr2.getLocalFilename(), null);

        System.out.println(dr2.getLocalFilename());
        assertTrue(dr2.getLocalFilename().endsWith("test4"));
    }

}