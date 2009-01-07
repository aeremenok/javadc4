/*
 * Created on 8.8.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import net.sf.javadc.interfaces.IShareManager;
import net.sf.javadc.listeners.ShareManagerListener;
import net.sf.javadc.util.FileUtils;
import spin.Spin;

/**
 * <CODE>ShareManagerWindow</CODE> is used as the splash screen of javadc3
 * which contains also information about the status of the application loading
 * phase.
 * 
 * @author Timo Westkämper
 */
public class ShareManagerWindow extends JWindow {

    /**
     * 
     */
    private static final long serialVersionUID = -565409389543402770L;

    /**
     * 
     */
    private final int screenWidth = getToolkit().getScreenSize().width;

    /**
     * 
     */
    private final int screenHeight = getToolkit().getScreenSize().height;

    // private final int frameWidth = 350;
    /**
     * 
     */
    private final int frameWidth = 175;

    // private final int frameHeight = 200;
    /**
     * 
     */
    private final int frameHeight = 100;

    /**
     * 
     */
    private final JPanel mainPanel = new JPanel();

    /**
     * 
     */
    private final JLabel label = new JLabel();

    /**
     * 
     */
    private final JLabel logo = new JLabel();

    /**
     * 
     */
    private ShareManagerListener shareManagerListener = new MyShareManagerListener();

    // external components
    /**
     * 
     */
    private final IShareManager shareManager;

    /**
     * Create a ShareManagerWindow with the given IShareManager instance
     * 
     * @param _shareManager
     *            IShareManager instance to be used
     */
    public ShareManagerWindow(IShareManager _shareManager) {
        super();

        if (_shareManager == null)
            throw new NullPointerException("shareManager was null");

        shareManager = (IShareManager) Spin.off(_shareManager);
        shareManager.addListener((ShareManagerListener) shareManagerListener);

        // sets location and size of the component
        setLocation((screenWidth / 2) - (frameWidth / 2), (screenHeight / 2)
                - (frameHeight / 2));

        setSize(frameWidth, frameHeight);

        // sets layout of the component
        logo.setIcon(FileUtils.loadIcon("images/JavaDC.gif"));

        mainPanel.setLayout(new BorderLayout());

        JPanel logoPanel = new JPanel();

        logoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        logoPanel.add(logo);
        mainPanel.add(logoPanel, BorderLayout.CENTER);

        JPanel labelPanel = new JPanel();

        labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(label);
        mainPanel.add(labelPanel, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.SOUTH);

        label.setText("Loading shared files.");

        // setModal(true); // dialog is modal
    }

    /** ********************************************************************** */
    private class MyShareManagerListener implements ShareManagerListener {

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ShareManagerListener#directoryAdded(java.lang.String)
         */
        public void directoryAdded(String directory) {
            label.setText("Directory " + directory + " added.");

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ShareManagerListener#creatingBrowseList()
         */
        public void creatingBrowseList() {
            label.setText("Creating BrowseList.");

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ShareManagerListener#browseListCreated()
         */
        public void browseListCreated() {
            label.setText("BrowseList created.");

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ShareManagerListener#fileHashed(java.lang.String)
         */
        public void fileHashed(String filename) {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see net.sf.javadc.listeners.ShareManagerListener#hashingFile(java.lang.String,
         *      double)
         */
        public void hashingFile(String filename, double percent) {
            // TODO Auto-generated method stub

        }
    }

}

/*******************************************************************************
 * $Log: ShareManagerWindow.java,v $
 * Revision 1.18  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.17 2005/09/26 17:53:13 timowest
 * added null checks
 * 
 * Revision 1.16 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.15 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
