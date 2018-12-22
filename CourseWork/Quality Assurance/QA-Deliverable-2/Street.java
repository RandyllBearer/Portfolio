import java.util.ArrayList;

//Street objects act as one-way edges in this program.
public class Street{
	City city;
	String name;
	Location startLocation;
	Location endLocation;
	
	//Default Constructor
	public Street(){
		
	}
	
	public Street(City newCity, String newName, Location newStartLocation, Location newEndLocation){
		this.setCity(newCity);
		this.setName(newName);
		this.setStartLocation(newStartLocation);
		this.setEndLocation(newEndLocation);
	}
	
	public boolean setCity(City desiredCity){
		if(desiredCity != null){
			city = desiredCity;
			return true;
		}else{
			System.out.println("ERROR: Cannot make a street's city's name NULL");
			return false;
		}
	}
	
	public City getCity(){
		return city;
	}
	
	public boolean setName(String streetName){
		if(streetName != null){
			name = streetName;
			return true;
		}else{
			System.out.println("ERROR: Cannot make a street's name NULL");
			return false;
		}
	}
	
	public String getName(){
		return name;
	}
	
	public boolean setStartLocation(Location desiredStartLocation){
		if(desiredStartLocation != null){
			startLocation = desiredStartLocation;
			return true;
		}else{
			System.out.println("ERROR: Cannot start a street at a NULL location");
			return false;
		}
	}
	
	public Location getStartLocation(){
		return startLocation;
	}
	
	public boolean setEndLocation(Location desiredEndLocation){
		if(desiredEndLocation != null){
			endLocation = desiredEndLocation;
			return true;
		}else{
			System.out.println("ERROR: Cannot end a street at a NULL location");
			return false;
		}
	}
	
	public Location getEndLocation(){
		return endLocation;
	}
	
}