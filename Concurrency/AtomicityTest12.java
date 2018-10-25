// concurrency/AtomicityTest12.java
// TIJ4 Chapter Concurrency, Exercise 12, page 1167
// Repair AtomicityTest.java using the synchronized keyword. 
// Can you demonstrate that it is now correct?


/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2009
*/

import java.util.concurrent.*;

public class AtomicityTest12 implements Runnable {
	private int i = 0;
	public synchronized int getValue() { return i; }
	private synchronized void evenIncrement() { i++; i++; } 
	public void run() {
		while(true) {
			evenIncrement();
		}
	}
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		AtomicityTest12 at = new AtomicityTest12();
		exec.execute(at);
		while(true) {
			int val = at.getValue();
			if(val % 2 != 0) {
				System.out.println(val);
				System.exit(0);
			}
		}
	}
}
