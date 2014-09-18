package fotoshop;

import fotoshop.Filter.*;
import fotoshop.GUI.SequencePanel.SequenceConstructorPanel;
import fotoshop.GUI.SequencePanel.SequenceButton;
import fotoshop.resources.*;
import fotoshop.GUI.ImagePanel;
import fotoshop.GUI.GUI;
import fotoshop.*;
import fotoshop.Events.EventController;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Adapted from the fotoshop assignment example by Richard Jones
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 * 
 * This class is the main processing class of the Fotoshop application. 
 * Fotoshop is a very simple image editing tool. Users can apply a number of
 * filters to an image. That's all. It should really be extended to make it more
 * useful!
 *
 * To edit an image, create an instance of this class and call the "edit"
 * method.
 *
 * This main class creates and initialises all the others: it creates the parser
 * and  evaluates and executes the commands that the parser returns.
 *
 * @author Richard Jones
 * @version 2013.09.10

 */

public class Editor  {
    private final String[] properties = {"LoadImage"};
    private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
    
    /**
     * EventController object that will pass any change in values to the GUI
     * @see #addController(fotoshop.Events.EventController) 
     * @see fotoshop.Events.EventController
     */
    private EventController controller;
    
    /**
     * The current stack of images instances being edited
     */
    private ImageStack currentImg;
    /**
     * The instance of ImageCache storing images currently being worked on
     */
    private final ImageCache imageCache = new ImageCache();

    /** filterMap contains the possible filters that may be applied to the
     * current image. Any attempt to apply a filter checks the name given 
     * against the filters named in this map.
     * Must be initialised in the {@link #initFilters() initFilters} method.
     **/
    private List<Filter> filterList = initFilters();
    
    /**
     * Initialise the Map of the possible filters that may be applied to the
     * image. Any new filters must also be added to this map through initFilters.
     * Any attempt to apply a filter checks the name give against the filters 
     * named in this map.
     * @return A map of the available filters
     */
    private List<Filter> initFilters(){
        List<Filter> fList = new ArrayList<Filter>();
        /**A internationalised version of the filter name can be obtained from
        *   the ResourceManager and locale specific properties**/
        fList.add(new MonoFilter(ResourceManager.getFilter("MONO")));
        fList.add(new RotateFilter(ResourceManager.getFilter("ROT90")));
        fList.add(new BrightnessFilter("BrightnessFilter"));
        return fList;
    }
    /**
     * Retrieve the names of the Filter objects within the {@link #filterList filterList}
     * @return List<String> of filter names
     */
    public List<String> getFiltersNames(){
        List<String> filters = new ArrayList<String>();
        for(Filter filter : filterList){
            filters.add(filter.getName());
        }
        return filters;
    }
    /**
     * Assign the editors EventController for this editor. Add a propertyChangeListener
     * to the {@link #pChange pChange} PropertyChangeSupport for each property that may change in this Editor
     * @param c the EventController to add to the PropertyChangeSupport object
     * @see fotoshop.Events.EventController
     */
    public void addController(EventController c){
        pChange.addPropertyChangeListener("LoadImage", c);
        pChange.addPropertyChangeListener("LoadName", c);
        pChange.addPropertyChangeListener("LoadStack", c);
        pChange.addPropertyChangeListener("LoadStackFocus", c);
        pChange.addPropertyChangeListener("LoadCache", c);
        pChange.addPropertyChangeListener("LoadCacheFocus", c);
        pChange.addPropertyChangeListener("activate", c);
        pChange.addPropertyChangeListener("refresh", c);
    }

    /**
     * Given a command, edit (that is: execute) the command.
     * Altered to make use of enum objects as suggested in "Objects First with 
     * Java" by Barnes and Kolling.
     * @param command The command to be processed.
     * @return true If the command ends the editing session, false otherwise.
     */
    public boolean processCommand(Command command) {
        System.out.println("Command = " + command.getCommandWord().toString());
        boolean wantToQuit = false;
        CommandWord commandWord = command.getCommandWord();
        switch(commandWord){
            case UNKNOWN:
                System.out.println(ResourceManager.getEditorText("unknownCommand"));
                break;
            case OPEN:
                open(command);
                break;
            case SAVE:
                save(command);
                break;
            case APPLY:
                performFilter(command);  
                break;
            case UNDO:
                restoreImage(command);
                break;
            case PUT:
                put(command);
                break;
            case GET:
                get(command);
                break;
        }
        return wantToQuit;
    }
    
    /**
     * Main property firing method. Fires property changes for all the relevant 
     * properties that need to be updated in the GUI
     */
    public void refreshValues(){
        if(currentImg.get() != null)
            pChange.firePropertyChange("LoadImage", null, (BufferedImage)currentImg.get());
        if(currentImg.getInstanceList() != null){
            pChange.firePropertyChange("LoadStack", null, currentImg.getInstanceList());
            System.out.println("stackIndex " + currentImg.getIndex());
            pChange.firePropertyChange("LoadStackFocus", -1, currentImg.getIndex());
        }
            pChange.firePropertyChange("LoadCache", null, imageCache.getCachedImages());
        if(currentImg.getName() != null){
            pChange.firePropertyChange("LoadName", null, currentImg.getName());
            pChange.firePropertyChange("LoadCacheFocus", null, currentImg.getName());
        }
        pChange.firePropertyChange("activate", 0, true);
        pChange.firePropertyChange("refresh", null, null);
    }
//----------------------------------
// Implementations of user commands:
//----------------------------------
    

    /**
     * "open" was entered. Open the file given as the second word of the command
     * and use as the current image. 
     * @param command the command given.
     */
    private void open(Command command) {
        if(command.getFile() == null){
            System.out.println("No file error");
            return;
        }
        File file = command.getFile();
        ColorImage img = FileManager.loadImage(file);
        if (img == null) {
            return;
        } else {
            currentImg = new ImageStack(img.getName(), img);
            refreshValues();
        }
    }

    /**
     * "save" was entered. Save the current image to the file given as the 
     * second word of the command. 
     * @param command the command given
     */
    private void save(Command command) {
        if (currentImg == null) {
            return;
        }
        File file = command.getFile();
        FileManager.SaveImage(currentImg.get(), file);
    }

    /**
     * "look" was entered. Report the status of the work bench. 
     * Lists the name of the current image, and a list of filters applied to
     * the image
     */
 /*   private void look() {
        System.out.println(ResourceManager.getEditorText("currentImage") +
                currentName);

        if(currentStack != null){
            System.out.print(ResourceManager.getEditorText("currentFilters"));
            List<ColorImage> stack = new ArrayList<ColorImage>();
            if(!cache.contains(currentName))
                stack = currentStack.getStack(currentName);
            else
                stack = cache.getStack(currentName);
            for(ColorImage filteredImg : stack){
                if(filterList.contains(filteredImg.getName()))
                    System.out.print(filteredImg.getName() + " ");
            }
        }
    }*/

    
    //------------------------------
    // Image Cache commands
    //-------------------------------
    /**
     * the "put" command finds an appropriate name from the command, or from the 
     * image name and performs {@link #putToCache(java.lang.String) putToCache}
     * method.
     * @param command Command that was called by the user
     */
    private void put(Command command){
        String name = currentImg.getName();
        if (command.getName() != null) {
            name = command.getName();
        }
        putToCache(name);
        currentImg.setName(name);
        refreshValues();

    }
    /**
     * The "get" command performs the {@link #getFromCache(java.lang.String) getFromCache}
     * method adding the current image to the cache using the name supplied by the Command
     * @param command Command that was called by the user
     */
    private void get(Command command){
        if (command.getName() == null) {
            // if there is no second word, we don't what to add it to cache as...
            System.out.println(ResourceManager.getEditorText("unknownSource"));
            return ;
        }
        getFromCache(command.getName());
        refreshValues();
    }
    /**
     * putToCache passes the current image into the ImageCache object via the
     * {@link ImageCache#put(fotoshop.ImageStack, java.lang.String)  put} method.
     * @param name String name to add the {@link #currentImg current image} to the cache as
     */
    public void putToCache(String name){
        if(currentImg == null){
            System.out.print("No image to put to cache");
        }
        else {
            ImageStack clone = currentImg.cloneStack();
            imageCache.put(clone, name);
            currentImg = clone;
            refreshValues();
            System.out.println(ResourceManager.getEditorText("cacheAdded") + 
                    name);
        }
    }
    /**
     * getFromCache passes the current image into the ImageCache object via the
     * {@link ImageCache#get(java.lang.String) get} method.
     * @param name name of the stored image to retrieve from the cache
     */
    public void getFromCache(String name){
        ImageStack img = imageCache.get(name);
        if(img != null){
            currentImg = img;
            System.out.println(name + ResourceManager.getEditorText("cacheRetrieved"));
           // look();
        }
        else
            System.out.print(ResourceManager.getEditorText("missingCache"));

    }

    /**
     * Attempt to pass currentImage into a filter
     * @param command The second word of the command entered is the filter name
     * that should be searched for, in the filterMap, and performed.
     */
    private void performFilter(Command command){
        System.out.println("apply filter");
        if(currentImg.get() != null && command.getName() != null){
            ColorImage filteredImage;
            for(Filter filter : filterList){
                if(filter.getName() == command.getName()){
                    float f = command.getValue();
                    filteredImage = filter.applyFilter(new ColorImage(currentImg.get(), currentImg.getName()), f);
                    pChange.firePropertyChange("LoadImage", 0, (BufferedImage)filteredImage);
                    pChange.firePropertyChange("refresh", null, null);
                    if(command.isTrue()){
                        currentImg.add(filteredImage);
                        refreshValues();
                    }
                }
            }
        }
        else {
            System.out.println(ResourceManager.getFilter("filterError"));
        }
    }

    /**
     * restoreImage calls {@link restoreImage restoreImage} for the previous 
     * instance of the currentImage contained within the currentStack.
     * The previous version of a ColorImage is contained within the ColorImage 
     * itself.
     */
    private void restoreImage(Command command){
        currentImg.get(Math.round(command.getValue()));
        refreshValues();
    }   


}
