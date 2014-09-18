package fotoshop;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Extends standard BufferedImage class with convenience functions
 * for getting/setting pixel values using the standard Color class
 * and converting the raster to a standard 24-bit direct colour format.
 *
 * Based on class OFImage described in chapter 11 of the book
 * "Objects First with Java" by David J Barnes and Michael Kolling
 * (from 2nd edition onwards).
 *
 * @author  Michael Kolling, David J. Barnes and Peter Kenny
 * @version 1.0
 */

public class ColorImage extends BufferedImage
{
    /**
     * List of filter names that have been applied to this instance of the image
     */
    private List<String> appliedFilters;
    /**
     * The name of this image
     */
    private String imageName;
    /**
     * Used in the {@link Editor#restoreImage() undo} functionality. The
     * previous instance of this image.
     */
    private ColorImage previousImage;
    /**
     * Create a ColorImage copied from a BufferedImage
     * Convert to 24-bit direct colour
     * @param image The image to copy
     * @param name The name to be given to this image
     */
    public ColorImage(BufferedImage image, String name)
    {
        super(image.getWidth(), image.getHeight(), TYPE_INT_RGB);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                setRGB(x, y, image.getRGB(x,y));
            }
        }
        imageName = name;
        this.appliedFilters = new ArrayList<String>();
    }

    /**
     * Create a ColorImage with specified size and 24-bit direct colour
     * @param width The width of the image
     * @param height The height of the image
     * @param name The name to be given to this image
     */
    public ColorImage(int width, int height, String name)
    {
        super(width, height, TYPE_INT_RGB);
        imageName = name;
        previousImage = null;
    }

    /**
     * Set a given pixel of this image to a specified color.
     * The color is represented as an (r,g,b) value.
     * @param x The x position of the pixel
     * @param y The y position of the pixel
     * @param col The color of the pixel
     */
    public void setPixel(int x, int y, Color col)
    {
        int pixel = col.getRGB();
        setRGB(x, y, pixel);
    }

    /**
     * Get the color value at a specified pixel position
     * @param x The x position of the pixel
     * @param y The y position of the pixel
     * @return The color of the pixel at the given position
     */
    public Color getPixel(int x, int y)
    {
        int pixel = getRGB(x, y);
        return new Color(pixel);
    }
    
    //Additional
    /**
     * Adds filter names to the list of filters applied to this image
     * @param filters Applied filter names to be added to the image
     */
    public void addFilters(List<String> filters){
        this.appliedFilters = new ArrayList<String>();
        this.appliedFilters.addAll(filters);
    }
    
    /**
     * Retrieve the names of filters that are applied to this image
     * @return Return a list of applied filter names
     */
    public List<String> getFilters(){
       for(String s : appliedFilters){
           System.out.println(s);
       }
        return this.appliedFilters;
    }
    
    /**
     * Retrieve the image name
     * @return Return a String name associated with this filter
     */
    public String getName(){
        return this.imageName;
    }

}
