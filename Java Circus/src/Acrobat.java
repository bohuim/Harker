/**
 *Acrobat object can clap, bend its knee
 * and counts each time it does any of these 
 *
 *
 * @author Dennis Moon
 * @version Sept 4 2012 
 * with assitance from: Andrew 
 *
 * Revision History:
 * Sept 4 2012 - doc created
 * Sept 5 2012 - added name function
 */
public class Acrobat
{

   private int count;
   private String name;

   /**
    * Method: Acrobat()
    * Usage: Acrobat objectName = new Acrobat("acrobatName")
    * 
    * Constructs a acrobat with the given name
    * 
    * @param h - takes in a string and sets it as the Acrobat's name
    */
   public Acrobat(String acrobatName)
   {

       name = acrobatName;
       count = 0;
   }

   /**
    * Method: clap
    * Usage: object.clap(n)
    * 
    * The specified Acrobat object claps according to the parameter
    * 
    * @param n - the number of times you want the object to clap
    */
   public void clap(int n)
   {
       
       if (n==0) {}
       else 
       {
           System.out.println(name + " claps");
           count++;
           n=n-1;
           clap(n);
       }
   }
   
   /**
    * Method: kneeBend
    * Usage: object.kneeBend(n)
    * 
    * The speicified Acrobat object kneeBend according to the parameter
    * 
    * @param n - the number of times you want the object to kneeBend
    */
   public void kneeBend(int n)
   {
       for (int i=0; i<n; i++)
           System.out.println(name + " claps");
	    count = count + n;
   }

   /**
    * Method: kneeBend
    * Usage: object.count()
    * 
    * @return - gives number of times a object performed either clap or kneeBend
    */
   public int count()
   {
      return count;
   }

   /**
    * Method: getName
    * Usage: object.getName()
    * 
    * @return - returns name of the Acrobat that was given during construction 
    */
   public String getName()
   {
      return name;
   }
}