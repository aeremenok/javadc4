/*
 * Copyright (C) 2001 Jesper Nordenberg, mayhem@home.se Copyright (C) 2004 Timo Westkämper This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FIT- NESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package net.sf.javadc.net.client;

/**
 * <code>ConnectionInfo</code> is an internal helper class of <code>Connection</code>. It contains basic data about the
 * related <code>Connection</code> instance.
 * 
 * @author Timo Westk�mper
 */
public class ConnectionInfo
{
    /**
     * 
     */
    private boolean useCompression;

    /**
     * 
     */
    private long    bytesToUpload;

    /** 
     * 
     */
    private int     verifyResumeSize;

    /**
     * 
     */
    private String  currentDirection;

    /**
     * 
     */
    private String  remoteKey;

    /**
     * 
     */
    private String  info;

    /**
     * Create a new ConnectionInfo instance
     */
    public ConnectionInfo()
    {
        super();
        // TODO Auto-generated constructor stub

    }

    /** ********************************************************************** */

    /**
     * @return Returns the bytesToUpload.
     */
    public long getBytesToUpload()
    {
        return bytesToUpload;
    }

    /**
     * @return Returns the currentDirection.
     */
    public String getCurrentDirection()
    {
        return currentDirection;
    }

    /**
     * @return Returns the info.
     */
    public String getInfo()
    {
        return info;
    }

    /**
     * @return Returns the remoteKey.
     */
    public String getRemoteKey()
    {
        return remoteKey;
    }

    /**
     * @return Returns the verifyResumeSize.
     */
    public int getVerifyResumeSize()
    {
        return verifyResumeSize;
    }

    /**
     * @return Returns the useCompression.
     */
    public boolean isUseCompression()
    {
        return useCompression;
    }

    /**
     * @param bytesToUpload The bytesToUpload to set.
     */
    public void setBytesToUpload(
        long bytesToUpload )
    {
        this.bytesToUpload = bytesToUpload;
    }

    /**
     * @param currentDirection The currentDirection to set.
     */
    public void setCurrentDirection(
        String currentDirection )
    {
        this.currentDirection = currentDirection;
    }

    /**
     * @param info The info to set.
     */
    public void setInfo(
        String info )
    {
        this.info = info;
    }

    /**
     * @param remoteKey The remoteKey to set.
     */
    public void setRemoteKey(
        String remoteKey )
    {
        this.remoteKey = remoteKey;
    }

    /**
     * @param useCompression The useCompression to set.
     */
    public void setUseCompression(
        boolean useCompression )
    {
        this.useCompression = useCompression;
    }

    /**
     * @param verifyResumeSize The verifyResumeSize to set.
     */
    public void setVerifyResumeSize(
        int verifyResumeSize )
    {
        this.verifyResumeSize = verifyResumeSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuffer buffer = new StringBuffer( "Connection information : " );
        buffer.append( "\n  bytesToUpload    : " ).append( bytesToUpload );
        buffer.append( "\n  currentDirection : " ).append( currentDirection );
        buffer.append( "\n  info             : " ).append( info );
        buffer.append( "\n  remoteKey        : " ).append( remoteKey );
        buffer.append( "\n  useCompression   : " ).append( useCompression );
        buffer.append( "\n  verifyResumeSize : " ).append( verifyResumeSize );

        return buffer.toString();
    }
}

/*******************************************************************************
 * $Log: ConnectionInfo.java,v $ Revision 1.7 2005/10/02 11:42:28 timowest updated sources and tests Revision 1.6
 * 2005/09/30 15:59:53 timowest updated sources and tests Revision 1.5 2005/09/12 21:12:02 timowest added log block
 */
