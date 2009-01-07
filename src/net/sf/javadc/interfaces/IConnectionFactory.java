/*
 * Copyright (C) 2004 Timo Westkämper
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.interfaces;

import net.sf.javadc.listeners.ConnectionListener;
import net.sf.javadc.net.client.Client;

/**
 * <CODE>IConnectionFactory</CODE> is the abstract interface for the Factory
 * Method implementation <CODE>ConnectionFactory</CODE> which creates
 * preconfigured Client <CODE>Connection</CODE> instances
 * 
 * @author Timo Westk�mper
 */
public interface IConnectionFactory extends IObject {

    /**
     * Create a Client Connection with the given parameters
     * 
     * @param client
     *            Client to be used
     * @param listener
     *            ConnectionListener to be used
     * @param isServer
     *            true, if Connection is active and fase, if not
     * @return
     */
    public IConnection createClientConnection(Client client,
            ConnectionListener listener, boolean isServer);

}