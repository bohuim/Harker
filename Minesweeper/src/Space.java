import java.awt.Color;

/**
 * A square space in a minesweeper field
 * 
 * @author Dennis Moon
 */
public class Space
{
    private MyBoundedGrid<Space> field;
    private Location location;
    
    private boolean isMine;
    private boolean flagged;
    private boolean activated;
    
    public Space(MyBoundedGrid<Space> aField, Location loc)
    {
        field = aField;
        location = loc;
        field.put(loc, this);
        
        isMine = false;
        activated = false;
    }
    
    /**
     * Method: isMine()
     * Usage: if(Space.isMine())
     * ---------------------------
     * @return whether or not this space is a mine
     */
    public boolean isMine()
    {
        return isMine;
    }
    
    /**
     * Method: activated()
     * Usage: if(!activated())
     * --------------------------
     * @return whether or not this space has been pressed
     */
    public boolean isActivated()
    {
        return activated;
    }
    
    /**
     * Method: isFlagged()
     * Usage: space.isFlagged()
     * --------------------------
     * @return wheter or not this space has been flagged
     */
    public boolean isFlagged()
    {
        return flagged;
    }
    
    /**
     * Method: plantMine()
     * Usage: plantMine()
     * --------------------
     * sets this space as a mine
     */
    public void plantMine()
    {
        isMine = true;
    }
    
    /**
     * Method: activate()
     * Usage: space.activate;
     * -----------------------
     * sets this space to clicked/activated
     * but returns false if its a mine
     * @return false if this space is a mine
     */
    public boolean activate()
    {
        activated = true;
        
        if (isMine)
            return false;
        return true;
    }
    
    /**
     * Method: flag()
     * Usage: space.flag();
     * --------------------
     * flags this space 
     */
    public void flag()
    {
        flagged = true;
    }
    
    /**
     * Method: unflag()
     * Usage: space.unflag()
     * -------------------
     * unflags this space
     */
    public void unflag()
    {
        flagged = false;
    }
}
