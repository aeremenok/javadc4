/*
 * Created on 12.11.2004
 */
package net.sf.javadc.config;

import junit.framework.TestCase;
import net.sf.javadc.interfaces.ISettings;

/**
 * @author Timo Westk�mper
 */
public abstract class AbstractSettingsTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        ISettings settings = getSettings();

        settings.setUploadSlots(10);
        settings.setDownloadSlots(10);

        assertEquals(settings.getFreeDownloadSlotCount(), settings
                .getDownloadSlots());

        assertEquals(settings.getFreeUploadSlotCount(), settings
                .getUploadSlots());
    }

    /**
     * Constructor for AbstractSettingsTest.
     * 
     * @param arg0
     */
    public AbstractSettingsTest(String arg0) {
        super(arg0);
    }

    protected abstract ISettings getSettings();

    public void testReservingDownloadSlots() throws Exception {

        ISettings settings = getSettings();

        // download slots

        for (int i = 0; i < 5; i++) {
            assertTrue(settings.reserveDownloadSlot());
        }

        assertEquals(settings.getFreeDownloadSlotCount(), 5);

        for (int i = 0; i < 5; i++) {
            assertTrue(settings.reserveDownloadSlot());
        }

        assertFalse(settings.reserveDownloadSlot());

        assertEquals(settings.getFreeDownloadSlotCount(), 0);

        for (int i = 0; i < 5; i++) {
            settings.releaseDownloadSlot();
        }

        assertEquals(settings.getFreeDownloadSlotCount(), 5);

    }

    public void testReservingUploadSlots() throws Exception {
        ISettings settings = getSettings();

        // upload slots

        for (int j = 0; j < 5; j++) {
            assertTrue(settings.reserveUploadSlot());
        }

        assertEquals(settings.getFreeUploadSlotCount(), 5);

        for (int i = 0; i < 5; i++) {
            assertTrue(settings.reserveUploadSlot());
        }

        assertFalse(settings.reserveUploadSlot());

        assertEquals(settings.getFreeUploadSlotCount(), 0);

        for (int i = 0; i < 5; i++) {
            settings.releaseUploadSlot();
        }

        assertEquals(settings.getFreeUploadSlotCount(), 5);

    }

}
