/*
 * 
 */

package bearer.lab.pkg04.program.pkg1;
/**
 * The purpose of this program is to calculate how many times a coin flip results
 * in heads facing up using a loop.  If the random generator is [0,1,2,3,4] the coin will land
 * on tails, if [5,6,7,8,9] the coin will land on heads.  The amount of times the
 * coin is flipped is controlled by user integer input.
 * 
 * @author rlb97
 */
import java.util.Scanner;
import java.util.Random;

public class BearerLab04Program1 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Random rand = new Random();
        Scanner keyboard = new Scanner(System.in);
        int userCount = 0;
        int heads = 0;
        int tails = 0;
        int count = 0;
        
        while(userCount <= 0){                  
            System.out.print("How many times should I flip a coin? "); // This will crash if given a string
            try{
                userCount = keyboard.nextInt();
            }catch (java.util.InputMismatchException ime){
                System.out.println("Please input a valid positive integer greater than 0.");
                keyboard.nextLine();           
            }
        }
        
        while (count < userCount){
            int coinflip = rand.nextInt(10); // Inclusive, needs to be 1 above 9
            if(coinflip > 4 && coinflip <= 9){
                heads += 1;
            }else if(coinflip >= 0 && coinflip <= 4){
                tails += 1;
            }
            count += 1;
        }
        
        System.out.println("Done. Heads showed up " + heads + " times.");
    } 
}
