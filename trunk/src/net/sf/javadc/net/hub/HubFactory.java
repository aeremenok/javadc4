/*
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

package net.sf.javadc.net.hub;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubFactory;
import net.sf.javadc.interfaces.IHubInfo;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITaskManager;

/**
 * <code>HubFactory</code> represents a factory method implementation used to
 * create preconfigured <code>Hub</code> instances.
 * 
 * @author tw70794
 */
public class HubFactory implements IHubFactory {

    // components
    /**
     * 
     */
    private final IHubManager hubManager;

    /**
     * 
     */
    private final IHubTaskFactory hubTaskFactory;

    /**
     * 
     */
    private final ITaskManager taskManager;

    /**
     * 
     */
    private final ISettings settings;

    /**
     * Create a HubFactory instance
     * 
     * @param _taskManager
     *            ITaskManager instance to be used
     * @param _hubManager
     *            IHubManager instance to be used
     * @param _hubTaskFactory
     *            IHubTaskFactory instance to be used
     * @param _settings
     *            ISettings instance to be used
     */
    public HubFactory(ITaskManager _taskManager, IHubManager _hubManager,
            IHubTaskFactory _hubTaskFactory, ISettings _settings) {

        if (_taskManager == null)
            throw new NullPointerException("_taskManager was null.");
        else if (_hubManager == null)
            throw new NullPointerException("_hubManager was null.");
        else if (_hubTaskFactory == null)
            throw new NullPointerException("_hubTaskFactory was null.");
        else if (_settings == null)
            throw new NullPointerException("_settings was null.");

        taskManager = _taskManager;
        hubManager = _hubManager;
        hubTaskFactory = _hubTaskFactory;
        settings = _settings;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFactory#createHub(net.sf.javadc.net.hub.HostInfo)
     */
    public final IHub createHub(HostInfo hostInfo) {
        if (hostInfo == null)
            throw new NullPointerException("HostInfo was null.");

        return new Hub(hostInfo, taskManager, hubManager, hubTaskFactory,
                settings);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubFactory#createHub(net.sf.javadc.interfaces.IHubInfo)
     */
    public final IHub createHub(IHubInfo hubInfo) {
        if (hubInfo == null)
            throw new NullPointerException("HubInfo was null.");

        IHub hub = createHub(hubInfo.getAddress());

        hub.setName(hubInfo.getName());
        hub.setDescription(hubInfo.getDescription());

        return hub;

    }

}

/*******************************************************************************
 * $Log: HubFactory.java,v $
 * Revision 1.12  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.11 2005/09/30 15:59:53 timowest updated
 * sources and tests
 * 
 * Revision 1.10 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
