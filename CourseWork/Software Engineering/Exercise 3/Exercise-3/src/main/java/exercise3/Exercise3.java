package exercise3;

public class Exercise3{
	
	public static int triangleNumberSequence(int i){
		int iteration = 0;
		int cumulative = 0;
		
		while (i > 0){
			cumulative = cumulative + i;
			i = i - 1;
		}
		
		return cumulative;
	}
	
	public static int lazyCatererSequence(int i){
		int result = (int)(Math.pow(i,2) + i + 2)/(2);
		
		return result;
	}
	
	//main = 'com.mrhaki.CurrentDate'
	//classpath = 'classpath'
	
	public static void main(String[] args){
		int userInput = 0;
		int result1 = 0;
		int result2 = 0;
		
		if(args.length != 1 ){
			System.out.println("ERROR: Exercise3.java requires a single integer argument to be passed");
			System.exit(1);
		}else{
			try{
				userInput = Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				System.out.println("ERROR: Exercise3.java could not parse the passed integer value");
				System.exit(1);
			}
		}
		
		result1 = triangleNumberSequence(userInput);
		result2 = lazyCatererSequence(userInput);
		
		System.out.println("Tri(" + userInput + ") = " + result1);
		System.out.println("Lazy(" + userInput + ") = " + result2);
		
		System.exit(0);
	}
	
}