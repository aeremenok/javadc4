/*
 * Copyright 2005 by Timo Westkämper
 * 
 * $Id$
 *
 * $Revision$
 */

package net.sf.javadc.gui.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

public class DateRendererTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(DateRendererTest.class);
    }

    public void testCreation() {
        new DateCellRenderer(new SimpleDateFormat("MM/dd/yyyy"));
    }

    public void testGetTableCellRendererComponent() {
        DateCellRenderer d = new DateCellRenderer(new SimpleDateFormat(
                "MM/dd/yyyy"));
        d.getTableCellRendererComponent(null, new Date(), false, false, 0, 0);
    }

}
