package fotoshop.GUI.MenuBar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Menu Panel for rotating an image. Creates a button that will fire a property 
 * change in the super triggering a 90 degree rotation filter to be applied
 * that value in the {@link FilterPanel super}.
 * @see fotoshop.GUI.MenuBar.FilterPanel
 * @see #createPanel(javax.swing.JPanel) 
 * @see #reset() 
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class FilterPanel_Rotate extends FilterPanel{
    /**
     * Initialise the panel setting its super name and calling the {@link #createPanel(javax.swing.JPanel)} method.
     * @param name String to set the name of the panel to
     */
    public FilterPanel_Rotate(String name){
        super.setName(name);
        createPanel(null);
    }
     @Override
    /**
     * Set up the panel components. Called by this, or children passing in unique components
     * @param component Any unique component to be added to the panel
     */
    protected void createPanel(JPanel component){
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JLabel sliderLabel = new JLabel(getName());
        buttonPanel.setBorder(BorderFactory.createTitledBorder(getName()));
        buttonPanel.setLayout(new BorderLayout());
        JButton button = new JButton(getName());
        buttonPanel.add(button, BorderLayout.CENTER);
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                setValue(getValue()+1);
            }
        });

        Dimension d = new Dimension((buttonPanel.getPreferredSize().width 
                + sliderLabel.getPreferredSize().width), 
                buttonPanel.getPreferredSize().height);
        buttonPanel.setMaximumSize(d);
        buttonPanel.setPreferredSize(d);
        super.createPanel(buttonPanel);
    }
     @Override
    protected void reset(){
        super.reset();
    }
}
