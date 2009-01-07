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

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.IClientTask;
import net.sf.javadc.interfaces.IConnection;
import net.sf.javadc.tasks.client.ClientTaskException;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

/**
 * <code>BaseClientTask</code> represents an abstract template implementation
 * of the <code>IClientTask</code> interface.
 * 
 * <p>
 * New <code>IClientTask</code> variants can be created by subclassing
 * <code>BaseClientTask</code> and naming the class
 * <code>net.sf.javadc.tasks.client.I${command name}Task</code>, overriding
 * the runTaskTemplate method and registering it in the
 * <code>ContainerBuilder</code> class.
 * </p>
 * 
 * @author tw70794
 */
public abstract class BaseClientTask implements IClientTask {

    private static final Category logger = Category
            .getInstance(BaseClientTask.class);

    private static final Logger clientTraffic = Logger.getLogger("C2C");

    // are left protected for speed reasons
    protected IConnection clientConnection;

    protected String cmdData;

    private List exceptions = new ArrayList();

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientTask#getClientConnection()
     */
    public IConnection getClientConnection() {
        return clientConnection;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientTask#getCmdData()
     */
    public String getCmdData() {
        return cmdData;

    }

    /**
     * wrapper method to catch exceptions and make the execution of tasks safe
     */
    public void runTask() {

        // execution should not forward exceptions

        try {
            runTaskTemplate();

        } catch (ClientTaskException ce) {
            String error = "Catched  ClientTaskException in "
                    + this.getClass().getName();

            if (clientConnection.getClient() != null) {
                error = error + " for "
                        + clientConnection.getClient().getNick();
            }

            exceptions.add(ce);
            logger.error(error, ce);

        } catch (Exception e) {
            String error = "Catched unexpected " + e.getClass().getName()
                    + " in " + this.getClass().getName();

            if (clientConnection.getClient() != null) {
                error = error + " for "
                        + clientConnection.getClient().getNick();
            }

            exceptions.add(e);
            logger.error(error, e);

        }

    }

    /**
     * template method
     */
    protected abstract void runTaskTemplate();

    /**
     * dispatching the sending of commands to the related ClientConnection
     */
    protected void sendCommand(String command, String data) {
        logger.debug("Sending command: " + command
                + ConstantSettings.COMMAND_SEP_CHAR + data);

        // client traffic output

        String cmd = command + ConstantSettings.COMMAND_SEP_CHAR + data;

        if (clientConnection.getClient() != null) {
            clientTraffic.info("s : " + cmd + "("
                    + clientConnection.getClient().getNick() + ")");
        } else {
            clientTraffic.info("s : " + cmd);
        }

        try {
            clientConnection.sendCommand(command, data);

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
     * @see net.sf.javadc.interfaces.IClientTask#setClientConnection(net.sf.javadc.interfaces.IConnection)
     */
    public void setClientConnection(IConnection connection) {
        clientConnection = connection;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientTask#setCmdData(java.lang.String)
     */
    public void setCmdData(String string) {
        cmdData = string;

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IClientTask#getExceptions()
     */
    public List getExceptions() {
        return exceptions;
    }

}