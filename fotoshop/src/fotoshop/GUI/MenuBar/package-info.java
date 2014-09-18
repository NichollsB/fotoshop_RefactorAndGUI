/**
 * Package containing the components for the GUI Jframes JMenuBar, and the 
 * specific Dialog window JPanels for each filter that may be applied. If there
 * is no specific filter window (i. for MonoFilter) then it shall be applied without 
 * any values pertaining to the filter, such as a percentage (BrightnessFilter),
 * or a number of applications to apply (RotateFilter).
 * <div>
 * <b>See Also:</b><br/>
 * {@link fotoshop.GUI.MenuBar.FileMenu}: JMenu for displaying file menu options such
 * as "open", "save", and "quit". Open and save will open a FileWindow.<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp; {@link fotoshop.GUI.MenuBar.FileWindow}: JFileChooser for displaying the open or
 * save dialogs and holding the last directory used.<br/><br/>
 * {@link fotoshop.GUI.MenuBar.FilterMenu}: JMenu for displaying the list of
 * filters that are available. Selecting a filter item may open a FilterPanel:<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;{@link fotoshop.GUI.MenuBar.FilterPanel}: Superclass 
 * JPanel for the specific filter windows. Each subclasses contains unique components
 * for adjusting the filter.<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;{@link fotoshop.GUI.MenuBar.FilterPanel_Brightness}:
 * Subclass of FilterPanel, applies a JSlider to the filter dialog which may be
 * used to adjust the brightness of the image.<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;{@link fotoshop.GUI.MenuBar.FilterPanel_Rotate}:
 * Subclass of FilterPanel, applies a JButton to the filter dialog which may be
 * used to rotate the image repeatedly.<br/>
 * </div>
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
package fotoshop.GUI.MenuBar;
