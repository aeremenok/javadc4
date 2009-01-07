/*
 * Created on 12.11.2004
 */
package net.sf.javadc.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ISettingsLoader;
import net.sf.javadc.mockups.BaseSettingsLoader;

/**
 * @author Timo Westk�mper
 */
public class SettingsAdapterTest extends AbstractSettingsTest {

    private ISettings settings = new SettingsAdapter(new BaseSettingsLoader());

    // private final String tempDir = "C:\\Temp";

    private String tempDir;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        if (File.separatorChar == '/') {
            tempDir = "/tmp";
        } else {
            tempDir = "C:\\Temp";
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();

        new File(tempDir, "test").delete();
        new File(tempDir, "test2").delete();
    }

    /**
     * Constructor for SettingsAdapterTest.
     * 
     * @param arg0
     */
    public SettingsAdapterTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.AbstractSettingsTest#getSettings()
     */
    protected ISettings getSettings() {
        return settings;
    }

    public void testClearTemporaryDirectory() {

        ISettingsLoader loader = new BaseSettingsLoader() {

            public ISettings load() {
                Settings settings = new Settings();

                settings.setTempDownloadDir(tempDir);
                return settings;
            }

        };

        File tempDirFile = new File(tempDir);
        int numberOfFiles = tempDirFile.listFiles().length;

        File file = new File(tempDirFile, "test");

        try {
            new RandomAccessFile(file, "rw").close();

        } catch (FileNotFoundException e) {
            fail(e.toString());

        } catch (IOException io) {
            fail(io.toString());
        }

        createFile(tempDir, "test2");

        // ensure that the files have been added to the directory for temporary
        // downloads
        assertEquals(tempDirFile.listFiles().length - 2, numberOfFiles);

        SettingsAdapter adapter = new SettingsAdapter(loader);

        assertEquals(adapter.getTempDownloadDir(), tempDir);

        // ensure that the empty file has been removed from the directory for
        // temporary downloads
        assertEquals(tempDirFile.listFiles().length - 1, numberOfFiles);

    }

    private void createFile(String dir, String fileName) {
        File file = new File(dir, fileName);

        OutputStream outStream = null;

        try {
            outStream = new FileOutputStream(file);

        } catch (FileNotFoundException f) {
            fail(f.toString());

        }

        byte[] bytes = new byte[5];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = 127;
        }

        try {
            outStream.write(bytes);
            // outStream.flush();
            outStream.close();

        } catch (IOException io) {
            fail(io.toString());

        }
    }

}
