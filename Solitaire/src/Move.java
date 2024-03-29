
/**
 * Move is a representation of an action made in Solitaire. 
 * It records the stacks as integer, in addition to number of cards sent, number of waste cards previously displayed and whether pile of turned up. </br>
 * 
 * @author Dennis Moon
 * @version Mar 11, 2013
 * 
 * @Revision:
 *     Mar 9, 2013 - class created: from, to, numberSent & flipped parameters added. </br>
 *     Mar 11, 2013 - added preDisplayed function. </br>
 */
public class Move
{
    public static final int STOCK = 0;
    public static final int WASTE = 1;
    
    private int start;
    private int destination;
    private int num;
    private int prevShown;
    private boolean flipped;
    
    /**
     * <b>Constructor: </b>Move</br> 
     * <b>Usage: <b>{@code undoStack.add(new Move())}</br>
     * -------------------------------</br>
     * Once the specifics of a move are set, they cannot be changed. </br>
     * 
     * @param from - int representation of origin stack
     * @param to - int representation of destination stack
     * @param numberSent - number of cards sent
     * @param prevDisplayed - number of waste cards displayed
     * @param flipped - whether the next top card of origin pile was flipped
     */
    public Move(int from, int to, int numberSent, int prevDisplayed, boolean flipped) //stock > waste
    {
        start = from;
        destination = to;
        num = numberSent;
        prevShown = prevDisplayed;
        this.flipped = flipped;
    }
    
    /**
     * <b>Method: </b>getStart</br> 
     * <b>Usage: </b>{@code Move.getStart()}</br>
     * -------------------------------</br>
     * Returns int representation of origin stack. </br>
     * 
     * @return start - int representation of origin stack
     */
    public int getStart()
    {
        return start;
    }
    
    /**
     * <b>Method: </b>getDestination</br> 
     * <b>Usage: </b>{@code Move.getDestination()}</br>
     * -------------------------------</br>
     * Returns int representation of destination stack. </br>
     * 
     * @return destination - int representation of destination stack
     */
    public int getDestination()
    {
        return destination;
    }
    
    /**
     * <b>Method: </b>getNumber</br> 
     * <b>Usage: </b>{@code Move.getNumber()}</br>
     * -------------------------------</br>
     * Returns number of cards sent. </br>
     * 
     * @return num of cards sent
     */
    public int getNumber()
    {
        return num;
    }
    
    /**
     * <b>Method: </b>getPrevDisplayed</br> 
     * <b>Usage: </b>{@code Move.getPrevDisplayed()}</br>
     * -------------------------------</br>
     * Returns number of waste cards previously displayed. </br>
     * 
     * @return prevShown - number of waste cards previously displayed
     */
    public int getPrevDisplayed()
    {
        return prevShown;
    }
    
    /**
     * <b>Method: </b>flipped</br> 
     * <b>Usage: </b>{@code Move.flipped()}</br>
     * -------------------------------</br>
     * Returns whether the card after the sent card was turned up. </br>
     * 
     * @return flipped - whether the card after the sent card was turned up
     */
    public boolean flipped()
    {
        return flipped;
    }
    
    /**
     * <b>Method: </b>convertPile</br> 
     * <b>Usage: </b>{@code Move.convertPile(index)}</br>
     * -------------------------------</br>
     * Returns the int representation of the given pile index. </br>
     * 
     * @param index of pile to convert to int representation
     * @return int representation of the given pile index
     */
    public static int convertPile(int index)
    {
        return index+2;
    }
    
    /**
     * <b>Method: </b>convertFoundation</br> 
     * <b>Usage: </b>{@code Move.convertFoundation(index)}</br>
     * -------------------------------</br>
     * Returns the int representation of the given foudnation index. </br>
     * 
     * @param index of foundation to convert to int representation
     * @return int representation of the given foundation index
     */
    public static int convertFoudation(int index)
    {
        return index+9;
    }
}
