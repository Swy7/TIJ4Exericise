// concurrency/Ex16.java
// TIJ4 Chapter Concurrency, Exercise 16, page 1177
// Modify Exercise 15 to use explicit Lock objects.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* May, 2009
*/

import static net.mindview.util.Print.*; 
import java.util.concurrent.locks.*;

class SyncTest1 { // all methods use same lock
	private Lock lock = new ReentrantLock();
	public void f1() {
		lock.lock();
		try {
			for(int i = 0; i < 5; i++) {
				print("f1()");
				Thread.yield();
			}
		} finally {
			lock.unlock();
		}		
	}
	public void g1() {
		lock.lock();
		try {
			for(int i = 0; i < 5; i++) {
				print("g1()");
				Thread.yield();
			}
		} finally {
			lock.unlock();
		}	
	}
	public void h1() {
		lock.lock();
		try {
			for(int i = 0; i < 5; i++) {
				print("h1()");
				Thread.yield();
			}
		} finally {
			lock.unlock();
		}	
	}
}

class SyncTest2 { // each method has a different lock
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	private Lock lock3 = new ReentrantLock();
	
	public void f2() {
		lock1.lock();
		try {
			for(int i = 0; i < 5; i++) {
				print("f2()");
				Thread.yield();
			}
		} finally {
			lock1.unlock();
		}
	}
	public void g2() {
		lock2.lock();
		try {
			for(int i = 0; i < 5; i++) {
				print("g2()");
				Thread.yield();
			}
		} finally {
			lock2.unlock();
		}
	}
	public void h2() {
		lock3.lock();
		try {
			for(int i = 0; i < 5; i++) {
				print("h2()");
				Thread.yield();
			}
		} finally {
			lock3.unlock();
		}
	}
}

public class Ex16 {
	public static void main(String[] args) {
		final SyncTest1 st1 = new SyncTest1();
		final SyncTest2 st2 = new SyncTest2();
		new Thread() {
			public void run() {
				st1.f1();
			}
		}.start();
		new Thread() {
			public void run() {
				st1.g1();
			}
		}.start();
		new Thread() {
			public void run() {
				st1.h1();
			}
		}.start();		
		new Thread() {
			public void run() {
				st2.f2();
			}
		}.start();
		new Thread() {
			public void run() {
				st2.g2();
			}
		}.start();
		new Thread() {
			public void run() {
				st2.h2();
			}
		}.start();			
	}
}
