/*
 *
 */

package bearer.lab.pkg04.program.pkg3;

/**
 * The purpose of this program is to find the floor of a logarithmic function
 * through repeated division.
 * The user is prompted to input a positive, integer base (b) and a positive, integer
 * value (x).  The program outputs the amount (y) of times the program can be divided.
 *
 * @author rlb97
 */
import java.util.Scanner;
        
public class BearerLab04Program3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int userBase = 0;     // B
        int userValue = 0;    // X
        int userExponent = 0; // Y
        
        while (userBase <= 1){
            try{
                System.out.print("What base (b) logarithm are you working with? ");
                userBase = keyboard.nextInt();
                if (userBase <= 1){
                    System.out.println("Please enter a valid positive integer greater than one.");}
            }catch(java.util.InputMismatchException ime){
                System.out.println("Please input a valid positive integer.");
                keyboard.nextLine();
            }
        }
        
        while (userValue <=1){
            try{
                System.out.print("What value (x) of the logarithm are you looking for? ");
                userValue = keyboard.nextInt();
                if (userValue <= 1){
                    System.out.println("Please input a valid positive integer.");
                }
            }catch(java.util.InputMismatchException ime){
                System.out.println("Please input a valid positive integer.");
                keyboard.nextLine();
            }
        }
        
        while (userValue >= userBase){
            userValue = userValue / userBase;
            userExponent += 1;
        }
        
        System.out.println("The floor of the logarithm b of x is " + userExponent + ".");
    }
    
}
