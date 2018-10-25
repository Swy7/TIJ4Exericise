// concurrency/NumRangeGenerator11.java
// TIJ4 Chapter Concurrency, Exercise 11b, page 1157
/** Create a class containing two data fields, and a method that manipulates
* those fields in a multistep process so that, during the execution of that 
* method, those fields are in an "improper state" (according to some definition
* that you establish). Add methods to read the fields, and create multiple 
* threads to call the various methods and show that the data is visible in its 
* "improper state." Fix the problem using the synchronized keyword.
**/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2009
*/

import java.util.*;
import java.util.concurrent.*;

abstract class NumRangeGenerator { // non-task, non-Runnable, can be canceled
	private volatile boolean canceled = false;
	public abstract int[] next(); 
	public void cancel() { canceled = true; }
	public boolean isCanceled() { return canceled; }
}

class NumRangeChecker11 implements Runnable { // task(s) that depend on & share NumRangeGenerator
	private NumRangeGenerator generator;
	private final int id;
	public NumRangeChecker11(NumRangeGenerator g, int ident) {
		generator = g;
		id = ident;
	}
	public void run() {
		System.out.println("Testing..");
		while(!generator.isCanceled()) {
			int[] range = generator.next();
	 		if( range[0] > range[1]) {
				System.out.println("Error in test #" + id + ": min " + range[0] 
					+ " > " + "max " + range[1] );
				generator.cancel();
			}
		}
	}
	public static void test(NumRangeGenerator gen, int count) {
		System.out.println("Press Ctrl-C to exit");
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < count; i++) 
			exec.execute(new NumRangeChecker11(gen, i));		
		exec.shutdown();
	}
	public static void test(NumRangeGenerator gen) {
		test(gen, 10);
	}
}

public class NumRangeGenerator11 extends NumRangeGenerator {
	private int min = 0;
	private int max = 0;
	private int[] range = { min, max };
	private Random rand = new Random();
	public int[] next() { // oops, method should be synchronized
		min = rand.nextInt(100);
		max = rand.nextInt(100);
		Thread.yield();
		if(min > max) max = min;
		int[] ia = { min, max };
		return ia;
	}	
	public static void main(String[] args) {
		NumRangeChecker11.test(new NumRangeGenerator11());
	}
}

/** Synchronized version that runs without error:
*
* import java.util.*;
* import java.util.concurrent.*;
*
* public class SynchronizedNumRangeGenerator11 extends NumRangeGenerator {
*	private int min = 0;
*	private int max = 0;
*	private int[] range = { min, max };
*	private Random rand = new Random();
*	public synchronized int[] next() { // synchronized!
*		min = rand.nextInt(100);
*		max = rand.nextInt(100);
*		Thread.yield();
*		if(min > max) max = min;
*		int[] ia = { min, max };
*		return ia;
*	}	
*	public static void main(String[] args) {
*		NumRangeChecker11.test(new SynchronizedNumRangeGenerator11());
*	}
* }
**/