package fotoshop.GUI.MenuBar;

import fotoshop.ColorImage;
import fotoshop.Events.EventController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Menu Panel for adjusting the brightness of an image. Creates a slider than 
 * may be used to adjust the brightness value, and will fire a property change for
 * that value in the {@link FilterPanel super}.
 * @see #FilterPanel_Brightness(java.lang.String) 
 * @see #createPanel(javax.swing.JPanel) 
 * @see #reset()
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class FilterPanel_Brightness extends FilterPanel {
    /**
     * Initialise the panel setting its super name and calling the {@link #createPanel(javax.swing.JPanel)} method.
     * @param name String to set the name of the panel to
     */
    public FilterPanel_Brightness(String name){
        super.setName(name);
        createPanel(null);
    }
    @Override
    /**
     * Set up the panel components. Called by this, or children passing in unique components
     * @param component Any unique component to be added to the panel
     */
    protected void createPanel(JPanel component){
        JPanel sliderPanel = new JPanel(new BorderLayout());
        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        slider.setValue(slider.getMaximum()/2);
        JLabel sliderLabel = new JLabel(getName());
        sliderPanel.setBorder(BorderFactory.createTitledBorder(getName()));
        sliderPanel.setLayout(new BorderLayout());
        sliderPanel.add(slider, BorderLayout.CENTER);
        ChangeListener slideChange = new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                JSlider slider = (JSlider) ce.getSource();
                //if(!slider.getValueIsAdjusting()){
                    //ColorImage img = adjustBrightness(image, 2*slider.getValue()/slider.getMaximum());
                    float i = slider.getValue();
                    float j = slider.getMaximum();
                    float n = 2*i/j;
                    setValue(n);
            }
        };
        slider.addChangeListener(slideChange);
        Dimension d = new Dimension((sliderPanel.getPreferredSize().width 
                + sliderLabel.getPreferredSize().width), 
                sliderPanel.getPreferredSize().height);
        sliderPanel.setMaximumSize(d);
        sliderPanel.setPreferredSize(d);
        super.createPanel(sliderPanel);
    }
    @Override
    protected void reset(){
        super.reset();
    }
}
