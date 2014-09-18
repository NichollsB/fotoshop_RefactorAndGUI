package fotoshop;


import fotoshop.resources.ResourceManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is derived from the "World of Zuul" application,
 * author Michael Kolling and David J. Barnes,
 * version 2006.03.30
 * This class holds an enumeration of all command words known to the editor.
 * It is used to recognise commands as they are typed in.
 *
 * @version 2013.09.09
 */

public class CommandWords
{
    /**
     * Map of String keys and associated {@link CommandWord CommandWords}.
     */
    private HashMap<String, CommandWord> validCommands;


    /**
     * <div>CommandWords - initialise the command words by retrieving those defined
     * within the ResourceManagers CommandWords bundle</div>
     * <div>- if no CommandWords are found, will default to originally set 
     * validCommands values</div>
     */
    public CommandWords()
    {   
        //Initialise the validCommands Map
        validCommands = new HashMap<String, CommandWord>();
        //Find the localised alternative commands
        Map<String, String> alternativeStrings = ResourceManager.getCommandWords();
        String commandString;
        
        for(CommandWord word : CommandWord.values()){
            if(word != CommandWord.UNKNOWN){
                validCommands.put(word.toString(), word);
                if(alternativeStrings != null){
                    //if there is a localised file available matching the command, use this
                    commandString = word.name();
                    if(alternativeStrings.containsKey(commandString)){
                        validCommands.remove(word.toString());
                        validCommands.put(alternativeStrings.get(commandString), word);
                    }
                    //otherwise use the default value of the CommandWord enum
                    else{
                        validCommands.put(word.toString(), word);
                    }
                }
                
            }
        }
        
    }

    /**
     * Check whether a given String is a valid command word. 
     * @param aString The string to check for in the validCommands map
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }
    

    /**
     * Retrieve a list/string of the keys within the commands Map
     * @return the command words
     */
    public List<String> getCommandWords(){
        List<String> words = new ArrayList<String>();
        for(String key : validCommands.keySet()){
            words.add(key);
        }
        return words;
    }
    
    /**
     * Retrieve the CommandWord associated with the given key
     * @param key The string to look for in the validCommand Map
     * @return The CommandWord associated with the given key
     */
    public CommandWord getCommand(String key){
        if(validCommands.containsKey(key))
            return validCommands.get(key);
        else
            return CommandWord.UNKNOWN;
    }
}
