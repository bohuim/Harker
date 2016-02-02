/**
 * Circus can add new Acrobats to its member list
 * and command all of them to clap or kneebend a certain amount of time
 *
 * @author Dennis Moon
 * @version Sept 5 2012 
 * with assitance from: 
 *
 * Revision History:
 * Sept 5 2012 - doc created
 */

import java.util.ArrayList;

public class Circus
{

    ArrayList<Acrobat> members = new ArrayList<Acrobat>();
    
    int count;
    
    /**
     * Method: Circus
     * Usage: Circus objectName = new Circus()
     * 
     * Constructs the Circus object and sets count to 0
     */
    public Circus()
    {
        count = 0;
    }
    
    /**
     * Method: join
     * Usage: join(new Acrobat("name"))
     * 
     * Join adds a new member to the circus list
     * 
     * @param person - takes in an Acrobat class object to add to member list
     */
    public void join(Acrobat person)
    {
        members.add(person);
    }
    
    /**
     * Method: clap
     * Usage: object.clap(n)
     * 
     * All Acrobats in the member list claps
     * and the sum of all kneeBends performed by all members are added to count
     * 
     * @param n - the number of times you want the object to clap
     */
    public void clap(int n)
    {
        
        for (int i=0; i<members.size(); i++)
        {
            members.get(i).clap(n);
        }
        System.out.println("");
    }
    
    /**
     * Method: kneeBend
     * Usage: object.clap(n)
     * 
     * All Acrobats in the member list kneeBends 
     * and the sum of all kneeBends performed by all members are added to count
     * 
     * @param n - the number of times you want the object to kneeBend
     */
    public void kneeBend(int n)
    {
        for (int i=0; i<members.size(); i++)
        {
            members.get(i).kneeBend(n);
        }
        System.out.println("");
    }
    
    /**
     * Method: count
     * Usage: object.count()
     * 
     * Returns the number of times clap or kneeBend was performed
     * 
     * @return count - the number of times clap or kneeBend was performed
     */
    public int count()
    {
        int count = 0;
        for (Acrobat a : members)
            count += a.count();
        return count;
    }
}
