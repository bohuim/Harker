import java.util.ArrayList;

import info.gridworld.actor.*;
import info.gridworld.grid.Location;

/**
 * StealthCritter copies the movement, direction and color of another critter
 * switches targets from time to time, and teleports randomly
 * 
 * @author Dennis Moon
 */
public class StealthCritter extends ChameleonCritter
{
    private int steps;
    
    /**
     * Constructor: StealthCritter()
     * Usage: called to make a StealthCritter
     * --------------------------------
     * constructor uses processActors(getActors()) to initially set the target
     */
    public StealthCritter()
    {
        steps = 0;
    }
    
    /**
     * Method: getMoveLocation()
     * Usage: 3rd step in act() of Critter
     * --------------------------------
     * Gets all the surrounding empty locations 
     * except a random location is passed every 20 steps
     * 
     * Postcondition: The state of all actors is unchanged.
     * @return a list of possible locations for the next move
     */
    public ArrayList<Location> getMoveLocations()
    {
        steps++;
        if (steps>20)
        {
            ArrayList<Location> next = new ArrayList<Location>();
            next.add((new Location((int)(Math.random()*getGrid().getNumRows()), (int)(Math.random()*getGrid().getNumCols()))));
            steps = 0;
            return next;
        }
        return getGrid().getEmptyAdjacentLocations(getLocation());
    }
}
