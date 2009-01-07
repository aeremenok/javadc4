/*
 * Created on 12.11.2004
 */
package net.sf.javadc.config;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper
 */
public class GuiSettingsTest extends TestCase {

    // private GuiSettings guiSettings;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for GuiSettingsTest.
     * 
     * @param arg0
     */
    public GuiSettingsTest(String arg0) {
        super(arg0);
    }

    public void testCreation() throws Exception {
        new GuiSettings();
    }

}
