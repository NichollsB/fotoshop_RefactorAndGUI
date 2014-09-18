/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fotoshop.GUI.MenuBar;

import fotoshop.Events.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import javax.swing.*;

/**
 * The JMenu for the filters available in the application
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class FilterMenu extends JMenu implements ControllerEventSender{
    /**
     * Map of available JPanel filter menus.
     * @see #initFilterPanes() 
     */
    private static final Map<String, FilterPanel> filterPanels = initFilterPanes();
    /**
     * Options for the apply filter JOptionPane Dialog
     */
    private static final Object[] options = {"Apply", "Cancel"};
    /**
     * The frame for the apply filter dialog
     */
    private JFrame menuFrame;
    /**
     * whether or not the menu items are usable
     */
    private boolean activated = false;
    
    /**
     * Menu items that may be activated or deactivated
     * @see #activate(boolean) 
     */
    private List<JMenuItem> activatable = new ArrayList<JMenuItem>();
    /**
     * Initialise {@link #filterPanels filterPanes}. A list of unique FilterPane objects
     * @return list of FilterPane objects
     */
    private static Map<String, FilterPanel> initFilterPanes(){
        Map<String, FilterPanel> panes = new HashMap<String, FilterPanel>();
        //panes.put(ResourceManager.getFilter("BrightnessFilter"), null);
        panes.put("BrightnessFilter", new FilterPanel_Brightness("BrightnessFilter"));
        panes.put("rot90", new FilterPanel_Rotate("rot90"));
        return panes;
    }
    
    /**
     * Initialise the JMenu, set the menu text
     * @param frame 
     */
    public FilterMenu(JFrame frame){
        this.setText("Filters");
        menuFrame = frame;
        activate(false);
    }
    /**
     * Create and add JMenuItems to the menu, depending on the list of
     * filters passed in.
     * @param filters List of String filter names to create menu items for.
     */
    public void createItems(List<String> filters){
        for(String f : filters){
            final String filter = f;
            JMenuItem menuItem = new JMenuItem(f);
            FilterPanel panel = (filterPanels.containsKey(f)) ? filterPanels.get(f) : null;
            menuItem.addActionListener(new filterMenuListener(panel));
            this.add(menuItem);
            activatable.add(menuItem);
        }
        activate(activated);
    }
    
    /**
     * Activate or deactivate the menu items in this menu.
     * @param bool boolean value. True to enable, false to disable
     */
    public void activate(boolean bool){
        activated = (activated == bool ) ? bool : activated;
        for(JMenuItem item : activatable){
            if(item.isEnabled() != activated) item.setEnabled(activated);
        }
    }
    /**
     * The ActionListener for each menu item added to the menu
     */
    private class filterMenuListener implements ActionListener {
        /**
         * The {@link FilterPanel FilterPanel} to display upon selecting this
         * menu item.
         */
        private FilterPanel messagePanel;
        /**
         * Initialise the ActionListener with the parameter panel. Upon selecting
         * the associated menu item opens the {@link FilterPanel menu dialog} and
         * fires the "apply" command to the {@link fotoshop.Events.EventController controller}
         * with the {@link fotoshop.Events.PropertyMessage property message}.
         * @param panel FilterPanel to set as the {@link #messagePanel messagePanel}.
         * @see #actionPerformed(java.awt.event.ActionEvent) 
         */
        public filterMenuListener(FilterPanel panel){
            this.messagePanel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            JMenuItem source = (JMenuItem) ae.getSource();
            String filterName = source.getText();
            PropertyMessage m = new PropertyMessage(filterName, 1, true);
            int returnVal = 0;
            if(messagePanel != null){
                messagePanel.addPropertyChangeListener("filterProperty", new FilterPropertyChanged(source.getText()));
                returnVal = JOptionPane.showOptionDialog(menuFrame, messagePanel, "Filter Dialog",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
                options, options[0]);
                System.out.println();
                if(returnVal == JOptionPane.YES_OPTION){
                    m = new PropertyMessage(filterName, messagePanel.getValue(), true);
                    fireEvent("apply", m);
                    messagePanel.reset();
                    return;
                }
                fireEvent("refresh", null);
                messagePanel.reset();
                return;
            }
            fireEvent("apply", m);
        }
    }
    
    /**
     * PropertyChangeListener that is triggered upon the use of the unique component
     * attached to any of the {@link FilterPanel panels} displayed. Fires the 
     * "apply" event to the {@link fotoshop.Events.EventController controller}, but
     * with a false value {@link fotoshop.Events.PropertyMessage message} that
     * prevents the change being confirmed, until accepted by the 
     * {@link #FilterPropertyChanged(java.lang.String) dialog}.
     */
    private class FilterPropertyChanged implements PropertyChangeListener {
        private String filterName;
        public FilterPropertyChanged(String name) {
           // controller.registerObserved(this);
            this.filterName = name;
        }

        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            PropertyMessage m = new PropertyMessage(filterName, (float)pce.getNewValue(), false);
            fireEvent("apply", m);
        }
    }
    @Override
    public void addController(EventController c){
        this.addPropertyChangeListener("apply", c);
        this.addPropertyChangeListener("refresh", c);
    }
    @Override
    public void fireEvent(String property, PropertyMessage message) {
        firePropertyChange(property, null, message);
    }
}
