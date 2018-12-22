/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bearer.lab.pkg07;

/**
 *
 * @author Randyll Bearer
 */
public class DriverClass {
    
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
