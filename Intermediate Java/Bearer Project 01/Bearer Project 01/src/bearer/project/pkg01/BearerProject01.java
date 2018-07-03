/*
 *
 */
package bearer.project.pkg01;

/**
 * The purpose of this program is to track a company's inventory.  The current
 * inventory will be read in through a file with specific formatting and outputted
 * to the same file with the same formatting.  The current program is able to track the name,
 * price, and quantity of three items.  The program can also handle three actions per
 * item, Buy More, Sell Stock, and Change Price. 
 * 
 * When Buying More, if the amount the user wishes to buy would cost more than their current
 * balance, a prompt will be given to the user asking if they would like to continue.
 * If so, the user's balance will go into the negative but quantity will rise, if
 * the user declines, the user will be asked for input again.
 * 
 * When Selling Stock, the user is prompted how many items they would like to sell.  
 * If they wish to sell more than they possess, a warning will be displayed asking
 * if they would still like to continue.  If so, the item's price will be multiplied
 * by the amount being sold, and that will be added to the user's current balance.  
 * If the user did not wish to continue, they will be asked for input again.
 * 
 * When Changing Price, the user will be asked for the new price.  If the new price
 * is negative, the user is asked again.  If positive, the item's price will be changed
 * to the new price.
 * 
 * (Format of the Input/Output File)
 * Current Balance
 * Price + " " + Quantity + " " + Name
 * Price + " " + Quantity + " " + Name
 * Price + " " + Quantity + " " + Name
 * 
 * @author Randyll Bearer
 */
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.NumberFormat;

public class BearerProject01 {
    
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    
    public static void main(String[] args) throws FileNotFoundException{
    
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    Scanner keyboard = new Scanner(System.in);
    double currentBalance = 0.00;
    double newPrice = 0.00;               // Need to check if valid input before changing current value
    int amountToBuy = 0;
    double amountToBuyPrice = 0.00;
    double amountToBuyTotal = 0.00;
    int amountToSell = 0;
    
    int flag = 0;
    int userChoice = -1;
    String userDecision; // For use in selling items if over stock / buying items if over balance, yes/no
    
    double itemOnePrice = 0.00;
    double itemTwoPrice = 0.00;
    double itemThreePrice = 0.00;
    int itemOneQuantity = 0;
    int itemTwoQuantity = 0;
    int itemThreeQuantity = 0;
    String itemOneName = "";
    String itemTwoName = "";
    String itemThreeName = "";
    
    //---------------Main Body---------------------//
    
    System.out.print("Please enter input file location: ");
    String filename = keyboard.nextLine();
    
    while(true){
        try{
            File invFile = new File(filename);
            if(invFile.exists()){
                Scanner inputFile = new Scanner(invFile);
                currentBalance = inputFile.nextDouble();        
                itemOnePrice = inputFile.nextDouble();          // Start of individual values
                itemOneQuantity = inputFile.nextInt();
                itemOneName = inputFile.nextLine();
                itemOneName = itemOneName.substring(1);         // Gets rid of the extra whitespace before itemName due to .nextLine leaving one space in
                itemTwoPrice = inputFile.nextDouble();
                itemTwoQuantity = inputFile.nextInt();
                itemTwoName = inputFile.nextLine();
                itemTwoName = itemTwoName.substring(1);
                itemThreePrice = inputFile.nextDouble();
                itemThreeQuantity = inputFile.nextInt();
                itemThreeName = inputFile.nextLine();
                itemThreeName = itemThreeName.substring(1);
                inputFile.close();
                flag = 1;
                break;
            }else if(filename.equalsIgnoreCase("stop")){        // Maybe didn't want to run program
                break;
            }
            else if(!invFile.exists()){
                System.out.println("Sorry but that file does not exist, please try again or enter 'stop'; to end.");
                System.out.print("Please enter input file location: ");
                filename = keyboard.next();
            }
        }catch(NullPointerException npe){
            System.out.println("Sorry but that file does not exist, please try again or enter 'stop' to end.");
        }catch(Exception e){
            System.out.println("Sorry but there was an error, please try again or enter 'stop' to end.");
        }
    }
    
    while(true && flag == 1){        // Main UI loop.
        try{
            System.out.println("---------------------------------");
            System.out.println("Current Balance: " + currencyFormatter.format(currentBalance));
            System.out.println("  1. " + itemOneName + "\t\t" + "(" + itemOneQuantity + " at " + currencyFormatter.format(itemOnePrice) + ")");
            System.out.println("  2. " + itemTwoName + "\t\t" + "(" + itemTwoQuantity + " at " + currencyFormatter.format(itemTwoPrice) + ")");
            System.out.println("  3. " + itemThreeName + "\t\t" + "(" + itemThreeQuantity + " at " + currencyFormatter.format(itemThreePrice) + ")");
            System.out.println("  0. Exit.");
            System.out.print("\nPlease enter choice: ");
            userChoice = keyboard.nextInt();
            
            if (userChoice == 0){                                   // Exit Loop
                System.out.println("Goodbye!");
                break;
            }else if(userChoice < 0 || userChoice > 3){
                System.out.println("User selection does not exist, please enter a valid, positive integer.");
            }else if(userChoice == 1){                              // Item 1
                while(true){
                    try{
                        System.out.println("---------------------------------");
                        System.out.println("Current Balance: " + currencyFormatter.format(currentBalance));
                        System.out.println("Current Quantity: " + itemOneQuantity);
                        System.out.println("Current Price: " + currencyFormatter.format(itemOnePrice));
                        System.out.println("1.  Sell " + itemOneName);
                        System.out.println("2.  Buy " + itemOneName);
                        System.out.println("3.  Change Price");
                        System.out.println("0.  Return to Menu");
                        System.out.print("\nPlease enter choice: ");
                        userChoice = keyboard.nextInt();
                        
                        if(userChoice == 0){        // Exit
                            break;
                        }else if(userChoice < 0 || userChoice > 3){
                            System.out.println("User selection does not exist, please enter a valid, positive integer.");
                        }else if(userChoice == 1){  // Sell
                            while(true){
                                try{
                                    System.out.print("Amount to sell (Current Quantity: " + itemOneQuantity + "): ");
                                    amountToSell = keyboard.nextInt();
                                    if(amountToSell < 0){
                                        System.out.println("Please enter a valid, positive integer value.");
                                    }else if(amountToSell > itemOneQuantity){           // Selling more than you have
                                        System.out.println("Warning: Selling more than is in stock!");
                                        System.out.print("Do you wish to continue? [Y/N]: ");           // Not having a valid answer will send back to beginning loop
                                        userDecision = keyboard.next();
                                        if(userDecision.equalsIgnoreCase("yes") || userDecision.equalsIgnoreCase("y")){
                                            itemOneQuantity = itemOneQuantity - amountToSell;
                                            currentBalance = currentBalance + (amountToSell * itemOnePrice);
                                            break;
                                        }else if (userDecision.equalsIgnoreCase("no") || userDecision.equalsIgnoreCase("n")){
                                            System.out.print("");
                                        }else{
                                            System.out.println("Please enter a 'Yes' or 'No' answer.");
                                        }
                                    }else{
                                        itemOneQuantity = itemOneQuantity - amountToSell;
                                        currentBalance = currentBalance + (amountToSell * itemOnePrice);
                                        break;
                                    }    
                                }catch(InputMismatchException ime){
                                    System.out.println("Please enter a valid, positive integer value.");
                                    keyboard.next();
                                }    
                            }
                        }else if(userChoice == 2){  // Buy
                            while(true){
                                try{
                                    System.out.print("Amount to buy: ");
                                    amountToBuy = keyboard.nextInt();
                                    if(amountToBuy < 0){                            // Will make you restart buy prompt if invalid
                                        System.out.println("Please enter a valid, positive integer value.");
                                    }else{
                                            System.out.print("Price per item: ");
                                            amountToBuyPrice = keyboard.nextDouble();
                                        if(amountToBuyPrice < 0){
                                            System.out.println("Please enter a valid, positive price.");
                                        }else{
                                            amountToBuyTotal = amountToBuyPrice * amountToBuy;
                                            if (amountToBuyTotal > currentBalance){
                                                System.out.print("You are about to buy more than you can afford, would you still like to continue? [Y/N] ");
                                                userDecision = keyboard.next();
                                                if(userDecision.equalsIgnoreCase("yes") || userDecision.equalsIgnoreCase("y")){
                                                    currentBalance = currentBalance - amountToBuyTotal;
                                                    itemOneQuantity = itemOneQuantity + amountToBuy;
                                                    break;
                                                }else if(userDecision.equalsIgnoreCase("no") || userDecision.equalsIgnoreCase("n")){
                                                    System.out.print("");
                                                }else{
                                                    System.out.println("Please enter a 'Yes' or 'No' answer.");
                                                }
                                            }else{
                                                currentBalance = currentBalance - amountToBuyTotal;
                                                itemOneQuantity = itemOneQuantity + amountToBuy;
                                                break;
                                            }     
                                        }
                                    }
                                }catch(InputMismatchException ime){
                                    System.out.println("Please enter a valid, positive integer amount.");
                                    keyboard.next();
                                }    
                            }
                        }else if(userChoice == 3){ // Change Price
                            while(true){
                                try{
                                    System.out.print("New price: ");
                                    newPrice = keyboard.nextDouble();
                                    if(newPrice < 0){
                                        System.out.println("Cannot have a negative price, please enter a valid, positive amount.");
                                    }else{
                                        itemOnePrice = newPrice;
                                        break;
                                    }
                                }catch(InputMismatchException ime){
                                    System.out.println("Please enter a valid, positive amount.");
                                    keyboard.next();
                                }
                            }
                        }
                    }catch(InputMismatchException ime){
                        System.out.println("Please input a valid, integer choice");
                        keyboard.next();
                    }
                }
                
            }else if(userChoice == 2){      // Item 2
                while(true){
                    try{
                        System.out.println("---------------------------------");
                        System.out.println("Current Balance: " + currencyFormatter.format(currentBalance));
                        System.out.println("Current Quantity: " + itemTwoQuantity);
                        System.out.println("Current Price: " + currencyFormatter.format(itemTwoPrice));
                        System.out.println("1.  Sell " + itemTwoName);
                        System.out.println("2.  Buy " + itemTwoName);
                        System.out.println("3.  Change Price");
                        System.out.println("0.  Return to Menu");
                        System.out.print("\nPlease enter choice: ");
                        userChoice = keyboard.nextInt();
                        
                        if(userChoice == 0){        // Exit
                            break;
                        }else if(userChoice < 0 || userChoice > 3){
                            System.out.println("User selection does not exist, please enter a valid, positive integer.");
                        }else if(userChoice == 1){  // Sell
                            while(true){
                                try{
                                    System.out.print("Amount to sell (Current Quantity: " + itemTwoQuantity + "): ");
                                    amountToSell = keyboard.nextInt();
                                    if(amountToSell < 0){
                                        System.out.println("Please enter a valid, positive integer value.");
                                    }else if(amountToSell > itemTwoQuantity){   // Selling more than you have
                                        System.out.println("Warning: Selling more than is in stock!");
                                        System.out.print("Do you wish to continue? [Y/N]: ");           // Not having a valid answer will send back to beginning loop
                                        userDecision = keyboard.next();
                                        if(userDecision.equalsIgnoreCase("yes") || userDecision.equalsIgnoreCase("y")){
                                            itemTwoQuantity = itemTwoQuantity - amountToSell;
                                            currentBalance = currentBalance + (amountToSell * itemTwoPrice);
                                            break;
                                        }else if (userDecision.equalsIgnoreCase("no") || userDecision.equalsIgnoreCase("n")){
                                            System.out.print("");
                                        }else{
                                            System.out.println("Please enter a 'Yes' or 'No' answer.");
                                        }
                                    }else{
                                        itemTwoQuantity = itemTwoQuantity - amountToSell;
                                        currentBalance = currentBalance + (amountToSell * itemTwoPrice);
                                        break;
                                    }    
                                }catch(InputMismatchException ime){
                                    System.out.println("Please enter a valid, positive integer value.");
                                    keyboard.next();
                                }    
                            }
                        }else if(userChoice == 2){  // Buy
                            while(true){
                                try{
                                    System.out.print("Amount to buy: ");
                                    amountToBuy = keyboard.nextInt();
                                    if(amountToBuy < 0){                            // Will make you restart buy prompt if invalid
                                        System.out.println("Please enter a valid, positive integer value.");
                                    }else{
                                            System.out.print("Price per item: ");
                                            amountToBuyPrice = keyboard.nextDouble();
                                        if(amountToBuyPrice < 0){
                                            System.out.println("Please enter a valid, positive price.");
                                        }else{
                                            amountToBuyTotal = amountToBuyPrice * amountToBuy;
                                            if (amountToBuyTotal > currentBalance){
                                                System.out.print("You are about to buy more than you can afford, would you still like to continue? [Y/N] ");
                                                userDecision = keyboard.next();
                                                if(userDecision.equalsIgnoreCase("yes") || userDecision.equalsIgnoreCase("y")){
                                                    currentBalance = currentBalance - amountToBuyTotal;
                                                    itemTwoQuantity = itemTwoQuantity + amountToBuy;
                                                    break;
                                                }else if(userDecision.equalsIgnoreCase("no") || userDecision.equalsIgnoreCase("n")){
                                                    System.out.print("");
                                                }else{
                                                    System.out.println("Please enter a 'Yes' or 'No' answer.");
                                                }
                                            }else{
                                                currentBalance = currentBalance - amountToBuyTotal;
                                                itemTwoQuantity = itemTwoQuantity + amountToBuy;
                                                break;
                                            }     
                                        }
                                    }
                                }catch(InputMismatchException ime){
                                    System.out.println("Please enter a valid, positive integer amount.");
                                    keyboard.next();
                                }    
                            }
                        }else if(userChoice == 3){      // Change Price
                            while(true){
                                try{
                                    System.out.print("New price: ");
                                    newPrice = keyboard.nextDouble();
                                    if(newPrice < 0){
                                        System.out.println("Cannot have a negative price, please enter a valid, positive amount.");
                                    }else{
                                        itemTwoPrice = newPrice;
                                        break;
                                    }
                                }catch(InputMismatchException ime){
                                    System.out.println("Please enter a valid, positive amount.");
                                    keyboard.next();
                                }
                            }
                        }
                    }catch(InputMismatchException ime){
                        System.out.println("Please input a valid, integer choice");
                        keyboard.next();
                    }
                }        
 
            }else if(userChoice == 3){      // Item 3
                while(true){
                    try{
                        System.out.println("---------------------------------");
                        System.out.println("Current Balance: " + currencyFormatter.format(currentBalance));
                        System.out.println("Current Quantity: " + itemThreeQuantity);
                        System.out.println("Current Price: " + currencyFormatter.format(itemThreePrice));
                        System.out.println("1.  Sell " + itemThreeName);
                        System.out.println("2.  Buy " + itemThreeName);
                        System.out.println("3.  Change Price");
                        System.out.println("0.  Return to Menu");
                        System.out.print("\nPlease enter choice: ");
                        userChoice = keyboard.nextInt();
                        
                        if(userChoice == 0){        // Exit
                            break;
                        }else if(userChoice < 0 || userChoice > 3){
                            System.out.println("User selection does not exist, please enter a valid, positive integer.");
                        }else if(userChoice == 1){  // Sell
                            while(true){
                                try{
                                    System.out.print("Amount to sell (Current Quantity: " + itemThreeQuantity + "): ");
                                    amountToSell = keyboard.nextInt();
                                    if(amountToSell < 0){
                                        System.out.println("Please enter a valid, positive integer value.");
                                    }else if(amountToSell > itemThreeQuantity){     // Selling more than you have
                                        System.out.println("Warning: Selling more than is in stock!");
                                        System.out.print("Do you wish to continue? [Y/N]: ");           // Not having a valid answer will send back to beginning loop.
                                        userDecision = keyboard.next();
                                        if(userDecision.equalsIgnoreCase("yes") || userDecision.equalsIgnoreCase("y")){
                                            itemThreeQuantity = itemThreeQuantity - amountToSell;
                                            currentBalance = currentBalance + (amountToSell * itemThreePrice);
                                            break;
                                        }else if (userDecision.equalsIgnoreCase("no") || userDecision.equalsIgnoreCase("n")){
                                            System.out.print("");
                                        }else{
                                            System.out.println("Please enter a 'Yes' or 'No' answer.");
                                        }
                                    }else{
                                        itemThreeQuantity = itemThreeQuantity - amountToSell;
                                        currentBalance = currentBalance + (amountToSell * itemThreePrice);
                                        break;
                                    }    
                                }catch(InputMismatchException ime){
                                    System.out.println("Please enter a valid, positive integer value.");
                                    keyboard.next();
                                }    
                            }
                        }else if(userChoice == 2){  // Buy
                            while(true){
                                try{
                                    System.out.print("Amount to buy: ");
                                    amountToBuy = keyboard.nextInt();
                                    if(amountToBuy < 0){                            
                                        System.out.println("Please enter a valid, positive integer value.");
                                    }else{
                                            System.out.print("Price per item: ");
                                            amountToBuyPrice = keyboard.nextDouble();
                                        if(amountToBuyPrice < 0){
                                            System.out.println("Please enter a valid, positive price.");
                                        }else{
                                            amountToBuyTotal = amountToBuyPrice * amountToBuy;
                                            if (amountToBuyTotal > currentBalance){
                                                System.out.print("You are about to buy more than you can afford, would you still like to continue? [Y/N] ");        // If user enters incorrect value, will loop back to start.
                                                userDecision = keyboard.next();
                                                if(userDecision.equalsIgnoreCase("yes") || userDecision.equalsIgnoreCase("y")){
                                                    currentBalance = currentBalance - amountToBuyTotal;
                                                    itemThreeQuantity = itemThreeQuantity + amountToBuy;
                                                    break;
                                                }else if(userDecision.equalsIgnoreCase("no") || userDecision.equalsIgnoreCase("n")){
                                                    System.out.print("");
                                                }else{
                                                    System.out.println("Please enter a 'Yes' or 'No' answer.");
                                                }
                                            }else{
                                                currentBalance = currentBalance - amountToBuyTotal;
                                                itemThreeQuantity = itemThreeQuantity + amountToBuy;
                                                break;
                                            }     
                                        }
                                    }
                                }catch(InputMismatchException ime){
                                    System.out.println("Please enter a valid, positive integer amount.");
                                    keyboard.next();
                                }    
                            }
                        }else if(userChoice == 3){ // Change Price
                            while(true){
                                try{
                                    System.out.print("New price: ");
                                    newPrice = keyboard.nextDouble();
                                    if(newPrice < 0){
                                        System.out.println("Cannot have a negative price, please enter a valid, positive amount.");
                                    }else{
                                        itemThreePrice = newPrice;
                                        break;
                                    }
                                }catch(InputMismatchException ime){
                                    System.out.println("Please enter a valid, positive amount.");
                                    keyboard.next();
                                }
                            }
                        }
                    }catch(InputMismatchException ime){
                        System.out.println("Please input a valid, integer choice");
                        keyboard.next();
                    }
                }
            }
            
        }catch(InputMismatchException ime){ 
            System.out.println("Please enter a valid, positive number choice.");
            keyboard.next();
        }
    }
   
    if(flag == 1){
        PrintWriter printWriter = new PrintWriter(filename);
        printWriter.println(currentBalance);
        printWriter.println(itemOnePrice + " " + itemOneQuantity + " " + itemOneName);
        printWriter.println(itemTwoPrice + " " + itemTwoQuantity + " " + itemTwoName);
        printWriter.println(itemThreePrice + " " + itemThreeQuantity + " " + itemThreeName);
        printWriter.close();        
    }        
            
            
    }
    
}
