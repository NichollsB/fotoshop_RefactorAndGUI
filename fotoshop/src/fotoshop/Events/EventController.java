
package fotoshop.Events;

import fotoshop.*;
import fotoshop.GUI.GUI;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;


/**
 * <div>Main controller for the fotoshop software. Makes use of the property change 
 * {@link java.beans.PropertyChangeEvent events}, and {@link java.beans.PropertyChangeListener listeners}
 * to receive user user interface events from the GUI menu objects and passed {@link fotoshop.Command Commands}
 * to the {@link fotoshop.Editor Editor}.</div>
 * <p>propertyChange() listener receives {@link java.beans.PropertyChangeEvent propertyChangeEvents}
 * from the GUI components and passes a Command to the Editor object. Also receives events from the
 * Editor after property changes and calls the associated update method in the GUI.
 * @see #EventController() 
 * @see #setEditor(fotoshop.Editor) 
 * @see #setGUI(fotoshop.GUI.GUI) 
 * @see #propertyChange(java.beans.PropertyChangeEvent) 
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class EventController implements PropertyChangeListener {
    /**
     * Editor object
     */
    private Editor editor;
    /**
     * GUI object
     */
    private GUI gui;
    /**
     * CommandWords object used to create the Command object to pass to the editor
     */
    private CommandWords commands;  // holds all valid command words
    
    /**
     * Instantiate the commands object
     */
    public EventController(){
        commands = new CommandWords();
    }
    
    /**
     * Set the Editor object and calls its {@link fotoshop.Editor#addController(fotoshop.Events.EventController) setEditor()} method.
     * @param editor 
     */
    public void setEditor(Editor editor){
        this.editor = editor;
        this.editor.addController(this);
    }
    
    /**
     * Set the GUI object and calls its {@link fotoshop.GUI.GUI#setFilters(java.util.List) setFilters()}
     * method with the list of filter names retrieved from the 
     * {@link fotoshop.Editor#getFiltersNames() Editor}
     * @param gui 
     */
    public void setGUI(GUI gui){
        this.gui = gui;
        this.gui.setFilters(editor.getFiltersNames());
        //this.gui.addController(this);
    }

    
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if(editor != null){
            String property = pce.getPropertyName();
            //System.out.println("Property " + property);
            Object propObj = pce.getNewValue();
            if(pce.getSource().getClass() != editor.getClass()){
                if(property == "refresh"){
                    editor.refreshValues();
                }
                editor.processCommand(new Command(commands.getCommand(property), 
                        (PropertyMessage)propObj));
            }
            else {
                switch(property){
                    case "LoadImage":
                        if(propObj instanceof BufferedImage){
                            gui.updateMainImage((BufferedImage)propObj);
                        }
                        break;
                    case "LoadName":
                        if(propObj instanceof String){
                            gui.updateImageLabel((String)propObj);
                        }
                        break;
                    case "LoadStack":
                        if(propObj instanceof List){
                            gui.updateUndo((List)propObj);
                        }
                        break;
                    case "LoadStackFocus":
                        if(propObj instanceof Integer){
                            gui.updateUndoFocus((int)propObj);
                        }
                    case "LoadCache":
                        if(propObj instanceof Map){
                            gui.updateCacheList((Map)propObj);
                        }
                        break;
                    case "LoadCacheFocus":
                        if(propObj instanceof String){
                            gui.updateCacheFocus((String)propObj);
                        }
                        break;
                    case "activate":
                        if(propObj instanceof Boolean){
                            gui.activateComponents((Boolean)propObj);
                        }
                    case "refresh":
                        gui.refresh();
                    default:
                        break;
                }
            }
        }
        /*System.out.print("property:" + property);
        if(property == "ApplyFilter"){
        }*/
    }
    
}
