/**
 * A single disc of a tower
 * 
 * @author Dennis Moon
 */
public class Disc
{
    public static final int DISCHEIGHT = 20;
    private int width;
    
    /**
     * Constructor: Disc()
     * Usage: created by main
     * -----------------------
     * sets width to w
     * @param w - width of disc
     */
    public Disc(int w)
    {
        width = 40 + 30*(w-1);
    }
    
    /**
     * Method: getWidth()
     * Usage: called by TowerDisplay
     * ----------------------
     * returns the width in pixels
     * @return width
     */
    public int getWidth()
    {
        return width;
    }
}
