//Java imports
import java.lang.*;
import java.util.logging.*;
import java.util.concurrent.TimeUnit;
import java.util.List;

//JUnit imports
import org.junit.*;
import static org.junit.Assert.*;

//Selenium imports
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * This is the test class for Deliverable 3, CS1632 Fall 2017
 * Authors: Randyll Bearer (github: rlb97) and Branden Keck (github: BrandenKeck)
 *
 */

public class Del3Test {
	
	// Creation of the driver
    static WebDriver driver;
    
	// Turns of the CSS warnings by suppressing the logger from the HTMLUnit package
    @BeforeClass
    public static void setUpDriver() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		driver = new HtmlUnitDriver(); 
    }
	
	// Before every test, load driver WebDriver with the cs1632ex.herokuapp.com
	//homepage. Then, force the element to wait some time for the page to fully load.
	@Before
	public void setUp() throws Exception {
		driver.get("https://cs1632ex.herokuapp.com/");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	// Requirement 1
	// Assert that the cs1632ex.herokuapp.com homepage contains the
	// text "Welcome, friend, to a land of pure calculation"
	@Test
	public void testHomepageTextDisplayed1() {
		try {
			String bodyText = driver.findElement(By.className("jumbotron")).getText();
			assertTrue(bodyText.contains("Welcome, friend,\nto a land of pure calculation"));

		} catch (NoSuchElementException nseex) {
			fail();
		}catch(NullPointerException npex){
			fail();
		}
	} 
	
	/*
	Requirement 1
	Assert that the cs1632ex.herokupapp.com homepage contains the
	text "Used for CS1632 Software Quality Assurance, taught by Bill Laboon".
	Searches the page for any element which contains the above text, if no such
	element is found, the test fails.
	*/
	@Test
	public void testHomepageTextDisplayed2(){
		try{
			String text = "Used for CS1632 Software Quality Assurance, taught by Bill Laboon";
			driver.findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	/*
	Requirement 2
	Assert that cs1632ex.herokuapp.com displays the 5 necessary links:
	"CS1632 D3 Home", "Factorial", "Fibonacci", "Hello", and "Cathedral Pics"
	If these 5 links are not present, the test fails.
	*/
	@Test
	public void testHomepageLinksPresent(){
		try{
			driver.findElement(By.linkText("CS1632 D3 Home"));
			driver.findElement(By.linkText("Factorial"));
			driver.findElement(By.linkText("Fibonacci"));
			driver.findElement(By.linkText("Hello"));
			driver.findElement(By.linkText("Cathedral Pics"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	/*
	Requirement 2
	Assert that cs1632ex.herokuapp.com/cathy displays the 5 necessary links:
	"CS1632 D3 Home", "Factorial", "Fibonacci", "Hello", and "Cathedral Pics"
	If these 5 links are not present, the test fails.
	*/
	@Test
	public void testCathedralPicsLinksPresent(){
		try{
			driver.get("https://cs1632ex.herokuapp.com/cathy");
			driver.findElement(By.linkText("CS1632 D3 Home"));
			driver.findElement(By.linkText("Factorial"));
			driver.findElement(By.linkText("Fibonacci"));
			driver.findElement(By.linkText("Hello"));
			driver.findElement(By.linkText("Cathedral Pics"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	/*
	Requirement 2
	Assert that the five links on the cs1632ex.herokuapp.com homepage 
	link you to their respective pages. I.E. Guarantees that 
	the "Cathedral Pics" link takes you to https://cs1632ex.herokuapp.com/cathy.
	If the links do not navigate to the correct url's, the test fails.
	*/
	@Test
	public void testHomepageLinksAccurate(){
		try{
			driver.findElement(By.linkText("CS1632 D3 Home")).click();
			String url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/"));
			
			driver.get("https://cs1632ex.herokuapp.com/");
			driver.findElement(By.linkText("Factorial")).click();
			url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/fact"));
			
			driver.get("https://cs1632ex.herokuapp.com/");
			driver.findElement(By.linkText("Fibonacci")).click();
			url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/fib"));
			
			driver.get("https://cs1632ex.herokuapp.com/");
			driver.findElement(By.linkText("Hello")).click();
			url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/hello"));
			
			driver.get("https://cs1632ex.herokuapp.com/");
			driver.findElement(By.linkText("Cathedral Pics")).click();
			url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/cathy"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	/*
	Requirement 2
	Assert that the five links on the cs1632ex.herokuapp.com/cathy 
	Cathedral Pics page link you to their respective pages. I.E. Guarantees that 
	the "CS1632 D3 Home" link takes you to https://cs1632ex.herokuapp.com/.
	If the links do not navigate to the correct url's, the test fails.
	*/
	@Test
	public void testCathedralPicsLinksAccurate(){
		try{
			driver.get("https://cs1632ex.herokuapp.com/cathy");
			driver.findElement(By.linkText("Cathedral Pics")).click();
			String url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/cathy"));
			
			driver.get("https://cs1632ex.herokuapp.com/cathy");
			driver.findElement(By.linkText("Factorial")).click();
			url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/fact"));
			
			driver.get("https://cs1632ex.herokuapp.com/cathy");
			driver.findElement(By.linkText("Fibonacci")).click();
			url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/fib"));
			
			driver.get("https://cs1632ex.herokuapp.com/cathy");
			driver.findElement(By.linkText("Hello")).click();
			url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/hello"));
			
			driver.get("https://cs1632ex.herokuapp.com/cathy");
			driver.findElement(By.linkText("CS1632 D3 Home")).click();
			url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	
	/*
	Requirement 3
	the factorial page shall take a positive integer (1-100)
	and return its factorial.  
	
	This is a base case with input 5.
	An assertion is made on the expected output,
	that text containing the appropriate value is displayed.
	*/
	@Test
	public void testFactorialPageWorksBase() {
		try{
			
			driver.findElement(By.linkText("Factorial")).click();
			
			WebElement fBox = driver.findElement(By.name("value"));
			fBox.sendKeys("5");
			
			WebElement sButton = driver.findElement(By.xpath("//input[@value='Submit']"));
			sButton.click();
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();
			
			assertTrue(eText.contains("Factorial of 5 is 120!"));
		
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	
	/*
	Requirement 3
	the factorial page shall take a positive integer (1-100)
	and return its factorial.  
	
	This is an edge case.
	An input with value 1 is used.
	An assertion is made on the expected output,
	that text containing the appropriate value (1) is displayed.
	*/
	@Test
	public void testFactorialPageWorksEdgeLower() {
		try{
			
			driver.findElement(By.linkText("Factorial")).click();
			
			WebElement fBox = driver.findElement(By.name("value"));
			fBox.sendKeys("1");
			
			WebElement sButton = driver.findElement(By.xpath("//input[@value='Submit']"));
			sButton.click();
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();
			
			assertTrue(eText.contains("Factorial of 1 is 1!"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	
	/*
	Requirement 3
	the factorial page shall take a positive integer (1-100)
	and return its factorial.  
	
	This is an edge case.
	An input with value 100 is used.
	An assertion is made on the expected output,
	that text containing the (lengthy) appropriate value is displayed.
	*/
	@Test
	public void testFactorialPageWorksEdgeUpper() {
		try{
			
			driver.findElement(By.linkText("Factorial")).click();
			
			WebElement fBox = driver.findElement(By.name("value"));
			fBox.sendKeys("100");
			
			WebElement sButton = driver.findElement(By.xpath("//input[@value='Submit']"));
			sButton.click();
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();
			
			assertTrue(eText.contains("Factorial of 100 is 93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000!"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
		
	/*
	Requirement 4
	the Fibonacci page shall take a positive integer (1-100)
	and return its associated Fibonacci number.
	
	This is a base case with input 5.
	An assertion is made on the expected output,
	that text containing the appropriate value is displayed.
	*/
	@Test
	public void testFibonacciPageWorksBase() {
		try{
			
			driver.findElement(By.linkText("Fibonacci")).click();
			
			WebElement fBox = driver.findElement(By.name("value"));
			fBox.sendKeys("5");
			
			WebElement sButton = driver.findElement(By.xpath("//input[@value='Submit']"));
			sButton.click();
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();
			
			assertTrue(eText.contains("Fibonacci of 5 is 8!"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	
	/*
	Requirement 4
	the Fibonacci page shall take a positive integer (1-100)
	and return its associated Fibonacci number.
	
	This is an edge case with input 1.
	An assertion is made on the expected output,
	that text containing the appropriate value (1) is displayed.
	*/
 	@Test
	public void testFibonacciPageWorksEdgeLower() {
		try{
		
			driver.findElement(By.linkText("Fibonacci")).click();
			
			WebElement fBox = driver.findElement(By.name("value"));
			fBox.sendKeys("1");
			
			WebElement sButton = driver.findElement(By.xpath("//input[@value='Submit']"));
			sButton.click();
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();
			
			assertTrue(eText.contains("Fibonacci of 1 is 1!"));
		
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	
	/*
	Requirement 4
	the Fibonacci page shall take a positive integer (1-100)
	and return its associated Fibonacci number.
	
	This is an edge case with input 100.
	An assertion is made on the expected output,
	that text containing the appropriate value is displayed.
	
	FAILS -- Fibonacci page bug (see defect report)
	*/
 	@Test
	public void testFibonacciPageWorksEdgeUpper() {
		try{
		
			driver.findElement(By.linkText("Fibonacci")).click();
			
			WebElement fBox = driver.findElement(By.name("value"));
			fBox.sendKeys("100");
			
			WebElement sButton = driver.findElement(By.xpath("//input[@value='Submit']"));
			sButton.click();
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();
			
			assertTrue(eText.contains("Fibonacci of 100 is 573147844013817084101!"));
		
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	
	/*
	Requirement 5
	the Factorial page should output the number one for an invalid input.
	
	All negative inputs are invalid.  -1 is used in this case.
	An assertion is made on the expected output,
	that text displays a value of 1.
	*/
	@Test
	public void testFactorialPageInvalidNegativeInt() {
		try{
			
			driver.findElement(By.linkText("Factorial")).click();
			
			WebElement fBox = driver.findElement(By.name("value"));
			fBox.sendKeys("-1");
			
			WebElement sButton = driver.findElement(By.xpath("//input[@value='Submit']"));
			sButton.click();
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();
			
			assertTrue(eText.contains("Factorial of -1 is 1!"));
		
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	
	/*
	Requirement 5
	the Fibonacci page should ALSO output the number one for an invalid input.
	
	All negative inputs are invalid.  -1 is used in this case.
	An assertion is made on the expected output,
	that text displays a value of 1.
	*/
	@Test
	public void testFibonacciPageInvalidNegativeInt() {
		try{
		
			driver.findElement(By.linkText("Fibonacci")).click();
			
			WebElement fBox = driver.findElement(By.name("value"));
			fBox.sendKeys("-1");
			
			WebElement sButton = driver.findElement(By.xpath("//input[@value='Submit']"));
			sButton.click();
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();
			
			assertTrue(eText.contains("Fibonacci of -1 is 1!"));
		
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	/*
	Requirement 5
	the Factorial page should output the number one for an invalid input.
	
	All float inputs are invalid.  0.01 is used in this case.
	An assertion is made on the expected output,
	that text displays a value of 1.
	
	FAILS - Float not handled properly (see defect report)
	*/
	@Test
	public void testFactorialPageInvalidFloat() {
		try{
		
			driver.findElement(By.linkText("Factorial")).click();
			
			WebElement fBox = driver.findElement(By.name("value"));
			fBox.sendKeys("0.01");
			
			WebElement sButton = driver.findElement(By.xpath("//input[@value='Submit']"));
			sButton.click();
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();
			
			assertTrue(eText.contains("Factorial of 0.01 is 1!"));
		
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	
	/*
	Requirement 5
	the Fibonacci page should ALSO output the number one for an invalid input.
	
	All float inputs are invalid.  0.01 is used in this case.
	An assertion is made on the expected output,
	that text displays a value of 1.
	
	FAILS - Float not handled properly (see defect report)
	*/
	@Test
	public void testFibonacciPageInvalidFloat() {
		try{
		
			driver.findElement(By.linkText("Fibonacci")).click();
			
			WebElement fBox = driver.findElement(By.name("value"));
			fBox.sendKeys("0.01");
			
			WebElement sButton = driver.findElement(By.xpath("//input[@value='Submit']"));
			sButton.click();
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();
			
			assertTrue(eText.contains("Fibonacci of 0.01 is 1!"));
		
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	
	/*
	Requirement 6
	the Hello page should display the appropriate text when the 
	url path ends with '/hello' with no trailing values
	
	We will assert on the expected value of text on the page,
	"Hello CS1632, from Prof. Laboon"
	*/
	@Test
	public void testHelloPageNoTrailingValue() {
		try{
			
			driver.findElement(By.linkText("Hello")).click();
			String url = driver.getCurrentUrl();
			assertTrue(url.equals("https://cs1632ex.herokuapp.com/hello"));

			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();

			assertTrue(eText.contains("Hello CS1632, from Prof. Laboon!"));
		
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	/*
	Requirement 7
	the Hello page should display the appropriate text when the 
	url path ends with '/hello/[name]' where the response is the original
	message except it ends with "from [name]".
	
	This test will be the base case using the name "Sammy"
	We will assert on the expected value of text on the page,
	"Hello CS1632, from Sammy"
	*/
	@Test
	public void testHelloPageNameBaseCase() {
 		try{
			
			driver.findElement(By.linkText("Hello")).click();
			driver.get("https://cs1632ex.herokuapp.com/hello/Sammy");
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();

			assertTrue(eText.contains("Hello CS1632, from Sammy!"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	/*
	Requirement 7
	the Hello page should display the appropriate text when the 
	url path ends with '/hello/[name]' where the response is the original
	message except it ends with "from [name]".  
	
	This is an edge case.  The requirements state that ALL inputs are allowed.
	Therefore, we will assert that common, non-English names are also covered 
	by this criteria.
	
	Two names will be tested in this Test Case
		1. Eduardo Michelle Núñez Méndez (Famous baseball player)
		2. Thomas Müller (Famous footballer/soccer player)
		
	We will assert on the two expected responses to the URL changes,
	1. "Hello CS1632, from Eduardo Michelle Núñez Méndez"
	2. "Hello CS1632, from Thomas Müller"
	*/
	@Test
	public void testHelloPageNameEdgeDifferentAlphabets() {
 		try{
			
			driver.findElement(By.linkText("Hello")).click();
			
			//Name 1:
			driver.get("https://cs1632ex.herokuapp.com/hello/Eduardo Michelle Núñez Méndez");
			WebElement jbt1 = driver.findElement(By.className("jumbotron"));
			String eText1 = jbt1.getText();

			assertTrue(eText1.contains("Hello CS1632, from Eduardo Michelle Núñez Méndez!"));
			
			//Name 2:
			driver.get("https://cs1632ex.herokuapp.com/hello/Thomas Müller");
			WebElement jbt2 = driver.findElement(By.className("jumbotron"));
			String eText2 = jbt2.getText();

			assertTrue(eText2.contains("Hello CS1632, from Thomas Müller!"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	
	/*
	Requirement 7
	the Hello page should display the appropriate text when the 
	url path ends with '/hello/[name]' where the response is the original
	message except it ends with "from [name]".  
	
	This is an edge case.  The requirements state that ALL inputs are allowed.
	Therefore, we must test cases that use common url characters because it is
	possible to escape these charactes with "%[hexadecimal code]"
	
	The first edge case we will test is the question mark (used in queries),
	we will used the trailing input, "Sammy (Wanna hang out later?)"
	
	We will assert on the expected value of text on the page,
	"Hello CS1632, from Sammy (Wanna hang out later?)"
	
	FAILS - Question mark is not excaped (see defect report)
	*/
 	@Test
	public void testHelloPageNameEdgeQuestionMark() {
 		try{
			
			driver.findElement(By.linkText("Hello")).click();
			driver.get("https://cs1632ex.herokuapp.com/hello/Sammy (Wanna hang out later?)");
			
			WebElement jbt = driver.findElement(By.className("jumbotron"));
			String eText = jbt.getText();

			assertTrue(eText.contains("Hello CS1632, from Sammy (Wanna hang out later?)!"));
			
		}catch(NoSuchElementException nseex){
			fail();
		}
	}
	
	/*
	Requirement 8
	Assert that https://cs1632ex.herokuapp.com/cathy displays 3 images in
	a numbered list. This test attempts to find an orderedList element and
	counts the number of child list elements it contains. If the number != 3
	or the images are not displayed, the test fails.
	*/
	@Test
	public void testCathyPageNumberedList(){
		try{
			driver.get("https://cs1632ex.herokuapp.com/cathy");
			WebElement orderedList = driver.findElement(By.tagName("ol") );
			List<WebElement> listElements = orderedList.findElements(By.tagName("img"));
			assertEquals(listElements.size(), 3);
			
		}catch(NoSuchElementException nsee){
			fail();
		}
	}
	
	
}
