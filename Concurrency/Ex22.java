// concurrency/Ex22.java
// TIJ4 Chapter Concurrency, Exercise 22, page 1203
/** Create an example of a busy wait. One task sleeps for a while and 
* then sets a flag to true. The second task watches that flag inside 
* a while loop (this is the busy wait) and when the flag becomes true,
* sets it back to false and reports the change to the console. Note 
* how much wasted time the program spends inside the busy wait, and 
* create a second version of the program that uses wait() instead of 
* the busy wait.
**/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* July, 2009
*/

import java.util.concurrent.*;
import static org.greggordon.tools.Print.*;

class A implements Runnable {
	boolean flag = false;
	public synchronized void run() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch(InterruptedException e) {
			println("sleep interrupted in A");
		}
		println("A setting flag = true"); 
		flag = true;		
	}	
}

class BusyWait implements Runnable {
	A a = new A();
	long start, end;
	public synchronized A getA() { return a; }
	private BusyWait(A a) {
		this.a = a;
	}
	public static BusyWait buildBusyWait(A a) {
		return new BusyWait(a);
	}
	public synchronized void run() {
		println("Busy a.flag = " + a.flag);		
		while(!Thread.interrupted()) {	
			start = System.nanoTime();		
			if(a.flag) {
				a.flag = false;
				println("BusyWait reset a.flag = false");
				end = System.nanoTime();
				println("Busy waiting " + (end - start) + " nanoseconds");
			}
		}		
	}
}

class BetterWait implements Runnable {
	private A a = new A();
	public synchronized A getA() { return a; }
	private BetterWait(A a) {
		this.a = a;
	}
	public static BetterWait buildBetterWait(A a) {
		return new BetterWait(a);
	}
	public synchronized void run() {
		println("Better a.flag = " + a.flag);
		try {			
			while(!a.flag) {
				wait();	
				a.flag = false;
				println("BetterWait reset a.flag = false");
			}				
		} catch(InterruptedException e) {
			println("BetterWait.run() interrupted");
		}
	}
}

public class Ex22 {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		BusyWait busy = BusyWait.buildBusyWait(new A());
		exec.execute(busy.a);
		exec.execute(busy);
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch(InterruptedException e) {
			println("sleep interrupted in main()");
		}
		println();
		BetterWait better = BetterWait.buildBetterWait(new A());
		exec.execute(better.getA());
		exec.execute(better);
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch(InterruptedException e) {
			println("sleep interrupted in main()");
		}
		synchronized(better) {
			println("Sending better.notifyAll()");
			better.notifyAll();
		}
		exec.shutdownNow();
	}
}
