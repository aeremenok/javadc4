/* *
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

package net.sf.javadc.tasks;

import java.util.ArrayList;
import java.util.List;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubTask;
import net.sf.javadc.tasks.hub.HubTaskException;

import org.apache.log4j.Category;

/**
 * <code>BaseHubTask</code> represents an abstract template implementation of
 * the <code>IHubTask</code> interface.
 * 
 * <p>
 * New <code>IHubTask</code> variants can be created by subclassing
 * <code>BaseHubTask</code> and naming the class
 * <code>net.sf.javadc.tasks.hub.I${command name}Task</code>, overriding the
 * runTaskTemplate method and registering it in the
 * <code>ContainerBuilder</code> class.
 * </p>
 * 
 * @author tw70794
 */
public abstract class BaseHubTask implements IHubTask {

    private static final Category logger = Category
            .getInstance(BaseHubTask.class);

    // are left protected for speed reasons
    protected String cmdData; // represents that data of the command

    protected IHub hub; // related hub

    private List exceptions = new ArrayList();

    /**
     * Create a BaseHubTask instance
     */
    public BaseHubTask() {

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubTask#getCmdData()
     */
    public String getCmdData() {
        return cmdData;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubTask#getHub()
     */
    public IHub getHub() {
        return hub;

    }

    /**
     * wrapper method to catch exceptions and make the execution of tasks safe
     */
    public void runTask() {

        // execution should not forward exceptions
        try {
            runTaskTemplate();

        } catch (HubTaskException he) {
            exceptions.add(he);

            logger.error("Catched HubTaskException in "
                    + this.getClass().getName(), he);

        } catch (Exception e) {
            exceptions.add(e);

            logger.error("Catched unexpected " + e.getClass().getName()
                    + " in " + this.getClass().getName(), e);

        }

    }

    /**
     * template method
     */
    abstract protected void runTaskTemplate();

    /**
     * dispatching the sending of commands to the underlying Hub
     */
    protected void sendCommand(String command, String data) {
        // logger.debug("Sending command: " + command
        // + ConstantSettings.COMMAND_SEP_CHAR + data);

        try {
            // dispatching
            hub.sendCommand(command, data);

        } catch (Exception e) {
            logger.error("Error in " + this.getClass().getName()
                    + " when trying to send " + " message " + command
                    + " with data " + data, e);

            // logger.error(e);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubTask#setCmdData(java.lang.String)
     */
    public void setCmdData(String string) {
        cmdData = string;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubTask#setHub(net.sf.javadc.interfaces.IHub)
     */
    public void setHub(IHub hub) {
        this.hub = hub;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IHubTask#getExceptions()
     */
    public List getExceptions() {
        return exceptions;
    }

}