import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
/**
*	CAUTION: NO USER SERVICABLE COMPONENTS INSIDE.  TAMPERING WITH THIS CODE
*   WILL VIOLATE YOUR WARRANTY.
*/
public class SketchPad extends JComponent
{
	private Color color = Color.BLACK;
	private java.util.List<Line> lines = new ArrayList<Line>();

	public SketchPad()
	{
		JFrame frame = new JFrame("SketchPad");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setPreferredSize(new Dimension(600, 600));
		frame.getContentPane().add(this);

		frame.pack();
		frame.setVisible(true);
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public void drawLine(double x1, double y1, double x2, double y2)
	{
		lines.add(new Line(color, x1, y1, x2, y2));
		repaint();
	}

	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		int width = getWidth();
		int height = getHeight();
		g2.setPaint(Color.WHITE);
		g2.fill(new Rectangle2D.Double(0, 0, width, height));

		for (int i = 0; i < lines.size(); i++)
		{
			Line line = lines.get(i);
			g2.setPaint(line.getColor());
			g2.draw(new Line2D.Double(line.getX1() * width, (1 - line.getY1()) * height,
										line.getX2() * width, (1 - line.getY2()) * height));
		}
	}

	private class Line
	{
		private Color color;
		private double x1;
		private double y1;
		private double x2;
		private double y2;

		public Line(Color color, double x1, double y1, double x2, double y2)
		{
			this.color = color;
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

		public Color getColor()
		{
			return color;
		}

		public double getX1()
		{
			return x1;
		}

		public double getY1()
		{
			return y1;
		}

		public double getX2()
		{
			return x2;
		}

		public double getY2()
		{
			return y2;
		}
	}
}