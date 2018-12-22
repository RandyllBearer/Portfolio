//Java imports
import java.lang.*;
import java.util.ArrayList;
import java.io.*;

//JUnit and Mockito imports
import org.junit.*;
import static org.junit.Assert.*;
import org.mockito.*;

/**
 *
 * This is the test class for Deliverable 5, CS1632 Fall 2017
 * Authors: Randyll Bearer (github: rlb97) and Branden Keck (github: BrandenKeck)
 *
 */

public class Del6Test {
	//global
	InputStream standardIn = System.in;
	PrintStream standardOut = System.out;
	
	// Create a new Element instance before testing
    @Before
    public void setup() {
		System.setIn(standardIn);
		System.setOut(standardOut);
    }
	
	//
	@After
	public void tearDown() {
		System.setIn(standardIn);
		System.setOut(standardOut);
	}
	
	//------------ runLoop() ------------
	
	/*
	* Assert that runLoop() will return the proper result of an addition
	* rpn expression.
	*/
	@Test
	public void testRunLoopValidAddition() {
		RPN testRPN = new RPN();
		testRPN.resetHashmap();
		
		ByteArrayInputStream testIn = new ByteArrayInputStream("LET A 10".getBytes());
		System.setIn(testIn);
	
		ArrayList<String> testOutput = testRPN.debugRunLoop(1);
		String expectedOutput = "10";
		String observedOutput = testOutput.get(0);
		
		testIn = new ByteArrayInputStream("A 10 +".getBytes());
		System.setIn(testIn);
		
		testOutput = testRPN.debugRunLoop(1);
		String expectedOutput2 = "20";
		String observedOutput2 = testOutput.get(0);
		
		assertTrue( expectedOutput.equals(observedOutput) && expectedOutput2.equals(observedOutput2) );
		
	}
	
	/*
	* Assert that runLoop() will display the proper error message
	* when there are too many elements left on the stack.
	*/
	@Test
	public void testRunLoopInvalidMultiplication() {
		RPN testRPN = new RPN();
		testRPN.resetHashmap();
		
		ByteArrayInputStream testIn = new ByteArrayInputStream("LET A 10".getBytes());
		System.setIn(testIn);
	
		ArrayList<String> testOutput = testRPN.debugRunLoop(1);
		String expectedOutput = "10";
		String observedOutput = testOutput.get(0);
		
		testIn = new ByteArrayInputStream("A 10 10 +".getBytes());
		System.setIn(testIn);
		
		testOutput = testRPN.debugRunLoop(1);
		String expectedOutput2 = "Line 1: 2 elements in stack after evaluation";
		String observedOutput2 = testOutput.get(0);
		
		assertTrue( expectedOutput.equals(observedOutput) && expectedOutput2.equals(observedOutput2) );
		
	}
	
	
	//------------- read() ---------------
	
	
	/*
	* Assert that read() returns a properly split array of strings
	* maintaining all characters as uppercase.
	*/
	@Test
	public void testReadUppercase() {
		RPN testRPN = new RPN();
		testRPN.resetHashmap();
		ByteArrayInputStream testIn = new ByteArrayInputStream("LET A 10 20 *".getBytes());
		System.setIn(testIn);
		
		String[] expectedOutput = {"LET", "A", "10", "20", "*" };
		String[] observedOutput = testRPN.read("", 1);
		
		assertArrayEquals(expectedOutput, observedOutput);
	}
	
	
	/*
	* Assert that read() returns a properly split array of uppercase
	* strings when it reads in lowercase strings.
	*/
	@Test
	public void testReadLowercase() {
		RPN testRPN = new RPN();
		testRPN.resetHashmap();
		ByteArrayInputStream testIn = new ByteArrayInputStream("let a 10 20 *".getBytes());
		System.setIn(testIn);
		
		String[] expectedOutput = {"LET", "A", "10", "20", "*" };
		String[] observedOutput = testRPN.read("", 1);
		
		assertArrayEquals(expectedOutput, observedOutput);
	}
	
	
	/*
	* Assert that read() returns a properly split array of uppercase
	* strings when it reads in mixedcase strings.
	*/
	@Test
	public void testReadMixedcase() {
		RPN testRPN = new RPN();
		ByteArrayInputStream testIn = new ByteArrayInputStream("lET a 10 20 *".getBytes());
		System.setIn(testIn);
		
		String[] expectedOutput = {"LET", "A", "10", "20", "*" };

		String[] observedOutput = testRPN.read("", 1);
		
		assertArrayEquals(expectedOutput, observedOutput);
	}
	
	//------------- eval() ----------------
	
	/*
	* Assert that when passed a valid division rpn expression,
	* eval() will return the proper result string to be printed
	*/
	@Test
	public void testEvalValidDivision() {
		RPN testRPN = new RPN();
		String[] testArray = {"100", "5", "/" };
		
		String observedOutput = testRPN.eval(testArray, 1);
		
		String expectedOutput = "20";

		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* Assert that when passed a valid addition rpn expression,
	* eval() will return the proper result string to be printed
	*/
	@Test
	public void testEvalValidAddition() {
		RPN testRPN = new RPN();
		String[] testArray = {"100", "5", "+" };
		
		String observedOutput = testRPN.eval(testArray, 1);
		
		String expectedOutput = "105";

		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* Assert that when passed a valid subtraction rpn expression,
	* eval() will return the proper result string to be printed
	*/
	@Test
	public void testEvalValidSubtraction() {
		RPN testRPN = new RPN();
		String[] testArray = {"100", "5", "-" };
		
		String observedOutput = testRPN.eval(testArray, 1);
		
		String expectedOutput = "95";

		assertEquals(expectedOutput, observedOutput );
	}
	
	/*
	* Assert that when passed a valid multiplication rpn expression,
	* eval() will return the proper result string to be printed
	*/
	@Test
	public void testEvalValidMultiplication() {
		RPN testRPN = new RPN();
		String[] testArray = {"100", "5", "*" };
		
		String observedOutput = testRPN.eval(testArray, 1);
		
		String expectedOutput = "500";

		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* Assert that when eval() is passed an rpn expression which
	* includes an uninitialized variable that it returns the proper
	* error string
	*/
	@Test
	public void testEvalInvalidInitialization() {
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"A", "5", "*" };
	
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: Variable A is not initialized";
		
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* Assert that when eval() is passed an rpn expression which
	* contains too many operators, that it returns the proper
	* error string
	*/
	@Test
	public void testEvalInvalidEmptyStack() {
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"5", "5", "*", "/" };
	
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: Operator / applied to empty stack";
		
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* Assert that when eval() is passed an rpn expression which
	* results in the stack containing more than one element that it
	* returns the proper error string
	*/
	@Test
	public void testEvalInvalidExcessiveStack() {
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"5", "5", "5","*"};
	
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: 2 elements in stack after evaluation";
		
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* Assert that when eval() is passed an rpn expression which
	* contains an invalid keyword, that it returns the proper
	* error string
	*/
	@Test
	public void testEvalInvalidKeyword() {
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"LOOP"};
	
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: Unknown keyword LOOP";
		
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* Assert that when eval() is passed an rpn expression which
	* contains a keyword in a non-evaluatable location, that it
	* returns the proper error string.
	*/
	@Test
	public void testEvalInvalidKeywordLocation() {
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"4", "3", "LET", "+", "a"};
	
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: Could not evaluate expression";
		
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* Assert that when eval() is passed an rpn expression which
	* contains a keyword in a non-evaluatable location, that it
	* returns the proper error string.
	*/
	@Test
	public void testEvalInvalidInitializationEmpty() {
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"LET", "A"};
	
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: Operator LET applied to empty stack";
		
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	
	//------------- print() ---------------
	
	
	/*
	* Assert that print() will output its given string to the correct
	* system.out stream when errorFlag = false;
	*/
	@Test
	public void testPrintOut() {
		RPN testRPN = new RPN();
		testRPN.setErrorFlag(false);
		String testString = "50\n";
		
		ByteArrayOutputStream testByteOut = new ByteArrayOutputStream();
		PrintStream testOut = new PrintStream(testByteOut);
		System.setOut(testOut);
		
		testRPN.print(testString, 1);
		
		assertTrue(testByteOut.toString().contains(testString) );
		
	}
	
	
	/*
	* Assert that print() will output its given string to the correct
	* system.err stream when errorFlag = true;
	*/
	@Test
	public void testPrintErr() {
		RPN testRPN = new RPN();
		testRPN.setErrorFlag(true);
		String testString = "Line 1: Unknown keyword LOOP\n";
		
		ByteArrayOutputStream testErr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(testErr));
		
		testRPN.print(testString, 1);
		
		assertTrue(testErr.toString().contains(testString) );
		
	}
	
	//------------ openFile() ----------

	/*
	* Assert that openFile() is able to open a valid filepath without error.
	*/
	@Test
	public void testOpenFileValidPath() {
		RPN testRPN = new RPN();
		BufferedReader inFile = null;
		
		inFile = testRPN.openFile("./test_files/File1.rpn");
		
		assertTrue(inFile != null);
	}
	
	/*
	* Assert that openFile() will throw an exception when filepath does not exist.
	*/
	@Test
	public void testOpenFileInvalidPath() {
		RPN testRPN = new RPN();
		BufferedReader inFile = null;

		inFile = testRPN.openFile("./test_files/shouldnt.exist");
		
		assertTrue(inFile == null);
	}
	
	//-------------- readFiles() ----------
	
	/*
	* Assert that readFiles() will return true when its evaluation finishes successfuly
	*/
	@Test
	public void testReadFilesValid() {
		RPN testRPN = new RPN();
		String[] testArguments = new String[1];
		testArguments[0] = "./test_files/File2.rpn";
		
		boolean result = testRPN.readFiles(testArguments);
		
		assertTrue(result);
	}
	
	/*
	* Assert that readFiles() will return false when its evaluation comes across an error
	*/
	@Test
	public void testReadFilesInvalid() {
		RPN testRPN = new RPN();
		String[] testArguments = new String[1];
		testArguments[0] = "./test_files/Bad.rpn";
		
		boolean result = testRPN.readFiles(testArguments);
		
		assertFalse(result);
	}
	
}