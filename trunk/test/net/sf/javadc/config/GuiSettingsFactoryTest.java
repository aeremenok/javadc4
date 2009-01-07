/*
 * Created on 12.11.2004
 */

package net.sf.javadc.config;

import junit.framework.TestCase;

/**
 * @author Timo Westk�mper
 */
public class GuiSettingsFactoryTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for GuiSettingsFactoryTest.
     * 
     * @param arg0
     */
    public GuiSettingsFactoryTest(String arg0) {
        super(arg0);
    }

    public void testCreation() throws Exception {
        GuiSettings guiSettings = GuiSettingsFactory
                .createGuiSettings(GuiSettingsFactory.DEFAULT);

        assertTrue(guiSettings != null);

        guiSettings = GuiSettingsFactory.createGuiSettings(-1);

        assertTrue(guiSettings == null);
    }

}