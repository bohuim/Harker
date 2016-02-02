
/**
 * ComplexCalc is class that does simple operations on ComplexNumber </br>
 * such as adding, multipying, & getting magnitude of the ComplexNumber from (0,0) </br>
 * All methods are static to avoid creating a ComplexCalc instance
 *
 * @author Dennis Moon
 * @version Feb 2 2013
 * 
 * Revision:
 *      Feb 2 2013 - class created: magnitude, addition, mutliplication added
 */
public class ComplexCalc
{
    
    /**
     * <b>Method: </b>magnitude</br> 
     * <b>Usage: </b>{@code calc.magnitude(a)}</br>
     * -------------------------------</br>
     * Magnitude of a complex number is given by the distance formula 
     * of the complex number and the origin on complex coordinate plane
     * mag = Sqrt(realVal^2 + imaginaryVal^2)
     * 
     * @param a - Complex Number the magnitude needs to calculated
     * @return magnitude of the given Complex Number
     */
    public static double magnitude(ComplexNumber a)
    {
        return Math.sqrt(a.getReal()*a.getReal() + a.getImaginary()*a.getImaginary());
    }
    
    /**
     * <b>Method: </b>add</br> 
     * <b>Usage: </b>{@code calc.add(a,b)}</br>
     * -------------------------------</br>
     * Adds the two given complex number
     * given a = c+di & b = e + fi
     * a+b = (c+e) + (d+f)i
     * 
     * @param a - first complex number
     * @param b - second ccomplex number to add on to first
     * @return sum of the complex numbers
     */
    public static ComplexNumber add(ComplexNumber a, ComplexNumber b)
    {
        double realSum = a.getReal() + b.getReal();
        double imaginarySum = a.getImaginary() + b.getImaginary();
        return new ComplexNumber(realSum, imaginarySum);
    }
    
    /**
     * <b>Method: </b>multiply</br> 
     * <b>Usage: </b>{@code calc.multiply(a,b)}</br>
     * -------------------------------</br>
     * Multiplies the two given complex numbers, 
     * which is the same as multiplying binomial through FOIL
     * (Ar + Ai)(Br + Bi) = ArBr + AiBr + ArBi - ArB = (ArBr-ArB) + (ABr+ArB)i 
     * because i^2 = -1
     * 
     * @param a - first complex number
     * @param b - second complex number to multiply to first
     * @return product of complex numbers as a new ComplexNumber
     */
    public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b)
    {
        double realSum = a.getReal()*b.getReal() - a.getImaginary()*b.getImaginary();
        double imaginarySum = a.getImaginary()*b.getReal() + a.getReal()*b.getImaginary();
        return new ComplexNumber(realSum, imaginarySum);
    }
}
