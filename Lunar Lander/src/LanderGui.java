import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * LanderGUI manages the graphical user interface for the Lunar Lander
 * @author Stuart Regis
 *         Revised: Jan. 2007 Mr. Page
 * @version 010407
 */
public class LanderGui
{
    private JFrame frame;
    private JPanel infoPanel;
    
    private JTextField myFuel;
    private JTextField myVelocity;
    private JTextField myAltitude;
    
    private Timer myTimer;
    
    private LunarLander theGame;
    private LunarPicture picture;

    /**
     * Constructor for objects of class LanderGui
     */
    public LanderGui(LunarLander lander)
    {
        theGame = lander;
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
    // create and show this application's GUI
    private void createAndShowGUI()
    {
       
        frame = new JFrame("Lunar Lander");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300,500));
        
        picture = new LunarPicture(theGame);
        frame.add(picture,BorderLayout.CENTER);
        
        JPanel p;
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3,1));
        p = new JPanel();
        myFuel = new JTextField(5);
        myFuel.setEditable(false);
        p.add(new JLabel("Fuel"));
        p.add(myFuel);
        infoPanel.add(p);
        
        p = new JPanel();
        myVelocity = new JTextField(10);
        myVelocity.setEditable(false);
        p.add(new JLabel("Velocity"));
        p.add(myVelocity);
        infoPanel.add(p);
        
        p = new JPanel();
        myAltitude = new JTextField(7);
        myAltitude.setEditable(false);
        p.add(new JLabel("Altitude"));
        p.add(myAltitude);
        infoPanel.add(p); 
        
        
        frame.add(infoPanel, BorderLayout.SOUTH);
        frame.add(createButtonPanel(), BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
        
        addTimer();
        myTimer.start();
        updateInfo();
    }

    private JPanel createButtonPanel()
    {
        // post: creates and returns a JPanel with buttons
        //       for reseting the simulation and for applying
        //       thrust
        JPanel buttons = new JPanel();
        
        JButton reset = new JButton("Reset");
        // add an inner class to handle the action
        reset.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                myTimer.restart();
                theGame.reset();
                updateInfo();
                picture.update();
                
            }
        });
        buttons.add(reset);

        JButton thrust = new JButton("Thrust");
        // add an inner class to handle the action
        thrust.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                
                theGame.thrust();
            }
        });
        buttons.add(thrust);
        
        return buttons;
    }
    
    private void addTimer()
    {
        // creates a timer that calls the lander tick() method
        // and updates the display
        ActionListener updater = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                picture.update();
                theGame.tick();
                updateInfo();
                if(theGame.getAltitude() <= 0) myTimer.stop();
                
            }
        };
        myTimer = new Timer(500, updater);
    }

    private void updateInfo()
    {
        myFuel.setText(theGame.getFuel() + " units");
        myVelocity.setText(theGame.getVelocity() + " meters/second");
        myAltitude.setText(theGame.getAltitude() + " meters");
        
    }
        

}
