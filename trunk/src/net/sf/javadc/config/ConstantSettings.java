/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: ConstantSettings.java,v 1.15 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.config;

/**
 * <CODE>ConstantSettings</CODE> represents constant application settings
 */
public class ConstantSettings
{

    /** File name of the File List */
    public final static String BROWSELIST                      = "MyList.DcLst";

    /** File name of the bz2 compressed File List */
    public final static String BROWSELIST_ZIP                  = "MyList.bz2";

    // public final static String HASH_INDEX_FILENAME = "hashedfilelist.xml";
    /**
     * 
     */
    public final static String HASH_INDEX_FILENAME             = "hashedfilelist";

    /** End character of a DirectConnect command */
    public final static char   COMMAND_END_CHAR                = '|';
    /** Separator char of a DirectConnect command */
    public final static char   COMMAND_SEP_CHAR                = ' ';

    /**
     * 
     */
    public final static String COMMAND_SEP                     = String.valueOf( COMMAND_SEP_CHAR );

    /** Path Separator for DirectConnect file lists */
    public final static char   DC_PATH_SEPARATOR               = '\\';

    /**
     * 
     */
    public final static String DOWNLOAD_DIRECTION              = "Download";

    /**
     * 
     */
    public final static String UPLOAD_DIRECTION                = "Upload";

    /**
     * 
     */
    public final static char   SEARCHRESULT_SEP_CHAR           = (char) 5;

    // GUI related

    /**
     * 
     */
    public final static String MAINFRAME_TITLE                 = "javadc3 - DirectConnect Java Client";

    /**
     * 
     */
    public final static int    STATISTICS_UPDATE_INTERVAL      = 1 * 1000;

    /**
     * 
     */
    public final static int    SEARCHRESULTS_UPDATEINTERVAL    = 1 * 1000;

    /**
     * 
     */
    public final static int    MANAGERCOMPONENT_UPDATEINTERVAL = 1 * 1000;

    /** Sleeping time for the main loop */
    public final static int    MAIN_THREADSLEEP_TIME           = 10000;

    /**
     * Strings used to query for Task classes which are related to Hub actions
     */
    public final static String HUBTASKFACTORY_PREFIX           = "net.sf.javadc.tasks.hub.";

    /**
     * 
     */
    public final static String HUBTASKFACTORY_POSTFIX          = "Task";

    /**
     * Strings used to query for Task classes which are related to Client actions
     */
    public final static String CLIENTTASKFACTORY_PREFIX        = "net.sf.javadc.tasks.client.";

    /**
     * 
     */
    public final static String CLIENTTASKFACTORY_POSTFIX       = "Task";

    /** represents the Default Port for Hub connections */
    public final static int    DEFAULT_PORT                    = 411;

    /** maximum of search results that are returned for one request */
    public final static int    MAX_SEARCH_RESULTS              = 15;

}

/*******************************************************************************
 * $Log: ConstantSettings.java,v $ Revision 1.15 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.14
 * 2005/09/30 15:59:52 timowest updated sources and tests Revision 1.13 2005/09/14 07:11:48 timowest updated sources
 */
