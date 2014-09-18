
package fotoshop;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */
public enum CommandWord
{
    /**
     * Open file...
     */
    OPEN("open"), 
    /**
     * Save current image as...
     */
    SAVE("save"),
    /**
     * Quit the program.
     */
    QUIT("quit"), 
    /**
     * Call help method.
     */
    HELP("help"), 
    /**
     * Put an image to the cache as name...
     */
    PUT("put"), 
    /**
     * Retrieve an image of name from cache...
     */
    GET("get"),
    /**
     * Apply a filter...
     */
    APPLY("apply"),
    /**
     * Undo the last filter.
     */
    UNDO("undo"), 
    /**
     * Call the look method to display image information.
     */
    LOOK("look"), 
    /**
     * Perform a series of actions from a given script...
     */
    SCRIPT("script"),
    /**
     * Command word is unknown
     */
    UNKNOWN("?"),
    /**
     * Apply the ROT90 filter (replaced by apply *filter*, but kept in for
     * SCRIPT purposes
     */
    ROT90("rot90"),
    /**
     * Apply the MONO filter (replaced by apply *filter*, but kept in for
     * SCRIPT purposes
     */
    MONO("mono");

    /**
     * The command string.
     */
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}