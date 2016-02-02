
/**
 * an ArrowListener listens to the user by being connected to gui and calls the approperiate methods
 * 
 * @version 10/26/12 - interface created
 * 
 * @author Dennis Moon
 */
public interface ArrowListener
{
    /**
     * Method: upPressed()
     * Usage: arrowListner.upPressed()
     * ---------------------------------
     * called when the up arrow key is pressed
     */
	void upPressed();
	
	/**
     * Method: downPressed()
     * Usage: arrowListner.downPressed()
     * ---------------------------------
     * called when the down arrow key is pressed
     */
	void downPressed();
	
	/**
     * Method: leftPressed()
     * Usage: arrowListner.leftPressed()
     * ---------------------------------
     * called when the left arrow key is pressed
     */
	void leftPressed();
	
	/**
     * Method: rightPressed()
     * Usage: arrowListner.rigthPressed()
     * ---------------------------------
     * called when the left arrow key is pressed
     */
	void rightPressed();
	
	/**
     * Method: spacePressed()
     * Usage: arrowListner.spacePressed()
     * ---------------------------------
     * called when space bar is pressed
     */
	void spacePressed();
	
	/**
     * Method: shiftPressed()
     * Usage: arrowListner.shiftPressed()
     * ---------------------------------
     * called when shift key is pressed
     */
	void shiftPressed();
	
	/**
	 * Method: pausePressed()
	 * Usage: arrowListener.pausePressed()
	 * ---------------------------------
	 * calls the showMenu and pauses the game
	 */
	void pausePressed();
	
	void unpause();
}