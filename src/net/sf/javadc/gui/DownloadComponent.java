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
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;   without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE.   See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: DownloadComponent.java,v 1.23 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import net.sf.javadc.gui.model.DownloadTreeModelAdapter;
import net.sf.javadc.gui.model.IDownloadNode;
import net.sf.javadc.gui.util.JTreeTable;
import net.sf.javadc.gui.util.TreeTablePanel;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.interfaces.IRequestsModel;
import net.sf.javadc.interfaces.ITask;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.net.DownloadRequest;
import net.sf.javadc.util.FileUtils;
import spin.Spin;

/**
 * <CODE>DownloadComponent</CODE> provides a TreeTable view with the active
 * Client <CODE>Connections</CODE> as the top level items and related <CODE>
 * DownloadRequests</CODE> as second level items
 * 
 * @author Jesper Nordenberg
 * @author Timo Westk�mper
 * @version $Revision: 1.23 $ $Date: 2005/10/02 11:42:28 $
 */
public class DownloadComponent extends TreeTablePanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1939780322156375326L;

    // private final static Category logger = Category
    // .getInstance(DownloadComponent.class);

    // attributes
    /**
     * 
     */
    private final DownloadTreeModelAdapter model;

    /**
     * 
     */
    private final JTreeTable list;

    // components
    // private final IRequestsModel requestsModel;
    /**
     * 
     */
    private final ITaskManager taskManager;

    /**
     * Create a DownloadComponent with the given IRequestsModel to be used
     * 
     * @param _model
     *            IRequestsModel instance to be used
     */
    public DownloadComponent(ITaskManager _taskManager, IRequestsModel _model) {
        super();

        if (_taskManager == null)
            throw new NullPointerException("taskManager was null.");

        if (_model == null)
            throw new NullPointerException("model was null.");

        taskManager = (ITaskManager) Spin.off(_taskManager);

        // requestsModel = _model;
        model = new DownloadTreeModelAdapter(_model);
        list = new JTreeTable(model);

        setJTreeTable(list);

        JTree tree = list.getTree();

        tree.setRootVisible(false);
        tree.setShowsRootHandles(false);

    }

    /** ********************************************************************** */

    /**
     * shows a popup menu related to the selected <CODE>ClientConnection
     * </CODE>
     */
    private final void showConnectionPopup(final IConnection cc, Point pos) {
        JPopupMenu popup = new JPopupMenu();

        final String nick = cc.getClient().getNick();

        // menu item to close the current connection to the remote client
        // (to remove the running download from the remote client)
        JMenuItem closeCurrent = new JMenuItem("Close current connection to: "
                + nick);

        closeCurrent.setIcon(FileUtils.loadIcon("images/16/connect_no.png"));
        closeCurrent.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // cc.getClient().removeDownload(0);
                taskManager.addEvent(new ITask() {

                    public void runTask() {
                        cc.disconnect();

                    }

                });

            }

        });

        popup.add(closeCurrent);

        // popup.add(closeAll);
        popup.show(this, pos.x, pos.y);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.util.TreeTablePanel#showPopup(java.awt.event.MouseEvent)
     */
    public final void showPopup(MouseEvent e) {
        Point pos = e.getPoint();

        IDownloadNode node = (IDownloadNode) list.getTree()
                .getClosestPathForLocation(pos.x, pos.y).getLastPathComponent();

        IDownloadNode parentNode = (IDownloadNode) list.getTree()
                .getClosestPathForLocation(pos.x, pos.y).getParentPath()
                .getLastPathComponent();

        if (node.getMainObject() instanceof IConnection) {
            IConnection connection = (IConnection) node.getMainObject();

            showConnectionPopup(connection, pos);

        } else if (node.getMainObject() instanceof DownloadRequest) {
            IConnection connection = (IConnection) parentNode.getMainObject();
            DownloadRequest request = (DownloadRequest) node.getMainObject();

            showQueuePopup(connection, request, pos);

        } else {

            // ?
        }

    }

    /**
     * shows a popup menu related to an item in the download queue of a remote
     * client
     */
    private final void showQueuePopup(final IConnection cc,
            final DownloadRequest dr, Point pos) {
        JPopupMenu popup = new JPopupMenu();

        JMenuItem closeCurrent = new JMenuItem("Remove "
                + FileUtils.getName(dr.getSearchResult().getFilename())
                + " from Queue");

        closeCurrent.setIcon(FileUtils.loadIcon("images/16/queue.png"));
        closeCurrent.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                taskManager.addEvent(new ITask() {

                    public void runTask() {
                        cc.getClient().removeDownload(dr);

                    }

                });

            }

        });

        popup.add(closeCurrent);
        popup.show(this, pos.x, pos.y);

    }

}

/*******************************************************************************
 * $Log: DownloadComponent.java,v $
 * Revision 1.23  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.22 2005/09/30 15:59:53 timowest
 * updated sources and tests
 * 
 * Revision 1.21 2005/09/26 17:53:13 timowest added null checks
 * 
 * Revision 1.20 2005/09/26 17:19:52 timowest updated sources and tests
 * 
 * Revision 1.19 2005/09/25 16:40:58 timowest updated sources and tests
 * 
 * Revision 1.18 2005/09/14 07:11:49 timowest updated sources
 * 
 * 
 * 
 */
