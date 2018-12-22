/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midterm.practice;

/**
 *
 * @author Randyll Bearer
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.lang.NullPointerException;
import java.io.IOException;
import java.util.InputMismatchException;

public class MidtermPractice {

    /**
     * @param args the command line arguments
     * @throws FileNotFoundException: May not be a suitable file to write to
     * @throws NullPointerException: May point to a file with nothing written in it
     * 
     * D:\School\Intermediate Java\Bearer Lab 05\Midterm Practice\Items.txt
     */

    public static void main(String[] args)throws FileNotFoundException, NullPointerException {
    
        Scanner keyboard = new Scanner(System.in);
        
        while(true){
            try{
                System.out.print("Hey Man! What file would you like to read from? ");
                String filename = keyboard.nextLine();
                File userFile = new File(filename);
                Scanner inFile = new Scanner(userFile);
                while(inFile.hasNext()){
                    int num1 = inFile.nextInt();
                    int num12 = inFile.nextInt();
                    String phrase1 = inFile.nextLine();
                    phrase1 = phrase1.substring(1);
                    int num2 = inFile.nextInt();
                    int num22 = inFile.nextInt();
                    String phrase2 = inFile.nextLine();
                    int num3 = inFile.nextInt();
                    int num33 = inFile.nextInt();
                    String phrase3 = inFile.nextLine();
                    System.out.println(phrase1 + phrase2 + phrase3);
                    System.out.println(" " + num1 + num2 + num3 + "    " + num12 + num22 + num33);
                    inFile.close();
                    
                    FileWriter outputFile = new FileWriter(filename, true);
                    PrintWriter outFile = new PrintWriter(outputFile);
                    System.out.print("Hello there, Please enter a positive number! ");
                    int usernum1 = keyboard.nextInt();
                    if (usernum1 >= 0){
                        outFile.print(usernum1 + " ");
                    }
                    System.out.print("Hi Guy, please enter a negative number! ");
                    int usernum2 = keyboard.nextInt();
                    if (usernum2 <= 0){
                        outFile.print(usernum2 + " ");
                    }
                    System.out.print("Hola!, please enter a phrase! ");
                    String userPhrase = keyboard.nextLine();
                    outFile.print(userPhrase + "\r\n");
                }
            }catch(FileNotFoundException fnfe){
                
            }catch(NullPointerException npe){
                
            }catch(IOException ioe){
           
            }catch(InputMismatchException){
                System.out.println("Sorry");
            }
            
        
        }

        
        
    }
    
}
