/**
 * Curve is an interface that defines a drawing on a SketchPad
 * All Curves must be provide draw, translate, scale, rotate, and copy methods
 * All transformations are done about the origin
 * 
 * @author Dennis Moon
 * @version Jan 28, 2013
 * 
 * Revision:
 *     Jan 28, 2013 - interface created
 */
public interface Curve 
{
    /**
     * <b>Method: </b>draw</br> 
     * <b>Usage: </b>{@code curve.draw}</br>
     * -------------------------------</br>
     * draws this curve/curves on the given pad
     * 
     * @param pad - SketchPad to draw on
     */
    public void draw(SketchPad pad);
    
    /**
     * <b>Method: </b>translate</br> 
     * <b>Usage: </b>{@code curve.translate(dx,dy)}</br>
     * -------------------------------</br>
     * Translates all curve(s) according to given delta values
     * 
     * @param dx - delta x of the translation
     * @param dy - delta y of the translation
     */
    void translate(double dx, double dy);
    
    /**
     * <b>Method: </b>scale</br> 
     * <b>Usage: </b>{@code curve.scale(sx,sy)}</br>
     * -------------------------------</br>
     * Scales all curve(s) according to given scale values relative to the origin
     * 
     * @param sx - scale x value
     * @param sy - scale y value
     */
    void scale(double sx, double sy);
    
    /**
     * <b>Method: </b>scale</br> 
     * <b>Usage: </b>{@code curve.scale(deg)}</br>
     * -------------------------------</br>
     * Rotates all curve(s) according to given degrees counter-clockwise
     * 
     * @param deg - degrees to rotate this line
     */
    void rotate(double degrees);
    
    /**
     * <b>Method: </b>copy</br> 
     * <b>Usage: </b>{@code copy = curve.copy}</br>
     * -------------------------------</br>
     * @return copy of this curve
     */
    Curve copy();
}
