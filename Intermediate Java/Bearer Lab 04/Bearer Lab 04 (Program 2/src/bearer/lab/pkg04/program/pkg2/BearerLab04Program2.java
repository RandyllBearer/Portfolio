/*
 * 
 */

package bearer.lab.pkg04.program.pkg2;
/**
 * The purpose of this program is to determine whether a one way street in New York
 * City is either Westbound or Eastbound using its street number.  If odd, the
 * street is Westbound, if even, the street is Eastbound.  The street number is to
 * be inputted by the user in an infinite loop until it is asked to be ended.
 *
 * @author rlb97
 */
import java.util.Scanner;

public class BearerLab04Program2 {
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        Scanner keyboard = new Scanner(System.in);
        int userStreet = 0;
        String userChoice = "";
        boolean wanttoend = false;      // Does the user want to exit the program? If true, exits all loops
        
        System.out.println("This program will tell you if a one-way street in New York City is either Eastbound or Westbound.");
        
        while(wanttoend == false){
            System.out.print("Please enter a one-way street number: ");
            while(userStreet < 1 || userStreet > 155){
                try{
                    userStreet = keyboard.nextInt();
                    if (userStreet < 1 || userStreet > 155){
                        System.out.print("Please enter a valid, positive, integer street number between 1-155: ");
                    }
                }catch(java.util.InputMismatchException ime){
                    System.out.print("Please enter a valid, positive, integer street number between 1-155: ");
                    keyboard.nextLine();
                }
            }
            
            if(userStreet % 2 == 0){
            System.out.println("\nIt is Eastbound.\n");
            }
            else if(userStreet %2 != 0){
            System.out.println("\nIt is Westbound.\n");
            }
            
            while(wanttoend == false){
                System.out.print("Would you like to determine the direction of another street? [Y/N] ");
                userChoice = keyboard.next();
                if (userChoice.equalsIgnoreCase("y") || userChoice.equalsIgnoreCase("yes")){
                    System.out.print(""); 
                    userStreet = 0;        // Resets the loop for next iteration
                    break;
                }else if(userChoice.equalsIgnoreCase("n") || userChoice.equalsIgnoreCase("no")){
                    System.out.println("Goodbye!");
                    wanttoend = true;
                }else{
                    System.out.println("Please enter a 'Yes' or 'No' answer.");
                }
            }   
        }
    }
}

