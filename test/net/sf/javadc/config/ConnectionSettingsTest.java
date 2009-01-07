/*
 * Created on 4.6.2005
 */
package net.sf.javadc.config;

import junit.framework.TestCase;

/**
 * @author Timo Westkämper
 */
public class ConnectionSettingsTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ConnectionSettingsTest.class);
    }

    /**
     * Constructor for ConnectionSettingsTest.
     * 
     * @param arg0
     */
    public ConnectionSettingsTest(String arg0) {
        super(arg0);
    }

    public void testPropertiesCanBeLoaded() {

        System.out.println(ConnectionSettings.DOWNLOAD_SEGMENT_SIZE);
    }

}
