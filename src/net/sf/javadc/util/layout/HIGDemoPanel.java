/*
 * HIGDemoPanel.java
 * Copyright (C) 1999 Daniel Michalik
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your option
 * ) any later version. 
 * 
 * This library is distributed in the hope that it will be useful,but WITHOUT ANY 
 * WARRANTY;  without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along 
 * with this library; if not, write to the Free Software Foundation, Inc., 59 
 * Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */

package net.sf.javadc.util.layout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 * Extended Swing's JPanel with added paint functionality. Draws design grid of
 * the panel.
 * 
 * @version 1.0 1/19/2002
 * @author Daniel Michalik (dmi@autel.cz)
 */
public class HIGDemoPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 48742214018117863L;

    public void paint(Graphics g) {
        super.paint(g);
        HIGLayout l;
        try {
            l = (HIGLayout) getLayout();
        } catch (ClassCastException ex) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        Insets insets = getInsets();
        int x[] = l.getColumnsX(width, insets);
        int y[] = l.getRowsY(height, insets);

        g.setColor(Color.gray);
        for (int i = 1; i < x.length; i++)
            g.drawLine(x[i], 0, x[i], height - 1);
        for (int i = 1; i < y.length; i++)
            g.drawLine(0, y[i], width - 1, y[i]);
    }
}