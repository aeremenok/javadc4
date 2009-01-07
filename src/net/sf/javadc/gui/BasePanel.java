/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

// $Id: BasePanel.java,v 1.15 2006/06/02 07:46:41 timowest Exp $
package net.sf.javadc.gui;

import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import net.sf.javadc.util.FileUtils;

/**
 * <CODE>BasePanel</CODE> is a utility super class which provides application specific icons via a member method.
 * 
 * @author Timo Westk�mper
 */
class BasePanel
    extends JPanel
{
    /**
     * 
     */
    private static final long     serialVersionUID = -1225195648999091041L;

    // images used to symbolize different connection types
    private static final String[] imageNames       =
                                                       { "images/32/modem.png", "images/32/Satellite.png",
                    "images/32/dsl.png", "images/32/Cable.png", "images/32/lan.png", "images/32/server.png",
                    "images/32/Fireball.png", "images/32/op.png" };

    /**
     * 
     */
    private final int             iconsize         = 16;

    /**
     * Create a BasePanel instance
     */
    public BasePanel()
    {
        super();

    }

    /**
     * Create a BasePanel instance which uses the given LayoutManager
     * 
     * @param layout
     */
    public BasePanel(
        LayoutManager layout )
    {
        super( layout );

    }

    /** ********************************************************************** */

    /**
     * Get the image with the given part in the given form
     * 
     * @param path relative file path of the image to be returned
     * @param shaded whether the image is to be shaded or not
     * @return
     */
    private final ImageIcon getImage(
        String path,
        boolean shaded )
    {
        ImageIcon icon = FileUtils.loadIcon( path );

        if ( icon == null )
        {
            throw new NullPointerException( "Could not load image for " + path );
        }

        Image image = icon.getImage().getScaledInstance( iconsize, iconsize, Image.SCALE_SMOOTH );

        icon.setImage( image );

        return icon;

    }

    /**
     * Get the user icons as an array of <CODE>ImageIcons</CODE>
     * 
     * @return
     */
    protected final ImageIcon[] getUserIcons()
    {
        ImageIcon[] buffer = new ImageIcon[16];

        // puts normal and shaded versions of the images into the buffer
        for ( int i = 0; i < buffer.length; i++ )
        {
            buffer[i] = getImage( imageNames[i % 8], (i > 7) );

        }

        return buffer;

    }

}

/*******************************************************************************
 * $Log: BasePanel.java,v $ Revision 1.15 2006/06/02 07:46:41 timowest removed obsolete test suites Revision 1.14
 * 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.13 2005/09/30 15:59:53 timowest updated sources and
 * tests Revision 1.12 2005/09/25 16:40:58 timowest updated sources and tests Revision 1.11 2005/09/15 17:32:29 timowest
 * added null checks Revision 1.10 2005/09/14 07:11:49 timowest updated sources
 */
