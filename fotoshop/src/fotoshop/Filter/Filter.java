package fotoshop.Filter;

import fotoshop.ColorImage;
import fotoshop.GUI.ImagePanel;
import java.awt.*;
import javax.swing.*;

/**
 * Derived from suggestions made in the book, "Objects First with
 * Java" by David Barnes and Michael Kolling.
 * Filter class is the abstract super for all filters. Simply contains the name
 * and the overridden {@link #applyFilter(fotoshop.ColorImage) applyFilter} 
 * method
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public abstract class Filter {
    
    private String filter;

    /**
     * Set the current filters name
     * @param name String to be set as the filters name
     */
    public void setName(String name){
        this.filter = name;
    }
    /**
     * Retrieve the name of this filter
     * @return The name of the filter
     */
    public String getName(){
        return filter;
    } 
    
    public ColorImage applyFilter(ColorImage image){return null;}
    public ColorImage applyFilter(ColorImage image, float i){return null;}
}
