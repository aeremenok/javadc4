/*
 * Created on 4.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.tasks;

import junit.framework.TestCase;
import net.sf.javadc.config.ConstantSettings;

/**
 * @author Timo Westk�mper
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HubTaskFactoryTest extends TestCase {

    /**
     * Constructor for HubTaskFactoryTest.
     * 
     * @param arg0
     */
    public HubTaskFactoryTest(String arg0) {
        super(arg0);
    }

    public void testClassCreation() throws Exception {
        Class cl = null;

        try {
            cl = Class.forName(ConstantSettings.HUBTASKFACTORY_PREFIX + "ILock"
                    + ConstantSettings.HUBTASKFACTORY_POSTFIX);

        } catch (ClassNotFoundException e) {

            System.out.println(e.toString());
        }

        assertTrue(cl != null);
    }

    public void testBorrowingAndReturning() {

        // TODO
    }

}