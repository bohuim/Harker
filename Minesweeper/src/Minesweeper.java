import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;

/**
 * Minesweeper game class
 * 
 * @author Dennis Moon
 */
public class Minesweeper
{
    private MyBoundedGrid<Space> field;
    private BlockDisplay display;
    private int totalSpaces;
    
    private int activated;
    private ArrayList<Location> mineLocs;
    private ArrayList<Space> flagged;
    private boolean gameOver;
    
    public static void main(String[] args)
    {
        Minesweeper game = new Minesweeper();
        game.play();
    }
    
    public Minesweeper()
    {
        field = new MyBoundedGrid<Space>(9,9);
        display = new BlockDisplay(this);
        totalSpaces = field.getNumRows()*field.getNumCols();
        
        activated = 0;
        mineLocs = new ArrayList<Location>();
        flagged = new ArrayList<Space>();
        gameOver = false;
        
    }
    
    public void play()
    {
        setField();
    }
    
    /**
     * Method: getField()
     * Usage: game.getField()
     * --------------------
     * @return grid of this minesweeper class
     */
    public MyBoundedGrid<Space> getField()
    {
        return field;
    }
    
    /**
     * Method: setField()
     * Usage: setField()
     * ------------------------
     * creates and sets all cells in the grid as spaces
     * then sets 10 of those spaces as mines
     */
    private void setField()
    {
        for (int row=0; row<field.getNumRows(); row++)
            for (int col=0; col<field.getNumCols(); col++)
                new Space(field, new Location(row,col));
        
        for (int i=0; i<10; i++)
        {
            int randomRow = (int)(Math.random()*field.getNumRows());
            int randomCol = (int)(Math.random()*field.getNumCols());
            Location randomLoc = new Location(randomRow, randomCol);
            
            if (!field.get(randomLoc).isMine())
            {
                field.get(randomLoc).plantMine();
                mineLocs.add(randomLoc);
            }
        }
    }
    
    /**
     * Method: activateSpace(Location)
     * Usage: game.activateSpace(loc);
     * --------------------------
     * activates the given location
     * 
     * @param loc - of the space to be activated
     */
    public void activateSpace(Location loc)
    {
        Space target = field.get(loc);
        if (target.isFlagged()) {} //dont do anything if space is flagged
        else if (target.isMine())
        {
            gameOver = true;
            activateAll();
            mineActivated(loc);
        }
        else
            chainActivate(loc);
    }
    
    /**
     * Method: chainActivate(Location)
     * Usage: chainActivate(loc);
     * -------------------------
     * Called only when the user clicked space is not flagged or is a mine
     * activates the clicked space and activates spaces with a recursive method
     * if any surrounding spaces are mines, the spaces is updated as an activated space with the number of mines around it
     * otherwise method starts with location north of current space
     * 
     * @param loc - of the space to be checked
     */
    private void chainActivate(Location loc)
    {        
        int surroundNum = checkSurrounding(loc);
        field.get(loc).activate();
        activated++;
        if (surroundNum>0)
            display.updateNumberSpace(loc, surroundNum);
        else
        {
            display.updateActivatedSpace(loc);
            
            for (int i=0; i<8; i++)
            {
                int direction = i*45;
                Location newLoc = loc.getAdjacentLocation(direction);
                if (field.isValid(newLoc) && !(field.get(newLoc).isActivated()) && !(field.get(newLoc).isFlagged()))
                    chainActivate(newLoc);
            }
        }
    }
    
    /**
     * Method: checkSurrounding(Location)
     * Usage: int numOfSurroudingMines = checkSurrouding(loc);
     * ---------------------------------
     * checks clockwise from the north of the given loc and counts all mines
     * around it, even in diagonal locations
     * and returns number of mines around the given loc
     * 
     * @param loc - around to check for mines around
     * @return number of locations with mines around the given loc
     */
    private int checkSurrounding(Location loc)
    {
        int minesAround = 0;
        for (int i=0; i<8; i++)
        {
            int direction = i*45;
            Location targetLoc = loc.getAdjacentLocation(direction);
            if (field.isValid(targetLoc) && field.get(targetLoc).isMine())
                minesAround++;
        }
        return minesAround;
    }
    
    /**
     * Method: flagSpace(Location)
     * Usage: game.flagSpace(loc)
     * ------------------------------
     * flags or unflags the given location if its not already activaed
     * 
     * @param loc - of the space to be flagged
     */
    public void flagSpace(Location loc)
    {
        if (!field.get(loc).isActivated()) //enter only if space is not already activated
        {
            if (field.get(loc).isFlagged()) //unflag the space if its already flagged
            {
                field.get(loc).unflag();
                flagged.remove(field.get(loc));
                display.updateFlaggedSpace(loc);
            }
            else if (flagged.size()<10)
            {
                field.get(loc).flag(); //flag the space if its not flagged already
                flagged.add(field.get(loc));
                display.updateFlaggedSpace(loc);
            }
        }
    }
    
    /**
     * Method: flaggedCorrectly()
     * Usage: flaggedCorrectly()
     * --------------------
     * checks each flag and mines to see if they match
     * if a flagged space is not a mine, returns false
     * 
     * @return false if any flagged space is not a mine
     */
    private boolean flaggedCorrectly()
    {
        for (Space s : flagged)
            if (!s.isMine())
                return false;
        return true;
    }
    
    /**
     * Method: activateAll()
     * Usage: activateAll()
     * ----------------------------
     * activates all the spaces
     * only to be used after game is over to block user from activating more spaces
     */
    private void activateAll()
    {
        for (int row=0; row<field.getNumRows(); row++)
            for (int col=0; col<field.getNumCols(); col++)
            {
                Space target = field.get(new Location(row, col));
                target.activate();
            }
    }
    
    /**
     * Method: mineActivated()
     * Usage: called at gameOver by user activating a mine
     * -------------------------------
     * slowly shows all the mines as red
     */
    private void mineActivated(Location loc)
    {
        display.updateFlaggedSpace(loc);
        mineLocs.remove(loc);
        
        try {Thread.sleep(500);}
        catch (InterruptedException e) {}
        
        for (int i=mineLocs.size()-1; i>=0; i--)
        {
            Location targetLoc = mineLocs.get(i);
            
            //if (!field.get(targetLoc).isFlagged())
                display.updateFlaggedSpace(targetLoc);
            mineLocs.remove(targetLoc);
            
            try {Thread.sleep(500);}
            catch (InterruptedException e) {}
        }
    }
}
