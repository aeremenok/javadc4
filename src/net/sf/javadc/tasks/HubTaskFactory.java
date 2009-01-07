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

import net.sf.javadc.interfaces.IHubTask;
import net.sf.javadc.interfaces.IHubTaskFactory;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.log4j.Category;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Startable;

/**
 * <code>HubTaskFactory</code> is a Jakarta Commons Pool based pooled Factory
 * Method implementation to create pooled instances of PicoContainer registered
 * <code>IHubTask</code> instances.
 * 
 * <p>
 * <code>HubTaskObjectFactory</code> is used as the underlying Factory
 * implementation.
 * </p>
 * 
 * @author tw70794
 */
public class HubTaskFactory extends GenericKeyedObjectPool implements
        Startable, IHubTaskFactory {

    private final static Category logger = Category
            .getInstance(HubTaskFactory.class);

    private final HubTaskObjectFactory factory;

    /**
     * Create a ClientTaskFactory with the given ClientTask PicoContainer
     * 
     * @param _clientTasksContainer
     *            PicoContainer instance to be used for the creation of tasks
     */
    public HubTaskFactory(MutablePicoContainer _hubTasksContainer) {

        if (_hubTasksContainer == null)
            throw new NullPointerException("_hubTasksContainer was null.");

        factory = new HubTaskObjectFactory(_hubTasksContainer);
        setFactory(factory);

    }

    /** ********************************************************************** */

    /**
     * Obtain an instance from my pool for the specified key. By contract,
     * clients MUST return the borrowed object using returnObject, or a related
     * method as defined in an implementation or sub-interface, using a key that
     * is equivalent to the one used to borrow the instance in the first place.
     * 
     * @see org.apache.commons.pool.KeyedObjectPool#borrowObject(java.lang.Object)
     */
    public Object borrowObject(Object key) {
        String error;

        if (key == null) {
            error = "key was null.";
            logger.error(error);
            throw new RuntimeException(error);
        }

        if (!(key instanceof String)) {
            error = "Invalid key class " + key.getClass().getName();
            logger.error(error);
            throw new RuntimeException(error);
        }

        try {
            return super.borrowObject(key);

        } catch (Exception e) {
            logger.error(e);
            return null;
        }

    }

    /**
     * 
     * Return an instance to my pool. By contract, obj MUST have been obtained
     * using borrowObject or a related method as defined in an implementation or
     * sub-interface using a key that is equivalent to the one used to borrow
     * the Object in the first place.
     * 
     * @see org.apache.commons.pool.KeyedObjectPool#returnObject(java.lang.Object,
     *      java.lang.Object)
     */
    public void returnObject(Object key, Object obj) {
        String error;

        if ((key == null) || (obj == null)) {
            error = "key or obj was null.";
            logger.error(error);
            throw new RuntimeException(error);
        }

        if (!(key instanceof String)) {
            error = "Invalid key class " + key.getClass().getName();
            logger.error(error);
            throw new RuntimeException(error);
        }

        if (!(obj instanceof IHubTask)) {
            error = "Invalid object class " + obj.getClass().getName();
            logger.error(error);
            throw new RuntimeException(error);
        }

        try {
            super.returnObject(key, obj);

        } catch (Exception e) {
            logger.error(e);

        }

    }/*
         * (non-Javadoc)
         * 
         * @see org.picocontainer.Startable#start()
         */

    public void start() {
        factory.initialize();

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.picocontainer.Startable#stop()
     */
    public void stop() {

    }

}