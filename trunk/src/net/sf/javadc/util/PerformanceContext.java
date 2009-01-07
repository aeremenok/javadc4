/*
 * Copyright 2005 by Timo Westkämper $Id$ $Revision$
 */
package net.sf.javadc.util;

/**
 * @author twe
 */
public class PerformanceContext
{

    /**
     * 
     */
    private String msg;

    /**
     * 
     */
    private long   start;

    /**
     * 
     */
    private long   stop;

    /**
     * Create a new PerformanceContext instance
     * 
     * @param msg
     */
    public PerformanceContext(
        String msg )
    {
        this.msg = msg;
    }

    /**
     * Set the end time
     * 
     * @return
     */
    public final PerformanceContext end()
    {
        stop = System.currentTimeMillis();
        return this;
    }

    /**
     * Get the duration in milliseconds
     * 
     * @return
     */
    public final long getDuration()
    {
        return stop - start;
    }

    /**
     * Set the start time
     * 
     * @return
     */
    public final PerformanceContext start()
    {
        start = System.currentTimeMillis();
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString()
    {
        return msg + " [" + getDuration() + " ms]";
    }

}

/*******************************************************************************
 * $Log$ Revision 1.2 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.1 2005/09/14 07:11:48 timowest
 * updated sources
 */
