
/**
 * Write a description of class WordGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.Random;

public class WordGame
{
    // instance variables 
    private WordGameDisplay display;
    
    private String userInput;
    private String input;
    private String output;
    private String history;
    private int submitCount;
    
    private String reverseString;
    
    private String giantString;
    private int giantStringIndex;
    private String searchWord;
    private int occurrences;
    private int searchIndex;
    
    private int chancesLeft;
    private String guessField;
    private int random;
    private int wordIndex;
    private boolean letterAppears;
    private String usedField;
    private String[] wordLetters;
    private String[] usedLetters;
    private String[] word;
    private String[] usedWords;
 
  //constants
    private static final String BASIC_HANGMAN_MESSAGE = "Guess a Letter or the Word" + "\n" + "Guessing a Word wrong will cost you 2 chances!" + "\n";
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int numberOfWords = 40158;
    
    
    public WordGame()
    {
        display = new WordGameDisplay();
    }
    
    /** getWords reads a dictionary and inputs them into an
     * array of strings
     *@param none
     *@return none
     */
    private void getWords()
    {
        word = new String[numberOfWords];
        try
        {
            BufferedReader in = new BufferedReader(new FileReader("/Users/dennis/Dropbox/Programming/Java/WordGame/src/bigdictionary.txt")); 
            String line = in.readLine();
            for (int i = 0; i < numberOfWords; i ++)
            {
                word[i] = line;
                line = in.readLine();
            }
            in.close();
        }
        // in case of error ...
        catch(IOException e)
        {
            RuntimeException up = new RuntimeException(e);
            throw up;
        }
    }
    
    /** the echo game method
     *@param none
     *@return none
     */
    private void echo()
    {
        display.setTitle("The Echo Game");
        
        getWords();
        submitCount = 1;
        history = "Echo Game" + "\n" + "Please Enter a Word" + "\n";
        display.setText(history);
        
        while (continueGame())
        {
            userInput = display.getGuess();
            
            if (isInDictionary())
                history = history + submitCount + ". " + "\"" + userInput + "\"" + " is a word!" + "\n" + "  Enter another word" + "\n";
            else 
                history = history + submitCount + ". " + "\"" + userInput + "\"" + " is not a word!" + "\n" + "  Enter another word" + "\n";
            display.setText(history);
            
            submitCount++;
        }
    }
    /**checks to see if entered word is in the dicitonary
     *@param none
     *@return true or false
     */
    private boolean isInDictionary()
    {
        for (int i = 0; i < numberOfWords; i++)
        {
            if (word[i].equals(userInput.toLowerCase()))
                return true;
        }
        return false;
    }
    
    
    /**converts an input string into with only lower case letters and returns a output string
     * @param a string
     * @return a string that discards all punctuation and turns all letters into lowercase
     */
    private String onlyLetters(String h)
    {
        output = "";
        input = h.toLowerCase();
        for (int i = 0; i<input.length(); i++)
        {
            int diff = input.substring(i,i+1).compareTo("a");
            if (diff >= 0 && diff <= 25)
                output = output + input.substring(i,i+1);
        }
        return output;
    }
    /** runs the lower case letters program
     *@param none
     *@return none
     */
    public void lowerCaseLetters()
    {
        display.setTitle("Only Lower Case");
        
        submitCount = 1;

        history = "" + "Lower Case Converter" + "\n" + "Please Enter a String";
        display.setText(history);
        
        while (continueGame())
        {
            userInput = display.getGuess();
            
            history = history + "\n" + submitCount + ". " + onlyLetters(userInput) + "\n" + "Please Enter Another String"; 
            display.setText(history);
            
            submitCount++;
        }
    }
    
 
    /**checks if an input string is a palindrome or not, returns accordingly 
     * @param a string
     * @return true if input length is 1 or the reverse is equals input; false if neither of those
     */
    private boolean isPalindrome(String h)
    {
        if (h.length() == 1)
            return true;
        else if (assembleBackwards(h).equals(h))
            return true;
        return false;
    }
    /**reassembles an input string backwards into a string named reverseString
     *@param a string
     *@return a string that is the reverse of the input
     */
    private String assembleBackwards(String h)
    {
        reverseString = "";
        for (int i = h.length(); i >= 1; i--)
            reverseString = reverseString + h.substring(i-1,i);
        return reverseString;
    }
    /**takes a input: converts it into lower case letters, then checks the input with its reverseString
     * @param none
     * @return none
     */
    public void palindrome()
    {
        display.setTitle("Palindrome");
        
        userInput = " ";
        submitCount = 1;
        
        history = "Panlindrome" + "\n" + "Please Enter a Palindrome or Nothing to Stop Playing";
        display.setText(history);
        
        while (continueGame() && !(userInput.length()==0))
        {
            userInput = display.getGuess();
            
            if (userInput.equals("stop playing") || userInput.equals("another game")) {}
            else if (userInput.length()==0) 
                history = history + "\n" + "End of Palindrome";
            else if (isPalindrome(onlyLetters(userInput)))
                history = history + "\n" + submitCount + ". " + "\"" + userInput + "\"" + " is a palindrome";
            else
                history = history + "\n" + submitCount + ". " + "\"" + userInput + "\"" + " is not a palindrome";
            display.setText(history);
            
            submitCount++;
        }
    }
    
    
    
   /**
       * readInput takes in a string containing a file name and
       * then wraps a BufferedReader around a FileReader for that
       * file. If the file is not found, or any other IO error
       * occurs, an exception is thrown.
       * @param filename is the input file name
       * @return a String containing the input data
    */
    private String readInput(String fileName)
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            // read a line of text into the variable line
            // end of line characters are discarded
            // you may want to put them back!
            String line = in.readLine() + " ";
            String lines = "";
            while (line != null)
            {
                lines = lines + line;// add code to append line to lines
                line = in.readLine() + " ";
            }
            in.close();
            return lines;
        }
        // in case of error ...
        catch(IOException e)
        {
            RuntimeException up = new RuntimeException(e);
            throw up;
        }
    }
    /** countWords compares the text file and the user input 
     * and adds 1 to numberOfWords every time it finds 
     * a matching word in the text file
     *@param string created from reading a text file, string the user inputed 
     *@return number of times the inputed word appears
     */
    private int countOccurrences(String h, String k)
    {
       occurrences = 0;
       searchWord = userInput.toLowerCase();
       giantStringIndex = 0;
       
       while (giantStringIndex < h.length()-k.length())
       {
           while (giantStringIndex < h.length()-k.length() && !k.substring(0,1).equals(h.substring(giantStringIndex,giantStringIndex+1)))
               giantStringIndex++;
           
           giantStringIndex++;
           for (int i = 1; i < k.length(); i++)
           {
               if (k.substring(i,i+1).equals(h.substring(giantStringIndex,giantStringIndex+1)))
               {
                   giantStringIndex++;
                   searchIndex++;
               }
           }
           if(searchIndex+1==k.length()) occurrences++;
           searchIndex = 0;
       }
       
       return occurrences;
    }
    /** countIt is the actual game method
     *It sets the title and initial message and runs the game
     *It receives int numberOfWords and sets text accordingly
     *@param none
     *@return none
     */
    public void countIt()
    {
       display.setTitle("Count Occurrences");
       
       resetCountIt();
       history = "" + "Count Occurrences" + "\n" + "Please Enter a File Location";
       display.setText(history);
       
       userInput = display.getGuess();
       if (continueGame())
       {
           giantString = readInput(userInput).toLowerCase(); 
           history = history + "\n" + "Processing file..." + "\n" + "Please Enter a Word";
           display.setText(history);
           
           userInput = display.getGuess();
           while (continueGame())
           {
               searchWord = userInput.toLowerCase();
               
               history = history + "\n" + submitCount + ". " + "\"" + userInput + "\"" + " occurs " + countOccurrences(giantString, searchWord) + " times."
               + "\n" + "Please Enter a Word";
               display.setText(history);
               
               submitCount++;
               
               userInput = display.getGuess();
           }
       }
    }
    /** resets all values used in countIt
     *@param none
     *@return none
     */
    private void resetCountIt()
    {
        giantString = "";
        giantStringIndex = 0;
        searchWord = "";
        occurrences = 0;
        searchIndex = 0;
        submitCount = 1;
    }
    
    /** masterGame sets the title as "Word Games"
     *and runs callGame until user wants to stop playing,
     *upon which it sets the text as "Game Over"
     *@param none
     *@return noen
     */
    public void masterGame()
    {
        display.setTitle("Word Games");
        
        userInput = "";
        
        while (!userInput.equals("stop playing")) callGame();
        display.setText("Game Over!");
    }
    /** callGame calls the game the user wants
     *and each game is modified to exit the game when the 
     *user wants to play another game, upon which 
     *callGame asks to enter the name of a game again
     *@param none
     *@return none
     */
    private void callGame()
    {   
        history = "" + "Select a Game:" + "\n" 
                    + "     1. Echo Game" + "\n" 
                    + "     2. Lower Case Converter" + "\n" 
                    + "     3. Palindrome" + "\n"
                    + "     4. Count It" + "\n" 
                    + "     5. Hangman" + "\n"
                    + "Type " + "\"" + "Another Game" + "\"" + " to switch games or " + "\"" + "Stop Playing" + "\"" + " to stop playing at anytime";
        display.setText(history);
        
        userInput = display.getGuess().toLowerCase();
        if (userInput.equals("stop playing")) {}
        else if (userInput.equals("echo game") || userInput.equals("echo")) echo();
        else if (userInput.equals("lower case") || userInput.equals("lower case converter")) lowerCaseLetters();
        else if (userInput.equals("palindrome") || userInput.equals("palindrome checker")) palindrome();
        else if (userInput.equals("count it") || userInput.equals("count")) countIt();
        else if (userInput.equals("hangman")) hangman();
        else
        {
            history = "" + "Sorry! You Entered an Unrecognized Command!" + "\n" + "Press Enter";
            display.setText(history);
            display.getGuess(); //to pause
        }
    }
    /**continueGame tells all game functions to run 
     * while the user wants to keep playing that game
     *@param none
     *@return true if the userInput is not "another game" nor "stop playing"
     */
    private boolean continueGame()
    {
        if (!userInput.toLowerCase().equals("another game") && !userInput.toLowerCase().equals("stop playing")) 
            return true;
        return false;
    }
    
    
    /** hangman sets the title as "Hangman"
     * initializes all the variables needed
     * @param none
     * @return none
     */
    public void hangman()
    {
        display.setTitle("Hangman");
        display.setText("Loading...");
        getWords();
        
        resetHangman();
        display.setText(history);
        
        while (continueGame())
            guessWord();
    }
    /**resetHangman resets all values used in hangman
     * @param
     * @return
     */
    private void resetHangman()
    {
        guessField = "";
        userInput = "";
        guessField = "";
        usedField = "";
        submitCount = 0;
        random = (int) (Math.random() * 40159);
        
        chancesLeft = word[random].length() * 3 / 2; //to make chances propotional to length of word
        
        wordLetters = new String[word[random].length()];
        usedLetters = new String [26];
        usedWords = new String [(int)(chancesLeft/2)+1];
        
        for (int i = 0; i < word[random].length(); i++)
            wordLetters[i] = "_ ";
        for (int i = 0; i < word[random].length(); i++)
            guessField = guessField + wordLetters[i];
        
        for (int i = 0; i < ALPHABET.length(); i++)
            usedLetters[i] = "";
        for (int i = 0; i < ALPHABET.length(); i++)
            usedField = usedField + usedLetters[i];
        for (int i = 0; i < ((int)(chancesLeft/2)+1); i++)
            usedWords[i] = "";
        
        wordIndex = 0;
        
        history = BASIC_HANGMAN_MESSAGE 
                + "\n"
                + "Letters used: " + usedField + "\n"
                + "Chances Left: " + chancesLeft + "\n"
                + guessField;
    }
    /**guessWord takes in the user's input
     * and runs either checkLetter or checkString, then
     * it sets the guessField and text accordingly
     * @param none
     * @return none
     */
    private void guessWord()
    {
        if (chancesLeft > 0 && !userInput.equals(word[random]))
        {
            userInput = display.getGuess();
            guessField = "";
            
            if (userInput.length() == 1) checkLetter();
            else checkString();
        }
    }
    /** checkLetter sets up the screen if the userInput was 1 letter
     * It updates the "_ " with lettters if the userInput matches.
     * It updates the letters used.
     * It reduces chancesLeft is userInput does not appear
     * @param none
     * @return none
     */
    private void checkLetter()
    {  
        letterAppears = false;
        while (wordIndex < word[random].length())
        {
            if (userInput.equals(word[random].substring(wordIndex,wordIndex+1)))
            {
                wordLetters[wordIndex] = word[random].substring(wordIndex, wordIndex+1); 
                letterAppears=true;
            }
            wordIndex++;
        }
        wordIndex = 0;
        
        for (int i = 0; i < word[random].length(); i++)
            guessField = guessField + wordLetters[i] + " ";
        
        if (letterAppears==true) //the input letter does appear
        {
            updateUsedLetters();
            history = BASIC_HANGMAN_MESSAGE 
                    + "Nice guess!" + "\n"
                    + "Letters used: " + usedField + "\n"
                    + "Chances left: " + chancesLeft + "\n"
                    + guessField; 
        }
        else if (alreadyUsed())
        {
            updateUsedLetters();
            history = BASIC_HANGMAN_MESSAGE 
                    + "You already used that letter!" + "\n"
                    + "Letters used: " + usedField + "\n"
                    + "Chances left: " + chancesLeft + "\n"
                    + guessField;
        }
        else
        {
            updateUsedLetters();
            chancesLeft--;
            if (chancesLeft > 0) 
            {
                history = BASIC_HANGMAN_MESSAGE 
                        + "Oops!" + "\n"
                        + "Letters used: " + usedField + "\n"
                        + "Chances left: " + chancesLeft + "\n"
                        + guessField;
            }
            else {
                history =  "GAME OVER! You ran out of chances!" + "\n"
                        + "\n"
                        + "It was " + word[random].toUpperCase() + "\n"
                        + "\n"
                        + "Press Enter to play again";
                    display.setText(history);  
                
                display.getGuess(); //to pause the game
                resetHangman();  
            }
        } 
        display.setText(history); 
    }
    /**checks if input was already used or not
     *@param none
     *@return true if typed input was already typed, false if new input
     */
    private boolean alreadyUsed()
    {
        if (userInput.length() == 1)
        {
            for (int i = 0; i < usedField.length(); i++)
            {
                if (userInput.equals(usedField.substring(i,i+1)))
                    return true;
            }
        }
        else 
        {
            for (int i = 0; i < usedWords.length; i++)
            {
                if (usedWords[i].equals(userInput))
                    return true;
            }
        }
        return false;
    }
    /** checkString compares the user input string
     * to the word and replaces the whole guessField
     * with the letters of the word if they match.
     * It also runs resetHangman to get a new word 
     * for the next time the guessWord runs
     * @param none
     * @return none
     */
    private void checkString()
    {
        if (userInput.equals(word[random]))
        {
            for (int i = 0; i < word[random].length(); i++)
                guessField = guessField + word[random].substring(i,i+1) + " ";
            
            history = BASIC_HANGMAN_MESSAGE 
                    + "YAY Correct Word!" + "\n"
                    + "Letters used: " + usedField + "\n"
                    + "Chances left: " + chancesLeft + "\n"
                    + guessField + "\n"
                    + "Press Enter to play again";
            display.setText(history);  
            
            display.getGuess(); //to pause the game
            resetHangman();
            display.setText(history);  
        }
        else
        {               
            for (int i = 0; i < word[random].length(); i++)
                guessField = guessField + wordLetters[i] + " ";
            
            if (alreadyUsed())
            {
                history = BASIC_HANGMAN_MESSAGE 
                        + "You already guessed that word!" + "\n"
                        + "Letters used: " + usedField + "\n"
                        + "Chances left: " + chancesLeft + "\n"
                        + guessField;
            }
            else if (chancesLeft > 0)
            {    
                usedWords[submitCount] = userInput;
                submitCount++;
                chancesLeft -= 2;
                history = BASIC_HANGMAN_MESSAGE 
                        + "That's not the Word!" + "\n"
                        + "Letters used: " + usedField + "\n"
                        + "Chances left: " + chancesLeft + "\n"
                        + guessField;
            }
            else 
            {
                history = "GAME OVER! You ran out of chances!" + "\n"
                        + "\n"
                        + "It was " + word[random].toUpperCase() + "\n"
                        + "\n"
                        + "Press Enter to play again";
                display.setText(history);  
                
                display.getGuess(); //to pause the game
                resetHangman();  
            }
            
            display.setText(history);  
        }
    }
    /**updateUsedLetters update usedField to show 
     * all letters that were inputed by the user
     *@param none
     *@return none
     */
    private void updateUsedLetters()
    {
        usedField = "";
        
        while (wordIndex < ALPHABET.length())
        {
            if (userInput.equals(ALPHABET.substring(wordIndex,wordIndex+1)))
                usedLetters[wordIndex] = ALPHABET.substring(wordIndex,wordIndex+1) + ", ";
            wordIndex++;
        }
        wordIndex = 0;
        
        for (int i = 0; i < ALPHABET.length(); i++)
            usedField = usedField + usedLetters[i];
    }
}
