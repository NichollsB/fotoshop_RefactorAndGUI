
package fotoshop.GUI;

import fotoshop.GUI.SequencePanel.FilterSequencePanel;
import fotoshop.GUI.MenuBar.FilterMenu;
import fotoshop.GUI.MenuBar.FileMenu;
import fotoshop.Editor;
import fotoshop.Events.EventController;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;

/**
 * The main GUI JFrame of the fotoshop application.
 * @see #GUI(fotoshop.Events.EventController) 
 * @see #editorFrame() 
 * @see #menuBar() 
 * @see #east() 
 * @see #west()
 * @see #center() 
 * @see #refresh()
 * @see #setFilters(java.util.List) 
 * @see #updateMainImage(java.awt.image.BufferedImage) 
 * @see #updateImageLabel(java.lang.String) 
 * @see #updateCacheList(java.util.Map) 
 * @see #updateCacheFocus(java.lang.String) 
 * @see #updateUndo(java.util.List) 
 * @see #updateUndoFocus(int) 
 * @author Benjamin Nicholls
 */
public class GUI extends JFrame {
    /**
     * Controller for passing commands from GUI components to the main editor
     */
    private EventController controller;
    
    /**
     * The menu in the menu bar for selecting and applying filters to the current image
     * @see #menuBar() 
     */
    private FilterMenu filterMenu;
    
    //Updateable Panels
    /**
     * Main panel for displaying the current image
     * @see #center() 
     */
    private final ImagePanel mainImage = new ImagePanel();
    /**
     * Label for displaying the name of the current image being worked on
     * @see #center() 
     */
    private final JLabel mainlabel = new JLabel();
    /**
     * Panel for displaying the stack of image instances and allowing user to 
     * undo changes
     * @see #center() 
     */
    private UndoPanel undoPanel;
    /**
     * Panel allowing the user to create a sequence of filters to be applied to an
     * image and select the sequence from a number of buttons
     * @see #east() 
     */
    private FilterSequencePanel sequencePanel;
    /**
     * Panel displaying the cache of stored images, allowing the user to select
     * one, retrieving it from the cache, or add the current image to the cache
     * @see #west() 
     */
    private CachePanel cachePanel;

    /**
     * Initialise the GUI JFrame with the passed in controller. Calls the 
     * {@link #editorFrame() } method
     * @param controller 
     */
    public GUI(EventController controller){
        this.controller = controller;
        editorFrame();
    }
    /**
     * Main method for initialising the components of the editors GUI. Adds various
     * swing components to the JFrame.
     * @see #menuBar() 
     * @see #center() 
     * @see #east() 
     * @see #west() 
     */
    private void editorFrame() {
        //this.setPreferredSize(SIZE);
        this.addComponentListener(new ResizeListener());
        this.setName("Fotoshop Image Editor");//editor.getTranslation(editorName)
                //fileManager = new FileManager(editorFrame);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //FileManager fileManager = new FileManager(editorFrame);
        this.setJMenuBar(menuBar());
        Container mainPane = this.getContentPane();
        mainPane.setLayout(new BorderLayout(5, 5));
        mainPane.add(center(), BorderLayout.CENTER);
        mainPane.add(east(), BorderLayout.EAST);
        mainPane.add(west(), BorderLayout.WEST);
        this.pack();
        this.setVisible(true);
        this.validate();
        this.repaint();
    }
    

    /**
     * Initialises the fotoshop menu bar, populating it with JMenu objects
     * @see FileMenu
     * @see FilterMenu
     * @return JMenuBar to be added to the GUI frame
     */
    private JMenuBar menuBar(){
      JMenuBar menuBar = new JMenuBar();

      //FILE MENU
      FileMenu fileMenu = new FileMenu(this);
      menuBar.add(fileMenu);
      fileMenu.addController(controller);
      //FILTER MENU
      filterMenu = new FilterMenu(this);
      menuBar.add(filterMenu);
      filterMenu.addController(controller);
      filterMenu.activate(false);
      
      return menuBar;
    }
    /**
     * Creates the center panel of the GUI and adds the main image panel and 
     * label to it
     * @return JPanel to be added to the center area of the GUI
     * @see ImagePanel
     */
    private JPanel center(){
        //MAIN IMAGE AREA
        mainImage.setSize(this.getPreferredSize());
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(5, 5));
        mainlabel.setText("No File");
        centerPanel.add(mainlabel, BorderLayout.NORTH);
        centerPanel.add(mainImage, BorderLayout.CENTER);
        return centerPanel;
    }
    /**
     * Create the east panel of the GUI and populate it with graphical components
     * including and undo panel, and a sequence creator panel
     * @return JPanel to add to the east area of the main GUI layout
     * @see UndoPanel
     * @see FilterSequencePanel
     */
    private JPanel east() {
               //////////////////////////////////////////////
        //EAST SECTION:
        /////
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //UNDO PANEL
        undoPanel = new UndoPanel();
        undoPanel.addController(controller);
        eastPanel.add(undoPanel);
        
        eastPanel.add(Box.createVerticalStrut(5));
        //SEQUENCE PANEL
        sequencePanel = new FilterSequencePanel(this);
        sequencePanel.addController(controller);
        eastPanel.add(sequencePanel);
        
        return eastPanel;
    }
    /**
     * Create the west panel of the GUI and populate it with relevant components.
     * In this case, the cache panel, for displaying, selecting and retrieving items
     * from the cache
     * @return JPanel to add to the west area of the main GUI
     * @see CachePanel
     */
    private JPanel west() {
        cachePanel = new CachePanel(this);
        cachePanel.addController(controller);
        return cachePanel;
    }
    /**
     * Pass a list of filter names to the relevant panels.
     * @param filters List of String names of the filters
     * @see FilterSequencePanel#setFilters(java.util.List) 
     * @see FilterMenu#createItems(java.util.List) 
     */
    public void setFilters(List<String> filters) {
        sequencePanel.setFilters(filters);
        filterMenu.createItems(filters);
    }
    
    /////////////////////////////////////////////////////////////////////
    //  Refresh GUI Component methods
    //////////////
    /**
     * revalidates and repaints JFrame
     */
    public void refresh(){
        revalidate();
        repaint();
    }
    /**
     * Show the image passed in the {@link #mainImage} panel.
     * @param img BufferedImage to display
     * @see ImagePanel#showImage(java.awt.image.BufferedImage) 
     */
    public void updateMainImage(BufferedImage img) {
        if(mainImage != null){
            mainImage.showImage(img);
        }
    }
    /**
     * Update the {@link #mainlabel label} of the center panel of the GUI
     * @param name Name to apply to the label
     */
    public void updateImageLabel(String name){
        mainlabel.setText(name);
    }
    /**
     * Re-create the buttons in the undo panel
     * @param undoList List of String names of prior instances of the current image
     * being worked on
     * @see UndoPanel#createButtons(java.util.List) 
     */
    public void updateUndo(List<String> undoList){
        undoPanel.createButtons(undoList);
    }
    /**
     * Focuses the undo panel buttons, highlighting the particular button by the index
     * passed in
     * @param undoFocus int index to focus on
     * @see UndoPanel#setButtonColor(int, boolean) 
     */
    public void updateUndoFocus(int undoFocus) {
        undoPanel.setButtonColor(undoFocus, true);
    }
    /**
     * Repopulate the cache with buttons displaying images and labels retrieved
     * from the passed in map
     * @param map String, BufferedImage pairs used to populate the cache buttons
     * @see CachePanel#populateCache(java.util.Map) 
     */
    public void updateCacheList(Map<String, BufferedImage> map) {
        cachePanel.populateCache(map);
    }
    /**
     * Set the focus of the cache, highlighting the current cached image currently
     * loaded, by the passed in key
     * @param key String key to focus on in the cache panel
     * @see CachePanel#focusButton(java.lang.String) 
     */
    public void updateCacheFocus(String key){
        cachePanel.focusButton(key);
    }
    /**
     * Activate or deactivate components that require an image to be loaded before
     * they can be used
     * @param bool True to activate, false to deactivate
     * @see CachePanel#activate(boolean) 
     * @see FilterMenu#activate(boolean) 
     */
    public void activateComponents(boolean bool){
        cachePanel.activate(bool);
        filterMenu.activate(bool);
        //sequencePanel.activate(bool);
    }
    
    /**
     * used to refresh the gui when it is re-sized. Upon re-sizing the main
     * GUI JFrame the {@link #mainImage main image panel} will re-show the
     * image, calculating the image size, and the JFrame with be 
     * {@link #refresh() refreshed}.
     * @see ImagePanel#showImage()  
     */
    public class ResizeListener extends ComponentAdapter{
        public void componentResized(ComponentEvent e){
            if(mainImage != null) {
                mainImage.showImage();
                refresh();
                setVisible(true);
            }
        }
    }
}
