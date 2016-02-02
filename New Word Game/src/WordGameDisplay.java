import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * UI display for the word games
 * can get the user's input, set title, and set text
 * 
 * @version 11/9/12 - JFrame size changed to 600x400
 * 
 * @author Mr. Page
 */
public class WordGameDisplay implements ActionListener
{
	private JFrame frame;
	private boolean buttonPressed;
	private JTextField guessField;
	private JTextArea textArea;

	/**
	 * Constructor: WordGameDisplay()
	 * Usage: WordGameDisplay display = new WordGameDisplay();
	 * ----------------------------------------
	 * creates a JFrame with an input text field and output text area
	 */
	public WordGameDisplay()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		frame.setSize(600, 400);

		guessField = new JTextField(40);
		guessField.addActionListener(this);
		frame.getContentPane().add(guessField);

		textArea = new JTextArea(40, 40);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		frame.getContentPane().add(scrollPane);

		guessField.requestFocusInWindow();

		//frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Method: setTitle(String)
	 * Usage: display.setTitle("Word Game");
	 * ----------------------------------------
	 * sets the JFrame title to the given string
	 * @param title to be title of the window
	 */
	public void setTitle(String title)
	{
		frame.setTitle(title);
	}

	/**
	 * Method: getGuess()
	 * Usage: String input = display.getGuess();
	 * ------------------------------------
	 * returns the user's input as a string
	 * @return user input as a string
	 */
	public String getGuess()
	{
		buttonPressed = false;
		while (!buttonPressed)
		{
			try
			{
				Thread.sleep(1);
			}
			catch(InterruptedException e)
			{
			}
		}
		String guess = guessField.getText();
		guessField.selectAll();
		return guess;
	}

	/**
	 * Method: actionPerformed(ActionEven)
	 * Usage: 
	 * -----------------------------
	 * helper method of getGuess
	 * when an action is performed: user presses enter in the text input box
	 * buttonPressed it set to true and guessGuess returns whatever is in the text area
	 */
	public void actionPerformed(ActionEvent event)
	{
		buttonPressed = true;
	}

	public void setText(String text)
	{
		textArea.setText(text);
	}

	/**
	 * Method: loadWords(fileLocation)
	 * Usage: ArrayList<String> dictionary = display.loadWords("C:/... dictionary.txt");
	 * ---------------------------------------
	 * returns the designated text file as an arraylist
	 * 
	 * @param file - location and name of the txt file
	 * @return ArrayList<String> with each element as a word
	 */
	public ArrayList<String> loadWords(String file)
	{
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = in.readLine();
			ArrayList<String> words = new ArrayList<String>();
			while (line != null)
			{
				words.add(line);
				line = in.readLine();
			}
			in.close();
			return words;
		}
		catch(IOException e)
		{
			throw new IllegalArgumentException(e.toString());
		}
	}
}