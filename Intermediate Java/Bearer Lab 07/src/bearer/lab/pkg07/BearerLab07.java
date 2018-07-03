package bearer.lab.pkg07;

/**
 * Represents a Right Triangle object, capable of displaying Length of LegA, LegB,
 * Total Perimeter, Total Area, and Hypotenuse.
 * 
 * @author Randyll Bearer
 */

public class BearerLab07 {
    private double legA = 0 ;
    private double legB = 0 ;       // Perhaps hypotenuse should be field, use it more than once
    
    /**
     * sets the length of legA
     * @param lengthA: New value for legA
     * @throws IllegalArgumentException; Thrown when lengthA is less than or equal to 0
    */
    public void setLegA(double lengthA){
        if(lengthA <= 0 ){
            IllegalArgumentException iae = new IllegalArgumentException("The length of Leg A must be positive");
            throw iae;
        }
        legA = lengthA;
    }
    
    /**
     * Gets the length for legA
     * @return legA: Length value for legA
     */
    public double getLegA(){
        return legA;
    }
    
    /**
     * Sets the length of legB
     * @param lengthB: New value for legB
     * @throws IllegalArgumentException: Thrown when lengthB is less than or equal to 0
     */
    public void setLegB(double lengthB){
        if(lengthB <= 0 ){
            IllegalArgumentException iae = new IllegalArgumentException("The length of Leg B must be positive");
            throw iae;
        }
        legB = lengthB;        
    }
    
    /**
     * gets the length for legB
     * @return legB: Length Value for legB
     */
    public double getLegB(){
        return legB;
    }
    
    /**
     * Calculates and returns the area of the right triangle
     * Area = (legA * lebB)/2
     * @return area: The area of the right triangle formed by legA and legB
     */
    public double getArea(){        // Include formula in comments?
        double area = (legA*legB)/2;
        return area;
    }
    
    /**
     * Calculates and returns the length of the Hypotenuse using the values for legA and legB
     * Hypotenuse = sqrt(legA^2 + legB^2)
     * @return hypotenuse: The length of the triangle's hypotenuse formed by legA and legB
     */
    public double getHypotenuse(){
        double hypotenuse = Math.sqrt((legA*legA) + (legB*legB));
        return hypotenuse;
    }
    
    /**
     * Calculates and returns the perimeter of the right triangle formed by legA, legB, and the hypotenuse
     * Perimeter = legA + legB + hypotenuse
     * @return perimeter: The total length of the right triangle's perimeter
     */
    public double getPerimeter(){
        double perimeter = legA + legB + (Math.sqrt((legA*legB) + (legB*legB)));
        return perimeter;
    }
}
