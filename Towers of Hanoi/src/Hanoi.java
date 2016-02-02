import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Dennis Moon
 */
public class Hanoi extends KeyAdapter
{
    private TowerDisplay display;
    private Tower[] tower;
    private int discNum;
    
    private boolean step;
    
    public Hanoi(int num)
    {   
        discNum = num;
        tower = new Tower[3];
        for (int i=0; i<3; i++)
            tower[i] = new Tower(i+1);
        for (int i=0; i<num; i++)
            tower[0].addDisc(new Disc(num-i));
        
        display = new TowerDisplay(tower);
        display.addKeyListener(this);
        
        step = false;
    }
    
    public void moveDiscs()
    {
        moveHelp(discNum, tower[0], tower[1], tower[2]);
    }
    
    private void moveHelp(int n, Tower source, Tower temp, Tower destination)
    {
        if (n>0)
        {
            moveHelp(n-1, source, destination, temp);
            display.repaint();
            try { Thread.sleep(100); }
            catch(InterruptedException e) {}
            moveDisc(n, source, destination);
            display.repaint();
            try { Thread.sleep(100); }
            catch(InterruptedException e) {}
            moveHelp(n-1, temp, source, destination);
            display.repaint();
            try { Thread.sleep(100); }
            catch(InterruptedException e) {}
            
//            while (step)
//            {
//                try { Thread.sleep(1); }
//                catch(InterruptedException e) {}
//            }
//            step = true;
//            
//            Disc myDisc = source.getDisc(source.getNumDisc()-n);
//            moveHelp(n-1, source, destination, temp);
//            display.repaint();
//            
//            while (step)
//            {
//                try { Thread.sleep(1); }
//                catch(InterruptedException e) {}
//            }
//            step = true;
//            
//            source.removeDisc(myDisc);
//            destination.addDisc(myDisc);
//            display.repaint();  
//            
//            while (step)
//            {
//                try { Thread.sleep(1); }
//                catch(InterruptedException e) {}
//            }
//            step = true;
//            
//            moveHelp(n-1, temp, source, destination);
        }
    }
    
    private void moveDisc(int n, Tower source, Tower destination)
    {
        Disc d = source.getDisc(source.getNumDisc()-1);
        source.removeDisc(d);
        destination.addDisc(d);
    }
    
    public void keyPressed(KeyEvent e)
    {
        System.out.println("yes");
        step = false;
    }
    
    public void step()
    {
        step = false;
    }
    
    public static void main(String[] args)
    {
        Hanoi h = new Hanoi(5);
        h.moveDiscs();
    }
}
