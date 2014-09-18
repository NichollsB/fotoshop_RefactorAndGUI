
package fotoshop.Events;

import java.io.File;

/**
 * PropertyMessage is used to pass various values to the Editor 
 * {@link fotoshop.Editor#processCommand(fotoshop.Command) process command} method
 * via a {@link fotoshop.Command Command} object.
 * @author Benjamin Nicholls, bn65@kent.ac.uk
 */
public class PropertyMessage {
    /**
     * String name of the property message
     */
    private String name = null;
    /**
     * float value of the property message
     */
    private float value = 0;
    /**
     * boolean value of the property message
     */
    private boolean isTrue = false;
    /**
     * File given to the property message
     */
    private File file = null;
    
    public PropertyMessage(){}
    public PropertyMessage(String name){this.name = name;}
    public PropertyMessage(File file){this.file = file;}
    public PropertyMessage(String name, float value, boolean bool){this.name = name; this.value = value; this.isTrue = bool;}
    public PropertyMessage(String name, float value, boolean bool, File file){
        if(name != null)this.name = name;
        if(value != 0)this.value = value;
        if(bool != false)this.isTrue = bool; 
        if(file != null)this.file = file;
    }
    //GET
    /**
     * Get the name
     * @return String name
     */
    public String getName(){return name;}
    /**
     * Get the float value
     * @return float value
     */
    public float getValue(){return value;}
    /**
     * Get the isTrue boolean
     * @return boolean value
     */
    public boolean getIsTrue(){return isTrue;}
    /**
     * Get the File
     * @return File assigned to this message
     */
    public File getFile(){return file;}
    
    //SET
    /**
     * Set the String name
     * @param name String to set as the name
     */
    public void setName(String name){this.name = name;}
    /**
     * Set the float value
     * @param value float to set as the value
     */
    public void setValue(float value){this.value = value;}
    /**
     * Set the boolean isTrue value
     * @param isTrue boolean to set as isTrue
     */
    public void setIsTrue(boolean isTrue){this.isTrue = isTrue;}
    /**
     * Set the File file
     * @param file File to set as file
     */
    public void setFile(File file){this.file = file;}
}
