/*
 * Created on 23.6.2005
 */
package net.sf.javadc.interfaces;

/**
 * @author Timo Westkämper
 */
public interface IObject
{
    /**
     * @param obj
     * @return
     */
    public boolean equals(
        Object obj );

    /**
     * @return
     */
    public int hashCode();

}
