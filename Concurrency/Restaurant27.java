// concurrency.Restaurant27.java
// TIJ4 Chapter Concurrency, page 1215, Exercise 27
// Modify Restaurant.java to use explicit Lock and Condition objects.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* September, 2009
*/

import java.util.concurrent.*;
import static net.mindview.util.Print.*;
import java.util.concurrent.locks.*;

class Meal {
	private final int orderNum;
	public Meal(int orderNum) { this.orderNum = orderNum; }
	public String toString() { return "Meal " + orderNum; }
}

class WaitPerson27 implements Runnable {
	private Restaurant27 restaurant;
	protected Lock lock = new ReentrantLock();
	protected Condition condition = lock.newCondition();
	public WaitPerson27(Restaurant27 r) { restaurant = r; }
	public void run() {
		try {
			while(!Thread.interrupted()) {
				lock.lock();
				try {
					while(restaurant.meal == null)
						condition.await();
				} finally {
					lock.unlock();
				}
				print("waitPerson got " + restaurant.meal);
				restaurant.chef.lock.lock();
				try {
					restaurant.meal = null;
					restaurant.chef.condition.signalAll();
				} finally {
					restaurant.chef.lock.unlock();
				}				
			}	
		} catch(InterruptedException e) {
			print("WaitPerson27 interrupted");
		}
	}
}

class Chef27 implements Runnable {
	private Restaurant27 restaurant;
	private int count = 0;
	protected Lock lock = new ReentrantLock();
	protected Condition condition = lock.newCondition();
	public Chef27(Restaurant27 r) { restaurant = r; }
	public void run() {
		try {
			while(!Thread.interrupted()) {
				lock.lock(); 
				try {
					while(restaurant.meal != null)
						condition.await();
				} finally {
					lock.unlock();
				}
				if(++count == 10) {
					print("Out of food, closing");
					restaurant.exec.shutdownNow();
					return;
				}
				printnb("Order up! ");
				restaurant.waitPerson.lock.lock();
				try {
					restaurant.meal = new Meal(count);
					restaurant.waitPerson.condition.signalAll();
				} finally {
					restaurant.waitPerson.lock.unlock();
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch(InterruptedException e) {
			print("chef interrupted");
		}
	}	
}

public class Restaurant27 {
	Meal meal;
	ExecutorService exec = Executors.newCachedThreadPool();
	WaitPerson27 waitPerson = new WaitPerson27(this);
	Chef27 chef = new Chef27(this);
	public Restaurant27() {
		exec.execute(chef);
		exec.execute(waitPerson);
	}
	public static void main(String[] args) {
		new Restaurant27();
	}
}