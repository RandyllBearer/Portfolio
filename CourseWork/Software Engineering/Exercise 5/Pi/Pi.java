import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ThreadLocalRandom;

public class Pi{
	static AtomicLong successes = new AtomicLong();
	
	
	public static void main(String[] args){
		final long start = System.currentTimeMillis();
		successes.set(0);
		
		//Check Arguments
		if(args.length < 2 || args.length > 2){
			System.out.println("ERROR: Pi.java only accepts two long arguments.");
			System.exit(1);
		}
		final long numThreads = Long.parseLong(args[0]);
		final long numTimes = Long.parseLong(args[1]);
		
		//Make Threads
		Thread[] threads = new Thread[(int)numThreads];
		final double toDo = (double) numTimes/numThreads;
		int i = 0;
		while(i < numThreads){
			threads[i] = new Thread(() ->{
				calculate(toDo);
			});
			i = i + 1;
		}
		
		//Run/Join Threads
		try{
			for(Thread t : threads){
				t.start();
			}
			for(Thread t : threads){
				t.join();
			}
		}catch(InterruptedException ie){
			System.out.println("ERROR: InterruptedException thrown when trying to start/join threads");
			System.exit(7);
		}
		
		long success = successes.get();
		long times = numTimes;
		long four = 4;
		double insideOutside = (double) success/times;
		
		double ratio = (((double) success/times) * four);
		
		System.out.println("Number of Iterations = " + numTimes);
		System.out.println("Number of Values Inside the Circle = " + success);
		System.out.println("Ratio of Inside:Outside = " + insideOutside);
		System.out.println("Approximated Value of Pi = " + ratio);
		
		final long durationInMilliseconds = System.currentTimeMillis()-start;
		System.out.println("task took " + durationInMilliseconds + "ms");
		
		System.exit(0);
	}
	
	public static void calculate(double amount){
		int j = 0;
		while(j < amount){
			double x = ThreadLocalRandom.current().nextDouble(0.000,1.000);
			double y = ThreadLocalRandom.current().nextDouble(0.000,1.000);
			if( (Math.pow(x,2) + Math.pow(y,2)) <= 1 ){
				//System.out.println("Thread: " + Thread.currentThread().getName());	//TEST
				successes.addAndGet(1);
			}
			
			j = j + 1;
		}
	}
	
}
