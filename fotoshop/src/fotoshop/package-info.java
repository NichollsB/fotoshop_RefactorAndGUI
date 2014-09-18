/**
 * Details of the packages used within the fotoshop application
 * 
 * @author Benjamin Nihcolls, bn65@kent.ac.uk
 */

/**
 * fotoshop package contains the primary image editor and various command 
 * processing methods.
 * <div><b>See Also:</b><br/>
 * {@link fotoshop.ColorImage}: Represents the images being worked on in
 * the application. Extends BufferedImage.<br/>
 * {@link fotoshop.Command}: A command to be processed by the application.<br/>
 * {@link fotoshop.CommandWord}: Enum of the available commands in the application.<br/>
 * {@link fotoshop.CommandWords}: Contains all the available viable commands and
 * translated versions.<br/>
 * {@link fotoshop.Editor}: Main editor of the application. Processes commands and
 * sends relevant updates to other classes.<br/>
 * {@link fotoshop.FileManager}: Handles the saving and opening of files.<br/>
 * {@link fotoshop.ImageCache}: A cache of images being worked on. Contains a map
 * of ImageStack objects and methods for adding to, or retrieving from this map<br/>
 * {@link fotoshop.ImageStack}: Stores a particular image and its various instances.
 * Includes methods for adding to or retrieving images from this stack.<br/>
 * {@link fotoshop.Main}: Simply creates the main {@link fotoshop.GUI.GUI GUI}
 * and {@link fotoshop.Editor Editor} objects..<br/>
 * </div>
 */
package fotoshop;