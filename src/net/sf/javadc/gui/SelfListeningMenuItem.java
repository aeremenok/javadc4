package net.sf.javadc.gui;

import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * menu item listens its own actions
 * 
 * @author eav
 */
public class SelfListeningMenuItem
    extends MenuItem
    implements
        ActionListener
{
    public SelfListeningMenuItem()
    {
        super();
        addActionListener( this );
    }

    public SelfListeningMenuItem(
        String label )
    {
        super( label );
        addActionListener( this );
    }

    public SelfListeningMenuItem(
        String label,
        MenuShortcut s )
    {
        super( label, s );
        addActionListener( this );
    }

    public void actionPerformed(
        ActionEvent e )
    {
    }
}
