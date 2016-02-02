import java.util.Timer;
import java.util.TimerTask;

/**
 * Main class the tetris runs in
 * keeps track of all things related to the game, such as:
 *      currently active/holded/next in line tetrads & grids
 *      whether it was switched, 
 * 
 * @version 10/26/12 - tetrad can be added to the main grid, gravity is applied, and can be controled by player
 * @version 10/29/12 - hard drop and hold grid added
 * @version 10/31/12 - next grids and shows next 5 tetrads, all variables and method related to the score added
 * @version 11/2/12 - minor fixes
 * 
 * @author Dennis Moon
 */
public class Tetris implements ArrowListener
{
    private MyBoundedGrid<Block> grid;
    private MyBoundedGrid<Block> holdGrid;
    private MyBoundedGrid<Block>[] nextGrids;
    private TetrisDisplay display;
    //private BlockDisplay display;
    
    private Tetrad active;
    private Tetrad hold;
    private Tetrad[] next;
    private boolean switched; //whether hold was used on this tetrad
    
    //all game values
    private int level;
    private int score;
    private int linesCleared;
    private int combo;
    private boolean performedDifficult;
    
    private boolean paused;
    
    private Timer sideTimer;
    private boolean executeSide;
    private Timer rotationTimer;
    private boolean executeRotation;
    
    /**
     * Tetris class created and play is called to play the game
     */
    public static void main(String[] args)
    {
        Tetris game = new Tetris();
        game.play();
    }
    
    /**
     * Constructor: Tetris()
     * Usage: Tetris game = new Tetris()
     * -------------------------------
     * makes all the grids, tetrads and sets the game values to 0 or false
     */
    public Tetris()
    {
        grid = new MyBoundedGrid<Block>(20,10);
        holdGrid = new MyBoundedGrid<Block>(4,4);
        nextGrids = new MyBoundedGrid[5];
        for (int i=0; i<nextGrids.length; i++)
            nextGrids[i] = new MyBoundedGrid<Block>(4,4);
        
        //display = new BlockDisplay(grid, holdGrid, nextGrids);
        display = new TetrisDisplay(grid, holdGrid, nextGrids);
        display.setTitle("Tetris");
        display.setArrowListener(this);
        
        next = new Tetrad[5];
        for (int i=0; i<next.length; i++)
        {
            next[i] = new Tetrad(grid);
            next[i].addToSubGrid(nextGrids[i]);
        }
        
        switched = false;
        linesCleared = 0;
        combo = 0;
        performedDifficult = false;
        level = 1;
        score = 0;
        
        paused = false;
        
        sideTimer = new Timer();
        executeSide = true;
        rotationTimer = new Timer();
        executeRotation = true;
        
        display.setScore(score);
        display.setLevel(level);
    }
    
    /**
     * Method: play()
     * Usage: Tetris.play()
     * -----------------------
     * main game method of tetris
     * runs the game while is not gameover, creates a new tetrad every time the active one reaches the bottom
     * and calls clearCompleteRows to clear rows
     */
    public void play()
    {
        //loading
        try {Thread.sleep(1750);}
        catch (InterruptedException e){}
        
        while(!(gameOver()) && getNext())
        {
            try {Thread.sleep(getTime(500));}
            catch (InterruptedException e){}
            display.setScore(score);
            
            applyGravity();
            
            clearCompletedRows();
            switched = false;
        }
        System.out.println("Game over");
        display.setMessage("GAME OVER!");
        display.setArrowListener(null);
    }
    
    /**
     * Method: gameOver()
     * Usage: while(!gameOver())
     * -----------------------------
     * @return true if the very top row is filled
     */
    private boolean gameOver()
    {
        for (int c=4; c<6; c++)
            if (grid.get(new Location(0,c))!=null) 
                return true;
        return false;
    }
    
    /**
     * Method: translateDown()
     * Usage: translateDown()
     * -------------------------
     * tries to translate the active tetrad down 
     * @return true if tetrad was tranlated down
     */
    private boolean translateDown()
    {
        if (active!=null)
        {
            boolean executed = active.translate(Location.SOUTH);
            display.repaint();
            
            return executed;
        }
        return false;
    }
    
    /**
     * Method: getTime(int)
     * Usage: getTime(int)
     * ---------------------
     * returns the time according to the given time
     * @precondition given time is divisible by 5
     * @return
     */
    private int getTime(int x)
    {
        int time = x - (level-1)*(x/5);
        if (time < (time/5))
            time = (time/5);
        
        return time;
    }
    
    /**
     * Method: applyGravity()
     * Usage: applyGravity()
     * --------------------------
     * drops the currently active tetrad until it hits another tetrad or the boundary
     * to the bottom
     * recursive method to prevent the tetrad from moving after while loop ends
     */
    private void applyGravity()
    {
        while(translateDown())
        {   
            try {Thread.sleep(getTime(1000));}
            catch (InterruptedException e){}
        }
        
        try {Thread.sleep(getTime(500));}
        catch (InterruptedException e){}
        
        if (translateDown())
            applyGravity();
    }
    
    /**
     * Method: isCompleteRow(int row)
     * Usuage: isCompleteRow(row#)
     * ----------------------------
     * @param row - number to check
     * @return true if the row is filled
     */
    private boolean isCompletedRow(int row)
    {
        int filled = 0;
        for (int col=0; col<grid.getNumCols(); col++)
        {
            if (grid.get(new Location(row,col))!=null)
                filled++;
        }
        
        if (filled==grid.getNumCols())
            return true;
        return false;
    }
    
    /**
     * Method: clearRow(int row)
     * Usage: clearRow(row#)
     * ---------------------------
     * clears the given row and shifts everything down one
     * @param row - row to be cleared
     */
    private void clearRow(int row)
    {
        for (int c=0; c<grid.getNumCols(); c++)
            grid.get(new Location(row,c)).removeSelfFromGrid();
        
        for (int r=row-1; r>0; r--)
        {
            for (int c=0; c<grid.getNumCols(); c++)
            {
                Location loc = new Location(r,c);
                if (grid.get(loc)!=null)
                    grid.get(loc).moveTo(loc.getAdjacentLocation(Location.SOUTH));
            }
        }
    }
    
    /**
     * Method: checkAndClear()
     * Usage: checkAndClear()
     * ----------------------
     * checks all rows and clears any rows that are filled
     */
    private void clearCompletedRows()
    {
        int clearedNow = 0;
        int row = 1;
        
        while (row<grid.getNumRows() && !isCompletedRow(row))
            row++;
        
        for (int r=row; r<row+4 && r<grid.getNumRows(); r++)
            if (isCompletedRow(r))
            {
                clearRow(r);
                clearedNow++;
            }
        
        updateScores(clearedNow);
    }
    
    /**
     * Method: updateScores(clearedNow)
     * Usage: updateScore(4);
     * ------------------------------------
     * @param clearedNow - lines cleared with currently active tetrad
     */
    private void updateScores(int clearedNow)
    {
        linesCleared += clearedNow;      
        level = (int)(linesCleared/5 + 1);
        
        int add = 0;
        if (clearedNow == 0)
            combo = 0;
        else if (0<clearedNow && clearedNow<4)
        {
            add = (clearedNow*200-100) * level;
            display.setMessage(clearedNow + " lines cleared");
            performedDifficult = false;
            combo++;
        }
        else //performed a tetris
        {
            add = clearedNow*200*level;
            if (performedDifficult)
            {
                add = (int) (add*1.5);
                display.setMessage("Back to back Tetris!");
            }
            else
                display.setMessage("Tetris!");
            performedDifficult = true;
            combo++;
        }
        score += add + 50*combo*level;
        
        display.setLinesCleared(linesCleared);
        display.setLevel(level);
    }
    
    /**
     * Method: removeHold()
     * Usage: Tetrad removedFromHold = getNext();
     * -----------------------------------------------
     * returns the next tetrad in line and shifts all tetrad up the line 
     * then makes a new tetrad and adds it to the end of the line
     * @return true if next tetrad does not overlap existing blocks in the grid
     */
    private boolean getNext()
    {
        active = next[0];
        
        for (int i=0; i<next.length; i++)
            next[i].removeBlocks();
        for (int i=0; i<next.length-1; i++)
        {
            next[i] = next[i+1];
            next[i].addToSubGrid(nextGrids[i]);
        }
        
        next[4] = new Tetrad(grid);
        while (tooManyRepeats(next[4]))
            next[4] = new Tetrad(grid);
        next[4].addToSubGrid(nextGrids[4]);
        
        boolean putIntoMain = active.addToMain();
        if (!putIntoMain)
        {
            active = null;
            return false;
        }
        
        display.repaint();
        return true;
    }
    
    /**
     * Method: tooManyRepeats(newTetrad)
     * Usage: while(!tooManyRepeats(target)) target = new Tetrad();
     * ------------------------------------------------------------
     * prevents from there being 3 or 4 of the same tetrad in a row
     * @param target - new tetrad to be checked
     * @return - true if 3 or more already in line
     */
    private boolean tooManyRepeats(Tetrad target)
    {
        int repeat = 0;
        
        for (int i=0; i<next.length-1; i++)
            if (target.getType()==next[i].getType())
                repeat++;
        if (active!=null && target.getType()==active.getType())
            repeat++;
        if (hold!=null && target.getType()==hold.getType())
            repeat++;
        
        if (repeat>=1)
            return true;
        return false;
    }
    
    /**
     * Method: upPressed()
     * Usage: upPressed()
     * --------------------------
     * rotates the block 90deg clockwise
     * creates a Timer to block user from just continuously pressing arrow key
     */
    public void upPressed()
    {
        if (active!=null && executeRotation)
        {
            active.rotate();
            display.repaint();
            executeRotation = false;
            
            rotationTimer.schedule(new TimerTask(){
                public void run() 
                {
                    executeRotation = true;
                }    
            }, 150);
        }
    }

    /**
     * Method: downPressed();
     * Usage: downPressed();
     * --------------------------
     * moves the block 1 row down when the user presses down arrow
     */
    public void downPressed()
    {
        if (active!=null)
        {
            active.translate(Location.SOUTH);
            score++;
            display.setScore(score);
            display.repaint();
        }
    }

    /**
     * Method: leftPressed()
     * Usage: leftPressed();
     * --------------------------
     * moves the block 1 column left if left is valid and it's not already occupied
     * creates a Timer to block user from just continuously pressing arrow key
     */
    public void leftPressed()
    {
        if (active!=null  && executeSide)
        {
            active.translate(Location.WEST);
            display.repaint();
            executeSide = false;
            
            sideTimer.schedule(new TimerTask(){
                public void run() 
                {
                    executeSide = true;
                }    
            }, 80);
        }
    }

    /**
     * Method: rightPressed()
     * Usage: rightPressed();
     * --------------------------
     * moves the block 1 column right if right is valid and it's not already occupied
     * creates a Timer to block user from just continuously pressing arrow key
     */
    public void rightPressed()
    {
        if (active!=null && executeSide)
        {
            active.translate(Location.EAST);
            display.repaint();
            executeSide = false;
            
            sideTimer.schedule(new TimerTask(){
                public void run() 
                {
                    executeSide = true;
                }    
            }, 80);
        }
    }
    
    /**
     * Method: spacePressed()
     * Usage: spacePressed();
     * --------------------------
     * drops the block straight down (hard drop), and adds score according to number of rows dropped
     * and sets active to null so that it cannot be moved after a hard drop
     */
    public void spacePressed()
    {
        int count = 0;
        while (active.translate(Location.SOUTH))
            count++;      
        score += count*2;       
        
        display.setScore(score);
        display.repaint();
        active=null;
    }
    
    /**
     * Method: shiftPressed()
     * Usage: shiftPressed();
     * ----------------------------
     * switches the hold piece if hold was not used on this tetrad yet
     */
    public void shiftPressed()
    {
        if (!switched)
        {
            Tetrad temp = active;
        
            active.removeBlocks();                 
            if (hold!=null)
            {
                hold.removeBlocks();
                active = hold;
                
                active.addToMain();
            }
            else
                getNext();
            
            hold = temp;
            hold.addToSubGrid(holdGrid);
            
            display.repaint();
            switched = true;
        }
    }
    
    /**
     * Method: pausePressed()
     * Usage: pausePressed()
     * -----------------------
     * calls showMenu and pauses the game
     */
    public void pausePressed()
    {
        try 
        { 
            Thread.sleep(1); 
            System.out.println("Paused");
        }
        catch (InterruptedException e) {}
    }
    
    public void unpause()
    {
        paused = false;
    }
}
