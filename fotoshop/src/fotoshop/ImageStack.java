
package fotoshop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Image stack stores a stack of {@link ColorImage ColorImage} instances up to the
 * {@link #stackSize maximum}.
 * @see #ImageStack(java.lang.String) 
 * @see #ImageStack(java.lang.String, fotoshop.ColorImage) 
 * @see #add(fotoshop.ColorImage) 
 * @see #get() 
 * @see #get(int) 
 * @see #getName() 
 * @see #getIndex() 
 * @see #getInstanceList() 
 * @see #cloneStack() s
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class ImageStack implements Cloneable{
    /**
     * Current working instance of ColorImage
     */
    private ColorImage currentImage;
    /**
     * Index of the current working instance of the ColorImage in the 
     * {@link #instances instances} list.
     */
    private int currentImageIndex = 0;
    /**
     * The name of this ImageStack object
     */
    private String name;
    /**
     * List of instances of the ColorImage object
     */
    private List<ColorImage> instances = new ArrayList<ColorImage>();
    /**
     * Maximum size of list of instances that are stored. Exceeding this value
     * removes the element at index 0.
     */
    private static final int stackSize = 10;
    
    /**
     * Initialise this ImageStack with a String name
     * @param name String to set as this objects name
     */
    public ImageStack(String name){
        this.name = name;
        currentImageIndex = 0;
    }
    /**
     * Initialise this ImageStack with a name and an initial image instance
     * @param name String to set as this objects name
     * @param firstImage First {@link ColorImage ColorImage} instance to add to 
     * this stack
     */
    public ImageStack(String name, ColorImage firstImage){
        this.name = name;
        instances.add(firstImage);
        currentImageIndex = 0;
        currentImage = firstImage;
    }
    /**
     * Set the name of this ImageStack object
     * @param name String name to apply to this object
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Retrieve the name of this ImageStack object
     * @return String name of this image
     */
    public String getName(){
        return this.name;
    }
    /**
     * Retrieve the index of the current working instance of the image
     * @return int index of the current working image instance
     */
    public int getIndex(){
        return currentImageIndex;
    }
    /**
     * Add the image to the instance list, remove any elements that exceed the 
     * stackSize
     * @param img ColorImage instance to add to the list
     */
    public void add(ColorImage img){
        if(img == null) return;
        if(instances.size() == stackSize) instances.remove(0);
        for(int i = currentImageIndex; i < (instances.size() - 1); i++){
            instances.remove(i);
        }
        instances.add(img);
        currentImage = img;
        currentImageIndex = instances.size() - 1;
    }
    
    /**
     * Retrieve the latest instance of the image
     * @return ColorImage in the last element of the instances list
     */
    public ColorImage get(){
        return currentImage;
    }
    
    /**
     * Retrieve the ColorImage instance at the particular index
     * @param index The index of the ColorImage to retrieve
     * @return ColorImage instance retrieved from the instances list
     */
    public ColorImage get(int index){
        if(index > instances.size() - 1) return null;
        this.currentImageIndex = index;
        currentImage = instances.get(index);
        return currentImage;
    }
    
    /**
     * Retrieve a list of names of past instances of the image.
     * @return List<String> of the names of image instances
     */
    public List<String> getInstanceList(){
        if(instances.size() > 0){
            List<String> names = new ArrayList<String>();
            for(ColorImage img : instances){
                names.add(img.getName());
            }
            return names;
        }
        else return null;
    }
    
    /**
     * Clone this current ImageStack object
     * @return ImageStack clone of this object
     */
    public ImageStack cloneStack(){
        ImageStack stack = null;
        try {
            stack = (ImageStack)this.clone();
        } catch(CloneNotSupportedException ex){}
        return stack;
    }
    
    
}
