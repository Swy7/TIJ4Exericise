// concurrency/ExchangerDemo34.java
// TIJ4 Chapter Concurrency, Exercise 34, page 1253
// Modify ExchangerDemo.java to use your own class
// instead of Fat.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* February, 2010
*/

/* solution includes in same package:
* public class XCTest34 {
*	private static int counter = 0;
*	private final int id = counter++;
*	public String toString() {
*		return "XCTest34 " + id;
*	}
* }
*/

import java.util.concurrent.*;
import java.util.*;
import net.mindview.util.*;

class ExchangerProducer34<T> implements Runnable {
	private Generator<T> generator;
	private Exchanger<List<T>> exchanger;
	private List<T> holder;
	ExchangerProducer34(Exchanger<List<T>> exchg, Generator<T> gen, List<T> holder) {
		exchanger = exchg;
		generator = gen;
		this.holder = holder;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				for(int i = 0; i < ExchangerDemo34.size; i++)
					holder.add(generator.next());
				// Exchange full for empty:
				holder = exchanger.exchange(holder);
			}
		} catch(InterruptedException e) {
			// OK to terminate this way
		}
	}
}

class ExchangerConsumer34<T> implements Runnable {
	private Exchanger<List<T>> exchanger;
	private List<T> holder;
	private volatile T value;
	ExchangerConsumer34(Exchanger<List<T>> ex, List<T> holder) {
		exchanger = ex;
		this.holder = holder;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				holder = exchanger.exchange(holder);
				for(T x : holder) {
					value = x; // Fetch out value
					holder.remove(x); // OK for CopyOnWriteArrayList
				}
			}
		} catch(InterruptedException e) {
			// OK to terminate this way
		}
		System.out.println("Final value: " + value);
	}
}

public class ExchangerDemo34 {
	static int size = 10;
	static int delay = 5; // Seconds
	public static void main(String[] args) throws Exception { 
		if(args.length > 0) size = new Integer(args[0]);
		if(args.length > 1) delay = new Integer(args[1]);
		ExecutorService exec = Executors.newCachedThreadPool();
		Exchanger<List<XCTest34>> xc = new Exchanger<List<XCTest34>>();
		List<XCTest34>
			producerList = new CopyOnWriteArrayList<XCTest34>(),
			consumerList = new CopyOnWriteArrayList<XCTest34>();
		exec.execute(new ExchangerProducer34<XCTest34>(xc, BasicGenerator.create(XCTest34.class), producerList));
		exec.execute(new ExchangerConsumer34<XCTest34>(xc, consumerList));
		TimeUnit.SECONDS.sleep(delay);
		exec.shutdownNow();
	}
}