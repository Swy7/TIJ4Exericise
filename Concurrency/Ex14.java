// concurrency/Ex14.java
// TIJ4 Chapter Concurrency, Exercise 14, page 1169
/** Demonstrate that java.util.Timer scales to large numbers by 
* creating a program that generates many Timer objects that perform
* some simple task when the timeout completes.
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
public class Ex14 implements Runnable {
	private static int timers = 0;
	private static int tasks = 0;
	public void run() {
		try {
			while(timers < 4000) { // create 4000 Timers
				++timers;
				Timer t = new Timer();		
				t.schedule(new TimerTask() {
					public void run() {
						++tasks; 
						if(timers % 100 == 0)		
							System.out.println(timers + " timers did " 
								+ tasks + " tasks");
					}
				}, 0);
				try {
					TimeUnit.MILLISECONDS.sleep(30); // time to do task
				} catch(InterruptedException e) {
					System.out.println("Sleep interrupted");
				}
				t.cancel();
			}
		} finally {
			System.out.println("Done. " + timers + " timers completed " 
				+ tasks + " tasks");
		} 
	}
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Ex14());
		exec.shutdown();
	}
}