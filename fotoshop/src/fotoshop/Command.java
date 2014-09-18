package fotoshop;


import fotoshop.Events.PropertyMessage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * This class is adapted from the "World of Zuul" application. 
 * 
 * Contains the String CommandWord of the command that should be performed.
 * Also contains the {@link fotoshop.Events.PropertyMessage PropertyMessage} that
 * contains any additional information to be passed to Editor commands
 * 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two strings: a command word and a second
 * word (for example, if the command was "take map", then the two strings
 * obviously are "take" and "map").
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 *
 * If the command had only one word, then the second word is <null>.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class Command
{
    /**
     * The CommandWord associated with this command
     */
    private CommandWord commandWord;
    /**
     * The secondWord string associated with this command
     */
    private PropertyMessage secondWord;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The {@link fotoshop.Events.PropertyMessage PropertyMessage}
     * second part of the Command.
     */
    public Command(CommandWord firstWord, PropertyMessage secondWord)
    {
        this.commandWord = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public CommandWord getCommandWord(){
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public Object getSecondWord()
    {
        if(secondWord != null)
            return secondWord;
        return null;
    }

    
    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    
    /**
     * Retrieve the name of the secondWord PropertyMessage
     * @return String name of the PropertyMessage
     * @see fotoshop.Events.PropertyMessage#getName() 
     */
    public String getName(){return secondWord.getName();}
    
    /**
     * Retrieve the value of the secondWord PropertyMessage
     * @return float value of the PropertyMessage
     * @see fotoshop.Events.PropertyMessage#getValue()  
     */
    public float getValue(){return secondWord.getValue();}
        
    /**
     * Retrieve the isTrue boolean of the secondWord PropertyMessage
     * @return boolean isTrue of the PropertyMessage
     * @see fotoshop.Events.PropertyMessage#getIsTrue() 
     */
    public boolean isTrue(){return secondWord.getIsTrue();}
    
    /**
     * Retrieve the name of the secondWord PropertyMessage
     * @return File assigned to the PropertyMessage
     * @see fotoshop.Events.PropertyMessage#getFile() 
     */
    public File getFile(){return secondWord.getFile();}
    
}

