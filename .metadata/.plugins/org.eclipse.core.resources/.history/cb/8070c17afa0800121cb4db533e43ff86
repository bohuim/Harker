/**
 * 
 * @author Mr. Page
 * @version 070111
 * 
 * This class is based on the parser developed by Michael Kolling
 * for the simple text-based adventure game "Zork".  
 * 
 * The Parser is responsible for obtaining and processing the terminal input.  
 * Every time getCommand is called, a line of input from the terminal is read and
 * processed.  The line is separated into tokens using white space as a delimiter.
 * The Java String class split method is used to tokenize the input stream.
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Parser
{
    private static final int NUMWORDS = 4;  // number of words in a command - default is 2
    private BufferedReader in;
    
    /**
     * Constructor - wrap a BufferedReader around System.in.  A possible extension
     * is to wrap a BufferedReader around a FileInputStream to allow for automated
     * testing.
     */
    public Parser()
    {
        in = new BufferedReader(new InputStreamReader(System.in));
    }
    /**
     * read and process a line of input
     * The first token extracted must be a valid word in CommandWords.  If it is not,
     * it is set to null.  The rest of the tokens are not checked.
     * @return a Command object that encapsulates the current parsed command
     * 
     * The String split method returns an array of Strings (tokens) of at least
     * length 1.  If there are more than NUMWORDS command words, the remainder of the 
     * input stream is returned as array element NUMWORDS.  The regular expression 
     * '/s' si predefined as white space.
     * 
     */
    public Command getCommand()
    {
        String inLine = "";
        try
        {
            inLine = in.readLine();
        }
        catch (IOException e)
        {
            // print error message and die
            System.out.println(" error reading from the terminal " + e);
            System.exit(0);
        }

        String[] tokens = inLine.split("\\s", NUMWORDS+1);
        
        // change first token to null if it is invalid
        //if(!CommandWords.isCommand(tokens[0])) tokens[0] = null;   
        
        // create a Command object that encapsulates this array of tokens
        return new Command(tokens);
    }
}
