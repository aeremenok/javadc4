/*
 * Copyright (C) 2001 Michael Kurz mkurz@epost.de Copyright (C) 2004 Timo Westkämper This program is free software; you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: TransferSettingsPanel.java,v 1.18 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.config.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import junit.framework.Assert;
import net.sf.javadc.gui.model.DirectoryListModel;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.util.layout.HIGConstraints;
import net.sf.javadc.util.layout.HIGLayout;

/**
 * <CODE>TransferSettingsPanel</CODE> is a subpanel of the <code>SettingsDialog</code>. It enables the user to set the
 * share preferences.
 * 
 * @author Michael Kurz
 */
public class TransferSettingsPanel
    extends AbstractSettingsPanel
{
    private static final long              serialVersionUID      = 8818041755542208208L;

    private final JButton                  addShareButton        = new JButton( "Add" );
    private final JButton                  tempDownloadDirButton = new JButton( "Browse" );
    private final JButton                  downloadDirButton     = new JButton();
    private final JTextField               txtDownloadDir        = new JTextField();
    private final JButton                  removeShareButton     = new JButton( "Remove" );
    private final JList                    shareList;                                       // =
    // new
    // JList();

    private final DirectoryListModel       shareModel            = new DirectoryListModel();
    private final JScrollPane              shareScrollPane;                                 // =
    // new
    // JScrollPane(shareList);

    private final JTextField               txtTempDownloadDir    = new JTextField();
    // private final JPanel uploadPanel = new JPanel();

    // components
    private final ISettings<EventListener> settings;
    private final IShareManager            shareManager;

    /**
     * Create a TransferSettingsPanel instance
     * 
     * @param _settings ISettings instance to be used
     * @param _shareManager IShareManager instance to be used
     */
    public TransferSettingsPanel(
        ISettings<EventListener> _settings,
        IShareManager _shareManager )
    {
        Assert.assertNotNull( _shareManager );
        Assert.assertNotNull( _settings );

        settings = _settings;
        shareManager = _shareManager;

        List<String> uploadDirs = settings.getUploadDirs();
        String[] dirs = new String[uploadDirs.size()];
        dirs = uploadDirs.toArray( dirs );

        populateShareList( dirs );

        shareList = new JList( shareModel );
        shareScrollPane = new JScrollPane( shareList );

        try
        {
            initComponents();
        }
        catch ( Exception ex )
        {
            logger.error( "cannot init panel", ex );
            throw new RuntimeException( ex );
        }
    }

    /**
     * Intialize the Panel
     */
    @Override
    public final void initPanel()
    {
        try
        {
            txtDownloadDir.setText( settings.getDownloadDir() );
        }
        catch ( NullPointerException e )
        {
            // logger.error(e);
            logger.error( "Caught " + e.getClass().getName(), e );
        }

        try
        {
            txtTempDownloadDir.setText( settings.getTempDownloadDir() );
        }
        catch ( NullPointerException e )
        {
            // logger.error(e);
            logger.error( "Caught " + e.getClass().getName(), e );

        }

        for ( String element : settings.getUploadDirs() )
        {
            shareModel.addDirectory( element );
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
        settings.setDownloadDir( txtTempDownloadDir.getText() );

        settings.setUploadDirs( shareModel.getDirList() );
        shareManager.update();

        settings.setTempDownloadDir( txtTempDownloadDir.getText() );
        settings.setDownloadDir( txtDownloadDir.getText() );

    }

    /**
     * This method is called when the "browse" button is pressed.
     * 
     * @return Returns the String
     */
    private final String browseDir()
    {
        JFileChooser chooser = new JFileChooser();

        chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

        int returnVal = chooser.showOpenDialog( this );

        if ( returnVal == JFileChooser.APPROVE_OPTION )
        {
            return chooser.getSelectedFile().getPath();
        }

        return null;
    }

    /**
     * Create a JPanel instance with the Download Preferences
     */
    private final JPanel getDownloadPreferences()
    {
        JPanel p1 = new JPanel();

        p1.setBorder( new TitledBorder( BorderFactory.createEtchedBorder( Color.white, new Color( 142, 142, 142 ) ),
            "Download Preferences" ) );

        txtTempDownloadDir.setColumns( 20 );

        int[] w1 = { 10, 70, 10, 100, 10, 70, 10 };
        int[] h1 = { 21, 21, 21, 21, 21, 10 };

        HIGLayout l1 = new HIGLayout( w1, h1 );
        HIGConstraints c = new HIGConstraints();

        l1.setColumnWeight( 4, 1 );
        l1.setRowWeight( 6, 1 );

        p1.setLayout( l1 );

        // Temp Download
        p1.add( new JLabel( "Temporary" ), c.rcwh( 1, 2, 3, 1 ) );
        p1.add( txtTempDownloadDir, c.rcwh( 2, 4, 1, 1 ) );

        tempDownloadDirButton.addMouseListener( new MouseAdapter()
        {
            @Override
            public void mouseClicked(
                MouseEvent e )
            {
                onBrowseDir( true );
            }
        } );

        p1.add( tempDownloadDirButton, c.rcwh( 2, 6, 1, 1 ) );

        // Download
        p1.add( new JLabel( "Downloads" ), c.rcwh( 3, 2, 3, 1 ) );
        p1.add( txtDownloadDir, c.rcwh( 4, 4, 1, 1 ) );

        downloadDirButton.setText( "Browse" );
        downloadDirButton.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed(
                ActionEvent e )
            {
                txtDownloadDir.setText( browseDir() );
            }
        } );

        p1.add( downloadDirButton, c.rcwh( 4, 6, 1, 1 ) );

        return p1;

    }

    /**
     * Create a JPanel instance with the Upload settings
     * 
     * @return
     */
    private final JPanel getUploadPreferences()
    {
        JPanel p1 = new JPanel();

        p1.setBorder( new TitledBorder( BorderFactory.createEtchedBorder( Color.white, new Color( 142, 142, 142 ) ),
            "Upload Preferences" ) );

        int[] w1 = { 10, 10, 10, 100, 10 };
        int[] h1 = { 10, 21, 10, 21, 100, 10 };

        HIGLayout l1 = new HIGLayout( w1, h1 );
        HIGConstraints c = new HIGConstraints();

        l1.setColumnWeight( 2, 1 );
        l1.setRowWeight( 5, 1 );

        p1.setLayout( l1 );

        // addShareButton = new JButton("Add");
        p1.add( addShareButton, c.rc( 2, 4 ) );

        // removeShareButton = new JButton("Remove");
        p1.add( removeShareButton, c.rc( 4, 4 ) );

        addShareButton.addMouseListener( new MouseAdapter()
        {
            @Override
            public void mouseClicked(
                MouseEvent e )
            {
                onBrowseDir( false );
            }
        } );

        removeShareButton.addMouseListener( new MouseAdapter()
        {
            @Override
            public void mouseClicked(
                MouseEvent e )
            {
                onRemoveShare();
            }
        } );

        shareScrollPane.setSize( 400, 175 );
        shareList.setSize( new java.awt.Dimension( 400, 175 ) );

        shareList.setPrototypeCellValue( "000000000000000000000000000000000000000000000000" );

        shareScrollPane.getViewport().add( shareList, null );

        p1.add( shareScrollPane, c.rcwh( 2, 2, 1, 4 ) );

        return p1;

    }

    /**
     * This method is called when the "browse" button is pressed.
     */
    private final void onBrowseDir(
        boolean download )
    {
        JFileChooser chooser = new JFileChooser();

        chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

        int returnVal = chooser.showOpenDialog( this );

        if ( returnVal == JFileChooser.APPROVE_OPTION )
        {
            if ( download )
            {
                txtTempDownloadDir.setText( chooser.getSelectedFile().getPath() );
            }
            else
            {
                ((DirectoryListModel) shareList.getModel()).addDirectory( chooser.getSelectedFile().getPath() );
            }

        }
        else
        {
            logger.debug( "Adding directory didn't work." );
        }
    }

    /**
     * This method is called when the "remove" button is pressed.
     * 
     * @deprecated 2002-05-08
     */
    @Deprecated
    private final void onRemoveShare()
    {
        try
        {
            ((DirectoryListModel) shareList.getModel()).removeDirectoryAt( shareList.getSelectedIndex() );
        }
        catch ( Exception e )
        {
            // logger.debug(e.toString());
            logger.error( "Caught " + e.getClass().getName(), e );

        }

    }

    /**
     * This Method fills the <code>shareModel</code> with the given directories.
     * 
     * @param dirs The Directories to fill into the <code>shareModel</code>. The Directories hav to be given as an
     *            <code>Array of
     * 		  Strings</code>.
     * @deprecated 2002-05-08
     */
    @Deprecated
    private final void populateShareList(
        String[] dirs )
    {
        for ( String dir : dirs )
        {
            shareModel.addDirectory( dir );
            logger.debug( "Directory " + dir + " added." );
        }
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

        p1.add( getDownloadPreferences() );
        p1.add( getUploadPreferences() );

        this.add( p1, BorderLayout.NORTH );
    }
}
