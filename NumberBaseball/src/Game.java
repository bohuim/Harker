import java.io.*;


/**
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li></li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Jun 20, 2013
 * 
 * @Revision
 *     Jun 20, 2013
 */
public class Game
{
    
    public static void main(String[] args)
    {
        Game game = new Game(3);
        game.play();
    }
    
    int size;
    private int[] list;
    private int[] guess;
    private boolean[] strike;
    private boolean[] ball;
    
    private BufferedReader in;
    
    public Game(int size)
    {
        this.size = size;
        list = new int[size];
        guess = new int[size];
        strike = new boolean[size];
        ball = new boolean[size];
        
        in = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void play()
    {
        String cont = "yes";
        while(cont.equals("yes"))
        {
            newGame();
            
            try { cont = in.readLine(); } 
            catch (IOException e)
                { e.printStackTrace(); }
            System.out.println();
        }
    }
    
    private void newGame()
    {
        for (int i=0; i<size; i++)
        {
            list[i] = (int)(Math.random() * 10);
            System.out.print(list[i]);
        }
        System.out.println();
        playRound();
    }
    
    private void playRound()
    {
        boolean cont = true;
        while (cont)
            cont = oneGuess();
    }
    
    private boolean oneGuess()
    {
        resetVariables();
        
        System.out.print("Enter a guess: ");
        
        String userGuess = "";
        try { userGuess = in.readLine(); } 
        catch (IOException e) { e.printStackTrace(); }
        
        for (int i=0; i<guess.length; i++)
            guess[i] = Integer.parseInt(userGuess.substring(i,i+1));
        
        checkGuess();
        return result();
    }
    
    private void resetVariables()
    {
        for (int i=0; i<size; i++)
        {
            strike[i] = false;
            ball[i] = false;
        }
    }
    
    private void checkGuess()
    {
        int s = 0;
        int b = 0;
        
        for (int i=0; i<size; i++)
            if(list[i] == guess[i])
                strike[i] = true;
        
        for (int i=0; i<size; i++)
        {
            if (!strike[i]) 
                for (int n=0; n<size; n++)
                    if(guess[i]==list[n])
                        ball[i] = true;
        }
    }
    
    private boolean result()
    {
        int s = 0;
        int b = 0;
        
        for (int i=0; i<size; i++)
        {
            if (strike[i]) s++;
            if (ball[i]) b ++;
        }
        
        System.out.println(s + " strike(s)");
        System.out.println(b + " ball(s)");
        System.out.println();
        
        if (s == 3)
        {
            System.out.println("You won! Play again?");
            return false;
        }
        return true;
    }
}
