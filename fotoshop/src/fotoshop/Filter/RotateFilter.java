
package fotoshop.Filter;

import fotoshop.ColorImage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * <div>Derived from the suggestions made in "Objects First with Java" by Michael
 * Kolling and David Barnes, along with the methods used originally within the 
 * Editor class of the prototype Fotoshop project. 
 *  * @author Benjamin Nicholls
 * </div>
 * <div> 
 * MonoFilter applies a 90 degree rotation to the image.
 * </div>
 */
public class RotateFilter extends Filter {
    /**
     * Initialise the RotateFilter.
     * @param name The name to be given to the filter
     */
    public RotateFilter(String name){
        super.setName(name);
    }
    
    /**
     * The default applyFilter method calls the {@link #applyFilter(fotoshop.ColorImage) applyFilter}
     * method i number of times
     * @param image the image to be rotated
     * @param i the number of times to apply this filter. i=1 will rotate 90 degrees,
     * i = 2 will rotate twice, rotating 180 degrees
     * @return imageOut return a rotated duplicate of the param image
     */
    @Override
    public ColorImage applyFilter(ColorImage image, float i){
        ColorImage rotImage = new ColorImage(image, this.getName());
        for(int j = 0; j < i; j++){
            rotImage = applyFilter(rotImage);
        }
        return rotImage;
    }
    /**
     * apply the RotateFilter, rotating 90 degrees
     * @param image
     * @return ColorImage rotated 90 degrees
     */
    @Override
    public ColorImage applyFilter(ColorImage image){
        ColorImage currentImage = image;
         // R90 = [0 -1, 1 0] rotates around origin
        // (x,y) -> (-y,x)
        // then transate -> (height-y, x)
        int height = currentImage.getHeight();
        int width = currentImage.getWidth();
        ColorImage rotImage = new ColorImage(height, width, this.getName());
        for (int y=0; y<height; y++) { // in the rotated image
            for (int x=0; x<width; x++) {
                Color pix = currentImage.getPixel(x,y);
                rotImage.setPixel(height-y-1,x, pix);
            }
        }
        return rotImage;
    }
}
