/**
 * A usable item in our game. It can be placed in a room or given to a person and be used by a person.
 * 
 * @version 120920
 *          created as a subclass of Thing, includes potions and scrolls
 * 
 * @author - Dennis Moon
 */
public class Consume extends Thing
{   
    public static final int POTION = 4;
    public static final int SCROLL = 5;
    
    private int rating;
    
    private ThingOwner owner;

    public Consume(String thingName, int aType, int power)
    {
        super(thingName, aType);
        rating = power;
    }
    
    /**
     * returns the attack/defense of weapon/armor
     * 
     * @return rating of weapon/armor
     */
    public int getRating()
    {
        return rating;
    }
    
    /**
     * getOwner returns owner of this object
     * 
     * @return owner - the current owner of this object
     */
    public ThingOwner getOwner()
    {
        return owner;
    }
    
    /**
     * changeOwner removes the old owner and adds the new owner
     * @param newOwner - new owner of the item
     */
    public void changeOwner(ThingOwner newOwner)
    {
        if (owner != null)
            owner.remove(this);
        newOwner.add(this);
        owner = newOwner;
    }

}
