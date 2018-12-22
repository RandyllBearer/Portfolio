package bearer.lab.pkg09;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * This program will create an ArrayList of user-provided strings, then search that
 * ArrayList for another user-provided string.  If two or more equal strings are found, then
 * the farthest indexed string will be removed.
 * 
 * @author rlb97
 */
public class BearerLab09 {
    
    /**
     * Takes a user-provided integer value greater than 0, and executes a loop that
     * amount of times.  Each iteration of the loop asks for the user to enter a string,
     * and then that string is added to an array list which is returned after the loop 
     * has finished.
     * 
     * @param amountOfStrings: The amount of iterations the for loop will run
     * @param userPrompt: What prompt message is displayed each iteration
     * @return: An ArrayList containing all user entered strings.
     */
    public static ArrayList<String> getStrings(int amountOfStrings, String userPrompt){
        ArrayList<String> userStrings = new ArrayList<String>();
        Scanner kb = new Scanner(System.in);
        String newString = "";
        for(int i = 0; i < amountOfStrings; i++){
            System.out.print(userPrompt);
            newString = kb.nextLine();
            userStrings.add(newString);
        }
        return userStrings;
    }
    
    /**
     * Takes a copy of the ArrayList containing all strings from getStrings() as well
     * as a user-provided string to parse for.  Returns the number of times the user-provided 
     * string occurs.
     * 
     * @param arrayToCheck: An ArrayList of strings
     * @param stringToCheck: What string is going to be searched for in arrayToCheck
     * @return: The number of times stringToCheck occurs in arrayToCheck 
     */
    public static int countValues(ArrayList<String> arrayToCheck, String stringToCheck){
        //
        int timesOccured = 0;
        for(int i = 0; i <= arrayToCheck.size(); i++){
            if(arrayToCheck.get(i).equals(stringToCheck)){
                timesOccured += 1;
            }
        }
        return timesOccured;
        
    }
    
    /**
     * Asks the user how many strings they would like to enter and passes it through
     * getStrings(). Requests a string to look for and passes it through countValues()
     * along with the returned ArrayList.  Checks the returned amount of occurrences, and
     * if greater than 1, deletes the last found indexed occurrence.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        ArrayList<String> storedValues = new ArrayList<String>();
        Scanner kb = new Scanner(System.in);
        String userPrompt = "Enter String: ";   // Prompt for use in getStrings()
        int flag = 0;  // Has getStrings() ran yet?
        
        // Form the Array
        while(flag == 0){   
            try{
                System.out.print("How many strings would you like to enter into the program? ");
                int numberOfTimes = kb.nextInt();
                if(numberOfTimes > 0){
                    storedValues = getStrings(numberOfTimes, userPrompt);
                    flag = 1;
                    kb.nextLine();
                }else{
                    System.out.println("Please enter an integer value greater than zero.");
                }
            }catch(InputMismatchException ime){
                System.out.println("Please enter an integer value greater than zero.\r\n");
                kb.nextLine();
            }        
        }
        
        // Search the Array
        System.out.print("\r\nWhich string would you like to search for? ");
        String toSearchFor = kb.nextLine();
        int numberOfOccurences = countValues(storedValues, toSearchFor);
        if(numberOfOccurences >= 2){    // Clean the array
            int indexToDelete = storedValues.lastIndexOf(toSearchFor);
            storedValues.remove(indexToDelete);
        }
        System.out.println("\r\n" + storedValues);
    }
}
