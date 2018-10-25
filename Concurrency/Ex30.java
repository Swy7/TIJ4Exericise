// concurrency/Ex30.java
// TIJ4 Chapter Concurrency, Exercise 30, page 1223
// Modify PipedIO.java to use a BlockingQueue instead of a pipe.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* November, 2009
*/

import java.util.concurrent.*;
import java.io.*;
import java.util.*;
import static net.mindview.util.Print.*;

class Sender implements Runnable {
	private Random rand = new Random(47);
	private LinkedBlockingQueue<Character> queue;
	public Sender(LinkedBlockingQueue<Character> lbq) {
		queue = lbq;
	}
	public void run() {
		try {
			while(true)
				for(char c = 'A'; c <= 'z'; c++) {
					queue.put(c);
					TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				}
		} catch(InterruptedException e) {
			print(e + " Sender sleep interrupted");
		}
	}
}

class Receiver implements Runnable {
	private LinkedBlockingQueue<Character> queue;
	public Receiver(LinkedBlockingQueue<Character> lbq) {
		queue = lbq;
	}
	public void run() {
		 try {
			while(true) {
				// Blocks until characters are there:
				printnb("Read: " + (char)queue.take() + ", ");
			}
		 } catch(InterruptedException e) {
			print(e + " Receiver read exception");
		}
	}
}

public class Ex30 {
	public static void main(String[] args) throws Exception {
		LinkedBlockingQueue<Character> lbq = new LinkedBlockingQueue<Character>();
		Sender sender = new Sender(lbq);
		Receiver receiver = new Receiver(lbq);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(sender);
		exec.execute(receiver);
		TimeUnit.SECONDS.sleep(4);
		exec.shutdownNow();
	}
}