/*
 * Copyright (C) 2001 Ryan Sweny, cabinboy@0wned.org Copyright (C) 2004 Timo Westkämper This program is free software;
 * you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: SettingsDialog.java,v 1.16 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.config.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import junit.framework.Assert;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;

import org.apache.log4j.Category;

/**
 * The main dialog for editing the application settings of javadc3
 * 
 * @author Timo Westk�mper
 */
public class SettingsDialog
    extends JDialog
{
    private static final long              serialVersionUID = 5170053346711095610L;

    private final static Category          logger           = Category.getInstance( SettingsDialog.class );

    private final Panel                    buttonPanel      = new Panel( new FlowLayout( FlowLayout.RIGHT ) );
    private final JButton                  okButton         = new JButton( "OK" );
    private final JButton                  cancelButton     = new JButton( "Cancel" );
    private final JButton                  applyButton      = new JButton( "Apply" );

    // private boolean doRefresh = true;
    // private final FlowLayout flowLayout1 = new FlowLayout();

    // main panels
    private final GeneralSettingsPanel     generalSettingsPanel;
    private final ConnectionSettingsPanel  connectionSettingsPanel;
    private final TransferSettingsPanel    transferSettingsPanel;
    private final JPanel                   mainPanel        = new JPanel();
    private final JTabbedPane              tabPane          = new JTabbedPane();

    // components
    private final ISettings<EventListener> settings;
    private final IShareManager            shareManager;

    /**
     * Create a SettingsDialog instance with the given container frame, title, modal settings and references to the
     * ISettings and ShareManager components
     * 
     * @param owner container Frame instance
     * @param title Title of the dialog
     * @param modal whether the dialog is modal or not
     * @param _settings ISettings instance to be used
     * @param _shareManager IShareManager to be used
     */
    public SettingsDialog(
        Frame owner,
        String title,
        boolean modal,
        ISettings<EventListener> _settings,
        IShareManager _shareManager )
    {
        super( owner, title, modal );

        Assert.assertNotNull( _shareManager );
        Assert.assertNotNull( _settings );

        settings = _settings;
        shareManager = _shareManager;

        generalSettingsPanel = new GeneralSettingsPanel( settings );
        connectionSettingsPanel = new ConnectionSettingsPanel( settings );
        transferSettingsPanel = new TransferSettingsPanel( settings, shareManager );

        this.setSize( 600, 460 );

        try
        {
            initComponents();
        }
        catch ( Exception e )
        {
            logger.error( "cannot initialize dialog", e );
        }

        initListeners();
    }

    /**
     * Intialize the subpanels and make them visible
     */
    public final void showSettings()
    {
        generalSettingsPanel.initPanel();
        connectionSettingsPanel.initPanel();
        transferSettingsPanel.initPanel();

        setVisible( true );
    }

    /**
     * Intialize the Dialog
     * 
     * @throws Exception
     */
    private final void initComponents()
        throws Exception
    {
        mainPanel.setLayout( new BorderLayout() );

        tabPane.addTab( "General", generalSettingsPanel );
        tabPane.addTab( "Connection", connectionSettingsPanel );
        tabPane.addTab( "Transfers", transferSettingsPanel );

        mainPanel.add( buttonPanel, BorderLayout.SOUTH );

        buttonPanel.add( okButton );
        okButton.setMnemonic( KeyEvent.VK_O );

        buttonPanel.add( cancelButton );
        cancelButton.setMnemonic( KeyEvent.VK_C );

        buttonPanel.add( applyButton );
        applyButton.setMnemonic( KeyEvent.VK_A );

        tabPane.setSize( 400, 300 );
        buttonPanel.setSize( 400, 50 );

        // setup tabbed pane
        mainPanel.add( tabPane, BorderLayout.CENTER );
        this.getContentPane().add( mainPanel, null );

        mainPanel.setPreferredSize( new Dimension( 400, 350 ) );
        this.setContentPane( mainPanel );
    }

    private void initListeners()
    {
        okButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                onOK();
                setVisible( false );
            }
        } );

        cancelButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                setVisible( false );
            }
        } );

        applyButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                onOK();
            }
        } );
    }

    /**
     * Action Handler invoked when OK is pressed
     */
    private final void onOK()
    {
        generalSettingsPanel.onOK();
        connectionSettingsPanel.onOK();
        transferSettingsPanel.onOK();
    }
}
