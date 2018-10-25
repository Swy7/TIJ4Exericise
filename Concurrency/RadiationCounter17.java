// concurrency/RadiationCounter17.java
// TIJ4, Chapter Concurrency, Exercise 17, page 1183
// Create a radiation counter that can have any number of remote sensors.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2009
*/

import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

class RadCount {
	private int count = 0;
	private Random rand = new Random();
	public synchronized int increment() {
		return count++;
	}
	public synchronized int value() { return count; } 
}

class Sensor implements Runnable {
	private static RadCount count = new RadCount();
	private static List<Sensor> sensors = new ArrayList<Sensor>();
	private int number = 0;
	// Doesn't need synchronization to read:
	private final int id;
	private static volatile boolean canceled = false;
	// Atomic operation on a volatile field:
	public static void cancel() { canceled = true; }
	public Sensor(int id) {
		this.id = id;
		// Keep this task in a list. Also prevents
		// garbage collection of dead tasks:
		sensors.add(this);
	}
	public void run() {
		while(!canceled) {
			synchronized(this) {
				++number;
			}
			print(this + " Total: " + count.increment());
			try {
				TimeUnit.MILLISECONDS.sleep(25);
			} catch(InterruptedException e) {
				print("sleep interrupted");
			}
		}
		print("Stopping " + this);
	}
	public synchronized int getValue() { return number; }
	public String toString() {
		return "Sensor " + id + ": " + getValue();
	} 
	public static int getTotalCount() {
		return count.value();
	}
	public static int sumSensors() {
		int sum = 0;
		for(Sensor sensor : sensors)
			sum += sensor.getValue();
		return sum;
	}
}

public class RadiationCounter17 {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 10; i++)
			exec.execute(new Sensor(i));
		// Run for a while, then stop and collect the data:
		TimeUnit.SECONDS.sleep(4);
		Sensor.cancel();
		exec.shutdown();
		if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
			print("Some tasks were not terminated");
		print("Total: " + Sensor.getTotalCount());
		print("Sum of Sensors: " + Sensor.sumSensors());
	}
}