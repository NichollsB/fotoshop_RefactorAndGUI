/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fotoshop.GUI;

import fotoshop.Events.ControllerEventSender;
import fotoshop.ColorImage;
import fotoshop.Events.EventController;
import fotoshop.Events.PropertyMessage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Panel for displaying past instances of the current working image and allowing
 * the user to select an image to be restored. Sending the command to the main
 * Editor
 * @see #UndoPanel() 
 * @see #createButtons(java.util.List) 
 * @see #setButtonColor(int, boolean) 
 * @see UndoAction
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class UndoPanel extends JPanel implements ControllerEventSender {
    /**
     * Panel containing the list of buttons for previous image instances
     */
    private final JPanel buttonPanel = new JPanel();
    /**
     * Constantly present button that will call for undo action on the last applied
     * filter
     */
    private final JButton undoButton = new JButton();
    /**
     * Focus of the undo list. The current instance that is being worked with. Highlights
     */
    private int undoFocus;
    
    /**
     * Instantiate the panel. Initialise the layout and borders of its components and
     * a header label, and the JScrollPane that will contain the panel of buttons.
     * Also adds the {@link #undoButton undoButton} to the bottom of the panel.
     */
    public UndoPanel(){
        this.setLayout(new BorderLayout(5,5));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JPanel headPanel = new JPanel();
        final JScrollPane scroll = new JScrollPane(buttonPanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createLoweredBevelBorder());
        headPanel.setLayout(new BorderLayout(5,5));
        JLabel headLabel = new JLabel("Previous Filters Applied"); 
        headLabel.setBorder(new EmptyBorder(10, 10, 0, 10));
        headPanel.add(headLabel, BorderLayout.NORTH);
        this.add(headPanel, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);    
        
        undoButton.setText("Undo Last");
        undoButton.setEnabled(false);
        undoButton.addActionListener(new UndoAction(0));
        this.add(undoButton, BorderLayout.SOUTH); 
        
        createButtons(null);
    }
    /**
     * Re-create the buttons of the panel. Removes all components from the
     * {@link #buttonPanel buttonPanel} and adds its buttons from the passed in
     * list of strings. Also adds an {@link UndoAction undoAction} listener to each
     * button with sequential indexes, giving the {@link #undoButton undoButton} 
     * a new listener.
     * @param list List of previous image instances (names) to create buttons for
     */
    public void createButtons(List<String> list){
        buttonPanel.removeAll();
        JButton undoButton;
        int n = 0;
        if(list != null){
            for(String img : list){
                undoButton = new JButton(">" + img);
                //Add UndoAction listener
                undoButton.addActionListener(new UndoAction(n));
                final int index = n;
                //Add Color changing mouse over lsitener
                undoButton.addMouseListener(new MouseAdapter(){
                    public void mouseEntered(MouseEvent evt){
                        JButton btn = (JButton)evt.getSource();
                        if(index != undoFocus){
                            btn.setForeground(Color.BLUE);
                        }
                    }
                    public void mouseExited(MouseEvent evt){
                        JButton btn = (JButton)evt.getSource();
                        if(index != undoFocus){
                            btn.setForeground(Color.BLACK);
                        }
                    }
                });
                n++;
                undoButton.setBorderPainted(false);
                undoButton.setContentAreaFilled(false);
                undoButton.setFocusPainted(false);
                undoButton.setOpaque(false);
                buttonPanel.add(undoButton);
            }
            this.undoButton.removeAll();
            this.undoButton.addActionListener(new UndoAction(n-2));
            this.undoButton.setEnabled(true);
            revalidate();
            //setButtonColor(list.size()-1);
        }
        else{
            buttonPanel.add(new JLabel("No image loaded"));
            this.undoButton.setEnabled(false);
            revalidate();
        }

    }
    /**
     * Set the colot of the int index button in the {@link #buttonPanel buttonPanel}
     * returning the rest to default colour
     * @param index The index of the button to highlight
     * @param bool boolean dictating whether or not to set this button as the 
     * undo panels {@link #undoFocus focus}
     */
    public void setButtonColor(int index, boolean bool){
        Component btns[] = buttonPanel.getComponents();
        int size = btns.length - 1;
        for(int i = 0; i <= size; i++){
            btns[i].setForeground(Color.BLACK);
        }
        if(index <= size)buttonPanel.getComponent(index).setForeground(Color.BLUE);
        if(bool)undoFocus = index;
        revalidate();
    }
    /**
     * Listener for the undo buttons. Listeners are initialised with sequential 
     * index {@link #buttonNum values}. Upon selecting the button the "undo" 
     * property change is fired with a {@link fotoshop.Events.PropertyMessage}
     * passing this index value.
     */
    public class UndoAction implements ActionListener {
        /**
         * index of the button
         */
        int buttonNum;
        /**
         * initialise with the passed in index
         * @param n int to set as the {@link #buttonNum buttonNum}
         */
        public UndoAction(int n){
            this.buttonNum = n;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(buttonNum >= 0){
                setButtonColor(buttonNum, true);
                fireEvent("undo", new PropertyMessage(null, buttonNum, true));
            }
        }
    }
    
    @Override
    public void addController(EventController c) {
        this.addPropertyChangeListener("undo", c);
    }

    @Override
    public void fireEvent(String property, PropertyMessage message) {
        firePropertyChange(property, null, message);
    }

}
