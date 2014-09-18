
package fotoshop.GUI.SequencePanel;

import fotoshop.Events.PropertyMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;

/**
 * JButton Storing a {@link #name name}, and a {@link #sequence sequence} list of filter names.
 * @see #SequenceButton(java.util.List) 
 * @see #addToSequence(java.lang.String, float) 
 * @see #setName(java.lang.String) 
 * @see #getName() 
 * @see #getSequence() 
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class SequenceButton extends JButton{
    /**
     * Sequence of {@link fotoshop.Events.PropertyMessage} objects, each containing
     * the name of a filter to apply.
     */
    private List<PropertyMessage> sequence = new ArrayList<PropertyMessage>();
    /**
     * Name of this button
     */
    private String name;
    
    /**
     * Intiialise the button with the list of PropertyMessages with filter names
     * to apply as the {@link #sequence} list
     * @param filterList List of PropertyMessages to apply to the sequence
     * @see fotoshop.Events.PropertyMessage
     */
    public SequenceButton(List<PropertyMessage> filterList){
        sequence = filterList;
    }
    /**
     * Add a filter name to the sequence list. Creates a new PropertyMessage
     * with the perameter values
     * @param filterName String name to give the new PropertyMessage sequence entry
     * @param value float value to give the new PropertyMessage sequence entry
     * @see #sequence
     * @see fotoshop.Events.PropertyMessage
     */
    public void addToSequence(String filterName, float value){
        sequence.add(new PropertyMessage(filterName, value, true));
    }
    /**
     * Set this buttons name
     * @param n String to apply as the name and text of this button
     */
    public void setName(String n){
            this.name = n;
            this.setText(n);
    }
    /**
     * get this buttons name
     * @return String name of this button
     */
    public String getName(){
        return name;
    }
    /**
     * Retrieve the sequence list
     * @return List of PropertyMessage objects
     * @see #sequence
     * @see fotoshop.Events.PropertyMessage
     */
    public List<PropertyMessage> getSequence(){
        return sequence;
    }
}
