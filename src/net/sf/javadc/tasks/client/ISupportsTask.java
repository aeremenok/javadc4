/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2002 Michael Kurz, mkurz@epost.de Copyright (C)
 * 2004 Timo Westkämper This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307,
 * USA.
 */

package net.sf.javadc.tasks.client;

import net.sf.javadc.tasks.BaseClientTask;

/**
 * <p>
 * Feature: Supports
 * </p>
 * <p>
 * Purpose: To identify which features a certain client supports
 * </p>
 * <p>
 * Usage: "$Supports &lt;feature1&gt; &lt;feature2&gt; ... &lt;featureN&gt;|" &lt;featurename&gt; = Case sensitive
 * string describing the feature that a certain client supports. This should be the name of the command being sent, i e
 * if a client supports a command "$GetBZListLen|", featurename should be GetBZListLen, unless of course, this is not a
 * feature that requires sending extra data. Feature names may (obviously) not contain spaces. Empty $Supports should
 * not be sent.
 * </p>
 * 
 * @author tw70794
 */
public class ISupportsTask
    extends BaseClientTask
{
    // private static final Category logger = Category
    // .getInstance(ISupportsTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.BaseClientTask#runTaskTemplate()
     */
    @Override
    protected final void runTaskTemplate()
    {
        // TODO : this can be extended

        if ( cmdData.indexOf( "BZList" ) != -1 )
        {
            clientConnection.getClient().setBZSupport( true );

        }

    }

}