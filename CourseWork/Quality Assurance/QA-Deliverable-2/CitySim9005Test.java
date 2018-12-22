//Modified from LaboonCoinTest.java from Exercise2 provided at
//https://github.com/laboon/CS1632_Fall2017/tree/master/exercises/2/LaboonCoin

import static org.junit.Assert.*;
import org.junit.*;
import java.util.ArrayList;
import static org.mockito.Mockito.*;

public class CitySim9005Test{
	
	//Re-usable CitySim9005, Location, Street, and Driver references.
	CitySim9005 _CitySim9005;
	City _City;
	Location _Location;
	Street _Street;
	Driver _Driver;
	
	//Create class instances of CitySim9005, City, Location, Street, and Driver
	@Before
	public void setup(){
		_CitySim9005 = new CitySim9005();
		_City = new City();
		_Location = new Location();
		_Street = new Street();
		_Driver = new Driver();
	}
	
	//---------------CREATION TESTS------------------
	
	//Assert that creating a new CitySim9005 instance does not return a null reference.
	@Test
	public void testCitySim9005Exists(){
		assertNotNull(_CitySim9005);
	}
	
	//Assert that creating a new City instance does not return a null reference.
	@Test
	public void testCityExists(){
		assertNotNull(_City);
	}
	
	//Assert that creating a new Location instance does not return a null reference.
	@Test
	public void testLocationExists(){
		assertNotNull(_Location);
	}
	
	//Assert that creating a new Street instance does not return a null reference.
	@Test
	public void testStreetExists(){
		assertNotNull(_Street);
	}
	
	//Assert that creating a new Driver instance does not return a null reference.
	@Test
	public void testDriverExists(){
		assertNotNull(_Driver);
	}
	
	//----------------City.java TESTS------------------
	
	//Assert that whenever you try to set a City's name to NULL, City.setName() 
	//will return FALSE.
	@Test
	public void testCitySetNameNull(){
		String newName = null;
		assertFalse(_City.setName(newName) );
	}
	
	//Assert that whenever you try to set a City's name to some non-null value,
	//City.setName() will return TRUE.
	@Test
	public void testCitySetNameNotNull(){
		String newName = "New City";
		assertTrue(_City.setName(newName));
	}
	
	//Assert that City.getName() returns the correct name of the City object.
	@Test
	public void testCityGetNameAccurate(){
		_City.name = "Test City";
		String result = _City.getName();
		assertEquals(result, "Test City");
	}
	
	//Assert that City.addLocation() properly updates its int City.numLocations field.
	//This increment should occur whenever a Location object is added to the City Object's
	//ArrayList<Location> locations field.
	//Implements a Mockito FAKE #1
	@Test
	public void testCityAddLocationIncrement(){
		_City.addLocation(mock(Location.class) );
		assertEquals(1, _City.numLocations);
	}
	
	//Assert that whenever you try to add a null Location object to a City's
	//ArrayList<Location> locations that City.addLocation() returns FALSE;
	@Test
	public void testCityAddLocationNull(){
		Location nullLocation = null;
		assertFalse(_City.addLocation(nullLocation) );
	}
	
	//Assert that whenever you try to add a non-null Location object to a City's
	//ArrayList<Location> locations that City.addLocation() returns TRUE;
	@Test
	public void testCityAddLocationNotNull(){
		assertTrue(_City.addLocation(_Location) );
	}
	
	//Assert that whenever you try to remove a null Location object from a City's
	//ArrayList<Location> locations that City.removeLocation() returns FALSE;
	@Test
	public void testCityRemoveLocationNull(){
		_City.locations.add(_Location);
		Location nullLocation = null;
		assertFalse(_City.removeLocation(nullLocation) );
		
	}
	
	//Assert that whenever you try to remove a non-null Location object from a City's
	//ArrayList<Location> locations that City.removeLocation() returns TRUE;
	@Test
	public void testCityRemoveLocationNotNull(){
		_City.locations.add(_Location);
		assertTrue(_City.removeLocation(_Location) );
	}
	
	//Assert that City.getLocations() returns an ArrayList<Location> with the same members
	//that were added to the ArrayList<Location> directly.
	@Test
	public void testCityGetLocations(){
		ArrayList<Location> toCompareAgainst = new ArrayList<Location>();
		Location _Location2 = new Location();
		Location _Location3 = new Location();
		toCompareAgainst.add(_Location);
		toCompareAgainst.add(_Location2);
		toCompareAgainst.add(_Location3);
		_City.addLocation(_Location);
		_City.addLocation(_Location2);
		_City.addLocation(_Location3);
		assertEquals(toCompareAgainst, _City.getLocations());
	}
	
	//Assert that City.getNumLocations() accurately returns the correct int amount
	//of locations in the City.numLocations field.
	@Test
	public void testCityGetNumLocationsAccurate(){
		_City.numLocations = 12345;
		assertEquals(12345, _City.getNumLocations());
	}
	
	//Assert that City.addNearbyCity() properly updates its int City.numNearbyCities field.
	//This increment should occur whenever a City object is added to the City Object's
	//ArrayList<City> nearbyCities field.
	//Implements a Mockito FAKE #2
	@Test
	public void testCityAddNearbyCityIncrement(){
		_City.addNearbyCity(mock(City.class));
		assertEquals(1, _City.getNumNearbyCities());
	}
	
	//Assert that whenever you try to add a null City object to a City's
	//ArrayList<City> nearbyCities that City.addNearbyCity() returns FALSE.
	@Test
	public void testCityAddNearbyCityNull(){
		City nullCity = null;
		assertFalse(_City.addNearbyCity(nullCity) );
	}
	
	//Assert that whenever you try to add a non-null City object to a City's
	//ArrayList<City> nearbyCities that City.addNearbyCity() returns TRUE.
	@Test
	public void testCityAddNearbyCityNotNull(){
		City _City2 = new City();
		assertTrue(_City.addNearbyCity(_City2));
	}
	
	//Assert that whenever you try to remove a null City object from a City's
	//ArrayList<City> nearbyCities that City.removeNearbyCity() returns FALSE.
	@Test
	public void testCityRemoveNearbyCityNull(){
		City nullCity = null;
		City _City2 = new City();
		_City.nearbyCities.add(_City2);
		assertFalse(_City.removeNearbyCity(nullCity));
	}
	
	//Assert that whenever you try to remove a non-null City object from a City's
	//ArrayList<City> nearbyCities that City.removeNearbyCity() returns TRUE.
	@Test
	public void testCityRemoveNearbyCityNotNull(){
		City _City2 = new City();
		_City.nearbyCities.add(_City2);
		assertTrue(_City.removeNearbyCity(_City2));
	}
	
	//Assert that City.getNumNearbyCities() returns an integer equal to the
	//City object's numNearbyCities field.
	@Test
	public void testCityGetNumNearbyCitiesAccurate(){
		_City.numNearbyCities = 200;
		assertEquals(200, _City.getNumNearbyCities());
	}
	
	//Assert that City.getNearbyCities returns an ArrayList<City> that is equal
	//to the ArrayList<City> nearbyCities field in the City object.
	@Test
	public void testCityGetNearbyCitiesAccurate(){
		ArrayList<City> testList = new ArrayList<City>();
		City _City2 = new City();
		City _City3 = new City();
		City _City4 = new City();
		testList.add(_City2);
		testList.add(_City3);
		testList.add(_City4);
		_City.nearbyCities = testList;
		assertEquals(testList, _City.getNearbyCities());
	}
	
	//--------------Location.java----------------
	
	//Assert that Location.setCity() will return FALSE whenever it is passed 
	//a null City object.
	@Test
	public void testLocationSetCityNull(){
		City nullCity = null;
		assertFalse(_Location.setCity(nullCity));
	}
	
	//Assert that Location.setCity() will return TRUE whenever it is passed
	//a non-null City object.
	@Test
	public void testLocationSetCityNotNull(){
		assertTrue(_Location.setCity(_City));
	}
	
	//Assert that Location.getCity() returns a City object which is equal
	//to the City object in the Location's city field.
	@Test
	public void testLocationGetCityAccurate(){
		_Location.setCity(_City);
		assertEquals(_City, _Location.getCity());
	}
	
	//Assert that Location.setName() returns FALSE whenever it is passed
	//a null String object.
	@Test
	public void testLocationSetNameNull(){
		String nullString = null;
		assertFalse(_Location.setName(nullString));
	}
	
	//Assert that Location.setName() returns TRUE whenever it is passed
	//a non-null String object.
	@Test
	public void testLocationSetNameNotNull(){
		assertTrue(_Location.setName("Test String Please Ignore"));
	}
	
	//Assert that Location.getName() returns a String which is equal
	//to the String object in the Location's name field.
	@Test
	public void testLocationGetNameAccurate(){
		String testString = "Test String";
		_Location.name = testString;
		assertEquals(testString, _Location.getName());
	}
	
	//Assert that Location.addReachableStreet() properly updates its int Location.numNearbyCities field.
	//This increment should occur whenever a Street object is added to the Location object's
	//ArrayList<Street> reachableStreets field.
	//Implements a Mockito FAKE #3
	@Test
	public void testLocationAddReachableStreetIncrement(){
		_Location.addReachableStreet(mock(Street.class));
		assertEquals(1, _Location.getNumReachableStreets());
	}
	
	//Assert that Location.addReachableStreet() returns FALSE whenever
	//it is passed a null Street object.
	@Test
	public void testLocationAddReachableStreetNull(){
		Street nullStreet = null;
		assertFalse(_Location.addReachableStreet(nullStreet));
	}
	
	//Assert that Location.addReachableStreet() returns TRUE whenever
	//it is passed a non-null Street object.
	@Test
	public void testLocationAddReachableStreetNotNull(){
		assertTrue(_Location.addReachableStreet(_Street));
	}
	
	//Assert that Location.removeReachableStreet() returns FALSE
	//whenever it is passed a null Street object.
	@Test
	public void testLocationRemoveReachableStreetNull(){
		Street nullStreet = null;
		_Location.reachableStreets.add(_Street);
		assertFalse(_Location.removeReachableStreet(nullStreet));
	}
	
	//Assert that Location.removeReachableStreet() returns TRUE
	//whenever it is passed a non-null Street object.
	@Test
	public void testLocationRemoveReachableStreetNotNull(){
		_Location.reachableStreets.add(_Street);
		assertTrue(_Location.removeReachableStreet(_Street));
	}
	
	//Assert that Location.getReachableStreets() returns an
	//ArrayList<Street> which is equal to the Location object's
	//reachableStreets field.
	@Test
	public void testLocationGetReachableStreetsAccurate(){
		ArrayList<Street> testList = new ArrayList<Street>();
		Street _Street2 = new Street();
		Street _Street3 = new Street();
		testList.add(_Street);
		testList.add(_Street2);
		testList.add(_Street3);
		_Location.reachableStreets = testList;
		assertEquals(testList, _Location.getReachableStreets());
		
	}
	
	//Assert that Location.getNumReachableStreets() returns an int
	//that is equal to the Location object's numReachableStreets field.
	@Test
	public void testLocationGetNumReachableStreetsAccurate(){
		int testInt = 12345;
		_Location.numReachableStreets = testInt;
		assertEquals(testInt, _Location.getNumReachableStreets());
	}
	
	//-------------Street.java-------------------
	
	//Assert that Street.setCity() returns FALSE whenever it is passed
	//a null City object.
	@Test
	public void testStreetSetCityNull(){
		City nullCity = null;
		assertFalse(_Street.setCity(nullCity));
	}
	
	//Assert that Street.setCity() returns TRUE whenever it is passed
	//a non-null City object.
	@Test
	public void testStreetSetCityNotNull(){
		assertTrue(_Street.setCity(_City));
	}
	
	//Assert that Street.getCity() returns a City object which is equal
	//to the Street object's city field.
	@Test
	public void testStreetGetCityAccurate(){
		_Street.city = _City;
		assertEquals(_City, _Street.getCity() );
	}
	
	//Assert that Street.setName() returns FALSE whenever it is passed
	//a null String object.
	@Test
	public void testStreetSetNameNull(){
		String nullString = null;
		assertFalse(_Street.setName(nullString));
	}
	
	//Assert that Street.setName() returns TRUE whenever it is passed
	//a non-null String object.
	@Test
	public void testStreetSetNameNotNull(){
		assertTrue(_Street.setName("Test Name"));
	}
	
	//Assert that Street.getName() returns a String object which is equal
	//to the Street object's name field.
	@Test
	public void testStreetGetNameAccurate(){
		_Street.name = "Test Street Please Ignore";
		assertEquals("Test Street Please Ignore", _Street.getName());
	}
	
	//Assert that Street.setStartLocation() returns FALSE whenever it is passed
	//a null Location object.
	@Test
	public void testStreetSetStartLocationNull(){
		Location nullLocation = null;
		assertFalse(_Street.setStartLocation(nullLocation));
	}
	
	//Assert that Street.setStartLocation() returns TRUE whenever it is passed
	//a non-null Location object.
	@Test
	public void testStreetSetStartLocationNotNull(){
		assertTrue(_Street.setStartLocation(_Location));
	}
	
	//Assert that Street.getStartLocation() returns a Location object which is
	//equal to the Street object's startLocation field.
	@Test
	public void testStreetGetStartLocationAccurate(){
		_Street.startLocation = _Location;
		assertEquals(_Location, _Street.getStartLocation());
	}
	
	//Assert that Street.setEndLocation() returns FALSE whenever it is passed
	//a null Location object.
	@Test
	public void testStreetSetEndLocationNull(){
		Location nullLocation = null;
		assertFalse(_Street.setEndLocation(nullLocation));
	}
	
	//Assert that Street.setEndLocation() returns TRUE whenever it is passed
	//a non-null Location object.
	@Test
	public void testStreetSetEndLocationNotNull(){
		assertTrue(_Street.setEndLocation(_Location));
	}
	
	//Assert that Street.getEndLocation() returns a Location object which is
	//equal to the Street object's endLocation field.
	//Implements Mockito Method Stub #1
	@Test
	public void testStreetGetEndLocationAccurate(){
		_Street.endLocation = _Location;
		assertEquals(_Location, _Street.getEndLocation());
	}
	
	//-------------------Driver.java-----------------------
	
	//Assert that Driver.setName() returns FALSE whenever it is passed
	//a null String object.
	@Test
	public void testDriverSetNameNull(){
		String nullString = null;
		assertFalse(_Driver.setName(nullString));
		
	}
	
	//Assert that Driver.setName() returns TRUE whenever it is passed 
	//a non-null String object.
	@Test
	public void testDriverSetNameNotNull(){
		assertTrue(_Driver.setName("Test Name"));
	}
	
	//Assert that Driver.getName() returns a String object which is equal
	//to the Driver object's name field.
	@Test
	public void testDriverGetNameAccurate(){
		_Driver.name = "Test Name";
		assertEquals("Test Name", _Driver.getName());
	}
	
	//Assert that Driver.setCurrentLocation() returns FALSE whenever
	//it is passed a null Location object.
	@Test
	public void testDriverSetCurrentLocationNull(){
		Location nullLocation = null;
		assertFalse(_Driver.setCurrentLocation(nullLocation));
	}
	
	//Assert that Driver.setCurrentLocation() returns TRUE whenever
	//it is passed a non-null Location object.
	@Test
	public void testDriverSetCurrentLocationNotNull(){
		assertTrue(_Driver.setCurrentLocation(_Location));
	}
	
	//Assert that Driver.getCurrentLocation() returns a Location object
	//which is equal to the Driver object's currentLocation field.
	@Test
	public void testDriverGetCurrentLocationAccurate(){
		_Driver.currentLocation = _Location;
		assertEquals(_Location, _Driver.getCurrentLocation());
	}
	
	//--------------UnitTests with Method Stubbing------------------
	/**
	* The following three unit tests can be easily rewritten without using method
	* stubbing. However, the project details require me to provide 3 examples of
	* method stubbing to prove that I understand the concept/execution. Unfortunately,
	* due to how my program is structured, I cannot really utilize method stubbing in
	* any advantageous way as none of my methods rely on other methods. With that said,
	* here are 3 examples.
	**/
	
	//Assert that City.addLocation, when passed a Location Object derived from a Street object
	//through Street.getStartLocation(), is able to properly add that Location to the City object's
	//ArrayList<Location> locations field.
	@Test
	public void testCityAddLocationMethodStub(){
		Street mockStreet = mock(Street.class);
		when(mockStreet.getStartLocation()).thenReturn(new Location());
		assertTrue(_City.addLocation(mockStreet.getStartLocation()) );
	}
	
	//Assert that Street.setStartLocation(), when passed a Location Object derived from a Driver object
	//through Driver.getCurrentLocation(), is able to properly add that Location to the Street object's
	//startLocation field.
	@Test
	public void testStreetSetStartLocationMethodStub(){
		Driver mockDriver = mock(Driver.class);
		when(mockDriver.getCurrentLocation()).thenReturn(new Location());
		assertTrue(_Street.setStartLocation(mockDriver.getCurrentLocation()));
	}
	
	//Assert that Street.setEndLocation() will return false when passed a derivative null Location
	//object from Driver.getCurrentLocation().
	@Test
	public void testStreetsSetEndLocationMethodStub(){
		Driver mockDriver = mock(Driver.class);
		when(mockDriver.getCurrentLocation()).thenReturn(null);
		assertFalse(_Street.setEndLocation(mockDriver.getCurrentLocation()));
	}
	
}