import java.awt.Color;
import java.util.ArrayList;

import info.gridworld.actor.*;
import info.gridworld.grid.Location;

/**
 * Follower stalks another critter for a while 
 * before detaching and roaming around to find another target
 * 
 * @author Dennis Moon
 */
public class Follower extends Critter
{
    private Actor target;
    private int steps;
    
    /**
     * Constructor: Follower()
     * Usage: called to a make Follower
     * ----------------------------
     * target Follower follows is set to null
     * and the steps it has taken since switching target is set to null
     */
    public Follower()
    {
        target = null;
        steps = 0;
    }
    
    /**
     * Method: processActors
     * Usage: 2nd step in act() of Critter
     * -----------------------------------
     * processActors changes the target actor of Follower to a random actor surrounding it
     *      if Follower has followed this actor for more than 20 steps 
     *      or it has no target
     * Follower does not choose a rock or flower as a target
     * then increments steps
     * 
     * @Postcondition: 
     *          (1) The state of all actors is unchanged.
     *          (2) The list returned is at least of size 1
     * @return a list of actors that this critter wishes to process.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        if (target==null || steps>40)
        {
            for (int i=actors.size()-1; i>=0; i--)
                if ((actors.get(i) instanceof Rock) || (actors.get(i) instanceof Flower))
                    actors.remove(i);
            
            if (actors.size()==0)
                target = null;
            else
                target = actors.get((int)(Math.random()*actors.size()));
        }
    }
    
    /**
     * Method: getMoveLocations()
     * Usage: 3rd step in act() of Critter
     * ----------------------------
     * if Follower has a target, Follower moves to the location opposite to the target's direction
     * if target is null, Follower roams like a generic Critter until a target is found
     * 
     * @Postcondition: The state of all actors is unchanged.
     * @return a list of possible locations for the next move
     */
    public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Location> nextLoc = new ArrayList<Location>();
        
        if (target!=null)
        {
            Location next = target.getLocation().getAdjacentLocation(target.getDirection()+Location.HALF_CIRCLE);
            if (getGrid().isValid(next))
                nextLoc.add(next);
        }
        else
        {
            for (Location l : super.getMoveLocations()) //for every empty adjacent loc
                if (getGrid().isValid(l))               //if it's also valid
                    nextLoc.add(l);                    //add it to list of possible locs
        }
        
        return nextLoc;
    }
    
    /**
     * Method: makeMove()
     * Usage: last step in act() of Critter
     * -------------------------------------------
     * Follower faces the same way target is and steps are incremented,
     * if target is null, it faces the direction it just moved in
     * then calls Critters makeMove to go to the new location
     * 
     * @Postcondition: 
     *      (1) <code>getLocation() == loc</code>. 
     *      (2) The state of all actors other than those at the old and new locations is unchanged.  
     * @param loc the location to move to
     */
    public void makeMove(Location loc)
    {
        if (target!=null)
        {
            setDirection(target.getDirection());
            steps++;
        }
        else
            setDirection(getLocation().getDirectionToward(loc));
        super.makeMove(loc);
    }
}
