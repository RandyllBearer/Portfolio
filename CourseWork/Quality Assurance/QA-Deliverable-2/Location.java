import java.util.ArrayList;

//Location objects acts as Nodes in this program.
public class Location{
	City city;
	String name;
	int numReachableStreets;
	ArrayList<Street> reachableStreets = new ArrayList<Street>();
	
	public boolean setCity(City desiredCity){
		if(desiredCity != null){
			city = desiredCity;
			return true;
		}else{
			System.out.println("ERROR: Cannot set a location's city's name to NULL");
			return false;
		}
	}
	
	public City getCity(){
		return city;
	}
	
	public boolean setName(String desiredName){
		if(desiredName != null){
			name = desiredName;
			return true;
		}else{
			System.out.println("ERROR: Cannot name a location NULL");
			return false;
		}
	}
	
	public String getName(){
		return name;
	}
	
	//A "ReachableStreet" is a one-way edge which originates from this node to some other node.
	public boolean addReachableStreet(Street newStreet){
		if(newStreet != null){
			reachableStreets.add(newStreet);
			numReachableStreets = numReachableStreets + 1;
			return true;
		}else{
			System.out.println("ERROR: Cannot add a NULL street");
			return false;
		}
	}
	
	
	public boolean removeReachableStreet(Street streetToRemove){
		if(streetToRemove != null){
			reachableStreets.remove(streetToRemove);
			numReachableStreets = numReachableStreets - 1;
			return true;
		}else{
			System.out.println("ERROR: Cannot remove a NULL street from a location's reachable streets");
			return false;
		}
	}
	
	public ArrayList<Street> getReachableStreets(){
		return reachableStreets;
	}
	
	public int getNumReachableStreets(){
		return numReachableStreets;
	}
	
}