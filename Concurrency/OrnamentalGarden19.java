// concurrency/OrnamentalGarden19.java
// TIJ4 Chapter Concurrency, Exercise 19, page 1191
// Modify OrnamentalGarden.java so that it uses interrupt().

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* June, 2009
*/

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
	// Doesn't need synchronization to read:
	private final int id;
	private static volatile boolean canceled = false;
	// Atomic operation on a volatile field:
	public static void cancel() { canceled = true; }
	public Entrance(int id) {
		this.id = id;
		// Keep this task in a list. Also prevents
		// garbage collection of dead tasks:
		entrances.add(this);
	}
	public void run() {
		while(!canceled) {
			synchronized(this) { // uninterruptible
				++number;
			}
			print(this + " Total: " + count.increment());
			try {
				TimeUnit.MILLISECONDS.sleep(50);
			} catch(InterruptedException e) {
				print("sleep interrupted");
				break; // need another way out (during sleep)
			}
		}
		print("Stopping " + this);
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

public class OrnamentalGarden19 {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 5; i++)
			exec.execute(new Entrance(i));
		// Run for a while, then stop and collect the data:
		TimeUnit.SECONDS.sleep(4);
		// Sent interrupts (without canceling):
		exec.shutdownNow();
		if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
			print("Some tasks were not terminated");
		print("Total: " + Entrance.getTotalCount());
		print("Sum of Entrances: " + Entrance.sumEntrances());
	}
}