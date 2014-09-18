
package fotoshop.Filter;

import fotoshop.ColorImage;
import java.awt.Color;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * <div>Derived from the suggestions made in "Objects First with Java" by Michael
 * Kolling and David Barnes, along with the methods used originally within the 
 * Editor class of the prototype Fotoshop project. 
 *  * @author Benjamin Nicholls
 * </div>
 * <div> 
 * MonoFilter applies a monochrome effect to the image.
 * </div>
 */
public class MonoFilter extends Filter {
    /**
     * Sets the name of the filter via the super
     * @param name The name of the filter
     */
    public MonoFilter(String name){
        super.setName(name);
    }
        
    /**
     * apply the MonoFilter, making the target image monochrome
     * @param image the image for the filter to be applied to.
     * @return imageOut return a monochrome duplicate of the parameter image
     */
    @Override
    public ColorImage applyFilter(ColorImage image){
        ColorImage imageOut = new ColorImage(image, this.getName());
        //Graphics2D g2 = currentImage.createGraphics();
        int height = imageOut.getHeight();
        int width = imageOut.getWidth();
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                Color pix = imageOut.getPixel(x, y);
                int lum = (int) Math.round(0.299*pix.getRed()
                                         + 0.587*pix.getGreen()
                                         + 0.114*pix.getBlue());
                imageOut.setPixel(x, y, new Color(lum, lum, lum));
            }
        }
        return imageOut;
    }
    /**
     * Default applyFilter method, calls the {@link #applyFilter(fotoshop.ColorImage) applyFilter}
     * method. 
     * @param image The ColorImage image to apply the filter to
     * @param i May be 0 value, has no effect in this filter
     * @return The monochrome version of the input image
     */
    @Override
    public ColorImage applyFilter(ColorImage image, float i){
        return applyFilter(image);
    }

}
