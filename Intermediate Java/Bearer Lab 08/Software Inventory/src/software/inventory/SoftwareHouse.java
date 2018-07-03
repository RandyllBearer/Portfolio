/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.inventory;

/**
 * The SoftwareHouse class.
 * This stores all of the information relevant for representing companies.
 * 
 * @author Michael Lipschultz
 */
public class SoftwareHouse 
{
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    
    /**
     * No-args constructor
     */
    public SoftwareHouse()
    {
        setName("");
        setStreet("");
        setCity("");
        setState("");
        setZipcode("00000");
    }
    
    /**
     * Initialize the fields
     * @param name Company name
     * @param street Street address
     * @param city City company is located in
     * @param state State company is located in
     * @param zip Zip code for the company
     */
    public SoftwareHouse(String name, String street, String city, String state, String zip)
    {
        setName(name);
        setStreet(street);
        setCity(city);
        setState(state);
        setZipcode(zip);
    }
    
    /**
     * The copy constructor
     * @param company The object to copy.
     */
    public SoftwareHouse(SoftwareHouse company)
    {
        setName(company.getName());
        setStreet(company.getStreet());
        setCity(company.getCity());
        setState(company.getState());
        setZipcode(company.getZipcode());
    }
    
    /**
     * Mutator method for the company's name.
     * @param n Name of the company
     */
    public void setName(String n)
    {
        name = n;
    }
    
    /**
     * Mutator method for the company's street address.
     * @param s Street address of the company
     */
    public void setStreet(String s)
    {
        street = s;
    }
    
    /**
     * Mutator method for the city the company is located in.
     * @param c The city
     */
    public void setCity(String c)
    {
        city = c;
    }
    
    /**
     * Mutator method for the state the company is located in.
     * @param s The state
     */
    public void setState(String s)
    {
        state = s;
    }
    
    /**
     * Mutator method for the company's zip code.
     * @param z The zip code
     * @return Returns true if the zip code is at least 5 characters long and false otherwise.  If false is returned, then setting the zipcode failed.
     */
    public boolean setZipcode(String zipcode)
    {
        if (zipcode.length() < 5) return false;
        zip = zipcode;
        return true;
    }
    
    /**
     * Accessor method for the company's name.
     * @return The name of the company.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Accessor method for the company's street address.
     * @return The street address of the company.
     */
    public String getStreet()
    {
        return street;
    }
    
    /**
     * Accessor method for the city the company is based in.
     * @return The city the company is based in.
     */
    public String getCity()
    {
        return city;
    }
    
    /**
     * Accessor method for the state the company is based in.
     * @return The state the company is based in.
     */
    public String getState()
    {
        return state;
    }
    
    /**
     * Accessor method for the company's zip code.
     * @return The zip code of the company.
     */
    public String getZipcode()
    {
        return street;
    }
    
    /**
     * Get the complete company address.
     * @return The company's full address
     */
    public String getAddress()
    {
        return street+"\n"+city+", "+state+" "+zip;
    }
    
    /**
     * toString method.
     * @return A string containing the company information.
     */
    
    public String toString()
    {
        String str = "Company Name: "+name+
                    "\nCompany Address: "+getAddress();
        return str;
    }
}

