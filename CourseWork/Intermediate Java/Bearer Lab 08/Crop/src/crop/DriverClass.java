package crop;

/**
 * Main Driver Class for Lab 08, following Lab Description.
 * - Creates 3 Corn Objects using 3 separate constructors
 * - Compares the name of Crop Object1 with the other Crop Objects. 
 * 
 * @author Randyll Bearer
 */
public class DriverClass {
    
    public static void main(String[] args){     // Main Method
        Crop corn = new Crop("Corn", 5000, .25);
        Crop wheat = new Crop();
        
        Crop corn2 = new Crop(corn);
        corn2.setYield(5500);
        corn2.setPrice(.25);
        
        System.out.println("\r\nObject1: Corn");
        System.out.println(corn.toString());
        System.out.println("\r\nObject2: Default");
        System.out.println(wheat.toString());
        System.out.println("\r\nObject3: Corn2");
        System.out.println(corn2.toString());
        
        System.out.println("\r\nComparing Object1 and Object2");
        if(corn.equals(wheat)){
            System.out.println("The first and second crops are equal.");
        }else{
            System.out.println("The first and second crops are NOT equal.");
        }
        
        System.out.println("\r\nComparing Object1 and Object3");
        if(corn.equals(corn2)){
            System.out.println("The first and third crops are equal.");
        }else{
            System.out.println("The first and third crops are NOT equal.");
        }
    }
    
    
}
