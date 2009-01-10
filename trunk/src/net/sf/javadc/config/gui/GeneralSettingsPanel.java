/*
 * Copyright (C) 2001 Michael Kurz mkurz@epost.de Copyright (C) 2004 Timo Westkämper This program is free software; you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: GeneralSettingsPanel.java,v 1.17 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.config.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import junit.framework.Assert;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IUserInfo;
import net.sf.javadc.util.layout.HIGConstraints;
import net.sf.javadc.util.layout.HIGLayout;

/**
 * <CODE>GeneralSettingsPanel</CODE> is a panel to edit general application settings
 * 
 * @author Timo Westk�mper
 */
public class GeneralSettingsPanel
    extends AbstractSettingsPanel
{
    private static final long serialVersionUID = 8431748853711086062L;

    private final String[]    speeds           = { "56Kbps", "Satellite", "DSL", "Cable", "LAN(T1)", "LAN(T3)" };

    private final JComboBox   comboConnection  = new JComboBox( speeds );
    private final JTextField  txtDescription   = new JTextField();
    private final JTextField  txtEmail         = new JTextField();
    private final JTextField  txtNick          = new JTextField();
    private final JTextField  txtTag           = new JTextField();
    private final JTextField  txtHubList       = new JTextField();
    private final JCheckBox   checkForceMove   = new JCheckBox( "Auto-Forward" );

    private final ISettings   settings;

    /**
     * Create a GeneralSettingsPanel instance with the given ISettings instance
     * 
     * @param _settings ISettings instance to be used
     */
    public GeneralSettingsPanel(
        ISettings _settings )
    {
        Assert.assertNotNull( _settings );

        settings = _settings;

        try
        {
            initComponents();
        }
        catch ( Exception ex )
        {
            String error = "cannot init panel";
            logger.error( error, ex );
            throw new RuntimeException( error, ex );
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
        IUserInfo lUser = settings.getUserInfo();

        txtNick.setText( lUser.getNick() );
        txtEmail.setText( lUser.getEmail() );

        txtTag.setText( lUser.getTag() );

        try
        {
            txtDescription.setText( lUser.getDescription() );
        }
        catch ( NullPointerException e )
        {
            // logger.error(e);
            logger.error( "Caught " + e.getClass().getName(), e );
        }

        try
        {
            txtHubList.setText( settings.getAdvancedSettings().getHublistAddress() );

        }
        catch ( NullPointerException e )
        {
            // logger.error(e);
            logger.error( "Caught " + e.getClass().getName(), e );
        }

        try
        {
            checkForceMove.setSelected( settings.getAdvancedSettings().isForceMove() );

        }
        catch ( NullPointerException e )
        {
            // logger.error(e);
            logger.error( "Caught " + e.getClass().getName(), e );
        }

        String strSpeed = lUser.getSpeed();

        for ( int i = 0; i < speeds.length; i++ )
        {
            if ( strSpeed.equalsIgnoreCase( speeds[i] ) )
            {
                comboConnection.setSelectedIndex( i );

            }

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
        IUserInfo lUser = settings.getUserInfo();

        lUser.setNick( txtNick.getText() );

        lUser.setEmail( txtEmail.getText() );

        lUser.setDescription( txtDescription.getText() );

        lUser.setTag( txtTag.getText() );

        lUser.setSpeed( speeds[comboConnection.getSelectedIndex()] );

        settings.getAdvancedSettings().setHublistAddress( txtHubList.getText() );

        settings.getAdvancedSettings().setForceMove( checkForceMove.isSelected() );

    }

    /**
     * Create a JPanel instance for various other settings
     * 
     * @return
     */
    private JPanel getOtherSettingsPanel()
    {
        final JPanel p1 = new JPanel();

        p1.setBorder( new TitledBorder( BorderFactory.createEtchedBorder( Color.white, new Color( 148, 145, 140 ) ),
            "Other Settings" ) );

        final int[] w1 = { 10, 70, 10, 150, 10 };
        final int[] h1 = { 21, 21, 21, 21, 10 };

        final HIGLayout l1 = new HIGLayout( w1, h1 );
        final HIGConstraints c = new HIGConstraints();

        l1.setColumnWeight( 4, 1 );
        p1.setLayout( l1 );

        // HubList
        p1.add( new JLabel( "Hub List Address" ), c.rcwh( 1, 2, 3, 1 ) );
        p1.add( txtHubList, c.rc( 2, 4 ) );

        // Force Move
        p1.add( new JLabel( "Force Move" ), c.rcwh( 3, 2, 2, 1 ) );
        p1.add( checkForceMove, c.rc( 4, 4 ) );

        checkForceMove.addActionListener( this.stateChangedListener );

        return p1;

    }

    /**
     * Create a JPanel instance for user settings
     * 
     * @return
     */
    private final JPanel getUserSettingsPanel()
    {
        final JPanel p1 = new JPanel();

        p1.setBorder( new TitledBorder( BorderFactory.createEtchedBorder( Color.white, new Color( 148, 145, 140 ) ),
            "User Settings" ) );

        final int[] w1 = { 10, 70, 10, 150, 10 };
        final int[] h1 = { 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 10 };

        final HIGLayout l1 = new HIGLayout( w1, h1 );
        final HIGConstraints c = new HIGConstraints();

        l1.setColumnWeight( 4, 1 );
        p1.setLayout( l1 );

        // Nick
        p1.add( new JLabel( "Nick" ), c.rcwh( 1, 2, 2, 1 ) );
        p1.add( txtNick, c.rc( 2, 4 ) );

        // Description
        p1.add( new JLabel( "Description" ), c.rcwh( 3, 2, 2, 1 ) );
        p1.add( txtDescription, c.rc( 4, 4 ) );

        // Tag
        p1.add( new JLabel( "Tag" ), c.rcwh( 5, 2, 2, 1 ) );
        p1.add( txtTag, c.rc( 6, 4 ) );

        // Email
        p1.add( new JLabel( "Email" ), c.rcwh( 7, 2, 2, 1 ) );
        p1.add( txtEmail, c.rc( 8, 4 ) );

        // Connection
        p1.add( new JLabel( "Connection" ), c.rcwh( 9, 2, 2, 1 ) );
        p1.add( comboConnection, c.rc( 10, 4 ) );

        return p1;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.config.gui.AbstractSettingsPanel#jbInit()
     */
    @Override
    protected final void initComponents()
        throws Exception
    {
        this.setLayout( new BorderLayout() );

        JPanel p1 = new JPanel();

        p1.setLayout( new BoxLayout( p1, BoxLayout.Y_AXIS ) );

        p1.add( getUserSettingsPanel() );
        p1.add( getOtherSettingsPanel() );

        this.add( p1, BorderLayout.NORTH );
    }
}
