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
public class Crop {
    private String name = "Wheat";
    private double yield = 0;
    private double price = 0;
    
    public Crop(String toName, double toYield, double toPrice){
        if(toYield >= 0 && toPrice >= 0){
            setYield(toYield);
            setPrice(toPrice);
            setName(toName);
        }else{
            throw new IllegalArgumentException("Both Yield and Price must be greater than or equal to zero!");
        }
    }
    
    public Crop(){
        setName("Wheat");
        setYield(0);
        setPrice(0);
    }
    public Crop(Crop toCopy){
        setName(toCopy.getName());
        setYield(toCopy.getYield());
        setPrice(toCopy.getPrice());
    }
    
    public void setName(String toName){
        name = toName;
    }
    
    public String getName(){
        return name;
    }
    
    public void setYield(double toYield){
        if(yield >= 0){
            yield = toYield;
        }
        else{
            throw new IllegalArgumentException("Yield must be greater than or equal to zero!");
        }
    }
    
    public double getYield(){
        return yield;
    }
    
    public void setPrice(double toPrice){
        if(price >= 0){
            price = toPrice;
        }
        else{
            throw new IllegalArgumentException("Price must be greater than or equal to zero!");
        }
    }
    
    public Double getPrice(){
        return price;
    }
    
    public boolean equals(Crop toEqual){
        if(this.getName().equalsIgnoreCase(toEqual.getName())){
            return true;
        }
        return false;
    }
    
    public boolean equals(String toEqual){
        if(this.getName().equalsIgnoreCase(toEqual)){
            return true;
        }
        return false;
    }
    
    public String toString(){
        return ("\r\nName of Crop: " + getName() + "\r\nAmount Yielded: " + getYield() + "\r\nPrice per Yield: $" + getPrice());
    }
    
}
