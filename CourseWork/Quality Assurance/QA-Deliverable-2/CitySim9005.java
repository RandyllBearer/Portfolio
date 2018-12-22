import java.util.ArrayList;
import java.util.Random;


public class CitySim9005{
	
	/**
	* Called only by the Main Method.
	* Initializes all Street, Location, and City objects for use in the simulation.
	* Initializes these objects according to the requirements found at
	* https://github.com/laboon/CS1632_Fall2017/blob/master/deliverables/2/requirements.md
	**/
	private static City initializeCity(){
		//Create City (Graph)
		City city = new City();
		city.setName("Pittsburgh");
		
		//Create Locations (Nodes)
		Location hotel = new Location();	//hotel
		hotel.setCity(city);
		hotel.setName("Hotel");
		city.addLocation(hotel);
		
		Location diner = new Location();	//diner
		diner.setCity(city);
		diner.setName("Diner");
		city.addLocation(diner);
		
		Location library = new Location();	//library
		library.setCity(city);
		library.setName("Library");
		city.addLocation(library);
		
		Location coffee = new Location();	//coffee
		coffee.setCity(city);
		coffee.setName("Coffee");
		city.addLocation(coffee);
		
		Location outsideCity= new Location();	//outside city, make sure this is the last Location in the ArrayList
		outsideCity.setName("Outside City");
		city.addLocation(outsideCity);
		
		//Create Streets (Edges)
		Street outsideCityHotel = new Street(city, "Fourth Ave.", outsideCity, hotel);
		city.addStreet(outsideCityHotel);
		
		Street hotelDiner = new Street(city, "Fourth Ave.", hotel, diner);
		city.addStreet(hotelDiner);
		hotel.addReachableStreet(hotelDiner);
		
		Street hotelLibrary = new Street(city, "Bill St.", hotel, library);
		city.addStreet(hotelLibrary);
		hotel.addReachableStreet(hotelLibrary);
		
		Street dinerOutsideCity = new Street(city, "Fourth Ave.", diner, outsideCity);
		city.addStreet(dinerOutsideCity);
		diner.addReachableStreet(dinerOutsideCity);
		
		Street dinerCoffee = new Street(city, "Phil St.", diner, coffee);
		city.addStreet(dinerCoffee);
		diner.addReachableStreet(dinerCoffee);
		
		Street outsideCityCoffee = new Street(city, "Fifth Ave.", outsideCity, coffee);
		city.addStreet(outsideCityCoffee);
		
		Street coffeeDiner = new Street(city, "Phil St.", coffee, diner);
		city.addStreet(coffeeDiner);
		coffee.addReachableStreet(coffeeDiner);
		
		Street coffeeLibrary = new Street(city, "Fifth Ave.", coffee, library);
		city.addStreet(coffeeLibrary);
		coffee.addReachableStreet(coffeeLibrary);
		
		Street libraryHotel = new Street(city, "Bill St.", library, hotel);
		city.addStreet(libraryHotel);
		library.addReachableStreet(libraryHotel);
		
		Street libraryOutsideCity = new Street(city, "Fifth Ave.", library, outsideCity);
		city.addStreet(libraryOutsideCity);
		library.addReachableStreet(libraryOutsideCity);
		
		return city;
	}
	
	/**
	* Main Driver for the CitySim9005 program. Will Create a City object, add Street and Location objects
	* to that city, then simulate 5 Driver objects moving about the city.
	* Abstracted into a graph problem. Locations are nodes, Streets are one-way edges.
	**/
	public static void main(String[] args){
		//Check Arguments + Create RNG(Seed) + Initialize City (Graph)
		int seed = 0;
		if(args.length != 1){
			System.out.println("ERROR: CitySim9005.java only accepts a single integer argument, program will now exit...");
			System.exit(0);
		}
		try{
			seed = Integer.parseInt(args[0]);
		}catch(NumberFormatException nfe){
			System.out.println("ERROR: CitySim9005.java only accepts a single integer argument, program will now exit...");
		}
		Random rng = new Random(seed);
	
		City mainCity = initializeCity();
		
		//Simulate Drivers
		int numLocations = mainCity.getNumLocations();
		int i = 1;
		while(i < 6){	//Iterate through Drivers
			Driver driver = new Driver();
			driver.setName("Driver "+ i);
			int startingLocation = rng.nextInt(mainCity.getNumLocations()-1);
			driver.setCurrentLocation(mainCity.getLocations().get(startingLocation));
			
			while(!driver.getCurrentLocation().getName().equals("Outside City")){	//Iterate through Streets
				ArrayList<Street> reachableStreets = driver.getCurrentLocation().getReachableStreets();
				int numReachable = driver.getCurrentLocation().getNumReachableStreets();
				int newStreet = rng.nextInt(numReachable);	//0[inclusive] - numReachable[exclusive]
				System.out.println(driver.getName() + " heading from " + driver.getCurrentLocation().getName() + " to " + driver.getCurrentLocation().getReachableStreets().get(newStreet).getEndLocation().getName() + " via " + driver.getCurrentLocation().getReachableStreets().get(newStreet).getName() );
				
				if(driver.getCurrentLocation().getReachableStreets().get(newStreet).getEndLocation().getName().equals("Outside City")	){		//if driver is leaving city, which city are they going to?
					if(driver.getCurrentLocation().getName().equals("Diner")	){	//Going to Philadelphia
						System.out.println(driver.getName() + " has gone to Philadelphia!");
					}else if(driver.getCurrentLocation().getName().equals("Library")){	//Going to Cleveland
						System.out.println(driver.getName() + " has gone to Cleveland!");
					}
				}
				
				driver.setCurrentLocation(driver.getCurrentLocation().getReachableStreets().get(newStreet).getEndLocation()	);
			}
			
			System.out.println("-----");
			i = i + 1;
		}
		
		System.exit(0);
	}
	
}