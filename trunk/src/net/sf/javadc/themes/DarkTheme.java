/*
 * Created on 5.6.2005
 */
package net.sf.javadc.themes;

import javax.swing.plaf.ColorUIResource;

import com.jgoodies.plaf.plastic.theme.DarkStar;

/**
 * @author Timo Westkämper
 */
public class DarkTheme extends DarkStar {

    private static final ColorUIResource DARK_GRAY = new ColorUIResource(64,
            64, 64);

    private static final ColorUIResource LIGHT_GRAY = new ColorUIResource(192,
            192, 192);

    private static final ColorUIResource WHITE = new ColorUIResource(
            ColorUIResource.WHITE);

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.metal.MetalTheme#getName()
     */
    public final String getName() {
        return "Dark Theme";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jgoodies.plaf.plastic.PlasticTheme#getSimpleInternalFrameBackground()
     */
    public final ColorUIResource getSimpleInternalFrameBackground() {
        return DARK_GRAY;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jgoodies.plaf.plastic.PlasticTheme#getSimpleInternalFrameForeground()
     */
    public final ColorUIResource getSimpleInternalFrameForeground() {
        return DARK_GRAY;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.metal.MetalTheme#getWindowBackground()
     */
    public final ColorUIResource getWindowBackground() {
        return DARK_GRAY;

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.metal.MetalTheme#getControlInfo()
     */
    public final ColorUIResource getControlInfo() {
        return DARK_GRAY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jgoodies.plaf.plastic.PlasticTheme#getTitleTextColor()
     */
    public final ColorUIResource getTitleTextColor() {
        return LIGHT_GRAY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.metal.MetalTheme#getSystemTextColor()
     */
    public final ColorUIResource getSystemTextColor() {
        return LIGHT_GRAY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.metal.MetalTheme#getHighlightedTextColor()
     */
    public final ColorUIResource getHighlightedTextColor() {
        return LIGHT_GRAY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.metal.MetalTheme#getControlTextColor()
     */
    public final ColorUIResource getControlTextColor() {
        return LIGHT_GRAY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.metal.MetalTheme#getMenuForeground()
     */
    public final ColorUIResource getMenuForeground() {
        return WHITE;
    }

}

/*******************************************************************************
 * $Log: DarkTheme.java,v $
 * Revision 1.6  2005/10/02 11:42:28  timowest
 * updated sources and tests
 * Revision 1.5 2005/09/14 07:11:50 timowest updated
 * sources
 * 
 * 
 * 
 */
