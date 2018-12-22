//A very minimally modified version of the TestRunner.java from exercise 2 provided at
//https://github.com/laboon/CS1632_Fall2017/tree/master/exercises/2/LaboonCoin

import java.util.ArrayList;
import org.junit.runner.*;
import org.junit.runner.notification.*;

public class TestRunner{
	
	public static void main(String[] args){
		
		ArrayList<Class> classesToTest = new ArrayList<Class>();
		boolean anyFailures = false;
		
		//Add any more classes you wish to test here
		classesToTest.add(CitySim9005Test.class);
		
		//Loop through classes to test
		for(Class c: classesToTest){
			
			Result r = JUnitCore.runClasses(c);
			
			for(Failure f : r.getFailures()){
				System.out.println(f.toString());
			}
			
			if(!r.wasSuccessful()){
				anyFailures = true;
			}
			
		}
		
		if(anyFailures){
			System.out.println("\n!!! - At least one failure, see above.");
		}else{
			System.out.println("\nALL TESTS PASSED");
		}
		
	}
	
}