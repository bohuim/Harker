import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * BoardDisplay takes care of all graphic representation of the Chess board and event handling. </br>
 * Uses a 2D array of JButtons as the squares on the Chess board in a JFrame and adds itself to the grid as a KeyAdapter. </br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code actionPerformed()} - called when a square on the display is called and takes care of move selection</li>
 *      <li>{@code showBoard()} - repaints the display</li>
 *      <li>{@code selectMove()} - returns the move selected by the user</li>
 *      <li>{@code keyPressed()} - called when a key is pressed and {@code board.undo()} is called if the keys are {@code ctrl + z}</li>
 *      <li>{@code switchTurn()} - makes the current turn into whichever color it was not</li>
 *      <li>{@code setTitle(str)} - sets the title of the frame to the given string</li>
 *      <li>{@code setColor(loc, color)} - frames the specified location with a square of the specified color</li>
 *      <li>{@code clearColors()} - clears any highlighted squares</li>
 *      <li>{@code flip()} - switches the instance variable flipped to whatever it is currently not</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version May 10, 2013
 * 
 * @Revision
 *     Apr 29, 2013 - modified so that all possible moves of a selectedPiece
 *     May 10, 2013 - modified to include turn color & return Castle as a select-able move
 *     May 12, 2013 - extended KeyAdapter and added keyPressed() for undo
 *     May 15, 2013 - modified keyPressed() to take in new game
 *     May 20, 2013 - added flip() method for reversing the board
 */
public class BoardDisplay extends KeyAdapter implements ActionListener
{
	private Board board;
	private JButton[][] grid;
	private Piece selectedPiece;
	private Move selectedMove;
	private JFrame frame;
	private Color[][] colors;
	
	private Color turn;
	private boolean flipped;

	/**
	 * <b>Constructor: </b>BoardDisplay</br> 
	 * <b>Usage: </b>{@code BoardDisplay display = new BoardDisplay()}</br>
	 * -------------------------------</br>
	 * Takes in a Board class containing the chess pieces, sets current turn as white, and creates {@code grid, color}, both 2D arrays. </br>
	 * {@code createAndShowGUI()} is called through a {@code SwingUtilitis.invokeLater} for safety reasons and thread waits until frame is available and visible. </br>
	 * 
	 * @param board of the game
	 */
	public BoardDisplay(Board board)
	{
		this.board = board;
		grid = new JButton[board.getNumRows()][board.getNumCols()];
		colors = new Color[board.getNumRows()][board.getNumCols()];
		turn = Color.WHITE;
		flipped = false;

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
	 * <b>Method: </b>createAndShowGUI</br> 
	 * <b>Usage: </b>{@code display.createAndShowGUI()}</br>
	 * -------------------------------</br>
	 * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
	 */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(board.getNumRows(), board.getNumCols()));

		//Create each square as a button.
        for (int row = 0; row < grid.length; row++)
        	for (int col = 0; col < grid[row].length; col++)
        	{
				grid[row][col] = new JButton();
				grid[row][col].setOpaque(true);
				if ((row + col) % 2 == 0)
					grid[row][col].setBackground(new Color(155, 145, 115));
				else
					grid[row][col].setBackground(new Color(110, 85, 55));
				grid[row][col].setPreferredSize(new Dimension(50, 50));
				grid[row][col].setActionCommand(row + "," + col);
				grid[row][col].addActionListener(this);
				grid[row][col].addKeyListener(this);
				frame.getContentPane().add(grid[row][col]);
			}

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * <b>Method: </b>actionPerformed</br> 
     * <b>Usage: </b>{@code display.actionPerformed()}</br>
     * -------------------------------</br>
     * Called when the user clicks a square on the display and determines the Location of the click. </br>
     * <ol>
     *      <li><b>No selectedPiece</b>: {@code selectedPiece} is set as {@code board.get(loc)}</br>
     *                                      and all destination locations available by the piece is highlighted yellow.</br>
     *                                      calls {@code board.checkCastle()} and highlights Rook locations if castling is available</li>
     *      <li><b>Clicked location has selectedPiece</b>: unselects piece & move and calls {@code clearColors()}</li>
     *      <li><b>selectedPiece exists</b>: creates a new Move and sets as the {@code selectedMove}</br>
     *                                        if piece is a King and clicked location contains a Rook, a new Castle move is created</li>
     * </ol>
     * 
     * @param event fired when display is clicked
     */
    public void actionPerformed(ActionEvent event)
    {
		//Determine location of clicked button.
		String command = event.getActionCommand();
		int comma = command.indexOf(",");
		int row = Integer.parseInt(command.substring(0, comma));
		if (flipped) row = 7 - row;
		int col = Integer.parseInt(command.substring(comma + 1));
		Location loc = new Location(row, col);

		if (selectedPiece == null)
		{
			//we have just selected a piece for the first time.
		    selectedPiece = board.get(loc);
			clearColors();
			if (selectedPiece != null && selectedPiece.getColor().equals(turn)) //when selected loc is not empty & of color of this turn
			{                                                                   //highlight available destinations
				setColor(loc, Color.CYAN);
				Iterator<Location> it = availableDestinations(selectedPiece.destinations());
				while(it.hasNext())
			        setColor(it.next(), Color.YELLOW);
				
				if(selectedPiece instanceof King)
				{
				    if (board.checkCastle(turn, Location.WEST)) 
				        setColor(new Location(row, 0), Color.YELLOW);
				    if (board.checkCastle(turn, Location.EAST))
				        setColor(new Location(row, 7), Color.YELLOW);
				}
			}
		}
		else if (loc.equals(selectedPiece.getLocation()))
		{
			//we are deselecting the piece
			selectedPiece = null;
			selectedMove = null;
			clearColors();
		}
		else
		{
			//we have selected a move
		    selectedMove = new Move(selectedPiece, loc);
		    //if pieces are King & Rook, initiate Castling
		    if (selectedPiece!=null)
		    {
		        if (selectedPiece instanceof King && board.get(loc)!=null && board.get(loc) instanceof Rook)
		            selectedMove = new Castle(selectedPiece, board.get(loc));
		        else if (selectedPiece instanceof Pawn && (loc.getRow()==0 || loc.getRow()==7))
		            selectedMove = new Promotion(selectedPiece, loc);   
		    }
		}
	}
    
    /**
     * <b>Method: </b>availableDestinations</br> 
     * <b>Usage: </b>{@code display.availableDesinations(it)}</br>
     * -------------------------------</br>
     * Given an iterator to a set of locations from {@code piece.desinations()}, each destination is checked for whether the corresponding move will put the King in check. </br>
     * If a desination puts the King in danger, it is not selectable. </br>
     * 
     * @param it - iterator to set of locations to check
     * @return ArrayList of destinations that will not put King in check
     */
    private Iterator<Location> availableDestinations(Iterator<Location> it)
    {
        ArrayList<Location> list = new ArrayList<Location>();
        while (it.hasNext())
        {
            Location dest = it.next();
            Move move = new Move(selectedPiece, dest);
            board.executeMove(move);
            if (!board.isInCheck(turn))
                list.add(dest);
            board.undoMove();
        }
        return list.iterator();
    }

    /**
     * <b>Method: </b>showBoard</br> 
     * <b>Usage: </b>{@code display.showBoard()}</br>
     * -------------------------------</br>
     * Redraws the entire board by going through the board with a nested for loop. </br>
     * Takes the image of the Piece, if one exits, at the location and draws it on the display and draws a square boarder, if one exists. </br>
     * If the board should be flipped, pieces are acquired from locaiton (7-row,7-col) instead of (row, col)
     */
	public void showBoard()
	{
		for (int row = 0; row < grid.length; row++)
			for (int col = 0; col < grid[row].length; col++)
			{
				Location loc = null;
				if (flipped) loc = new Location(7-row, 7-col);
				else loc = new Location(row, col);

			    Piece piece = board.get(loc);
				
				Icon icon = null;
				if (piece != null)
				{
					grid[row][col].setForeground(piece.getColor());
					icon = new ImageIcon(piece.getImageFileName());
				}
				grid[row][col].setIcon(icon);

				Color color = colors[row][col];

				if (color == null)
					grid[row][col].setBorder(null);
				else
					grid[row][col].setBorder(BorderFactory.createLineBorder(color));
			}
	}
	
	/**
	 * <b>Method: </b>flip</br> 
	 * <b>Usage: </b>{@code display.flip()}</br>
	 * -------------------------------</br>
	 * Sets flipped to whatever it is currently not. </br>
	 * The instance variable flipped controls the display aspect by making the board in the perspective of white or black. 
	 */
	public void flip()
	{
	    flipped = !flipped;
	    showBoard();
	}

	/**
	 * <b>Method: </b>selectMove</br> 
	 * <b>Usage: </b>{@code display.selectMove()}</br>
	 * -------------------------------</br>
	 * Returns selectedMove made by the user by waiting until one exists and resets {@code selectedPiece, selectedMove}. </br>
	 * Move is selected by actionPerformed when the user selects a piece and its destination. </br>
	 * 
	 * @return Move selected by user
	 */
	public Move selectMove()
	{
		try
		{		    
			selectedPiece = null;
			selectedMove = null;
			while (selectedMove == null)
				Thread.sleep(1);
			Move move = selectedMove;
			selectedPiece = null;
			selectedMove = null;
			return move;
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}
	
	/**
	 * <b>Method: </b>keyPressed</br> 
	 * <b>Usage: </b>{@code keyPressed(KeyEvent)}</br>
	 * -------------------------------</br>
	 * Fired when user presses any key, but only the following combinations cause a response: </br>
	 * <ul>
	 *     <li>{@code ctrl + z} calls board's {@code undoMove()} method twice, redraws board and clears colors</li>
	 *     <li>{@code F2} calls board's {@code reset()} method to start a new game </li>
	 * </ul>
	 * 
	 * @param e - keyevent
	 */
	public void keyPressed(KeyEvent e)
	{
	    if (e.getKeyCode() == e.VK_Z && (e.getModifiers() & e.CTRL_MASK) != 0)
	    {
	        board.undoMove();
	        board.undoMove();
	    }
	    if (e.getKeyCode() == e.VK_F2)
	        board.reset();
	    showBoard();
        clearColors();
	}
	
	/**
	 * <b>Method: </b>switchTurn</br> 
	 * <b>Usage: </b>{@code display.switchColor()}</br>
	 * -------------------------------</br>
	 * Changes the color of the current turn: black if it was white and vise versa
	 */
	public void switchTurn()
	{
	    if (turn.equals(Color.WHITE))
	        turn = Color.BLACK;
	    else
	        turn = Color.WHITE;
	}

	/**
	 * <b>Method: </b>setTitle</br> 
	 * <b>Usage: </b>{@code display.setTitle(title)}</br>
	 * -------------------------------</br>
	 * Sets the title of the frame to the given title string
	 * 
	 * @param title of frame
	 */
	public void setTitle(String title)
	{
		frame.setTitle(title);
	}

	/**
	 * <b>Method: </b>setColor</br> 
	 * <b>Usage: </b>{@code display.setColor(loc, color)}</br>
	 * -------------------------------</br>
	 * Highlights the given square at the given loc with a square border of the specified color and redraws the board
	 * 
	 * @param loc of square to highlight
	 * @param color to highlight loc in
	 */
	public void setColor(Location loc, Color color)
	{
	    if (!flipped)
	        colors[loc.getRow()][loc.getCol()] = color;
	    else
	        colors[7-loc.getRow()][7-loc.getCol()] = color;
		showBoard();
	}

	/**
	 * <b>Method: </b>clearColors</br> 
	 * <b>Usage: </b>{@code Display.clearColors()}</br>
	 * -------------------------------</br>
	 * Clears all border colors and redraws the board.
	 */
	public void clearColors()
	{
		for (int row = 0; row < colors.length; row++)
			for (int col = 0; col < colors[row].length; col++)
				colors[row][col] = null;
		showBoard();
	}
}