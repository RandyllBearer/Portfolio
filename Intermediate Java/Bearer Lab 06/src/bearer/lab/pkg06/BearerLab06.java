package bearer.lab.pkg06;

/**
 * This program has two purposes.  The first is to check to see if a user inputted
 * number is between a hard-coded LOWERBOUND and UPPERBOUND, this will repeat until
 * the value is.  The second purpose is to ask the user for a filename, and then to count
 * the number of lines in that file. If the file does not exist, the program will 
 * continue to ask for a filename until a valid one is given..
 * 
 * D:\School\Intermediate Java\Bearer Lab 06\Lines.txt
 * @author Randyll Bearer
 */

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.File;
import java.lang.NullPointerException;

public class BearerLab06 {

    /**
     * This method will check to see if the user-value lands between the two bounds.
     * 
     * @param LOWERBOUND: Minimum value the user values can equal
     * @param UPPERBOUND: Maximum value the user values can equal
     * @return: The first user value to land between bounds
     */
    public static double getValidNumber(double LOWERBOUND, double UPPERBOUND){      // mention that a do/while loop here, the exception would break the loop
        Scanner keyboard = new Scanner(System.in);
        double userValue = -1;
        
        while(true){
            try{
                System.out.print("Please enter a value between 0 - 100: ");
                userValue = keyboard.nextDouble();
                if(userValue >= LOWERBOUND && userValue <= UPPERBOUND){
                    return userValue;
                }else{
                    System.out.println("Please enter a valid value between 0 - 100.\r\n");
                    keyboard.nextLine();
                }
            }catch(InputMismatchException ime){
                System.out.println("Please enter a valid numercial value between 0 - 100.\r\n");
                keyboard.nextLine();
            }
        }
    }
    
    /**
     * This method will ask the user for a filename, open it, check to see if it 
     * exists, and if so, it returns that filename as a String.  If not, repeats.
     * 
     * @return: A valid file address. 
     */
    public static String getFilename(){
        Scanner keyboard = new Scanner(System.in);
        String filename = "";
        
        while(true){
            try{
                System.out.print("Please enter a filename: ");
                filename = keyboard.nextLine();
                File userFile = new File(filename);
                if(!userFile.exists()){
                    System.out.println("That file does not exist, please try again.\r\n");
                }else{
                    return filename;
                }
            
            }catch(NullPointerException npe){
                System.out.println("Sorry, but that file could not be used.\r\n");
            }
        }
    }
    /**
     * This method uses the String returned from getValidNumber to open the file
     * and count its number of lines.
     * 
     * @param filename: String value containing the address of a valid file location
     * @return : The number of lines in that file
     */
    public static int countFileLines(String filename){
        File userFile = new File(filename);
        Scanner inFile = null;
        int numLines = 0;
        
        try{
            inFile = new Scanner(userFile);
            while(inFile.hasNextLine()){
                numLines += 1;
                inFile.nextLine();
            }
        }catch(FileNotFoundException fnfe){
            System.out.println("Sorry but the file could not be read from");
        }finally{
            inFile.close();
        }
        return numLines;
    }
    
    /**
     * This main method acts as a driver method, calling the other methods
     * and utilizing them effectively. This method will print out a valid number
     * and the amount of lines and filename.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int LOWERBOUND = 0;
        final int UPPERBOUND = 100;
        double num = getValidNumber(LOWERBOUND, UPPERBOUND);
        System.out.println("The value you entered, " + num + " ,satisfies the lower and upper bounds.\r\n");
        
        String filename = getFilename();
        int numLines = countFileLines(filename);
        System.out.println("The file '" + filename + "' has " + numLines + " lines.");
        
    }
    
}   
