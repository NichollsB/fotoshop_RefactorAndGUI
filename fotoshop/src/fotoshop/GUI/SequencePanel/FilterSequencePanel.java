
package fotoshop.GUI.SequencePanel;

import fotoshop.Events.ControllerEventSender;
import fotoshop.Events.EventController;
import fotoshop.Events.PropertyMessage;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 * JPanel for creating sequences of filters. Sequences are represented by 
 * {@link SequenceButton sequence buttons}, which fire the "apply" property change
 * event, applying a list of filters to the current image
 * @see #FilterSequencePanel(javax.swing.JFrame) 
 * @see #createSequenceButtons() 
 * @see #setFilters(java.util.List) 
 * @see #activate() 
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class FilterSequencePanel extends JPanel implements ControllerEventSender{
    /**
     * List of filter names
     */
    private List<String> filterList = new ArrayList<String>();
    /**
     * Panel containing the {@link SequenceButton sequence buttons}.
     */
    private final JPanel buttonPanel = new JPanel();
    /**
     *Options for the add sequence dialog
     */
    private Object[] panelOptions = {"Accept", "Cancel"};
    /**
     * List of {@link SequenceButton SequenceButtons} in this panel
     */
    private final List<SequenceButton> sequenceBtns = new ArrayList<SequenceButton>();
    
    /**
     * Initialise this panel with a scroll pane for sequence buttons, and a button
     * for creating a new sequence
     * @param editorFrame The main GUI frame
     */
    public FilterSequencePanel(JFrame editorFrame){
        this.setLayout(new BorderLayout());
        JLabel sequenceHead = new JLabel("Filter Sequences");
        this.add(new JLabel("Filter Sequences"), BorderLayout.NORTH);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
       
        buttonPanel.setBackground(Color.GRAY);
        buttonPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        final Dimension size = this.getMaximumSize();
        /**
         * Create "addSequence" button. Upon clicking the button bring up a
         * sequence creation dialog
        **/
        JButton addSequence = new JButton("New Sequence");
        addSequence.addActionListener(new AddSequenceListener(editorFrame));
        //buttonPanel.add(new JSeparator());
        JScrollPane scroller = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scroller, BorderLayout.CENTER);
        this.add(addSequence, BorderLayout.SOUTH);
    }
    /**
     * Populate or re-populate the sequence scroll pane with the stored sequence
     * buttons
     * @see #sequenceBtns
     * @see SequenceButton
     */
    private void createSequenceButtons(){
        buttonPanel.removeAll();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        int n = 0;
        buttonPanel.add(Box.createVerticalStrut(5), c);
        for(SequenceButton btn : sequenceBtns){
            c.gridy = n;
            buttonPanel.add(Box.createVerticalStrut(5), c);
            n++;
            c.gridy = n;
            buttonPanel.add(btn, c);
            n++;
        }
        c.weighty = 1;
        c.gridy = n;
        buttonPanel.add(Box.createVerticalStrut(5), c);
        validate();
    }
    /**
     * Set the {@link #filterList filter list} of filters that are available
     * @param filters List of String filter names that are available for constructing
     * a filter sequence
     */ 
    public void setFilters(List<String> filters){
        if(filters != null)this.filterList = filters;
    }

    /**
     * activate or deactivate the filter sequence buttons.
     */
    public void activate(){
        for(Component cmpnt : this.getComponents()){
            JButton btn = (JButton)cmpnt;
            btn.setEnabled(!btn.isEnabled());
        }
    }
    
    /**
     * Listener for the 'Add Sequence' button. Brings up a {@link SequenceConstructorPanel}
     * dialog and if the Dialog is accepted create a new {@link SequenceButton} and
     * add it to the panel and the {@link FilterSequencePanel#sequenceBtns sequenceBtns} list.
     */
    private class AddSequenceListener implements ActionListener {
        /**
         * The main GUI frame
         */
        private JFrame frame;
        /**
         * Initialise the Listener adding the main GUI JFrame to {@link #frame frame}
         * @param frame JFrame to apply to frame
         */
        public AddSequenceListener(JFrame frame){
            this.frame = frame;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Create an SequencePanel dialog to allow the selection of a 
             * filter sequence and adjustment of filter values to be applied
             */
            SequenceConstructorPanel optionPanel = 
                    new SequenceConstructorPanel(filterList);
            int value = JOptionPane.showOptionDialog(frame, 
                    optionPanel, "Sequence constructor", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
                    null, panelOptions, panelOptions[0]);
            /**
             * If the dialog is accepted create a new sequence button
             * (containing the filter sequence) and add it to this sequence
             * panel
             */
            if(value == JOptionPane.YES_OPTION){
                SequenceButton sequenceButton;
                if(optionPanel.getName() == null || optionPanel.getReturnList().size() < 1){
                    JOptionPane.showMessageDialog(frame, "Sequence requires a name",
                            "Missing name", JOptionPane.PLAIN_MESSAGE);
                }
                else{
                    sequenceButton = new SequenceButton(optionPanel.getReturnList());
                    sequenceButton.setName(optionPanel.getName());
                    sequenceButton.addActionListener(new SequenceButtonListener());
                    sequenceBtns.add(sequenceButton);
                    createSequenceButtons();
                }
            }
        }
    }
    /**
     * Listener for the {@link SequenceButton sequence buttons}. Selecting the button
     * calls the {@link fotoshop.Events.ControllerEventSender#fireEvent(java.lang.String, fotoshop.Events.PropertyMessage) fireEvent}
     * method for the "apply" property for each {@link fotoshop.Events.PropertyMessage} listed in the 
     * {@link SequenceButton}.
     */
    private class SequenceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SequenceButton button = (SequenceButton) ae.getSource();
            String[] name = {"performFilter", button.getName()};
            for(PropertyMessage filter : button.getSequence()){
                fireEvent("apply", filter);
            }
        }
    }
    @Override
    public void fireEvent(String property, PropertyMessage message) {
        firePropertyChange(property, null, message);
    }
    @Override
    public void addController(EventController c){
        this.addPropertyChangeListener("apply", c);
    }
}
