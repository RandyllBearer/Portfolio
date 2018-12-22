/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midterm.practice.pkg2;

/**
 *
 * @author Randyll Bearer
 */
import java.util.Scanner;
import java.util.InputMismatchException;

public class MidtermPractice2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        float userNum1 = 0;
        int end = 10
        float userNum2 = 0;
        Scanner keyboard = new Scanner(System.in);
        while(true){
            try{
                System.out.print("Please enter your numerator: ");
                userNum1 = keyboard.nextFloat();
                System.out.print("Please enter your denominator: ");
                userNum2 = keyboard.nextFloat();
                float result = userNum1 / userNum2;
                int integerPart = Math.floor(result);
                int remainder = userNum1 % userNum2;
                System.out.println("integer part = " + integerPart + "    result = " + result + "     remainder = "+ remainder);
            }catch(InputMismatchException ime){
                System.out.println("Sorry but please enter a valid number!");
        }
    }
    }    
    
    
}
