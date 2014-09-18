
package fotoshop.GUI;

import fotoshop.Events.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

/**
 * A JPanel for displaying the a cache of stored images. Comprised of a 
 * JScrollPane populated with a series of JButtons comprised of ImagePanels and labels
 * obtained from an input Map of String-BufferedImage pairs.
 * @see #CachePanel(javax.swing.JFrame) 
 * @see #populateCache(java.util.Map) 
 * @see #addToCache(boolean) 
 * @see #focusButton(java.lang.String) 
 * @see CacheButtonListener
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class CachePanel extends JPanel implements ControllerEventSender{
    /**
     * The content panel for the main cache {@link #cacheScroll scroll pane}.
     */
    private final JPanel scrollPanel = new JPanel();
    /**
     * The main scroll pane of cache buttons
     */
    private final JScrollPane cacheScroll = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    /**
     * The Dimension used to set the desired size of the ImagePanel of each cache
     * button and govern the width of the panel
     */
    private final Dimension imageSize = new Dimension(150, 80);
    /**
     * Map of the cache buttons. Each JButton refers to an instance of an image
     * stored in the {@link fotoshop.ImageCache cache}. The String key refers to
     * the name the cache is stored as, and is passed to the JButtons 
     * {@link CacheButtonListener ActionListener}.
     */
    private Map<String, JButton> cacheButtonMap = new HashMap<String, JButton>();
    /**
     * List storing the JButtons that are used to trigger the addition of the
     * currentImage to the cache.
     */
    private final List<JButton> addButtons = new ArrayList<JButton>();
    /**
     * The particular JButton that is being focused on. The button that is
     * highlighted/bordered when open in the main editor
     */
    private JButton focus;
    //private JFrame frame;
    
    /**
     * Instantiate the CachePanel. Set up the preferred size of the panel, add a 
     * JLabel heading, and set up the Layout, border and colour of the components.
     * @param frame The main GUI JFrame
     */
    public CachePanel(JFrame frame){
        this.setMinimumSize(new Dimension(imageSize.width, this.getPreferredSize().height));
        this.setPreferredSize(new Dimension(imageSize.width + 10, this.getPreferredSize().height));
        //this.frame = frame;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(new BorderLayout(5, 5));
        JLabel label = new JLabel("Image Cache");
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add(label, BorderLayout.NORTH);
        this.add(cacheScroll, BorderLayout.CENTER);
        this.add(Box.createHorizontalGlue(), BorderLayout.EAST);
        this.add(Box.createHorizontalGlue(), BorderLayout.WEST);
        cacheScroll.add(Box.createHorizontalStrut(imageSize.width + 20));
        cacheScroll.setAlignmentX(LEFT_ALIGNMENT);
        cacheScroll.setBorder(BorderFactory.createLoweredBevelBorder());

        scrollPanel.setBackground(Color.GRAY);
 
        JPanel basePanel = new JPanel();
        basePanel.setLayout(new FlowLayout());
        
        //Add current image button:
        JButton baseButton = new JButton("Add");
        baseButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                addToCache(false);
            }
        });
        basePanel.add(baseButton);
        baseButton.setEnabled(false);
        addButtons.add(baseButton);
        //Add image as button:
        baseButton = new JButton("Add as");
        baseButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                addToCache(true);
            }
        });
        basePanel.add(baseButton);
        addButtons.add(baseButton);
        activate(false);
        this.add(basePanel, BorderLayout.SOUTH);
    }       
    /**
     * Populate/re-populate the display of the items in the cache. Re-constructs
     * the buttons and button listeners at each call of the method, from the 
     * input map of String names that each cached image was stored as, and
     * BufferedImage of the current working ColorImage.
     * @param cacheMap The map of string key and BufferedImages to display in the
     * panel.
     */
    public void populateCache(Map<String, BufferedImage> cacheMap){
        System.out.println("Populating cache " + cacheMap.size());
        JPanel buttonPanel;
        JButton button;
        ImagePanel imagePanel;
        scrollPanel.removeAll();
        scrollPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.gridy = 0;
        c.ipady = 5;
        c.ipadx = 10;
        c.insets = new Insets(5, 2, 5, 2);
        c.anchor = GridBagConstraints.NORTH;
        //c.gridheight = imageSize.height;
        cacheButtonMap = new HashMap<String, JButton>();
        int n = 0;
        for(String key : cacheMap.keySet()){
            c.gridy = n;
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            final JLabel buttonLabel = new JLabel("> " + key);
            buttonPanel.add(buttonLabel, BorderLayout.SOUTH);
            
            imagePanel = new ImagePanel(imageSize.width, imageSize.height);
            buttonPanel.add(imagePanel, BorderLayout.CENTER);
            buttonPanel.setBackground(Color.lightGray);
            imagePanel.showImage(cacheMap.get(key));
            
            button = new JButton();
            button.add(buttonPanel);
            button.setBackground(Color.GRAY);
            cacheButtonMap.put(key, button);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.addActionListener(new CacheButtonListener(key));
            button.addMouseListener(new MouseAdapter(){
                public void mouseEntered(MouseEvent evt){
                    JButton btn = (JButton)evt.getSource();
                    if(btn != focus){
                        btn.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                        buttonLabel.setForeground(Color.BLUE);
                    }
                }
                public void mouseExited(MouseEvent evt){
                    JButton btn = (JButton)evt.getSource();
                    if(btn != focus){
                        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        buttonLabel.setForeground(Color.BLACK);
                    }
                }
            });
            scrollPanel.add(button, c);
            n++;
            c.gridy = n;
            scrollPanel.add(Box.createVerticalStrut(10), c);
            n++;
        }
        c.gridy = n + 1;
        c.weighty = 1;
        scrollPanel.add(Box.createVerticalBox(), c);
        revalidate();
        repaint();
    }
    /**
     * Focus on a particular cache button in the panel. Change the colour of the
     * particular buttons border (identified by the key) and re-set the colour of
     * the borders of the remaining buttons in the {@link #cacheButtonMap panel}.
     * @param key 
     */
    public void focusButton(String key){
        if(cacheButtonMap.containsKey(key)){
            JButton btn;
            for(String s : cacheButtonMap.keySet()){
                btn = cacheButtonMap.get(s);
                if(s == key){
                    btn.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                    focus = btn;
                }
                else
                    btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
            validate();
        }
    }
    /**
     * Add the current image to the cache. If the parameter dialog is true, this
     * will display a JOptionPane dialog requesting a name to store the image as.
     * Then fires the propertychange event with a {@link fotoshop.Events.PropertyMessage 
     * PropertyMessage} containing the name obtained (or not) from the dialog, and the
     * boolean dialog parameter value.
     * @param dialog boolean parameter governing if a name is to input via a
     * JOptionPane dialog.
     */
    private void addToCache(boolean dialog){
        String name = null;
        if(dialog){
            name = (String)JOptionPane.showInputDialog(new JFrame(), "Add as...",
                    "Add to cache dialog", JOptionPane.PLAIN_MESSAGE, null, 
                    null, null);
        }
        fireEvent("put", new PropertyMessage(name, 0, dialog));
    }
    /**
     * The ActionListener for each cache button. Will send a "get" property change
     * event to the controller, in turn retrieving the cached image from the 
     * editor.
     */
    public class CacheButtonListener implements ActionListener {
        private String key;
        /**
         * The Cache buttons action listener is instantiated with a string key obtained
         * from the map key value passed in on populating the cache.
         * @param key String key to be fired as part of the "get" property event
         */
        public CacheButtonListener(String key){
            this.key = key;
        }
        @Override
        /**
         * Call the fireEvent method with the "get" property and a PropertyMessage
         * containing the key of the cached image to retrieve.
         */
        public void actionPerformed(ActionEvent ae) {
            fireEvent("get", new PropertyMessage(key));
            focusButton(key);
        }
        
    }
    /**
     * Enable, or disable the add to cache buttons
     * @param bool Whether to enable the button (true), or not (false).
     */
    public void activate(boolean bool){
        for(JButton btn : addButtons){
            btn.setEnabled(bool);
        }
    }
    

    @Override
    public void addController(EventController c) {
        this.addPropertyChangeListener("get", c);
        this.addPropertyChangeListener("put", c);
    }

    @Override
    public void fireEvent(String property, PropertyMessage message) {
        firePropertyChange(property, null, message);
    }
   
}
