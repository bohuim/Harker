import java.util.ArrayList;

/**
 * Tower class has an array of discs
 * with index 0 as the lowest disc
 * 
 * @author Dennis Moon
 */
public class Tower
{
    private ArrayList<Disc> stack;
    private int x;
    
    /**
     * Constructor: Tower()
     * Usage: Tower t = new Tower()
     * ----------------------------
     * creates a new stack
     */
    public Tower(int x)
    {
        this.x = x;
        stack = new ArrayList<Disc>();
    }
    
    /**
     * Method: getX()
     * Usage: called by TowerDisplay
     * --------------------------
     * returns x in pixels 
     * @return x
     */
    public int getX()
    {
        return x*TowerDisplay.UNIT;
    }
    
    public int getNumDisc()
    {
        return stack.size();
    }
    
    /**
     * Method: addDisc()
     * Usage: called by main to add a disc to a tower
     * ---------------------------
     * @param d - disc to be added
     */
    public void addDisc(Disc d)
    {
        stack.add(d);
    }
    
    public Disc getDisc(int i)
    {
        return stack.get(i);
    }
    
    /**
     * Method: removeDisc()
     * Usage: called by main to remove disc
     * --------------------------
     * removes the given disc
     * but the disc must be in the stack and on the top
     * 
     * @param d - disc to be removed
     * @return true if dics exists and is on top
     */
    public boolean removeDisc(Disc d)
    {
        return stack.remove(d);
    }
    
    /**
     * Method: getDiscs()
     * Usage: called by TowerDisplay
     * -------------------------
     * creates a new array of discs and transfers stack to the new arry
     * 
     * @return d - the new array
     */
    public Disc[] getDiscs()
    {
        Disc[] d = new Disc[stack.size()];
        for (int i=0; i<stack.size(); i++)
            d[i] = stack.get(i);
        return d;
    }
}
