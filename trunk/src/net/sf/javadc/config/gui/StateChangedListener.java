/*
 * Copyright (C) 2001 Michael Kurz mkurz@epost.de Copyright (C) 2004 Timo Westkämper This program is free software; you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: StateChangedListener.java,v 1.12 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.config.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Category;

/**
 * <CODE>StateChangedListener</CODE> is an <CODE>ActionListener</CODE> implementation which flags the changed attribute
 * of the related <CODE>
 * AbstractSettingsPanel</CODE> instance, when changes have occured
 * 
 * @author Timo Westk�mper
 */
public class StateChangedListener
    implements
        ActionListener
{
    private final static Category       logger = Category.getInstance( StateChangedListener.class );

    /**
     * 
     */
    private final AbstractSettingsPanel panel;

    /**
     * Create a StateChangedListener instance
     * 
     * @param panel
     */
    public StateChangedListener(
        AbstractSettingsPanel panel )
    {
        if ( panel == null )
        {
            throw new NullPointerException( "panel was null" );
        }

        this.panel = panel;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public final void actionPerformed(
        ActionEvent e )
    {
        logger.debug( "Settings changed to true for instance of " + panel.getClass() );
        this.panel.setChanged( true );

    }

    /**
     * Get the Panel this StateChangedListener instance is related to
     * 
     * @return
     */
    public final AbstractSettingsPanel getPanel()
    {
        return panel;

    }

}

/*******************************************************************************
 * $Log: StateChangedListener.java,v $ Revision 1.12 2005/10/02 11:42:28 timowest updated sources and tests Revision
 * 1.11 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.10 2005/09/25 16:40:58 timowest updated
 * sources and tests Revision 1.9 2005/09/14 07:11:49 timowest updated sources
 */
