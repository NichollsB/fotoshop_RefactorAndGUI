
package fotoshop.GUI.MenuBar;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The JFileChooser for choosing a file to open, or to save to.
 * @see #FileWindow(javax.swing.JFrame) 
 * @see #open() 
 * @see #save() 
 * @see #getLocation(int) 
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class FileWindow extends JFileChooser{
    /**
     * Current working directory to look in with the JFileChooser. Set by default
     * to the "users.dir", but updated to the last 
     * {@link #getLocation() directory} opened or saved to.
     */
    private File dir = new File(System.getProperty("user.dir"));
    /**
     * Main GUI JFrame
     */
    private JFrame frame = new JFrame();
    
    /**
     * Instantiate FileWindow JFileChooser with the main GUI JFrame, the current
     * directory, and a FileFilter which should ensure image suffixes.
     * @param frame 
     */
    public FileWindow(JFrame frame){
        this.setCurrentDirectory(dir);
        this.frame = frame;
        // Get array of available formats
        String[] suffices = ImageIO.getReaderFileSuffixes();

        // Add a file filter for each one
        for (int i = 0; i < suffices.length; i++) {
            FileFilter filter = new FileNameExtensionFilter(suffices[i] + " files", suffices[i]);
            this.addChoosableFileFilter(filter);
        }
    }
    /**
     * ShowOpenDialog and return the File to open
     * @return File to attempt to open
     * @see #getLocation(int) 
     */
    public File open(){
        return getLocation(this.showOpenDialog(frame));
    }
    /**
     * ShowSaveDialog and return the File to save to.
     * @return File to attempt to save to
     * @see #getLocation(int) 
     */
    public File save(){
        return getLocation(this.showSaveDialog(frame));
    }
    /**
     * If the input choice is JFileChooser.APPROVE_OPTION retrieve, or create a 
     * File to save or open to. Also update the most recent {@link #dir directory}
     * to open the save or open dialog.
     * @param choice The choice returned by the JFileChooser.showOpenDialog/.showSaveDialog
     * @return File to save to, or open from.
     */
    private File getLocation(int choice){
        this.setCurrentDirectory(dir);
        if(choice == JFileChooser.APPROVE_OPTION){
            File file = this.getSelectedFile();
            dir = file;
            return file;
        }
        return null;
    }
    
}
