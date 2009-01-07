/*
 * Copyright (C) 2001 Ryan Sweny, cabin@geeky.net Copyright (C) 2004 Timo Westkämper This program is free software; you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

// $Id: UserCellRenderer.java,v 1.15 2005/10/02 11:42:27 timowest Exp $
package net.sf.javadc.gui.util;

import java.awt.Component;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.javadc.gui.model.RowTableModel;
import net.sf.javadc.net.SearchResult;
import net.sf.javadc.net.hub.HubUser;

/**
 * <CODE>UserCellRenderer</CODE> is an extension to the <CODE>DefaultTableCellRenderer</CODE> which renders column
 * contents of <CODE>HubUser</CODE>
 * 
 * @author Ryan Sweny
 * @version $Revision: 1.15 $ $Date: 2005/10/02 11:42:27 $
 */
public class UserCellRenderer
    extends DefaultTableCellRenderer
{
    /**
     *  
     */
    private static final long serialVersionUID = 7085360865219997773L;

    private final ImageIcon[] bufferedImages;

    private final List        opList;

    /**
     * Create a UserCellRenderer
     * 
     * @param i array of ImageIcons
     * @param a List of operators
     */
    public UserCellRenderer(
        ImageIcon[] i,
        List a )
    {
        bufferedImages = i;
        opList = a;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    @Override
    public final Component getTableCellRendererComponent(
        JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column )
    {
        JLabel result = (JLabel) super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );

        String speed = ((String) table.getModel().getValueAt( row, 2 )).toLowerCase();

        result.setHorizontalAlignment( JLabel.LEFT );

        RowTableModel model = (RowTableModel) ((TableSorter) table.getModel()).getModel();

        Object o = model.getRow( row );
        HubUser user;

        if ( o instanceof SearchResult )
        {
            user = ((SearchResult) o).getUser();

        }
        else if ( o instanceof HubUser )
        {
            user = (HubUser) o;

        }
        else
        {
            throw new RuntimeException( "Encountered unexpected instance of " + o.getClass().getName() );
        }

        String code = "";

        try
        {
            code = "" + user.getSpeedCode();

        }
        catch ( Exception e )
        {
            // todo ?
        }

        // standard speed icons
        if ( code.compareTo( "3" ) == 0 ) // shade icon
        {
            if ( speed.indexOf( "kbp" ) != -1 )
            {
                result.setIcon( bufferedImages[8] );

            }
            else if ( speed.indexOf( "satellit" ) != -1 )
            {
                result.setIcon( bufferedImages[9] );

            }
            else if ( speed.indexOf( "d" ) != -1 )
            {
                result.setIcon( bufferedImages[10] );

            }
            else if ( speed.indexOf( "cabl" ) != -1 )
            {
                result.setIcon( bufferedImages[11] );

            }
            else
            {
                result.setIcon( bufferedImages[12] );

            }

        }
        else
        // normal icon
        {
            if ( speed.indexOf( "kbp" ) != -1 )
            {
                result.setIcon( bufferedImages[0] );

            }
            else if ( speed.indexOf( "satellit" ) != -1 )
            {
                result.setIcon( bufferedImages[1] );

            }
            else if ( speed.indexOf( "d" ) != -1 )
            {
                result.setIcon( bufferedImages[2] );

            }
            else if ( speed.indexOf( "cabl" ) != -1 )
            {
                result.setIcon( bufferedImages[3] );

            }
            else
            {
                result.setIcon( bufferedImages[4] );

            }

        }

        // special icoons
        if ( code.compareTo( "11" ) == 0 || code.compareTo( "15" ) == 0 )
        {
            result.setIcon( bufferedImages[14] ); // fireball away

        }
        else if ( code.compareTo( "13" ) == 0 )
        {
            result.setIcon( bufferedImages[6] ); // fireball here

        }
        else if ( code.compareTo( "5" ) == 0 )
        {
            result.setIcon( bufferedImages[5] ); // server here

        }
        else if ( code.compareTo( "7" ) == 0 )
        {
            result.setIcon( bufferedImages[13] ); // server away

        }

        // op icon
        String nick = (String) value;

        try
        {
            if ( opList.contains( nick ) )
            {
                if ( code.compareTo( "3" ) == 0 )
                {
                    result.setIcon( bufferedImages[15] );

                }
                else
                {
                    result.setIcon( bufferedImages[7] );

                }

            }

        }
        catch ( NullPointerException e )
        {

            // ?
        }

        setText( nick );

        return this;

    }

}

/*******************************************************************************
 * $Log: UserCellRenderer.java,v $ Revision 1.15 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.14
 * 2005/09/25 16:40:58 timowest updated sources and tests Revision 1.13 2005/09/14 07:11:49 timowest updated sources
 */
