/**
 * 
 * @author Mr. Page
 * @version 070111
 * This class encapsulates a command for the Hark adventure game. An array of String
 * objects holds the command words as determined by the parser.
 * 
 *
 */
public class Command
{
    private String[] words;
/**
 * constructor copies the input array into the instance field since we don't know
 * what will happen to the input array.    
 * @param in the array of String objects to copy into words
 */
    public Command (String[] in)
    {
        words = new String[in.length];
        for(int i = 0; i < in.length; i++) 
        	words[i] = in[i];
    }
    /**
     * get the ith command word (counting is 1 based to be consistent with original
     * Zork game.)
     * @param i - the command word to get (1 based)
     * @return - the ith command word or null if out of bounds
     */
    public String getCommandWord(int i)
    {
        if(i < 1 || i > words.length) return null;
        return words[i-1];
    }
    /**
     * checks to see if there is a nth word in this command
     * @param n - the word whose existence we want to check (1 based)
     * @return true if there are at least n words
     */
    public boolean hasWord(int n) {return words.length >= n;}
    /**
     * utility function to check to see if the first word of the command
     * is a valid word according to CommandWords
     * @return true if the command is valid (words[0])
     */
    public boolean isUnknown() 
    {   if(words[0] == null ) return true;
        return !CommandWords.isCommand(words[0]);
    }
}
