import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * 
 * @author Dennis Moon
 * @version Feb 11, 2013
 * 
 * Revision:
 *     Feb 11, 2013
 */
public class SampleMain
{
    //heres a sample of how things work
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Note c = new Note(40); //given a number on a piano, converts to the frequency
        
        String key = br.readLine(); //read what the user typed
        
        //pluck the correct note: add else if on
        if (key.equals("c"))
            c.pluck();
        
        while(true)
        {
            c.tic(); //updates the sound
            AudioLib.play(c.sample()); //AudioLib makes an instance of a sound
            //updating & playing an instance really fast makes the sounds
        }
    }
}
