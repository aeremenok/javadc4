/*
 * Copyright 2005 by Timo Westkämper
 * 
 * $Id$
 *
 * $Revision$
 */
package net.sf.javadc.gui.util;

import junit.framework.TestCase;

public class ResumeAsFileChooserTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ResumeAsFileChooserTest.class);
    }

    public void testCreation() {
        ResumeAsFileChooser r = new ResumeAsFileChooser("/tmp");
        // r.setupFilters(new File("/tmp/test.gif"));

    }

}
