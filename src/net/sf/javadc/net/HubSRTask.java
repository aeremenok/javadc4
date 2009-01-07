/*
 * Copyright 2005 by Timo Westkämper $Id $Revision
 */
package net.sf.javadc.net;

import net.sf.javadc.interfaces.IHub;
import net.sf.javadc.interfaces.IHubManager;
import net.sf.javadc.interfaces.IHubTask;
import net.sf.javadc.interfaces.IHubTaskFactory;
import net.sf.javadc.interfaces.ITask;

import org.apache.log4j.Category;

/**
 * @author twe
 */
public class HubSRTask
    implements
        ITask
{
    private static final Category logger = Category.getInstance( HubSRTask.class );

    /**
     * 
     */
    private final String          result;

    /**
     * 
     */
    private String                hubIP;

    // external components

    /**
     * 
     */
    private final IHubManager     hubManager;

    /**
     * 
     */
    private final IHubTaskFactory hubTaskFactory;

    /**
     * Create a HubSRTask instance with the given IHubManager, IHubTaskFactory, search result and hub IP
     * 
     * @param _hubManager IHubManager instance to be used
     * @param _hubTaskFactory IHubTaskFactory instance to be used
     * @param _result search result to be used
     * @param _hubIP hub ip to be used
     */
    public HubSRTask(
        IHubManager _hubManager,
        IHubTaskFactory _hubTaskFactory,
        String _result,
        String _hubIP )
    {

        if ( _hubManager == null )
        {
            throw new NullPointerException( "hubManager was null." );
        }

        if ( _hubTaskFactory == null )
        {
            throw new NullPointerException( "hubTaskFactory was null." );
        }

        if ( _result == null )
        {
            throw new NullPointerException( "result was null." );
        }

        if ( _hubIP == null )
        {
            throw new NullPointerException( "hubIP was null." );
        }

        hubManager = _hubManager;
        hubTaskFactory = _hubTaskFactory;
        result = _result;
        hubIP = _hubIP;

        // strip the port information off
        if ( hubIP.contains( ":" ) )
        {
            hubIP = hubIP.substring( 0, hubIP.lastIndexOf( ":" ) );
        }

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ITask#runTask()
     */
    public void runTask()
    {
        IHub hub = hubManager.getHubWithIP( hubIP );

        if ( hub != null )
        {
            IHubTask task = null;

            try
            {
                task = (IHubTask) hubTaskFactory.borrowObject( "ISR" );

            }
            catch ( Exception e )
            {
                String error = "Caught " + e.getClass().getName();
                logger.error( error, e );
                throw new RuntimeException( error, e );
            }

            if ( task != null )
            {
                task.setHub( hub );
                task.setCmdData( result.substring( 4 ) );
                task.runTask();

                try
                {
                    hubTaskFactory.returnObject( "ISR", task );

                }
                catch ( Exception e )
                {
                    String error = "Caught " + e.getClass().getName();
                    logger.error( error, e );
                    throw new RuntimeException( error, e );
                }
            }
            else
            {
                logger.error( "Task ISRTask could not be obtained" );
            }

        }
        else
        {
            logger.error( "Hub with IP " + hubIP + " was not found." );

        }

    }

}

/*******************************************************************************
 * $Log$ Revision 1.5 2005/10/02 11:42:27 timowest updated sources and tests Revision 1.4 2005/09/30 15:59:53 timowest
 * updated sources and tests Revision 1.3 2005/09/15 17:31:54 timowest updated HubSRTask Revision 1.2 2005/09/12
 * 21:12:02 timowest added log block
 */
