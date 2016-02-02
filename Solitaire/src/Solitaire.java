import java.awt.event.*;
import java.util.*;

import javax.swing.JFrame;

/**
 * Solitaire class is where the game actually runs. All the different decks of cards such as the stock, waste, and piles are kept as stacks. </br>
 * The SolitaireDisplay class provides Solitaire with which stacks are selected and clicked. </br>
 * By the display calling the appropriate methods like stockClicked(), wasteClicked, etc, the Solitaire class handles the game according to the rules. </br>
 * 
 * @author Dennis Moon
 * @version Mar 11, 2013 </br>
 * 
 * @Revision
 *     Mar 3, 2013 - class created: constructor, createStock(), deal(), dealThreeCards(), getStockCard(), getWasteCard(), getPile(), stockClicked(), wasteClicked() added </br>
 *     Mar 4, 2013 - foundationClicked(), pileClicked(), canAddToPile(), removeFaceUpCards(), addToPile() added; modified so that top 3 cards of waste are showed </br>
 *     Mar 5, 2013 - extends KeyAdapter & newGame() added; pileDoubleClicked() allows double click to send to foundation, removeFaceUp() modified to allow moving only some cards </br>
 *                  shortcut methods that allow quicker move from field to foudations added </br>
 *     Mar 9, 2013 - implemented undo function, which uses a stack of Moves </br>
 *     Mar 11, 2013 - overrode undo() to hard coding for each case; removeFaceUpCards() & addToPile() modified to take Stack<Card>, not index of a pile </br>
 *     Mar 12, 2013 - modified constructor & reset() method to reset start time of display
 */
public class Solitaire extends KeyAdapter
{
    /**
     * <b>Method: </b>main</br> 
     * <b>Usage: </b>{@code main}</br>
     * -------------------------------</br>
     * initializes the solitaire class
     * 
     * @param args
     */
	public static void main(String[] args)
	{
		new Solitaire();
	}

	private Stack<Card> stock;
	private Stack<Card> waste;
	private Stack<Card>[] foundations;
	private Stack<Card>[] piles;
	private SolitaireDisplay display;
	
	private int selectedNumber;
	private Stack<Move> undoStack;

	/**
	 * <b>Constructor: </b>Solitaire</br> 
	 * <b>Usage: </b>{@code new Solitaire()}</br>
	 * -------------------------------</br>
	 * Constructor initializes all the stacks used in the game: stock, waste, piles, foundations </br>
	 * createStock() is called to make the deck & shuffle it, which is followed by deal() that sets up the game </br>
	 * display is initialized </br>
	 *
	 */
	public Solitaire()
	{   
	    stock = new Stack<Card>();
        waste = new Stack<Card>();
        foundations = new Stack[4];
        for (int i=0; i<foundations.length; i++)
            foundations[i] = new Stack<Card>();
        piles = new Stack[7];
        for (int i=0; i<piles.length; i++)
            piles[i] = new Stack<Card>();
        
        selectedNumber = 0;
        undoStack = new Stack<Move>();
	    display = new SolitaireDisplay(this);
	    
	    createStock();
        deal();
        
        display.resetTime();
        new Thread(display).start();
	}
	
	/**
	 * <b>Method: </b></br> 
	 * <b>Usage: </b>{@code }</br>
	 * -------------------------------</br>
	 * Resets the game by clearing all stacks of cards, and the waste pile in display. Then re-deals the cards.
	 */
	public void newGame()
	{
        for (int i=0; i<piles.length; i++)
            piles[i].clear();
        for (int i=0; i<foundations.length; i++)
            foundations[i].clear();
        stock.clear();
        waste.clear();
        display.clearWastes();
        display.repaint();
        
        createStock();
        deal();
        
        display.resetTime();
	}
	
	/**
	 * <b>Method: </b>createStock</br> 
	 * <b>Usage: </b>{@code createStock()}</br>
	 * -------------------------------</br>
	 * createStock makes a standard deck of cards & shuffles them into stock. </br>
	 * An ArrayList of Card is created and filled with each suit & rank. </br>
	 * Then each card is randomly added to the stock. </br>
	 */
	private void createStock()
	{
	    ArrayList<Card> deck = new ArrayList<Card>();
	    for (int i=1; i<=13; i++)
	        for (int s=0; s<4; s++)
	        {   
	            String suit;
	            if (s==0) suit = "d";
	            else if (s==1) suit = "c";
	            else if (s==2) suit = "h";
	            else suit = "s";
	            deck.add(new Card(i, suit));
	        }
	    
	    int size = deck.size();
	    for (int i=0; i<size; i++)
	    {
	        int index = (int)(Math.random()*deck.size());
	        stock.add(deck.remove(index));
	    }
	    display.repaint();
	}
	
	/**
	 * <b>Method: </b>deal</br> 
	 * <b>Usage: </b>{@code deal()}</br>
	 * -------------------------------</br>
	 * {@code deal()} deals the cards from a new stack to the piles to setup the game. </br>
	 * One card is dealt to a pile at a time until the number of cards in a pile is one more than index of the pile & the last card is faced up. </br>
	 * Slight delay in dealing to show animation. </br>
	 */
	private void deal()
	{
	    for (int i=0; i<7; i++)
	    {
            for (int n=i; n<7; n++)
            {
                piles[n].add(stock.pop());
                try{ Thread.sleep(15); }
                catch(InterruptedException e){}
                display.repaint();
            }
            piles[i].lastElement().turnUp();
	    }
	}

	/**
	 * <b>Method: </b>getStockCard</br> 
	 * <b>Usage: </b>{@code game.getStockCard()}</br>
	 * -------------------------------</br>
	 * Returns the top card of stock or null if stock is empty.
	 * 
	 * @return top card of stock
	 */
	public Card getStockCard()
	{
	    if (stock.size()==0)
	        return null;
		return stock.peek();
	}

	/**
	 * <b>Method: </b>getWasteCard</br> 
	 * <b>Usage: </b>{@code game.getWasteCard()}</br>
	 * -------------------------------</br>
	 * Returns the top card of waste or null if waste is empty.
	 * 
	 * @return top card of waste 
	 */
	public Card getWasteCard()
	{
	    if (waste.size()==0)
	        return null;
		return waste.peek();
	}

	/**
	 * <b>Method: </b>getFoundationCard</br> 
	 * <b>Usage: </b>{@code game.getFoundationCard(index)}</br>
	 * -------------------------------</br>
	 * Returns the top card of the specified foundation or null if that foundation is empty.
	 * 
	 * @precondition 0 <= index < 4
	 * @param index of the foundation
	 * @return top card of specified foundation
	 */
	public Card getFoundationCard(int index)
	{
	    if (foundations[index].size()==0)
	        return null;
		return foundations[index].peek();
	}

	/**
	 * <b>Method: </b>getPile</br> 
	 * <b>Usage: </b>{@code game.getPile()}</br>
	 * -------------------------------</br>
	 * Returns the specified pile 
	 * 
	 * @precondition 0 <= index < 7
	 * @param index of the pile
	 * @return the specified pile
	 */
	public Stack<Card> getPile(int index)
	{
		return piles[index];
	}
	

	/**
	 * <b>Method: </b>stockClicked</br> 
	 * <b>Usage: </b>{@code game.stockClicked()}</br>
	 * -------------------------------</br>
	 * Called by display when the stock is clicked. </br>
	 * Regardless of what is currently selected, it is unselected and stock is activated. </br>
	 * Three cards are flipped to the waste or stock is refilled from waste if empty.
	 */
	public void stockClicked()
	{
	    display.unselect();
	    if (stock.size()>0)
	        dealThreeCards();
	    else
	        resetStock();
	}
	
	/**
	 * <b>Method: </b>dealThreeCards</br> 
	 * <b>Usage: </b>{@code dealThreeCards()}</br>
	 * -------------------------------</br>
	 * Up to three cards from the stock are flipped to the waste. </br>
	 * Display's waste pile is cleared to show new top three cards. </br>
	 * Each card popped to waste is turned up and added to display's waste pile. </br>
	 */
	private void dealThreeCards()
    {
        int run = 3;
        if (stock.size()<3) run = stock.size();
        
        int displayed = display.clearWastes();
        for (int i=0; i<run; i++)
        {
            Card popped = stock.pop();
            waste.add(popped);
            waste.lastElement().turnUp();
            display.addWaste(popped);
        }
        undoStack.add(new Move(Move.STOCK, Move.WASTE, run, displayed, false));
    }
	
	/**
	 * <b>Method: </b>resetStock()</br> 
	 * <b>Usage: </b>{@code resetStock()}</br>
	 * -------------------------------</br>
	 * All cards from waste is flipped back to stock, turned down, and display's waste pile is cleared.
	 */
	private void resetStock()
    {
        int size = waste.size();
        for (int i=0; i<size; i++)
        {
            stock.add(waste.pop());
            stock.lastElement().turnDown();
        }
        int displayed = display.clearWastes();
        undoStack.add(new Move(Move.WASTE, Move.STOCK, size, displayed, false));
    }

	/**
	 * <b>Method: </b>wasteClicked</br> 
	 * <b>Usage: </b>{@code game.wasteClicked()}</br>
	 * -------------------------------</br>
	 * Called by display when waste is selected. </br>
	 * Waste is selected if waste has cards and waste or a pile is not already selected. Otherwise current selected item is unselected.
	 */
	public void wasteClicked()
	{
		if (waste.size()>0 && !display.isWasteSelected() && !display.isPileSelected())
		    display.selectWaste();
		else
		    display.unselect();
	}
	
	/**
	 * <b>Method: </b>removeDisplayWaste</br> 
	 * <b>Usage: </b>{@code removeDisplayWaste()}</br>
	 * -------------------------------</br>
	 * If last card of displayed waste is removed, but actual waste is not empty, next top card of waste is added to display's waste pile.
	 */
	private void removeDisplayWaste()
	{
	    if (display.removeWaste() && waste.size()!=0)
            display.addWaste(waste.peek());
	}
	
	private void displayTopWasteCards(int n)
	{
	    display.clearWastes();
	    Stack<Card> removed = new Stack<Card>(); //display the top three cards of waste
        for (int i=0; i<n; i++)
            removed.add(waste.pop());
        for (int i=0; i<n; i++)
        {
            Card reAdd = removed.pop();
            waste.add(reAdd);
            display.addWaste(reAdd);
        }
	}

	/**
	 * <b>Method: </b>foundationClicked</br> 
	 * <b>Usage: </b>{@code game.foundationClicked(index)}</br>
	 * -------------------------------</br>
	 * Called by display when a foundation is clicked and unselected after method. </br>
	 * If a pile was selected and top card of pile can move specified to foundation, it is moved and next card of selected pile is turned up. </br>
	 * If waste was selected and top card of waste can be moved specified to foundation, it moved and top card of display's waste pile is removed. </br>
	 * 
	 * @precondition 0 <= index < 4
	 * @param index of target foundation
	 */
	public void foundationClicked(int index)
	{
	    if (!display.isFoundationClicked() && !display.isPileSelected() && !display.isWasteSelected())
	        display.selectFoundation(index);
	    else if (display.isPileSelected() && canAddToFoundation(piles[display.selectedPile()].peek(), index))
		{
		    foundations[index].add(piles[display.selectedPile()].pop());		    
		    boolean flipped = flipTopOfPile(display.selectedPile());
		    undoStack.add(new Move(Move.convertPile(display.selectedPile()), Move.convertFoudation(index), 1, 0, flipped));
		    display.unselect();
		}
		else if (display.isWasteSelected() && canAddToFoundation(waste.peek(), index))
		{
		    foundations[index].add(waste.pop());
		    undoStack.add(new Move(Move.WASTE, Move.convertFoudation(index), 1, 0, false));
		    removeDisplayWaste();
		    display.unselect();
		}
	}

	/**
	 * <b>Method: </b>canAddToFoundation</br> 
	 * <b>Usage: </b>{@code canAddToFoundation(card, index)}</br>
	 * -------------------------------</br>
	 * Aces can be moved to empty foundations. Other cards must be one rank higher and of the same suit.
	 * 
	 * @param card to check 
	 * @param index of clicked foundation
	 * @return true if selected card abides by rules
	 */
	private boolean canAddToFoundation(Card card, int index)
	{
	    if (foundations[index].size()==0)
	    {
	        if (card.getRank()==1)
	            return true;
	        return false;
	    }
	    
	    if (card.getSuit().equals(foundations[index].peek().getSuit()) && (card.getRank()-1 == foundations[index].peek().getRank()))
	        return true;    
	    return false;
	}
	
	/**
     * <b>Method: </b>wasteDoubleClicked</br> 
     * <b>Usage: </b>{@code game.wasteDoubleClicked}</br>
     * -------------------------------</br>
     * Called when waste is double clicked for shortcut add to foundation. </br>
     * Top card of waste is checked with all foundations and if it can be added to any foundations,
     * it is added, previous select is unselected and display's waste is removed. </br>
     * 
     * @precondition waste is not empty
     * @return true if waste was added to a foudation
     */
    public boolean shortcutFromWaste()
    {
        if (waste.size()!=0)
        {
            Card target = waste.peek();
            int in = -1;
            for (int i=3; i>=0; i--)
                if (canAddToFoundation(target, i))
                    in = i;
            
            if (in != -1)
            {
                display.selectWaste();
                foundationClicked(in);
                return true;
            }
        }
        return false;
    }
    
    /**
     * <b>Method: </b>shortcutFromPile</br> 
     * <b>Usage: </b>{@code game.shortCutFromPile()}</br>
     * -------------------------------</br>
     * Called when a pile is double clicked for shortcut add to foundation. </br>
     * Top card of specified pile is checked with all foundations and if it can be added to any foundations,
     * it is added, previous select is unselected and next top card of the pile if turned up. </br> 
     * 
     * @param index of clicked pile
     * @return true if card from pile was added to a foundation
     */
    public boolean shortcutFromPile(int index)
    {
        if (piles[index].size() != 0)
        {
            Card target = piles[index].peek();
            int in = -1;
            for (int i=3; i>=0; i--)
                if (canAddToFoundation(target, i))
                    in = i;
            
            if (in != -1)
            {
                display.selectPile(index);
                foundationClicked(in);
                display.unselect();
                return true;
            }
        }
        return false;
    }
    
    /**
     * <b>Method: </b>allShortcut</br> 
     * <b>Usage: </b>{@code game.allShortCut}</br>
     * -------------------------------</br>
     * Recursive method to send all available cards to foundations. </br>
     * A round of shortcut methods are called on waste and all piles. If any of return true, allShortcut is called again. </br>
     * 
     * @param cont - true if method is called
     */
    public void allShortcut(boolean cont)
    {
        ArrayList<Boolean> list = new ArrayList<Boolean>();
        list.add(shortcutFromWaste());
        for (int i=0; i<7; i++)
            list.add(shortcutFromPile(i));
        
        if (list.contains(true))
            allShortcut(cont);
    }

	/**
	 * <b>Method: </b></br> 
	 * <b>Usage: </b>{@code }</br>
	 * -------------------------------</br>
	 * Called by display when a pile is clicked. </br>
	 * <ol>
	 *     <li> Nothing previously selected - All face up cards of the specified pile is selected. </li>
	 *     <li> Same pile is clicked - Most bottom face-up card of pile is dropped from selection until 0 cards are selected. </li>
	 *     <li> Waste previously selected - If top card of waste can be added, it is added and {@code removeDisplayWaste()} is called. </br>
	 *     <li> Different pile previously selected - Number of cards selected are popped off and added to specified pile if possible or back to previous pile if not. </li>
	 * </ol>
	 * Last two routes unselect regardless of whether card(s) was moved or not.
	 * 
	 * @precondition 0 <= index < 7
	 * @param index of clicked pile
	 */
	public void pileClicked(int index)
	{
	    if (!display.isWasteSelected() && !display.isPileSelected() && !display.isFoundationClicked())
	    {
	        selectedNumber = countFaceUpCards(index);
	        display.selectPile(index);
	    }
	    else if (display.selectedPile()==index)
	    {
	        selectedNumber--;
	        if (selectedNumber == 0)
	            display.unselect();
	    }
	    else if (display.isFoundationClicked())
	    {
	        if (canAddToPile(foundations[display.selectedFoundation()].peek(), index))
	        {
	            piles[index].add(foundations[display.selectedFoundation()].pop());
	            undoStack.add(new Move(Move.convertFoudation(display.selectedFoundation()), Move.convertPile(index), selectedNumber, 0, false));
	        }
	        display.unselect();
	    }
	    else if (display.isWasteSelected())
	    {
	        display.unselect();
	        if (canAddToPile(waste.peek(), index))
	        {
	            piles[index].add(waste.pop());
	            undoStack.add(new Move(Move.WASTE, Move.convertPile(index), 1, 0, false));
	            removeDisplayWaste();
	        }
	        else
	            pileClicked(index);
	    }
	    else if (display.isPileSelected())
	    {
	        Stack<Card> removed = removeFaceUpCards(piles[display.selectedPile()], selectedNumber);
	        if (canAddToPile(removed.peek(), index))
	        {
	            addToPile(removed, piles[index]);
	            boolean flipped = flipTopOfPile(display.selectedPile());
	            undoStack.add(new Move(Move.convertPile(display.selectedPile()), Move.convertPile(index), selectedNumber, 0, flipped));
	        }
	        else
	            addToPile(removed, piles[display.selectedPile()]);
	        display.unselect();
	        selectedNumber = 0;
	    }
	    
	}
	
	/**
     * <b>Method: </b>flipTopOfPile</br> 
     * <b>Usage: </b>{@code flipTopOfPile()}</br>
     * -------------------------------</br>
     * Top card of specified pile is turned up if pile is not empty and true is returned if top card was flipped. </br>
     * 
     * @param index of specified pile
     * @return true if tope of pile was flipped
     */
    private boolean flipTopOfPile(int index)
    {
        if (piles[index].size()!=0)
        {
            piles[index].peek().turnUp();
            return true;
        }
        return false;
    }
	
    /**
     * <b>Method: </b>getSelectedNumber</br> 
     * <b>Usage: </b>{@code game.getSelectedNumber}</br>
     * -------------------------------</br>
     * Returns the number of cards selected in a selected pile. </br>
     * 
     * @return selectedNumber
     */
	public int getSelectedNumber()
    {
        return selectedNumber;
    }
	
	/**
	 * <b>Method: </b>canAddToPile</br> 
	 * <b>Usage: </b>{@code canAddToPile()}</br>
	 * -------------------------------</br>
	 * Only a king can be added to an empty pile. </br>
	 * Otherwise, the new card must of different color and a rank lower. </br>
	 * 
	 * @param card to check
	 * @param index of target pile
	 * @return true if card card can be added
	 */
	private boolean canAddToPile(Card card, int index)
	{
	    if (piles[index].size()==0)
	    {
	        if (card.getRank()==13)
	            return true;
	        else
	            return false;
	    }
	    
	    Card prev = piles[index].peek();
	    if ( prev.isDiffColorFrom(card) && card.getRank()+1 == prev.getRank() )
	        return true;
	    return false;
	}
	
	//precondition: 0 <= index < 7
	/**
	 * <b>Method: </b>countFaceUpCards</br> 
	 * <b>Usage: </b>{@code countFaceUpCards(index)}</br>
	 * -------------------------------</br>
	 * All face up cards are removed as a stack from the specified pile and added back. Returns the size of the stack. </br>
	 * 
	 * @param index of specified pile
	 * @return number of face up cards of specified pile
	 */
	private int countFaceUpCards(int index)
	{
	    Stack<Card> count = new Stack<Card>();
        while (piles[index].size()>0 && piles[index].peek().isFaceUp())
            count.add(piles[index].pop());
	    int size = count.size();
	    addToPile(count, piles[index]);
	    return size;
	}
	
	/**
	 * <b>Method: </b>removeFaceUpCards</br> 
	 * <b>Usage: </b>{@code removeFaceUpCards(index)}</br>
	 * -------------------------------</br>
	 * Removes the specified number of cards from the given Stack<Card> and returns them as a new stack. </br>
	 * 
	 * @param index of pile to remove cards
	 * @return stack of removed cards
	 */
	private Stack<Card> removeFaceUpCards(Stack<Card> cards, int n)
	{
	    Stack<Card> remove = new Stack<Card>();
        while (cards.size()>0 && cards.peek().isFaceUp() && n>0)
        {
            remove.add(cards.pop());
            n--;
        }
        return remove;
	}
	
	/**
	 * <b>Method: </b>addToPile</br> 
	 * <b>Usage: </b>{@code addToPile()}</br>
	 * -------------------------------</br>
	 * Adds all cards from the given Stack<Card> start to the specified Stack<Card> end. </br>
	 * 
	 * @precondition 0 <= index < 7
	 * @param cards stack of cards to be moved
	 * @param index of target pile
	 */
	private void addToPile(Stack<Card> start, Stack<Card> end)
	{
	    int size = start.size();
	    for (int i=0; i<size; i++)
	        end.add(start.pop());
	}
	
	/**
	 * <b>Method: </b>undo</br> 
	 * <b>Usage: </b>{@code undo()}</br>
	 * -------------------------------</br>
	 * Pops one move from the undoStack and reverses the move.
	 * Move's original start & destination stacks are reversed during undo process. </br></br>
	 * Different implementation according to destination: 
	 * <ul>
	 *     <li>Stock - Any card moving back to the stock is faced down. 
	 *         If removing one waste card from display empties display, next top three waste cards are displayed. 
	 *         Otherwise, number previously displayed is redisplayed. </br>
	 *     <li>Waste - If more than one card is returned to waste, it is reverse of resetStock() and therefore number of cards previously displayed is redisplayed. 
	 *         Otherwise, the card is returned to waste and added to display. </br>
	 *     <li>Pile/Foundation - If returned stack of cards caused next card in pile to face up, that card is faced down. </br>
	 * </ul>
	 */
	private void undo()
	{
	    Move move = undoStack.pop();
	    Stack<Card> start = getStack(move.getDestination()); //Move's destination is undo's target
	    Stack<Card> destination = getStack(move.getStart()); //Move's target is undo's detination
	    int run = move.getNumber();
	    
	    if (move.getStart()==Move.STOCK) //cards returning to stock can only come from waste
	    {
	        for (int i=0; i<run; i++)
	        {
	            destination.add(start.pop());
	            destination.peek().turnDown();
	            if (display.removeWaste() && waste.size()>0) //if waste exists but display is empty
	                displayTopWasteCards(3);
	        }
	        displayTopWasteCards(move.getPrevDisplayed());
	    }
	    else if (move.getStart()==Move.WASTE) //to waste
	    {
	        if (run > 1)
	        {
	            for (int i=0; i<run; i++)
	            {
	                start.peek().turnUp();
	                destination.add(start.pop());
	            }
	            displayTopWasteCards(move.getPrevDisplayed());
	        }
	        else
	        {
	            Card card = start.pop();
	            destination.add(card);
	            display.addWaste(card);
	        }
	    }
	    else //to pile/foundation
        {
            if (move.flipped() && destination.size()>0) //if destination is a pile, top card must be turned down
                destination.peek().turnDown();
            addToPile(removeFaceUpCards(start, run), destination);
        }
	    
	    display.repaint();
	}
	
	/**
	 * <b>Method: </b>getStack</br> 
	 * <b>Usage: </b>{@code getStack(index)}</br>
	 * -------------------------------</br>
	 * Returns a stack in the game according to the index from Move given. </br>
	 * <ul>
	 *     <li>0 - stock</li>
	 *     <li>1 - waste</li>
	 *     <li>2~9 - piles[index-2]</li>
	 *     <li>9+ - foundations[index-9]</li>
	 * </ul>
	 * 
	 * @param index of stack according to Move class
	 * @return specified stack
	 */
	private Stack<Card> getStack(int index)
	{
	    if (index == 0)
	        return stock;
	    else if (index == 1)
	        return waste;
	    else if (1<index && index<=8)
	        return piles[index-2];
        return foundations[index-9];
	}
	
	/**
	 * <b>Method: </b>keyPressed</br> 
	 * <b>Usage: </b>{@code keyPressed(KeyEvent)}</br>
	 * -------------------------------</br>
	 * Picks up the keypress. 
	 * <ul>
	 *     <li>F2 - calls {@code newGame()} and resets the game. </li>
	 *     <li>Ctrl+Z - calls {@code undo()} and undos the lastest action. </li>
	 * </ul>
	 * 
	 * @param e - keyEvent
	 */
	public void keyPressed(KeyEvent e)
	{
	    int code = e.getKeyCode();
	    if (code == e.VK_F2)
	        newGame();
	    else if (code == e.VK_Z && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0)
	        if (undoStack.size() > 0)
	            undo();
	}
}