import java.awt.Color;
import java.util.ArrayList;
import info.gridworld.actor.*;
import info.gridworld.grid.Location;

/**
 * Wolf might eat another critter, but only
 * breeding more wolves when it has eaten enough and dying when it hasn't eaten enough
 * 
 * @author Dennis Moon
 */
public class Wolf extends Critter 
{
    private int stepsNotEaten;
    private int numEaten;
    private Location lastPreyLoc;
    
    /**
     * Constructor: Wolf()
     * Usage: GridWorld creates a wolf when the user selects to do so
     * ------------------------------
     * number of things eaten and period of not eating anything is set to 0
     * Wolf's direction randomly set to give variety
     */
    public Wolf()
    {
        numEaten = 0;
        stepsNotEaten = 0;
        
        setColor(Color.WHITE);
        setDirection((int)(Math.random()*8) * 90);
    }
    
    /**
     * Method: getActors()
     * Usage: 1st step of act() in Critter
     * ---------------------------------------
     * Wolf is only concerned with what is in front of him,
     *      if the location in front of him is valid, whatever in that location is added to list of actors to be processed.
     * 
     * @Postcondition: 
     *          (1) The state of all actors is unchanged.
     *          (2) The list returned is at least of size 1
     * @return a list of actors that this critter wishes to process.
     */
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> infront = new ArrayList<Actor>();
        Location targetLoc = getLocation().getAdjacentLocation(getDirection());
        if (getGrid().isValid(targetLoc))
            infront.add(getGrid().get(targetLoc)); //adds whatever in the direction its facing
        else
            infront.add(null); //null is added if facing locaiton is not valid
        
        return infront;
    }
    
    /**
     * Method: processActors(listOf1Actor)
     * Usage: 2nd step of act() in Critter
     * --------------------------------------
     * Wolf eates whatever is in front of it, unless it's a rock, another wolf, or null
     *      then numEaten in incremented & stepsNotEaten is set back to 0
     *      but it doesnt eat anything, stepsNotEaten is incremented & numEaten is decreased by 1.
     * If Wolf has eaten 3 things, it breeds another Wolf into a valid & empty location surrounding it
     * 
     * @Precondition: The list given is at least of size 1
     * @Postcondition: 
     *      (1) The state of all actors in the grid other than this
     *          critter and the elements of <code>actors</code> is unchanged. 
     *      (2) The location of this critter is unchanged.
     * @param actors the actors to be processed
     */
    public void processActors(ArrayList<Actor> actors)
    {
        Actor a = actors.get(0);
        if (a!=null && !(a instanceof Rock) && !(a instanceof Wolf))
        {
            a.removeSelfFromGrid();
            numEaten++;
            stepsNotEaten = 0;
        }
        else
            stepsNotEaten++;
        
        Location breedLoc = super.selectMoveLocation(super.getMoveLocations());
        if (numEaten > 2 && getGrid().isValid(breedLoc))
        {
            numEaten = 0;
            (new Wolf()).putSelfInGrid(getGrid(), breedLoc);
        }
    }
    
    /**
     * Method: getMoveLocations()
     * Usage: 3rd step of act() in Critter
     * --------------------------------------
     * Wolf can move to any empty location surrounding it      
     *      all the valid locations are filtered from empty locations given by super.getMoveLocations()
     * 
     * @Postcondition: The state of all actors is unchanged.
     * @return a list of possible locations for the next move
     */
    public ArrayList<Location> getMoveLocations()
    {      
        ArrayList<Location> nextLocs = new ArrayList<Location>(); 
        
        for (Location l : super.getMoveLocations()) //for every empty adjacent loc
            if (getGrid().isValid(l))               //if it's also valid
                nextLocs.add(l);                    //add it to list of possible locs
        
        return nextLocs;
    }
    
    /**
     * Method: selectMoveLocation(possibleLocs)
     * Usage: 4th step of act() in Critter
     * ------------------------------------
     * Wolf dies if it has not eaten for 5 steps
     * But if it doesn't it moves decides to move in a totally random location 
     * chosen from the list given 
     * 
     * @Postcondition: 
     *      (1) The returned location is an element of
     *          <code>locs</code>, this critter's current location, or <code>null</code>. 
     *      (2) The state of all actors is unchanged.
     * @param locs the possible locations for the next move
     * @return the location that was selected for the next move.
     */
    public Location selectMoveLocation(ArrayList<Location> locs)
    {
        if (stepsNotEaten > 20)
            return null;
        return super.selectMoveLocation(locs);
    }
    
    /**
     * Method: makeMove(nextLoc)
     * Usage: last step of act() in Critter
     * --------------------------------------
     * Wolf turns itself in a random direction
     * then moves to the new location using super class Critter's makeMove
     * 
     * @Postcondition: 
     *      (1) <code>getLocation() == loc</code>. 
     *      (2) The state of all actors other than those at the old and new locations is unchanged.  
     * @param loc the location to move to
     */
    public void makeMove(Location loc)
    {
        setDirection((int)(Math.random()*8) * 45);
        super.makeMove(loc);
    }
}
