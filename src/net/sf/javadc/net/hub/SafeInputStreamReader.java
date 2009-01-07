/*
 * Created on 12.6.2005
 */
package net.sf.javadc.net.hub;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * @author Timo Westkämper
 */
public class SafeInputStreamReader extends InputStreamReader {

    private final static Category logger = Logger
            .getLogger(SafeInputStreamReader.class);

    /**
     * Create a new SafeInputStreamReader instance
     * 
     * @param arg0
     */
    public SafeInputStreamReader(InputStream arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    private void error(Exception e) {
        logger.error("Caught " + e.getClass().getName(), e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.Reader#read()
     */
    public int read() throws IOException {

        try {
            return super.read();
        } catch (Exception e) {
            logger.error(e);
        }

        // The character read, as an integer in the range 0 to 65535
        // (0x00-0xffff), or -1 if the end of the stream has been reached
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.Reader#read(char[], int, int)
     */
    public int read(char[] cbuf, int offset, int length) throws IOException {

        try {
            return super.read(cbuf, offset, length);
        } catch (Exception e) {
            error(e);
        }

        // The number of characters read, or -1 if the end of the stream has
        // been reached
        return length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.Reader#read(char[])
     */
    public int read(char[] cbuf) throws IOException {

        try {
            return super.read(cbuf);
        } catch (Exception e) {
            error(e);
        }

        // The number of characters read, or -1 if the end of the stream has
        // been reached

        return cbuf.length;
    }

}

/*******************************************************************************
 * $Log: SafeInputStreamReader.java,v $
 * Revision 1.6  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.5 2005/09/26 17:19:52
 * timowest updated sources and tests
 * 
 * Revision 1.4 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
