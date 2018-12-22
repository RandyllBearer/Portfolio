/*
	HelloWorld.java by Randyll Bearer
	Simple Program to test development environment and Java install
*/


public class HelloWorld {

	public static void main(String args[] ){
		
		String phrase = "";
		phrase = getPhraseToPrint();
		printToScreen(phrase);
		
		System.exit(0);	//Make sure our program ends
		
	}

	private static void printToScreen(String toPrint){
		
		System.out.println(toPrint);
	
	}
	
	
	
	private static String getPhraseToPrint(){
		return "Hello World!";
	}

} 
//End of File