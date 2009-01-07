/*
 * Created on 12.6.2005
 */
package net.sf.javadc.net.hub;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Timo Westkämper
 */
public class SafeInputStream
    extends InputStream
{

    // private final static Category logger =
    // Logger.getLogger(SafeInputStream.class);

    /**
     * 
     */
    private final InputStream is;

    /**
     * Create a new SafeInputStream instance
     * 
     * @param is
     */
    public SafeInputStream(
        InputStream is )
    {
        if ( is == null )
        {
            throw new NullPointerException( "is was null." );
        }

        this.is = is;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.InputStream#available()
     */
    @Override
    public int available()
        throws IOException
    {
        // logger.debug("available()");
        return is.available();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.InputStream#close()
     */
    @Override
    public void close()
        throws IOException
    {
        // logger.debug("close()");
        is.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.InputStream#mark(int)
     */
    @Override
    public void mark(
        int readlimit )
    {
        // logger.debug("mark(int readLimit)");
        is.mark( readlimit );

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.InputStream#markSupported()
     */
    @Override
    public boolean markSupported()
    {
        // logger.debug("markSupported()");
        return is.markSupported();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.InputStream#read()
     */
    @Override
    public int read()
        throws IOException
    {
        // logger.debug("read()");

        // try{
        return transform( is.read() );
        // }catch(Exception e) { error(e); }
        //        
        // return 32;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.InputStream#read(byte[])
     */
    @Override
    public int read(
        byte[] b )
        throws IOException
    {
        // logger.debug("read(byte[] b)");

        int read = -1;
        // try{
        read = is.read( b );
        // }catch(Exception e) { error(e); }

        for ( int i = 0; i < b.length; i++ )
        {
            b[i] = (byte) transform( b[i] );
        }

        // return (read != -1) ? read : b.length;
        return read;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.InputStream#read(byte[], int, int)
     */
    @Override
    public int read(
        byte[] b,
        int off,
        int len )
        throws IOException
    {
        // logger.debug("read(byte[], int off, int len)");
        int read = -1;

        // try{
        read = is.read( b, off, len );
        // }catch(Exception e) { error(e); }

        for ( int i = off; i < off + len; i++ )
        {
            b[i] = (byte) transform( b[i] );
        }

        // return (read != -1) ? read : len;
        return read;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.InputStream#reset()
     */
    @Override
    public void reset()
        throws IOException
    {
        // logger.debug("reset()");
        is.reset();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.InputStream#skip(long)
     */
    @Override
    public long skip(
        long n )
        throws IOException
    {
        // logger.debug("skip(long n)");
        return is.skip( n );
    }

    /**
     * Log the given Exception instance
     * 
     * @param e
     */
    // private void error(Exception e){
    // logger.error("Caught " + e.getClass().getName(), e);
    // }
    /**
     * @param ch
     * @return
     */
    protected int transform(
        int ch )
    {
        if ( ch < 32 )
        {
            return 32;
        }
        else if ( ch > 127 && ch < 160 )
        {
            return 32;
        }
        else
        {
            return ch;
        }

    }

}

/*******************************************************************************
 * $Log: SafeInputStream.java,v $ Revision 1.8 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.7
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.6 2005/09/26 17:19:52 timowest updated sources and
 * tests Revision 1.5 2005/09/12 21:12:02 timowest added log block
 */
