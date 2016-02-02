import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * SubGridDisplay displays a grid with blocks in it
 * but scales the blocks down to 17 pixels for hold & mainNext 
 * and to 11 pixels for smaller mains
 * @author Dennis Moon
 */
public class SubGridDisplay extends GridDisplay
{
    private boolean small;
    
    public SubGridDisplay(MyBoundedGrid<Block> gr, boolean small)
    {
        super(gr);
        this.small = small;
    }
    
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        
        for (Location l : getGrid().getOccupiedLocations())
        {
            Block b = getGrid().get(l);
            
            int val = 17;
            if (small) val -= 6;
            
            int x = (l.getCol()+1) + l.getCol()*val + shiftX(b.getColor(),val);
            int y = (l.getRow()+1) + l.getRow()*val + shiftY(b.getColor(),val);
            g2.setColor(b.getColor());
            g2.fill(new Rectangle2D.Double(x, y, val, val));
        }
    }
    
    /**
     * Method: shiftX()
     * Usage: used to shift tetrad accordingly in subgrids
     * ----------------------------------
     * distinguishes tetrads by color and translates accordingly
     * @param color - of block 
     */
    private int shiftX(Color color, int val)
    {
        if (color.equals(Color.MAGENTA)) return val/2;
        else if (color.equals(Color.RED)) return -1 * val/2;
        else if (color.equals(Color.GREEN)) return val/2;
        return 0;
    }
    
    /**
     * Method: shiftX()
     * Usage: used to shift tetrad accordingly in subgrids
     * ----------------------------------
     * distinguishes tetrads by color and translates accordingly
     * @param color - of block 
     */
    private int shiftY(Color color, int val)
    {
        if (color.equals(Color.CYAN)) return val/2;
        else if (color.equals(Color.BLUE) || color.equals(Color.ORANGE)) return -1 * val/2 + 1;
        return 0;
    }
}
