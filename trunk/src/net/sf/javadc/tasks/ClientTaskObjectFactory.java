/* *
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.javadc.config.ConstantSettings;
import net.sf.javadc.interfaces.IClientTask;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.log4j.Category;
import org.picocontainer.ComponentAdapter;
import org.picocontainer.MutablePicoContainer;

/**
 * <code>ClientTaskObjectFactory</code> is a simple Jakarta Commons Pool and
 * PicoContainer based Factory method implementation used via the
 * <code>ClientTaskFactory</code>.
 * 
 * @author Timo Westk�mper
 */
public class ClientTaskObjectFactory extends BaseKeyedPoolableObjectFactory {

    private final static Category logger = Category
            .getInstance(ClientTaskObjectFactory.class);

    private final MutablePicoContainer clientTasksContainer;

    private Map componentAdapters;

    /**
     * Create a ClientTaskObjectFactory instance which creates ClientTask
     * instances via the given PicoContainer tasksContainer
     * 
     * @param tasksContainer
     *            PicoContainer used to create preconfigured instances of client
     *            tasks
     */
    public ClientTaskObjectFactory(MutablePicoContainer tasksContainer) {
        clientTasksContainer = tasksContainer;
    }

    /**
     * Initializes the ClientTaskObjectFactory by creating the component
     * adapters from the clientTasksContains
     */
    protected void initialize() {
        Collection _adapters = clientTasksContainer.getComponentAdapters();

        Object[] compArray = _adapters.toArray(new Object[_adapters.size()]);

        componentAdapters = new HashMap();

        for (int i = 0; i < compArray.length; i++) {
            componentAdapters.put(((ComponentAdapter) compArray[i])
                    .getComponentKey(), compArray[i]);

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.pool.BaseKeyedPoolableObjectFactory#makeObject(java.lang.Object)
     */
    public Object makeObject(Object arg0) throws Exception {

        if ((arg0 == null) || !(arg0 instanceof String)) {
            throw new NullPointerException("Invalid arguments");

        }

        String taskName = (String) arg0;

        if (taskName.equals("")) {
            return null;

        }

        IClientTask task = null;

        try {
            Class aclass = Class
                    .forName(ConstantSettings.CLIENTTASKFACTORY_PREFIX
                            + taskName
                            + ConstantSettings.CLIENTTASKFACTORY_POSTFIX);

            // retrieve component adapter related to the specific component type
            final ComponentAdapter adapter = (ComponentAdapter) componentAdapters
                    .get(aclass);

            if (adapter != null) {
                task = (IClientTask) adapter.getComponentInstance();

            } else {
                logger.error("No Task for " + taskName);

            }

        } catch (ClassNotFoundException e) {
            logger.error("Task not found : " + taskName);

        } catch (NoClassDefFoundError ex) {
            logger.error("Task not found : " + taskName);

        }

        return task;
    }

}