/*
 *
 * 
 */
package bearer.lab.pkg03;

/**
 * The purpose of this program is to calculate the monthly bill of an
 * ISP customer who is subscribed to one of three plans. The total monthly bill
 * will be calculated through the equation 
 * 
 * Total = Package Cost + (userExtraHours * Additional Hour cost)
 * Output should be in the form of #xx.xx
 * 
 * The program is split into 3 phases through the use of a "flag" variable.
 * The first part asks and checks what package the user has.
 * The second part asks and checks how many hours the user has used.
 * The last part calculates the total bill.
 * 
 * @author Randyll Bearer
 */
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.InputMismatchException;

public class BearerLab03 {
    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        
        Scanner keyboard = new Scanner(System.in);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
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
        int flag = 0;                   
        
        while(!(flag == 1)){
            try{
                System.out.print("What package have you subscribed to? ");
                userPackage = scan.nextLine();
                if (userPackage.equalsIgnoreCase("A")){
                    userTotal = packageAFlat;           // User is guaranteed to pay atleast this
                    userAdd = packageAAdd;
                    userComp = packageAComp;
                    flag = 1;
                    System.out.print("How many hours did you use? ");
                }else if(userPackage.equalsIgnoreCase("B")){
                    userTotal = packageBFlat;
                    userAdd = packageBAdd;
                    userComp = packageBComp;
                    flag = 1;
                    System.out.print("How many hours did you use? ");
                }else if(userPackage.equalsIgnoreCase("C")){
                    userTotal = packageCFlat;
                     userAdd = packageCAdd;
                    userComp = packageCComp;
                    flag = 1;
                    System.out.print("How many hours did you use? ");
                }else{
                    System.out.println("Sorry but that is not an acceptable package. The "
                    + "packages that we offer are 'A', 'B', and 'C'.");
                }
            }catch(InputMismatchException ime){
                System.out.println("Please enter a valid package.  The packages that we offer are 'A', 'B', and 'C'.");
            }
        }
        
        if (scan.hasNextDouble() && flag == 1){
            while(!(flag == 2)){
                try{
                    userHours = scan.nextDouble();
                    double remainder = userHours % 1;       // This will round any number up
                    if (userHours <0){                     
                        System.out.println("Sorry but that is not an acceptable number of hours. Please enter a positive integer.");
                        break;
                    }else if (remainder > 0){
                        userHours = userHours - remainder + 1;
                    }else if(flag == 0){
                        System.out.print("");
                        flag = 1;
                        break;
                    }else{
                        System.out.println("Sorry but that is not an acceptable number of hours. Please enter a positive number.");
                }
            }catch (InputMismatchException ime){
                System.out.println("Sorry but that is not an acceptable number of hours. Please enter a positive number.");
            }
        }
        
        if (flag == 2){
            while(true){
                try{
                    userExtraHours = userHours - userComp;
                    if (userExtraHours <0){
                        userExtraHours = 0;}
                    userTotal = userTotal + (userExtraHours * userAdd);
                    System.out.println("Your total charge is " + currencyFormatter.format(userTotal) + ".");
                }catch(InputMismatchException ime){
                    
                }
}}}}