/*
 * Copyright (C) 2001 Michael Kurz mkurz@epost.de Copyright (C) 2004 Timo Westkämper This program is free software; you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: AbstractSettingsPanel.java,v 1.13 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.config.gui;

import javax.swing.JPanel;

import org.apache.log4j.Category;

/**
 * <CODE>AbstractSettingsPanel</CODE> is an abstract super class for <CODE>
 * JPanel</CODE> instances in the
 * <CODE>SettingsDialog</CODE>
 * 
 * @author Timo Westk�mper
 */
public abstract class AbstractSettingsPanel
    extends JPanel
{
    protected final Category             logger               = Category.getInstance( getClass() );
    private boolean                      changed;
    protected final StateChangedListener stateChangedListener = new StateChangedListener( this );

    /**
     * Create an AbstractSettingsPanel instance
     */
    public AbstractSettingsPanel()
    {
        super();
    }

    /**
     * Return whether the settings displayed in the Panel have changed
     * 
     * @return true, if they have changed and false, if not
     */
    public boolean isChanged()
    {
        return changed;
    }

    /**
     * Set whether the settings displayed in the Panel have changed
     * 
     * @param b
     */
    public void setChanged(
        boolean b )
    {
        changed = b;
    }

    /**
     * Initialize the subpanels
     * 
     * @throws Exception
     */
    protected abstract void initComponents()
        throws Exception;

    /**
     * Loads the values and initializes them if needed.
     */
    protected abstract void initPanel();

    /**
     * Action Handler invoked when Cancel is pressed
     */
    protected abstract void onCancel();

    /**
     * Action Handler invoked when OK is pressed
     */
    protected abstract void onOK();
}
