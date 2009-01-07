/*
 * Copyright (C) 2001 Michael Kurz mkurz@epost.de This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT- NESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General
 * Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA.
 */
// $Id:
package net.sf.javadc.util.bzip2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The BZip main class.
 * 
 * @created 20. Mai 2002
 */
public final class BZip
{
    /**
     * Description of the Field
     */
    public final static ProgressListener dummyProg = new ProgressListener()
                                                   {
                                                       public void setProgress(
                                                           long val )
                                                       {

                                                       }
                                                   };

    /**
     * Description of the Method
     * 
     * @param infile Description of the Parameter
     * @param outfile Description of the Parameter
     * @param blockSize Description of the Parameter
     */
    public final static void compress(
        File infile,
        File outfile,
        int blockSize )
    {

        compress( infile, outfile, blockSize, dummyProg );
    }

    /**
     * Description of the Method
     * 
     * @param infile Description of the Parameter
     * @param outfile Description of the Parameter
     * @param blockSize Description of the Parameter
     * @param prog Description of the Parameter
     */
    public final static void compress(
        File infile,
        File outfile,
        int blockSize,
        ProgressListener prog )
    {
        try
        {
            final FileOutputStream fos = new FileOutputStream( outfile );
            final FileInputStream fis = new FileInputStream( infile );

            compress( fis, fos, blockSize, prog );

        }
        catch ( IOException e )
        {
            e.printStackTrace();

        }
    }

    /**
     * Description of the Method
     * 
     * @param infile Description of the Parameter
     * @param outstream Description of the Parameter
     * @param blockSize Description of the Parameter
     * @param prog Description of the Parameter
     */
    public final static void compress(
        File infile,
        OutputStream outstream,
        int blockSize,
        ProgressListener prog )
    {
        try
        {
            final FileInputStream fis = new FileInputStream( infile );

            compress( fis, outstream, blockSize, prog );

        }
        catch ( IOException e )
        {
            e.printStackTrace();

        }
    }

    /**
     * Description of the Method
     * 
     * @param instream Description of the Parameter
     * @param outfile Description of the Parameter
     * @param blockSize Description of the Parameter
     * @param prog Description of the Parameter
     */
    public final static void compress(
        InputStream instream,
        File outfile,
        int blockSize,
        ProgressListener prog )
    {
        try
        {
            final FileOutputStream fos = new FileOutputStream( outfile );

            compress( instream, fos, blockSize, prog );

        }
        catch ( IOException e )
        {
            e.printStackTrace();

        }
    }

    /**
     * Use the CBZip2OutputStream to compress data.
     * 
     * @param instream Description of the Parameter
     * @param outstream Description of the Parameter
     * @param blockSize Description of the Parameter
     * @param prog Description of the Parameter
     */
    public final static void compress(
        InputStream instream,
        OutputStream outstream,
        int blockSize,
        ProgressListener prog )
    {

        try
        {
            final BufferedOutputStream bos = new BufferedOutputStream( outstream );

            bos.write( 'B' );
            bos.write( 'Z' );

            final BufferedInputStream bis = new BufferedInputStream( instream );
            final CBZip2OutputStream bzos = new CBZip2OutputStream( bos );

            int ch = bis.read();

            while ( ch != -1 )
            {
                bzos.write( ch );
                ch = bis.read();

            }

            bzos.close();
            bos.flush();
            bos.close();
            outstream.flush();
            outstream.close();

        }
        catch ( IOException e )
        {
            e.printStackTrace();

        }

        // System.gc();
    }

    /**
     * Description of the Method
     * 
     * @param infilename Description of the Parameter
     * @param outfilename Description of the Parameter
     * @param blockSize Description of the Parameter
     * @param prog Description of the Parameter
     */

    public final static void compress(
        String infilename,
        String outfilename,
        int blockSize,
        ProgressListener prog )
    {

        final File theinFile = new File( infilename );
        final File theoutFile = new File( outfilename );

        compress( theinFile, theoutFile, blockSize, prog );
    }

    /**
     * Decompresse the given inputfile into the given outputfile.
     * 
     * @param infile The <code>File</code> to decompress.
     * @param outfile The decompressed <code>File</code>.
     */
    public final static void decompress(
        File infile,
        File outfile )
    {
        decompress( infile, outfile, dummyProg );
    }

    /**
     * Decompresse the given inputfile into the given outputfile.
     * 
     * @param infile The <code>File</code> to decompress.
     * @param outfile The decompressed <code>File</code>.
     * @param prog The ProgressListener, that listens to the progress.
     */
    public final static void decompress(
        File infile,
        File outfile,
        ProgressListener prog )
    {

        try
        {
            final FileOutputStream fos = new FileOutputStream( outfile );
            final FileInputStream fis = new FileInputStream( infile );

            decompress( fis, fos, prog );

            fis.close();
            fos.close();

            // infile.close();
            // outfile.close();

        }
        catch ( IOException e )
        {
            e.printStackTrace();

        }
    }

    /**
     * Description of the Method
     * 
     * @param infile Description of the Parameter
     * @param outstream Description of the Parameter
     * @param prog Description of the Parameter
     */
    public final static void decompress(
        File infile,
        OutputStream outstream,
        ProgressListener prog )
    {

        try
        {
            final FileInputStream fis = new FileInputStream( infile );

            decompress( fis, outstream, prog );
            fis.close();
            // infile.close();

        }
        catch ( IOException e )
        {
            e.printStackTrace();

        }
    }

    /**
     * Description of the Method
     * 
     * @param instream Description of the Parameter
     * @param outfile Description of the Parameter
     * @param prog Description of the Parameter
     */
    public final static void decompress(
        InputStream instream,
        File outfile,
        ProgressListener prog )
    {

        try
        {
            final FileOutputStream fos = new FileOutputStream( outfile );

            decompress( instream, fos, prog );
            fos.close();
            // outfile.close();

        }
        catch ( IOException e )
        {
            e.printStackTrace();

        }
    }

    /**
     * Use the BZip2InputStream to uncompress data.
     * 
     * @param instream Description of the Parameter
     * @param outstream Description of the Parameter
     * @param prog Description of the Parameter
     */
    public final static void decompress(
        InputStream instream,
        OutputStream outstream,
        ProgressListener prog )
    {

        try
        {
            final BufferedOutputStream bos = new BufferedOutputStream( outstream );

            final BufferedInputStream bis = new BufferedInputStream( instream );

            int b = bis.read();

            if ( b != 'B' )
            {
                return;
            }

            b = bis.read();
            if ( b != 'Z' )
            {
                return;
            }

            final CBZip2InputStream bzis = new CBZip2InputStream( bis );
            int ch = bzis.read();

            while ( ch != -1 )
            {
                bos.write( ch );
                ch = bzis.read();
            }
            bos.flush();

        }
        catch ( IOException e )
        {
            e.printStackTrace();

        }
    }

    /**
     * Decompresse the given inputfile into the given outputfile.
     * 
     * @param infile A <code>String</code> containig the Name of the File to decompress.
     * @param outfile A <code>String</code> containig the Name of the decompressed File.
     * @param prog The ProgressListener, that listens to the progress.
     */
    public final static void decompress(
        String infilename,
        String outfilename,
        ProgressListener prog )
    {

        final File theinFile = new File( infilename );
        final File theoutFile = new File( outfilename );

        decompress( theinFile, theoutFile, dummyProg );
    }

    /**
     * A unit test for JUnit
     * 
     * @param infile Description of the Parameter
     * @return Description of the Return Value
     */
    public final static boolean test(
        File infile )
    {
        try
        {
            final FileInputStream fis = new FileInputStream( infile );
            final BufferedInputStream bis = new BufferedInputStream( fis );

            int b = bis.read();

            if ( b != 'B' )
            {
                System.out.println( "incorrect start characters" );
                return false;
            }

            b = bis.read();
            if ( b != 'Z' )
            {
                System.out.println( "incorrect start characters" );
                return false;
            }

            // final CBZip2Decompress cbd = new CBZip2Decompress(dummyProg);
            // return cbd.testStream(bis);

        }
        catch ( IOException e )
        {
            e.printStackTrace();

        }

        return false;
    }

    /**
     * A unit test for JUnit
     * 
     * @param infilename Description of the Parameter
     * @return Description of the Return Value
     */
    public final static boolean test(
        String infilename )
    {
        final File theinFile = new File( infilename );
        return test( theinFile );
    }
}
// $Id:
