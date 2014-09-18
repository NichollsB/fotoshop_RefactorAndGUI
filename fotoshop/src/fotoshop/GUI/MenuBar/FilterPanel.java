
package fotoshop.GUI.MenuBar;

import fotoshop.Events.EventController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

/**
 * The JPanel to display when selecting a {@link FilterMenu filter menu} menu item.
 * <div><p><b>Methods:</b><br/>
 * {@link #initPanel(java.lang.String)}, 
 * {@link #getName() }, 
 * {@link #getValue() }, 
 * {@link #setValue(float) }, 
 * {@link #setName(java.lang.String) }, 
 * {@link #createPanel(javax.swing.JPanel) }, 
 * {@link #reset() } 
 * </p>
 * <p>
 * <b>Children:</b><br/>
 * {@link FilterPanel_Brightness}, 
 * {@link FilterPanel_Rotate}
 * </p></div>
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
class FilterPanel extends JPanel {
    /**
     * String name of the filter menu panel
     */
    private String name;
    /**
     * float value of this filter menu
     */
    private float filterProperty;
    

    /**
     * Overridden by children. Create a basic filter display panel and assign it a name
     * @param name The name to give the panel
     */
    public void initPanel(String name){
        this.name = name;
        createPanel(null);
    }
    /**
     * Get the filter panels name
     * @return String name of this panel
     */
    public String getName(){
        return name;
    }
    /**
     * Retrieve the filter panel value.
     * @return {@link #filterProperty filterProperty}.
     */
    public float getValue(){
        return filterProperty;
    }
    /**
     * Set the current panels name
     * @param name String to be set as the filters name
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Set the value of this filter panel
     * @param f value to assign to{@link #filterProperty filterProperty}.
     */
    protected void setValue(float f){
        firePropertyChange("filterProperty", filterProperty, f);
        filterProperty = f;
    }

    /**
     * Set up the panel components. Called by this, or children passing in unique components
     * @param component Any unique component to be added to the panel
     */
    protected void createPanel(JPanel component){
        JLabel label = new JLabel("Apply filter" + this.getName());
        this.setLayout(new BorderLayout(5, 5));
        this.add(label, BorderLayout.NORTH);
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new FlowLayout());
        if(component != null)
            bodyPanel.add(component);
        this.add(bodyPanel);
    }
    
    /**
     * Reset the {@link #filterProperty value} of this panel.
     */
    protected void reset(){
        filterProperty = 0;
    }
}
