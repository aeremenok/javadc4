/*
 * Created on 13.11.2004
 */
package net.sf.javadc.themes;

import junit.framework.TestCase;
import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.ISettings;

/**
 * @author Timo Westk�mper
 */
public class ThemeManagerTest extends TestCase {

    private ISettings settings = new Settings();

    private ThemeManager themeManager = new ThemeManager(settings);

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Constructor for ThemeManagerTest.
     * 
     * @param arg0
     */
    public ThemeManagerTest(String arg0) {
        super(arg0);
    }

    public void testInitialization() throws Exception {
        themeManager.installLookAndFeels();

        themeManager.initialize();

        themeManager.setUIDefaults();

        themeManager.getInstalledLookAndFeels();

        themeManager.getThemes();

        themeManager.getThemeNames();

        themeManager.getLafsNames();

    }

}
