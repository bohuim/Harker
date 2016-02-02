import java.awt.Color;


/**
 * Mandelbrot is fractalGenerator that gives the Mandelbrot curve </br></br>
 * 
 * Mandelbrot Set:</br>
 * - The set of all complex numbers C where the recursive definition z = z^2 + C does not escape to infinity</br>
 *      z of 0 = C</br>
 * - Because it is not actually possible to recursively calculate to infinity, </br>
 *      if magnitude of z > 2 at any step, it is determined to escape to infinity</br>
 *      if magnitude of z does not exceed 2 in the given number of iterations, it is determined to be in the set</br></br>
 * 
 * Because the curve is plotted on a complex plane & needs all 4 quadrants,</br>
 * convert() converts the x & y values on the screen to a virtual complex plane
 *
 * @author Dennis Moon
 * @version Feb 3, 2013
 * 
 * Revision:
 *      Feb 3, 2013
 */
public class Mandelbrot
{
    
    /**
     * <b>Method: </b>draw</br> 
     * <b>Usage: </b>{@code m.draw(n, pad)}</br>
     * -------------------------------</br>
     * Given n, the accuracy to which the Mandelbrot should be calculated to </br>
     *      - the number of iterations is determined by 2^n </br>
     *      - 0 is no accuracy at all, 6 produced good enough </br></br>
     *      
     * Every point in a .001 interval on SketchPad, starting from 0 is converted to the counterpart Complex Coordinate point</br></br>
     * 
     * Each of these points are run through iterate to determine whether the point is in the set</br>
     *      - more iteration means less points in the set</br></br>
     *      
     * The points are colored according to the number of iterations taken to escape beyond 2</br>
     *      - Black: seed value does not escape </br>
     *      - Shades of blue: determined by the 
     * 
     * @param n - accuracy to which set should be painted
     * @param pad - SketchPad to draw on
     */
    public void draw(int n, SketchPad pad)
    {     
        int iterations = ((int) Math.pow(2, n))-1;
        FancyCurve set = new FancyCurve();
        
        for (double x=0; x<=1; x+=.0015)
            for (double y=0; y<=1; y+=.0015)
            {
                ComplexNumber c = new ComplexNumber(convert(x), convert(y));
                int it = iterate(c, iterations);
                if (it>=0)
                {
                    int val = (int) it*10;
                    if (val>255) val = 255;
                    
                    int blue = 170 + (int) it*10;
                    if (blue>255) blue = 255;
                    
                    pad.setColor(new Color(val, val ,blue));
                }
                else
                    pad.setColor(Color.BLACK);
                pad.drawLine(x, y, x, y);
            }
        
        set.draw(pad);
    }
    
    /**
     * <b>Method: </b>convert</br> 
     * <b>Usage: </b>{@code covert(x)}</br>
     * -------------------------------</br>
     * Because SketchPad goes from [0,1] x [0,1] </br>
     * All points need to be times by 4 & subtracted 2 </br>
     *      Pad   -> Complex plane </br>
     *      (0,0) -> (-2, -2i) </br>
     *      (0,1) -> (-2, 2i)</br>
     *      (1,1) -> (2, 2i) </br>
     *      (1,0) -> (2,-2i) </br>
     * 
     * @param p - x or y coordinate point on the screen
     * @return the counterpart on a complex plane from [-2,2] x [-2,2]
     */
    private double convert(double p)
    {
        return p*4 - 2;
    }
    
    /**
     * <b>Method: </b>iterate</br> 
     * <b>Usage: </b>{@code iterate(c, n)}</br>
     * -------------------------------</br>
     * iterate is is the recursive definition of the Mandelbrot set </br>
     * using the given c as the seed value, uses the Mandelbrot recursion z = z^2 + c </br>
     *      - if at anytime, magnitude of z goes beyond 2, it is not in the Mandelbrot set </br>
     *      - if z does not grow above 2 within the given number of iterations, it is considered to be in the Mandelbrot set </br>
     * iterate returns the number of iterations before the seed value escapes 2 to color the seed point differently 
     * 
     * @param c - the seed complex number to check if its in the Mandelbrot set
     * @param n - number of iterations the seed value should be calculated to
     * @return number of iterations before seed escapes beyond magnitude 2 or -1 if it does not within given n
     */
    private int iterate(ComplexNumber c, int n)
    {
        ComplexNumber z = c;
        int iteration = 0;
        if (ComplexCalc.magnitude(z)>2)
            return iteration;
        while(iteration<n)
        {
            ComplexNumber z2 = ComplexCalc.multiply(z, z);
            z = ComplexCalc.add(z2, c);
            if (ComplexCalc.magnitude(z)>2)
                return iteration;
            iteration++;
        }
        return -1;
    }
}
