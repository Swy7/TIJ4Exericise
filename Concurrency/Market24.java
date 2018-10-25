// concurrency/Market24.java
// TIJ4 Chapter Concurrency, Exercise 24, page 1212
/** Solve a single-producer, single consumer problem using wait() and 
* notifyAll(). The producer must not overflow the receiver's buffer,
* which can happen if the producer is faster than the consumer. If the
* consumer is faster than the producer, then it must not read the same
* data more than once. Do not assume anything about the relative speeds
* of the producer or consumer.
**/

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* September, 2009
*/

import java.util.*;
import java.util.concurrent.*;
import static org.greggordon.tools.Print.*;

class Item {
	private final int itemNum;
	public Item(int itemNum) { this.itemNum = itemNum; }
	public String toString() { return "Item " + itemNum; }
}

class Producer implements Runnable {
	private int count = 0;
	Market24 market;
	Producer(Market24 m) { market = m; }
	protected int getCount() { return count; }
	public void run() {
		while(!Thread.interrupted()) {
			try {
				while(count < 100) {
					Item item = new Item(++count);
					if(market.storage.offer(item)) {
						println("Produced " + item);
						// Notify consumer that Item available:
						synchronized(market.consumer) { 
							market.consumer.notifyAll();
						}	
					}
					// Storage holds only 10 Items:
					synchronized(this) { 
						while(!(market.storage.size() < 10)) {
							wait();
						}
					}
			}
			// Stop production after 100 Items:
			println("Produced " + count + " Items"
			 	+ "\nStopping production");
			market.exec.shutdownNow();	
			} catch(InterruptedException e) {
				println("Producer interrupted");
				println("Produced " + count + " Items"); 
			}					
		}
	}
}

class Consumer implements Runnable {
	int consumed = 0;
	Market24 market;
	List<Item> cart = new ArrayList<Item>();
	Consumer(Market24 m) { market = m; }
	public void run() {
		try {
			while(!Thread.interrupted()) {
				// Cannot consume more Items than produced:
				synchronized(this) {
					while(!(cart.size() < market.producer.getCount())) {
						wait();
					} 
				}
				// Move Item from storage to cart:				
				if(cart.add(market.storage.poll())) {
					println("Consuming Item " + ++consumed); 
					// Notify producer - ready for more:
					synchronized(market.producer) {
						market.producer.notifyAll();
					}
				}			
			}		
		}
		catch(InterruptedException e) {
			println("Consumer interrupted");
			println("Consumed " + consumed + " Items");
		}
	}
}


public class Market24 {
	ExecutorService exec = Executors.newCachedThreadPool();
	Queue<Item> storage = new LinkedList<Item>();
	Producer producer = new Producer(this);
	Consumer consumer = new Consumer(this);
	public Market24() {
		exec.execute(producer);
		exec.execute(consumer);
	}
	public static void main(String[] args) {
		new Market24();
	}
}