package fotoshop.GUI.MenuBar;

import fotoshop.Events.ControllerEventSender;
import fotoshop.Events.*;
import java.awt.Component;
import java.awt.event.*;
import java.io.File;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * File menu JMenu. Contains the "Open", "Save", "Quit" options, etc. JMenuItems
 * fire the appropriate command with any File (saving, or loading)
 * @see #FileMenu(javax.swing.JFrame) 
 * @see #setOptionsText(java.util.Map) 
 * @author Benjmain Nicholls, bn65@kent.ac.uk
 */
public class FileMenu extends JMenu implements ControllerEventSender{
    /**
     * The JMenuItems to create
     */
    private final static String[] fileOptions = {"open", "save", "quit"};
    /**
     * JFileChooser object for choosing a file to load from or save to
     */
    private FileWindow window;
    
    /**
     * Initialise the menu items, defined by the {@link #fileOptions file options}.
     * @param frame JFrame of the main GUI
     */
    public FileMenu(JFrame frame){
        window = new FileWindow(frame);
        this.setText("File");
        JMenuItem item;
        for(int i = 0; i < fileOptions.length; i++){
            final int n = i;
            item = new JMenuItem(fileOptions[i]);
            item.setName(fileOptions[i]);
            item.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae) {
                    JMenuItem source = (JMenuItem) ae.getSource();
                    PropertyMessage m = new PropertyMessage();
                    if(source.getName() == "open") m = new PropertyMessage(window.open());
                    else if(source.getName() == "save") m = new PropertyMessage(window.save());
                    fireEvent(source.getName(), m);
                }
                
            });
            this.add(item);
        }
        
    }
    /**
     * Set the text of the {@link #fileOptions file menu options}. Replaces the
     * text of each JMenuItem with the passed in map value if it finds a file
     * option which matches the corresponding key.
     * @param options Map of keys (options to replace the text of) and values
     * (text to replace the text with).
     */
    public void setOptionsText(Map<String, String> options){
        JMenuItem item;
        for(Component itm : this.getComponents()){
            item = (JMenuItem)itm;
            if(options.containsKey(item.getName()))
                item.setText(options.get(item.getName()));
        }
    }
    @Override
    public void addController(EventController controller){
        for(int i = 0; i < fileOptions.length; i++){
            this.addPropertyChangeListener(fileOptions[i], controller);
        }
    }

    @Override
    public void fireEvent(String property, PropertyMessage message) {
        firePropertyChange(property, null, message);
    }
    
}
