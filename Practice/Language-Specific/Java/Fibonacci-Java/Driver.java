/*
* Driver.java by Randyll Bearer 2019
*
* Simple implementation of recursive programming to find the nth fibonacci number
* NOTE: This is a naive implementation using recursion, any significatly large number
* Might require too many recursive calls and therefore overflow the stack space with
* Too much recursive information, it also has a runtime of 2^n so just keep it to < 100
* to be safe.
*/
public class Driver{
	
	//Recursive function to find fibonacci, naive runtime of 2^n, can be brought down
	//In future by using memoization
	public static int fibonacci(int n){
		if(n == 0){	//Base case 1/2: 0 Cannot be broken down further 
			return 0;
		}else if(n == 1){	//Base case 2/2: 1 Cannot be broken down further
			return 1;
		}else{	//Keep decomposing the number
			return fibonacci(n-1) + fibonacci(n-2);
		}
	}
	
	public static void main(String[] args){
	
		int toFind = Integer.parseInt(args[0]);	//Get the input from command line
		int result = fibonacci(toFind);
		System.out.println(toFind+"'th Fibonacci number is " + result);
	
	
	
	}
	
}
//End of File