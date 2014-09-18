/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fotoshop;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ben
 */
public class ImageCache {
    /**
     * Maximum number of items that may be stored in the cache.
     * The default 0 == infinite. May be altered to limit the number.
     */
    private final static int size_MAXIMUM = 5;
    /**
     * Map of the stored images. Containing the String name given to this 
     * instance of the image, and the ImageStack containing the image
     */
    private final Map<String, ImageStack> imageCache = new LinkedHashMap<String, ImageStack>();
    
    public ImageCache(){
        
    }
    /**
     * Add an ImageStack image to the cache as name
     * @param image ImageStack instance to add to the cache
     * @param name String name to store the image as
     */
    public void put(ImageStack image, String name){
        imageCache.put(name, image);
        
    }
    
    /**
     * Get the image stored as name from the cache
     * @param name String name to look up in the cache
     * @return ImageStack instance retrieved from the cache, null if it does not contain this key
     */
    public ImageStack get(String name){
        ImageStack img = (imageCache.containsKey(name)) ? imageCache.get(name) : null;
        return img;
    }
    
    /**
     * Retrieve a list of names of the name-ImageStack pairs stored in the cache
     * @return List<String> of cached image names
     */
    public Map<String, BufferedImage> getCachedImages(){
        Map<String, BufferedImage> map = new LinkedHashMap<String, BufferedImage>();
        for(String key : imageCache.keySet()){
            map.put(key, imageCache.get(key).get());
        }
        return map;
    }
    
}
