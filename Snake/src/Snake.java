import java.awt.Color;
import java.util.ArrayList;

/**
 * Body of the snake class
 * has an arraylist of blocks
 * 
 * @author Dennis Moon
 */
public class Snake
{
    private Block head;
    private ArrayList<Block> body;
    private MyBoundedGrid<Block> grid;
    
    private int direction;
    
    //number left to grow to be used by move: if growNumber is > 0, move adds a body block
    private int growNumber;
    
    /**
     * Constructor: Snake()
     * Usage: Snake snake = new Snake();
     * ---------------------------------
     * takes in a grid to set the grid, which never changes
     * the head is created & added to (15,15)
     * the body has 3 blocks which are added toward the south form the head
     * initial direction is north
     * 
     * @param gr - grid snake is put into
     */
    public Snake(MyBoundedGrid<Block> gr)
    {
        grid = gr;
        
        head = new Block();
        head.setColor(Color.RED); 
        Location headLoc = new Location(15,15);
        
        body = new ArrayList<Block>();
        Location[] initial = new Location[3];
        body.add(new Block());
        initial[0] = headLoc.getAdjacentLocation(Location.SOUTH);
        for (int i=1; i<initial.length; i++)
        {
            body.add(new Block());
            initial[i] = initial[i-1].getAdjacentLocation(Location.SOUTH);
        }
        
        addToLocations(headLoc, initial);
        
        direction = Location.NORTH;
        growNumber = 0;
    }
    
    /**
     * Method: removeBlocks()
     * Usage: called by move() to remove snake 
     * -----------------------------------
     * removes the head & body blocks from the grid
     */
    private void removeBlocks()
    {
        head.removeSelfFromGrid();
        for (Block b : body)
            b.removeSelfFromGrid();
    }
    
    /**
     * Method: addToLocations(head,body)
     * Usage: called by move() to add to new locations
     * -----------------------------------
     * puts the head into headLoc and
     * body into newLocs
     * 
     * @param headLoc - new location of the head
     * @param newLocs - new locations of the body
     */
    private void addToLocations(Location headLoc, Location[] newLocs)
    {
        head.putSelfInGrid(grid, headLoc);
        for (int i=0; i<newLocs.length; i++)
            body.get(i).putSelfInGrid(grid, newLocs[i]);
    }
    
    /**
     * Method: areValid(newLocs[])
     * Usage: if(areValid(newLocs))
     * --------------------------------
     * @param newLocs - new location of the blocks
     * @return true if all are valid
     */
    public boolean areValid(Location headLoc, Location[] newLocs)
    {
        if (!grid.isValid(headLoc))
            return false;
        
        int valid = 0;
        for (int i=0; i<body.size(); i++)
            if (grid.isValid(newLocs[i]))
                valid++;
        
        if (valid == body.size())
            return true;
        return false;
    }
    
    /**
     * Method: doesNotEatItself(Location[])
     * Usage: doesNotEatITself(newLocs)
     * -----------------------------------
     * @param newLocs - translated locations of the snake
     * @return true if the head's newLoc is not any of the body blocks' newLoc
     */
    private boolean eatsItself(Location newHeadLoc, Location[] newLocs)
    {
        for (int i=0; i<newLocs.length; i++)
            if (newHeadLoc.equals(newLocs[i]))
                return true;
        return false;
    }
    
    /**
     * Method: changeDirection()
     * Usage: called by Game to change snake's direction
     * ---------------------------------
     * @param dir - new direction
     */
    public void changeDirection(int dir)
    {
        this.direction = dir;
    }
    
    /**
     * Method: getDirection()
     * Usage: called by game to get direction of snake
     * --------------------------------
     * @return direction of snake
     */
    public int getDirection()
    {
        return direction;
    }
    
    /**
     * Method: move(direction)
     * Usage: snake.move(Location.EAST);
     * --------------------------------------
     * advances the head to the location adj in direction
     * and each body piece moves to the loc of the body piece before it
     * 
     * if all new locs are valid & snake does not eat itself
     * snake is removed, enlongated if necessary, and added back
     * 
     * @param int - the direction the first block moves in
     * @return true if succeeded
     */
    public boolean move()
    {   
        Location newHeadLoc = head.getLocation().getAdjacentLocation(direction);
 
        Location[] newLocs = new Location[body.size()];            
        newLocs[0] = head.getLocation();  
        for (int i=1; i<body.size(); i++)
            newLocs[i] = body.get(i-1).getLocation();
        
        Location newBodyLoc = body.get(body.size()-1).getLocation();
        
        if (areValid(newHeadLoc, newLocs) && !eatsItself(newHeadLoc,newLocs))
        {
            removeBlocks();
            if (eatsFood(newHeadLoc) || growNumber>0)
                grow( eatsFood(newHeadLoc), newBodyLoc);
            addToLocations(newHeadLoc, newLocs);
            return true;
        }
        return false;
    }
    
    /**
     * Method: eatsFood()
     * Usage: called by move to check if snake eats
     * -------------------------------
     * if the new headLoc is has a block before head moves
     * the snake eats
     * 
     * @param newHeadLoc - loc the head is going to move to
     * @return - true if that loc has food
     */
    private boolean eatsFood(Location newHeadLoc)
    {
        if (grid.get(newHeadLoc)!=null)
            return true;
        return false;
    }
    
    /**
     * Method: grow()
     * Usage: called by move to increase body
     * --------------------------------
     * if snake eats 5 is added to 
     * @param eatsFood
     * @param newBodyLoc
     */
    private void grow(boolean eatsFood, Location newBodyLoc)
    {
        if (eatsFood)
            growNumber += 5;
        else if (growNumber<0)
            growNumber = 5;
        
        body.add(new Block());
        body.get(body.size()-1).putSelfInGrid(grid, newBodyLoc);
        
        growNumber--;
    }
    
    
}
