/*
 * Created on 20.4.2005
 */

package net.sf.javadc.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;

/**
 * @author Timo Westk�mper
 */
public class SegmentManagerMergeTaskTest extends TestCase {

    private String namePrefix;

    private int numberOfFiles = 4;

    private int fileSize = 10;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SegmentManagerMergeTaskTest.class);
    }

    /**
     * Constructor for SegmentManagerMergeTaskTest.
     * 
     * @param arg0
     */
    public SegmentManagerMergeTaskTest(String arg0) {
        super(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {

        if (File.separatorChar == '/') {
            namePrefix = "/tmp/test";
        } else {
            namePrefix = "C://test";
        }

        new File(namePrefix).delete();

        // iterate over the number of segments

        for (int i = 0; i < numberOfFiles; i++) {

            // create the files

            File file = new File(namePrefix
                    + DownloadSegmentList.getSuffix(i + 1, numberOfFiles));

            System.out.println("Created " + file.getPath());
            System.out.println();

            Writer writer = null;

            try {
                writer = new OutputStreamWriter(new FileOutputStream(file));

            } catch (FileNotFoundException fne) {
                throw new RuntimeException(
                        "Caught " + fne.getClass().getName(), fne);

            }

            for (int j = 0; j < fileSize; j++) {
                try {
                    writer.write(String.valueOf(i * fileSize + j % 10));

                } catch (IOException io) {
                    throw new RuntimeException("Caught "
                            + io.getClass().getName(), io);

                }

            }

            try {
                writer.close();

                System.out.println("Closed " + file.getPath());
                System.out.println();

            } catch (IOException io) {
                throw new RuntimeException("Caught " + io.getClass().getName(),
                        io);
            }

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    public void tearDown() {

        new File(namePrefix).delete();

        new File(namePrefix + ".001of004").delete();
        new File(namePrefix + ".002of004").delete();
        new File(namePrefix + ".003of004").delete();
        new File(namePrefix + ".004of004").delete();

    }

    public void testDirectUsage() {
        SegmentManagerMergeTask task = new SegmentManagerMergeTask(namePrefix,
                numberOfFiles);

        System.out.println("Running task.");
        System.out.println();

        task.runTask();

        // validate file

        validateFile();
    }

    public void testIndirectUsageViaDownloadSegmentList() throws Exception {

        /*
         * public DownloadSegmentList(int size, int segmentSize, int fullsize,
         * String localFilenamePrefix, ISettings settings)
         */

        new DownloadSegmentList(numberOfFiles, 10, 40, namePrefix,
                new Settings());

        Thread.sleep(2000);

        // validate file

        validateFile();
    }

    private void validateFile() {

        File file = new File(namePrefix);
        assertTrue("File " + namePrefix + " didn't exist.", file.exists());

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));

        } catch (FileNotFoundException fne) {
            throw new RuntimeException("Caught " + fne.getClass().getName(),
                    fne);

        }

        System.out.println("Ensuring that the concatenation of the segments "
                + "succeeded.");
        System.out.println();

        try {
            String input = reader.readLine();

            StringBuffer buffer = new StringBuffer();

            for (int i = 0; i < (numberOfFiles * fileSize); i++) {
                buffer.append(String.valueOf(i));
            }

            assertEquals("Concatenated file didn't have expected content",
                    input, buffer.toString());

        } catch (IOException io) {
            throw new RuntimeException("Caught " + io.getClass().getName(), io);
        }

    }

}
