/**
 * Package containing the components for the sequence panel: A panel allowing 
 * one of a number of sequences of filters to be applied to the image, and the 
 * creation of new sequences.
 * <div>
 * <b>See Also:</b><br/>
 * {@link fotoshop.GUI.SequencePanel.FilterSequencePanel}: The JPanel for the
 * application of a sequence of filters through specific buttons, or the creation
 * of a new sequence.<br/><br/>
 * {@link fotoshop.GUI.SequencePanel.SequenceConstructorPanel}: JPanel displayed
 * in a dialog for the construction of a new sequence of filters. Created by
 * entering a name into a JTextField, and selecting from a list of JButtons of 
 * the available filters. Upon selecting a filter it will appear in a JScrollPane
 * along with a JFormattedTextField, allowing the entry of a value to be applied
 * with this filter. Accepting the new filter adds a SequenceButton to the main
 * FilterSequencePanel.<br/><br/>
 * {@link fotoshop.GUI.SequencePanel.SequenceButton}: A JButton that contains
 * the name of a sequence and a list of PropertyMessages containing the name of
 * each filter to be applied.<br/>
 * </div>
 *@author Benjamin Nicholls, bn65@kent.ac.uk
 */
package fotoshop.GUI.SequencePanel;
