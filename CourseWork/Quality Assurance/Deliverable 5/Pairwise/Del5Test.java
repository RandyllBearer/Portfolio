//Java imports
import java.lang.*;
import java.util.ArrayList;

//JUnit and Mockito imports
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * This is the test class for Deliverable 5, CS1632 Fall 2017
 * Authors: Randyll Bearer (github: rlb97) and Branden Keck (github: BrandenKeck)
 *
 */

public class Del5Test {
	//Declare reusable element object
	Pairwise _p;
	
	// Create a new Element instance before testing
    @Before
    public void setup() {
		_p = new Pairwise();
    }
    
    // Initial test to ensure that the new Element instance isn't null
    @Test
    public void testElementExists() {
		assertNotNull(_p);
    }
	
	
	//BEGIN TESTS

	//-------- parseInput(String[] arguments) -------
	
	/*
	Assert that a valid input of two arguments is correctly parsed and returned 
	as an array list.
	*/
    @Test
    public void testInputsValid() {
		ArrayList<String> test1 = new ArrayList<String>();
		ArrayList<String> test2 = new ArrayList<String>();
		
		String[] args = new String[2];
		args[0] = "Bill";
		args[1] = "Laboon";
		
		test1 = _p.parseInput(args);
		
		test2.add("Bill");
		test2.add("Laboon");

		assertEquals(test1, test2);
    }
	
	
	/*
	Assert that an INVALID input returns an empty list, which will be later
	handled as an error.
	An input of only one argument is invalid.  For this test we will use an
	input array of only one element, "Bill".
	*/
    @Test
    public void testInputsTooFew() {
		//Create an array list for the test
		ArrayList<String> test1 = new ArrayList<String>();
		
		//
		String[] args = new String[1];
		args[0] = "Bill";
		
		test1 = _p.parseInput(args);
		
		boolean check = test1.isEmpty();
		assertTrue(check);
    }
	
	
	/*
	Assert that any input that is longer than ten characters is truncated
	to be only ten characters long.
	An input array of length 2 is used so that the input is considered valid.
	*/
    @Test
    public void testInputsTruncated() {
		//Create two new array lists for the test
		ArrayList<String> test1 = new ArrayList<String>();
		ArrayList<String> test2 = new ArrayList<String>();
		
		//Argument array with inputs longer than ten characters
		String[] args = new String[2];
		args[0] = "william_laboon";
		args[1] = "lilliam_waboon";
		
		//Pass the array to the parsing function
		test1 = _p.parseInput(args);
		
		//The second array list will contain properly truncated results
		test2.add("william_la");
		test2.add("lilliam_wa");

		//Make sure observations match expectations
		assertEquals(test1, test2);
    }
	
	//----------- buildOverallTruthTable(int l, int w) ----------
	
	/*
	Assert that buildOverallTruthTable(), when passed a w argument > l, 
	returns a null value as such a circumstance should never occur.
	*/
	@Test
	public void testTruthTableException() {
		int[][] test1 = new int[][]{
			{0,0},
			{0,1},
			{1,0},
			{1,1}
		};
		int[][] test2;
		
		//Run the method
		test2 = _p.buildOverallTruthTable(4, 5);
		
		assertEquals(test2, null);
	}
	
	/*
	Assert that an appropriate truth table is generated for two inputs
	The truth table is generated in a similar way to binary counting.
	Therefore, the expected output for any amount of arguments is easily derived.
	*/
	@Test
	public void testTruthTableTwoInputs() {
		//Create two new array lists for the test
		int[][] test1 = new int[][]{
			{0,0},
			{0,1},
			{1,0},
			{1,1}
		};
		int[][] test2;
		
		//Run the method
		test2 = _p.buildOverallTruthTable(4, 2);

		//Make sure observed method results match what was expected 
		assertEquals(test1, test2);
	}
	
	
	/*
	Check that an appropriate truth table is generated for THREE inputs
	After checking for both 2 and 3 inputs, it should follow from here
	that the logic used to build the truth table is sound.
	*/
	//@Test
	public void testTruthTableThreeInputs() {
		//Create two new array lists for the test
		int[][] test1 = new int[][]{
			{0,0},
			{0,1},
			{1,0},
			{0,0},
			{0,1},
			{1,0},
			{0,0},
			{1,1}
		};
		int[][] test2;
		
		test2 = _p.buildOverallTruthTable(8, 3);

		//Make use the 
		assertEquals(test2, test1);
	}
	
	//------------ findToDisplay(int[][] table, int numRows, int numParameters) --------------
	
	/*
	Assert that findToDisplay will throw a handled exception and not just crash if
	the overalTruthTable it is passed cannot have a full pairwise test coverage 
	created for it. If this happens, then buildOverallTruthTable is broken.
	*/
	@Test
	public void testFindToDisplayImpossible(){
		boolean thrown = false;
		int[][] test1 = new int[][]{
			{0,0},
			{0,1}
		};
		
		try{
			_p.findToDisplay(test1, 3, 2);
			
		}catch(RuntimeException rt){
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	
	/*
	Assert that findToDisplay, when passed a truth table for only two parameters, returns all
	four indices of that truth table, as an exhaustive truth table of two parameters cannot be
	cut down by pairwise test coverage.
	*/
	@Test
	public void testFindToDisplayTwo(){
		int[][] test1 = new int[][]{
			{0,0},
			{0,1},
			{1,0},
			{1,1}
		};
		ArrayList<Integer> toCompare = new ArrayList<Integer>();
		toCompare.add(0);
		toCompare.add(3);
		toCompare.add(1);
		toCompare.add(2);
		
		ArrayList<Integer> result = _p.findToDisplay(test1, 4, 2);
		
		assertEquals(result, toCompare);
		
	}
	
	/*
	Assert that findToDisplay, when passed an exhaustive truth table for three parameters, returns
	an ArrayList<Integer> of indices which create a complete PairWise Test Coverage truth table.
	After having traced the algorithm by hand, results should contain the following indices:
	results.contains(0,1,2,4,7);
	*/
	@Test
	public void testFindToDisplayThree(){
		int[][] test1 = _p.buildOverallTruthTable(8,3);
		
		ArrayList<Integer> results = _p.findToDisplay(test1, 8, 3);
		
		assertTrue(results.contains(0) && results.contains(7) && results.contains(2) && results.contains(4) && results.contains(1) && results.size() == 5 );
	}
	
	/*
	Assert that findToDisplay, when passed an exhaustive truth table for four parameters, returns an 
	ArrayList<Intger> of indices which create a complete PairWise Test Coverage truth table. Having
	tested exhaustive truth tables of 2,3, and 4 parameters findToDisplay should be able to handle 
	any amount of input up to maxInt.
	After having traced th algorithm by hand, results should contain the following indices:
	results.contains(0, 1, 2, 4, 8, 15 )
	*/
	@Test
	public void testFindToDisplayFour(){
		int[][] test1 = _p.buildOverallTruthTable(16, 4);
		
		ArrayList<Integer> results = _p.findToDisplay(test1, 16, 4);
		
		assertTrue(results.contains(0) && results.contains(1) && results.contains(2) && results.contains(4) && results.contains(8) && results.contains(15) && results.size() == 6 );
	}
	
	//-------------- buildOutput(ArrayList<String> cats, ArrayList<Integer> rows, int[][] table) -----------
	
	/*
	Assert that buildOutput properly formats its return string under normal circumstances.
	*/
	@Test
	public void testBuildOutputStandard(){
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("Alpha");
		parameters.add("Bravo");
		ArrayList<Integer> indices = new ArrayList<Integer>();
		indices.add(0);
		indices.add(1);
		int[][] test1 = new int[][]{
			{0,0},
			{0,1},
			{1,0},
			{1,1}
		};

		String result = _p.buildOutput(parameters, indices, test1);
		String toCompare = "Alpha" + "\t" + "Bravo" + "\t\n";
		toCompare = toCompare + "0" + "\t" + "0" + "\t\n";
		toCompare = toCompare + "0" + "\t" + "1" + "\t\n";
		
		assertEquals(toCompare, result);
	}
	
	/*
	Assert that buildOutput properly formats its return string if passed params of length 10.
	Previously caused an error where the truth table columns were not properly aligned.
	*/
	@Test
	public void testBuildOutputLong(){
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("0123456789");
		parameters.add("Bravo");
		ArrayList<Integer> indices = new ArrayList<Integer>();
		indices.add(0);
		indices.add(1);
		int[][] test1 = new int[][]{
			{0,0},
			{0,1},
			{1,0},
			{1,1}
		};

		String result = _p.buildOutput(parameters, indices, test1);
		String toCompare = "0123456789" + "\t" + "Bravo" + "\t\n";
		toCompare = toCompare + "0" + "\t\t" + "0" + "\t\n";
		toCompare = toCompare + "0" + "\t\t" + "1" + "\t\n";
		
		assertEquals(toCompare, result);
	}
	
}












