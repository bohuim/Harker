
public class Creator
{
    WordGameDisplay display;
    
    private String email;
    private String emailList;
    private static String ACCOUNT = "@students.harker.org,";
    
    public Creator()
    {
        email = "";
        emailList = "";
        display = new WordGameDisplay();
    }
    
    public void create()
    {
        display.setTitle("Email List Creator");
        int count = 0;
        
        while (true)
        {
            email = display.getGuess() + ACCOUNT;
            emailList += email;
            if (count==10 && count!=0)
                emailList += " " + "\n";
            else 
                emailList += " ";
            display.setText(emailList);
            count++;
        }
    }
    
    public void separate()
    {
        email = display.getGuess();
        String tokens[] = email.split("\n");
        
        for (int i=0; i<tokens.length; i++)
        {
            System.out.println(i);
            emailList += tokens[i] + "\n" + "\n" + "        ";
        }
        
        display.setText(emailList);
    }
}
