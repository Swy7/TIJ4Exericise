// concurrency/waxomatic23/WaxOMatic23.java
// TIJ4 Chapter Concurrency, Exercise 23, page 1208
// Demonstrate that WaxOMatic.java works successfully when 
// you use notify() instead of noifyAll().

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* July, 2009
*/

package concurrency.waxomatic23;
import java.util.concurrent.*;
import static net.mindview.util.Print.*;

class Car {
	private boolean waxOn = false;
	public synchronized void waxed() {
		waxOn = true; // Ready to buff
		// notifyAll();
		notify();
	}
	public synchronized void buffed() {
		waxOn = false; // Ready for another coat of wax
		// notifyAll();
		notify();
	}
	public synchronized void waitForWaxing() throws InterruptedException {
		while(waxOn == false) wait();
	}
	public synchronized void waitForBuffing() throws InterruptedException {
		while(waxOn == true) wait();
	}
}	

class WaxOn implements Runnable {
	private Car car;
	public WaxOn(Car c) { car = c; }
	public void run() {
		try {
			while(!Thread.interrupted()) {
				printnb("Wax On! ");
				TimeUnit.MILLISECONDS.sleep(200);
				car.waxed();
				car.waitForBuffing();
			}
		} catch(InterruptedException e) {
			print("Exiting via interrupt");
		}
		print("Ending Wax On task");
	}
}

class WaxOff implements Runnable {
	private Car car;
	public WaxOff(Car c) { car = c; }
	public void run() {
		try {
			while(!Thread.interrupted()) {
				car.waitForWaxing();
				printnb("Wax Off! ");
				TimeUnit.MILLISECONDS.sleep(200);
				car.buffed();
			}
		} catch(InterruptedException e) {
			print("Exiting via interrupt");
		}
		print("Ending Wax Off task");
	}
}

public class WaxOMatic23 {
	public static void main(String[] args) throws Exception {
		Car car = new Car();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new WaxOff(car));
		exec.execute(new WaxOn(car));
		TimeUnit.SECONDS.sleep(5); // Run for a while...
		exec.shutdownNow(); // Interrupt all tasks
	}
}	
 
