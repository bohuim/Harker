import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

/**
 * A simple class capable of displaying a number of colored rectangles and circles
 */
public class GridDisplay extends JComponent
{
    private MyBoundedGrid<Block> grid;

	/**
	 * Constructor: ShapeDisplay()
	 * Usage: ShapeDisplay display = new ShapeDisplay()
	 * -------------------------------
	 * makes a new visible JFrame of 600x600
	 */
	public GridDisplay(MyBoundedGrid<Block> gr)
	{
	    grid = gr;
	}

	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
		
		for (Location l : grid.getOccupiedLocations())
		{
		    Block b = grid.get(l);
		    
		    if (b.getColor().equals(Color.BLACK))
		    {
		        int x = (l.getCol()+1) + l.getCol()*19;
                int y = (l.getRow()+1) + l.getRow()*19;
                g2.setColor(Color.GRAY);
                g2.draw(new Rectangle2D.Double(x, y, 19, 19));
		    }
		    else
		    {
    		    int x = (l.getCol()+1) + l.getCol()*19;
    		    int y = (l.getRow()+1) + l.getRow()*19;
    		    g2.setColor(b.getColor());
    		    g2.fill(new Rectangle2D.Double(x, y, 19, 19));
		    }
		}
	}

	public MyBoundedGrid<Block> getGrid()
	{
	    return grid;
	}
}