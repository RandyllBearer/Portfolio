package bearer.lab.pkg1;

import java.util.Scanner;
import java.text.DecimalFormat;

public class Lab1_Part3 {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        DecimalFormat fmt3Decs = new DecimalFormat("0.000");
        
        int choice;
        do {
            System.out.println(" 1. Chemistry: Celsius to Fahrenheit");
            System.out.println(" 2. Math: Compute Squares");
            System.out.println(" 3. Biology: Population Growth");
            System.out.println(" 0. End Program");
            System.out.print("Enter your choice: ");
            choice = keyboard.nextInt();
            
            System.out.println();
            
            if (choice == 1) {
                System.out.println("I'm going to help you convert Celsius to Fahrenheit!");
                System.out.println("I only work with realistic temperatures, so anything below absolute zero (-273.15) and above the temperature of the Sun (5506.85) will not work.");
                System.out.print("Please enter your temperature in Celsius: ");
                double temp = keyboard.nextDouble();
                while (temp < -273.15 && temp > 5506.85) {
                    System.out.print("Please enter a temperature between absolute zero (-273.15 C) and the temperature of the Sun (5506.85 C): ");
                    temp = keyboard.nextDouble();
                }
                System.out.println("The temperature in Fahrenheit is: "+9./5*temp+32);
            }
            
            if (choice == 2) {
                //Math
                System.out.println("I'm going to calculate the squares of all integers between 0 and the number you give me!");
                int i = keyboard.nextInt();
                for (choice = i; choice >= 0; choice--) {
                    System.out.print("The square of" + choice + "is" + choice*choice);
                }
            }
            
            if (choice == 3) {
                //Biology
                System.out.println("I'm going to calculate population growth for you!!");
                System.out.println("We'll start with a population of 2 and see how it grows over 10 days.");
                double p = 2;
                
                System.out.print("But first, what's the average percent increase per day?");
                int increase = keyboard.nextInt();
                while (increase < 0) {
                    System.out.print("Percent increase must be positive. Please try again: ");
                    increase = keyboard.nextInt();
                }
                
                for(int d = 0; d <= 10; ) {
                    System.out.println("On day" + d + ", the population will be:" + fmt3Decs.format(p));
                    p = p + p*increase;
                }
            }
            
            else
            {
                System.exit(0);
            }
        } while (choice != -1);
    }
}
