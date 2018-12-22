//Java imports
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;

//JUnit and Mockito imports
import org.junit.*;
import static org.junit.Assert.*;
import org.mockito.*;

/**
 *
 * This is the test class for Deliverable 4, CS1632 Fall 2017
 * Authors: Randyll Bearer (github: rlb97) and Branden Keck (github: BrandenKeck)
 *
 */

public class Del4Test {
	//Declare reusable element object
	Element _e;
	
	// Create a new Element instance before testing
    @Before
    public void setup() {
		_e = new Element();
    }
    
    // Initial test to ensure that the new Element instance isn't null
    @Test
    public void testElementExists() {
		assertNotNull(_e);
    }
	
	
	/**
	*
	BEGIN TESTS
	The twelve required software tests begin here...
	
	Note: the program returns empty array lists for invalid inputs
	any line that returns an empty list is handled and printed as "Could not create... etc."
	
	Note: the program logic is set up to attempt to build the string in the forward
	direction in steps of two, using a step of one if the next grouping of two doesn't exist.
	If this fails, then it is still possible that a permutation of element combinations
	was missed.  So, it is recursively attempted to apply this logic to the string in the
	backwards direction. Our unit tests are designed to ensure that strings for which
	the forward logic is needed and strings for which backwards logic is needed can both be built.
	*
	*/

	
	/*
	TEST 1:
	Ensure that a simple valid string returns a non-empty array list.
	This string ('Laboon') can be built in the forward direction using
	the getAbbreviations() step logic. (Check combination of two, then one)
	The returned array list is checked for the correct value
	*/
	
    @Test
    public void testSimpleInputValidForward() {
		boolean checkMe;
		ArrayList<String> t = new ArrayList<String>();
		ArrayList<String> test = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Mock the hash map values used in this case
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Simulate the getAbbreviations method
		t = _e.getAbbreviations("Laboon", hmap, true);
		
		// Create the test list to check against
		test.add("LA");
		test.add("B");
		test.add("O");
		test.add("O");
		test.add("N");
		
		// Ensure that the array is not empty and that it equals
		// the desired output.
		checkMe = t.isEmpty();
		assertFalse(checkMe);
		assertEquals(t, test);
    }
	
	
	/*
	TEST 2:
	Ensure that a simple valid string returns a non-empty array list.
	This string ('Creat') can be built in the backwards direction, but NOT
	the forward direction using the getAbbreviations() step logic
	(Check combination of two, then one).  This test checks that if the forward
	logic fails, then the backwards logic is applied. The returned array list is checked
	for the correct value.
	*/
	
    @Test
    public void testSimpleInputValidBackward() {
		boolean checkMe;
		ArrayList<String> t = new ArrayList<String>();
		ArrayList<String> test = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Mock the hash map values used in this case
		Mockito.when(hmap.get("C")).thenReturn("Carbon");
		Mockito.when(hmap.get("RE")).thenReturn("Rhenium");
		Mockito.when(hmap.get("AT")).thenReturn("Astatine");

		// Simulate the getAbbreviations method
		t = _e.getAbbreviations("Creat", hmap, true);
		
		// Create the test list to check against
		test.add("C");
		test.add("RE");
		test.add("AT");
		
		// Ensure that the array is not empty and that it equals
		// the desired output.
		checkMe = t.isEmpty();
		assertFalse(checkMe);
		assertEquals(t, test);
    }
	
	
	/*
	TEST 3:
	Ensure that a simple NON-VALID string returns an EMPTY array list.
	This string ('Bill') cannot be built using either the forward or backwards steps.
	*/
	
    @Test
    public void testSimpleInputNotValid() {
		boolean checkMe;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		//Mock of the hash map values that are valid in this case
		Mockito.when(hmap.get("BI")).thenReturn("Bismuth");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("I")).thenReturn("Iodine");
		
		// Testing of the function for an invalid input
		t = _e.getAbbreviations("Bill", hmap, true);
		
		// Ensure that an empty array is returned for this invalid input
		checkMe = t.isEmpty();
		assertTrue(checkMe);
    }
	
	
	/*
	TEST 4:
	Ensure that lines containing odd symbols and otherwise
	uncommon text return an empty array list.  In this test, the
	sample string "[%-!&*$]" is used.
	*/
	@Test
    public void testUncommonInput() {
		boolean checkMe;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Add some valid values to the hashmap
		Mockito.when(hmap.get("BI")).thenReturn("Bismuth");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("I")).thenReturn("Iodine");
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Testing of the function with an UNCOMMON string
		t = _e.getAbbreviations("[%-!&*$]", hmap, true);
		
		// Ensure that an empty array is returned for this invalid input
		checkMe = t.isEmpty();
		assertTrue(checkMe);
    }
	
	
	/*
	TEST 5:
	Ensure that if a file has no content, or a line is empty,
	that an empty array list is generated
	*/
	@Test
    public void testSimpleInputEmptyForward() {
		boolean checkMe;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Adding some values so the map is not empty
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Testing of the function with an EMPTY string
		t = _e.getAbbreviations("", hmap, true);
		
		// Ensure that an empty array is returned for this invalid input
		checkMe = t.isEmpty();
		assertTrue(checkMe);
    }
	
	
	/*
	TEST 6:
	Ensure that the program is case-insensitive by asserting that two identical
	inputs with different cases yield the same abbreviation
	*/
    @Test
    public void testNotCaseSensitive() {
		ArrayList<String> t1 = new ArrayList<String>();
		ArrayList<String> t2 = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Mocking the required hashmap
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Simulation using indentical strings with different cases
		t1 = _e.getAbbreviations("Laboon", hmap, true);
		t2 = _e.getAbbreviations("lAbOoN", hmap, true);
		
		// Assert that both strings return a value and that the values are equal
		assertFalse(t1.isEmpty());
		assertFalse(t2.isEmpty());
		assertEquals(t1, t2);
    }

	
	/*
	TEST 7:
	Given an appropriate list of element abbreviations, ensure
	that the correct string is built and returned from buildAbbreviationString()
	*/
	@Test
    public void testBuildAbbreviationsValid() {
		String testStr;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Adding some values to the array list
		t.add("LA");
		t.add("B");
		t.add("O");
		t.add("O");
		t.add("N");
		
		// Add the appropriate values to the hashmap
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Testing of the function with a valid array list and hashmap
		testStr = _e.buildAbbreviationString(t, hmap);
		
		// Ensure that the appropriate string is returned
		assertEquals(testStr, "La - B - O - O - N");
    }
	
	
	/*
	TEST 8:
	Given an appropriate list of element abbreviations, ensure
	that the correct string is built and returned from buildElementString()
	*/
	@Test
    public void testBuildElementsValid() {
		String testStr;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Adding some values to the array list
		t.add("LA");
		t.add("B");
		t.add("O");
		t.add("O");
		t.add("N");
		
		// Add the appropriate values to the hashmap
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Testing of the function with a valid array list and hashmap
		testStr = _e.buildElementString(t, hmap);
		
		// Ensure that the appropriate string is returned
		assertEquals(testStr, "\nLanthanum - Boron - Oxygen - Oxygen - Nitrogen");
    }
	
	
	/*
	TEST 9:
	Given an INVALID list of element abbreviations, ensure
	that buildAbbreviationString() returns an empty string.
	Using invalid string "Bill".
	*/
	@Test
    public void testBuildAbbreviationsNotValid() {
		String testStr;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Adding some values to the array list
		t.add("B");
		t.add("I");
		t.add("L");
		t.add("L");
		
		// Add the appropriate values to the hashmap
		Mockito.when(hmap.get("BI")).thenReturn("Bismuth");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("I")).thenReturn("Iodine");
		
		// Testing of the function with an INVALID array list and hashmap
		testStr = _e.buildAbbreviationString(t, hmap);
		
		// Ensure that an empty string is returned for this invalid input
		assertEquals(testStr, "");
    }
	
	
	/*
	TEST 10:
	Given an INVALID list of element abbreviations, ensure
	that buildElementString() also returns an empty string.
	Using invalid string "Bill".
	*/
	@Test
    public void testBuildElementsNotValid() {
		String testStr;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Adding some values to the array list
		t.add("B");
		t.add("I");
		t.add("L");
		t.add("L");
		
		// Add the appropriate values to the hashmap
		Mockito.when(hmap.get("BI")).thenReturn("Bismuth");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("I")).thenReturn("Iodine");
		
		// Testing of the function with an INVALID array list
		testStr = _e.buildElementString(t, hmap);
		
		// Ensure that an empty array is returned for this invalid input
		assertEquals(testStr, "");
    }
	
	
	/*
	TEST 11:
	Given an EMPTY list of element abbreviations, ensure
	that buildAbbreviationString() returns an empty string.
	*/
	@Test
    public void testBuildAbbreviationsEmpty() {
		String testStr;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Add some valid values to the hashmap
		Mockito.when(hmap.get("BI")).thenReturn("Bismuth");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("I")).thenReturn("Iodine");
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Testing of the function with an EMPTY array list
		t.clear();
		testStr = _e.buildAbbreviationString(t, hmap);
		
		// Ensure that an empty string is returned for this invalid input
		assertEquals(testStr, "");
    }
	
	
	/*
	TEST 12:
	Given an EMPTY list of element abbreviations, ensure
	that buildElementString() also returns an empty string.
	*/
	@Test
    public void testBuildElementsEmpty() {
		String testStr;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Add some valid values to the hashmap
		Mockito.when(hmap.get("BI")).thenReturn("Bismuth");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("I")).thenReturn("Iodine");
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Testing of the function with an EMPTY array list
		t.clear();
		testStr = _e.buildElementString(t, hmap);
		
		// Ensure that an empty array is returned for this invalid input
		assertEquals(testStr, "");
    }
}