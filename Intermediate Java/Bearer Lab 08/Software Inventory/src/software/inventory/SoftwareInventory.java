/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.inventory;

/**
 *
 * @author Randyll Bearer
 */
public class SoftwareInventory {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SoftwareHouse company = new SoftwareHouse();
        
        company.setName("Google");
        company.setStreet("1 Silver Birch Lane");
        company.setCity("Oil City");
        company.setState("PA");
        company.setZipcode("16301");
        
        Software product = new Software();
        Software product2 = new Software();
        
        product.setName("Videogame");
        product2.setName("Spreadsheet");
        product.setPrice(60);
        product2.setPrice(30);
        product.setQuantity(1);
        product2.setQuantity(5);
        product.setSoftwareHouse(company);
        product2.setSoftwareHouse(company);
        
        System.out.println(product + "\r\n");
        System.out.println(product2 + "\r\n");
    }
    
}
