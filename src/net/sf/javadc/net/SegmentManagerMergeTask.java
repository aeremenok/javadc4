package net.sf.javadc.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

import net.sf.javadc.interfaces.ITask;

import org.apache.log4j.Category;

/**
 * @author Timo Westkämper
 */
public class SegmentManagerMergeTask implements ITask {

    private final static Category logger = Category
            .getInstance(SegmentManagerMergeTask.class);

    /**
     * 
     */
    private final String name;

    /**
     * 
     */
    private final int numSegments;

    /**
     * Create a MergeSegmentsTask with the given file name and number of
     * segments
     * 
     * @param _name
     *            filename to be used for merged file
     * @param _numSegments
     *            number of file segments to be used
     */
    public SegmentManagerMergeTask(String _name, int _numSegments) {
        name = _name;
        numSegments = _numSegments;

    }

    /** ********************************************************************** */

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.interfaces.ITask#runTask()
     */
    public void runTask() {
        try {
            task();

        } catch (Exception e) {
            logger.error(e);

        }

    }

    /**
     * @throws Exception
     */
    public void task() throws Exception {
        String suffix = DownloadSegmentList.getSuffix(1, numSegments);
        File complete = new File(name + suffix);

        RandomAccessFile outFile = new RandomAccessFile(complete, "rw");

        // iterates over the segments
        for (int i = 1; i < numSegments; i++) {
            suffix = DownloadSegmentList.getSuffix(i + 1, numSegments);
            File currSeg = new File(name + suffix);
            FileInputStream inFile = new FileInputStream(currSeg);

            int size = inFile.available();

            try {
                if (size > 0) {
                    byte[] buffer = new byte[size];

                    inFile.read(buffer);
                    outFile.seek(outFile.length());
                    outFile.write(buffer);

                }

            } finally {
                try {
                    inFile.close();

                } catch (Exception e) {
                    logger.error(e);

                }
            }

            boolean success = currSeg.delete();

            logger.info("Files deleted? " + success);

        }

        try {
            outFile.close();

        } catch (Exception e) {
            logger.error(e);

        }

        complete.renameTo(new File(name));

        // taskManager.removeTask(this);
    }

}

/*******************************************************************************
 * $Log: SegmentManagerMergeTask.java,v $
 * Revision 1.6  2005/10/02 11:42:27  timowest
 * updated sources and tests
 * Revision 1.5 2005/09/30 15:59:53
 * timowest updated sources and tests
 * 
 * Revision 1.4 2005/09/12 21:12:02 timowest added log block
 * 
 * 
 */

