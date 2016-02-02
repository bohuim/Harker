import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

// Used to display the contents of a game board
public class BlockDisplay extends JComponent implements KeyListener
{
	private static final Color BACKGROUND = Color.WHITE;

	private MyBoundedGrid<Block> board;
	private JPanel[][] grid;
	private JFrame frame;
	private ArrowListener listener;

	// Constructs a new display for displaying the given board
	public BlockDisplay(MyBoundedGrid<Block> board)
	{
		this.board = board;
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(board.getNumCols()*10, board.getNumCols()*10));
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.getContentPane().add(this);

		//Show the board
		frame.repaint();

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight()));
        for (Location l : board.getOccupiedLocations())
        {
            Block b = board.get(l);
            
            g2.setColor(b.getColor());

            int x = (l.getCol()+1) + l.getCol()*9;
            int y = (l.getRow()+1) + l.getRow()*9;
            g2.setColor(b.getColor());
            g2.fill(new Rectangle2D.Double(x, y, 9, 9));
        }
    }
    
    public void repaint()
    {
        frame.repaint();
    }

	// Sets the title of the window.
	public void setTitle(String title)
	{
		frame.setTitle(title);
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void keyPressed(KeyEvent e)
	{
		if (listener == null)
			return;
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT)
			listener.leftPressed();
		else if (code == KeyEvent.VK_RIGHT)
			listener.rightPressed();
		else if (code == KeyEvent.VK_DOWN)
			listener.downPressed();
		else if (code == KeyEvent.VK_UP)
			listener.upPressed();
	}

	public void setArrowListener(ArrowListener listener)
	{
		this.listener = listener;
	}
}