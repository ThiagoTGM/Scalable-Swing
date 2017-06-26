/* ComponentScaler.java
 *
 * Copyright (C) 2017 Thiago Marback
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package com.github.thiagotgm.scalable_swing;

import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * Class that manages the size scaling for a single component.<br>
 * Changes made to the component size directly (using the JComponent methods) are not
 * reflected in the sizes set in this controller.
 *
 * @version 1.0
 * @author ThiagoTGM
 * @since 2017-06-25
 */
public class ComponentScaler implements ScalableComponent {
    
    private JComponent target;
    private int resolution;
    
    private RealDimension actualMinSize;
    private Dimension scaledMinSize;
    private RealDimension actualMaxSize;
    private Dimension scaledMaxSize;
    private RealDimension actualPreferredSize;
    private Dimension scaledPreferredSize;
    
    /**
     * Creates a new Scaler that manages the scaling of a given target.
     *
     * @param target Component to be scaled.
     */
    public ComponentScaler( JComponent target ) {
        
        this.target = target;
        this.resolution = target.getToolkit().getScreenResolution();
        
    }

    /**
     * Retrieves the minimum size of the component, scaled to the monitor's resolution, if a scaled size was set.
     *
     * @return The minimum size of the component, in inches, or null if no scaled minimum size was set.
     * @see javax.swing.JComponent#getMinimumSize()
     */
    @Override
    public RealDimension getScaledMinimumSize() {
        
        return actualMinSize;

    }

    /**
     * Retrieves the maximum size of the component, scaled to the monitor's resolution, if a scaled size was set.
     *
     * @return The maximum size of the component, in inches, or null if no scaled maximum size was set.
     * @see javax.swing.JComponent#getMaximumSize()
     */
    @Override
    public RealDimension getScaledMaximumSize() {

        return actualMaxSize;
        
    }

    /**
     * Retrieves the preferred size of the component, scaled to the monitor's resolution, if a scaled size was set.
     *
     * @return The preferred size of the component, in inches, or null if no scaled preferred size was set.
     * @see javax.swing.JComponent#getPrefferedSize()
     */
    @Override
    public RealDimension getScaledPreferredSize() {

        return actualPreferredSize;
        
    }

    @Override
    public void setScaledMinimumSize( RealDimension minimumSize ) {

        actualMinSize = minimumSize;
        scaledMinSize = ScaleManager.scale( minimumSize, resolution );
        target.setMinimumSize( scaledMinSize );

    }

    @Override
    public void setScaledMaximumSize( RealDimension maximumSize ) {

        actualMaxSize = maximumSize;
        scaledMaxSize = ScaleManager.scale( maximumSize, resolution );
        target.setMaximumSize( scaledMaxSize );

    }

    @Override
    public void setScaledPreferredSize( RealDimension preferredSize ) {

        actualPreferredSize = preferredSize;
        scaledPreferredSize = ScaleManager.scale( preferredSize, resolution );
        target.setPreferredSize( scaledPreferredSize );

    }

    /**
     * Recalculates all dimensions to scale on the current monitor resolution.<br>
     * This overrides any direct changes to the component size (that used the JComponent methods)
     * with the scaled sizes last set through this manager.
     */
    @Override
    public void rescale() {

        resolution = target.getToolkit().getScreenResolution(); // Ensures resolution is up to date.
        if ( actualMinSize != null ) { // Rescales minimum size if one was set.
            scaledMinSize = ScaleManager.scale( actualMinSize, resolution );
            target.setMinimumSize( scaledMinSize );
        }
        if ( actualMaxSize != null ) { // Rescales maximum size if one was set.
            scaledMaxSize = ScaleManager.scale( actualMaxSize, resolution );
            target.setMaximumSize( scaledMaxSize );
        }
        if ( actualPreferredSize != null ) { // Rescales preferred size if one was set.
            scaledPreferredSize = ScaleManager.scale( actualPreferredSize, resolution );
            target.setPreferredSize( scaledPreferredSize );
        }

    }
    
    /**
     * Retrieves the resolution currently being used by this Scaler to scale the
     * target component.
     *
     * @return The current resolution.
     */
    public int getResolution() {
        
        return resolution;
        
    }

}
