/*
 * Created on 15.2.2005
 */
package net.sf.javadc.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Timo Westk�mper
 */
public class TestFileHelper {

    /**
     * @param file
     * @param b
     */
    public static void writeIntoFile(File file, int b) {
        OutputStream outStream = null;

        try {
            outStream = new FileOutputStream(file);

        } catch (FileNotFoundException f) {
            throw new RuntimeException(f);

        }

        byte[] bytes = new byte[b];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = 127;
        }

        try {
            outStream.write(bytes);
            // outStream.flush();
            outStream.close();

        } catch (IOException io) {
            throw new RuntimeException(io);

        }
    }

}
