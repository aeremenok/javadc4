/*
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

// $Id: HubList.java,v 1.24 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.net.hub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.javadc.interfaces.IHubInfo;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.ITask;
import net.sf.javadc.interfaces.ITaskManager;
import net.sf.javadc.util.PerformanceContext;
import net.sf.javadc.util.SafeParser;
import net.sf.javadc.util.bzip2.CBZip2InputStream;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * <code>HubList</code> represents a list of public hubs, which can be
 * downloaded via HTTP based URL.
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.24 $ $Date: 2005/10/02 11:42:28 $
 */
public class HubList extends AbstractHubList implements Runnable {

    private final static Category logger = Logger.getLogger(HubList.class);

    /**
     * 
     */
    private static final String defaultCharset = "ISO-8859-1";

    // component dependencies
    /**
     * 
     */
    private final ISettings settings;

    /**
     * 
     */
    private final ITaskManager taskManager;

    // private final HubFactory hubFactory;

    /**
     * creates a <CODE>HubList</CODE> with the given <CODE>ISettings</CODE>
     * and <CODE>ITaskManager</CODE>
     */
    public HubList(ISettings _settings, ITaskManager _taskManager) {

        if (_settings == null)
            throw new NullPointerException("_settings was null.");
        else if (_taskManager == null)
            throw new NullPointerException("_taskManager was null.");

        settings = _settings;
        taskManager = _taskManager;

        new Thread(this, "HubList").start();

    }

    /** ********************************************************************** */

    /**
     * Create the hub list
     * 
     * @return
     * @throws IOException
     */
    protected List createHubList() throws IOException {
        // returns a copy of the string, with leading and trailing whitespace
        // omitted.

        String hubListAddress = null;

        try {
            hubListAddress = settings.getAdvancedSettings().getHublistAddress()
                    .trim();

        } catch (NullPointerException e) {
            logger.error("Caught " + e.getClass().getName(), e);
            return new ArrayList();
        }

        boolean success = true;

        URL url = new URL(hubListAddress);

        InputStream inputStream = (InputStream) url.getContent();

        // changed 01.06.2004 by Timo Westk�mper
        // bzip2 compressed hub list
        if (hubListAddress.endsWith(".bz2")) {
            // decompressed the original stream via a Decorator
            try {
                // read file header 'BZ' from input stream
                if (readFileHeader(inputStream)) {
                    inputStream = new CBZip2InputStream(inputStream);

                } else { // file header was wrong
                    success = false;

                }

                // error in initialization of CBZip2InputStream
            } catch (NullPointerException e) {
                logger.error("Caught NullPointerException in createHubList()",
                        e);
                // logger.error(e);
                success = false;

            } catch (Exception e) {
                logger.error("Caught " + e.getClass().getName()
                        + " in createHubList()", e);
                // logger.error(e);
                success = false;

            }

        }

        // changed 26.04.2004 by Timo Westk�mper
        List newHubInfos;

        try {
            // long start = System.currentTimeMillis();
            PerformanceContext cont = new PerformanceContext("Loading hublist")
                    .start();

            if (!success) { // if the connection succeeded
                newHubInfos = new ArrayList();

            } else if (hubListAddress.indexOf("xml") > -1) {
                newHubInfos = readHubInfosFromXml(inputStream);

            } else {
                newHubInfos = readHubInfos(inputStream);

            }

            System.out.println(cont.end());

            // long end = System.currentTimeMillis();
            // System.out.println("Loaded hublist in " + (end - start) + "
            // ms.");

        } finally {
            inputStream.close();

        }

        return newHubInfos;

    }

    /**
     * Read the hub list in XML format from the given <CODE>InputStream</CODE>
     * 
     * @param inputStream
     * @return List of HubInfo instances created from the hublist
     * @throws IOException
     */
    protected List readHubInfosFromXml(InputStream inputStream)
            throws IOException {
        final List newHubInfos = new ArrayList();

        XMLReader reader = null;

        try {
            reader = XMLReaderFactory.createXMLReader();

        } catch (SAXException e) {
            logger.error("Caught " + e.getClass().getName(), e);
            Class cl = null;

            try {
                cl = Class.forName("org.apache.crimson.parser.XMLReaderImpl");
            } catch (ClassNotFoundException e1) {
                logger.error("Caught " + e1.getClass().getName(), e1);
            }

            try {
                reader = (XMLReader) cl.newInstance();
            } catch (InstantiationException e1) {
                logger.error("Caught " + e1.getClass().getName(), e1);

            } catch (IllegalAccessException e1) {
                logger.error("Caught " + e1.getClass().getName(), e1);
            }

        }

        ContentHandler contentHandler = new DefaultHandler() {

            private List columns = new ArrayList();

            public void startElement(String namespaceURI, String localName,
                    String qName, Attributes atts) throws SAXException {

                // only handle Hub elements
                if (localName.equals("Hub")) {
                    handleHubElement(atts);

                } else if (localName.equals("Column")) {
                    columns.add(atts.getValue("Name"));
                }
            }

            private void handleHubElement(Attributes atts) {
                IHubInfo hubInfo = new HubInfo();

                try {
                    if (columns.contains("Name")) {
                        hubInfo.setName(atts.getValue("Name"));
                    }

                    // mandatory elements
                    hubInfo.setAddress(new HostInfo(atts.getValue("Address")
                            + ":" + atts.getValue("Port")));

                    // String values

                    if (columns.contains("Description")) {
                        // hubInfo.setDescription(atts.getValue("Description"));

                        if (atts.getValue("Description").startsWith("<R")) {
                            int i = atts.getValue("Description").indexOf(">");
                            hubInfo.setDescription(atts.getValue("Description")
                                    .substring(i + 1));
                        } else {
                            hubInfo
                                    .setDescription(atts
                                            .getValue("Description"));
                        }

                    }

                    if (columns.contains("Country"))
                        hubInfo.setCountry(atts.getValue("Country"));

                    if (columns.contains("Status"))
                        hubInfo.setStatus(atts.getValue("Status"));

                    // int values

                    if (columns.contains("Users")
                            && atts.getValue("Users") != null)
                        hubInfo.setUserCount(Integer.parseInt(atts
                                .getValue("Users")));

                    if (columns.contains("Minshare")
                            && atts.getValue("Minshare") != null)
                        hubInfo.setMinshare(Long.parseLong(atts
                                .getValue("Minshare")));

                    if (columns.contains("Minslots")
                            && atts.getValue("Minslots") != null)
                        hubInfo.setMinslots(Integer.parseInt(atts
                                .getValue("Minslots")));

                    if (columns.contains("Maxhubs")
                            && atts.getValue("Maxhubs") != null)
                        hubInfo.setMaxhubs(Integer.parseInt(atts
                                .getValue("Maxhubs")));

                    if (columns.contains("Maxusers")
                            && atts.getValue("Maxusers") != null)
                        hubInfo.setMaxusers(Integer.parseInt(atts
                                .getValue("Maxusers")));

                } catch (Exception e) {
                    logger.error("Caught " + e.getClass().getName(), e);

                    String[] names = { "Name", "Address", "Port",
                            "Description", "Users" };

                    for (int i = 0; i < names.length; i++) {
                        System.out.println(names[i] + " : "
                                + atts.getValue(names[i]));
                    }

                }

                newHubInfos.add(hubInfo);

            }

        };

        reader.setContentHandler(contentHandler);

        // logging messages
        reader.setErrorHandler(new MyErrorHandler());

        try {
            // use a SafeInputStream to avoid errors
            reader.parse(new InputSource(new SafeInputStream(inputStream)));

            // use a SafeInputStreamReader to avoid errors
            // reader.parse(new InputSource(new
            // SafeInputStreamReader(inputStream)));

        } catch (IOException e1) {
            // e1.printStackTrace();
            logger.error("Caught " + e1.getClass().getName(), e1);
        } catch (SAXException e1) {
            // e1.printStackTrace();
            logger.error("Caught " + e1.getClass().getName(), e1);
        }

        return newHubInfos;
    }

    /**
     * Read the hub list from the given <CODE>InputStream</CODE>
     */
    protected List readHubInfos(InputStream inputStream) throws IOException {
        List newHubInfos = new ArrayList();

        // InputStreamReader instance used the default charset

        BufferedReader indata = new BufferedReader(new InputStreamReader(
                inputStream, defaultCharset));

        String line;

        while ((line = indata.readLine()) != null) {
            try {
                String[] tokens = line.split("\\|");

                String name = tokens[0];
                String host = tokens[1].trim();
                String description = tokens[2];

                int userCount = SafeParser.parseInt(tokens[3], 0);

                // add hub if host length is greater than zero
                if (host.length() > 0) {
                    IHubInfo hubInfo = new HubInfo();

                    hubInfo.setName(name);

                    if (description.startsWith("<R")) {
                        int i = description.indexOf(">");
                        hubInfo.setDescription(description.substring(i + 1));
                    } else {
                        hubInfo.setDescription(description);
                    }

                    // hubInfo.setDescription(description);

                    hubInfo.setUserCount(userCount);
                    hubInfo.setAddress(new HostInfo(host));

                    // newHubs.put(hostInfo, hub);
                    newHubInfos.add(hubInfo);

                }

            } catch (ArrayIndexOutOfBoundsException e) {

                // logger.error();
            }

        }

        return newHubInfos;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public final void run() {
        // changed 26.04.2004 by Timo Westk�mper
        while (true) {

            if (update) {
                logger.debug("Updating HubList.");

                List newHubInfos;

                try {
                    newHubInfos = createHubList();

                } catch (IOException io) {
                    // logger.error(io.toString());
                    logger.error("Error connecting to URL "
                            + settings.getAdvancedSettings()
                                    .getHublistAddress(), io);
                    // logger.error(io);

                    newHubInfos = new ArrayList();
                }

                synchronized (this) {
                    hubInfos = newHubInfos;
                    logger.debug("HubList updated.");

                }

                taskManager.addEvent(new ITask() {

                    public void runTask() {
                        fireHubListChanged();

                    }

                });

                update = false;

            }

            // TODO: Semaphore wait
            try {
                Thread.sleep(100);

            } catch (Exception e) {
                logger.error("Caught " + e.getClass().getName(), e);

            }

        }

    }

    /**
     * reads the 'BZ' file header from the bzip compressed file and returns true
     * if the correct file header was found and false if not
     */
    private boolean readFileHeader(InputStream bis) throws IOException {

        try {
            int b = bis.read();

            if (b != 'B') {
                return false;

            }

            b = bis.read();

            if (b != 'Z') {
                return false;

            }

            return true;

        } catch (IOException io) {
            bis.close();

        }

        return false;

    }

    private class MyErrorHandler implements ErrorHandler {

        /*
         * (non-Javadoc)
         * 
         * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
         */
        public void error(SAXParseException arg0) throws SAXException {
            logger.error("Caught " + arg0.getClass().getName(), arg0);

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
         */
        public void fatalError(SAXParseException arg0) throws SAXException {
            logger.error("Caught " + arg0.getClass().getName(), arg0);

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
         */
        public void warning(SAXParseException arg0) throws SAXException {
            logger.error("Caught " + arg0.getClass().getName(), arg0);

        }

    }

}

/*******************************************************************************
 * $Log: HubList.java,v $
 * Revision 1.24  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.23 2005/09/30 15:59:53 timowest updated
 * sources and tests
 * 
 * Revision 1.22 2005/09/26 17:19:52 timowest updated sources and tests
 * 
 * Revision 1.21 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
