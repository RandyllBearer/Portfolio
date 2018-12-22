package test;

public class test{

	public static void Test(int i){
		System.out.println("TEST SUCCESSFUL " + i);
	}

	public static void main(String[] args){
		int userNumber = Integer.parseInt(args[0]);
		Test(userNumber);
		System.exit(0);
	}


	
}

