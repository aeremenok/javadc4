package net.sf.javadc.gui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sf.javadc.Main;

public class TrayMenu
    extends PopupMenu
{
    public static void createTrayMenu()
    {
        if ( SystemTray.isSupported() )
        {
            Image image = Toolkit.getDefaultToolkit().getImage( "images/16/javadc.ico" );
            TrayIcon trayIcon = new TrayIcon( image, "Java DC", new TrayMenu() );

            trayIcon.addActionListener( new ActionListener()
            {
                public void actionPerformed(
                    ActionEvent e )
                {
                    getMainFrame().showhide();
                }
            } );

            try
            {
                SystemTray.getSystemTray().add( trayIcon );
            }
            catch ( AWTException e )
            {
                e.printStackTrace();
            }
        }
    }

    private static MainFrame getMainFrame()
    {
        return (MainFrame) Main.getGuiContainer().getComponentInstance( MainFrame.class );
    }

    public TrayMenu()
    {
        super();

        add( new SelfListeningMenuItem( "Show/Hide" )
        {
            @Override
            public void actionPerformed(
                ActionEvent e )
            {
                getMainFrame().showhide();
            }

        } );
        add( new SelfListeningMenuItem( "Settings" )
        {
            @Override
            public void actionPerformed(
                ActionEvent e )
            {
                getMainFrame().showSettings();
            }
        } );
        add( new SelfListeningMenuItem( "Exit" )
        {
            @Override
            public void actionPerformed(
                ActionEvent e )
            {
                Main.close();
            }
        } );
    }
}
