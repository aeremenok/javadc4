/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se
 *
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software;      you can redistribute it and/or modify it
 * under the terms of the   GNU General Public License as published by the Free
 * Software Foundation;    either version 2 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;   without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE.   See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: UserListComponent.java,v 1.22 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.gui.model.RowTableModel;
import net.sf.javadc.gui.model.SortableTable;
import net.sf.javadc.gui.model.SortableTableListener;
import net.sf.javadc.gui.util.ByteCellRenderer;
import net.sf.javadc.gui.util.UserCellRenderer;
import net.sf.javadc.interfaces.IDownloadManager;
import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.listeners.HubListener;
import net.sf.javadc.listeners.HubListenerBase;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.hub.HubUser;
import net.sf.javadc.util.FileUtils;
import spin.Spin;

/**
 * <CODE>UserListComponent</CODE> provides a view on the users of a specific
 * <CODE>Hub</CODE> or all connected <CODE>Hubs</CODE>
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.22 $ $Date: 2005/10/02 11:42:28 $
 */
public class UserListComponent extends BasePanel implements
        SortableTableListener {

    /**
     * 
     */
    private static final long serialVersionUID = 2650931664823334860L;

    // private final static Category logger = Category
    // .getInstance(UserListComponent.class);
    /**
     * 
     */
    private final IHub hub;

    /**
     * 
     */
    private HubComponent hubComponent;

    /**
     * 
     */
    private final RowTableModel model = new RowTableModel(new String[] {
            "Nick", "Size", "Speed", "Description" });

    /**
     * 
     */
    private final SortableTable list = new SortableTable(new int[] { 90, 70,
            70, -1 }, this, model, "users");

    // external components
    /**
     * 
     */
    private final ISettings settings;

    /**
     * 
     */
    private final IDownloadManager downloadManager;

    /**
     * Create a new UserListComponent instance
     * 
     * @param _hub
     * @param _settings
     * @param _downloadManager
     */
    public UserListComponent(IHub _hub, HubComponent _hubComponent,
            ISettings _settings, IDownloadManager _downloadManager) {
        super(new BorderLayout());

        if (_hub == null)
            throw new NullPointerException("hub was null");

        if (_settings == null)
            throw new NullPointerException("settings was null");

        if (_downloadManager == null)
            throw new NullPointerException("downloadManager was null");

        // already spinned
        hub = _hub;

        hubComponent = _hubComponent;

        settings = _settings;

        // already spinned
        downloadManager = _downloadManager;

        initialize();
    }

    /** ********************************************************************** */

    /**
     * 
     */
    private void initialize() {
        hub.addListener((HubListener) Spin.over(new MyHubListener(model)));

        list.getTable().setDefaultRenderer(Long.class, new ByteCellRenderer());

        ImageIcon[] buffer = getUserIcons();

        List opList = null;

        try {
            opList = ((IHub) hub).getOpList();

        } catch (Exception e) {

            // we will not show Ops in the AllHubs tab
        }

        list.getTable().getColumn("Nick").setCellRenderer(
                new UserCellRenderer(buffer, opList));

        list.getTable().setRowHeight(
                settings.getAdvancedSettings().getIconSize());

        /*
         * JLabel usersLabel = new JLabel("Users"); usersLabel.setBorder(new
         * EmptyBorder(5,5,5,5)); add(usersLabel, BorderLayout.NORTH);
         */
        add(list, BorderLayout.CENTER);

        setMinimumSize(new Dimension(200, 0));
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.SortableTableListener#cellSelected(int, int)
     */
    public final void cellSelected(int row, int column, int[] selectedColumn) {
        HubUser user = (HubUser) model.getRow(row);
        String filename = "MyList.DcLst";

        SearchResult sr = new SearchResult(hub, user.getNick(), filename,
                settings, 1);

        /*
         * String localname = settings.getDownloadDir() +
         * FileUtils.getNameNoExtension(filename) + "." + user.getNick() + "." +
         * FileUtils.getExtension(filename);
         */
        String localname = settings.getDownloadDir() + "MyList."
                + user.getNick() + ".DcLst";

        DownloadRequest dr = new DownloadRequest(sr, localname, settings);

        downloadManager.requestDownload(dr);

    }

    /**
     * Get the column contents for the given HubUser
     * 
     * @param user
     *            HubUser instance for which the column contents are to be
     *            retrieved
     * 
     * @return
     */
    private final Object[] getUserColumns(HubUser user) {
        return new Object[] { user.getNick(), new Long(user.getSharedSize()),
                user.getSpeed(), user.getDescription() };

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.SortableTableListener#showPopupClicked(int,
     *      int, java.awt.event.MouseEvent)
     */
    public final void showPopupClicked(final int row, final int column,
            MouseEvent e, int[] selectedRows) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem sendMessage = new JMenuItem("Send Message");

        sendMessage.setIcon(FileUtils.loadIcon("images/16/mail_generic.png"));

        JMenuItem browse = new JMenuItem("Browse Files");

        browse.setIcon(FileUtils.loadIcon("images/16/list.png"));

        sendMessage.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                HubUser user = (HubUser) model.getRow(row);

                hubComponent.addUserMessageTab(user.getNick());

            }

        });

        browse.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int[] els = {};
                cellSelected(row, column, els);

            }

        });

        popup.add(sendMessage);
        popup.add(browse);
        popup.show(e.getComponent(), e.getX(), e.getY());

    }

    private final class MyHubListener extends HubListenerBase {

        private final RowTableModel model;

        // private long lastChanged = 0;
        private ArrayList added = new ArrayList();

        private ArrayList removed = new ArrayList();

        private boolean updated = false;

        private MyHubListener(RowTableModel model) {
            super();
            this.model = model;

        }

        public void userAdded(IHub hub, HubUser ui) {
            added.add(ui);

            if (!updated) {
                addUpdateTimer();

            }

        }

        public void userRemoved(IHub hub, HubUser ui) {
            removed.add(ui);

            if (!updated) {
                addUpdateTimer();

            }

        }

        public void addUpdateTimer() {
            updated = true;

            ActionListener taskPerformer = new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    update();
                    updated = false;

                }

            };

            Timer timer = new Timer(
                    ConstantSettings.MANAGERCOMPONENT_UPDATEINTERVAL,
                    taskPerformer);

            timer.setRepeats(false);
            timer.start();

        }

        private void update() {
            // updates the model only if the last change has happend more
            // than 1 second ago
            HubUser[] ad = (HubUser[]) added.toArray(new HubUser[added.size()]);

            added.clear();

            HubUser[] re = (HubUser[]) removed.toArray(new HubUser[removed
                    .size()]);

            removed.clear();

            // adds new hubs
            for (int i = 0; i < ad.length; i++) {
                model.addRow(ad[i], getUserColumns(ad[i]));

            }

            // removed old hubs
            for (int j = 0; j < re.length; j++) {
                model.deleteRow(re[j]);

            }

        }

    }

}

/*******************************************************************************
 * $Log: UserListComponent.java,v $
 * Revision 1.22  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.21 2005/09/30 15:59:53 timowest
 * updated sources and tests
 * 
 * Revision 1.20 2005/09/26 17:53:13 timowest added null checks
 * 
 * Revision 1.19 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.18 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
