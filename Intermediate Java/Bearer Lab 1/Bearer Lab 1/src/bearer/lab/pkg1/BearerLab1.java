package bearer.lab.pkg1;

/**
 * First Lab Assignment CS 0401
 * @author rlb97    
 */
public class BearerLab1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String name = "Randyll Bearer";
        double sideA = 7;
        double sideB = 9;
        double sideC = 14;
        
        double semiperimeter1 = 0.5 * (sideA + sideB + sideC);
        System.out.println("The semiperimeter of the triangle is " + semiperimeter1);
        
        double semiperimeter2 = (sideA + sideB + sideC) / 2;
        System.out.println("The semiperimeter of the triangle is " + semiperimeter2);
        
        System.out.println (name + ", the program is now ending.");
    }
    
}
