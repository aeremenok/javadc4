/*
 * Created on 28.12.2004
 */

package net.sf.javadc.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.ISegmentManager;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.mockups.BaseHub;
import net.sf.javadc.mockups.BaseSettings;
import net.sf.javadc.net.DownloadManager;
import net.sf.javadc.net.SegmentManager;
import net.sf.javadc.net.hub.HubManager;

/**
 * @author Timo Westkämper
 */
public class HubComponentSimulation {

    // private ISettings settings = new Settings();
    private ISettings settings = new BaseSettings(true);

    private IHubManager hubManager = new HubManager();

    private ISegmentManager segmentManager = new SegmentManager(settings);

    private IDownloadManager downloadManager = new DownloadManager(hubManager,
            segmentManager);

    private HubComponent hubComponent = new HubComponent(new BaseHub(),
            settings, downloadManager);

    public HubComponentSimulation() {

        JFrame frame = new JFrame();

        frame.getContentPane().add(hubComponent);

        frame.setLocation(100, 100);
        frame.setSize(800, 600);

        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }

        });

        frame.setVisible(true);

        try {
            while (true) {
                Thread.sleep(10000);

            }

        } catch (InterruptedException e) {
            // logger.error(e.toString());
            System.out.println(e.toString());

        }
    }

    public static void main(String[] args) {
        new HubComponentSimulation();
    }

}

/*******************************************************************************
 * $Log: HubComponentSimulation.java,v $
 * Revision 1.7  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.6 2005/09/14 07:11:49
 * timowest updated sources
 * 
 * 
 * 
 */
