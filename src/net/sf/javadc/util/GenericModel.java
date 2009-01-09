/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
/*
 * Created on 14.7.2004 To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code
 * Generation&gt;Code and Comments
 */

package net.sf.javadc.util;

import java.util.EventListener;

import javax.swing.event.EventListenerList;

import net.sf.javadc.interfaces.IGenericModel;

/**
 * @author Timo Westk�mper To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class GenericModel<ListenerType extends EventListener>
    implements
        IGenericModel<ListenerType>
{
    protected final transient EventListenerList listenerList = new EventListenerList();

    /* 
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IGenericModel#addListener(java.util.EventListener)
     */
    public void addListener(
        ListenerType listener )
    {
        listenerList.add( getListenerClass(), listener );
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IGenericModel#getListeners()
     */
    public EventListenerList getListeners()
    {
        return listenerList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.IGenericModel#removeListener(java.util.EventListener)
     */
    public void removeListener(
        ListenerType listener )
    {
        listenerList.remove( getListenerClass(), listener );
    }

    /**
     * Return the listener class the implementation class is bound to
     * 
     * @return
     */
    protected abstract Class<ListenerType> getListenerClass();
}
