
package fotoshop.GUI.SequencePanel;

import fotoshop.Events.PropertyMessage;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Panel displayed in a dialog by the {@link FilterSequencePanel} for constructing
 * a new sequence of filters that should be applied to the current image
 * @see #SequenceConstructorPanel(java.util.List) 
 * @see #addFiltersList(java.lang.String) 
 * @see #getName() 
 * @see #getReturnList() 
 * @see TextField_Change
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class SequenceConstructorPanel extends JPanel{
    /**
     * The working sequence, list of messages (indicating the filter to apply)
     */
    private List<PropertyMessage> messageList = new ArrayList<PropertyMessage>();
    /**
     * name of the current working sequence
     */
    private String sequenceName = null;
    
    /**
     * filters that have currently been selected to be in this sequence
     */
    private JPanel sequencesPanel = new JPanel();
    
    /**
     * Layout constraints of the panel
     */
    private GridBagConstraints thisC = new GridBagConstraints();
    
    /**
     * Initialise the constructor panel with a list of filters. Set up the layout of the
     * panels components, create a list of buttons to allow the addition of filters
     * to the sequence, and a textbox to allow the naming of the sequence
     * @param filters List of available filters (names) that can be added to 
     * the sequence
     * @see TextField_Change
     */
    public SequenceConstructorPanel(List<String> filters){

        JPanel buttonPanel = new JPanel();
        this.setLayout(new GridBagLayout());
        GridBagConstraints buttonC = new GridBagConstraints();
        thisC.gridx = 0;
        thisC.gridy = 0;
        thisC.fill = GridBagConstraints.HORIZONTAL;
        this.add(new JLabel("Choose filters to apply"), thisC);
        thisC.gridy = 1;
        thisC.fill = GridBagConstraints.NONE;
        this.add(new JLabel("Sequence Name: "), thisC);
        thisC.fill = GridBagConstraints.HORIZONTAL;
        thisC.gridx = 1;
        //Text field for naming the sequence
        JTextField nameField = new JTextField();
        nameField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent event){
                JTextField field = (JTextField) event.getSource();
                sequenceName = field.getText();
            }
        });
        this.add(nameField, thisC);
        thisC.fill = GridBagConstraints.VERTICAL;
        thisC.gridy = 2;
        thisC.gridx = 0;
        this.add(buttonPanel, thisC);
        thisC.gridx = 1;
        this.add(sequencesPanel, thisC);

        sequencesPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Filter sequence:");

        buttonPanel.setLayout(new GridBagLayout());
        buttonC.fill = GridBagConstraints.HORIZONTAL;
        buttonC.gridx = 0;
        buttonC.gridy = 0;
        buttonC.anchor = GridBagConstraints.PAGE_START;
        
        //Create buttons for adding to the sequence
        buttonPanel.add(new JLabel("Available Filters:"), buttonC);
        addFiltersList(null);
        JButton optionButton;
        for(String s : filters){
            buttonC.gridy = buttonC.gridy+1;
            optionButton = new JButton(s);
            optionButton.addActionListener(new FilterButtonListener(s));
            buttonPanel.add(optionButton, buttonC);
        }

    }
    /**
     * Reconstruct the {@link #sequencesPanel}, adding a new filter. 
     * Removing all components and adding labels
     * for each filter in the current sequence to a JScrollPane. Also added is a 
     * JFormattedTextField to allow values to be given to the sequence entry (i.e
     *  the number of 90 degree rotations to perform, or the percentage to increase the
     * brightness by - to a max of %100).
     * @param s 
     * @see TextField_Change
     */
    public void addFiltersList(String s){

        List<String> entry = new ArrayList<String>();
        //Add value s to the messageList
        if(s != null){
            entry.add(s);
            messageList.add(new PropertyMessage(s, 0, true));
        }
        
        sequencesPanel.removeAll();
        //Set the layout of the panel
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel label = new JLabel("Filter sequence:");
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        panel.setLayout(layout);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(label, c);
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        label = new JLabel("Filter");
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        panel.add(label, c);
        c.gridx = 1;
        c.weightx = 1;
        label = new JLabel("Value");
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        panel.add(label, c);
        c.fill = GridBagConstraints.NONE;

        JFormattedTextField valueField;
        int n = 0;
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridBagLayout());
        
        //Add a label JFormattedTextField pair for each PropertyMessage in messageList
        for(PropertyMessage msg : messageList){
            c.gridy = n;
            c.gridx = 0;
            c.weightx = 0.5;
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.NONE;

            label = new JLabel(n+1 + ": " + msg.getName());
            //label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            scrollPanel.add(label, c);
            c.gridx = 1;
            c.anchor = GridBagConstraints.EAST;
            c.weightx = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            NumberFormat format = NumberFormat.getNumberInstance();
            format.setMaximumIntegerDigits(100);
            valueField = new JFormattedTextField(format);
            //valueField.setValue(new Integer(filter.get(1)));
            valueField.setValue(new Float(msg.getValue()));
            valueField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            valueField.addPropertyChangeListener(new TextField_Change(msg, n));
            scrollPanel.add(valueField, c);
            n++;
        }
        JScrollPane scroller = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setPreferredSize(new Dimension(200, sequencesPanel.getPreferredSize().height));
        c.gridx = 0;
        c.gridy = c.gridy+1;
        c.fill = GridBagConstraints.CENTER;
        panel.add(scroller, c);
        sequencesPanel.add(scroller);
        validate();
    }
    /**
     * Get the sequence name
     * @return {@link #sequenceName}
     */
    public String getName(){
        return sequenceName;
    }
    /**
     * Get the sequence of filters to apply
     * @return {@link #messageList}
     */
    public List<PropertyMessage> getReturnList(){
        return messageList;
    } 
    /**
     * Listener for the Text fields used by the constructor panel. Any change in
     * text attempts to update the textfields {@link #entry} and messageList
     * {@link #index}
     */
    public class TextField_Change implements PropertyChangeListener {
        /**
         * PropertyMessage the text field will edit
         */
        private PropertyMessage entry;
        /**
         * index the entry should be put into the {@link #messageList}
         */
        private int index;
        /**
         * Initialise the Listener with the PropertyMessage entry and int index value
         * @param entry PropertyMessage applied to entry
         * @param n int applied to index
         */
        public TextField_Change(PropertyMessage entry, int n){
            this.entry = entry;
            this.index = n;
        }

        public void propertyChange(PropertyChangeEvent event){
            JFormattedTextField source = (JFormattedTextField) event.getSource();
            Integer str = ((Number)source.getValue()).intValue();
            entry.setValue(str);
            messageList.set(index, entry);
        }
    }
    /**
     * Listener for buttons that add filters to the working {@link #messageList sequence}
     * Initialised with a string value, which is {@link #addFiltersList(java.lang.String)} added
     * to the filtersList on activation.
     */
    public class FilterButtonListener implements ActionListener{
        /**
         * Value to add to the messageList
         */
        String filterName;
        /**
         * Initialise the listener with value s
         * @param s String value to apply to filterName
         */
        public FilterButtonListener(String s){
            this.filterName = s;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            addFiltersList(this.filterName);
        }
    }

}
