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
package net.sf.javadc.net;

/**
 * @author Timo Westk�mper
 */
public class InvalidArgumentException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -928194359308275021L;

    /**
     * Create a new InvalidArgumentException instance
     */
    public InvalidArgumentException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Create a new InvalidArgumentException instance
     * 
     * @param arg0
     */
    public InvalidArgumentException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * Create a new InvalidArgumentException instance
     * 
     * @param arg0
     */
    public InvalidArgumentException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * Create a new InvalidArgumentException instance
     * 
     * @param arg0
     * @param arg1
     */
    public InvalidArgumentException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

}

/*******************************************************************************
 * $Log: InvalidArgumentException.java,v $
 * Revision 1.7  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.6 2005/09/26 17:19:52
 * timowest updated sources and tests
 * 
 * Revision 1.5 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
