/*
 * Copyright (C) 2001 Ryan Sweny, cabin@geeky.net
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

// $Id: UDPListenerThread.java,v 1.14 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.net;

import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;

import org.picocontainer.Startable;

/**
 * <code>UDPListenerThread</code>
 * 
 * @author Ryan Sweny
 * @version $Revision: 1.14 $ $Date: 2005/10/02 11:42:27 $
 */
public class UDPListenerThread implements Startable {

    // private static final Category logger = Category
    // .getInstance(UDPListenerThread.class);
    /**
     * 
     */
    private final UDPListener listener;

    // private final boolean stopThread = true;
    /**
     * 
     */
    private Thread thread;

    // components
    /**
     * 
     */
    private final ISettings settings;

    /**
     * Create a UDPListenerThread with the given IHubManager, ITaskManager,
     * ISettings and IHubTaskFactory
     * 
     * @param _hubManager
     *            IHubManager instance to be used
     * @param _taskManager
     *            ITaskManager instance to be used
     * @param _settings
     *            ISettings instance to be used
     * @param _hubTaskFactory
     *            IHubTaskFactory instance to be used
     */
    public UDPListenerThread(IHubManager _hubManager,
            ITaskManager _taskManager, ISettings _settings,
            IHubTaskFactory _hubTaskFactory) {
        settings = _settings;
        listener = new UDPListener(_hubManager, _taskManager, _hubTaskFactory);

    }

    /** ********************************************************************** */

    /**
     * Enable and disable the UDPListenerThread
     * 
     * @param b
     */
    public final synchronized void setEnabled(boolean b) {
        if (b && (thread == null)) { // enable
            listener.setPort(settings.getUserInfo().getPort());

            thread = new Thread(listener, "UDPListener");
            thread.start();

        } else if (!b && (thread != null)) { // disable
        // thread.stop();
            listener.kill();
            thread = null;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#start()
     */
    public void start() {
        setEnabled(settings.isActive());

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public void stop() {
        setEnabled(false);

    }

}

/*******************************************************************************
 * $Log: UDPListenerThread.java,v $
 * Revision 1.14  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.13 2005/09/30 15:59:53 timowest
 * updated sources and tests
 * 
 * Revision 1.12 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
