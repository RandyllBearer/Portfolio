/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bearer.lab.pkg05;

/**
 *
 * @author Randyll Bearer
 */

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class BearerLab05 {

    /**
     * D:\School\Intermediate Java\Bearer Lab 05\UsageHistory.txt
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner keyboard = new Scanner(System.in);
        DecimalFormat decimalFormatter = new DecimalFormat("#.##");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        
        int historyCount = 0;           // Divide by this to get Average Prices
        String historyPackage = "";
        double historyHours = 0;
        double historyMonthly = 0;
        double packageAFlat = 9.95;     // Flat rate for the month
        double packageBFlat = 13.95;
        double packageCFlat = 19.95;
        double packageAAdd = 2;         // Extra cost per hour
        double packageBAdd = 1;
        double packageCAdd = 0;
        double packageAComp = 10;       // Hours given by the package
        double packageBComp = 20;
        double packageCComp = -1;       // Unlimited access
        double userTotal = 0;           
        double userComp = 0;               
        double userAdd = 0;             
        double userExtraHours = 0;      // How many additional hours must the user pay for?
        String userPackage = "z";
        double userHours = 0;
        double remainder = 0;
        int flag = 0;
        
        System.out.print("Please enter input file location: ");
        String filename = keyboard.nextLine();
        
        while(true){    // Reads fie (creates if necessary) and lists User Averages
            try{
                File usageFile = new File(filename);
                if(usageFile.exists()){
                    Scanner inputFile = new Scanner(usageFile);
                    while(inputFile.hasNext()){
                        historyPackage = inputFile.next();
                        historyHours += inputFile.nextDouble();
                        historyMonthly += inputFile.nextDouble();
                        historyCount += 1;
                    }
                    System.out.println("Usage History:");
                    System.out.println("\tAverage Hours Used: " + decimalFormatter.format(historyHours/historyCount));
                    System.out.println("\tAverage Monthly Cost: " + currencyFormatter.format(historyMonthly/historyCount));
                    System.out.println("\tTotal Paid: " +currencyFormatter.format(historyMonthly));
                    
                    break;
                }else if(!(usageFile.exists())){            // Theoretically these two statements should cover everything unless it is impossible to create a file at desired destination
                    System.out.println("No such file exists; Creating it...");
                    usageFile.createNewFile();
                    break;
                }
                
            }catch(Exception e){
                System.out.println("Sorry, but a file could not be created at that location");
                System.out.print("Please enter input file location: ");
                filename = keyboard.nextLine();
            }
        }
        
        while(true){
            try{
                System.out.print("What package have you subscribed to? ");
                userPackage = keyboard.nextLine();
                if (userPackage.equalsIgnoreCase("a")){
                    userTotal = packageAFlat;           // User is guaranteed to pay atleast this
                    userAdd = packageAAdd;
                    userComp = packageAComp;
                    flag = 1;                    
                }else if (userPackage.equalsIgnoreCase("b")){
                    userTotal = packageBFlat;
                    userAdd = packageBAdd;
                    userComp = packageBComp;
                    flag = 1;
                }else if (userPackage.equalsIgnoreCase("c")){
                    userTotal = packageCFlat;
                    userAdd = packageCAdd;
                    userComp = packageCComp;
                    flag = 1;
                }else{
                    System.out.println("Sorry but that is not a valid package, the packages that we offer are 'A', 'B', and 'C'");
                }
                
                while(flag==1){
                    try{
                        System.out.print("How many hours did you use? ");
                        userHours = keyboard.nextDouble();
                        remainder = userHours % 1;
                        if(userHours < 0){
                            System.out.println("Sorry but you cannot have used negative hours, please enter a valid positive amount");
                        }else if (remainder > 0){
                            userHours = userHours - remainder + 1;
                            flag = 2;
                        }else{
                            flag = 2;
                        }
                    }catch(InputMismatchException ime){
                        System.out.println("Please enter a valid, positive amount of hours used");
                        keyboard.next();
                    }// Print out after hours used
                }
                
                if (flag==2){
                    userExtraHours = userHours - userComp;
                    if (userExtraHours <0){
                        userExtraHours = 0;}
                    userTotal = userTotal + (userExtraHours * userAdd);
                    System.out.println("Your total charge is " + currencyFormatter.format(userTotal) + ".");    // Begin to Write to File
                    FileWriter fileWriter = new FileWriter(filename,true);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    printWriter.append(userPackage.toUpperCase() + " " + userHours + " " + decimalFormatter.format(userTotal) + "\r\n");
                    printWriter.close();
                    
                    break;
                }
                
            }catch(InputMismatchException ime){
                System.out.println("Sorry but that is not a valid package, the packages that we offer are 'A', 'B', and 'C'");
                keyboard.next();
            }catch(IOException ioe){
                System.out.println("Sorry but the program could not write to file");
                keyboard.next();
            }
        }
    }
}
