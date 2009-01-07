/*
 * Created on 7.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.tasks.client;

import net.sf.javadc.net.client.ConnectionState;
import net.sf.javadc.tasks.BaseClientTask;

import org.apache.log4j.Category;

/**
 * @author Timo Westkämper To change the template for this generated type
 *         comment go to Window&gt;Preferences&gt;Java&gt;Code
 *         Generation&gt;Code and Comments
 */
public class SDownloadFinishedTask extends BaseClientTask {

    private static final Category logger = Category
            .getInstance(SDownloadFinishedTask.class);

    // private final ISettings settings;

    // helpers
    // private final ConnectionSlotReservation slotReservation;

    /**
     * Create a <code>SDownloadFinishedTask</code> instance
     */
    public SDownloadFinishedTask() {

        // if (_settings == null)
        // throw new NullPointerException("_settings was null.");
        //        
        // settings = _settings;
    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.ClientBaseTask#runTaskTemplate()
     */
    protected final void runTaskTemplate() {
        clientConnection.closeFile();

        logger.info("Download for " + clientConnection.getDownloadRequest()
                + " finished.");

        clientConnection.fireDownloadComplete();

        clientConnection.setState(ConnectionState.COMMAND_DOWNLOAD);

    }

}