import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// Used to display the contents of a game board
public class BlockDisplay
{
	private static final Color BACKGROUND = Color.LIGHT_GRAY;

	private Minesweeper game;
	private MyBoundedGrid<Space> board;
	private JPanel mineField;
	private JPanel[][] grid;
	private JFrame frame;

	// Constructs a new display for displaying the given board
	public BlockDisplay(Minesweeper aGame)
	{   
	    game = aGame;
		board = game.getField();
		grid = new JPanel[board.getNumRows()][board.getNumCols()];
		
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });

		//Wait until display has been drawn
        try
        {
        	while (frame == null || !frame.isVisible())
        		Thread.sleep(1);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setTitle("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(20*board.getNumRows()+5,20*board.getNumCols()+27);
        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setLayout(null);

		//Create each square component
        mineField = new JPanel();
        mineField.setSize(20*board.getNumRows(),20*board.getNumCols());
        mineField.setLayout(new GridLayout(board.getNumRows(), board.getNumCols()));
        for (int row = 0; row < grid.length; row++)
        	for (int col = 0; col < grid[row].length; col++)
        	{
				grid[row][col] = new JPanel();
				setMouseListener(row,col);
				grid[row][col].setBackground(BACKGROUND);
				grid[row][col].setBorder(BorderFactory.createBevelBorder(0));
				grid[row][col].setSize(20, 20);
				mineField.add(grid[row][col]);
			}
        frame.add(mineField);
        
        //Display the window.
        frame.setVisible(true);
    }
    
    /**
     * Method: setMouseListener(int, int)
     * Usage: setMouseListener(row, col)
     * -------------------------------------
     * adds mouse listener to the panel of the given row and col
     * @param row - of the panel to set mouse listener
     * @param col - of the panel to set mouse listener
     */
    private void setMouseListener(final int row, final int col)
    {
        grid[row][col].addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){}

            public void mousePressed(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                    game.activateSpace(new Location(row, col));
                else if (SwingUtilities.isRightMouseButton(e))
                    game.flagSpace(new Location(row, col));
            }

            public void mouseReleased(MouseEvent e)
            {

            }
            
            public void mouseEntered(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
        });
    }
    
    /**
     * Method: updateActivatedSpace(Locaiton)
     * Usage: updateActivatedSpace(loc)
     * -----------------------------
     * makes the given location cell look like an activated cell
     * 
     * @param loc - of the cell to be repainted 
     */
    public void updateActivatedSpace(Location loc)
    {
        int row = loc.getRow();
        int col = loc.getCol();
        
        grid[row][col].setBorder(BorderFactory.createBevelBorder(1));
        grid[row][col].validate();
    }
    
    /**
     * Method: updateNumberSpace(Location, int)
     * Usage: updateNumberSpace(loc, num)
     * --------------------------------
     * makes a JLabel and sets the given location cell as the number given
     * and makes it look like an activated cell
     * 
     * @param loc - of the cell to show the number
     * @param num - to be shown
     */
    public void updateNumberSpace(Location loc, int num)
    {   
        updateActivatedSpace(loc);
        
        int row = loc.getRow();
        int col = loc.getCol();
        
        JLabel number = new JLabel();
        number.setFont(new Font("Times New Roman", Font.BOLD, 12));
        number.setText("" + num);
        if (num==1)
            number.setForeground(Color.BLUE);
        else if (num==2)
            number.setForeground(Color.GREEN);
        else if (num>2)
            number.setForeground(Color.RED);
        
        grid[row][col].add(number, BorderLayout.NORTH);
        grid[row][col].validate();
    }
    
    /**
     * Method: updateFlaggedSpace(Location)
     * Usage: updateFlaggedSpace(loc)
     * ---------------------------------
     * makes the given location cell red or back to normal depending on the current state
     * 
     * @param loc - of the cell to be repainted
     */
    public void updateFlaggedSpace(Location loc)
    {
        int row = loc.getRow();
        int col = loc.getCol();
        
        if (grid[row][col].getBackground()==Color.RED)
            grid[row][col].setBackground(Color.LIGHT_GRAY);
        else
            grid[row][col].setBackground(Color.RED);
        
        grid[row][col].validate();
    }
}