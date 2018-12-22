import java.util.ArrayList;

//The City acts as the graph in this program. Contains Locations (Nodes) and Streets (Edges).
public class City{
	String name;
	int numLocations;
	int numStreets;
	int numNearbyCities;
	ArrayList<Location> locations = new ArrayList<Location>();
	ArrayList<Street> streets = new ArrayList<Street>();
	ArrayList<City> nearbyCities = new ArrayList<City>();

	public boolean setName(String desiredName){
		if(desiredName != null){
			name = desiredName;
			return true;
		}else{
			System.out.println("ERROR: Cannot make a city's name NULL");
			return false;
		}
	}
	
	public String getName(){
		return name;
	}
	
	public boolean addLocation(Location locationToAdd){
		if(locationToAdd != null){
			locations.add(locationToAdd);
			numLocations = numLocations + 1;
			return true;
		}else{
			System.out.println("ERROR: Cannot add a NULL location to a city");
			return false;
		}
	}
	
	public boolean removeLocation(Location locationToRemove){
		if(locationToRemove != null){
			locations.remove(locationToRemove);
			numLocations = numLocations - 1;
			return true;
		}else{
			System.out.println("ERROR: Cannot remove a NULL location from a city");
			return false;
		}
	}
	
	public ArrayList<Location> getLocations(){
		return locations;
	}
	
	public int getNumLocations(){
		return numLocations;
	}
	
	public boolean addStreet(Street streetToAdd){
		if(streetToAdd != null){
			streets.add(streetToAdd);
			numStreets = numStreets + 1;
			return true;
		}else{
			System.out.println("ERROR: Cannot add a NULL street to a city");
			return false;
		}
	}
	
	public boolean removeStreet(Street streetToRemove){
		if(streetToRemove != null){
			streets.remove(streetToRemove);
			numStreets = numStreets - 1;
			return true;
		}else{
			System.out.println("ERROR: Cannot remove a NULL street from a city");
			return false;
		}
	}
	
	public ArrayList<Street> getStreets(){
		return streets;
	}
	
	public int getNumStreets(){
		return numStreets;
	}
	
	public boolean addNearbyCity(City cityToAdd){
		if(cityToAdd != null){
			nearbyCities.add(cityToAdd);
			numNearbyCities = numNearbyCities + 1;
			return true;
		}else{
			System.out.println("ERROR: Cannot add a NULL nearby city");
			return false;
		}
	}
	
	public boolean removeNearbyCity(City cityToRemove){
		if(cityToRemove != null){
			nearbyCities.remove(cityToRemove);
			numNearbyCities = numNearbyCities - 1;
			return true;
		}else{
			System.out.println("ERROR: Cannot remove a NULL nearby city");
			return false;
		}
	}
	
	public ArrayList<City> getNearbyCities(){
		return nearbyCities;
	}
	
	public int getNumNearbyCities(){
		return numNearbyCities;
	}
	

	
}