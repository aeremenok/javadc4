/*
 * Created on 8.6.2005
 */

package net.sf.javadc.net.hub;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import junit.framework.TestCase;
import net.sf.javadc.util.bzip2.CBZip2InputStream;

/**
 * @author Timo Westkämper
 */
public class HubListXMLTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(HubListXMLTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for HubListXMLTest.
     * 
     * @param arg0
     */
    public HubListXMLTest(String arg0) {
        super(arg0);
    }

    public void testReading() throws FileNotFoundException, IOException {

        reading(false);

    }

    public void testReadingSafe() throws FileNotFoundException, IOException {

        reading(true);

    }

    private void reading(boolean useSafe) throws FileNotFoundException,
            IOException {
        File file = new File("test/data/FullHubList.xml.bz2");

        assertTrue(file.exists());

        InputStream inputStream = new FileInputStream(file);

        assertTrue(readFileHeader(inputStream));

        inputStream = new CBZip2InputStream(inputStream);

        if (useSafe) {
            inputStream = new SafeInputStream(inputStream);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));

        BufferedWriter writer = null;

        if (useSafe) {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(new File(
                            "test/data/FullHubList_safeVersion.xml"))));

        } else {
            writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(new File(
                            "test/data/FullHubList.xml"))));

        }

        try {
            String line = reader.readLine();
            while (line != null) {
                writer.write(line.trim() + "\n");
                line = reader.readLine();
            }

        } finally {
            try {
                if (reader != null)
                    reader.close();

            } finally {
                if (writer != null)
                    writer.close();

            }

        }

    }

    private boolean readFileHeader(InputStream bis) throws IOException {
        int b = bis.read();

        if (b != 'B') {
            return false;

        }

        b = bis.read();

        if (b != 'Z') {
            return false;

        }

        return true;

    }

}
