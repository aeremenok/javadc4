/*
 * Created on 6.4.2005
 */

package net.sf.javadc.config;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.ISettings;

/**
 * @author Timo Westk�mper
 */
public class SettingsFactoryTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(SettingsFactoryTest.class);
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
     * Constructor for SettingsFactoryTest.
     * 
     * @param arg0
     */
    public SettingsFactoryTest(String arg0) {
        super(arg0);
    }

    public void testCreation() {

        ISettings settings = new SettingsFactory().createDefaultSettings();

        // userInfo
        assertTrue(settings.getUserInfo() != null);

        // advancedSettings
        assertTrue(settings.getAdvancedSettings() != null);

        // guiSettings
        assertTrue(settings.getGuiSettings() != null);
    }

}
