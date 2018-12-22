//Driver objects traverse through the City graph in this program, from Location to Location.
public class Driver{
	String name;
	Location currentLocation;
	
	public boolean setName(String desiredName){
		if(desiredName != null){
			name = desiredName;
			return true;
		}else{
			System.out.println("ERROR: Cannot make a driver's name NULL");
			return false;
		}
	}
	
	public String getName(){
		return name;
	}
	
	public boolean setCurrentLocation(Location newCurrentLocation){
		if(newCurrentLocation != null){
			currentLocation = newCurrentLocation;
			return true;
		}else{
			System.out.println("ERROR: Cannot place a driver at a NULL location");
			return false;
		}
	}
	
	public Location getCurrentLocation(){
		return currentLocation;
	}
	
}