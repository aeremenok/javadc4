/*
 * Created on 5.6.2005
 */

package net.sf.javadc.tasks.hub;

import java.util.Arrays;
import java.util.List;

import net.sf.javadc.tasks.BaseHubTask;

/**
 * @author Timo Westkämper
 */
public class ISupportsTask extends BaseHubTask {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ISupportsTask.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.BaseHubTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {

        List supports = Arrays.asList(cmdData.split("\\s"));

        hub.setSupports(supports);
    }

}
