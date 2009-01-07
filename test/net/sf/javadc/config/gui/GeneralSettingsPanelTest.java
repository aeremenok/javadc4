/*
 * Created on 27.11.2004
 */

package net.sf.javadc.config.gui;

import junit.framework.TestCase;
import net.sf.javadc.config.SettingsAdapter;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseSettingsLoader;

/**
 * @author Timo Westk�mper
 */
public class GeneralSettingsPanelTest extends TestCase {

    // private ISettings settings = new Settings();
    private ISettings settings = new SettingsAdapter(new BaseSettingsLoader());

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for GeneralSettingsPanelTest.
     * 
     * @param arg0
     */
    public GeneralSettingsPanelTest(String arg0) {
        super(arg0);
    }

    public void testCreation() throws Exception {

        GeneralSettingsPanel panel = new GeneralSettingsPanel(settings);

        assertTrue(panel != null);

    }

    // TODO : create more tests

}