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

package net.sf.javadc.tasks.client;

/**
 * @author Timo Westk�mper
 */
public class ClientTaskException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 58051011102740666L;

    /**
     * Create a new ClientTaskException instance
     */
    public ClientTaskException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Create a new ClientTaskException instance
     * 
     * @param arg0
     */
    public ClientTaskException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * Create a new ClientTaskException instance
     * 
     * @param arg0
     */
    public ClientTaskException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * Create a new ClientTaskException instance
     * 
     * @param arg0
     * @param arg1
     */
    public ClientTaskException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

}
