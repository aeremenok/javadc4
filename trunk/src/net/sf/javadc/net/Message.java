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

// $Id: Message.java,v 1.13 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.net;

/**
 * <code>Message</code> represents a single message posted to a
 * <code>Hub</code>
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.13 $ $Date: 2005/10/02 11:42:27 $
 */
public class Message {

    /**
     * 
     */
    private final String from;

    /**
     * 
     */
    private final String to;

    /**
     * 
     */
    private final String text;

    /**
     * 
     */
    private final long time;

    /**
     * Create a new <CODE>Message</CODE> with the given parameters
     */
    public Message(String _from, String _to, String _text) {
        if (_from == null)
            throw new NullPointerException("_from was null.");

        if (_to == null)
            throw new NullPointerException("_to was null.");

        if (_text == null)
            throw new NullPointerException("_text was null.");

        from = _from;
        to = _to;
        text = deescape(_text);

        time = System.currentTimeMillis();

    }

    /** ********************************************************************** */

    /**
     * @param _text
     * @return
     */
    public static String deescape(String _text) {
        _text = _text.replaceAll("&#124;", "|");

        if (_text.length() > 4)
            return _text.replaceAll("&#36;", "\\$");
        else
            return _text;
    }

    /**
     * Get the sender property
     * 
     * @return
     */
    public final String getFrom() {
        return from;

    }

    /**
     * Get the receiver property
     * 
     * @return
     */
    public final String getTo() {
        return to;

    }

    /**
     * Get the message body
     * 
     * @return
     */
    public final String getText() {
        return text;

    }

    /**
     * Get the time in milliseconds when this Message object was created
     * 
     * @return
     */
    public final long getTime() {
        return time;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        StringBuffer str = new StringBuffer();

        str.append("Message from:").append(getFrom());
        str.append(" to: ").append(getTo());
        str.append(" Message:").append(getText());

        return str.toString();

    }

}

/*******************************************************************************
 * $Log: Message.java,v $
 * Revision 1.13  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.12 2005/09/30 15:59:53 timowest updated
 * sources and tests
 * 
 * Revision 1.11 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */
