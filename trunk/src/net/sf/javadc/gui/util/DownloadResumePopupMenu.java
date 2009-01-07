/*
 * Copyright (C) 2001 Johan Nilsson, jfn@home.se Copyright (C) 2004 Timo Westkämper This program is free software; you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: DownloadResumePopupMenu.java,v 1.22 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.gui.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.util.FileUtils;

import org.apache.log4j.Category;

import spin.Spin;

/**
 * <CODE>DownloadResumePopupMenu</CODE> is a popup menu with download and resume functionality. (Moved out from
 * <CODE>SearchComponent.</CODE>)
 */
public class DownloadResumePopupMenu
    extends JPopupMenu
{
    /** 
     * 
     */
    private static final long      serialVersionUID = -8219487136313980549L;

    private static final Category  logger           = Category.getInstance( DownloadResumePopupMenu.class );

    private final IHub             hub;

    private final SearchResult     searchResult;

    private final List             allSelectedSearchResults;

    // components
    private final ISettings        settings;

    private final IDownloadManager downloadManager;

    /**
     * Create a DownloadResumePopupMenu which uses the given IHub, SearchResult, and ISettings instance
     * 
     * @param _hub IHub to be used
     * @param _searchResult SearchResult to be used
     * @param _settings ISettings instance to be used
     * @param _downloadManager IDownloadManager instance to be used
     */
    public DownloadResumePopupMenu(
        IHub _hub,
        SearchResult _searchResult,
        List _allSelectedSearchResults,
        ISettings _settings,
        IDownloadManager _downloadManager )
    {

        if ( _hub == null )
        {
            throw new NullPointerException( "_hub was null." );
        }
        else if ( _searchResult == null )
        {
            throw new NullPointerException( "_searchResult was null." );
        }
        else if ( _allSelectedSearchResults == null )
        {
            throw new NullPointerException( "_allSelectedSearchResults was null." );
        }
        else if ( _settings == null )
        {
            throw new NullPointerException( "_settings was null." );
        }
        else if ( _downloadManager == null )
        {
            throw new NullPointerException( "_downloadManager was null." );
        }

        hub = (IHub) Spin.off( _hub );
        searchResult = _searchResult;
        allSelectedSearchResults = _allSelectedSearchResults;
        settings = _settings;

        // already spinned
        downloadManager = _downloadManager;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JPopupMenu#show(java.awt.Component, int, int)
     */
    @Override
    public final void show(
        Component invoker,
        int x,
        int y )
    {
        setupMenu();
        super.show( invoker, x, y );

    }

    /**
     * Choose file to resume via a ResumeAsFileChooser
     * 
     * @param searchResult SearchResult for which an available file should be used for resume
     */
    private final void chooseFileToResume(
        SearchResult searchResult )
    {
        final ResumeAsFileChooser fileChooser = new ResumeAsFileChooser( settings.getTempDownloadDir() );

        if ( fileChooser.showResumeDialog( new File( searchResult.getFilename() ) ) == ResumeAsFileChooser.APPROVE_OPTION )
        {
            logger.debug( "Resuming file as: " + fileChooser.getSelectedFile().getAbsolutePath() );

            // NOTE : takes only first item into account

            try
            {
                downloadManager.requestDownload( new DownloadRequest( searchResult, fileChooser.getSelectedFile()
                    .getAbsolutePath(), settings ) );

            }
            catch ( Exception e )
            {
                logger.error( e );

            }

        }

    }

    /**
     * Custom resume the SearchResult with the given filename
     * 
     * @param name filename to be used to resume download
     * @param searchResult SearchResult to be used for resume
     */
    private final void customResume(
        String name,
        SearchResult searchResult )
    {
        try
        {
            downloadManager.requestDownload( new DownloadRequest( searchResult, settings.getTempDownloadDir() +
                File.separator + name, settings ) );

        }
        catch ( Exception e )
        {
            logger.error( e );

        }

    }

    /**
     * Start a download for the given SearchResult
     * 
     * @param searchResult
     */
    private final void downloadFile(
        SearchResult searchResult )
    {
        // logger.debug("Requesting download from hub " + hub.toString());

        downloadManager.requestDownload( new DownloadRequest( searchResult, settings ) );

    }

    /**
     * Requests download for all given SearchResults
     */
    private final void downloadFiles()
    {

        for ( Iterator i = allSelectedSearchResults.iterator(); i.hasNext(); )
        {
            SearchResult searchResult = (SearchResult) i.next();
            downloadFile( searchResult );
        }

    }

    /**
     * Setup the download menu item
     */
    private final void setupDownloadMenuItem()
    {
        final JMenuItem download = new JMenuItem( "Download" );

        download.setIcon( FileUtils.loadIcon( "images/16/down.png" ) );
        download.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {
                // downloadFile(searchResult);
                downloadFiles();

            }

        } );

        add( download );

    }

    /**
     * Setup the download menu
     */
    private final void setupMenu()
    {
        setupDownloadMenuItem(); // download the item
        setupSearchForAlternatesMenuItem(); // search for alternates based on
        // TTH info

        add( new JSeparator() );

        setupResumeAsFileChooserMenuItem(); // resume as via file chooser
        setupResumeAsMenuItem(); // resume as via popup list

    }

    /**
     * Setup the ResumeAsFileChooser menu item
     */
    private final void setupResumeAsFileChooserMenuItem()
    {
        final JMenuItem resume = new JMenuItem( "Resume File As..." );

        resume.setIcon( FileUtils.loadIcon( "images/16/saveas_edit.gif" ) );
        resume.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {
                chooseFileToResume( searchResult );

            }

        } );

        add( resume );

    }

    /**
     * Setup the resume as menu item
     */
    private final void setupResumeAsMenuItem()
    {
        final JMenu menu = new JMenu( "Resume File As" );

        menu.setIcon( FileUtils.loadIcon( "images/16/save_edit.gif" ) );
        final File downloadDir = new File( settings.getTempDownloadDir() );

        if ( downloadDir != null & downloadDir.isDirectory() )
        {
            final String[] list = downloadDir.list();
            final String remoteName = searchResult.getFilename();

            for ( int i = 0; i < list.length; i++ )
            {
                final String name = list[i];

                if ( FileUtils.isSimilar( name, remoteName ) )
                {
                    JMenuItem submenu = new JMenuItem( name );

                    submenu.addActionListener( new ActionListener()
                    {

                        public void actionPerformed(
                            ActionEvent e )
                        {
                            customResume( name, searchResult );

                        }

                    } );

                    menu.add( submenu );

                }

            }

        }

        if ( menu.getItemCount() < 1 )
        {
            menu.setEnabled( false );

        }

        add( menu );

    }

    /**
     * Search for alternate sources for the selected SearchResults, based on the TTH hash information, if available
     */
    private final void setupSearchForAlternatesMenuItem()
    {
        final JMenuItem search = new JMenuItem( "Search for alternates" );

        search.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent arg0 )
            {

                // iterates over the search results and initiates searches
                // if TTH information is available

                for ( Iterator i = allSelectedSearchResults.iterator(); i.hasNext(); )
                {
                    SearchResult searchResult = (SearchResult) i.next();

                    if ( searchResult.getTTH() != null )
                    {
                        try
                        {
                            hub.search( new SearchRequest( searchResult.getTTH(), SearchRequest.TTH, 0, true, false ) );
                        }
                        catch ( IOException e )
                        {
                            logger.error( "Caught " + e.getClass().getName(), e );
                        }
                    }

                }
            }

        } );

        add( search );
    }

}

/*******************************************************************************
 * $Log: DownloadResumePopupMenu.java,v $ Revision 1.22 2005/10/02 11:42:27 timowest updated sources and tests Revision
 * 1.21 2005/09/25 16:40:58 timowest updated sources and tests Revision 1.20 2005/09/14 07:11:49 timowest updated
 * sources
 */
