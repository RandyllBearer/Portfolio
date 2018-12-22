/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication17;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
/**
 *
 * @author Randyll Bearer
 */
public class JavaApplication17 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int flag = 0;
        int numStrings = 0;
        
        while(flag == 0){
            try{
                System.out.print("How many strings would you like to enter? ");
                numStrings = kb.nextInt();
                if(numStrings > 0 ){
                    flag = 1;
                }else{
                    System.out.println("User must enter more than 0 Strings");
                }
            }catch(InputMismatchException ime){
                System.out.println("User mut enter an integer amount");
            }
        }
        
        ArrayList<String> array = getStrings(numStrings, "Please enter String: ");
        
        System.out.print("What string would you like to search for? ");
        String toSearch = kb.next();
        
        int timesOccurred = countStrings(array, toSearch);
        
        if(timesOccurred > 1){
            int indexToDelete = array.lastIndexOf(toSearch);
            array.remove(indexToDelete);
        }
        
        System.out.println("\r\n" + array);
        
    }
    
    public static ArrayList getStrings(int numTimes, String userPrompt){
        Scanner kb = new Scanner(System.in);
        ArrayList<String> userStrings = new ArrayList<String>();
        String toAdd = "";
        
        for(int i = 0; i< numTimes; i++){
            System.out.print(userPrompt);
            toAdd = kb.nextLine();
            userStrings.add(toAdd);
        }
        return userStrings;
    }
    
    public static int countStrings(ArrayList userArray, String toParse){
        int timesOccurred = 0;
        
        for(int i = 0; i < userArray.size(); i++){
            if(userArray.get(i).equals(toParse)){
                timesOccurred += 1;
            }
        }
   
        return timesOccurred;
    }
}