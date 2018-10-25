// concurrency/CachedThreadPool20.java
// TIJ4 Chapter Concurrency, Exercise 20, page 1191
// Modify CachedThreadPool so that all threads receive an interrupt()
// before they are completed.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* June, 2009
*/

import java.util.concurrent.*;

public class CachedThreadPool20 {
	public static void main(String[] args) throws Exception {
		System.out.println("Using LiftOff:");
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 5; i++) {
			Future<?> f = exec.submit(new LiftOff());
			f.cancel(true);							
		}
		exec.shutdownNow();
		if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
			System.out.println("Some tasks were not terminated");
		// Using modified LiftOff:
		System.out.println("\nUsing LiftOff20:"); 
		ExecutorService exec20 = Executors.newCachedThreadPool();
		for(int i = 0; i < 5; i++) {
			Future<?> f = exec20.submit(new LiftOff20());
			f.cancel(true);					
		}
		exec20.shutdownNow();
		if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
			System.out.println("Some tasks were not terminated");		
	}
}