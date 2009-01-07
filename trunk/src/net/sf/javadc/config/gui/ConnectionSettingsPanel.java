/*
 * Copyright (C) 2001 Michael Kurz mkurz@epost.de Copyright (C) 2004 Timo Westkämper This program is free software; you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: ConnectionSettingsPanel.java,v 1.16 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.config.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.util.layout.HIGConstraints;
import net.sf.javadc.util.layout.HIGLayout;

import org.apache.log4j.Category;

/**
 * <CODE>ConnectionSettingsPanel</CODE> is a panel to edit connection related settings
 * 
 * @author Timo Westk�mper
 */
public class ConnectionSettingsPanel
    extends AbstractSettingsPanel
{
    /**
     * 
     */
    private static final long     serialVersionUID = -2854836248361391163L;

    private final static Category logger           = Category.getInstance( ConnectionSettingsPanel.class );

    /**
     * 
     */
    private final JRadioButton    radioActive      = new JRadioButton( "Active Mode" );

    /**
     * 
     */
    private final JRadioButton    radioPassive     = new JRadioButton( "Passive Mode" );

    /**
     * 
     */
    private final JTextField      txtForceIP       = new JTextField();

    /**
     * 
     */
    private final JTextField      txtForcePort     = new JTextField();

    /**
     * 
     */
    private final JTextField      txtUploadSlots   = new JTextField();

    /**
     * 
     */
    private final JTextField      txtDownloadSlots = new JTextField();

    // components
    private final ISettings       settings;

    /**
     * Create a ConnectionSettingsPanel instance with the given ISettings component
     * 
     * @param _settings ISettings component to be used
     */
    public ConnectionSettingsPanel(
        ISettings _settings )
    {
        if ( _settings == null )
        {
            throw new NullPointerException( "settings was null." );
        }

        settings = _settings;

        try
        {
            jbInit();

        }
        catch ( Exception e )
        {
            String error = "Caught " + e.getClass().getName() + " when trying to initialize ConnectionSettingsPanel.";
            logger.error( error, e );

            throw new RuntimeException( error, e );
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.gui.AbstractSettingsPanel#initPanel()
     */
    @Override
    public final void initPanel()
    {
        setChanged( false );

        // TODO : better Exception handling

        try
        {
            txtForceIP.setText( settings.getIP() );

        }
        catch ( NullPointerException e )
        {
            // logger.error(e);
            logger.error( "Caught " + e.getClass().getName(), e );

        }

        try
        {
            txtForcePort.setText( new Integer( settings.getUserInfo().getPort() ).toString() );

        }
        catch ( NullPointerException e )
        {
            // logger.error(e);
            logger.error( "Caught " + e.getClass().getName(), e );
        }

        try
        {
            txtUploadSlots.setText( new Integer( settings.getUploadSlots() ).toString() );

        }
        catch ( NullPointerException e )
        {
            // logger.error(e);
            logger.error( "Caught " + e.getClass().getName(), e );
        }

        try
        {
            txtDownloadSlots.setText( new Integer( settings.getDownloadSlots() ).toString() );

        }
        catch ( NullPointerException e )
        {
            // logger.error(e);
            logger.error( "Caught " + e.getClass().getName(), e );
        }

        if ( settings.isActive() )
        {
            radioActive.setSelected( true );

        }
        else
        {
            radioPassive.setSelected( true );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.gui.AbstractSettingsPanel#onCancel()
     */
    @Override
    public final void onCancel()
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.gui.AbstractSettingsPanel#onOK()
     */
    @Override
    public final void onOK()
    {
        if ( isChanged() )
        {
            settings.setIP( txtForceIP.getText() );

            settings.getUserInfo().setPort( Integer.parseInt( txtForcePort.getText() ) );

            settings.setActive( radioActive.isSelected() );

            settings.setUploadSlots( new Integer( txtUploadSlots.getText() ).intValue() );

            settings.setDownloadSlots( new Integer( txtDownloadSlots.getText() ).intValue() );

        }

    }

    /**
     * Enable the force ip and force port text elements, if the Client uses active connection
     * 
     * @param b true if the Client uses active connections and false, if the Client uses passive connections
     */
    private final void enableActiveOptions(
        boolean b )
    {
        txtForceIP.setEnabled( b );
        txtForcePort.setEnabled( b );

    }

    /**
     * Create a JPanel instance for the active settings
     * 
     * @return
     */
    private final JPanel getActiveSettingsPanel()
    {
        final JPanel p1 = new JPanel();

        p1.setBorder( new TitledBorder( BorderFactory.createEtchedBorder( Color.white, new Color( 142, 142, 142 ) ),
            "Active Settings" ) );

        final int[] w1 = { 10, 70, 10, 150, 10 };
        final int[] h1 = { 21, 21, 21, 21, 21 };

        HIGLayout l1 = new HIGLayout( w1, h1 );
        HIGConstraints c = new HIGConstraints();

        l1.setColumnWeight( 4, 1 );
        p1.setLayout( l1 );

        // IP
        p1.add( new JLabel( "Force IP" ), c.rcwh( 1, 2, 2, 1 ) );
        p1.add( txtForceIP, c.rc( 2, 4 ) );

        txtForceIP.addActionListener( this.stateChangedListener );

        // Port
        p1.add( new JLabel( "Force Port" ), c.rcwh( 3, 2, 2, 1 ) );
        p1.add( txtForcePort, c.rc( 4, 4 ) );

        txtForcePort.addActionListener( this.stateChangedListener );

        return p1;

    }

    /**
     * Create a JPanel instance with the connection mode settings
     * 
     * @return
     */
    private final JPanel getConnectionModePanel()
    {
        final JPanel p1 = new JPanel();

        p1.setLayout( new FlowLayout( FlowLayout.LEFT ) );

        p1.setBorder( new TitledBorder( BorderFactory.createEtchedBorder( Color.white, new Color( 142, 142, 142 ) ),
            "Connection Mode" ) );

        radioActive.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {
                enableActiveOptions( radioActive.isSelected() );

            }

        } );

        radioPassive.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {
                enableActiveOptions( radioActive.isSelected() );

            }

        } );

        radioActive.addActionListener( this.stateChangedListener );

        radioPassive.addActionListener( this.stateChangedListener );

        // Group the radio buttons.
        ButtonGroup group = new ButtonGroup();

        group.add( radioActive );
        group.add( radioPassive );

        p1.add( radioActive, null );
        p1.add( radioPassive, null );

        return p1;

    }

    /**
     * Create a JPanel instance for the slots settings
     * 
     * @return
     */
    private final JPanel getSlotsPanel()
    {
        JPanel p1 = new JPanel();

        p1.setBorder( new TitledBorder( BorderFactory.createEtchedBorder( Color.white, new Color( 142, 142, 142 ) ),
            "Slots" ) );

        int[] w1 = { 10, 70, 10, 150, 10 };
        int[] h1 = { 21, 21, 21, 21, 10 };

        HIGLayout l1 = new HIGLayout( w1, h1 );
        HIGConstraints c = new HIGConstraints();

        l1.setColumnWeight( 4, 1 );
        p1.setLayout( l1 );

        // Upload Slots
        p1.add( new JLabel( "Upload Slots" ), c.rcwh( 1, 2, 2, 1 ) );
        p1.add( txtUploadSlots, c.rc( 2, 4 ) );

        txtUploadSlots.addActionListener( this.stateChangedListener );

        // Download Slots
        p1.add( new JLabel( "Download Slots" ), c.rcwh( 3, 2, 2, 1 ) );
        p1.add( txtDownloadSlots, c.rc( 4, 4 ) );

        txtDownloadSlots.addActionListener( this.stateChangedListener );

        return p1;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.gui.AbstractSettingsPanel#jbInit()
     */
    @Override
    protected final void jbInit()
        throws Exception
    {
        this.setLayout( new BorderLayout() );
        this.add( getConnectionModePanel(), BorderLayout.NORTH );

        JPanel p1 = new JPanel();

        p1.setLayout( new BoxLayout( p1, BoxLayout.Y_AXIS ) );

        /* p1.add(getConnectionModePanel()); */
        p1.add( getActiveSettingsPanel() );
        p1.add( getSlotsPanel() );

        JPanel p2 = new JPanel( new BorderLayout() );

        p2.add( p1, BorderLayout.NORTH );

        this.add( p2, BorderLayout.CENTER );

    }

}

/*******************************************************************************
 * $Log: ConnectionSettingsPanel.java,v $ Revision 1.16 2005/10/02 11:42:28 timowest updated sources and tests Revision
 * 1.15 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.14 2005/09/25 16:40:58 timowest updated
 * sources and tests Revision 1.13 2005/09/14 07:11:49 timowest updated sources
 */
