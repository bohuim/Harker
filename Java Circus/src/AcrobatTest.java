/**
 *Acrobat object can clap, bend its knee
 * and counts each time it does any of these 
 *
 *
 * @author Dennis Moon
 * @version Sept 4 2012 
 * with assitance from: 
 *
 * Revision History:
 * Sept 4 2012 - doc created
 */
public class AcrobatTest
{

	public static void main(String[] args)
	{
	    
	    
		//Circus
	    Circus circus = new Circus();
		
		circus.join(new Acrobat("Dennis"));
		circus.clap(2);
		
		circus.join(new Acrobat("Shreyas"));
		circus.clap(3);
		
		circus.join(new Acrobat("Andy"));
		circus.kneeBend(2);
		
		System.out.println(circus.count() + " total activities performed");
	}
}