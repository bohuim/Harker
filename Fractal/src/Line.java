import java.awt.Color;

/**
 * Line is the most basic Curve and provides all methods defined in Curve
 * 
 * @author Dennis Moon
 * @version Jan 29, 2013
 * 
 * Revision:
 *     Jan 28, 2013 - class created: constructor, draw, translate, scale, rotate methods
 *     Jan 29, 2013 - copy method added
 */
public class Line implements Curve
{
    double x1, x2, y1, y2;
    
    /**
     * <b>Constructor: </b>Line</br> 
     * <b>Usage: <b>{@code line = new Line(x1,x2,y1,y2)}</br>
     * -------------------------------</br>
     * Constructs a line segment that knows the coordinates of its two endpoints
     * 
     * @param x1 - x coor. of one endpoint
     * @param x2 - x coor. of the other endpoint
     * @param y1 - y coor. of one endpoint
     * @param y2 - y coor. of the other endpoint
     */
    public Line (double x1, double x2,
                 double y1, double y2)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    
    /**
     * <b>Method: </b></br> 
     * <b>Usage: </b>{@code }</br>
     * -------------------------------</br>
     * Draws a line segment on SketchPad with the endpoints of this line
     * 
     * @param c
     */
    public void draw(SketchPad pad)
    {
        pad.drawLine(x1, y1, x2, y2);
    }

    /**
     * <b>Method: </b>translate</br> 
     * <b>Usage: </b>{@code line.translate(dx,dy)}</br>
     * -------------------------------</br>
     * Adds the given delta x & y values to the current endpoints
     * Scale values of less than 1 srhinks the line
     * 
     * @param dx - delta x of the translation
     * @param dy - delta y of the translation
     */
    public void translate(double dx, double dy)
    {
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
    }
    
    /**
     * <b>Method: </b>scale</br> 
     * <b>Usage: </b>{@code line.scale(sx,sx)}</br>
     * -------------------------------</br>
     * Multiplies the given scale values to current endpoints
     * 
     * @param sx - scale x value
     * @param sy - scale y value
     */
    public void scale(double sx, double sy)
    {
        x1 *= sx;
        x2 *= sx;
        y1 *= sy;
        y2 *= sy;
    }

    /**
     * <b>Method: </b>rotate</br> 
     * <b>Usage: </b>{@code line.rotate(deg)}</br>
     * -------------------------------</br>
     * Rotates the line about the origin using f(x,y) = (x',y') according to
     *      x' = xcos(deg) - ysin(deg)
     *      y' = xsin(deg) + ycos(deg)
     * 
     * @param deg - degrees to rotate this line
     */
    public void rotate(double degrees)
    {
        double rad = (degrees*Math.PI)/180;
        double newX;
        double newY;
        
        newX = x1*Math.cos(rad) - y1*Math.sin(rad);
        newY = x1*Math.sin(rad) + y1*Math.cos(rad);
        x1 = newX;
        y1 = newY;
        
        newX = x2*Math.cos(rad) - y2*Math.sin(rad);
        newY = x2*Math.sin(rad) + y2*Math.cos(rad);
        x2 = newX;
        y2 = newY;
    }
    
    /**
     * <b>Method: </b>copy</br> 
     * <b>Usage: </b>{@code line.copy()}</br>
     * -------------------------------</br>
     * @return new line with the same coordinates
     */
    public Curve copy()
    {
        return new Line(x1,x2,y1,y2);
    }
}
