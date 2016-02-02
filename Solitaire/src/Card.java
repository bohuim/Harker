
/**
 * The card is just a representation of a playing card. 
 * It does not directly interact with other objects. </br>
 * Getters getRank(), getSuit(), isRed(), isFaceUp(), and getFileName() provides type and status of a card
 * with setters turnUp() and turnDown() flipping the card up or down. </br>
 * 
 * @author Dennis Moon
 * @version Mar 3, 2013
 * 
 * Revision:
 *     Mar 3, 2013 - class created & finished
 */
public class Card
{
    private static String IMAGE_PATH = "/Users/Dennis/GDrive/Projects/Personal/Java/Solitaire/src/cards/";

    private int rank;
    private String suit;
    private boolean isFaceUp;
    
    /**
     * <b>Constructor:</b> Card</br> 
     * <b>Usage:</b> {@code new Card(#, letter)}</br>
     * -------------------------------</br>
     * Instance fields {@code rank} & {@code suit} are set to the passed parameters, but all cards are initially faced down. </br>
     * 
     * @param rank - number of the card
     * @param suitInitial - initial of the suit
     */
    public Card(int rank, String suitInitial)
    {
        this.rank = rank;
        suit = suitInitial;
        isFaceUp = false;
    }
    
    /**
     * <b>Method: </b>getRank</br> 
     * <b>Usage: </b>{@code card.getRank()}</br>
     * -------------------------------</br>
     * Returns the {@code rank} of this card. </br>
     * 
     * @return {@code rank} of this card
     */
    public int getRank()
    {
        return rank;
    }
    
    /**
     * <b>Method: </b>getSuit</br> 
     * <b>Usage: </b>{@code card.getSuit()}</br>
     * -------------------------------</br>
     * Returns the {@code suit} of this card. </br>
     * 
     * @return {@code suit} of this card
     */
    public String getSuit()
    {
        return suit;
    }
    
    /**
     * <b>Method: </b>isRed</br> 
     * <b>Usage: </b>{@code card.isRed()}</br>
     * -------------------------------</br>
     * Returns whether this card is red or not. </br>
     * 
     * @return {@code true} if suit is diamonds or hearts
     */
    public boolean isRed()
    {
        return suit.equals("d") || suit.equals("h");
    }
    
    /**
     * <b>Method: </b>getColor</br> 
     * <b>Usage: </b>{@code card.getColor()}</br>
     * -------------------------------</br>
     * Returns whether specified card has the same color. </br>
     * 
     * @return true if colors are different
     */
    public boolean isDiffColorFrom(Card card)
    {
        return (isRed() && !card.isRed()) || (!isRed() && card.isRed());
    }
    
    /**
     * <b>Method: </b>isFaceUp</br> 
     * <b>Usage: </b>{@code card.isFaceUp()}</br>
     * -------------------------------</br>
     * Returns whether this card is face up or not. </br>
     * 
     * @return {@code isFaceUp}
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }
    
    /**
     * <b>Method: </b>turnUp</br> 
     * <b>Usage: </b>{@code card.turnUp()}</br>
     * -------------------------------</br>
     * Makes this card face up by setting {@code isFaceUp = true} 
     */
    public void turnUp()
    {
        isFaceUp = true;
    }
    
    /**
     * <b>Method: </b>turnDown</br> 
     * <b>Usage: </b>{@code card.turnDown()}</br>
     * -------------------------------</br>
     * Makes this card face down by setting {@code isFaceUp = false} 
     */
    public void turnDown()
    {
        isFaceUp = false;
    }
    
    /**
     * <b>Method: </b>getFileName()</br> 
     * <b>Usage: </b>{@code card.getFileName()}</br>
     * -------------------------------</br>
     * Returns the image location for this card in the author's hard drive. </br>
     * Face down cards are directed to the back of a card image. </br>
     * Face up cards are directed to images named "ranksuit.gif" 
     * where suit is the initial of the suit and rank is a number except for 10, Jack, Queen King as t, j, q, k. </br> 
     * 
     * @return image location and name of this card
     */
    public String getFileName()
    {
        if (!isFaceUp)
            return IMAGE_PATH + "back.gif";
        
        String r = "" + rank;
        if (rank == 10) r = "t";
        else if (rank == 11) r = "j";
        else if (rank == 12) r = "q";
        else if (rank == 13) r = "k";
        else if (rank == 1) r = "a";
        
        String file = r + suit;
        return IMAGE_PATH + file + ".gif";
    }  
}
