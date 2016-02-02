import java.util.ArrayList;

/**
 * Class: WordGame
 * 
 * A collection of word games: echo, jotto, jumble, search, subwords, hangman
 * has a instance of WordGameDisplay and a ArrayList<String> dictionary;
 * 
 * @version 11/7/12 - dictionary index, getRandomWord methods added
 * @version 11/8/12 - commonLetters added
 * @version 11/9/12 - menu method and jotto game added
 * @version 11/10/12 - hangman, subwords games game added
 * @version 11/12/12 - jumble and search added
 * @version 11/13/12 - menu refined and bugs fixed
 * 
 * @author Dennis Moon
 */
public class WordGame
{
    private WordGameDisplay display;
    private ArrayList<String> dictionary;
    
    /**
     * Constructor: WordGame()
     * Usage: WordGame game = new WordGame();
     * ---------------------------------------
     * creates a display in which the user interacts
     * and makes an arraylist of words in the set dictionary text file
     */
    public WordGame()
    {
        display = new WordGameDisplay();
        dictionary = display.loadWords("/Google Drive/Programming/Java/New Word Game/words.txt");
    }
    
    /**
     * Method: dictionaryIndex(word)
     * Usage: int index = dictionaryIndex(word);
     * -----------------------------------------
     * uses binary search to find the given word, but returns -1 
     * if word is not found
     * @param word - to find
     * @return index of that word in the dictionary
     */
    public int dictionaryIndex(String word)
    {
        int index = -1;
        int first = 0;
        int last = dictionary.size()-1;
        
        while ((last-first)>=0)
        {
            int middle = first + (last-first)/2;
            int compare = word.compareTo(dictionary.get(middle));
            
            if ((compare==0 || last==first) && word.equals(dictionary.get(middle)))
            {
                index = middle;
                last = -1;
                first = 0;
            }
            else if (compare < 0)
                last = middle-1;
            else
                first = middle+1;
        }
        
        return index;
    }
    
    /**
     * Method: getRandomWord()
     * Usage: String randomWord = getRandomWord()
     * --------------------------------------------
     * @return a random word of random length from the dictionary 
     */
    private String getRandomWord()
    {
        int index = (int)(Math.random()*dictionary.size());
        
        return dictionary.get(index);
    }
    
    /**
     * Method: getRandomWord(int)
     * Usage: String randomWord = getRandomWord(lengthOfWord);
     * -------------------------------------------------
     * keeps finding a word until the word's length is the same as the request length
     * 
     * Precondition: the given length must be equal to or less than the longest word in the dictionary
     * @param length desired of the random word
     * @return random word of the given length
     */
    public String getRandomWord(int length)
    {   
        String word = dictionary.get((int)(Math.random()*dictionary.size()));
        
        while (word.length()!=length)
            word = dictionary.get((int)(Math.random()*dictionary.size()));
        
        return word;
    }
    
    /**
     * Method: commonLetters(string, string)
     * Usage: int numCommon = commonLetters(word1,word2);
     * -----------------------------------------------
     * takes in two words and returns the number of 
     * letters that are common to both, but repeated letters 
     * are not counted
     * 
     * @param word1 to be compared with word2
     * @param word2 to be compared with word1
     * @return number of common letters between word1 & word2
     */
    public int commonLetters(String word1, String word2) //bee ebb
    {      
        if (word1.length()==0)
            return 0;
        else
        {
            String compare = word1.substring(0,1);
            int indexOf = word2.indexOf(compare);
            
            word1 = word1.substring(1);
            if (indexOf != -1)
            {
                word2 = word2.substring(0,indexOf) + word2.substring(indexOf+1);
                return 1+commonLetters(word1,word2);
            }
            
            return commonLetters(word1,word2);
        }
    }
    
    /**
     * Method: menu()
     * Usage: game.menu()
     * -------------------------
     * menu for the word games
     * runs until the user closes the window
     * typing the number or name of the game takes the user to the game 
     * and quitting the game takes the user back to this menu
     */
    public void menu()
    {   
        String userInput = "";
        
        while (true)
        {
            display.setTitle("Menu");
            
            String menu = "This is the WordGame menu! \n" +
                    "Choose a game you like to play by typing its name or number. \n\n" +
                    
                    "You can quit each game by typing \"quit game.\" \n\n" +
                    
                    "1. Echo - See if your guess exists! \n\n" +
                    
                    "2. Jotto - Guess the secret word! \n" +
                    "           The game will tell you how many letters in common your guess has with the secret word! \n\n" +
                    
                    "3. Jumble - Unjumble the word! Gets progressively harder as you get it right! \n\n" +
                    
                    "4. Search - Guess the secret word! \n" + 
                    "            The game will tell you whether your guess is before or after the secret word alphabetically! \n\n" +
                    
                    "5. Subwords - Guess all the subwords of the given word! Gets progressively harder! \n\n" +
                    
                    "6. Hangman - Classic Hangman (without the drawing)";      
            display.setText(menu);
            
            userInput = display.getGuess().toLowerCase();

            if (userInput.equals("echo") || userInput.equals("1"))
                echo();
            else if (userInput.equals("jotto") || userInput.equals("2"))
                jotto();
            else if (userInput.equals("jumble") || userInput.equals("3"))
                jumble();
            else if (userInput.equals("search") || userInput.equals("4"))
                search();
            else if (userInput.equals("subwords") || userInput.equals("5"))
                subwords();
            else if (userInput.equals("hangman") || userInput.equals("6"))
                hangman();
        }
    }
    
    /** 
     * Method: echo()
     * Usage: game.echo();
     * ----------------------------
     * the echo game method called,
     * echo game tells the user whether the typed word exists in the dictionary
     * if it does, the index of the word is also given 
     */
    private void echo()
    {
        display.setTitle("Echo Game");
        
        int submitCount = 1;
        String history = "Enter a word and hear the echo!" + "\n";
        display.setText(history);
        
        String userInput = "";
        while (!userInput.equals("quit game"))
        {
            userInput = display.getGuess();
            
            int index = dictionaryIndex(userInput);
            
            if (index != -1)
                history = history + submitCount + ". " + "\"" + userInput + "\"" + " is the "+ (index+1) +"th word in the dictionary! \n";
            else 
                history = history + submitCount + ". " + "\"" + userInput + "\"" + " is not a word! \n";

            display.setText(history);            
            submitCount++;
        }
    }
    
    /**
     * Method: jotto()
     * Usage: game.jotto();
     * ------------------------
     * initiates the jotto game 
     * the game starts with a secret random 2 letter word and the user guess the secret word
     * the game tells the user various hints in comparison to the entered guess: 
     *      - how many letters in common with the secret word
     *      - if the guess matches length of secret word
     *      - if the guess is a valid word in the dictionary
     * if the guess is right a new secret word with an extra letter is chosen
     * 
     * the user can quit by typing quit game
     */
    private void jotto()
    {
        display.setTitle("Jotto");
        String history = "";
        String userGuess = ""; 
        int submitCount = 1;
        
        boolean newWord = false;
        int wordLength = 2;
        String word = getRandomWord(wordLength);
        
        while(!userGuess.equals("quit game"))
        {
            //System.out.println(word);
            history = "Guess a " + wordLength + " letter word! \n";
            display.setText(history);
            
            while(!newWord)
            {
                userGuess = display.getGuess().toLowerCase();
                String quotedUserGuess = "\"" + userGuess + "\"";
                history += submitCount + ". ";
                
                if (userGuess.equals(word))
                {
                    history += "That's correct! Enter anything to play Again! \n";
                    display.setText(history);
                    userGuess = display.getGuess().toLowerCase(); //pause before next word
                    wordLength++; //increase word length
                    newWord = true;
                }
                else if (userGuess.equals("pass"))
                {
                    history += "The word was \"" + word + "\" \n" +
                               "Enter anything to guess another " + wordLength + " letter word";
                    display.setText(history);
                    userGuess = display.getGuess().toLowerCase(); //pause before next word
                    newWord = true;
                }
                else if (userGuess.equals("quit game"))
                    newWord = true;
                else if (userGuess.length()!=wordLength)
                    history += quotedUserGuess + " isn't " + wordLength + " letters!";
                else 
                {
                    int index = dictionaryIndex(userGuess);
                    if (index == -1)
                        history += quotedUserGuess + " is not a word";
                    else
                        history += quotedUserGuess + " has " + commonLetters(userGuess, word) + " letters in common with the word!";               
                }
                
                submitCount++;
                history += "\n";
                display.setText(history);
             }
           
           submitCount = 1;
           word = getRandomWord(wordLength); 
           newWord = false;
        }
    }
    
    /**
     * Method: jumble()
     * Usage: jumble()
     * -----------------------
     * initiates the jumble game
     * the game gets a random letter and jumbles the word
     * everytime the user gets it right, the gave starts over with a longer word
     * the user can pass or quit game, if the user runs out of chances the game starts again with the same letter word
     */
    private void jumble()
    {
        display.setTitle("Jumble");
        String userGuess = "";
        String history = "";
        
        int submitCount = 1;
        boolean newWord = false;
        int wordLength = 3;
        
        while(!userGuess.equals("quit game"))
        {
            String word = getRandomWord(wordLength);
            String jumbled = jumbleWord(word);
            while (jumbled.equals(word))
                jumbled = jumbleWord(word);

            int chances = (int)(word.length());
            String message = "Try it!";
            String jumbleField = "";
            
            //show the jumbled letters
            for (int i=0; i<jumbled.length(); i++)
                jumbleField += jumbled.substring(i,i+1).toUpperCase() + " ";
            
            //set the text
            history = "Try to umjumble this " + word.length() + " letter word! \n\n" +
                    jumbleField + "\n\n" + 
                    message;
            display.setText(history);
            
            while (chances>0 && !newWord)
            {
                userGuess = display.getGuess().toLowerCase();
                
                if (userGuess.equals("quit game"))
                    newWord = true;
                else if (userGuess.equals("pass"))
                {
                    message = "Lame! You passed";
                    jumbleField = "";
                    for (int i=0; i<jumbled.length(); i++)
                        jumbleField += word.substring(i,i+1).toUpperCase() + " ";
                    newWord = true;
                }
                else if (userGuess.length()!=word.length())
                    message = "That's not even the right length";
                else if (userGuess.equals(word))
                {
                    message = "Nice one! Enter anything to play again!";
                    wordLength++;
                    newWord = true;
                }
                else
                {
                    message = "Nope. Try again";
                    chances--;
                }
                
                if (chances==0)
                    message = "You suck! You ran out of chances";
                history = "Try to umjumble this " + word.length() + " letter word! \n\n" +
                          jumbleField + "\n\n" + 
                          message;
                display.setText(history);
            }
            
            newWord = false;
            if (!userGuess.equals("quit game"))
                display.getGuess();
        }
    }
    
    /**
     * Method: jumbleWord(String)
     * Usage: jubleWord(word)
     * --------------------------
     * recursive helper method of jumble
     * base case: given string is length 1 - return the string
     * else: take a random letter from the word and return that letter + jumbleWord(all other letters)
     * 
     * @param word to be jumbled
     */
    private String jumbleWord(String word)
    {
        if (word.length()==1)
            return word;
        else
        {
            int index = (int)(Math.random()*word.length());
            String add = word.substring(index, index+1);
            String passOn = word.substring(0,index) + word.substring(index+1);
            return add + jumbleWord(passOn);
        }
    }
    
    /**
     * Method: search()
     * Usage: search()
     * ------------------------
     * initiates the search game
     * search game tells the player whether their word comes before or after the secret word
     * words not in the dictionary are rejected, and the user can pass to see the word and get a new word
     */
    private void search()
    {
        display.setTitle("Search!");
        String userGuess = "";
        String history = "";
        
        int submitCount = 1;
        boolean newWord = false;
        
        while (!userGuess.equals("quit game"))
        {
            String word = getRandomWord();
            System.out.println(word);
            int index = dictionaryIndex(word);
            String output = "";
            history = "Try guessing the " + word.length()+ " word I'm thinkning of!";
            display.setText(history);
            
            while (!newWord)
            {
                userGuess = display.getGuess().toLowerCase();
                int guessIndex = dictionaryIndex(userGuess);
                
                output = submitCount + ". ";           
                if (userGuess.equals("quit game"))
                    newWord = true;
                else if (userGuess.equals("pass"))
                {
                    output += "Pass?!? Here's your lousy word: \"" + word + "\" \n" +
                              "Enter anything to play again";
                    newWord = true;
                }
                else if (userGuess.equals(word))
                {
                    output += "Wow nice guess, \"" + userGuess + "\" is right! \n Enter anything to play again";
                    newWord = true;
                }
                else if (guessIndex==-1)
                    output += "\"" + userGuess + "\" is not even a word!";
                else
                {
                    if (guessIndex<index)
                        output += "\"" + userGuess + "\" comes before the word";
                    else
                        output += "\"" + userGuess + "\" comes after the word";
                }
                
                submitCount++;
                history += "\n" + output;
                display.setText(history);
            }
            
            newWord = false;
            submitCount = 1;
            if (!userGuess.equals("quit game"))
                userGuess = display.getGuess();
        }
    }
    
    /**
     * Method: subwords()
     * Usage: subwords()
     * ------------------------
     * runs the subwords game
     * subwords gets a random 4 or 5 letter word and makes an arraylist of all the 3/4 words that can be made from that word
     * the user must guess all the subwords, and the game gets a new word if all the words are guessed
     */
    private void subwords()
    {
        display.setTitle("Subwords");
        String userGuess = "";
        String history = "";
        
        int wordLength = 4;
        
        while (!userGuess.toLowerCase().equals("quit game"))
        {
            String word = getRandomWord(wordLength);
            ArrayList<String> subwords = findSubwords(word);

            String guessedField = "";
            String message = "Try it!";
            
            history = "Try to guess all the subwords of: " + word.toUpperCase() + " starting with 3 letters\n\n" +
                      guessedField + "\n\n" +
                      message + "\n" +
                      "Words left: " + subwords.size();
            display.setText(history);
            
            while (subwords.size()>0)
            {
                userGuess = display.getGuess().toLowerCase();
                
                if (userGuess.equals("quit game"))
                    subwords.removeAll(subwords);
                else if (userGuess.equals("pass"))
                {
                    message = "Quitter! Here are your words";
                    for (String s : subwords)
                        guessedField += s + ", ";
                    subwords.removeAll(subwords);
                }
                else if (userGuess.length()<3)
                    message = "That's not even 3 letters!";
                else if (subwords.contains(userGuess))
                {
                    message = "Good one!";
                    subwords.remove(userGuess);
                    guessedField += userGuess + ", ";
                    if (subwords.size()==0)
                    {
                        message = "Wow! You got all of the subwords! Enter anything to play again!";     
                        wordLength++; //increase lenght of word if user guesses all subwords
                    }
                }
                else
                    message = "Nope.";
                        
                history = "Try to guess all the subwords of: " + word.toUpperCase() + " starting with 3 letters\n\n" +
                        guessedField + "\n\n" +
                        message + "\n" +
                        "Words left: " + subwords.size();
                display.setText(history);
            }
            
            if (!userGuess.equals("quit game"))
                display.getGuess();
        }
    }
    
    /**
     * Method: findSubwords(String)
     * Usage: ArrayList<String> allSubwords = findSubwords(word)
     * -------------------------------------------
     * splits the given word by removing each letter and the letter combinations
     * are fed into combinationsOf to find all the combinations then to actualWords to 
     * limit the outcome to only words in the dictionary 
     * then if the subletters are 4 or more, it is run through findSubwords again to get all subwords
     * from 3 letters to word's length - 1
     * 
     * @param word - to find the subwords from
     * @return ArrayList of subwords
     */
    private ArrayList<String> findSubwords(String word)
    {
//        ArrayList<String> allWords = new ArrayList<String>();
//        
//        for (int i=0; i<word.length(); i++)
//        {
//            String subletters = word.substring(0,i) + word.substring(i+1); //reduce the string
//            for (String s : actualWordsOf(combosOf(subletters))) //get all the subwords of those
//            {
//                if (!allWords.contains(s)) // prevents repeating same words
//                    allWords.add(s);
//            }
//            
//            if (subletters.length()>3) 
//                for (String s : findSubwords(subletters)) //take the reduced string and make all subwords out of it
//                {
//                    if (!allWords.contains(s)) //prevents repeating same words
//                        allWords.add(s);
//                }
//        }
//        
//        return allWords;
        
        ArrayList<String> subWords = new ArrayList<String>();
        for (int i=0; i<dictionary.size(); i++)
        {
            int inCommon = commonLetters(word, dictionary.get(i));
            if (inCommon >= 3 && dictionary.get(i).length() < word.length())
                subWords.add(dictionary.get(i));
        }
        return subWords;
    }
    
    /**
     * Method: combosOf(String)
     * Usage: actualWords(combosOf(word))
     * ----------------------------------------
     * subwords game helper method that uses recursion to get all combinations of the given word
     * base case: if given word is length one, just add the letter to the arraylist and return it
     * else: add(take the first letter + combinationsOf(word without the taken out letter)
     * 
     * @param word - word to find other combinations from
     * @return Arraylist of combinations
     */
    private ArrayList<String> combosOf(String word)
    {
        //combinationsOf returns all cominations of the word in an arraylist
        ArrayList<String> combos = new ArrayList<String>();
        if (word.length()==1)
        {
            combos.add(word);
            return combos;
        }
        else //longer than 1 word
        {
            for (int i=0; i<word.length(); i++)
            {
                String add = word.substring(i,i+1);
                String passOn = word.substring(0,i) + word.substring(i+1);
                
                for (String s : combosOf(passOn))
                    if (!combos.contains(add+s)) //prevent repeated combos
                        combos.add(add + s);
            }
        }
        
        return combos;
    }
    
    
    /**
     * Method: actualWordsOf(ArrayList<String>)
     * Usage: actualWordsOf(words)
     * ---------------------------------------
     * traverses the given arraylist backwards and removes any word not in the dictionary
     * 
     * @param words - arraylist of all the possible combinations
     * @return just the words that are in the dictionary from the given combintatins
     */
    private ArrayList<String> actualWordsOf(ArrayList<String> words)
    {
        for (int i=words.size()-1; i>=0; i--)
            if (dictionaryIndex(words.get(i))==-1)
                words.remove(i);
        return words;
    }
    
    /**
     * Method: hangman()
     * Usage: hangman()
     * ---------------------
     * initiates the hangman game
     * Hangman gets a random word and runs the actual game until the user quits, runs out of guesses, or guesses correctly
     * contains lots of ifs and else to direct the flow and display messages according to the user's guess
     * the chances are proportional to the lenght of the word; each wrong letter guess is -1, wrong word guesses are -2 chances
     * 
     * Hangman keeps track of the guessField, which shows "_" for unguessed letters and the correct letters for correctly guessed letters using {@code updateLetters}
     * and usedField, which shows the wrongly guessed letters in alphabetical order using {@code updateUsed()}
     */
    private void hangman()
    {
        display.setTitle("Hangman");
        String userGuess = "";
        String history = "";
        
        boolean correctGuess = false;
        int wordLength = 4;
        
        while(!userGuess.equals("quit game")) //creates new words and reenters the game until player quits
        {   
            //creates all the necessary variables and sets them before a new game with a new word
            String word = getRandomWord(wordLength);
            int chances = (int)(word.length()*3/2);
            String[] guessLetters = new String[word.length()];
            
            String message = "Go ahead, guess!";
            String guessField = "";
            String usedField = "";
            
            for (int i=0; i<guessLetters.length; i++)
            {
                guessLetters[i] = "_";
                guessField += guessLetters[i] + " ";
            }
            
            history = "Guess the following " + word.length() + " letter word! \n \n" +
                    guessField + "\n \n" +
                    message + "\n" +
                    "Chances left: " + chances + "\n" +
                    "Guessed letters: " + usedField;      
            display.setText(history);
            
            while(chances>0 && !correctGuess) //lets the player guess again until chances run out
            {         
                userGuess = display.getGuess().toLowerCase();
                
                if (userGuess.equals("quit game")) //user decides to quit
                    chances = 0;
                else if (userGuess.length()==1) //user guesses a letter
                {
                    int index = word.indexOf(userGuess);
                    
                    if (alreadyGuessed(userGuess, guessLetters, usedField))
                        message = "You already guessed that letter!";
                    
                    else if (index!=-1) // letter exists in word
                    {
                        message = "Good guess!";
                        
                        guessLetters = updateLetters(userGuess, word, guessLetters);
                        guessField = "";
                        for (String s : guessLetters)
                            guessField += s + " ";
                    }
                    
                    else //doesnt exist
                    {
                        message = "Wrong guess!";
                        usedField = updateUsed(userGuess, usedField);
                        chances--;
                    }
                }
                else if (userGuess.length()==word.length()) //user guesses the word
                {
                    if (userGuess.equals(word)) //correct
                    {
                        message = "Nice, you got it! Enter anything to play again";
                        correctGuess = true;
                        guessField = "";
                        wordLength++;
                        for (int i=0; i<word.length(); i++)
                            guessField += word.substring(i,i+1) + " ";
                    }
                    else //wrong
                    {
                        message = "Wrong word!";
                        chances -= 2; 
                    }
                }
                else //user guesses a word thats not the length of the word
                    message = "That's not even the correct lenght! Try again";
                
                
                if (chances<=0) //if user lost due to losing all chances
                {
                    message = "Sorry! You lost! Enter anything to play again";
                    guessField = "";
                    for (int i=0; i<word.length(); i++)
                        guessField += word.substring(i,i+1) + " ";
                }
                
                //display all the necessary info
                history = "Guess the following " + word.length() + " letter word! \n \n" +
                        guessField + "\n \n" +
                        message + "\n" +
                        "Chances left: " + chances + "\n" +
                        "Guessed letters: " + usedField;      
                display.setText(history);
            }
            
            correctGuess = false;
            if (!userGuess.equals("quit game")) // pauses game to show message before getting new word
                display.getGuess();             // unless the user decied to quit the game
        }      
        
        //back to menu
    }
    
    /**
     * Method: alreadyGuessed(String, String[], String)
     * Usage: alreadyGuessed(userGuess, guessLetters, usedLetters)
     * --------------------------------------------------------------
     * hangman helper method
     * takes in the user's guess, guessed letters, and used up letters
     * and if the user's guess already exists in either guessed Letters or used letters
     * it return true
     * 
     * @param userGuess - current guess
     * @param guessLetters - already correct guesses
     * @param usedLetters - already wrong guesses
     * @return true if userGuess exists in either guessLetters or usedLetters
     */
    private boolean alreadyGuessed(String userGuess, String[] guessLetters, String usedLetters)
    {
        if (usedLetters.indexOf(userGuess)!= -1)
            return true;
        
        for (int i=0; i<guessLetters.length; i++)
            if (userGuess.equals(guessLetters[i]))
                return true;
        
        return false;
    }
    
    /**
     * Method: updateLetters(String, String[], String)
     * Usage: String[] updatedLetters = updateLetters(word, oldLetters, userGuess);
     * --------------------------------------------------------------
     * Hangman helper method
     * takes in the current field and the userGuess
     * and replaces "_ " with the given userGuess
     * 
     * @param word - the current word being guessed at
     * @param guessLetters
     * @param userInput
     * @return 
     */
    private String[] updateLetters(String userGuess, String word, String[] guessLetters)
    {
        for (int i=0; i<word.length(); i++)
        {
            if (userGuess.toLowerCase().equals(word.substring(i,i+1)))
                guessLetters[i] = userGuess;
        }
        
        return guessLetters;
    }
    
    /**
     * Method: updateUsed(String, String)
     * Usage: updatedUsedField = upatedUsed(userGuess, usedField)
     * --------------------------------------------------------
     * helper method of hangman
     * takes in a wrong guess and splits the string into characters
     * then arranges them in alphabetical order and return it
     * 
     * @param userGuess - the wrong user guess inputed
     * @param usedField - string of the previous wrong guesses
     * @return string of new wrong guesses in alphabetical order
     */
    private String updateUsed(String userGuess, String usedField)
    {
        usedField += userGuess;
       
        String[] usedLetters = usedField.split(", ");
        
        usedField = "";
        for (int n=0; n<26; n++)
            for (int i=0; i<usedLetters.length; i++)
                if (usedLetters[i].compareToIgnoreCase("a")==n)
                    usedField += usedLetters[i] + ", ";
        
        return usedField;
    }
}
