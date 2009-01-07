/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
// $Id: MessageComponent.java,v 1.20 2005/10/02 11:42:28 timowest Exp $
package net.sf.javadc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.sf.javadc.gui.model.SortableTableListener;
import net.sf.javadc.interfaces.ISettings;
import net.sf.javadc.interfaces.IUserInfo;
import net.sf.javadc.net.Message;

import org.apache.log4j.Category;

/**
 * <CODE>MessageComponent</CODE> provides a view on the messages being posted to a <CODE>Hub</CODE>, the local client is
 * connected to. Posting messages is also possible via this component
 * 
 * @author Jesper Nordenberg
 * @version $Revision: 1.20 $ $Date: 2005/10/02 11:42:28 $
 */
public class MessageComponent
    extends JPanel
    implements
        SortableTableListener
{

    /**
     *  
     */
    private static final long     serialVersionUID = 1149842645679402961L;

    private final static Category logger           = Category.getInstance( MessageComponent.class );

    /**
     * 
     */
    private final DateFormat      dateFormat       = new SimpleDateFormat( "HH:mm" );

    /**
     * 
     */
    private boolean               firstMessage     = true;

    /**
     * 
     */
    private final String          from;

    /**
     * 
     */
    private final HubComponent    hubComponent;

    /**
     * 
     */
    private final JTextField      inputField       = new JTextField();

    /**
     * 
     */
    private final JTextPane       messages         = new JTextPane();

    /**
     * 
     */
    private final JScrollPane     scrollPane       = new JScrollPane( messages );

    /**
     * 
     */
    private final boolean         showFromColumn;

    // private final Settings settings;
    /**
     * 
     */
    private final IUserInfo       user;

    /**
     * Create a MessageComponent instance
     * 
     * @param hc HubComponent to be used
     * @param _showFromColumn whether the from information is shown
     * @param _from
     * @param settings ISettings instance to be used
     */
    public MessageComponent(
        HubComponent hc,
        boolean _showFromColumn,
        String _from,
        ISettings settings )
    {
        super( new BorderLayout() );

        if ( hc == null )
        {
            throw new NullPointerException( "hc was null" );
        }

        if ( settings == null )
        {
            throw new NullPointerException( "settings was null" );
        }

        hubComponent = hc;
        from = _from;

        showFromColumn = _showFromColumn;
        user = settings.getUserInfo();

        messages.setEditable( false );

        // messages.setMinimumSize(new Dimension(0, 0));
        inputField.addActionListener( new ActionListener()
        {

            public void actionPerformed(
                ActionEvent e )
            {
                onInputField();

            }

        } );

        add( scrollPane, BorderLayout.CENTER );
        add( inputField, BorderLayout.SOUTH );

        setMinimumSize( new Dimension( 200, 0 ) );

    }

    /** ********************************************************************** */

    /**
     * Add the given Message to the list of messages
     * 
     * @param message Message instance to be added
     */
    public final void addMessage(
        Message message )
    {
        if ( !hubComponent.isActive() )
        {
            logger.debug( "HubComponent is not active anymore. Adding of Message aborted." );
            return;

        }

        try
        {
            SimpleAttributeSet set = new SimpleAttributeSet();

            StyleConstants.setBold( set, true );

            Document doc = messages.getDocument();

            doc.insertString( doc.getLength(), (firstMessage ? "" : "\n") +
                dateFormat.format( new Date( message.getTime() ) ) + " " +
                (showFromColumn ? "<" + message.getFrom() + "> " : ""), set );

            doc.insertString( doc.getLength(), message.getText(), null );
            firstMessage = false;

            JViewport p = scrollPane.getViewport();

            p.setViewPosition( new Point( (int) p.getViewPosition().getX(), (int) (p.getView().getHeight() - p
                .getViewSize().getHeight()) ) );

        }
        catch ( Exception e )
        {
            logger.error( e.toString() );

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.SortableTableListener#cellSelected(int, int)
     */
    public final void cellSelected(
        int row,
        int column,
        int[] selectedColumn )
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.gui.model.SortableTableListener#showPopupClicked(int,
     *      int, java.awt.event.MouseEvent)
     */
    public final void showPopupClicked(
        int row,
        int column,
        MouseEvent e,
        int[] selectedRows )
    {

        // ?
    }

    /**
     * Handler for the input field which sends the typed message to the Hub in general or client specific mode
     */
    private final void onInputField()
    {
        if ( !hubComponent.isActive() )
        {
            logger.debug( "HubComponent is not active anymore. Sending of Message aborted." );
            return;

        }

        try
        {
            // send the message as a general message
            if ( from == null )
            {
                hubComponent.getHub().sendChatMessage( inputField.getText() );

                // send the message to a specific remote client
            }
            else
            {
                hubComponent.getHub().sendPrivateMessage( inputField.getText(), from );

                String nick = user.getNick();

                Message m = new Message( nick, from, "<" + nick + "> " + inputField.getText() );

                addMessage( m );

            }

            inputField.setText( "" );

        }
        catch ( IOException e )
        {
            logger.error( e.toString() );

        }

    }

}

/*******************************************************************************
 * $Log: MessageComponent.java,v $ Revision 1.20 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.19
 * 2005/09/26 17:53:12 timowest added null checks Revision 1.18 2005/09/25 16:40:58 timowest updated sources and tests
 * Revision 1.17 2005/09/14 07:11:49 timowest updated sources
 */
