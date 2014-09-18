
package fotoshop;

import fotoshop.resources.ResourceManager;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.*;

/**
 *
 * @author Ben
 */
public abstract class FileManager {
    
    public FileManager(){

    }

    public static ColorImage loadImage(File file) {
        ColorImage img = null;
        try {
            img = new ColorImage(ImageIO.read(file), file.getName());
        } catch (IOException e) {
            System.out.println(ResourceManager.getEditorText("fileError") + file);
            System.out.println(ResourceManager.getEditorText("directoryPrompt") + System.getProperty("user.dir"));
        }
        return img;
    }
    
    public static void SaveImage(ColorImage image, File destFile){
        try {
            System.out.println("Saving");
            //File outputFile = new File(fileName);
            ImageIO.write(image, "jpg", destFile);
            System.out.println(ResourceManager.getEditorText("savedTo") + destFile.getName());
        } catch (IOException e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
    }
    public void actionPerformed(ActionEvent e){

    }
    
}
