/*
 * Copyright (C) 2001 Michael Kurz mkurz@epost.de
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

// $Id:
package net.sf.javadc.net;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>Features</code> lists all supported features by the protocol
 * specification. This is currently only used by DC++. so i hope it will work
 * with arnes implementation.
 * 
 * @author Michael Kurz
 * @version $Revision:
 */
public class Features {

    // private final static String ENDTOKEN = "|";

    // private final static String[] features = new String[] { "BZList" };

    private final static String[] features = new String[] { "BZList",
            "GetZBlock", "MiniSlots", "XmlBZList", "ADCGet", "ZLig", "TTHL",
            "TTHF" };

    private Features() {

    }

    /** ********************************************************************** */

    // private final static String SUPPORTCOMMAND = "$Supports ";
    /**
     * Return the list of features;
     */
    public static final String[] getFeatures() {
        return features;

    }

    /**
     * Return the number of features implemented in the client.
     */
    public static final int getFeatureSize() {
        return features.length;

    }

    /**
     * Return an String containg the supported commands.
     */
    public static final List getSupportCommands() {
        List commands = new ArrayList();

        for (int i = 0; i < features.length; i++) {
            commands.add(features[i]);

        }

        return commands;

    }

}

/*******************************************************************************
 * $Log: Features.java,v $
 * Revision 1.9  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.8 2005/09/12 21:12:02 timowest added log
 * block
 * 
 * 
 */
