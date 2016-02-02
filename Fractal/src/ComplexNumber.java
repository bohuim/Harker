
/**
 * ComplexNumber is a representation of a complex number A + Bi </br>
 * where A is real & Bi is the imaginary part </br></br>
 * 
 * The A & B are stored as instance variables </br>
 * The real & imaginary numbers can be returned, but not changed
 *
 * @author Dennis
 * @version Feb 3, 2013
 * 
 * Revision
 *      Feb 3, 2013 - class created: instance fields, get methods added
 */
public class ComplexNumber
{
    private double real;
    private double imaginary;
    
    /**
     * <b>Constructor: </b>ComplexNumber</br> 
     * <b>Usage: <b>{@code ComplexNumber c = new ComplexNumber(A, B)}</br>
     * -------------------------------</br>
     * given real A & imaginary B, represents A + Bi
     * 
     * @param a - real number of this complex number
     * @param b - imaginary part of this complex number
     */
    public ComplexNumber(double a, double b)
    {
        real = a;
        imaginary = b;
    }
    
    /**
     * <b>Method: </b>getReal</br> 
     * <b>Usage: </b>{@code c.getReal()}</br>
     * -------------------------------</br>
     * @return real number part of this complex number
     */
    public double getReal()
    {
        return real;
    }
    
    /**
     * <b>Method: </b>getImaginary</br> 
     * <b>Usage: </b>{@code c.getImaginary()}</br>
     * -------------------------------</br>
     * @return imaginary part of this complex number
     */
    public double getImaginary()
    {
        return imaginary;
    }
    
    /**
     * <b>Method: </b>toString</br> 
     * <b>Usage: </b>{@code c.toString()}</br>
     * -------------------------------</br>
     * @return string representation of this complex number as (a + bi)
     */
    public String toString()
    {
        return ""+ real + "+" + imaginary + "i";
    }
}
