/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midterm.practice.pkg5.summing.values.in.file;

/**
 *
 * @author Randyll Bearer
 */
import java.util.Scanner;
import java.io.File;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.lang.NullPointerException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
public class MidtermPractice5SummingValuesInFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DecimalFormat decimalFormatter = new DecimalFormat("#.####");
        double hello = 0.123456;
        System.out.println(decimalFormatter.format(hello));
        
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        
        System.out.println(currencyFormatter.format(1.546));
        System.out.println(percentFormatter.format(50));
    }
    
}
