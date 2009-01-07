/*
 * Created on 5.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.config;

import net.sf.javadc.interfaces.ISettings;

/**
 * @author Timo Westk�mper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SettingsTest extends AbstractSettingsTest {

    private ISettings settings = new Settings();

    /**
     * Constructor for SettingsTest.
     * 
     * @param arg0
     */
    public SettingsTest(String arg0) {
        super(arg0);

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.AbstractSettingsTest#getSettings()
     */
    protected ISettings getSettings() {
        return settings;
    }

}