package crop;

/**
 * Represents a Crop object, with fields Name, Yield, and Price (per kg).
 * Has access to Accessor/Mutator methods, an equal methods for comparing the names
 * of two Crop objects and comparing a Cop object name with a string, as well as a
 * toString method.
 * 
 * @author Randyll Bearer
 */
import java.text.NumberFormat;
public class Crop {
    private String name;
    private double yield;
    private double price;
    
    
    /**
     * No-Args constructor to provide a default initialization for the Crop object.  
     */
    public Crop(){
        setName("Wheat");
        setYield(8000);
        setPrice(0.003);
    }
    
    /**
     * Initializer-Constructor to assign values to Crop object fields.  
     * 
     * @param cropName: Name of the Crop object
     * @param cropYield: How much of the Crop object was grown
     * @param cropPrice: Price of the Crop object per kilogram 
     */
    public Crop(String newName, double newYield, double newPrice){   
        setName(newName);
        setYield(newYield);
        setPrice(newPrice);
    }
    
    /**
     * Copy-Constructor to make a duplicate of a Crop object.
     * 
     * @param cropToCopy: The Crop object you wish to make a duplicate of
     */
    public Crop(Crop cropToCopy){           
        setName(cropToCopy.getName());
        setYield(cropToCopy.getYield());
        setPrice(cropToCopy.getPrice());
    }
    
    /**
     * Mutator method for Crop object name.
     * 
     * @param cropName: New String name for Crop object. (No Restrictions) 
     */
    public void setName(String cropName){ 
        name = cropName;
    }
    
    /**
     * Accessor method for Crop object name.
     * 
     * @return: Name of Crop object   
     */
    public String getName(){                
        return name;
    }
    
    /**
     * Mutator method for Crop object yield
     * 
     * @param cropYield: The new amount of Crop object grown (Must be >= 0) 
     */
    public void setYield(double cropYield){
        if(cropYield < 0){
            IllegalArgumentException iae = new IllegalArgumentException("Crop Yield must be no lower than zero.");
            throw iae;
        }
        yield = cropYield;
    }
    
    /**
     * Accessor method for Crop object Yield
     * 
     * @return: The amount of Crop object grown
     */
    public double getYield(){               
        return yield;
    }
    
    /**
     * Mutator method for Crop object price
     * 
     * @param cropPrice: New price for Crop object per kilogram (Must be >= $0) 
     */
    public void setPrice(double cropPrice){ 
        if(cropPrice < 0){
            IllegalArgumentException iae = new IllegalArgumentException("Crop Price must be larger than zero dollars.");
            throw iae;
        }
        price = cropPrice;
    }
    
    /**
     * Accessor method for Crop object price
     * 
     * @return: The price of Crop object per kilogram 
     */
    public double getPrice(){               
        return price;
    }
    
    /**
     * Equals method to compare the names of two Crop objects (Ignores capitalization)
     * 
     * @param cropToCheck: Crop object to compare to
     * @return: Boolean value, will return True if Crop object names are equal, False if not 
     */
    public boolean equals(Crop cropToCheck){       
        return name.equalsIgnoreCase(cropToCheck.getName());
    }
    
    /**
     * Equals method to compare the name of a Crop object with a String (Ignores capitalization)
     * 
     * @param stringToCheck: String to compare to
     * @return: Boolean value, will return True if Crop object name and String are equal, will return False if not 
     */
    public boolean equals(String stringToCheck){    
        return name.equalsIgnoreCase(stringToCheck);
    }
    
    /**
     * toString method to be used when displaying object to the screen.
     * 
     * @return: A String containing the three fields (Name, Yield, Price) with brief descriptive text 
     */
    public String toString(){                       
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        if(price < 0.01){
            return "Name of Crop = " + name + "\r\nCrop Yield = " + yield + "\r\nCrop Price (kg) = $" + price;
        }
        return "Name of Crop = " + name + "\r\nCrop Yield = " + yield + "\r\nCrop Price (kg) = " + currencyFormatter.format(price);
    }
}
