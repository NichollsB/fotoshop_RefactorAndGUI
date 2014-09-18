
package fotoshop.Filter;

import fotoshop.ColorImage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * BrightnessFilter child of Filter object. Adjusts the brightness of the image
 * passed in
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class BrightnessFilter extends Filter {

    public BrightnessFilter(String name){
        super.setName(name);
    }
        /**
     * apply the BrightnessFilter, adjusting the targets brightness
     * @param image the image for the filter to be applied to.
     * @return ColorImage image with adjusted brightness
     */
    @Override
    public ColorImage applyFilter(ColorImage image, float i){
        BufferedImage imageOut = adjustBrightness(image, new ColorImage(image, ""), i);
        return new ColorImage(imageOut, this.getName());
    }
    /**
     * Adjust the brightness of the image passed in
     * @param imageIn ColorImage to adjust
     * @param imageOut The output image
     * @param adjustment float value to adjust the brightness by
     * @return BufferedImage with an adjusted brightness
     */
    private BufferedImage adjustBrightness(ColorImage imageIn, ColorImage imageOut, float adjustment){
        RescaleOp adjusted = new RescaleOp(adjustment, 0, null);
        return adjusted.filter((BufferedImage) imageIn, (BufferedImage) imageOut);
    }

}
