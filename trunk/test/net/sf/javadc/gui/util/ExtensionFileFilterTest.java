/*
 * Copyright 2005 by Timo Westkämper
 * 
 * $Id$
 *
 * $Revision$
 */
package net.sf.javadc.gui.util;

import java.io.File;

import junit.framework.TestCase;

public class ExtensionFileFilterTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ExtensionFileFilterTest.class);
    }

    public void testCreation() {
        ExtensionFileFilter eff = new ExtensionFileFilter("gif");
        assertTrue(eff.accept(new File("test.gif")));
        assertFalse(eff.accept(new File("test.gif.test")));
    }

}
