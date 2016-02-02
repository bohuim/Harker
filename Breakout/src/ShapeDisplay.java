import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

/**
 * A simple class capable of displaying a number of colored rectangles and circles
 */
public class ShapeDisplay extends JComponent
{
	private JFrame frame;
	private Color color = Color.black;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();

	/**
	 * Constructor: ShapeDisplay()
	 * Usage: ShapeDisplay display = new ShapeDisplay()
	 * -------------------------------
	 * makes a new visible JFrame of 600x600
	 */
	public ShapeDisplay()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(600,600);
		frame.add(this);
		
		//frame.setLayout(null);
		
//		setPreferredSize(new Dimension(600, 600));
//		frame.getContentPane().add(this);
//		frame.pack();
		
		frame.setVisible(true);
	}

	/**
	 * Method: setTitle(String)
	 * Usage: display.setTitle(title)
	 * -----------------------------
	 * @param title - of JFrame
	 */
	public void setTitle(String title)
	{
		frame.setTitle(title);
	}

	/**
	 * Method: setBackgroundColor(Color)
	 * Usage: display.setBackgroundColor(BGColor)
	 * -----------------------------
	 * @param color - of JFrame background
	 */
	public void setBackgroundColor(Color color)
	{
		this.color = color;
	}

	/**
	 * Method: add(Shape)
	 * Usage: display.add(Circle)
	 * -----------------------------
	 * Adds the given shape to the display & repaints
	 * @param shape - to be added
	 */
	public void add(Shape shape)
	{
		shapes.add(shape);
		repaint();
	}

	/**
	 * Method: shapes()
	 * Usage: Iterator<Shape> itr = display.shapes()
	 * ----------------------------
	 * @return iterator for ArrayList of shapes in ShapeDisplay
	 */
	public Iterator<Shape> shapes()
	{
		return shapes.iterator();
	}

	public void addKeyListener(KeyListener listener)
	{
		frame.addKeyListener(listener);
	}

	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(color);
		g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
		for (int i = 0; i < shapes.size(); i++)
		{
			Shape shape = shapes.get(i);
			g2.setColor(shape.getColor());

			double x = convertX(shape.getX());
			double y = convertY(shape.getY());
			double width = convertX(shape.getWidth());
			double height = convertY(shape.getHeight());

			if (shape.isRound())
				g2.fill(new Ellipse2D.Double(x, y, width, height));
			else
				g2.fill(new Rectangle2D.Double(x, y, width, height));
		}
	}

	private double convertX(double x)
	{
		return x / 100.0 * getWidth();
	}

	private double convertY(double y)
	{
		return y /100.0 * getHeight();
	}
}