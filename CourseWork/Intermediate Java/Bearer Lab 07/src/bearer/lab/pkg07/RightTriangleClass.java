/*
 * Driver Class for BearerLab07.  
 */
package bearer.lab.pkg07;

/**
 * Driver method for BearerLab07.  Initializes a right triangle and displays its
 * properties.
 * 
 * @author Randyll Bearer
 */
public class RightTriangleClass {
    public static void main(String [ ] args){
        BearerLab07 rightTriangle = new BearerLab07();
        
        rightTriangle.setLegA(5);
        rightTriangle.setLegB(10);
        
        System.out.println("Length of Leg A = " + rightTriangle.getLegA());
        System.out.println("Length of Leg B = " + rightTriangle.getLegB());
        System.out.println("Length of Hypotenuse = " + rightTriangle.getHypotenuse());
        System.out.println("Length of Perimeter = " + rightTriangle.getPerimeter());
        System.out.println("Total Area = " + rightTriangle.getArea());
}
}
