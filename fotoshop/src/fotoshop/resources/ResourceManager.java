/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fotoshop.resources;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <div>ResourceManager simply contains methods used for obtaining resources for 
 * textual and other language dependant data.
 *  * @author Ben</div>
 * <div>Editor messages, Command Words and Filter names may be included within:
 * <br>
 * <b>EditorText.properties :</b> Holds alternatives to the default editor messages.
 * This file MUST be included for any text messages to appear
 * <br>
 * <b>CommandWord.properties :</b> Holding any alternatives to the default command words.
 * Any new command words must be added to the CommandWord enum class
 * <br>
 * <b>Filter.properties :</b> Holds the names for any filters that are available.
 * Additional filters MUST be added in the Editor class filterMap.
 * </div>

 */
public abstract class ResourceManager {
    private static Locale locale;
    private static ResourceBundle commandBundle;
    private static ResourceBundle filterBundle;
    private static ResourceBundle editorBundle;
    static {
        locale = Locale.getDefault();
        try{
            commandBundle = ResourceBundle.getBundle("fotoshop/resources/CommandWords", locale);
        }
        catch (MissingResourceException r){
            System.out.println("ERROR");
        }
        try{
            filterBundle = ResourceBundle.getBundle("fotoshop/resources/Filters", locale);
        }
        catch (MissingResourceException r){
            System.out.println("ERROR");
        }
          try{
            editorBundle = ResourceBundle.getBundle("fotoshop/resources/EditorText", locale);
        }
        catch (MissingResourceException r){
            System.out.println("ERROR");
        }
    }

    /**
     * Finds the text associated with the textKey. Can be used to find
     * internationalised versions of Editor display messages
     * @param textKey The message key to find in the editorBundle
     * @return Returns the string associated with the parameter key
     */
    public static String getEditorText(String textKey){
        try{
            return editorBundle.getString(textKey);
        } 
        catch(MissingResourceException r){
            try{
                return editorBundle.getString("missingKey");
            } 
            catch(MissingResourceException r2){}
        }
        return null;
    }

    /**
     * Finds the text associated the command words. Can be used to find
     * internationalised versions of the command words
     * @return Returns a map of the command word text
     */
    public static Map<String, String> getCommandWords(){
        System.out.println("Getting command words");
        Map<String, String> commandMap = new HashMap<String, String>();
        Enumeration<String> keys1 = commandBundle.getKeys();        
        try{
            Enumeration<String> keys = commandBundle.getKeys();
            while (keys.hasMoreElements()){
                String key = keys.nextElement();
                String value = commandBundle.getString(key);
                commandMap.put(key, value);
            }
            return commandMap;
        } 
        catch (NullPointerException ex){
            System.out.println("Resource Error");
            return null;
        }
    }
    
    /**
     * Finds the text associated with the filter. Can be used to find
     * internationalised versions of filter name
     * @param filterKey The key of the filter to find in the filterBundle
     * @return Returns the filter name associated with the parameter key
     */
    public static String getFilter(String filterKey){
        try{
            return filterBundle.getString(filterKey);
        } 
        catch(MissingResourceException r){
            return null;
        }
    }
}
