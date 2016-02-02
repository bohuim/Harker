import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
/**
* WordGameDisplay class is used for the word game lab
* This class contains no user serviceable parts
* modifying this class in any way will void your warranty
*/
public class WordGameDisplay implements ActionListener
{
	private JFrame frame;
	private boolean buttonPressed;
	private JTextField guessField;
	private JTextArea textArea;

	public WordGameDisplay()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		frame.add(Box.createVerticalStrut(10));
		
		guessField = new JTextField(40);
		guessField.setBorder(BorderFactory.createLineBorder(Color.red));
		guessField.addActionListener(this);
		frame.getContentPane().add(guessField);
		frame.add(Box.createVerticalStrut(10));

		textArea = new JTextArea(20, 40);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		frame.getContentPane().add(scrollPane);

		guessField.requestFocusInWindow();

		frame.pack();
		frame.setVisible(true);
	}

	public void setTitle(String title)
	{
		frame.setTitle(title);
	}

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

	public void actionPerformed(ActionEvent event)
	{
		buttonPressed = true;
	}

	public void setText(String text)
	{
		textArea.setText(text);
	}

}