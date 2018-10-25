// concurrency/OrnamentalGarden32.java
// TIJ4 Chapter Concurrency, Exercise 32, page 1232
/* Use a CountDownLatch to solve the problem of correlating the
* results from the Entrances in OrnamentalGarden.java. Remove
* the unnecessary code from the new version of the example.
*/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* November, 2009
*/

// concurrency/OrnamentalGarden.java

import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

class Count {
	private int count = 0;
	private Random rand = new Random(47);
	// Remove the synchronized keyword to see counting fail:
	public synchronized int increment() {
		int temp = count;
		if(rand.nextBoolean()) // Yield half the time
			Thread.yield();
		return (count = ++temp);
	}
	public synchronized int value() { return count; } 
}

class Entrance implements Runnable {
	private static Count count = new Count();
	private static List<Entrance> entrances = new ArrayList<Entrance>();
	private int number = 0;
	private final int id;
	private final CountDownLatch doneSignal;
	private static CountDownLatch stopSignal;
	public Entrance(int id, CountDownLatch doneSignal, CountDownLatch stopSignal) {
		this.id = id;
		this.doneSignal = doneSignal;
		this.stopSignal = stopSignal;
		// Keep this task in a list. Also prevents
		// garbage collection of dead tasks:
		entrances.add(this);
	}
	public void run() {
		while(!(stopSignal.getCount() == 0)) {
			synchronized(this) {
				++number;
			}
			print(this + " Total: " + count.increment());
			try { 
				TimeUnit.MILLISECONDS.sleep(100);
			} catch(InterruptedException e) {
				print("sleep interrupted");
			}
		}
		print("Closing " + this);
		doneSignal.countDown();
	}
	public synchronized int getValue() { return number; }
	public String toString() {
		return "Entrance " + id + ": " + getValue();
	} 
	public static int getTotalCount() {
		return count.value();
	}
	public static int sumEntrances() {
		int sum = 0;
		for(Entrance entrance : entrances)
			sum += entrance.getValue();
		return sum;
	}
}

public class OrnamentalGarden32 {
	public static void main(String[] args) throws Exception {
		int SIZE = 5;
		CountDownLatch stopSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(SIZE);
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < SIZE; i++)
			exec.execute(new Entrance(i, doneSignal, stopSignal));
		// Run for a while, then stop and collect the data:
		TimeUnit.SECONDS.sleep(2);
		// close the entrances:
		stopSignal.countDown();
		// wait for entrances to close:
		doneSignal.await();
		exec.shutdown();
		print("Total: " + Entrance.getTotalCount());
		print("Sum of Entrances: " + Entrance.sumEntrances());
	}
}