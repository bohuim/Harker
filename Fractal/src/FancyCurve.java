import java.util.ArrayList;

/**
 * FancyCurve is a collection of Curves, including Lines and other FancyCruves </br>
 * All transformations done on FancyCurve is applied to all Curves in the collection
 * 
 * @author Dennis Moon
 * @version Jan 28, 2013
 * 
 * Revision:
 *     Jan 28, 2013 - class created
 *     Jan 29, 2013 - copy() added
 */
public class FancyCurve implements Curve
{
    private ArrayList<Curve> curves;
    
    /**
     * <b>Constructor: </b>FancyCurve</br> 
     * <b>Usage: <b>{@code fancy = new FancyCurve()}</br>
     * -------------------------------</br>
     * creates a new ArrayList to hold curves
     */
    public FancyCurve()
    {
        curves = new ArrayList<Curve>();
    }
    
    /**
     * <b>Method: </b>add</br> 
     * <b>Usage: </b>{@code line.add(curve)}</br>
     * -------------------------------</br>
     * adds the curve to FancyCurve's ArrayList
     * 
     * @param c - new curve 
     */
    public void add(Curve c)
    {
        curves.add(c);
    }

    /**
     * <b>Method: </b>draw</br> 
     * <b>Usage: </b>{@code fancy.draw(pad)}</br>
     * -------------------------------</br>
     * Draws all curves on the given pad
     * 
     * @param pad - SketchPad to draw all curves on
     */
    public void draw(SketchPad pad)
    {
        for (Curve c : curves)
            c.draw(pad);
    }

    /**
     * <b>Method: </b>translate</br> 
     * <b>Usage: </b>{@code fancy.translate(dx,dy)}</br>
     * -------------------------------</br>
     * Translates all Curves according to the given delta values
     * 
     * @param dx - delta x of the translation
     * @param dy - delta y of the translation
     */
    public void translate(double dx, double dy)
    {
        for (Curve c : curves)
            c.translate(dx, dy);
    }

    /**
     * <b>Method: </b>scale</br> 
     * <b>Usage: </b>{@code fancy.scale(sx,sy)}</br>
     * -------------------------------</br>
     * Scales all curves according to the given scale values relative to the origin
     * 
     * @param sx - scale x value
     * @param sy - scale y value
     */
    public void scale(double sx, double sy)
    {
        for (Curve c : curves)
            c.scale(sx, sy);
    }

    /**
     * <b>Method: </b>rotate</br> 
     * <b>Usage: </b>{@code fancy.rotate(deg)}</br>
     * -------------------------------</br>
     * Rotates all curves counter-clockwise to the given deg about the origin
     * 
     * @param deg - degrees to rotate this line
     */
    public void rotate(double degrees)
    {
        for (Curve c : curves)
            c.rotate(degrees);
    }
    
    /**
     * <b>Method: </b>copy</br> 
     * <b>Usage: </b>{@code fancy.copy}</br>
     * -------------------------------</br>
     * @return copy - a new FancyCurve with copies of all curves
     */
    public Curve copy()
    {
        FancyCurve copy = new FancyCurve();
        for (Curve c : curves)
            copy.add(c.copy());
        return copy;
    }
}
