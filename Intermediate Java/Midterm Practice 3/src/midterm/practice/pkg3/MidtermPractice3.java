/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midterm.practice.pkg3;

/**
 *
 * @author Randyll Bearer
 */
import java.util.Scanner;
import java.util.InputMismatchException;
public class MidtermPractice3 {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner keyboard = new Scanner(System.in);
        
        try{
            System.out.print("Hello! Please enter a number in the range of 1 - 10: ");
            int userNumber = keyboard.nextInt();
            if (userNumber < 1 || userNumber > 10){
                System.out.println("Sorry but that number was not in the range of 1 - 10.");
            }else if (userNumber == 1){
                System.out.println("|");
            }else if ( userNumber == 2){
                System.out.println("||");
            }else if (userNumber == 3){
                System.out.println("|||");
            }else if (userNumber == 4){
                System.out.println("|V");
            }else if (userNumber == 5){
                System.out.println("V");
            }else if (userNumber == 6){
                System.out.println("V|");
            }else if (userNumber == 7){
                System.out.println("V||");
            }else if (userNumber == 8){
                System.out.println("V|||");
            }else if (userNumber == 9){
                System.out.println("|X");
            }else if (userNumber == 10){
                System.out.println("X");
            }
            
        }catch(InputMismatchException ime){
            System.out.println("Sorry but you must enter a number");
        }
    }
    
}
