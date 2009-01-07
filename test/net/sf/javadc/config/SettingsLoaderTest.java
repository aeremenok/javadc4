/*
 * Created on 12.11.2004
 */
package net.sf.javadc.config;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ISettingsLoader;

/**
 * @author Timo Westk�mper
 */
public class SettingsLoaderTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testLoad() throws Exception {
        ISettingsLoader loader = new SettingsLoader();

        ISettings settings = loader.load();

        assertTrue(settings != null);
    }

}
