/*
 * Created on 12.11.2004
 */

package net.sf.javadc.listeners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import net.sf.javadc.gui.model.FileListModel;
import net.sf.javadc.interfaces.IClientManager;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IConnectionManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubList;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.client.Client;

import org.apache.log4j.Category;

/**
 * @author Timo Westk�mper
 */
public class ListenerInterfacesMatchClassesTest extends TestCase {

    private static final Category logger = Category
            .getInstance(ListenerInterfacesMatchClassesTest.class);

    // listener classes
    private Class[] listeners = { ClientListener.class,
            ClientManagerListener.class, ConnectionListener.class,
            ConnectionManagerListener.class,

            DownloadRequestListener.class, FileListModelListener.class,
            HubListener.class, HubListListener.class, HubManagerListener.class,
            RequestsModelListener.class, SettingsListener.class,
            ShareManagerListener.class };

    // observables
    private Class[] observables = { Client.class, IClientManager.class,
            IConnection.class, IConnectionManager.class,

            DownloadRequest.class, FileListModel.class, IHub.class,
            IHubList.class, IHubManager.class, IRequestsModel.class,
            ISettings.class, IShareManager.class };

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

    }

    /**
     * Constructor for ListenerInterfacesMatchClasses.
     * 
     * @param arg0
     */
    public ListenerInterfacesMatchClassesTest(String arg0) {
        super(arg0);
    }

    /**
     * Ensures that for each method in each class of the listeners array there
     * is an equivalent method in the equivalent class of the observables array
     * 
     * @throws Exception
     */
    public void testMatches() throws Exception {

        List errors = new ArrayList();

        // ensure that listeners and observables have same length
        assertEquals(listeners.length, observables.length);

        // iterate through all listener classes
        for (int i = 0; i < listeners.length; i++) {
            Class cl = listeners[i];

            logger.debug("Iterating through all methods of " + cl.getName());

            System.out
                    .println("-----------------------------------------------------\n");

            Method[] methods = cl.getMethods();

            // iterate through all methods of a listener class instance
            for (int j = 0; j < methods.length; j++) {
                String name = methods[j].getName();

                StringBuffer debug = new StringBuffer();

                debug
                        .append("Ensuring that an equivalent method is available for method ");
                debug.append(cl.getName()).append(".").append(name).append(
                        " in ");
                debug.append(observables[i].getName());

                logger.debug(debug.toString());

                System.out
                        .println("-----------------------------------------------------\n");

                boolean equivalent = false;

                Method[] obsMethods = observables[i].getMethods();

                logger.debug("Iterating through all methods of "
                        + observables[i].getName());

                // iterate through all methods of an observable class instance

                for (int k = 0; k < obsMethods.length; k++) {
                    String pattern = "fire"
                            + name.substring(0, 1).toUpperCase()
                            + name.substring(1);

                    if (obsMethods[k].getName().equals(pattern)) {
                        logger.debug(obsMethods[k].getName()
                                + " matched the pattern " + pattern);

                        equivalent = true;

                    } else {
                        logger.debug(obsMethods[k].getName()
                                + " didn't match the pattern " + pattern);

                    }

                }// for

                System.out.println();

                // add an error to the list, if an equivalent method could not
                // be found
                if (!equivalent) {
                    debug = new StringBuffer();

                    debug.append("No equivalent method could be found for ");
                    debug.append(cl.getName()).append(".").append(name).append(
                            " in ");
                    debug.append(observables[i].getName());

                    errors.add(debug.toString());
                }

            }// for

            System.out.println();

        }// for

        System.out.println();

        for (Iterator i = errors.iterator(); i.hasNext();) {
            logger.error((String) i.next());

        }

        assertTrue(errors.isEmpty());

    }

}