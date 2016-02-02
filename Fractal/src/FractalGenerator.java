
/**
 * FractalGenerator defines steps to create a fractal image on SketchPad
 * 
 * @author Dennis
 */
public interface FractalGenerator
{
    /**
     * <b>Method: </b>step0</br> 
     * <b>Usage: </b>{@code gen.step0()}</br>
     * -------------------------------</br>
     * @return new Curve representing step0 of the fractal
     */
    Curve step0();
    
    /**
     * <b>Method: </b>transform</br> 
     * <b>Usage: </b>{@code gen.tranform(curve)}</br>
     * -------------------------------</br>
     * given a curve representing step n of the fractal, </br>
     * uses that curve to return a new curve representing step n+1 of the fractal.
     * 
     * @param previous - nth step of the Curve
     * @return next - (n+1)th step of the Curve
     */
    Curve transform(Curve curve);
}
