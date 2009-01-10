/*
 * Copyright (C) 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details. You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.javadc.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventListener;

import javax.swing.JFrame;

import net.sf.javadc.config.Settings;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.HubManager;

/**
 * todo what is it?
 * 
 * @author Timo Westk�mper
 */
public class FileBrowseComponentSimulation
{
    private IHubManager              hubManager          = new HubManager();
    private ISettings<EventListener> settings            = new Settings();
    private ISegmentManager          segmentManager      = new SegmentManager( settings );
    private IDownloadManager         downloadManager     = new DownloadManager( hubManager, segmentManager );
    private FileBrowseComponent      fileBrowseComponent = new FileBrowseComponent( settings, downloadManager );

    public static void main(
        String[] args )
    {
        new FileBrowseComponentSimulation();
    }

    public FileBrowseComponentSimulation()
    {
        JFrame fileListFrame = new JFrame();

        fileListFrame.getContentPane().add( fileBrowseComponent );

        fileListFrame.setLocation( 100, 100 );
        fileListFrame.setSize( 400, 300 );

        fileListFrame.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing(
                WindowEvent e )
            {
                System.exit( 0 );
            }
        } );

        fileListFrame.setVisible( true );

        try
        {
            while ( true )
            {
                Thread.sleep( 10000 );
            }
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
    }
}
