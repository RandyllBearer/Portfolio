/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

/**
 *
 * @author Randyll Bearer
 */
public class Practice {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Crop corn = new Crop("Corn", 5000, .25);
        System.out.println(corn.toString());
        
        Crop wheat = new Crop();
        wheat.setName("Wheat");
        wheat.setYield(8000);
        wheat.setPrice(.003);
        System.out.println(wheat.toString());
        
        Crop corn2 = new Crop(corn);
        corn2.setYield(5500);
        corn2.setPrice(.25);
        System.out.println(corn2.toString());
        
        if(corn.equals(wheat)){
            System.out.println("\r\nCorn Object 1 is equal to Wheat Object 1");
        }else{
            System.out.println("\r\nCorn Object 1 is NOT equal to Wheat Object 1");
        }
        
        if(corn.equals(corn2)){
            System.out.println("\r\nCorn Object 1 is equal to Corn Object 2");
        }else{
            System.out.println("\r\nCorn Object 1 is NOT equal to Corn Object 2");
        }
        
        
    }
    
}
