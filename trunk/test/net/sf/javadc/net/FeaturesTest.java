/*
 * Copyright 2005 by Timo Westkämper
 * 
 * $Id$
 *
 * $Revision$
 */

package net.sf.javadc.net;

import junit.framework.TestCase;

public class FeaturesTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(FeaturesTest.class);
    }

    public void testAccess() {
        Features.getFeatures();
        assertEquals(Features.getFeatureSize(), Features.getSupportCommands()
                .size());

    }

}
