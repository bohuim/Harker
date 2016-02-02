import java.awt.*;
import java.util.Timer;
import java.awt.event.*;
import java.util.TimerTask;

import javax.swing.*;

/**
 * Modified version of BlockDisplay that uses Graphics2D 
 * instead of colored panels
 * 
 * @author Dennis Moon
 */
public class TetrisDisplay extends KeyAdapter
{
    private JFrame frame;
    private ArrowListener listener;
    
    private JPanel mainPanel;
    private MyBoundedGrid<Block> board;
    
    private JPanel holdPanel;
    private MyBoundedGrid<Block> holdBoard;
    
    private MyBoundedGrid<Block>[] nextBoard;
    private JPanel mainNextPanel;
    private JPanel otherNextPanels;
    private JPanel[] nextPanels;
    private JLabel nextLabel;
    
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JLabel linesClearedLabel;
    
    private int score;
    private boolean showingMessage;
    
    public TetrisDisplay(MyBoundedGrid<Block> b, MyBoundedGrid<Block> holdB, MyBoundedGrid<Block>[] nextB)
    {
        board = b;
        holdBoard = holdB;
        nextBoard = nextB;
        score = 0;
        showingMessage = false;
        
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });

        //Wait until display has been drawn
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Method: createAndShowGUI()
     * Usage: createAndShowGUI()
     * ---------------------------
     * creates a frame, sets and adds all necessary components
     * calls various helper methods to add hold & next brids
     */
    private void createAndShowGUI()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setPreferredSize(new Dimension(415,485));
        frame.setResizable(false);
        frame.setLayout(null);
        
        //main
        mainPanel = new JPanel();
        mainPanel.setSize(201, 406);
        mainPanel.setLocation(100,40+10);
        GridDisplay main = new GridDisplay(board);
        main.setPreferredSize(mainPanel.getSize());
        mainPanel.add(main);
        frame.add(mainPanel);
        
        //hold
        addHoldGrid();
        //next
        addNextGrid();
        
        createAndAddScore();
        createAndAddLinesCleared();
        
        frame.repaint();
        frame.pack();
        frame.setVisible(true);
        frame.repaint();
    }
    
    /**
     * Method: addHoldGrid()
     * Usage: addHoldGrid()
     * ------------------------
     * makes and adds the hold Grid and its border and label
     */
    private void addHoldGrid()
    {
        holdPanel = new JPanel();
        holdPanel.setLayout(null);
        holdPanel.setLocation(18,85+10);
        holdPanel.setSize(18*4+1,18*4+5);
        SubGridDisplay hold = new SubGridDisplay(holdBoard, false);
        hold.setSize(holdPanel.getSize());
        holdPanel.add(hold);
        frame.add(holdPanel);
        
        JLabel holdLabel = new JLabel();
        holdLabel.setFont(new Font("MV Boli", Font.PLAIN, 24));
        holdLabel.setText("HOLD");
        holdLabel.setForeground(Color.MAGENTA);
        holdLabel.setLocation(20,50+10);
        holdLabel.setSize(18*4,35);
        frame.add(holdLabel);
    }
    
    /**
     * Method: addNextGrid()
     * Usage: addNextGrid()
     * ----------------------------
     * makes and adds the nextGrids and its border and label
     */
    private void addNextGrid()
    {
        mainNextPanel = new JPanel();
        mainNextPanel.setLayout(null);
        mainNextPanel.setLocation(308,85+10);
        mainNextPanel.setSize(18*4+1,18*4+5);
        mainNextPanel.setBackground(Color.GREEN);  
        SubGridDisplay next = new SubGridDisplay(nextBoard[0], false);
        next.setSize(mainNextPanel.getSize());
        mainNextPanel.add(next);
        frame.add(mainNextPanel);
        
        otherNextPanels = new JPanel();
        otherNextPanels.setBackground(Color.BLACK);
        otherNextPanels.setSize(56,48*4+25);
        otherNextPanels.setLocation(317,165+10);
        nextPanels = new JPanel[5];
        for (int i=1; i<nextPanels.length; i++)
        {
            nextPanels[i] = new JPanel();
            nextPanels[i].setSize(48,48);
            nextPanels[i].setLocation(4,(i-1)*48);
            next = new SubGridDisplay(nextBoard[i], true);
            next.setPreferredSize(nextPanels[i].getSize());
            otherNextPanels.add(next);
        }
        frame.add(otherNextPanels);
        
        nextLabel = new JLabel();
        nextLabel.setFont(new Font("MV Boli", Font.PLAIN, 24));
        nextLabel.setText("NEXT");
        nextLabel.setForeground(Color.MAGENTA);
        nextLabel.setLocation(310,50+10);
        nextLabel.setSize(18*4,35);
        frame.add(nextLabel);
    }
    
    /**
     * Method: createAndAddScore()
     * Usage: createAndAddScore()
     * ------------------------------
     * makes and adds the score and level gui and its border and label
     */
    private void createAndAddScore()
    {
        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("MV Boli", Font.BOLD, 32));
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        scoreLabel.setForeground(Color.MAGENTA);
        scoreLabel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.ORANGE), "SCORE"));
        scoreLabel.setSize(400,50);//180,50);
        scoreLabel.setLocation(0,0);//122,0);
        
        scoreLabel.setText(""+0);
        frame.add(scoreLabel);
        
        levelLabel = new JLabel();
        levelLabel.setFont(new Font("MV Boli", Font.BOLD, 32));
        levelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        levelLabel.setForeground(Color.MAGENTA);
        levelLabel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.ORANGE), "LEVEL"));
        levelLabel.setSize(90,50);
        levelLabel.setLocation(5,200);
        
        levelLabel.setText("" + 1);
        frame.add(levelLabel);
    }
    
    /**
     * Method: createAndAddlinesClearedLabel()
     * Usage: createAndAddlinesClearedLabel()
     * -----------------------------------
     * makes and adds the linesClearedLabel gui and its border and label
     */
    private void createAndAddLinesCleared()
    {
        linesClearedLabel = new JLabel();
        linesClearedLabel.setFont(new Font("MV Boli", Font.BOLD, 32));
        linesClearedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        linesClearedLabel.setForeground(Color.MAGENTA);
        linesClearedLabel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.ORANGE), "Cleared"));
        linesClearedLabel.setSize(90,50);
        linesClearedLabel.setLocation(5,280);
        
        linesClearedLabel.setText("" + 0);
        frame.add(linesClearedLabel);
    }
    
    /**
     * Method: setTitle()
     * Usage: called by Tetris class
     * ------------------------
     * @param title - new title of frame
     */
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }
    
    
    public void repaint()
    {
        frame.repaint();
    }
    
    /**
     * Method: setLevel(level)
     * Usage: display.setLevel(level);
     * -------------------------------
     * @param lvl - level to be shown
     */
    public void setLevel(int lvl)
    {
        levelLabel.setText("" + lvl);
    }
    
    /**
     * Method: setScore(int)
     * Usage: display.setScore(1000);
     * -------------------------------
     * sets the score
     * @param score - the new score
     */
    public void setScore(int scr)
    {
        score = scr;
        if (!showingMessage)
            scoreLabel.setText(""+score);
    }
    
    /**
     * Method: setMessage()
     * Usage: called when line(s) is cleared
     * ------------------------------
     * shows the message for 1 sec & redisplays the score
     * @param mssg - to be displayed
     */
    public void setMessage(String mssg)
    {
        scoreLabel.setText(mssg);
        showingMessage = true;
        
        Timer temp = new Timer();
        temp.schedule(new TimerTask(){
            public void run() 
            {
                showingMessage = false;
                scoreLabel.setText("" + score);
            }    
        }, 3000);
    }
    
    /**
     * Method: setlinesClearedLabel()
     * Usage: setlinesClearedLabel()
     * --------------------------
     * sets the linesClearedLabel label
     * @param linesClearedLabel - the new linesClearedLabel
     */
    public void setLinesCleared(int lines)
    {
        linesClearedLabel.setText("" + lines);
    }
    
    /**
     * Method: setArrowListener(ArrowListner)
     * Usage: BlockDisplay.setArrowListener(Tetris)
     * ---------------------------------------------
     * switches the arrowListner of to the given one
     * @param listener - the given arrowListener
     */
    public void setArrowListener(ArrowListener listener)
    {
        this.listener = listener;
    }
    
    /**
     * Method: keyPressed()
     * Usage: 
     * ----------------------------
     * when a key is pressed on an element that is focused and has a keyListener
     * keyPressed calls the correct method according to the pressed key
     */
    public void keyPressed(KeyEvent e)
    {
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT)
            listener.leftPressed();
        else if (code == KeyEvent.VK_RIGHT)
            listener.rightPressed();
        else if (code == KeyEvent.VK_DOWN)
            listener.downPressed();
        else if (code == KeyEvent.VK_UP)
            listener.upPressed();
        else if (code == KeyEvent.VK_SPACE)
            listener.spacePressed();
        else if (code == KeyEvent.VK_SHIFT)
            listener.shiftPressed();
    }
}
