import java.awt.Color;
import java.util.concurrent.Semaphore;

/**
 * Snake game class
 * Contains the board and the snake
 * keeps track of snake's direction
 * 
 * @author Dennis
 */
public class Game implements ArrowListener
{
    private MyBoundedGrid<Block> grid;
    private Snake snake;
    private BlockDisplay display;
    private Block food;

    private boolean gameOver;
    private Semaphore lock;
    
    /**
     * Constructor: Game()
     * Usage: called by main thread
     * -------------------------------
     * makes the grid, puts the snake into the grid,
     * sets itself as display's arrowListener
     * and adds 1 food
     */
    public Game()
    {
        grid = new MyBoundedGrid(30,60);
        snake = new Snake(grid);
        display = new BlockDisplay(grid);
        display.setArrowListener(this);
        
        makeFood();
        display.repaint();
        
        gameOver = false;
        
        lock = new Semaphore(1, true);
    }
    
    /**
     * Method: play()
     * Usage: Game.play() in main thread
     * ----------------------------
     * moves the snake 
     * makes food if food was eaten
     * repaints the display
     * and repeats every 80ms 
     * 
     * gameOver if snake.move() returns false
     */
    public void play()
    {
        while(!gameOver)
        {
            gameOver = !snake.move();
            makeFood();
            display.repaint();
            try
            {
                Thread.sleep(80);
            }
            catch (InterruptedException e){}
        }

    }
    
    /**
     * Method: makeFood()
     * Usage: called by play after each move from snake
     * ---------------------------------
     * if food is null or there is no food in the grid
     * a new food (a black block) is created and added to the grid
     * at an random location that is not already occupied by snake
     */
    public void makeFood()
    {   
        if (food==null || food.getGrid()==null)
        {   
            food = new Block();
            food.setColor(Color.BLACK);
            
            Location foodLoc = new Location((int)(Math.random()*grid.getNumRows()), (int)(Math.random()*grid.getNumCols()));
            while(grid.get(foodLoc)!=null)
                foodLoc = new Location((int)(Math.random()*grid.getNumRows()), (int)(Math.random()*grid.getNumCols()));
                
            food.putSelfInGrid(grid,foodLoc);
        }
    }
    
    /**
     * Method: upPressed()
     * Usage: changes direction of snake
     * ---------------------------------
     * sets snake's direction to north
     */
    public void upPressed()
    {
        if (snake.getDirection()!=Location.SOUTH)
            snake.changeDirection(Location.NORTH);
    }

    /**
     * Method: Pressed()
     * Usage: changes direction of snake
     * ---------------------------------
     * sets snake's direction to south
     */
    public void downPressed()
    {
        if (snake.getDirection()!=Location.NORTH)
            snake.changeDirection(Location.SOUTH);
    }

    /**
     * Method: leftPressed()
     * Usage: changes direction of snake
     * ---------------------------------
     * sets snake's direction to west
     */
    public void leftPressed()
    {
        if (snake.getDirection()!=Location.EAST)
            snake.changeDirection(Location.WEST);
    }

    /**
     * Method: rightPressed()
     * Usage: changes direction of snake
     * ---------------------------------
     * sets snake's direction to east
     */
    public void rightPressed()
    {
        if (snake.getDirection()!=Location.WEST)
            snake.changeDirection(Location.EAST);
    }
}
