import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Gui of the piano project
 * Has the 88 piano keys that are buttons
 * but can also respond to keypress by user
 * 
 * @author Dennis Moon
 */
public class Keyboard implements KeyListener
{
    private JFrame frame;
    private JLabel display;
    private JLayeredPane keyboard;
    private JButton[] keys;
    private int wLocation;
    private int bLocation;
    private int blackOrWhite;
    
    private Instrument piano;
    
    /**
     * Constructor: Piano(instrument)
     * Usage: Keyboard keyboard = new Keyboard();
     * -------------------------------------------
     * takes in an instrument and puts it into piano
     */
    public Keyboard(Instrument instr)
    {
        piano = instr;
        blackOrWhite = 10;
        wLocation = 0;
        bLocation = 16;
        
        // schedule a job for the event-dispatching thread to
        // create and show this GUI
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
        
        // wait until the display has been drawn
        try
        {
            while(frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Method: createAndShowGUI()
     * Usage: createAndShowGUI();
     * -----------------------------
     */
    private void createAndShowGUI()
    {
        frame = new JFrame("Piano");
        display = new JLabel();
        keyboard = new JLayeredPane();
        keys = new JButton[88];
        
        frame.setLayout(null);
        keyboard.setLayout(null);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1680,250);
        frame.setVisible(true);
        frame.setFocusable(true);
        
        frame.add(display);
        display.setSize(100,50);
        display.setLocation(790, 0);
        
        frame.add(keyboard);
        keyboard.setSize((new Dimension(1680,200)));
        keyboard.setLocation(0,50);
        
        frame.addKeyListener(this);
        
        createAndShowKeys();
    }
    
    private void createAndShowKeys()
    {
        keys = new JButton[88];
        
        for (int i=0; i<keys.length; i++)
        {
            if (blackOrWhite<6)
            {
                if (blackOrWhite%2!=0)
                    makeWhiteKey(i);
                else
                    makeBlackKey(i);
            }
            else 
            {
                if (blackOrWhite%2==0)
                    makeWhiteKey(i);
                else
                    makeBlackKey(i);
            }
            
            blackOrWhite++;
            if (blackOrWhite>12)
                blackOrWhite=1;
        }
    }
    
    private void makeWhiteKey(final int i)
    {
        keys[i] = new JButton();
        keys[i].setBackground(Color.WHITE);
        keys[i].setLocation(wLocation, 0);
        keys[i].setSize(32,160);
        
        keys[i].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                piano.playNote(i);
                display.setText(piano.convertToNoteName(i));
            }
        });
        
        keyboard.add(keys[i], 0, -1);
        keys[i].setFocusable(false);
        
        wLocation += 32;
    }
    
    private void makeBlackKey(final int i)
    {
        keys[i] = new JButton();
        keys[i].setBackground(Color.BLACK);
        keys[i].setLocation(bLocation, 0);
        keys[i].setSize(28,110);
        
        keys[i].addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                piano.playNote(i);
                display.setText(piano.convertToNoteName(i));
            }
        });
        
        keyboard.add(keys[i], 1, -1);
        keys[i].setFocusable(false);
        
        if(blackOrWhite==4 || blackOrWhite==11)
            bLocation += 64;
        else
            bLocation += 32;
    }
    
    public void keyPressed(KeyEvent e)
    {
        char c = e.getKeyChar();
        String str = Character.toString(c);
        piano.playNote(str);
        display.setText(piano.convertToNoteName(str));
    }

    public void keyReleased(KeyEvent e){}
    
    public void keyTyped(KeyEvent e){}
}
